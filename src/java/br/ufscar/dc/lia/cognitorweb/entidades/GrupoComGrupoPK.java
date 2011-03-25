/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import javax.persistence.*;

/**
 * Classe para representar o identificador do relacionamento entre grupos.
 *
 * @author David Buzatto
 */
@Embeddable
public class GrupoComGrupoPK implements Serializable {

    @Basic( optional = false )
    @Column( name = "grupoPrincipal_id", nullable = false )
    private long idGrupoPrincipal;
    
    @Basic( optional = false )
    @Column( name = "grupoQueContem_id", nullable = false )
    private long idGrupoQueContem;

    public GrupoComGrupoPK() {}

    public GrupoComGrupoPK( long idGrupoPrincipal, long idGrupoQueContem ) {
        this.idGrupoPrincipal = idGrupoPrincipal;
        this.idGrupoQueContem = idGrupoQueContem;
    }

    public long getIdGrupoPrincipal() {
        return idGrupoPrincipal;
    }

    public void setIdGrupoPrincipal( long idGrupoPrincipal ) {
        this.idGrupoPrincipal = idGrupoPrincipal;
    }

    public long getIdGrupoQueContem() {
        return idGrupoQueContem;
    }

    public void setIdGrupoQueContem( long idGrupoQueContem ) {
        this.idGrupoQueContem = idGrupoQueContem;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final GrupoComGrupoPK other = ( GrupoComGrupoPK ) obj;
        if ( this.idGrupoPrincipal != other.idGrupoPrincipal ) {
            return false;
        }
        if ( this.idGrupoQueContem != other.idGrupoQueContem ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ( int )this.idGrupoPrincipal;
        hash = 97 * hash + ( int ) this.idGrupoQueContem;
        return hash;
    }

}
