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
 * País de um estado.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Pais implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Length( max = 50 )
    @NotNull
    @NotEmpty
    private String nome;

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

}
