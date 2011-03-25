/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lia.cognitorweb.util;

import java.io.*;

/**
 * Classe que contém métodos utilitários para manipulação de Strings.
 *
 * @author David Buzatto
 */
public class StringUtils {

    /**
     * Converte uma String codificada ISO-8859-1 para UTF-8.
     * 
     * @param isoString String codificada em ISO-8859-1
     * @return Uma String codificada em UTF-8
     */
    public static String toUTF8(String isoString) {

        String utf8String = null;

        if (null != isoString && !isoString.equals("")) {

            try {
                byte[] stringBytesISO = isoString.getBytes("ISO-8859-1");
                utf8String = new String(stringBytesISO, "UTF-8");
            } catch (UnsupportedEncodingException exc) {
                exc.printStackTrace();
                utf8String = isoString;
            }

        } else {

            utf8String = isoString;

        }

        return utf8String;

    }

    /**7
     * Converte uma String codificada UTF-48 parar ISO-8859-1.
     *
     * @param isoString String codificada em UTF-8
     * @return Uma String codificada em ISO-8859-1
     */
    public static String toISO(String utfString) {

        String isoString = null;

        if (null != utfString && !utfString.equals("")) {

            try {
                byte[] stringBytesUTF = utfString.getBytes("UTF-8");
                isoString = new String(stringBytesUTF, "ISO-8859-1");
            } catch (UnsupportedEncodingException exc) {
                exc.printStackTrace();
                isoString = utfString;
            }

        } else {

            isoString = utfString;

        }

        return isoString;

    }
}
