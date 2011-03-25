/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.Usuario;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para geração de relatórios.
 *
 * @author David Buzatto
 */
public class ReportsServlet extends HttpServlet {
   
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

        response.setContentType( "application/pdf" );
        OutputStream out = response.getOutputStream();

        try {

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            HttpSession s = request.getSession( false );
            Usuario u = ( Usuario ) s.getAttribute( "usuario" );

            String tipo = request.getParameter( "tipo" );

            ReportUtil ru = new ReportUtil();

            if ( tipo.equals( "estatisticas" ) ) {

                response.setHeader( "Content-Disposition", "attachment;filename=Estatisticas.pdf" );

                Map< String, Object > parametros = new HashMap< String, Object >();

                List lista = ht.find( "from Material group by dataCriacao" );

                int quantMat = lista.size();
                int quantUs = ht.find( "from Usuario" ).size();

                parametros.put( "quantidadeMateriais", quantMat );
                parametros.put( "quantidadeUsuarios", quantUs );
                parametros.put( "media", ( double ) quantMat / ( double ) quantUs );
                parametros.put( "nomeUsuario", u.getPrimeiroNome() + " " + u.getUltimoNome() );
                parametros.put( "data", new Date() );

                ru.gerar( "Estatisticas", lista, parametros, out );

            }

            t.commit();

        } catch ( Exception exc ) {

            exc.printStackTrace();

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
