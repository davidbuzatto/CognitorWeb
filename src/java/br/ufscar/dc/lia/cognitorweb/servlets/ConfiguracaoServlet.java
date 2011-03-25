/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para gerencimento de configurações.
 *
 * @author David Buzatto
 */
public class ConfiguracaoServlet extends HttpServlet {
   
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

        out.print( "{ success: " );

        try {

            /*
             * Faz a validação do usuário.
             * Caso não seja válido, dispara uma exceção.
             * Apenas o administrador tem acesso a este servlet.
             */
            Uteis.validateUser( request, rs, TipoUsuario.A );

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            String acao = request.getParameter( "acao" );
            String id = request.getParameter( "id" );

            if ( acao.equals( "salvar" ) ) {

                String propriedade = request.getParameter( "propriedade" );
                String valor = request.getParameter( "valor" );

                Configuracao c = null;

                if ( id.equals( "" ) )
                    c = new Configuracao();
                else
                    c = ( Configuracao ) ht.load( Configuracao.class, Long.parseLong( id ) );

                c.setPropriedade( propriedade );
                c.setValor( valor );

                ht.saveOrUpdate( c );
                t.commit();

            } else if ( acao.equals( "excluir" ) ) {

                Configuracao c = ( Configuracao ) ht.load( Configuracao.class, Long.parseLong( id ) );
                ht.delete( c );
                t.commit();

            }

            // recarrega atributos no contexto
            getServletContext().setAttribute( "configs",
                    Uteis.obtemConfiguracoes() );

            out.print( "true" );

        } catch ( Exception exc ) {

            Map< String, String > configs = ( HashMap< String, String > )
                    getServletContext().getAttribute( "configs" );

            /*
             * Baseado no parâmetro de debug da aplicação, cria a mensagem de
             * erro para retornar
             */
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
