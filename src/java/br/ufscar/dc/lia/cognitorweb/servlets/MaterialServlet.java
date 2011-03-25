/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Esse servlet apenas cria a estrutura básica de um material,
 * ou seja, a raiz (material em si) e seus metadados. Permite também que um
 * material seja excluido.
 *
 * Ele retorna o id do material para ser carregado na interface caso seja criado.
 *
 * @author David Buzatto
 */
public class MaterialServlet extends HttpServlet {
   
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

            Usuario u = Uteis.validateUser( request, rs, TipoUsuario.values() );
            String acao = request.getParameter( "acao" );

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            if ( acao.equals( "novo" ) ) {

                String titulo = request.getParameter( "titulo" );
                String compartilhado = request.getParameter( "compartilhado" );
                String estrCon = request.getParameter( "estrCon" );
                String organizacaoPagina = request.getParameter( "organizacaoPagina" );
                String organizacaoBarra = request.getParameter( "organizacaoBarra" );

                // toda a hierarquia dos metadados é gerada e salva dentro desse método
                Metadata meta = MetadataUtils.createMetadataInstance( 
                        ht, titulo, request.getLocale().getLanguage(), "LOMv1.0" );

                Material m = new Material();
                m.setTitulo( titulo );
                m.setDataCriacao( new Date() );
                m.setDataAtualizacao( new Date() );
                m.setCompartilhado( Boolean.parseBoolean( compartilhado ) );
                m.setConhecimentoEstruturado( Boolean.parseBoolean( estrCon ) );
                m.setLayout( Uteis.getMaterialLayout( organizacaoPagina, organizacaoBarra ) );
                m.setUsuario( u );
                m.setMetadata( meta );

                ht.saveOrUpdate( m );

                /*
                 * Cria estrutura de diretórios:
                 * raiz
                 *   |-- arquivos
                 *   |     |-- imagens
                 *   |     |-- sons
                 *   |     |-- videos
                 *   |-- build
                 *   |-- dist
                 *   |-- temp
                 */
                // raiz do material
                String caminhoRaiz = configs.get( "repositorioArquivos" ) +
                        u.getEmail() + "/" + m.getId();

                List< File > diretorios = new ArrayList< File >();
                diretorios.add( new File( caminhoRaiz ) );
                diretorios.add( new File( caminhoRaiz + "/build" ) );
                diretorios.add( new File( caminhoRaiz + "/arquivos" ) );
                diretorios.add( new File( caminhoRaiz + "/arquivos/imagens" ) );
                diretorios.add( new File( caminhoRaiz + "/arquivos/videos" ) );
                diretorios.add( new File( caminhoRaiz + "/arquivos/sons" ) );
                diretorios.add( new File( caminhoRaiz + "/dist" ) );
                diretorios.add( new File( caminhoRaiz + "/temp" ) );

                for ( File f : diretorios ) {
                    f.mkdir();
                }

                t.commit();

                out.print( "true," );
                out.print( "idMaterialGerado:" + m.getId() );

            } else if ( acao.equals( "excluir" ) ) {

                long id = Long.parseLong( request.getParameter( "id" ) );
                Material m = ( Material ) ht.load( Material.class, id );

                if ( Uteis.mesmoUsuario( "material", m, u ) ) {

                    DeleteUtils.deleteOA( ht, m );

                    File diretorio = new File( configs.get( "repositorioArquivos" ) +
                            u.getEmail() + "/" + m.getId() );
                    Uteis.deleteDirectory( diretorio );

                    t.commit();
                    out.print( "true" );

                } else {
                    throw new ServletException( "Não permitido - Not allowed" );
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
