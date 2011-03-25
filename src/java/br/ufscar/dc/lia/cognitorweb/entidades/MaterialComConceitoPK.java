/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import javax.persistence.*;

/**
 * Classe para representar o identificador do relacionamento entre materiais e
 * conceitos.
 *
 * @author David Buzatto
 */
@Embeddable
public class MaterialComConceitoPK implements Serializable {

    @Basic( optional = false )
    @Column( name = "material_id", nullable = false )
    private long idMaterial;
    
    @Basic( optional = false )
    @Column( name = "conceito_id", nullable = false )
    private long idConceito;

    public MaterialComConceitoPK() {}

    public MaterialComConceitoPK( long idMaterial, long idConceito ) {
        this.idMaterial = idMaterial;
        this.idConceito = idConceito;
    }

    public long getIdMaterialPrincipal() {
        return idMaterial;
    }

    public void setIdMaterialPrincipal( long idMaterial ) {
        this.idMaterial = idMaterial;
    }

    public long getIdMaterialQueContem() {
        return idConceito;
    }

    public void setIdMaterialQueContem( long idConceito ) {
        this.idConceito = idConceito;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final MaterialComConceitoPK other = ( MaterialComConceitoPK ) obj;
        if ( this.idMaterial != other.idMaterial ) {
            return false;
        }
        if ( this.idConceito != other.idConceito ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ( int )this.idMaterial;
        hash = 97 * hash + ( int ) this.idConceito;
        return hash;
    }

}
