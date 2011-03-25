/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.sensocomum;

import java.util.*;

/**
 * Classe Analogy para consulta na base de senso comum.
 *
 * @author David Buzatto
 */
public class Analogy {

    private String conceptBase;
    private String conceptTarget;
    private double weight;
    private List< AnalogyPart > analogyParts;

    public Analogy() {
        analogyParts = new ArrayList< AnalogyPart >();
    }

    public String getConceptBase() {
        return conceptBase;
    }

    public void setConceptBase( String conceptBase ) {
        this.conceptBase = conceptBase;
    }

    public String getConceptTarget() {
        return conceptTarget;
    }

    public void setConceptTarget( String conceptTarget ) {
        this.conceptTarget = conceptTarget;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight( double weight ) {
        this.weight = weight;
    }

    public List<AnalogyPart> getAnalogyParts() {
        return analogyParts;
    }

    public void setAnalogyParts( List<AnalogyPart> analogyParts ) {
        this.analogyParts = analogyParts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append( conceptBase + "\n" );
        sb.append( conceptTarget + "\n" );
        sb.append( weight + "\n" );

        for ( AnalogyPart p : analogyParts ) {
            sb.append( p.toString() );
        }

        return sb.toString();
    }

}
