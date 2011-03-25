/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.util;

import br.ufscar.dc.lia.cognitorweb.entidades.*;
import java.io.*;
import java.util.*;

/**
 * Classe com métodos para a geração da lista de arquivos de um material.
 *
 * @author David Buzatto
 */
public class GeradorArquivosMaterial {

    /*
     * Gera a lista de arquivos de um tipo especificado.
     */
    public String gerarArquivosMaterial( Material m, String tipo,
            Map< String, String > configs ) {

        StringBuilder sb = new StringBuilder();

        sb.append( "[" );
        extrairDados( m, sb, tipo, configs );
        sb.append( "]" );

        return sb.toString();

    }

    /*
     * Extrai a lista de arquivos de um determinado tipo de um material.
     */
    private void extrairDados( Material m, StringBuilder sb, String tipo, Map< String, String > configs ) {

        String salvarEm = "";

        if ( tipo.equals( "imagem" ) )
            salvarEm = "/arquivos/imagens/";
        else if ( tipo.equals( "video" ) )
            salvarEm = "/arquivos/videos/";
        else if ( tipo.equals( "som" ) )
            salvarEm = "/arquivos/sons/";

        File arquivos = new File(
                configs.get( "repositorioArquivos" ) +
                m.getUsuario().getEmail() + "/" + m.getId() +
                salvarEm );

        for ( File f : arquivos.listFiles() ) {
            sb.append( "{ nome: '" + f.getName() + "' }," );
        }

    }

}
