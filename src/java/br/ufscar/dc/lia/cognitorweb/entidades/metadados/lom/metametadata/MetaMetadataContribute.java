/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.metametadata;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Contribute de MetaMetadata.
 *
 * @author David Buzatto
 */
@Root( name = "contribute" )
@Entity
@Transactional
public class MetaMetadataContribute implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private MetaMetadata metaMetadata;

    @Element( required = false )
    @OneToOne
    private MetaMetadataContributeRole role;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "contribute" )
    @Size( min = 0, max = 10 )
    private List< MetaMetadataContributeEntity > entities;

    @Element( required = false )
    @OneToOne
    private MetaMetadataContributeDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MetaMetadata getMetaMetadata() {
        return metaMetadata;
    }

    public void setMetaMetadata(MetaMetadata metaMetadata) {
        this.metaMetadata = metaMetadata;
    }

    public MetaMetadataContributeRole getRole() {
        return role;
    }

    public void setRole(MetaMetadataContributeRole role) {
        this.role = role;
    }

    public List<MetaMetadataContributeEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<MetaMetadataContributeEntity> entities) {
        this.entities = entities;
    }

    public MetaMetadataContributeDate getDate() {
        return date;
    }

    public void setDate(MetaMetadataContributeDate date) {
        this.date = date;
    }

}
