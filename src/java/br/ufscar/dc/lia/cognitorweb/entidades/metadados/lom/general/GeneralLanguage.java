/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.general;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Language de General.
 *
 * @author David Buzatto
 */
@Root( name = "language" )
@Entity
@Transactional
public class GeneralLanguage implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private General general;

    @Text
    @Column( name = "gvalue" )
    @Length( max = 100 )
    @NotNull
    private String value;

    public GeneralLanguage() {}

    public GeneralLanguage( String value ) {
        setValue( value );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
