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
 * Cidade de um usuário.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Cidade implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Length( max = 50 )
    @NotNull
    @NotEmpty
    private String nome;
    
    @ManyToOne
    @NotNull
    private Estado estado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
}
