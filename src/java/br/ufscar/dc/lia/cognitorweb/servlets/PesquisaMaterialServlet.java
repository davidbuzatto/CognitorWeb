/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para pesquisa de materiais.
 *
 * @author David Buzatto
 */
public class PesquisaMaterialServlet extends HttpServlet {
   
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

        Map< String, String > configs = ( HashMap< String, String > )
                getServletContext().getAttribute( "configs" );

        out.print( "{ success: " );

        try {

            String tipoPesquisa = request.getParameter( "tipoPesquisa" );

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            List<Material> lista = new ArrayList<Material>();

            // obtém a query consultaMateriais (configurada na entidade Material)
            Query query = ht.getSessionFactory().getCurrentSession()
                        .getNamedQuery( "consultaMateriais" );

            // pesquisa normal
            if ( tipoPesquisa.equals( "pn" ) ) {

                String pesq = request.getParameter( "pesq" );

                if ( pesq.trim().length() > 2 )
                    lista = ( List<Material> ) query.
                        setParameter( "titulo", "%" + pesq + "%" ).
                        setParameter( "descricao", "%" ).
                        setParameter( "palavraChave", "%" ).
                        setParameter( "inicio", 0 ).
                        setParameter( "fim", 50 ).
                        list();

                // pesquisa avançada
            } else if ( tipoPesquisa.equals( "pa" ) ) {

                String pesqTit = request.getParameter( "pesqTit" );
                String pesqDesc = request.getParameter( "pesqDesc" );
                String pesqKey = request.getParameter( "pesqKey" );

                if ( pesqTit.trim().length() < 3 )
                    pesqTit = "%";
                if ( pesqDesc.trim().length() < 3 )
                    pesqDesc = "%";
                if ( pesqKey.trim().length() < 3 )
                    pesqKey = "%";

                lista = ( List<Material> ) query.
                        setParameter( "titulo", "%" + pesqTit + "%" ).
                        setParameter( "descricao", "%" + pesqDesc + "%" ).
                        setParameter( "palavraChave", "%" + pesqKey + "%" ).
                        setParameter( "inicio", 0 ).
                        setParameter( "fim", 50 ).
                        list();

            }

            t.commit();
            out.print( "true, " );

            StringBuilder sb = new StringBuilder();
            SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );

            sb.append( "dados: [" );

            for ( Material m : lista ) {
                sb.append( "{" );
                sb.append( "id: '" + m.getId() + "', " );
                sb.append( "titulo: '" + m.getTitulo() + "', " );
                sb.append( "autor: '" + m.getUsuario().getPrimeiroNome() + " " + m.getUsuario().getUltimoNome() + "', " );
                sb.append( "descricao: '" + m.getMetadata().getLom().getGeneral().getDescriptions().get( 0 ).getStrings().get( 0 ).getValue().replace( "\n", "<br/>" ) + "', " );
                sb.append( "palavrasChave: '" + m.getMetadata().getLom().getGeneral().getKeywords().get( 0 ).getStrings().get( 0 ).getValue().replace( "\n", "<br/>" ) + "', " );
                sb.append( "dataCriacao: '" + df.format( m.getDataCriacao() ) + "', " );
                sb.append( "dataAtualizacao: '" + df.format( m.getDataAtualizacao() ) + "'" );
                sb.append( "}," );
            }

            sb.append( "]" );

            out.print( sb.toString().replace( "},]", "}]"  ) );

        } catch ( Exception exc ) {
            exc.printStackTrace();
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
