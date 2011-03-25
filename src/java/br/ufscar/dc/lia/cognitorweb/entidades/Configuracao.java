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
 * Entidade que mantém as configurações do sistema.
 *
 * @author David Buzatto
 */
@Entity
@Table( uniqueConstraints ={ @UniqueConstraint( columnNames = "propriedade" ) } )
@Transactional
public class Configuracao implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Length( max = 50 )
    @NotNull
    @NotEmpty
    private String propriedade;

    @Length( max = 200 )
    @NotNull
    @NotEmpty
    private String valor;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getPropriedade() {
        return propriedade;
    }

    public void setPropriedade( String propriedade ) {
        this.propriedade = propriedade;
    }

    public String getValor() {
        return valor;
    }

    public void setValor( String valor ) {
        this.valor = valor;
    }

}
