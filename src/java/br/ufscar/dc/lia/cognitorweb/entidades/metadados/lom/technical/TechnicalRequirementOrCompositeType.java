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
 * Tag Type da tag OrComposite da tag Requeriment de Technical.
 *
 * @author David Buzatto
 */
@Root( name = "type" )
@Entity
@Transactional
public class TechnicalRequirementOrCompositeType implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 30 )
    @NotNull
    private String source;

    @Element
    @Column( name = "tvalue" )
    @Length( max = 30 )
    @NotNull
    private String value;

    @OneToOne( mappedBy = "type" )
    private TechnicalRequirementOrComposite orComposite;

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

    public TechnicalRequirementOrComposite getOrComposite() {
        return orComposite;
    }

    public void setOrComposite(TechnicalRequirementOrComposite orComposite) {
        this.orComposite = orComposite;
    }
    
}
