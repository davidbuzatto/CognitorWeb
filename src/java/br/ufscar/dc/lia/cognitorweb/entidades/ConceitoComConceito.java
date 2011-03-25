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
 * Classe para ligação entre conceitos.
 *
 * O mapeamento Conceito com Conceito é utilizado no nível de hierarquia do mapa
 * de conceitos, ou seja, o relacionamento mapeado por essa classe representa
 * o relacionamento de ligação de hierarquia entre os conceitos e não
 * outros relacionamentos que não sejam de hierarquia.
 *
 * Por exemplo:
 *
 * Animal
 *    Macaco
 *    Elefante
 *
 * Relacionamentos que DEVEM ser mapeados por essa classe:
 * Animal -> Macaco
 * Animal -> Elefante
 *
 * Outros possíveis relacionamentos como:
 * Macaco -> Animal
 * Elefante -> Animal
 * Macaco -> Elefante
 * Elefante -> Macaco
 *
 * Não devem ser mapeados dentro dessa classe, e sim em RelacaoMapaConceito.
 * Note que se houver necessidade de existir mais de uma relação Animal -> Macaco
 * por exemplo, sempre uma das relações será de hierarquia (ConceitoComConceito),
 * enquanto as outras serão em nível de ligação (RelacaoMapaConceito).
 *
 * @author David Buzatto
 * @see RelacaoMapaConceito
 */
@Entity
@Table( name = "Conceito_Conceito" )
@Transactional
public class ConceitoComConceito implements Serializable {

    @EmbeddedId
    protected ConceitoComConceitoPK conceitoComConceitoPK;

    @NotNull
    private int ordem;

    // relação de minsky presente entre os conceitos
    @Length( max = 50 )
    private String relacaoMinsky;

    // relação inserida pelo usuário
    @Length( max = 120 )
    private String relacaoUsuario;

    @JoinColumn( name = "conceitoPrincipal_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Conceito conceitoPrincipal;

    @JoinColumn( name = "conceitoQueContem_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false )
    @ManyToOne( optional = false )
    private Conceito conceitoQueContem;

    public ConceitoComConceito() {}

    public ConceitoComConceito( ConceitoComConceitoPK conceitoComConceitoPK ) {
        this.conceitoComConceitoPK = conceitoComConceitoPK;
    }

    public ConceitoComConceito( ConceitoComConceitoPK conceitoComConceitoPK, int ordem ) {
        this.conceitoComConceitoPK = conceitoComConceitoPK;
        this.ordem = ordem;
    }

    public ConceitoComConceito( long idConceito1, long idConceito2 ) {
        this.conceitoComConceitoPK = new ConceitoComConceitoPK( idConceito1, idConceito2 );
    }
    
    public ConceitoComConceitoPK getConceitoComConceitoPK() {
        return conceitoComConceitoPK;
    }

    public void setConceitoComConceitoPK( ConceitoComConceitoPK conceitoComConceitoPK ) {
        this.conceitoComConceitoPK = conceitoComConceitoPK;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem( int ordem ) {
        this.ordem = ordem;
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

    public Conceito getConceitoPrincipal() {
        return conceitoPrincipal;
    }

    public void setConceitoPrincipal( Conceito conceitoPrincipal ) {
        this.conceitoPrincipal = conceitoPrincipal;
    }

    public Conceito getConceitoQueContem() {
        return conceitoQueContem;
    }

    public void setConceitoQueContem( Conceito conceitoQueContem ) {
        this.conceitoQueContem = conceitoQueContem;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final ConceitoComConceito other = ( ConceitoComConceito ) obj;
        if ( this.conceitoComConceitoPK != other.conceitoComConceitoPK && ( this.conceitoComConceitoPK == null || !this.conceitoComConceitoPK.equals( other.conceitoComConceitoPK ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + ( this.conceitoComConceitoPK != null ? this.conceitoComConceitoPK.hashCode() : 0 );
        return hash;
    }
    
}
