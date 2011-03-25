/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.mapaconceitos;

import org.simpleframework.xml.*;

/**
 * Representa uma relação do mapa de conceitos.
 * Vale notar que:
 *     C1 representa o conceito de origem
 *     C2 representa o conceito de destino
 * 
 * @author David Buzatto
 */
@Root( name = "relacao" )
public class RelacaoMapa {

    @Element
    private long idC1;

    @Element
    private long idC2;

    @Element
    private String tituloC1;

    @Element
    private String tituloC2;

    @Element( required = false )
    private String relacaoMinsky;

    @Element
    private String relacaoUsuario;

    @Element
    private boolean hierarquia; // indica se é uma relação da hierarquia

    public long getIdC1() {
        return idC1;
    }

    public void setIdC1( long idC1 ) {
        this.idC1 = idC1;
    }

    public long getIdC2() {
        return idC2;
    }

    public void setIdC2( long idC2 ) {
        this.idC2 = idC2;
    }

    public String getTituloC1() {
        return tituloC1;
    }

    public void setTituloC1( String tituloC1 ) {
        this.tituloC1 = tituloC1;
    }

    public String getTituloC2() {
        return tituloC2;
    }

    public void setTituloC2( String tituloC2 ) {
        this.tituloC2 = tituloC2;
    }

    public String getRelacaoMinsky() {
        return relacaoMinsky;
    }

    public void setRelacaoMinsky( String relacaoMinsky ) {
        this.relacaoMinsky = relacaoMinsky;
    }

    public String getRelacaoUsuario() {
        return relacaoUsuario;
    }

    public void setRelacaoUsuario( String relacaoUsuario ) {
        this.relacaoUsuario = relacaoUsuario;
    }

    public boolean isHierarquia() {
        return hierarquia;
    }

    public void setHierarquia( boolean hierarquia ) {
        this.hierarquia = hierarquia;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append( "idC1: " + idC1 + ", tituloConceito1: " + tituloC1 +
                ", idC2: " + idC2 + ", tituloConceito2: " + tituloC2 +
                ", relacaoMinsky: " + relacaoMinsky +
                ", relacaoUsuario: " + relacaoUsuario +
                ", hierarquia: " + hierarquia );

        return sb.toString();

    }

}
