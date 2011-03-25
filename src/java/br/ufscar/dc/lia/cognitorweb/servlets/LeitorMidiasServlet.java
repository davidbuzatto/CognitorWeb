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
 * Servlet para leitura de mídias.
 *
 * @author David Buzatto
 */
public class LeitorMidiasServlet extends HttpServlet {
   
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

        OutputStream out = response.getOutputStream();

        Map< String, String > configs = ( HashMap< String, String > )
                getServletContext().getAttribute( "configs" );

        try {
            
            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();
            
            String nomeArquivo = request.getParameter( "nomeArquivo" );
            String tipo = request.getParameter( "tipo" );
            String extensao = nomeArquivo.substring( nomeArquivo.lastIndexOf( "." ) + 1, nomeArquivo.length() );
            long idMaterial = Long.parseLong( request.getParameter( "idMaterial" ) );
            Material m = ( Material ) ht.load( Material.class, idMaterial );
            
            String lerDe = "";

            if ( tipo.equals( "imagem" ) ) {

                lerDe = "/arquivos/imagens/";
                response.setContentType( "image/" + extensao );

            } else if ( tipo.equals( "video" ) ) {

                lerDe = "/arquivos/videos/";
                response.setContentType( "video/x-" + extensao );
                response.setHeader( "Content-Disposition", "attachment;filename=" + nomeArquivo );

            } else if ( tipo.equals( "som" ) ) {

                lerDe = "/arquivos/sons/";
                response.setContentType( "audio/x-" + extensao );
                response.setHeader( "Content-Disposition", "attachment;filename=" + nomeArquivo );

            } else {

                throw new ServletException( "Tipo não suportado - Not supported type" );

            }

            FileInputStream fis = new FileInputStream( new File(
                    configs.get( "repositorioArquivos" ) +
                    m.getUsuario().getEmail() + "/" + m.getId() +
                    lerDe + nomeArquivo ) );

            byte[] buffer = new byte[ 2048 ];
            int b = 0;

            while ( ( b = fis.read( buffer, 0, 2048 ) ) != -1 ) {
                out.write( buffer, 0, b );
            }

            fis.close();

        } catch ( FileNotFoundException exc ) {
            
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
