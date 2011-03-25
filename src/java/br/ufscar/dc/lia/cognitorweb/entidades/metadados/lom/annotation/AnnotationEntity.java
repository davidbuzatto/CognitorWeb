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
 * Tag Entity de Annotation.
 *
 * @author David Buzatto
 */
@Root( name = "entity" )
@Entity
@Transactional
public class AnnotationEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "entity" )
    private Annotation annotation;

    @Text
    @Column( name = "avalue" )
    @Length( max = 1000 )
    @NotNull
    private String value;

    public AnnotationEntity() {}

    public AnnotationEntity( String value ) {
        setValue( value );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
