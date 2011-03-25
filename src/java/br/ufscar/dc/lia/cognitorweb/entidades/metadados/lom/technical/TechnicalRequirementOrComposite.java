/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.technical;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag OrComposite da tag Requirement de Technical.
 *
 * @author David Buzatto
 */
@Root( name = "orComposite" )
@Entity
@Transactional
public class TechnicalRequirementOrComposite implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private TechnicalRequirement requirement;

    @Element
    @OneToOne
    private TechnicalRequirementOrCompositeType type;

    @Element
    @OneToOne
    private TechnicalRequirementOrCompositeName name;

    @Element
    @Length( max = 30 )
    private String minimumVersion;

    @Element
    @Length( max = 30 )
    private String maximumVersion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TechnicalRequirement getRequirement() {
        return requirement;
    }

    public void setRequirement(TechnicalRequirement requirement) {
        this.requirement = requirement;
    }

    public TechnicalRequirementOrCompositeType getType() {
        return type;
    }

    public void setType(TechnicalRequirementOrCompositeType type) {
        this.type = type;
    }

    public TechnicalRequirementOrCompositeName getName() {
        return name;
    }

    public void setName(TechnicalRequirementOrCompositeName name) {
        this.name = name;
    }

    public String getMinimumVersion() {
        return minimumVersion;
    }

    public void setMinimumVersion(String minimumVersion) {
        this.minimumVersion = minimumVersion;
    }

    public String getMaximumVersion() {
        return maximumVersion;
    }

    public void setMaximumVersion(String maximumVersion) {
        this.maximumVersion = maximumVersion;
    }

}
