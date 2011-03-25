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
 * Servlet para a geração de analogias.
 *
 * O algoritmo é o mesmo da versão desktop do Cognitor, foram feitas
 * apenas algumas mudanças para ficar mais legível.
 *
 * @author David Buzatto
 */
public class GerarAnalogiasServlet extends HttpServlet {
   
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

            String conceito = request.getParameter( "conceito" ).toLowerCase();
            String usadoPara = request.getParameter( "usadoPara" ).toLowerCase();
            String capazDe = request.getParameter( "capazDe" ).toLowerCase();
            String preReq = request.getParameter( "preReq" ).toLowerCase();
            String primeiroPassoPara = request.getParameter( "primeiroPassoPara" ).toLowerCase();
            String passoPara = request.getParameter( "passoPara" ).toLowerCase();
            String ultimoPassoPara = request.getParameter( "ultimoPassoPara" ).toLowerCase();
            String tipoDe = request.getParameter( "tipoDe" ).toLowerCase();
            String adjetivos = request.getParameter( "adjetivos" ).toLowerCase();
            String parteDe = request.getParameter( "parteDe" ).toLowerCase();
            String feitoDe = request.getParameter( "feitoDe" ).toLowerCase();
            String definidoComo = request.getParameter( "definidoComo" ).toLowerCase();
            String encontradoEm = request.getParameter( "encontradoEm" ).toLowerCase();
            String temConsequencias = request.getParameter( "temConsequencias" ).toLowerCase();
            String motivacaoPara = request.getParameter( "motivacaoPara" ).toLowerCase();
            String desejoDe = request.getParameter( "desejoDe" ).toLowerCase();

            HashMap< String, String > listaValores = new HashMap< String, String >();
            listaValores.put( "UsedFor", usadoPara );
            listaValores.put( "CapableOf", capazDe );
            listaValores.put( "PrerequisiteEventOf", preReq );
            listaValores.put( "FirstSubeventOf", primeiroPassoPara );
            listaValores.put( "SubeventOf", passoPara );
            listaValores.put( "LastSubeventOf", ultimoPassoPara );
            listaValores.put( "IsA", tipoDe );
            listaValores.put( "PropertyOf", adjetivos );
            listaValores.put( "PartOf", parteDe );
            listaValores.put( "MadeOf", feitoDe );
            listaValores.put( "DefinedAs", definidoComo );
            listaValores.put( "LocationOf", encontradoEm );
            listaValores.put( "EffectOf", temConsequencias );
            listaValores.put( "MotivationOf", motivacaoPara );
            listaValores.put( "DesireOf", desejoDe );

            StringBuilder entradas = new StringBuilder();
            for ( String predicado: listaValores.keySet() ) {
                String[] valores = listaValores.get( predicado ).split(",");
                for ( int i = 0; i < valores.length; i++ ) {
                    String valor = valores[i].trim();
                    if ( valor.length() != 0 ) {
                        entradas.append( "(" + predicado +
                                " \"" + conceito + "\" \"" + valor+ "\" \"f=1;i=1\")\n" );
                    }
                }
            }

            String conhecimento = entradas.toString();
            
            OMCSClient cliente = new OMCSClient( "http://lia.dc.ufscar.br:8001" );
            List< Analogy > lista = cliente.getAnalogies(
                    StringUtils.toISO( conceito ), StringUtils.toISO( conhecimento ) );


            StringBuilder sb = new StringBuilder();

            sb.append( "{ success: true, results: [ " );

            if ( lista.size() != 0 ) {
                for ( Analogy a : lista ) {
                    sb.append( jsonToAnalogy( a ) );
                }
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

    private String jsonToAnalogy( Analogy a ) {

        StringBuilder sb = new StringBuilder();

        sb.append( "{" +
                "conceptBase: '" + StringUtils.toUTF8( a.getConceptBase() ) + "', " +
                "conceptTarget: '" + a.getConceptTarget() + "', " +
                "weight: " + a.getWeight() +  ", " +
                "analogyParts: [" );

        for ( AnalogyPart p : a.getAnalogyParts() ) {

            sb.append( "{" +
                    "predicate: '" + p.getPredicate() + "', " +
                    "expert1: '" + p.getExpert1() + "', " +
                    "expert2: '" + p.getExpert2() + "', " +
                    "commonSense1: '" + p.getCommonSense1() + "', " +
                    "commonSense2: '" + p.getCommonSense2() + "'}, " );

        }

        return sb.append( "]}," ).toString();

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
