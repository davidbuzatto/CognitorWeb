/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import javax.persistence.*;

/**
 * Classe para representar o identificador do relacionamento entre conceitos.
 *
 * @author David Buzatto
 */
@Embeddable
public class ConceitoComConceitoPK implements Serializable {

    @Basic( optional = false )
    @Column( name = "conceitoPrincipal_id", nullable = false )
    private long idConceitoPrincipal;
    
    @Basic( optional = false )
    @Column( name = "conceitoQueContem_id", nullable = false )
    private long idConceitoQueContem;

    public ConceitoComConceitoPK() {}

    public ConceitoComConceitoPK( long idConceitoPrincipal, long idConceitoQueContem ) {
        this.idConceitoPrincipal = idConceitoPrincipal;
        this.idConceitoQueContem = idConceitoQueContem;
    }

    public long getIdConceitoPrincipal() {
        return idConceitoPrincipal;
    }

    public void setIdConceitoPrincipal( long idConceitoPrincipal ) {
        this.idConceitoPrincipal = idConceitoPrincipal;
    }

    public long getIdConceitoQueContem() {
        return idConceitoQueContem;
    }

    public void setIdConceitoQueContem( long idConceitoQueContem ) {
        this.idConceitoQueContem = idConceitoQueContem;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final ConceitoComConceitoPK other = ( ConceitoComConceitoPK ) obj;
        if ( this.idConceitoPrincipal != other.idConceitoPrincipal ) {
            return false;
        }
        if ( this.idConceitoQueContem != other.idConceitoQueContem ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + ( int )this.idConceitoPrincipal;
        hash = 97 * hash + ( int ) this.idConceitoQueContem;
        return hash;
    }

}
