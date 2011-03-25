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
import org.apache.commons.mail.EmailException;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet usando para o cadastro de novos usuários.
 *
 * @author David Buzatto
 */
public class CadastroNovoUsuarioServlet extends HttpServlet {
   
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

            String email = request.getParameter( "email" );
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

            Usuario u = new Usuario();

            u.setEmail( email );
            u.setSenha( senha );
            // qq usuário que se cadastre é educador por padrão
            u.setTipo( TipoUsuario.E );
            u.setPrimeiroNome( primeiroNome );
            u.setNomeMeio( nomeMeio );
            u.setUltimoNome( ultimoNome );
            u.setDataNascimento( Uteis.createDateForString( dataNascimento ) );
            u.setSexo( Sexo.valueOf( sexo ) );
            u.setRua( rua );
            u.setNumero( numero );
            u.setComplemento( complemento );
            u.setPais( ( Pais ) ht.load( Pais.class, Long.parseLong( pais ) ) );

            if ( estado.equals( "S" ) )
                u.setEstado( null );
            else
                u.setEstado( ( Estado ) ht.load( Estado.class, Long.parseLong( estado ) ) );

            if ( cidade.equals( "S" ) )
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
             */
            File diretorio = new File( configs.get( "repositorioArquivos" ) + u.getEmail() );
            diretorio.mkdir();

            t.commit();

            out.print( "true" );
            
            // tenta enviar e-mail para o novo usuário
            try {
                Uteis.sendMail(
                        u.getEmail(),
                        configs.get( "emailCognitor" ),
                        configs.get( "nomeCognitor" ),
                        configs.get( "servidorEmail" ),
                        rs.getString( "servlets.cadNovoUsuario.email.titulo" ),
                        geraMensagemNovoUsuario( u, rs ) );
                out.print( ", envioEmail: true" );
            } catch ( EmailException mexc ) {
                out.print( ", envioEmail: false" );
            }

        } catch ( Exception exc ) {

            out.print( Uteis.createErrorMessage( exc, new Boolean( configs.get( "debug" ) ) ) );
            
        } finally {
            out.print( "}" );
            out.close();
        }

    } 

    private String geraMensagemNovoUsuario( Usuario u, ResourceBundle rs ) {

        StringBuilder sb = new StringBuilder();

        sb.append( rs.getString( "email.comum.preambulo1" ) + "\n" );
        sb.append( rs.getString( "email.comum.preambulo2" ) + "\n\n" );
        sb.append( rs.getString( "email.comum.separador" ) + "\n\n" );
        sb.append( rs.getString( "email.comum.preambulo3" ) + u.getPrimeiroNome() + " " + u.getUltimoNome() + ",\n\n" );
        sb.append( rs.getString( "servlets.cadNovoUsuario.email.bemVindo" ) + "\n" );
        sb.append( rs.getString( "servlets.cadNovoUsuario.email.dadosLogin" ) + "\n\n" );
        sb.append( rs.getString( "email.comum.email" ) + u.getEmail() + "\n" );
        sb.append( rs.getString( "email.comum.senha" ) + u.getSenha() );
        sb.append( "\n\n" + rs.getString( "email.comum.separador" ) );
        sb.append( "\n\n" + rs.getString( "email.comum.agradecimento" ) );

        return sb.toString();

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
