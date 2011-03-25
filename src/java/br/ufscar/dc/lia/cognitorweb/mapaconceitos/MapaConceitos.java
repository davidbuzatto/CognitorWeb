/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.mapaconceitos;

import org.simpleframework.xml.*;

/**
 * Classe que representa a raiz do mapa de conceitos.
 * É utilizada para deserialização.
 *
 * @author David Buzatto
 */
@Root
public class MapaConceitos {

    @Element
    private long idMaterial;

    @Element
    private ConceitosMapa conceitos;

    @Element( required = false )
    private RelacoesMapa relacoes;

    public long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial( long idMaterial ) {
        this.idMaterial = idMaterial;
    }

    public ConceitosMapa getConceitos() {
        return conceitos;
    }

    public void setConceitos( ConceitosMapa conceitos ) {
        this.conceitos = conceitos;
    }

    public RelacoesMapa getRelacoes() {
        return relacoes;
    }

    public void setRelacoes( RelacoesMapa relacoes ) {
        this.relacoes = relacoes;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append( "idMaterial: " + idMaterial + "\n" );

        sb.append( "----- conceitos: \n" );
        for ( ConceitoMapa c : conceitos.getConceitos() ) {
            sb.append( c + "\n" );
        }

        sb.append( "----- relações: \n" );
        for ( RelacaoMapa r : relacoes.getRelacoes() ) {
            sb.append( r + "\n" );
        }

        return sb.toString();

    }

}
