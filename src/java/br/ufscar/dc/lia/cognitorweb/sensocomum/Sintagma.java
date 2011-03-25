/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.sensocomum;

/**
 * Classe Sintagma para consulta na base de senso comum.
 *
 * @author David Buzatto
 */
public class Sintagma {

    private String kind;
    private String phrase;

    public Sintagma() {
    }

    public Sintagma(String kind, String phrase) {
        this.kind = kind;
        this.phrase = phrase;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }
    
}
