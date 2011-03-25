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
 * Servlet para alteração de algumas propridades de um OA que seja
 * um material, conceito, grupo ou página.
 *
 * @author David Buzatto
 */
public class AlteraPropriedadesOAServlet extends HttpServlet {
   
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
            String acao = request.getParameter( "acao" );

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();
            
            long id = Long.parseLong( request.getParameter( "id" ) );
            String titulo = request.getParameter( "titulo" );
            boolean compartilhado = request.getParameter( "compartilhado" ) == null ? false : true;
            String organizacaoPagina = request.getParameter( "organizacaoPagina" );
            String organizacaoBarra = request.getParameter( "organizacaoBarra" );
            
            if ( acao.equals( "alterar-material" ) ) {
                
                Material m = ( Material ) ht.load( Material.class, id );
                m.setTitulo( titulo );
                m.setCompartilhado( compartilhado );
                m.setLayout( Uteis.getMaterialLayout( organizacaoPagina, organizacaoBarra ) );
                m.setDataAtualizacao( new Date() );
                ht.saveOrUpdate( m );

                LangString langTitulo = m.getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
                langTitulo.setValue( titulo );
                ht.saveOrUpdate( langTitulo );
            
            } else if ( acao.equals( "alterar-conceito" ) ) {
                
                Conceito c = ( Conceito ) ht.load( Conceito.class, id );
                c.setTitulo( titulo );

                // compartilhamento não será mais alterado.
                // c.setCompartilhado( compartilhado );

                c.setDataAtualizacao( new Date() );
                ht.saveOrUpdate( c );

                List<ConceitoComPagina> lcp = c.getConceitosComPaginas();
                for ( ConceitoComPagina cp : lcp ) {
                    Pagina p = cp.getPagina();
                    if ( p.isPrincipal() ) {
                        p.setTitulo( titulo );
                        ht.saveOrUpdate( p );
                    }
                }

                LangString langTitulo = c.getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
                langTitulo.setValue( titulo );
                ht.saveOrUpdate( langTitulo );
                
            } else if ( acao.equals( "alterar-grupo" ) ) {
                
                Grupo g = ( Grupo ) ht.load( Grupo.class, id );
                g.setTitulo( titulo );

                // compartilhamento não será mais alterado.
                // g.setCompartilhado( compartilhado );

                g.setDataAtualizacao( new Date() );
                ht.saveOrUpdate( g );

                LangString langTitulo = g.getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
                langTitulo.setValue( titulo );
                ht.saveOrUpdate( langTitulo );
                
            } else if ( acao.equals( "alterar-pagina" ) ) {

                Pagina p = ( Pagina ) ht.load( Pagina.class, id );
                p.setTitulo( titulo );

                // compartilhamento não será mais alterado.
                // p.setCompartilhado( compartilhado );

                p.setDataAtualizacao( new Date() );
                ht.saveOrUpdate( p );

                if ( p.isPrincipal() ) {
                    List<ConceitoComPagina> lcp = p.getConceitosComPaginas();
                    for ( ConceitoComPagina cp : lcp ) {
                        Conceito c = cp.getConceito();
                        c.setTitulo( titulo );
                        ht.saveOrUpdate( c );
                    }
                }

                LangString langTitulo = p.getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
                langTitulo.setValue( titulo );
                ht.saveOrUpdate( langTitulo );

            } else if ( acao.equals( "alterar-imagem" ) ) {

                Imagem i = ( Imagem ) ht.load( Imagem.class, id );
                i.setTitulo( titulo );

                // compartilhamento não será mais alterado.
                // i.setCompartilhado( compartilhado );

                i.setDataAtualizacao( new Date() );
                ht.saveOrUpdate( i );

                LangString langTitulo = i.getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
                langTitulo.setValue( titulo );
                ht.saveOrUpdate( langTitulo );

            } else if ( acao.equals( "alterar-video" ) ) {

                Video v = ( Video ) ht.load( Video.class, id );
                v.setTitulo( titulo );

                // compartilhamento não será mais alterado.
                // v.setCompartilhado( compartilhado );

                v.setDataAtualizacao( new Date() );
                ht.saveOrUpdate( v );

                LangString langTitulo = v.getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
                langTitulo.setValue( titulo );
                ht.saveOrUpdate( langTitulo );

            } else if ( acao.equals( "alterar-som" ) ) {

                Som s = ( Som ) ht.load( Som.class, id );
                s.setTitulo( titulo );

                // compartilhamento não será mais alterado.
                // s.setCompartilhado( compartilhado );

                s.setDataAtualizacao( new Date() );
                ht.saveOrUpdate( s );

                LangString langTitulo = s.getMetadata().getLom().getGeneral().getTitle().getStrings().get( 0 );
                langTitulo.setValue( titulo );
                ht.saveOrUpdate( langTitulo );

            }

            t.commit();
            out.print( "true" );

        } catch ( Exception exc ) {

            out.print( Uteis.createErrorMessage( exc, new Boolean( configs.get( "debug" ) ) ) );

        } finally {
            out.print( "}" );
            out.close();
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
