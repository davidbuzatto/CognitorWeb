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
 * Classe que mantem uma tabela de Strings para internacionalização.
 * Não está sendo utilizada. Está consifigurada para ser gerada, mas
 * o sistema de internacionalização está sendo feito através de resource
 * bundles.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class I18NTable implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Length( max = 10 )
    @NotNull
    @NotEmpty
    private String idioma;

    @Length( max = 50 )
    @NotNull
    @NotEmpty
    private String propriedade;

    @Length( max = 250 )
    @NotNull
    @NotEmpty
    private String valor;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma( String idioma ) {
        this.idioma = idioma;
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
