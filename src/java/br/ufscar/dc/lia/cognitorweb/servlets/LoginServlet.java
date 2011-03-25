/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para execução do processo de autenticação.
 *
 * @author David Buzatto
 */
public class LoginServlet extends HttpServlet {
   
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
        out.print( "{ success: " );

        Map< String, String > configs = ( HashMap< String, String > )
                getServletContext().getAttribute( "configs" );

        try {

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            String tipo = request.getParameter( "tipo" );
            
            // verifica se é o login do testador
            if ( tipo != null && tipo.equals( "testador" ) ) {

                List< Usuario > lu = ( List< Usuario > ) ht.find(
                        "from Usuario " +
                        "where " +
                        "    email = ?", configs.get( "emailTestador" ) );
                t.commit();
                
                if ( lu.size() > 0 ) {

                    Usuario u = lu.get( 0 );

                    // se o usuário existe:
                    // abre a sessão
                    HttpSession s = request.getSession( true );

                    // coloca o usuário na sessão
                    s.setAttribute( "usuario", u );

                    // a carga da próxima página é feita via JavaScript
                    out.print( "true" );

                } else {
                    out.print( "false" );
                }

            } else {

                String email = request.getParameter( "email" );
                String senha = request.getParameter( "senha" );

                List< Usuario > lu = ( List< Usuario > ) ht.find(
                        "from Usuario " +
                        "where " +
                        "    email = ?", email );
                t.commit();

                if ( lu.size() > 0 ) {

                    Usuario u = lu.get( 0 );
                    
                    if ( u.getSenha().equals( senha ) ) {

                        // se o usuário existe:
                        // abre a sessão
                        HttpSession s = request.getSession( true );

                        // coloca o usuário na sessão
                        s.setAttribute( "usuario", u );

                        // a carga da próxima página é feita via JavaScript
                        out.print( "true" );

                    } else {
                        out.print( "false" );
                    }
                } else {
                    out.print( "false" );
                }

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
