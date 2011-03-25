/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.util;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.enumeracoes.*;
import br.ufscar.dc.lia.cognitorweb.excecoes.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import org.apache.commons.fileupload.*;
import org.apache.commons.mail.*;
import org.joda.time.*;
import org.springframework.orm.hibernate3.*;

/**
 * Classe de métodos utilitários.
 *
 * @author David Buzatto
 */
public class Uteis {

    /*
     * Obtém as configurações do sistema, que estão gravadas na tabela Configuracao.
     * 
     */
    @SuppressWarnings( value = "unchecked" )
    public static Map< String, String > obtemConfiguracoes() {

        Map< String, String > configs = new HashMap< String, String >();

        Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
        HibernateTemplate hTemplate = dao.getHibernateTemplate();
        List< Configuracao > conf = ( List< Configuracao>  ) hTemplate.loadAll( Configuracao.class );

        for ( Configuracao c : conf ) {
            configs.put( c.getPropriedade(), c.getValor() );
        }

        return configs;

    }

    /*
     * Cria um java.util.Data a partir de uma String bem formatada (dd/mm/yyyy)
     */
    public static Date createDateForString( String dataString ) {

        String[] tokens = dataString.split( "/" );
        DateTime dt = new DateTime(
                Integer.parseInt( tokens[2] ),
                Integer.parseInt( tokens[1] ),
                Integer.parseInt( tokens[0] ),
                0, 0, 0, 0 );

        return dt.toDate();

    }

    /*
     * Cria mensagem de erro com base em um Throwable.
     */
    public static String createErrorMessage( Throwable exc, Boolean debug ) {

        StringBuilder sb = new StringBuilder();

        sb.append( "false," );
        sb.append( "debug: " + debug + "," );
        sb.append( "errorMsg: '" + exc.getMessage() + "'," );

        if ( debug ) {

            sb.append( "stackTrace: '" );
            for ( StackTraceElement e : exc.getStackTrace() ) {
                sb.append( e.toString() + "\\n" );
            }
            sb.append( "'" );

        } else { 

            sb.append( "stackTrace: '" );
            for ( StackTraceElement e : exc.getStackTrace() ) {
                sb.append( e.toString() + "\\n" );
            }
            sb.append( "'" );

        }

        return sb.toString();

    }

    /*
     * Envia um e-mail simples.
     */
    public static void sendMail( String para, String deEmail, String deNome,
            String servidor, String titulo, String mensagem )
            throws EmailException {

        SimpleEmail email = new SimpleEmail();
        email.setHostName( servidor );
        email.addTo( para );
        email.setFrom( deEmail, deNome );
        email.setSubject( titulo );
        email.setMsg( mensagem );
        email.send();

    }

    /*
     *  Obtém o ResourceBundle interno do sistema o código do idioma.
     */
    public static ResourceBundle getBundle( String idioma ) {

        Locale locale = null;

        if ( idioma.equals( "pt" ) ) {
            locale = new Locale( "pt" );
        } else {
            locale = new Locale( "en" );
        }

        return ResourceBundle.getBundle(
                "br.ufscar.dc.lia.cognitorweb.i18n.TabelaStringsInterno", locale );

    }

    /*
     * Valida um usuário.
     * Caso o usuário passado esteja listado em um dos tipos permitidos, a verificação
     * passa. Caso contrário, lança uma exceção.
     *
     * Retorna o usuário logado caso seja bem sucedida.
     *
     */
    public static Usuario validateUser( HttpServletRequest request,
            ResourceBundle rs, TipoUsuario... tipos )
            throws NotLoggedUserException, InvalidUserException {

        HttpSession sessao = request.getSession();
        Usuario u = ( Usuario ) sessao.getAttribute( "usuario" );
        boolean ok = false;

        if ( u == null ) {
            throw new NotLoggedUserException( rs.getString( "erro.usuarioNaoLogado" ) );
        } else {
            for ( TipoUsuario t : tipos ) {
                if ( u.getTipo() == t ) {
                    ok = true;
                    break;
                }
            }
        }

        if ( !ok ) {
            throw new InvalidUserException( rs.getString( "erro.usuarioInvalido" ) );
        }

        return u;

    }

    /*
     * Deleta um diretório de forma recursiva.
     */
    public static boolean deleteDirectory( File file ) {

        if( file.exists() ) {
            for( File f : file.listFiles() ) {
                if( f.isDirectory() ) {
                    deleteDirectory( f );
                } else {
                    f.delete();
                }
            }
        }

        return file.delete();

    }

    /*
     * Deleta o conteúdo de um diretório de forma recursiva.
     */
    public static void deleteDirectoryContent( File diretorio ) {

        if( diretorio.exists() ) {
            for( File f : diretorio.listFiles() ) {
                if( f.isDirectory() ) {
                    deleteDirectory( f );
                } else {
                    f.delete();
                }
            }
        }

    }

