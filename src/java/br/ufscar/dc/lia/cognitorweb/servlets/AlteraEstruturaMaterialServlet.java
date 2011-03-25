/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.comum.*;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para alteração da estrutuda de um material.
 *
 * Algumas seções do codigo não levam em consideração a ordem de inserção.
 * Para esses casos, a seção alterarOrdem é responsável em alterar a ordem dos nós.
 *
 * Note que a estrutura da árvore só é alterada para materiais que não
 * usam o assistente de estruturação do conhecimento, ou seja, contem apenas
 * grupos e páginas. A única excessão é a alteração de título de páginas
 * que é refletida no conceito.
 *
 * @author David Buzatto
 */
public class AlteraEstruturaMaterialServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings( value = "unchecked" )
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResourceBundle rs = Uteis.getBundle( request.getLocale().getLanguage() );

        Map< String, String > configs = ( HashMap< String, String > )
                getServletContext().getAttribute( "configs" );

        out.print( "{ success: " );

        try {

            Usuario u = Uteis.validateUser( request, rs, TipoUsuario.values() );
            boolean mesmoUsuario = false;

            String acao = request.getParameter( "acao" );

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            // altera o título de um material
            if ( acao.equals( "alterarTitulo" ) ) {

                long id = Long.parseLong( request.getParameter( "id" ) );
                String tipo = request.getParameter( "tipo" );
                String titulo = request.getParameter( "titulo" );

                ObjetoAprendizagem oa = null;

                /*
                 * TODO: Altera apenas o material que é do usuário.
                 * Quando o sistema for migrar para ser colaborativo,
                 * precisa verificar se o usuário está na lista de usuários
                 * donos do OA.
                 */
                oa = Uteis.getOA( ht, tipo, id );
                mesmoUsuario = Uteis.mesmoUsuario( tipo, oa, u );
                
                // altera o título do conceito caso a página seja principal
                if ( mesmoUsuario ) {

                    oa.setTitulo( titulo );
                    oa.setDataAtualizacao( new Date() );
                    ht.saveOrUpdate( oa );
                    
                    if ( oa instanceof Pagina ) {
                        Pagina p = ( Pagina ) oa;
                        if ( p.isPrincipal() ) {
                            List<ConceitoComPagina> lcp = p.getConceitosComPaginas();
                            for ( ConceitoComPagina cp : lcp ) {
                                Conceito c = cp.getConceito();
                                c.setTitulo( titulo );
                                c.setDataAtualizacao( new Date() );
                                ht.saveOrUpdate( c );
                            }
                        }
                    }

                    // altera o título da página principal de um conceito
                    if ( oa instanceof Conceito ) {
                        Conceito c = ( Conceito ) oa;
                        List<ConceitoComPagina> lcp = c.getConceitosComPaginas();
                        for ( ConceitoComPagina cp : lcp ) {
                            Pagina p = cp.getPagina();
                            if ( p.isPrincipal() ) {
                                p.setTitulo( titulo );
                                p.setDataAtualizacao( new Date() );
                                ht.saveOrUpdate( p );
                            }
                        }
                    }

                    alterarTituloMetadados( oa, titulo, ht );

                }
                
                // altera a estrutura do material (reorganização da estrutura)
            } else if ( acao.equals( "alterarEstrutura" ) ) {

                long idMaterial = Long.parseLong( request.getParameter( "idMaterial" ) );
                long idNo = Long.parseLong( request.getParameter( "idNo" ) );
                String tipoNo = request.getParameter( "tipoNo" );
                long idPaiVelho = Long.parseLong( request.getParameter( "idPaiVelho" ) );
                String tipoPaiVelho = request.getParameter( "tipoPaiVelho" );
                long idPaiNovo = Long.parseLong( request.getParameter( "idPaiNovo" ) );
                String tipoPaiNovo = request.getParameter( "tipoPaiNovo" );
                String retirarDe = request.getParameter( "retirarDe" );
                String inserirEm = request.getParameter( "inserirEm" );

                // verifica se o usuário é dono do material
                Material m = ( Material ) ht.load( Material.class, idMaterial );
                mesmoUsuario = u.equals( m.getUsuario() );

                if ( mesmoUsuario ) {

                    // obtém os OAs
                    ObjetoAprendizagem oa = Uteis.getOA( ht, tipoNo, idNo );
                    ObjetoAprendizagem antigo = Uteis.getOA( ht, tipoPaiVelho, idPaiVelho );
                    ObjetoAprendizagem alvo = Uteis.getOA( ht, tipoPaiNovo, idPaiNovo );

                    // remove as associações antigas
                    if ( retirarDe.equals( "material-grupo" ) ) {
                        ht.delete( new MaterialComGrupo(
                                new MaterialComGrupoPK( antigo.getId(), oa.getId() ) ) );
                    } else if ( retirarDe.equals( "grupo-grupo" ) ) {
                        ht.delete( new GrupoComGrupo(
                                new GrupoComGrupoPK( antigo.getId(), oa.getId() ) ) );
                    } else if ( retirarDe.equals( "grupo-pagina" ) ) {
                        ht.delete( new GrupoComPagina(
                                new GrupoComPaginaPK( antigo.getId(), oa.getId() ) ) );
                    }

                    // cria associações novas
                    if ( inserirEm.equals( "material-grupo" ) ) {
                        ht.saveOrUpdate( new MaterialComGrupo(
                                new MaterialComGrupoPK( alvo.getId(), oa.getId() ) ) );
                    } else if ( inserirEm.equals( "grupo-grupo" ) ) {
                        ht.saveOrUpdate( new GrupoComGrupo(
                                new GrupoComGrupoPK( alvo.getId(), oa.getId() ) ) );
                    } else if ( inserirEm.equals( "grupo-pagina" ) ) {
                        ht.saveOrUpdate( new GrupoComPagina(
                                new GrupoComPaginaPK( alvo.getId(), oa.getId() ) ) );
                    }

                }

                // inserir um nó em uma hierarquia
            } else if ( acao.equals( "inserirNo" ) ) {

                long idMaterial = Long.parseLong( request.getParameter( "idMaterial" ) );
                int ordem = Integer.parseInt( request.getParameter( "ordem" ) );
                long idNo = Long.parseLong( request.getParameter( "idNo" ) );
                String tipoNo = request.getParameter( "tipoNo" );
                long idPai = Long.parseLong( request.getParameter( "idPai" ) );
                String tipoPai = request.getParameter( "tipoPai" );
                String inserirEm = request.getParameter( "inserirEm" );

                // obtém os OAs
                ObjetoAprendizagem oa = Uteis.getOA( ht, tipoNo, idNo );
                ObjetoAprendizagem pai = Uteis.getOA( ht, tipoPai, idPai );

                // verifica se o usuário é dono do material
                Material m = ( Material ) ht.load( Material.class, idMaterial );
                mesmoUsuario = u.equals( m.getUsuario() );

                if ( mesmoUsuario ) {

                    // cria associações novas
                    if ( inserirEm.equals( "material-grupo" ) ) {
                        ht.saveOrUpdate( new MaterialComGrupo(
                                new MaterialComGrupoPK( pai.getId(), oa.getId() ), ordem ) );
                    } else if ( inserirEm.equals( "grupo-grupo" ) ) {
                        ht.saveOrUpdate( new GrupoComGrupo(
                                new GrupoComGrupoPK( pai.getId(), oa.getId() ), ordem ) );
                    } else if ( inserirEm.equals( "grupo-pagina" ) ) {
                        ht.saveOrUpdate( new GrupoComPagina(
                                new GrupoComPaginaPK( pai.getId(), oa.getId() ), ordem ) );
                    }

                }

                // remover as associações de um nó
            } else if ( acao.equals( "removerNo" ) ) {

                long idMaterial = Long.parseLong( request.getParameter( "idMaterial" ) );
                long idNo = Long.parseLong( request.getParameter( "idNo" ) );
                String tipoNo = request.getParameter( "tipoNo" );
                long idPai = Long.parseLong( request.getParameter( "idPai" ) );
                String tipoPai = request.getParameter( "tipoPai" );
                String retirarDe = request.getParameter( "retirarDe" );

                // verifica se o usuário é dono do material
                Material m = ( Material ) ht.load( Material.class, idMaterial );
                mesmoUsuario = u.equals( m.getUsuario() );

                if ( mesmoUsuario ) {
                    
                    // obtém os OAs
                    ObjetoAprendizagem oa = Uteis.getOA( ht, tipoNo, idNo );
                    ObjetoAprendizagem pai = Uteis.getOA( ht, tipoPai, idPai );
                    
                    /*
                     * Flag para indicar se a exclusão física deve ocorrer
                     * quando um material está associado a outro, nunca deve
                     * ocorrer a exclusão física. Caso sejam outros componentes,
                     * exclui fisicamente apenas se a ligação existir somente
                     * entre o pai e o filho.
                     */
                    boolean removerFisicamente = true;

                    // remove as associações
                    if ( retirarDe.equals( "material-grupo" ) ) {
                        ht.delete( new MaterialComGrupo(
                                new MaterialComGrupoPK( pai.getId(), oa.getId() ) ) );
                    } else if ( retirarDe.equals( "grupo-grupo" ) ) {
                        ht.delete( new GrupoComGrupo(
                                new GrupoComGrupoPK( pai.getId(), oa.getId() ) ) );
                    } else if ( retirarDe.equals( "grupo-pagina" ) ) {
                        ht.delete( new GrupoComPagina(
                                new GrupoComPaginaPK( pai.getId(), oa.getId() ) ) );
                    } else if ( retirarDe.equals( "pagina-imagem" ) ) {
                        Pagina p = ( Pagina ) pai;
                        List<Imagem> imagens = p.getImagens();

                        // esse código é um absurdo, mas da forma mais simples
                        // simplesmente não funciona... não vou perder tempo verificando o pq
                        // pois o conograma está apertado...
                        for ( int i = 0; i < imagens.size(); i++ ) {
                            if ( imagens.get(  i ).getId() == idNo ) {
                                imagens.remove( i );
                                break;
                            }
                        }
                        ht.update( p );
                    } else if ( retirarDe.equals( "pagina-video" ) ) {
                        Pagina p = ( Pagina ) pai;
                        List<Video> videos = p.getVideos();
                        for ( int i = 0; i < videos.size(); i++ ) {
                            if ( videos.get(  i ).getId() == idNo ) {
                                videos.remove( i );
                                break;
                            }
                        }
                        ht.update( p );
                    } else if ( retirarDe.equals( "pagina-som" ) ) {
                        Pagina p = ( Pagina ) pai;
                        List<Som> sons = p.getSons();
                        for ( int i = 0; i < sons.size(); i++ ) {
                            if ( sons.get(  i ).getId() == idNo ) {
                                sons.remove( i );
                                break;
                            }
                        }
                        ht.update( p );
                    }

                    if ( removerFisicamente ) {
                        DeleteUtils.deleteOA( ht, oa );
                    }

                }

            } else if ( acao.equals( "alterarOrdem" ) ) {

                long idMaterial = Long.parseLong( request.getParameter( "idMaterial" ) );

                // verifica se o usuário é dono do material
                Material m = ( Material ) ht.load( Material.class, idMaterial );
                mesmoUsuario = u.equals( m.getUsuario() );

                if ( mesmoUsuario ) {

                    String dados = request.getParameter( "dados" );
                    
                    for ( String dado : dados.split( "[$]" ) ) {

                        String[] valores = dado.split( ":" );
                        String mudarEm = valores[0];
                        String[] ids = valores[1].split( "-" );
                        long idPai = Long.parseLong( ids[0] );
                        long idFilho = Long.parseLong( ids[1] );
                        int ordem = Integer.parseInt( valores[2] );

                        // atualiza as ordens
                        if ( mudarEm.equals( "material-grupo" ) ) {
                            MaterialComGrupoPK pk = new MaterialComGrupoPK( idPai, idFilho );
                            MaterialComGrupo o = ( MaterialComGrupo ) ht.load( MaterialComGrupo.class, pk );
                            o.setOrdem( ordem );
                            ht.saveOrUpdate( o );
                        } else if ( mudarEm.equals( "grupo-grupo" ) ) {
                            GrupoComGrupoPK pk = new GrupoComGrupoPK( idPai, idFilho );
                            GrupoComGrupo o = ( GrupoComGrupo ) ht.load( GrupoComGrupo.class, pk );
                            o.setOrdem( ordem );
                            ht.saveOrUpdate( o );
                        } else if ( mudarEm.equals( "grupo-pagina" ) ) {
                            GrupoComPaginaPK pk = new GrupoComPaginaPK( idPai, idFilho );
                            GrupoComPagina o = ( GrupoComPagina ) ht.load( GrupoComPagina.class, pk );
                            o.setOrdem( ordem );
                            ht.saveOrUpdate( o );
                        }

                    }

                }

            }

            t.commit();

            if ( mesmoUsuario )
                out.print( "true" );
            else
                out.print( "false" );

        } catch ( Exception exc ) {

            out.print( Uteis.createErrorMessage( exc, new Boolean( configs.get( "debug" ) ) ) );

        } finally {
            out.print( "}" );
            out.close();
        }

    }

    /*
     * Método para altera o título dos metadados de um OA.
     */
    private void alterarTituloMetadados(
            ObjetoAprendizagem oa,
            String titulo,
            HibernateTemplate ht ) throws Exception {

        LangString langTitulo = null;

        if ( oa instanceof Material ) {
            langTitulo = ( ( Material ) oa ).getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
        } else if ( oa instanceof Pagina ) {
            langTitulo = ( ( Pagina ) oa ).getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
        } else if ( oa instanceof Conceito ) {
            langTitulo = ( ( Conceito ) oa ).getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
        } else if ( oa instanceof Grupo ) {
            langTitulo = ( ( Grupo ) oa ).getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
        } else if ( oa instanceof Imagem ) {
            langTitulo = ( ( Imagem ) oa ).getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
        } else if ( oa instanceof Video ) {
            langTitulo = ( ( Video ) oa ).getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
        } else if ( oa instanceof Som ) {
            langTitulo = ( ( Som ) oa ).getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
        }

        langTitulo.setValue( titulo );
        ht.saveOrUpdate( langTitulo );

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
