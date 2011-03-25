/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.testes;

import br.ufscar.dc.lia.cognitorweb.enumeracoes.TipoUsuario;
import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.comum.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.general.*;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.Escolaridade;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.LayoutMaterial;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.Sexo;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Testes de criação e consultas de OAs.
 * O código está uma bagunça, apenas usar como teste.
 *
 * @author David Buzatto
 */
public class TestesCriacaoOAsServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methodht.
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

        out.print( "{ sucesso: " );

        try {

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            String acao = request.getParameter( "acao" );

            if ( acao.equals( "cadastrar" ) ) {

                String tituloMaterial = request.getParameter( "tituloMaterial" );
                String conteudo = request.getParameter( "conteudo" );
                String tituloMeta = request.getParameter( "tituloMeta" );
                String descricaoMeta = request.getParameter( "descricaoMeta" );
                String key1Meta = request.getParameter( "key1Meta" );
                String key2Meta = request.getParameter( "key2Meta" );
                String key3Meta = request.getParameter( "key3Meta" );
                String key4Meta = request.getParameter( "key4Meta" );
                String coberturaMeta = request.getParameter( "coberturaMeta" );

                Pais p = new Pais();
                p.setNome( "Brasil" );
                ht.saveOrUpdate( p );


                Estado e = new Estado();
                e.setNome( "São Paulo" );
                e.setSigla( "SP" );
                e.setPais( p );
                ht.saveOrUpdate( e );


                Cidade c = new Cidade();
                c.setNome( "São Carlos" );
                c.setEstado( e );
                ht.saveOrUpdate( c );


                Usuario u = new Usuario();
                u.setEmail( "david@david.com" );
                u.setSenha( "12345678" );
                u.setPrimeiroNome( "David" );
                u.setNomeMeio( "teste" );
                u.setUltimoNome( "Buzatto" );
                u.setDataNascimento( new Date() );
                u.setSexo( Sexo.M );
                u.setRua( "Rua Prof José" );
                u.setNumero( "123" );
                u.setComplemento( "testee" );
                u.setCidade( c );
                u.setEscolaridade( Escolaridade.EFC );
                u.setOcupacao( "bla bla bla" );
                u.setTipo( TipoUsuario.A );
                ht.saveOrUpdate( u );


                Lom lom1 = new Lom();
                lom1.setXmlns( "aaa" );
                ht.saveOrUpdate( lom1 );


                Metadata mt1 = new Metadata();
                mt1.setSchema( "schema aaa" );
                mt1.setSchemaVersion( "1" );
                mt1.setLom( lom1 );
                ht.saveOrUpdate( mt1 );


                Lom lom2 = new Lom();
                lom2.setXmlns( "bbb" );
                ht.saveOrUpdate( lom2 );


                Metadata mt2 = new Metadata();
                mt2.setSchema( "schema bbb" );
                mt2.setSchemaVersion( "2" );
                mt2.setLom( lom2 );
                ht.saveOrUpdate( mt2 );


                General g = new General();
                ht.saveOrUpdate( g );

                GeneralTitle gt = new GeneralTitle();
                LangString lsGT = new LangString();
                lsGT.setLanguage( "pt" );
                lsGT.setValue( tituloMeta );
                ht.saveOrUpdate( lsGT );
                List< LangString > lsGTs = new ArrayList< LangString >();
                lsGTs.add( lsGT );
                gt.setStrings( lsGTs );
                ht.saveOrUpdate( gt );
                g.setTitle( gt );
                ht.saveOrUpdate( g );


                GeneralDescription gd = new GeneralDescription();
                LangString lsGD = new LangString();
                lsGD.setLanguage( "pt" );
                lsGD.setValue( descricaoMeta );
                ht.saveOrUpdate( lsGD );
                List< LangString > lsGDs = new ArrayList< LangString >();
                lsGDs.add( lsGD );
                gd.setStrings( lsGDs );
                gd.setGeneral( g );
                ht.saveOrUpdate( gd );
                ht.saveOrUpdate( g );


                GeneralKeyword gk1 = new GeneralKeyword();
                GeneralKeyword gk2 = new GeneralKeyword();
                GeneralKeyword gk3 = new GeneralKeyword();
                GeneralKeyword gk4 = new GeneralKeyword();
                LangString lsGK1 = new LangString();
                LangString lsGK2 = new LangString();
                LangString lsGK3 = new LangString();
                LangString lsGK4 = new LangString();
                lsGK1.setLanguage( "pt" );
                lsGK1.setValue( key1Meta );
                ht.saveOrUpdate( lsGK1 );
                lsGK2.setLanguage( "pt" );
                lsGK2.setValue( key2Meta );
                ht.saveOrUpdate( lsGK2 );
                lsGK3.setLanguage( "pt" );
                lsGK3.setValue( key3Meta );
                ht.saveOrUpdate( lsGK3 );
                lsGK4.setLanguage( "pt" );
                lsGK4.setValue( key4Meta );
                ht.saveOrUpdate( lsGK4 );
                List< LangString > lsGKs1 = new ArrayList< LangString >();
                List< LangString > lsGKs2 = new ArrayList< LangString >();
                List< LangString > lsGKs3 = new ArrayList< LangString >();
                List< LangString > lsGKs4 = new ArrayList< LangString >();
                lsGKs1.add( lsGK1 );
                lsGKs2.add( lsGK2 );
                lsGKs3.add( lsGK3 );
                lsGKs4.add( lsGK4 );
                gk1.setStrings( lsGKs1 );
                gk1.setGeneral( g );
                ht.saveOrUpdate( gk1 );
                gk2.setStrings( lsGKs2 );
                gk2.setGeneral( g );
                ht.saveOrUpdate( gk2 );
                gk3.setStrings( lsGKs3 );
                gk3.setGeneral( g );
                ht.saveOrUpdate( gk3 );
                gk4.setStrings( lsGKs4 );
                gk4.setGeneral( g );
                ht.saveOrUpdate( gk4 );
                ht.saveOrUpdate( g );


                GeneralCoverage gc = new GeneralCoverage();
                LangString lsGC = new LangString();
                lsGC.setLanguage( "pt" );
                lsGC.setValue( coberturaMeta );
                ht.saveOrUpdate( lsGC );
                List< LangString > lsGCs = new ArrayList< LangString >();
                lsGCs.add( lsGC );
                gc.setStrings( lsGCs );
                gc.setGeneral( g );
                ht.saveOrUpdate( gc );
                ht.saveOrUpdate( g );


                Lom lom3 = new Lom();
                lom3.setXmlns( "ccc" );
                lom3.setGeneral( g );
                ht.saveOrUpdate( lom3 );


                Metadata mt3 = new Metadata();
                mt3.setSchema( "schema ccc" );
                mt3.setSchemaVersion( "3" );
                mt3.setLom( lom3 );
                ht.saveOrUpdate( mt3 );


                Pagina pag = new Pagina();
                pag.setTitulo( "título da página" );
                pag.setUsuario( u );
                pag.setCompartilhado( false );
                pag.setDataCriacao( new Date() );
                pag.setDataAtualizacao( new Date() );
                pag.setConteudoHtml( conteudo );
                pag.setMetadata( mt1 );
                ht.saveOrUpdate( pag );


                Conceito co = new Conceito();
                co.setTitulo( "títutlo do conceito" );
                co.setUsuario( u );
                co.setCompartilhado( false );
                co.setDataCriacao( new Date() );
                co.setDataAtualizacao( new Date() );
                List< Pagina > paginas = new ArrayList< Pagina >();
                paginas.add( pag );
                co.setMetadata( mt2 );
                ht.saveOrUpdate( co );
                ConceitoComPaginaPK cpk = new ConceitoComPaginaPK( co.getId(), pag.getId() );
                ConceitoComPagina cp = new ConceitoComPagina( cpk );
                cp.setOrdem( 0 );
                ht.saveOrUpdate( cp );
                


                Material m = new Material();
                m.setTitulo( tituloMaterial );
                m.setUsuario( u );
                m.setCompartilhado( false );
                m.setDataCriacao( new Date() );
                m.setDataAtualizacao( new Date() );
                m.setLayout( LayoutMaterial.PCLCBN );
                List< Conceito > conceitos = new ArrayList< Conceito >();
                conceitos.add( co );
                m.setMetadata( mt3 );
                ht.saveOrUpdate( m );
                MaterialComConceitoPK mpk = new MaterialComConceitoPK( m.getId(), co.getId() );
                MaterialComConceito mc = new MaterialComConceito( mpk );
                mc.setOrdem( 1 );
                ht.saveOrUpdate( mc );


                t.commit();
                out.print( " true }");

            } else if ( acao.equals( "listar" ) ) {
                
                List< Material > materiais = ht.find( "from Material m order by m.titulo" );

                StringBuilder sb = new StringBuilder();
                sb.append( " true, ");
                sb.append( "materiais: [");
                
                for ( Material m : materiais ) {

                    sb.append( "{ id: " + m.getId() + ", tituloMaterial: '" + m.getTitulo() + "'}," );

                }

                String valor = sb.toString();
                out.print( valor.substring( 0, valor.length()-1 ) );
                out.print( "]}");

            } else if ( acao.equals( "obterConteudo" ) ) {

                Material m = ( Material ) ht.load( Material.class,
                        Long.parseLong( request.getParameter( "id" ) ) );
                
                out.print( " true, ");
                String conteudo = m.getMateriaisComConceitos().get( 0 ).getConceito().getConceitosComPaginas().get( 0 ).getPagina().getConteudoHtml().replace( "\n", "<br/>" ).replace( "\"", "\\\"" );
                out.print( "conteudoMaterial: '" + conteudo + "'}" );

            } else if ( acao.equals( "consultar" ) ) {

                String tituloMeta = request.getParameter( "tituloMeta" );
                String descricaoMeta = request.getParameter( "descricaoMeta" );
                String keyMeta = request.getParameter( "keyMeta" );
                String coberturaMeta = request.getParameter( "coberturaMeta" );
                String query = request.getParameter( "query" );

                StringBuilder con = new StringBuilder();
                if ( !query.equals( "" ) )
                    con.append( query );
                else {
                    con.append( "from Material m where true = true " );
                    if ( !tituloMeta.equals( "" ) )
                        con.append( " and (select ls from LangString ls where ls.value = '" + tituloMeta + "') = some elements(m.metadata.lom.general.title.strings)" );
                    if ( !descricaoMeta.equals( "" ) )
                        con.append( " and (select ls from LangString ls where ls.value = '" + descricaoMeta + "') in elements(m.metadata.lom.general.descriptions.strings)" );
                    if ( !keyMeta.equals( "" ) ) {
                        if ( keyMeta.contains( "," ) ) {
                            String[] palavras = keyMeta.split( "," );
                            for ( String s : palavras )
                                con.append( " and (select ls from LangString ls where ls.value = '" + s.trim() + "') in elements(m.metadata.lom.general.keywords.strings)" );
                        } else {
                            con.append( " and (select ls from LangString ls where ls.value = '" + keyMeta + "') in elements(m.metadata.lom.general.keywords.strings)" );
                        }

                    }
                    if ( !coberturaMeta.equals( "" ) )
                        con.append( " and (select ls from LangString ls where ls.value = '" + coberturaMeta + "') in elements(m.metadata.lom.general.coverages.strings)" );
                }
                //con.append( "order by m.titulo" );
                List< Material > materiais = ht.find( con.toString() );

                StringBuilder sb = new StringBuilder();
                sb.append( " true ");
                

                if ( materiais.size() > 0 ) {
                    sb.append( ", materiais: [");
                }
                for ( Material m : materiais ) {

                    sb.append( "{ id: " + m.getId() + ", tituloMaterial: '" + m.getTitulo() + "'}," );

                }

                String valor = sb.toString();
                out.print( valor.substring( 0, valor.length()-1 ) );

                if ( materiais.size() > 0 ) {
                    out.print( "]");
                }
                out.print( "}");

            }
            
        } catch ( Exception exc ) {
            
            exc.printStackTrace();
            out.print( " false," +
                    "causa: '" + exc.getCause() + "'" +
                    "}");

        } finally {
            out.close();
        }

    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methodht. Click on the + sign on the left to edit the code.">
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
