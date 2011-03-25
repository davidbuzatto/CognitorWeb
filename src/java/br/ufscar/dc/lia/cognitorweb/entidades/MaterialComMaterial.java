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
 * Classe para ligação entre materiais.
 *
 * @author David Buzatto
 */
@Entity
@Table( name = "Material_Material" )
@Transactional
public class MaterialComMaterial implements Serializable {

    @EmbeddedId
    protected MaterialComMaterialPK materialComMaterialPK;

    @NotNull
    private int ordem;

    @JoinColumn( name = "materialPrincipal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Material materialPrincipal;

    @JoinColumn( name = "materialQueContem_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Material materialQueContem;

    public MaterialComMaterial() {}

    public MaterialComMaterial( MaterialComMaterialPK materialComMaterialPK ) {
        this.materialComMaterialPK = materialComMaterialPK;
    }

    public MaterialComMaterial( MaterialComMaterialPK materialComMaterialPK, int ordem ) {
        this.materialComMaterialPK = materialComMaterialPK;
        this.ordem = ordem;
    }

    public MaterialComMaterial( long idMaterial1, long idMaterial2 ) {
        this.materialComMaterialPK = new MaterialComMaterialPK( idMaterial1, idMaterial2 );
    }
    
    public MaterialComMaterialPK getMaterialComMaterialPK() {
        return materialComMaterialPK;
    }

    public void setMaterialComMaterialPK( MaterialComMaterialPK materialComMaterialPK ) {
        this.materialComMaterialPK = materialComMaterialPK;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem( int ordem ) {
        this.ordem = ordem;
    }

    public Material getMaterialPrincipal() {
        return materialPrincipal;
    }

    public void setMaterialPrincipal( Material materialPrincipal ) {
        this.materialPrincipal = materialPrincipal;
    }

    public Material getMaterialQueContem() {
        return materialQueContem;
    }

    public void setMaterialQueContem( Material materialQueContem ) {
        this.materialQueContem = materialQueContem;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final MaterialComMaterial other = ( MaterialComMaterial ) obj;
        if ( this.materialComMaterialPK != other.materialComMaterialPK && ( this.materialComMaterialPK == null || !this.materialComMaterialPK.equals( other.materialComMaterialPK ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ( this.materialComMaterialPK != null ? this.materialComMaterialPK.hashCode() : 0 );
        return hash;
    }
    
}
