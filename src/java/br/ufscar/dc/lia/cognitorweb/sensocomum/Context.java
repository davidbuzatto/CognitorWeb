/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.sensocomum;

/**
 * Classe Context para consulta na base de senso comum.
 *
 * @author David Buzatto
 */
public class Context {

    private String concept;
    private Double value;

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return concept + " (" + String.valueOf( ( int ) ( value * 100 ) ) + "%)";
    }

}