    /*
     * Retorna o layout do material com base nas opções.
     */
    public static LayoutMaterial getMaterialLayout( String organizacaoPagina,
            String organizacaoBarra ) {

        if ( organizacaoPagina != null && organizacaoBarra != null )
            return LayoutMaterial.PCLCBN;
        if ( organizacaoPagina != null && organizacaoBarra == null )
            return LayoutMaterial.PCLSBN;
        if ( organizacaoPagina == null && organizacaoBarra != null )
            return LayoutMaterial.PSLCBN;
        if ( organizacaoPagina == null && organizacaoBarra == null )
            return LayoutMaterial.PSLSBN;

        return LayoutMaterial.PCLSBN;

    }

    /*
     * Obtém um OA a partir do tipo e do hibernate template.
     */
    public static ObjetoAprendizagem getOA( HibernateTemplate ht, String tipo, long id ) {

        ObjetoAprendizagem oa = null;

        if ( tipo.equals( "material" ) )
            oa = ( Material ) ht.load( Material.class, id );
        else if ( tipo.equals( "conceito" ) )
            oa = ( Conceito ) ht.load( Conceito.class, id );
        else if ( tipo.equals( "grupo" ) )
            oa = ( Grupo ) ht.load( Grupo.class, id );
        else if ( tipo.equals( "pagina" ) )
            oa = ( Pagina ) ht.load( Pagina.class, id );
        else if ( tipo.equals( "imagem" ) )
            oa = ( Imagem ) ht.load( Imagem.class, id );
        else if ( tipo.equals( "video" ) )
            oa = ( Video ) ht.load( Video.class, id );
        else if ( tipo.equals( "som" ) )
            oa = ( Som ) ht.load( Som.class, id );

        return oa;

    }

    /*
     * Verifica se é o usuario dono do OA.
     */
    public static boolean mesmoUsuario( String tipo, ObjetoAprendizagem oa, Usuario u ) {

        boolean mesmoUsuario = false;

        if ( tipo.equals( "material" ) )
            mesmoUsuario = u.equals( ( ( Material ) oa ).getUsuario() );
        else if ( tipo.equals( "conceito" ) )
            mesmoUsuario = u.equals( ( ( Conceito ) oa ).getUsuario() );
        else if ( tipo.equals( "grupo" ) )
            mesmoUsuario = u.equals( ( ( Grupo ) oa ).getUsuario() );
        else if ( tipo.equals( "pagina" ) )
            mesmoUsuario = u.equals( ( ( Pagina ) oa ).getUsuario() );

        return mesmoUsuario;

    }

    /*
     * Extrai de uma lista de FileItem o nome e o valor do campo.
     */
    public static Map< String, String > getFieldValues( List lista ) {

        HashMap< String, String > mapa = new HashMap< String, String >();
        
        for ( int i = 0; i < lista.size(); i++ ) {
            FileItem item = ( FileItem ) lista.get( i );
            if ( item.isFormField() ) {
                mapa.put( item.getFieldName(), item.getString() );
            }
        }

        return mapa;

    }

    /*
     * Limpa uma String, retirando acentos e qualquer caracter especial.
     */
    public static String retiraAcentos( String s ) {

        s = s.replaceAll( "á|à|ã|â|ä", "a" );
        s = s.replaceAll( "Á|À|Ã|Â|Ä", "A" );
        s = s.replaceAll( "é|è|ê|ë", "e" );
        s = s.replaceAll( "É|È|Ê|Ë", "E" );
        s = s.replaceAll( "í|ì|î|ï", "i" );
        s = s.replaceAll( "Í|Ì|Î|Ï", "I" );
        s = s.replaceAll( "ó|ò|õ|ô|ö", "o" );
        s = s.replaceAll( "Ó|Ò|Õ|Ô|Ö", "O" );
        s = s.replaceAll( "ú|ù|û|ü", "u" );
        s = s.replaceAll( "Ú|Ù|Û|Ü", "U" );
        s = s.replaceAll( "ç", "c" );
        s = s.replaceAll( "Ç", "C" );
        s = s.replaceAll( " ", "_" );
        s = s.replaceAll( "[\\W&&[^\\.]&&[^\\-]]", "" );

        return s;

    }

    /*
     * Faz o escape dos caracteres para o html.
     */
    public static String escapeAcentos( String s ) {
        StringBuilder b = new StringBuilder( s.length() );
        for ( int i = 0; i < s.length(); i++ ) {
            char ch = s.charAt( i );
            if ( ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' ) {
                b.append( ch );
            } else {
                if ( Character.isDefined( ch ) ) {
                    int ci = ( int ) ch;
                    if ( ci >= 161 && ci <= 255  )
                        b.append( "&#" ).append( ( int ) ch ).append( ";" );
                    else
                        b.append( ch );
                }
            }
        }
        return b.toString();
    }

}
