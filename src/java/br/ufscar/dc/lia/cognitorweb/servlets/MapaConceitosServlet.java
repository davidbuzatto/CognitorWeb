/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.*;
import br.ufscar.dc.lia.cognitorweb.excecoes.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import br.ufscar.dc.lia.cognitorweb.mapaconceitos.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para criação e atualização dos mapas de conceitos.
 *
 * @author David Buzatto
 */
public class MapaConceitosServlet extends HttpServlet {
   
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

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            String acao = request.getParameter( "acao" );
            String dadosMapa = request.getParameter( "dadosMapa" );
            String relacoesHExcluir = request.getParameter( "relacoesHExcluir" );
            String relacoesExcluir = request.getParameter( "relacoesExcluir" );
            MapaConceitos mapa = null;
            Material m = null;
            
            if ( dadosMapa != null ) {
                Serializer s = new Persister();
                mapa = s.read( MapaConceitos.class, dadosMapa );
                m = ( Material ) ht.load( Material.class, mapa.getIdMaterial() );
            } else {
                String idMaterial = request.getParameter( "idMaterial" );
                m = ( Material ) ht.load( Material.class, Long.parseLong( idMaterial ) );
            }
            
            boolean mesmoUsuario = u.equals( m.getUsuario() );
            
            /*
             * Mapa para conter todos os conceitos gerados.
             * Esse mapa será usado na atribuição das relações.
             */
            HashMap< String, ConceitoEOrdem > mapaDados = new HashMap< String, ConceitoEOrdem >();

