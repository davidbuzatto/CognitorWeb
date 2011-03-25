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
 * Tag AggregationLevel de General.
 * 
 * @author David Buzatto
 */
@Root( name = "aggregationLevel" )
@Entity
@Transactional
public class GeneralAggregationLevel implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 30 )
    @NotNull
    private String source;

    @Element
    @Column( name = "gvalue" )
    @Length( max = 30 )
    @NotNull
    private String value;

    @OneToOne( mappedBy = "aggregationLevel" )
    private General general;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }
    
}
