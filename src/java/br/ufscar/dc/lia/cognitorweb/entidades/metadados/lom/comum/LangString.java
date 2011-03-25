/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.comum;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tipo LangString. Comum a vários campos do LOM.
 *
 * @author David Buzatto
 */
@Root( name = "string" )
@Entity
@Transactional
public class LangString implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Attribute
    @Column( name = "lslanguage" )
    @Length( max = 100 )
    @NotNull
    private String language;

    @Text
    @Column( name = "lsvalue" )
    @Length( max = 2000 )
    @NotNull
    private String value;

    public LangString() {}

    public LangString( String value ) {
        setValue( value );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
