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
 * Classe para ligação entre grupos.
 *
 * @author David Buzatto
 */
@Entity
@Table( name = "Grupo_Grupo" )
@Transactional
public class GrupoComGrupo implements Serializable {

    @EmbeddedId
    protected GrupoComGrupoPK grupoComGrupoPK;

    @NotNull
    private int ordem;

    @JoinColumn( name = "grupoPrincipal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Grupo grupoPrincipal;

    @JoinColumn( name = "grupoQueContem_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Grupo grupoQueContem;

    public GrupoComGrupo() {}

    public GrupoComGrupo( GrupoComGrupoPK grupoComGrupoPK ) {
        this.grupoComGrupoPK = grupoComGrupoPK;
    }

    public GrupoComGrupo( GrupoComGrupoPK grupoComGrupoPK, int ordem ) {
        this.grupoComGrupoPK = grupoComGrupoPK;
        this.ordem = ordem;
    }

    public GrupoComGrupo( long idGrupo1, long idGrupo2 ) {
        this.grupoComGrupoPK = new GrupoComGrupoPK( idGrupo1, idGrupo2 );
    }
    
    public GrupoComGrupoPK getGrupoComGrupoPK() {
        return grupoComGrupoPK;
    }

    public void setGrupoComGrupoPK( GrupoComGrupoPK grupoComGrupoPK ) {
        this.grupoComGrupoPK = grupoComGrupoPK;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem( int ordem ) {
        this.ordem = ordem;
    }

    public Grupo getGrupoPrincipal() {
        return grupoPrincipal;
    }

    public void setGrupoPrincipal( Grupo grupoPrincipal ) {
        this.grupoPrincipal = grupoPrincipal;
    }

    public Grupo getGrupoQueContem() {
        return grupoQueContem;
    }

    public void setGrupoQueContem( Grupo grupoQueContem ) {
        this.grupoQueContem = grupoQueContem;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final GrupoComGrupo other = ( GrupoComGrupo ) obj;
        if ( this.grupoComGrupoPK != other.grupoComGrupoPK && ( this.grupoComGrupoPK == null || !this.grupoComGrupoPK.equals( other.grupoComGrupoPK ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ( this.grupoComGrupoPK != null ? this.grupoComGrupoPK.hashCode() : 0 );
        return hash;
    }
    
}
