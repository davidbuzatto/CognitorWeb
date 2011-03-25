/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.mapaconceitos;

import java.util.*;
import org.simpleframework.xml.*;

/**
 * Conjunto de reclaoes.
 *
 * @author David Buzatto
 */
@Root( name = "relacoes" )
public class RelacoesMapa {

    @ElementList( inline = true )
    private List< RelacaoMapa > relacoes;

    public List<RelacaoMapa> getRelacoes() {
        return relacoes;
    }

    public void setRelacoes( List<RelacaoMapa> relacoes ) {
        this.relacoes = relacoes;
    }

}
