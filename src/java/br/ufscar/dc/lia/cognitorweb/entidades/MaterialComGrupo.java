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
 * Classe para ligação entre materiais e grupos.
 *
 * @author David Buzatto
 */
@Entity
@Table( name = "Material_Grupo" )
@Transactional
public class MaterialComGrupo implements Serializable {

    @EmbeddedId
    protected MaterialComGrupoPK materialComGrupoPK;

    @NotNull
    private int ordem;

    @JoinColumn( name = "material_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Material material;

    @JoinColumn( name = "grupo_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Grupo grupo;

    public MaterialComGrupo() {}

    public MaterialComGrupo( MaterialComGrupoPK materialComGrupoPK ) {
        this.materialComGrupoPK = materialComGrupoPK;
    }

    public MaterialComGrupo( MaterialComGrupoPK materialComGrupoPK, int ordem ) {
        this.materialComGrupoPK = materialComGrupoPK;
        this.ordem = ordem;
    }

    public MaterialComGrupo( long idMaterial, long idGrupo ) {
        this.materialComGrupoPK = new MaterialComGrupoPK( idMaterial, idGrupo );
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem( int ordem ) {
        this.ordem = ordem;
    }

    public MaterialComGrupoPK getMaterialComGrupoPK() {
        return materialComGrupoPK;
    }

    public void setMaterialComGrupoPK( MaterialComGrupoPK materialComGrupoPK ) {
        this.materialComGrupoPK = materialComGrupoPK;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial( Material material ) {
        this.material = material;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo( Grupo grupo ) {
        this.grupo = grupo;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final MaterialComGrupo other = ( MaterialComGrupo ) obj;
        if ( this.materialComGrupoPK != other.materialComGrupoPK && ( this.materialComGrupoPK == null || !this.materialComGrupoPK.equals( other.materialComGrupoPK ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ( this.materialComGrupoPK != null ? this.materialComGrupoPK.hashCode() : 0 );
        return hash;
    }
    
}
