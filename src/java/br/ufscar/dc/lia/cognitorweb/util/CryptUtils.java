/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.util;

/**
 * Classe para encriptação e desencriptação de Strings.
 * 
 * @author David Buzatto
 */
public class CryptUtils {

    /**
     * Encripta uma String, dado o tamanho da chave que deve ser gerada.
     */
    public static String encrypt( String string, int kSize ) {

        if ( string.length() >= kSize ) {

            StringBuilder sb = new StringBuilder();
            String key = generateKey( kSize );
            int v = processKey( key );
            boolean add = true;
            int k = 0;

            for ( char c : string.toCharArray() ) {
                if ( add ) {
                    sb.append( ( char ) ( ( int ) c + v ) );
                    add = false;
                } else {
                    sb.append( ( char ) ( ( int ) c - v ) );
                    add = true;
                }
                if ( k < key.length() ) {
                    sb.append( key.toCharArray()[k] );
                    k++;
                }
            }

            sb.append( "@" + key.length() );

            return sb.toString();

        } else {
            return null;
        }
    }

    /**
     * Desencripta uma String.
     */
    public static String decrypt( String string ) {

        String key = getKey( string );
        int v = processKey( key );
        boolean add = true;
        String rawString = extractMetadata( string, key.length() );
        StringBuilder sb = new StringBuilder();

        for ( char c : rawString.toCharArray() ) {
            if ( add ) {
                sb.append( ( char ) ( ( int ) c - v ) );
                add = false;
            } else {
                sb.append( ( char ) ( ( int ) c + v ) );
                add = true;
            }
        }

        return sb.toString();

    }

    /*
     * Gera chave de encriptaÃ§Ã£o.
     */
    private static String generateKey( int size ) {
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < size; i++ )
            sb.append( ( char ) ( 33 + ( Math.random() * 92 ) ) );
        return sb.toString();
    }

    /*
     * A partir da chave, gera um nÃºmero usado para encriptar a String.
     */
    private static int processKey( String key ) {
        int v = 0;
        for ( char c : key.toCharArray() )
            v += Integer.parseInt( String.valueOf( String.valueOf( ( int ) c ).toCharArray()[0] ) );
        return v / ( ( int ) ( key.length() / 1.5 ) );
    }

    /*
     * ObtÃ©m a chave usada na String encriptada.
     */
    private static String getKey( String string ) {

        StringBuilder sb = new StringBuilder();
        int kSize = Integer.parseInt( string.substring( string.lastIndexOf( "@" ) + 1, string.length() ) );
        char[] chars = string.toCharArray();

        for ( int i = 0; i < kSize; i++ ) {
            sb.append( chars[ ( i * 2 ) + 1 ] );
        }

        return sb.toString();
    }

    /*
     * A partir de uma String encriptada e do tamanho da sua chave,
     * retira os dados da chave da String.
     */
    private static String extractMetadata( String string, int kSize ) {

        StringBuilder sb = new StringBuilder();
        char[] chars = string.toCharArray();
        int cont = 0;

        for ( int i = 0; i < chars.length; i++ ) {
            if ( cont < kSize ) {
                if ( i != ( cont * 2 ) + 1 ) {
                    sb.append( chars[ i ] );
                } else {
                    cont++;
                }
            } else {
                sb.append( chars[ i ] );
            }
        }

        String s = sb.toString();
        return s.substring( 0, s.lastIndexOf( "@" ) );

    }

}
