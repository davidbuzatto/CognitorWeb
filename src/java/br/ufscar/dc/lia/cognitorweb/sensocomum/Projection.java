/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.sensocomum;

import java.util.*;

/**
 * Classe Projection para consulta na base de conhecimento de senso comum.
 *
 * @author David Buzatto
 */
public class Projection {

    private String name;
    private List<Context> contexts;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public List<Context> getContexts() {
        return contexts;
    }

    public void setContexts( List<Context> contexts ) {
        this.contexts = contexts;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        
        sb.append( name + "\n" );
        for ( Context c : contexts )
            sb.append( c + "\n" );

        return sb.toString();
    }
}
