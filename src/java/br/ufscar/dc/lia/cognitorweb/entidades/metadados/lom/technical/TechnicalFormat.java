/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.technical;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Format de Technical.
 *
 * @author David Buzatto
 */
@Root( name = "format" )
@Entity
@Transactional
public class TechnicalFormat implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Technical technical;

    @Text
    @Column( name = "tvalue" )
    @Length( max = 500 )
    @NotNull
    private String value;

    public TechnicalFormat() {}

    public TechnicalFormat( String value ) {
        setValue( value );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
