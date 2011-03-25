/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.lifecycle;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Contribute de LifeCycle.
 *
 * @author David Buzatto
 */
@Root( name = "contribute" )
@Entity
@Transactional
public class LifeCycleContribute implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private LifeCycle lifeCycle;

    @Element( required = false )
    @OneToOne
    private LifeCycleContributeRole role;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "contribute" )
    @Size( min = 0, max = 40 )
    private List< LifeCycleContributeEntity > entities;

    @Element( required = false )
    @OneToOne
    private LifeCycleContributeDate date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(LifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public LifeCycleContributeRole getRole() {
        return role;
    }

    public void setRole(LifeCycleContributeRole role) {
        this.role = role;
    }

    public List<LifeCycleContributeEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<LifeCycleContributeEntity> entities) {
        this.entities = entities;
    }

    public LifeCycleContributeDate getDate() {
        return date;
    }

    public void setDate(LifeCycleContributeDate date) {
        this.date = date;
    }
    
}
