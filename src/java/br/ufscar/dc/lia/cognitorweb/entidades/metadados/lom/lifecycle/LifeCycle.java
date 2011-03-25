/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.lifecycle;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Representa a categoria Life Cycle do IEEE LOM.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class LifeCycle implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "lifeCycle" )
    private Lom lom;

    @Element( required = false )
    @OneToOne
    private LifeCycleVersion version;

    @Element( required = false )
    @OneToOne
    private LifeCycleStatus status;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "lifeCycle" )
    @Size( min = 0, max = 30 )
    private List< LifeCycleContribute > contributes;

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

    public LifeCycleVersion getVersion() {
        return version;
    }

    public void setVersion(LifeCycleVersion version) {
        this.version = version;
    }

    public LifeCycleStatus getStatus() {
        return status;
    }

    public void setStatus(LifeCycleStatus status) {
        this.status = status;
    }

    public List<LifeCycleContribute> getContributes() {
        return contributes;
    }

    public void setContributes(List<LifeCycleContribute> contributes) {
        this.contributes = contributes;
    }

}
