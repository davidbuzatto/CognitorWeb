/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.sensocomum;

/**
 * Classe Relation para consulta na base de senso comum.
 *
 * @author David Buzatto
 */
public class Relation {

    private String predicate;
    private String concept1;
    private String concept2;
    private int frequency;
    private int inference;

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getConcept1() {
        return concept1;
    }

    public void setConcept1(String concept1) {
        this.concept1 = concept1;
    }

    public String getConcept2() {
        return concept2;
    }

    public void setConcept2(String concept2) {
        this.concept2 = concept2;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getInference() {
        return inference;
    }

    public void setInference(int inference) {
        this.inference = inference;
    }

    @Override
    public String toString() {

        return predicate + " - " + concept1 + ", " + concept2 +
                " (f=" + frequency + ", i=" + inference + ")";

    }
    
}
