/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;

/**
 * Classe que representa um objeto de aprendizagem.
 * Serve como base para as outras classes.
 *
 * @author David Buzatto
 */
@MappedSuperclass
public abstract class ObjetoAprendizagem implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Length( max = 100 )
    @NotNull
    @NotEmpty
    private String titulo;

    @Temporal( TemporalType.TIMESTAMP )
    @NotNull
    private Date dataCriacao;

    @Temporal( TemporalType.TIMESTAMP )
    @NotNull
    private Date dataAtualizacao;

    private boolean compartilhado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
    
    public boolean isCompartilhado() {
        return compartilhado;
    }

    public void setCompartilhado(boolean compartilhado) {
        this.compartilhado = compartilhado;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final ObjetoAprendizagem other = ( ObjetoAprendizagem ) obj;
        if ( this.id != other.id ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + ( int ) ( this.id ^ ( this.id >>> 32 ) );
        return hash;
    }

}
