/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import javax.persistence.*;

/**
 * Classe para representar o identificador do relacionamento entre grupos e
 * páginas.
 *
 * @author David Buzatto
 */
@Embeddable
public class GrupoComPaginaPK implements Serializable {

    @Basic( optional = false )
    @Column( name = "grupo_id", nullable = false )
    private long idGrupo;
    
    @Basic( optional = false )
    @Column( name = "pagina_id", nullable = false )
    private long idPagina;

    public GrupoComPaginaPK() {}

    public GrupoComPaginaPK( long idGrupo, long idPagina ) {
        this.idGrupo = idGrupo;
        this.idPagina = idPagina;
    }

    public long getIdGrupoPrincipal() {
        return idGrupo;
    }

    public void setIdGrupoPrincipal( long idGrupo ) {
        this.idGrupo = idGrupo;
    }

    public long getIdGrupoQueContem() {
        return idPagina;
    }

    public void setIdGrupoQueContem( long idPagina ) {
        this.idPagina = idPagina;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final GrupoComPaginaPK other = ( GrupoComPaginaPK ) obj;
        if ( this.idGrupo != other.idGrupo ) {
            return false;
        }
        if ( this.idPagina != other.idPagina ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ( int )this.idGrupo;
        hash = 97 * hash + ( int ) this.idPagina;
        return hash;
    }

}
