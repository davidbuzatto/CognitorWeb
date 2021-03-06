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
import org.joda.time.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para recuperação de senha.
 *
 * @author David Buzatto
 */
public class RecuperaSenhaServlet extends HttpServlet {
   
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

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            String email = request.getParameter( "email" );

            List< Usuario > lu = ( List< Usuario > ) ht.find(
                    "from Usuario " +
                    "where " +
                    "    email = ?", email );
            t.commit();

            if ( lu.size() > 0 ) {
                out.print( "true" );
                Usuario u = lu.get( 0 );
                Uteis.sendMail(
                        u.getEmail(),
                        configs.get( "emailCognitor" ),
                        configs.get( "nomeCognitor" ),
                        configs.get( "servidorEmail" ),
                        rs.getString( "servlets.recSenha.email.titulo" ),
                        geraMensagemRecuperarSenha( u, rs ) );
            } else {
                out.print( "false" );
            }

        } catch ( Exception exc ) {

            out.print( Uteis.createErrorMessage( exc, new Boolean( configs.get( "debug" ) ) ) );

        } finally {
            out.print( "}" );
            out.close();
        }

    }

    private String geraMensagemRecuperarSenha( Usuario u, ResourceBundle rs ) {

        StringBuilder sb = new StringBuilder();
        DateTime dt = new DateTime();
        sb.append( rs.getString( "email.comum.preambulo1" ) + "\n" );
        sb.append( rs.getString( "email.comum.preambulo2" ) + "\n\n" );
        sb.append( rs.getString( "email.comum.separador" ) + "\n\n" );
        sb.append( rs.getString( "email.comum.preambulo3" ) + u.getPrimeiroNome() + " " + u.getUltimoNome() + ",\n\n" );
        sb.append( rs.getString( "servlets.recSenha.email.req1" ) + dt.toString( "HH:mm" ) + " (GTM " + dt.toString( "z" ) );
        sb.append( "), " + rs.getString( "servlets.recSenha.email.req2" ) + dt.toString( "dd/MM/YYYY" ) + rs.getString( "servlets.recSenha.email.req3" ) + "\n" );
        sb.append( rs.getString( "servlets.recSenha.email.req4" ) + "\n" );
        sb.append( rs.getString( "servlets.recSenha.email.req5" ) + "\n\n" );
        sb.append( rs.getString( "email.comum.email" ) + u.getEmail() + "\n" );
        sb.append( rs.getString( "email.comum.senha" ) + u.getSenha() );
        sb.append( "\n\n" + rs.getString( "email.comum.separador" ) );
        sb.append( "\n\n" + rs.getString( "email.comum.agradecimento" ) );

        return sb.toString();

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
