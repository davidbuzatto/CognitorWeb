/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.springframework.transaction.annotation.*;

/**
 * Classe para ligação entre conceitos.
 *
 * Essa classe é utilizada para fazer o mapeamento entre conceitos não em
 * nível de hierarquia, mas sim em nível de ligações.
 *
 * Por exemplo:
 *
 * Animal
 *    Macaco
 *    Elefante
 *
 * Relacionamentos que DEVEM ser mapeados por essa classe:
 * Macaco -> Animal
 * Elefante -> Animal
 * Macaco -> Elefante
 * Elefante -> Macaco
 *
 * Outros possíveis relacionamentos como:
 * Animal -> Macaco
 * Animal -> Elefante
 *
 * Podem ser mapeados nessa classe, entretanto eles não serão parte da 
 * hierarquia. Caso seja necessário criar relacionamentos em nível de hierarquia,
 * a classe ConceitoComConceito deve ser utilizada.
 *
 * @author David Buzatto
 * @see ConceitoComConceito
 */
@Entity
@Transactional
public class RelacaoMapaConceito implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    // relação de minsky presente entre os conceitos
    @Length( max = 50 )
    private String relacaoMinsky;

    // relação inserida pelo usuário
    @Length( max = 120 )
    private String relacaoUsuario;

    @ManyToOne
    @NotNull
    private Conceito conceitoOrigem;

    @ManyToOne
    @NotNull
    private Conceito conceitoDestino;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getRelacaoMinsky() {
        return relacaoMinsky;
    }

    public void setRelacaoMinsky( String relacaoMinsky ) {
        this.relacaoMinsky = relacaoMinsky;
    }

    public String getRelacaoUsuario() {
        return relacaoUsuario;
    }

    public void setRelacaoUsuario( String relacaoUsuario ) {
        this.relacaoUsuario = relacaoUsuario;
    }

    public Conceito getConceitoOrigem() {
        return conceitoOrigem;
    }

    public void setConceitoOrigem( Conceito conceitoOrigem ) {
        this.conceitoOrigem = conceitoOrigem;
    }

    public Conceito getConceitoDestino() {
        return conceitoDestino;
    }

    public void setConceitoDestino( Conceito conceitoDestino ) {
        this.conceitoDestino = conceitoDestino;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final RelacaoMapaConceito other = ( RelacaoMapaConceito ) obj;
        if ( this.id != other.id ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + ( int ) ( this.id ^ ( this.id >>> 32 ) );
        return hash;
    }

}
