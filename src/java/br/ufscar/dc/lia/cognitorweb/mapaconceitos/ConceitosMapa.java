/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.mapaconceitos;

import java.util.*;
import org.simpleframework.xml.*;

/**
 * Conjunto de conceitos.
 *
 * @author David Buzatto
 */
@Root( name = "conceitos" )
public class ConceitosMapa {

    @ElementList( inline = true )
    private List< ConceitoMapa > conceitos;

    public List<ConceitoMapa> getConceitos() {
        return conceitos;
    }

    public void setConceitos( List<ConceitoMapa> conceitos ) {
        this.conceitos = conceitos;
    }

}
