/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.classification;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Purpose de Classification.
 *
 * @author David Buzatto
 */
@Root( name = "purpose" )
@Entity
@Transactional
public class ClassificationPurpose implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 30 )
    @NotNull
    private String source;

    @Element
    @Column( name = "cvalue" )
    @Length( max = 30 )
    @NotNull
    private String value;

    @OneToOne( mappedBy = "purpose" )
    private Classification classification;

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

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

}
