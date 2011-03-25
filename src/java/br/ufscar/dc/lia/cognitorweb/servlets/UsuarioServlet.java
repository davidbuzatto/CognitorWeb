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
 * Servlet para gerenciamento de usuários.
 * 
 * @author David Buzatto
 */
public class UsuarioServlet extends HttpServlet {
   
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

            String acao = request.getParameter( "acao" );
            String id = request.getParameter( "id" );

            if ( acao.equals( "salvar" ) ) {

                Uteis.validateUser( request, rs, TipoUsuario.A );

                String email = request.getParameter( "email" );
                String senha = request.getParameter( "senha" );
                String tipo = request.getParameter( "tipo" );
                String primeiroNome = request.getParameter( "primeiroNome" );
                String nomeMeio = request.getParameter( "nomeMeio" );
                String ultimoNome = request.getParameter( "ultimoNome" );
                String dataNascimento = request.getParameter( "dataNascimento" );
                String sexo = request.getParameter( "sexo" );
                String rua = request.getParameter( "rua" );
                String numero = request.getParameter( "numero" );
                String complemento = request.getParameter( "complemento" );
                String pais = request.getParameter( "pais" );
                String estado = request.getParameter( "estado" );
                String cidade = request.getParameter( "cidade" );
                String escolaridade = request.getParameter( "escolaridade" );
                String ocupacao = request.getParameter( "ocupacao" );

                // email antigo é usado para mudar o nome do repositório
                String emailAntigo = null;

                Usuario u = null;

                if ( id.equals( "" ) ) {
                    u = new Usuario();
                } else {
                    u = ( Usuario ) ht.load( Usuario.class, Long.parseLong( id ) );
                    // armazena o e-mail antigo
                    emailAntigo = u.getEmail();
                }

                u.setEmail( email );
                u.setSenha( senha );
                u.setTipo( TipoUsuario.valueOf( tipo ) );
                u.setPrimeiroNome( primeiroNome );
                u.setNomeMeio( nomeMeio );
                u.setUltimoNome( ultimoNome );
                u.setDataNascimento( Uteis.createDateForString( dataNascimento ) );
                u.setSexo( Sexo.valueOf( sexo ) );
                u.setRua( rua );
                u.setNumero( numero );
                u.setComplemento( complemento );
                u.setPais( ( Pais ) ht.load( Pais.class, Long.parseLong( pais ) ) );

                if ( estado.equals( "" ) )
                    u.setEstado( null );
                else
                    u.setEstado( ( Estado ) ht.load( Estado.class, Long.parseLong( estado ) ) );

                if ( cidade.equals( "" ) )
                    u.setCidade( null );
                else
                    u.setCidade( ( Cidade ) ht.load( Cidade.class, Long.parseLong( cidade ) ) );

                u.setEscolaridade( Escolaridade.valueOf( escolaridade ) );
                u.setOcupacao( ocupacao );

                ht.saveOrUpdate( u );

                /*
                 * Tenta criar o diretório dentro do repositório. É imporante fazer
                 * isso antes do commit, pois o diretório da área do usuário terá o
                 * nome igual ao e-mail do usuário e este precisa ter sido validado.
                 * Caso dê algum erro no saveOrUpdate o repositório do usuário não
                 * será criado.
                 *
                 * Se o e-mail antigo é null, quer dizer que é um novo usuário.
                 * Sendo assim, cria o diretório.
                 *
                 * Caso não seja um usuário novo, renomeia o diretório.
                 */
                if ( emailAntigo == null ) {
                    File diretorio = new File( configs.get( "repositorioArquivos" ) + u.getEmail() );
                    diretorio.mkdir();
                } else {
                    File diretorioAntigo = new File( configs.get( "repositorioArquivos" ) + emailAntigo );
                    File novoDiretorio = new File( configs.get( "repositorioArquivos" ) + u.getEmail() );
                    diretorioAntigo.renameTo( novoDiretorio );
                }

                t.commit();

            } else if ( acao.equals( "alterarUsuarioLogado" ) ) {

                /*
                 * NUNCA altera o tipo do usuário - segurança ;)
                 * O diretório do repositório tbm não é alterado, pois o e-mail
                 * não pode ser alterado pelo usuário.
                 */
                Uteis.validateUser( request, rs,
                        TipoUsuario.A,
                        TipoUsuario.E,
                        TipoUsuario.P );

                String senha = request.getParameter( "senha" );
                String primeiroNome = request.getParameter( "primeiroNome" );
                String nomeMeio = request.getParameter( "nomeMeio" );
                String ultimoNome = request.getParameter( "ultimoNome" );
                String dataNascimento = request.getParameter( "dataNascimento" );
                String sexo = request.getParameter( "sexo" );
                String rua = request.getParameter( "rua" );
                String numero = request.getParameter( "numero" );
                String complemento = request.getParameter( "complemento" );
                String pais = request.getParameter( "pais" );
                String estado = request.getParameter( "estado" );
                String cidade = request.getParameter( "cidade" );
                String escolaridade = request.getParameter( "escolaridade" );
                String ocupacao = request.getParameter( "ocupacao" );

                Usuario u = ( Usuario ) ht.load( Usuario.class, Long.parseLong( id ) );

                u.setSenha( senha );
                u.setPrimeiroNome( primeiroNome );
                u.setNomeMeio( nomeMeio );
                u.setUltimoNome( ultimoNome );
                u.setDataNascimento( Uteis.createDateForString( dataNascimento ) );
                u.setSexo( Sexo.valueOf( sexo ) );
                u.setRua( rua );
                u.setNumero( numero );
                u.setComplemento( complemento );
                u.setPais( ( Pais ) ht.load( Pais.class, Long.parseLong( pais ) ) );

                if ( estado.equals( "" ) )
                    u.setEstado( null );
                else
                    u.setEstado( ( Estado ) ht.load( Estado.class, Long.parseLong( estado ) ) );

                if ( cidade.equals( "" ) )
                    u.setCidade( null );
                else
                    u.setCidade( ( Cidade ) ht.load( Cidade.class, Long.parseLong( cidade ) ) );

                u.setEscolaridade( Escolaridade.valueOf( escolaridade ) );
                u.setOcupacao( ocupacao );

                ht.saveOrUpdate( u );
                t.commit();

                /*
                 * Atualiza usuário que está na sessão.
                 * Não cria uma sessão nova.
                 */
                HttpSession session = request.getSession();
                session.setAttribute( "usuario", u );

            } else if ( acao.equals( "excluir" ) ) {

                Uteis.validateUser( request, rs, TipoUsuario.A );
                
                Usuario u = ( Usuario ) ht.load( Usuario.class, Long.parseLong( id ) );
                ht.delete( u );

                // exclui o diretório
                Uteis.deleteDirectory( new File( configs.get( "repositorioArquivos" ) + u.getEmail() ) );

                t.commit();

            }

            out.print( "true" );
            
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
