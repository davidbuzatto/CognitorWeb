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
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para fazer upload de arquivos.
 * Apenas sobe os arquivos para a pasta correta, não criando o OA.
 *
 * @author David Buzatto
 */
public class UploadServlet extends HttpServlet {
   
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

            // verifica se é multipart
            if ( ServletFileUpload.isMultipartContent( request ) ) {

                Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
                HibernateTemplate ht = dao.getHibernateTemplate();
                Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
                t.begin();

                int tamanhoMaximoArquivo = Integer.parseInt( configs.get( "tamanhoMaximoArquivo" ) );

                // cria uma fábrica para items baseados em disco
                DiskFileItemFactory factory = new DiskFileItemFactory(
                        tamanhoMaximoArquivo,
                        new File( configs.get( "repositorioArquivos" ) ) );

                // cria um novo manipulador de arquivos enviados
                ServletFileUpload upload = new ServletFileUpload( factory );

                // configura o tamanho total do request
                upload.setSizeMax( tamanhoMaximoArquivo );

                // analisa o request
                List items = upload.parseRequest( request );
                
                // obtém o mapa com os dados
                Map< String, String > mapa = Uteis.getFieldValues( items );

                String tipo = mapa.get( "tipo" );
                long idMaterial = Long.parseLong( mapa.get( "idMaterial" ) );

                Material m = ( Material ) ht.load( Material.class, idMaterial );

                for ( int i = 0; i < items.size(); i++ ) {

                    FileItem item = ( FileItem ) items.get( i );

                    // se for arquivo
                    if ( !item.isFormField() ) {

                        String salvarEm = "";

                        if ( tipo.equals( "imagem" ) )
                            salvarEm = "/arquivos/imagens/";
                        else if ( tipo.equals( "video" ) )
                            salvarEm = "/arquivos/videos/";
                        else if ( tipo.equals( "som" ) )
                            salvarEm = "/arquivos/sons/";
                        
                        File arquivoEnviado = new File(
                                configs.get( "repositorioArquivos" ) +
                                m.getUsuario().getEmail() + "/" + m.getId() +
                                salvarEm + Uteis.retiraAcentos( StringUtils.toUTF8( mapa.get( "Filename" ) ) ) );
                        item.write( arquivoEnviado );

                    }

                }

                t.commit();
                out.print( rs.getString( "servlets.upload.arquivoOk" ) );

            } else {
                throw new ServletException( "Dados incorretos - Incorrect Data" );
            }

        } catch ( Exception exc ) {

            out.print( rs.getString( "servlets.upload.erroArquivo" ) );
            out.print( Uteis.createErrorMessage( exc, new Boolean( configs.get( "debug" ) ) ) );

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
