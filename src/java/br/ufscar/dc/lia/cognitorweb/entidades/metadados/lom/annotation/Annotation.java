/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.annotation;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Representa a categoria Annotation do IEEE LOM.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Annotation implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Lom lom;

    @Element( required = false )
    @OneToOne
    private AnnotationEntity entity;

    @Element( required = false )
    @OneToOne
    private AnnotationDate date;

    @Element( required = false )
    @OneToOne
    private AnnotationDescription description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lom getLom() {
        return lom;
    }

    public void setLom(Lom lom) {
        this.lom = lom;
    }

    public AnnotationEntity getEntity() {
        return entity;
    }

    public void setEntity(AnnotationEntity entity) {
        this.entity = entity;
    }

    public AnnotationDate getDate() {
        return date;
    }

    public void setDate(AnnotationDate date) {
        this.date = date;
    }

    public AnnotationDescription getDescription() {
        return description;
    }

    public void setDescription(AnnotationDescription description) {
        this.description = description;
    }

}
