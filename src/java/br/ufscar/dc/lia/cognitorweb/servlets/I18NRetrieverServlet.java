/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.Dao;
import br.ufscar.dc.lia.cognitorweb.entidades.I18NTable;
import br.ufscar.dc.lia.cognitorweb.util.SpringUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.orm.hibernate3.HibernateTemplate;

/**
 * Servlet para obtenção de dados de i18n usando a base de dados.
 *
 * @author David Buzatto
 */
public class I18NRetrieverServlet extends HttpServlet {
   
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

        String idioma = request.getParameter("idioma");

        try {

            StringBuilder sb = new StringBuilder();

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();

            List< I18NTable > i18n = ( List< I18NTable > ) ht.find(
                    "from I18NTable where idioma = '" + idioma + "'"  );

            sb.append( "{i18n:{" );

            if ( i18n.size() == 0 ) {
                sb.append( "vazio: 'vazio'," );
            } else {
                for ( I18NTable i : i18n ) {
                    sb.append( i.getPropriedade() + ": '" + i.getValor() + "'," );
                }
            }

            String saida = sb.toString();
            saida = saida.substring( 0, saida.length() - 1 );

            out.print( saida + "}}" );

        } finally {
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
