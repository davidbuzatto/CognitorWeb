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
 * Tag Duration de Techincal.
 *
 * @author David Buzatto
 */
@Root( name = "duration" )
@Entity
@Transactional
public class TechnicalDuration implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 30 )
    private String duration;

    @OneToOne( mappedBy = "duration" )
    private Technical technical;

    @Element
    @OneToOne
    private TechnicalDurationDescription description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }

    public TechnicalDurationDescription getDescription() {
        return description;
    }

    public void setDescription(TechnicalDurationDescription description) {
        this.description = description;
    }

}
