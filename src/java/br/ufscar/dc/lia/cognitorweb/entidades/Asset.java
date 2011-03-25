/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import javax.persistence.*;
import org.hibernate.validator.*;

/**
 * Asset de um material.
 *
 * @author David Buzatto
 */
@MappedSuperclass
public abstract class Asset extends ObjetoAprendizagem {

    @Length( max = 150 )
    @NotNull
    @NotEmpty
    private String nomeArquivo;

    // indica se um arquivo é externo ou não
    boolean externo;

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public boolean isExterno() {
        return externo;
    }

    public void setExterno( boolean externo ) {
        this.externo = externo;
    }

}
