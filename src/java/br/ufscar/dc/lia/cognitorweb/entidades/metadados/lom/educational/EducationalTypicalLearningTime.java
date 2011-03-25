/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.educational;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag TypicalLearningTime de Educational.
 *
 * @author David Buzatto
 */
@Root( name = "typicalLearningTime" )
@Entity
@Transactional
public class EducationalTypicalLearningTime implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 100 )
    @NotNull
    private String duration;

    @OneToOne( mappedBy = "typicalLearningTime" )
    private Educational educational;

    @ElementList( inline = true, required = false  )
    @OneToMany( mappedBy = "typicalLearningTime" )
    @Size( min = 0, max = 10 )
    private List< EducationalTypicalLearningTimeDescription > descriptions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Educational getEducational() {
        return educational;
    }

    public void setEducational(Educational educational) {
        this.educational = educational;
    }

    public List< EducationalTypicalLearningTimeDescription > getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List< EducationalTypicalLearningTimeDescription > descriptions) {
        this.descriptions = descriptions;
    }

}
