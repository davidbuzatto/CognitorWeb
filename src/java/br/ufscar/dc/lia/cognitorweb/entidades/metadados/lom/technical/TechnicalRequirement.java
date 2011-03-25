/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.technical;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Requirement de Technical.
 *
 * @author David Buzatto
 */
@Root( name = "requirement" )
@Entity
@Transactional
public class TechnicalRequirement implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Technical technical;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "requirement" )
    @Size( min = 0, max = 40 )
    private List< TechnicalRequirementOrComposite > orComposites;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }

    public List< TechnicalRequirementOrComposite > getOrComposites() {
        return orComposites;
    }

    public void setOrComposites(List< TechnicalRequirementOrComposite > orComposites) {
        this.orComposites = orComposites;
    }

}
