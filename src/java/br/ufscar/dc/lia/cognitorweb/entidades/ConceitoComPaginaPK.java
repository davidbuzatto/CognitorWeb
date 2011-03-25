/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import javax.persistence.*;

/**
 * Classe para representar o identificador do relacionamento entre conceitos e
 * páginas.
 *
 * @author David Buzatto
 */
@Embeddable
public class ConceitoComPaginaPK implements Serializable {

    @Basic( optional = false )
    @Column( name = "conceito_id", nullable = false )
    private long idConceito;
    
    @Basic( optional = false )
    @Column( name = "pagina_id", nullable = false )
    private long idPagina;

    public ConceitoComPaginaPK() {}

    public ConceitoComPaginaPK( long idConceito, long idPagina ) {
        this.idConceito = idConceito;
        this.idPagina = idPagina;
    }

    public long getIdConceitoPrincipal() {
        return idConceito;
    }

    public void setIdConceitoPrincipal( long idConceito ) {
        this.idConceito = idConceito;
    }

    public long getIdConceitoQueContem() {
        return idPagina;
    }

    public void setIdConceitoQueContem( long idPagina ) {
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
        final ConceitoComPaginaPK other = ( ConceitoComPaginaPK ) obj;
        if ( this.idConceito != other.idConceito ) {
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
        hash = 97 * hash + ( int )this.idConceito;
        hash = 97 * hash + ( int ) this.idPagina;
        return hash;
    }

}
