/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.springframework.transaction.annotation.*;

/**
 * Classe para ligação entre grupos e páginas.
 *
 * @author David Buzatto
 */
@Entity
@Table( name = "Grupo_Pagina" )
@Transactional
public class GrupoComPagina implements Serializable {

    @EmbeddedId
    protected GrupoComPaginaPK grupoComPaginaPK;

    @NotNull
    private int ordem;

    @JoinColumn( name = "grupo_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Grupo grupo;

    @JoinColumn( name = "pagina_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Pagina pagina;

    public GrupoComPagina() {}

    public GrupoComPagina( GrupoComPaginaPK grupoComPaginaPK ) {
        this.grupoComPaginaPK = grupoComPaginaPK;
    }

    public GrupoComPagina( GrupoComPaginaPK grupoComPaginaPK, int ordem ) {
        this.grupoComPaginaPK = grupoComPaginaPK;
        this.ordem = ordem;
    }

    public GrupoComPagina( long idGrupo1, long idPagina ) {
        this.grupoComPaginaPK = new GrupoComPaginaPK( idGrupo1, idPagina );
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem( int ordem ) {
        this.ordem = ordem;
    }

    public GrupoComPaginaPK getGrupoComPaginaPK() {
        return grupoComPaginaPK;
    }

    public void setGrupoComPaginaPK( GrupoComPaginaPK grupoComPaginaPK ) {
        this.grupoComPaginaPK = grupoComPaginaPK;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo( Grupo grupo ) {
        this.grupo = grupo;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina( Pagina pagina ) {
        this.pagina = pagina;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final GrupoComPagina other = ( GrupoComPagina ) obj;
        if ( this.grupoComPaginaPK != other.grupoComPaginaPK && ( this.grupoComPaginaPK == null || !this.grupoComPaginaPK.equals( other.grupoComPaginaPK ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ( this.grupoComPaginaPK != null ? this.grupoComPaginaPK.hashCode() : 0 );
        return hash;
    }
    
}
