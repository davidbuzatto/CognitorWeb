/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import javax.persistence.*;

/**
 * Classe para representar o identificador do relacionamento entre materiais.
 *
 * @author David Buzatto
 */
@Embeddable
public class MaterialComMaterialPK implements Serializable {

    @Basic( optional = false )
    @Column( name = "materialPrincipal_id", nullable = false )
    private long idMaterialPrincipal;
    
    @Basic( optional = false )
    @Column( name = "materialQueContem_id", nullable = false )
    private long idMaterialQueContem;

    public MaterialComMaterialPK() {}

    public MaterialComMaterialPK( long idMaterialPrincipal, long idMaterialQueContem ) {
        this.idMaterialPrincipal = idMaterialPrincipal;
        this.idMaterialQueContem = idMaterialQueContem;
    }

    public long getIdMaterialPrincipal() {
        return idMaterialPrincipal;
    }

    public void setIdMaterialPrincipal( long idMaterialPrincipal ) {
        this.idMaterialPrincipal = idMaterialPrincipal;
    }

    public long getIdMaterialQueContem() {
        return idMaterialQueContem;
    }

    public void setIdMaterialQueContem( long idMaterialQueContem ) {
        this.idMaterialQueContem = idMaterialQueContem;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final MaterialComMaterialPK other = ( MaterialComMaterialPK ) obj;
        if ( this.idMaterialPrincipal != other.idMaterialPrincipal ) {
            return false;
        }
        if ( this.idMaterialQueContem != other.idMaterialQueContem ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ( int )this.idMaterialPrincipal;
        hash = 97 * hash + ( int ) this.idMaterialQueContem;
        return hash;
    }

}
