/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import br.ufscar.dc.lia.cognitorweb.enumeracoes.*;
import br.ufscar.dc.lia.cognitorweb.util.CryptUtils;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.springframework.transaction.annotation.*;

/**
 * Usuário do sistema.
 *
 * @author David Buzatto
 */
@Entity
@Table( uniqueConstraints ={ @UniqueConstraint( columnNames = "email" ) } )
@Transactional
public class Usuario implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Length( max = 150 )
    @NotNull
    @NotEmpty
    private String email;

    /*
     * Tamanho mínimo: 8 carateres + 4 chave + 2 metadados da chave
     * Tamanho máximo: 16 carateres + 4 chave + 2 metadados da chave
     */
    @Length( min = 14, max = 22 )
    @NotNull
    @NotEmpty
    private String senha;

    @Length( max = 30 )
    @NotNull
    @NotEmpty
    private String primeiroNome;

    @Length( max = 30 )
    private String nomeMeio;

    @Length( max = 50 )
    @NotNull
    @NotEmpty
    private String ultimoNome;

    @Temporal( TemporalType.TIMESTAMP )
    @NotNull
    private Date dataNascimento;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Sexo sexo;

    @Length( max = 100 )
    private String rua;

    @Length( max = 5 )
    private String numero;

    @Length( max = 50 )
    private String complemento;

    @ManyToOne
    @NotNull
    private Pais pais;

    @ManyToOne
    private Estado estado;

    @ManyToOne
    private Cidade cidade;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Escolaridade escolaridade;

    @Length( max = 100 )
    private String ocupacao;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoUsuario tipo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSenha() {
        return CryptUtils.decrypt( senha );
    }

    public void setSenha(String senha) {
        this.senha = CryptUtils.encrypt( senha, 4 );
    }
    
    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getNomeMeio() {
        return nomeMeio;
    }

    public void setNomeMeio(String nomeMeio) {
        this.nomeMeio = nomeMeio;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String ultimoNome) {
        this.ultimoNome = ultimoNome;
    }
    
    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais( Pais pais ) {
        this.pais = pais;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado( Estado estado ) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Escolaridade getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(Escolaridade escolaridade) {
        this.escolaridade = escolaridade;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final Usuario other = ( Usuario ) obj;
        if ( this.id != other.id ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + ( int ) ( this.id ^ ( this.id >>> 32 ) );
        return hash;
    }

}
