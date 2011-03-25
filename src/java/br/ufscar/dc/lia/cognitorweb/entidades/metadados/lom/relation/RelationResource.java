/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.relation;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Resource de Relation.
 *
 * @author David Buzatto
 */
@Root( name = "resource" )
@Entity
@Transactional
public class RelationResource implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "resource" )
    private Relation relation;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "resource" )
    @Size( min = 0, max = 10 )
    private List< RelationResourceIdentifier > identifiers;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "resource" )
    @Size( min = 0, max = 10 )
    private List< RelationResourceDescription > descriptions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public List< RelationResourceIdentifier > getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List< RelationResourceIdentifier > identifiers) {
        this.identifiers = identifiers;
    }

    public List<RelationResourceDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<RelationResourceDescription> descriptions) {
        this.descriptions = descriptions;
    }
    
}
