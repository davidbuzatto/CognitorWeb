/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.classification;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag TaxonPath de Classification.
 *
 * @author David Buzatto
 */
@Root( name = "taxonPath" )
@Entity
@Transactional
public class ClassificationTaxonPath implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Classification classification;

    @Element
    @OneToOne
    private ClassificationTaxonPathSource source;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "taxonPath" )
    @Size( min = 0, max = 15 )
    private List< ClassificationTaxonPathTaxon > taxons;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public ClassificationTaxonPathSource getSource() {
        return source;
    }

    public void setSource(ClassificationTaxonPathSource source) {
        this.source = source;
    }

    public List<ClassificationTaxonPathTaxon> getTaxons() {
        return taxons;
    }

    public void setTaxons(List<ClassificationTaxonPathTaxon> taxons) {
        this.taxons = taxons;
    }

}
