/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.*;
import br.ufscar.dc.lia.cognitorweb.excecoes.InvalidUserException;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Cria um OA.
 * Apenas a estrutura.
 *
 * @author David Buzatto
 */
public class CriarOAServlet extends HttpServlet {
   
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

            long idMaterial = Long.parseLong( request.getParameter( "material" ) );

            Material m = ( Material ) ht.load( Material.class, idMaterial );
            boolean mesmoUsuario = u.equals( m.getUsuario() );

            Date data = new Date();

            if ( mesmoUsuario ) {

                if ( acao.equals( "criar-pagina" ) || acao.equals( "criar-grupo" ) ) {

                    String titulo = request.getParameter( "titulo" );

                    // compartilhamento não será mais alterado.
                    // boolean compartilhado = request.getParameter( "compartilhado" ) == null ? false : true;

                    String icone = request.getParameter( "icone" );

                    Pagina p = null;
                    Grupo g = null;

                    // toda a hierarquia dos metadados é gerada e salva dentro desse método
                    Metadata meta = MetadataUtils.createMetadataInstance( 
                            ht, titulo, request.getLocale().getLanguage(), "LOMv1.0" );

                    if ( acao.equals( "criar-pagina" ) ) {

                        p = new Pagina();
                        p.setTitulo( titulo );
                        p.setDataCriacao( data );
                        p.setDataAtualizacao( data );

                        // compartilhamento não será mais alterado.
                        // p.setCompartilhado( compartilhado );

                        p.setConteudoHtml( "" );
                        p.setUsuario( u );
                        p.setMetadata( meta );

                        m.setDataAtualizacao( data );

                        ht.saveOrUpdate( p );
                        ht.saveOrUpdate( m );

                    } else {

                        g = new Grupo();
                        g.setTitulo( titulo );
                        g.setDataCriacao( data );
                        g.setDataAtualizacao( data );

                        // compartilhamento não será mais alterado.
                        // g.setCompartilhado( compartilhado );

                        g.setUsuario( u );
                        g.setMetadata( meta );

                        m.setDataAtualizacao( data );

                        ht.saveOrUpdate( g );
                        ht.saveOrUpdate( m );

                    }

                    t.commit();

                    out.print( "true," );

                    if ( p != null ) {

                        out.print( "idInterno: " + p.getId() + ", " );
                        out.print( "ordem: 0, " );
                        out.print( "compartilhado: " + p.isCompartilhado() + ", " );
                        out.print( "principal: " + p.isPrincipal() + ", " );
                        out.print( "tipo: 'pagina', " );
                        out.print( "text: '" + p.getTitulo() + "', " );
                        out.print( "icon: '" + icone + "', " );
                        out.print( "leaf: true" );

                    } else {

                        out.print( "idInterno: " + g.getId() + ", " );
                        out.print( "ordem: 0, " );
                        out.print( "compartilhado: " + g.isCompartilhado() + ", " );
                        out.print( "tipo: 'grupo', " );
                        out.print( "text: '" + g.getTitulo() + "', " );
                        out.print( "icon: '" + icone + "', " );
                        out.print( "leaf: false" );

                    }

                } else if ( acao.equals( "criar-imagem" ) ) {

                    long idPagina = Long.parseLong( request.getParameter( "idPagina" ) );
                    String nomeArquivo = request.getParameter( "nomeArquivo" );
                    String titulo = request.getParameter( "titulo" );
                    boolean externo = Boolean.valueOf( request.getParameter( "externo" ) );

                    Pagina p = ( Pagina ) ht.load( Pagina.class, idPagina );
                    Metadata meta = MetadataUtils.createMetadataInstance( 
                            ht, titulo, request.getLocale().getLanguage(), "LOMv1.0" );

                    Imagem i = new Imagem();
                    i.setTitulo( titulo );
                    i.setDataCriacao( data );
                    i.setDataAtualizacao( data );

                    // compartilhamento não será mais alterado.
                    // i.setCompartilhado( compartilhado );

                    i.setNomeArquivo( nomeArquivo );
                    i.setExterno( externo );

                    i.setUsuario( u );
                    i.setMetadata( meta );

                    m.setDataAtualizacao( data );

                    ht.saveOrUpdate( i );
                    ht.saveOrUpdate( m );

                    // adiciona a imagem e atualiza
                    p.getImagens().add( i );
                    ht.update( p );

                    t.commit();

                    out.print( "true," );
                    out.print( "idImagem: " + i.getId() );

                } else if ( acao.equals( "criar-video" ) ) {

                    long idPagina = Long.parseLong( request.getParameter( "idPagina" ) );
                    String nomeArquivo = request.getParameter( "nomeArquivo" );
                    String titulo = request.getParameter( "titulo" );
                    boolean externo = Boolean.valueOf( request.getParameter( "externo" ) );

                    Pagina p = ( Pagina ) ht.load( Pagina.class, idPagina );
                    Metadata meta = MetadataUtils.createMetadataInstance( ht, titulo,
                            request.getLocale().getLanguage(), "LOMv1.0" );

                    Video v = new Video();
                    v.setTitulo( titulo );
                    v.setDataCriacao( data );
                    v.setDataAtualizacao( data );

                    // compartilhamento não será mais alterado.
                    // v.setCompartilhado( compartilhado );

                    v.setNomeArquivo( nomeArquivo );
                    v.setExterno( externo );

                    v.setUsuario( u );
                    v.setMetadata( meta );

                    m.setDataAtualizacao( data );

                    ht.saveOrUpdate( v );
                    ht.saveOrUpdate( m );

                    // adiciona a imagem e atualiza
                    p.getVideos().add( v );
                    ht.update( p );

                    t.commit();

                    out.print( "true," );
                    out.print( "idVideo: " + v.getId() );

                } else if ( acao.equals( "criar-som" ) ) {

                    long idPagina = Long.parseLong( request.getParameter( "idPagina" ) );
                    String nomeArquivo = request.getParameter( "nomeArquivo" );
                    String titulo = request.getParameter( "titulo" );
                    boolean externo = Boolean.valueOf( request.getParameter( "externo" ) );

                    Pagina p = ( Pagina ) ht.load( Pagina.class, idPagina );
                    Metadata meta = MetadataUtils.createMetadataInstance( 
                            ht, titulo, request.getLocale().getLanguage(), "LOMv1.0" );

                    Som s = new Som();
                    s.setTitulo( titulo );
                    s.setDataCriacao( data );
                    s.setDataAtualizacao( data );

                    // compartilhamento não será mais alterado.
                    // s.setCompartilhado( compartilhado );

                    s.setNomeArquivo( nomeArquivo );
                    s.setExterno( externo );

                    s.setUsuario( u );
                    s.setMetadata( meta );

                    m.setDataAtualizacao( data );

                    ht.saveOrUpdate( s );
                    ht.saveOrUpdate( m );

                    // adiciona a imagem e atualiza
                    p.getSons().add( s );
                    ht.update( p );

                    t.commit();

                    out.print( "true," );
                    out.print( "idSom: " + s.getId() );

                }

            } else {

                throw new InvalidUserException( rs.getString( "erro.usuarioInvalido" ) );

            }
            

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
