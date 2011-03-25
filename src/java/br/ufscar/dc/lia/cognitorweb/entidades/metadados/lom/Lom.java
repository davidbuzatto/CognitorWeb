/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.annotation.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.classification.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.educational.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.general.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.lifecycle.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.metametadata.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.relation.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.rights.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.technical.Technical;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Classe que representa o conjunto de metadados IEEE LOM.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Lom implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "lom" )
    private Metadata metadata;

    @Attribute
    @Length( max = 200 )
    @NotNull
    private String xmlns;

    @Element( required = false )
    @OneToOne
    private General general;

    @Element( required = false )
    @OneToOne
    private LifeCycle lifeCycle;

    @Element( required = false )
    @OneToOne
    private MetaMetadata metaMetadata;

    @Element( required = false )
    @OneToOne
    private Technical technical;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "lom" )
    @Size( min = 0, max = 100 )
    private List< Educational > educationals;

    @Element( required = false )
    @OneToOne
    private Rights rights;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "lom" )
    @Size( min = 0, max = 100 )
    private List< Relation > relations;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "lom" )
    @Size( min = 0, max = 30 )
    private List< Annotation > annotations;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "lom" )
    @Size( min = 0, max = 40 )
    private List< Classification > classifications;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns( String xmlns ) {
        this.xmlns = xmlns;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(LifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public MetaMetadata getMetaMetadata() {
        return metaMetadata;
    }

    public void setMetaMetadata(MetaMetadata metaMetadata) {
        this.metaMetadata = metaMetadata;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }

    public List< Educational > getEducationals() {
        return educationals;
    }

    public void setEducationals(List< Educational > educationals) {
        this.educationals = educationals;
    }

    public Rights getRights() {
        return rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }

    public List< Relation > getRelations() {
        return relations;
    }

    public void setRelations(List< Relation > relations) {
        this.relations = relations;
    }

    public List< Annotation > getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List< Annotation > annotations) {
        this.annotations = annotations;
    }

    public List< Classification > getClassifications() {
        return classifications;
    }

    public void setClassifications(List< Classification > classifications) {
        this.classifications = classifications;
    }

}
