/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.sensocomum.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Classe para consulta na base de senso comum.
 * 
 * @author David Buzatto
 */
public class CommonSenseServlet extends HttpServlet {
   
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

        try {

            String conceito = StringUtils.toISO( request.getParameter( "concept" ).toLowerCase() );
            String relacao = request.getParameter( "relation" );
            StringBuilder sb = new StringBuilder();

            if ( relacao.equals( "All" ) )
                relacao = null;

            OMCSClient cliente = new OMCSClient( "http://lia.dc.ufscar.br:8001" );
            List< Relation > lista = cliente.getDisplayNodeTo( conceito, relacao );

            sb.append( "{ success: true, results: [ " );

            if ( lista.size() != 0 ) {
                for ( Relation r : lista ) {
                    sb.append(
                            "{ relacao: '" + r.getPredicate() + "', " +
                            " conceito: '" + r.getConcept2() + "'}," );
                }
            } else {
                sb.append( "{ relacao: '', conceito: '" + rs.getString( "servlets.commonSense.semResultados" ) + "'}," );
            }

            String saida = sb.toString();
            saida = saida.substring( 0, saida.length() -1 );

            out.print( saida + " ]" );

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
