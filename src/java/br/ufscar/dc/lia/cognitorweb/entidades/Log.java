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
 * Representação do log do usuário.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Log implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Temporal( TemporalType.TIMESTAMP )
    @NotNull
    @NotEmpty
    private Date data;

    @Length( max = 200 )
    @NotNull
    @NotEmpty
    private String tarefa;

    @ManyToOne
    @NotNull
    private Usuario usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public String getTarefa() {
        return tarefa;
    }

    public void setTarefa(String tarefa) {
        this.tarefa = tarefa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