            if ( mesmoUsuario ) {

                if ( acao.equals( "novo" ) ) {

                    for ( ConceitoMapa c : mapa.getConceitos().getConceitos() ) {

                        Metadata meta = MetadataUtils.createMetadataInstance( 
                                ht, c.getTitulo(), request.getLocale().getLanguage(), "LOMv1.0" );
                        Conceito con = new Conceito();
                        con.setCompartilhado( false );
                        con.setDataCriacao( new Date() );
                        con.setDataAtualizacao( new Date() );
                        con.setTitulo( c.getTitulo() );
                        con.setUsuario( null );
                        con.setMetadata( meta );
                        con.setUsuario( u );

                        Pagina pag = new Pagina();
                        pag.setCompartilhado( false );
                        pag.setConteudoHtml( "" );
                        pag.setDataCriacao( new Date() );
                        pag.setDataAtualizacao( new Date() );
                        pag.setPrincipal( true );
                        pag.setTitulo( c.getTitulo() );
                        pag.setMetadata( meta );
                        pag.setUsuario( u );

                        ht.saveOrUpdate( con );
                        ht.saveOrUpdate( pag );

                        ConceitoComPagina cp = new ConceitoComPagina( con.getId(), pag.getId() );
                        ht.saveOrUpdate( cp );

                        if ( c.isRaiz() ) {
                            MaterialComConceito mc = new MaterialComConceito( mapa.getIdMaterial(), con.getId() );
                            ht.saveOrUpdate( mc );
                        }

                        // insere no mapa para poder fazer os relacionamentos
                        ConceitoEOrdem co = new ConceitoEOrdem();
                        co.conceito = con;
                        co.ordem = c.getOrdem();
                        mapaDados.put( c.getTitulo(), co );

                    }

                    for ( RelacaoMapa r : mapa.getRelacoes().getRelacoes() ) {

                        // se é da hierarquia, usa o ConceitoComConceito
                        if ( r.isHierarquia() ) {

                            ConceitoComConceito cc = new ConceitoComConceito(
                                    mapaDados.get( r.getTituloC1() ).conceito.getId(),
                                    mapaDados.get( r.getTituloC2() ).conceito.getId() );
                            cc.setOrdem( mapaDados.get( r.getTituloC2() ).ordem );
                            cc.setRelacaoMinsky( r.getRelacaoMinsky() );
                            cc.setRelacaoUsuario( r.getRelacaoUsuario() );

                            ht.save( cc );

                            // caso não seja, usa RelacaoMapaConceito
                        } else {

                            RelacaoMapaConceito rm = new RelacaoMapaConceito();
                            rm.setConceitoOrigem( mapaDados.get( r.getTituloC1() ).conceito );
                            rm.setConceitoDestino( mapaDados.get( r.getTituloC2() ).conceito );
                            rm.setRelacaoMinsky( r.getRelacaoMinsky() );
                            rm.setRelacaoUsuario( r.getRelacaoUsuario() );

                            ht.save( rm );

                        }

                    }

                } else if ( acao.equals( "alterar" ) ) {

                    // desamarrações necessárias
                    String[] relacoesH = relacoesHExcluir.split( "[$]" );
                    if ( relacoesH != null ) {
                        for ( String relacao : relacoesH ) {
                            if ( !relacao.equals( "" ) ) {
                                String[] ids = relacao.split( "-" );

                                long id = Long.parseLong( ids[0] );
                                long idPai = Long.parseLong( ids[1] );

                                ConceitoComConceito cc = new ConceitoComConceito(
                                        idPai, id );
                                ht.delete( cc );
                            }
                        }
                    }

                    String[] relacoes = relacoesExcluir.split( "[$]" );
                    if ( relacoes != null ) {
                        for ( String relacao : relacoes ) {
                            if ( !relacao.equals( "" ) ) {
                                String[] ids = relacao.split( "-" );

                                long id = Long.parseLong( ids[0] );
                                long idPai = Long.parseLong( ids[1] );

                                List<RelacaoMapaConceito> lista = ( List<RelacaoMapaConceito> )
                                        ht.find( "from RelacaoMapaConceito " +
                                        "where " +
                                        "conceitoOrigem = " + id + " and " +
                                        "conceitoDestino = " + idPai );

                                for ( RelacaoMapaConceito rr : lista ) {
                                    ht.delete( rr );
                                }
                            }
                        }
                    }

                    // exclusão é tratada fora

                    // cria o que é novo
                    for ( ConceitoMapa c : mapa.getConceitos().getConceitos() ) {

                        Conceito con = null;

                        if ( c.isNovo() ) {

                            Metadata meta = MetadataUtils.createMetadataInstance(
                                    ht, c.getTitulo(), request.getLocale().getLanguage(), "LOMv1.0" );
                            con = new Conceito();
                            con.setCompartilhado( false );
                            con.setDataCriacao( new Date() );
                            con.setDataAtualizacao( new Date() );
                            con.setTitulo( c.getTitulo() );
                            con.setUsuario( null );
                            con.setMetadata( meta );
                            con.setUsuario( u );

                            Pagina pag = new Pagina();
                            pag.setCompartilhado( false );
                            pag.setConteudoHtml( "" );
                            pag.setDataCriacao( new Date() );
                            pag.setDataAtualizacao( new Date() );
                            pag.setPrincipal( true );
                            pag.setTitulo( c.getTitulo() );
                            pag.setMetadata( meta );
                            pag.setUsuario( u );

                            ht.saveOrUpdate( con );
                            ht.saveOrUpdate( pag );

                            ConceitoComPagina cp = new ConceitoComPagina( con.getId(), pag.getId() );
                            ht.saveOrUpdate( cp );

                            if ( c.isRaiz() ) {
                                MaterialComConceito mc = new MaterialComConceito( mapa.getIdMaterial(), con.getId() );
                                ht.saveOrUpdate( mc );
                            }

                        } else {

                            con = ( Conceito ) ht.load( Conceito.class, c.getId() );

                            if ( c.isRaiz() ) {
                                MaterialComConceito mc = new MaterialComConceito( mapa.getIdMaterial(), con.getId() );
                                ht.saveOrUpdate( mc );
                            }

                        }

                        // insere no mapa para poder fazer os relacionamentos
                        ConceitoEOrdem co = new ConceitoEOrdem();
                        co.conceito = con;
                        co.ordem = c.getOrdem();
                        mapaDados.put( c.getTitulo(), co );

                    }

                    // associa
                    for ( RelacaoMapa r : mapa.getRelacoes().getRelacoes() ) {

                        // se é da hierarquia, usa o ConceitoComConceito
                        if ( r.isHierarquia() ) {

                            ConceitoComConceito cc = new ConceitoComConceito(
                                    mapaDados.get( r.getTituloC1() ).conceito.getId(),
                                    mapaDados.get( r.getTituloC2() ).conceito.getId() );
                            cc.setOrdem( mapaDados.get( r.getTituloC2() ).ordem );
                            cc.setRelacaoMinsky( r.getRelacaoMinsky() );
                            cc.setRelacaoUsuario( r.getRelacaoUsuario() );

                            ht.saveOrUpdate( cc );

                            // caso não seja, usa RelacaoMapaConceito
                        } else {
                            
                            boolean achou = false;

                            String relacaoMinsky = r.getRelacaoMinsky();
                            String relacaoUsuario = r.getRelacaoUsuario();

                            if ( relacaoMinsky == null || relacaoMinsky.equals( "null" ) )
                                relacaoMinsky = null;

                            if ( relacaoUsuario == null || relacaoUsuario.equals( "null" ) )
                                relacaoUsuario = null;

                            List<RelacaoMapaConceito> lista = ( List<RelacaoMapaConceito> )
                                    ht.find( "from RelacaoMapaConceito " +
                                    "where " +
                                    "conceitoOrigem = " + r.getIdC1() + " and " +
                                    "conceitoDestino = " + r.getIdC2() );

                            for ( RelacaoMapaConceito rr : lista ) {
                                achou = true;
                                rr.setRelacaoMinsky( relacaoMinsky );
                                rr.setRelacaoUsuario( relacaoUsuario );
                                ht.update( rr );
                            }

                            if ( !achou ) {
                                RelacaoMapaConceito rm = new RelacaoMapaConceito();
                                rm.setConceitoOrigem( mapaDados.get( r.getTituloC1() ).conceito );
                                rm.setConceitoDestino( mapaDados.get( r.getTituloC2() ).conceito );
                                rm.setRelacaoMinsky( relacaoMinsky );
                                rm.setRelacaoUsuario( relacaoUsuario );
                                ht.saveOrUpdate( rm );
                            }

                        }

                    }

                } else if ( acao.equals( "excluirConceito" ) ) {

                    long idConceito = Long.parseLong( request.getParameter( "idConceito" ) );
                    DeleteUtils.deleteConceito( ht,
                            ( Conceito ) ht.load( Conceito.class, idConceito ), true );

                }

                t.commit();
                
                out.print( "true" );

            } else {

                throw new InvalidUserException( rs.getString( "erro.usuarioInvalido" ) );

            }

        } catch ( Exception exc ) {
            exc.printStackTrace();
            out.print( Uteis.createErrorMessage( exc, new Boolean( configs.get( "debug" ) ) ) );

        } finally {
            out.print( "}" );
            out.close();
        }

    }

    /* Classe interna para conter o conceito e a ordem que este está inserido
     * no conceito pai.
     */
    private class ConceitoEOrdem {

        private Conceito conceito;
        private int ordem;

        @Override
        public String toString() {
            return conceito.getTitulo();
        }

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
