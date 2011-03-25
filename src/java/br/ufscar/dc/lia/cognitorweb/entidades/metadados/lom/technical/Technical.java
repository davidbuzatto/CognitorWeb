/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.technical;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Representa a categoria Technical do IEEE LOM.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Technical implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "technical" )
    private Lom lom;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "technical" )
    @Size( min = 0, max = 40 )
    private List< TechnicalFormat > formats;

    @Element( required = false )
    @Column( name = "tsize" )
    @Length( max = 30 )
    private String size;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "technical" )
    @Size( min = 0, max = 10 )
    private List< TechnicalLocation > locations;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "technical" )
    @Size( min = 0, max = 40 )
    private List< TechnicalRequirement > requirements;

    @Element( required = false )
    @OneToOne
    private TechnicalInstallationRemarks installationRemarks;

    @Element( required = false )
    @OneToOne
    private TechnicalOtherPlatformRequirements otherPlatformRequirements;

    @Element( required = false )
    @OneToOne
    private TechnicalDuration duration;

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

    public List<TechnicalFormat> getFormats() {
        return formats;
    }

    public void setFormats(List<TechnicalFormat> formats) {
        this.formats = formats;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<TechnicalLocation> getLocations() {
        return locations;
    }

    public void setLocations(List<TechnicalLocation> locations) {
        this.locations = locations;
    }

    public List<TechnicalRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<TechnicalRequirement> requirements) {
        this.requirements = requirements;
    }

    public TechnicalInstallationRemarks getInstallationRemarks() {
        return installationRemarks;
    }

    public void setInstallationRemarks(TechnicalInstallationRemarks installationRemarks) {
        this.installationRemarks = installationRemarks;
    }

    public TechnicalOtherPlatformRequirements getOtherPlatformRequirements() {
        return otherPlatformRequirements;
    }

    public void setOtherPlatformRequirements(TechnicalOtherPlatformRequirements otherPlatformRequirements) {
        this.otherPlatformRequirements = otherPlatformRequirements;
    }

    public TechnicalDuration getDuration() {
        return duration;
    }

    public void setDuration(TechnicalDuration duration) {
        this.duration = duration;
    }

}
