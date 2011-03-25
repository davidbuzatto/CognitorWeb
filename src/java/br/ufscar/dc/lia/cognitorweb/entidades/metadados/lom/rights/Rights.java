/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.rights;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import javax.persistence.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Representa a categoria Rights do IEEE LOM.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Rights implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "rights" )
    private Lom lom;

    @Element( required = false )
    @OneToOne
    private RightsCost cost;

    @Element( required = false )
    @OneToOne
    private RightsCopyrightAndOtherRestrictions copyrightAndOtherRestrictions;

    @Element( required = false )
    @OneToOne
    private RightsDescription description;

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

    public RightsCost getCost() {
        return cost;
    }

    public void setCost(RightsCost cost) {
        this.cost = cost;
    }

    public RightsCopyrightAndOtherRestrictions getCopyrightAndOtherRestrictions() {
        return copyrightAndOtherRestrictions;
    }

    public void setCopyrightAndOtherRestrictions(RightsCopyrightAndOtherRestrictions copyrightAndOtherRestrictions) {
        this.copyrightAndOtherRestrictions = copyrightAndOtherRestrictions;
    }

    public RightsDescription getDescription() {
        return description;
    }

    public void setDescription(RightsDescription description) {
        this.description = description;
    }

}
