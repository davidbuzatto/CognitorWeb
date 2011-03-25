/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.annotation;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Date de Annotation.
 *
 * @author David Buzatto
 */
@Root( name = "date" )
@Entity
@Transactional
public class AnnotationDate implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 100 )
    @NotNull
    private String dateTime;

    @OneToOne( mappedBy = "date" )
    private Annotation annotation;

    @Element
    @OneToOne
    private AnnotationDateDescription description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public AnnotationDateDescription getDescription() {
        return description;
    }

    public void setDescription(AnnotationDateDescription description) {
        this.description = description;
    }

}
