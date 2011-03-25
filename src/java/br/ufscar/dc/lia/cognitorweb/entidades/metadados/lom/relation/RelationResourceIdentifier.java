/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.relation;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Identifier da tag Resource de Relation.
 *
 * @author David Buzatto
 */
@Root( name = "identifier" )
@Entity
@Transactional
public class RelationResourceIdentifier implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private RelationResource resource;

    @Element
    @Column( name = "rcatalog" )
    @Length( max = 1000 )
    @NotNull
    private String catalog;

    @Element
    @Length( max = 1000 )
    @NotNull
    private String entry;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RelationResource getResource() {
        return resource;
    }

    public void setResource(RelationResource resource) {
        this.resource = resource;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

}
