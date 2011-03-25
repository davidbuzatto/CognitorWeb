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
 * Estado de uma cidade.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Estado implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Length( max = 50 )
    @NotNull
    @NotEmpty
    private String nome;

    @Length( max = 2 )
    @NotNull
    @NotEmpty
    private String sigla;

    @ManyToOne
    @NotNull
    private Pais pais;

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

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

}
