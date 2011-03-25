/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.classification;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Representa a categoria Classification do IEEE LOM.
 *
 * @author David Buzatto
 */
@Root( name = "" )
@Entity
@Transactional
public class Classification implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Lom lom;

    @Element( required = false )
    @OneToOne
    private ClassificationPurpose purpose;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "classification" )
    @Size( min = 0, max = 15 )
    private List< ClassificationTaxonPath > taxonPaths;

    @Element( required = false )
    @OneToOne
    private ClassificationDescription description;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "classification" )
    @Size( min = 0, max = 40 )
    private List< ClassificationKeyword > keywords;

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

    public ClassificationPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(ClassificationPurpose purpose) {
        this.purpose = purpose;
    }

    public List<ClassificationTaxonPath> getTaxonPaths() {
        return taxonPaths;
    }

    public void setTaxonPaths(List<ClassificationTaxonPath> taxonPaths) {
        this.taxonPaths = taxonPaths;
    }

    public ClassificationDescription getDescription() {
        return description;
    }

    public void setDescription(ClassificationDescription description) {
        this.description = description;
    }

    public List<ClassificationKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<ClassificationKeyword> keywords) {
        this.keywords = keywords;
    }

}
