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
 * Classe para ligação entre conceitos e páginas.
 *
 * @author David Buzatto
 */
@Entity
@Table( name = "Conceito_Pagina" )
@Transactional
public class ConceitoComPagina implements Serializable {

    @EmbeddedId
    protected ConceitoComPaginaPK conceitoComPaginaPK;

    @NotNull
    private int ordem;

    @JoinColumn( name = "conceito_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Conceito conceito;

    @JoinColumn( name = "pagina_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Pagina pagina;

    public ConceitoComPagina() {}

    public ConceitoComPagina( ConceitoComPaginaPK conceitoComPaginaPK ) {
        this.conceitoComPaginaPK = conceitoComPaginaPK;
    }

    public ConceitoComPagina( ConceitoComPaginaPK conceitoComPaginaPK, int ordem ) {
        this.conceitoComPaginaPK = conceitoComPaginaPK;
        this.ordem = ordem;
    }

    public ConceitoComPagina( long idConceito, long idPagina ) {
        this.conceitoComPaginaPK = new ConceitoComPaginaPK( idConceito, idPagina );
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem( int ordem ) {
        this.ordem = ordem;
    }

    public ConceitoComPaginaPK getConceitoComPaginaPK() {
        return conceitoComPaginaPK;
    }

    public void setConceitoComPaginaPK( ConceitoComPaginaPK conceitoComPaginaPK ) {
        this.conceitoComPaginaPK = conceitoComPaginaPK;
    }

    public Conceito getConceito() {
        return conceito;
    }

    public void setConceito( Conceito conceito ) {
        this.conceito = conceito;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina( Pagina pagina ) {
        this.pagina = pagina;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final ConceitoComPagina other = ( ConceitoComPagina ) obj;
        if ( this.conceitoComPaginaPK != other.conceitoComPaginaPK && ( this.conceitoComPaginaPK == null || !this.conceitoComPaginaPK.equals( other.conceitoComPaginaPK ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ( this.conceitoComPaginaPK != null ? this.conceitoComPaginaPK.hashCode() : 0 );
        return hash;
    }
    
}
