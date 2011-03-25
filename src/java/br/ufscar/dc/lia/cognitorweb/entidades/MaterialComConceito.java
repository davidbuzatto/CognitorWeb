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
 * Classe para ligação entre materiais e conceitos.
 *
 * @author David Buzatto
 */
@Entity
@Table( name = "Material_Conceito" )
@Transactional
public class MaterialComConceito implements Serializable {

    @EmbeddedId
    protected MaterialComConceitoPK materialComConceitoPK;

    @NotNull
    private int ordem;

    @JoinColumn( name = "material_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Material material;

    @JoinColumn( name = "conceito_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Conceito conceito;

    public MaterialComConceito() {}

    public MaterialComConceito( MaterialComConceitoPK materialComConceitoPK ) {
        this.materialComConceitoPK = materialComConceitoPK;
    }

    public MaterialComConceito( MaterialComConceitoPK materialComConceitoPK, int ordem ) {
        this.materialComConceitoPK = materialComConceitoPK;
        this.ordem = ordem;
    }

    public MaterialComConceito( long idMaterial, long idConceito ) {
        this.materialComConceitoPK = new MaterialComConceitoPK( idMaterial, idConceito );
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem( int ordem ) {
        this.ordem = ordem;
    }

    public MaterialComConceitoPK getMaterialComConceitoPK() {
        return materialComConceitoPK;
    }

    public void setMaterialComConceitoPK( MaterialComConceitoPK materialComConceitoPK ) {
        this.materialComConceitoPK = materialComConceitoPK;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial( Material material ) {
        this.material = material;
    }

    public Conceito getConceito() {
        return conceito;
    }

    public void setConceito( Conceito conceito ) {
        this.conceito = conceito;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final MaterialComConceito other = ( MaterialComConceito ) obj;
        if ( this.materialComConceitoPK != other.materialComConceitoPK && ( this.materialComConceitoPK == null || !this.materialComConceitoPK.equals( other.materialComConceitoPK ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ( this.materialComConceitoPK != null ? this.materialComConceitoPK.hashCode() : 0 );
        return hash;
    }
    
}
