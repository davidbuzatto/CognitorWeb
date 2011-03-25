/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import javax.persistence.*;

/**
 * Classe para representar o identificador do relacionamento entre materiais e
 * grupos.
 *
 * @author David Buzatto
 */
@Embeddable
public class MaterialComGrupoPK implements Serializable {

    @Basic( optional = false )
    @Column( name = "material_id", nullable = false )
    private long idMaterial;
    
    @Basic( optional = false )
    @Column( name = "grupo_id", nullable = false )
    private long idGrupo;

    public MaterialComGrupoPK() {}

    public MaterialComGrupoPK( long idMaterial, long idGrupo ) {
        this.idMaterial = idMaterial;
        this.idGrupo = idGrupo;
    }

    public long getIdMaterialPrincipal() {
        return idMaterial;
    }

    public void setIdMaterialPrincipal( long idMaterial ) {
        this.idMaterial = idMaterial;
    }

    public long getIdMaterialQueContem() {
        return idGrupo;
    }

    public void setIdMaterialQueContem( long idGrupo ) {
        this.idGrupo = idGrupo;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final MaterialComGrupoPK other = ( MaterialComGrupoPK ) obj;
        if ( this.idMaterial != other.idMaterial ) {
            return false;
        }
        if ( this.idGrupo != other.idGrupo ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ( int )this.idMaterial;
        hash = 97 * hash + ( int ) this.idGrupo;
        return hash;
    }

}
