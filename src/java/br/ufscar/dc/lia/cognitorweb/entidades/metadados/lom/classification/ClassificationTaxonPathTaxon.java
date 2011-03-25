/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.classification;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Taxon da tag TaxonPath de Classification.
 *
 * Observação: A tag taxon possum uma subtag denominada id, sendo assim
 * a chave primária dessa classe se chama taxonId, ao contrário das outras classes
 * onde a chave primária é definida pelo campo id.
 * 
 * @author David Buzatto
 */
@Root( name = "taxon" )
@Entity
@Transactional
public class ClassificationTaxonPathTaxon implements Serializable {

    @Id
    @GeneratedValue
    private long taxonId;

    @ManyToOne
    @NotNull
    private ClassificationTaxonPath taxonPath;

    @Element
    @Length( max = 100 )
    @NotNull
    private String id;

    @Element
    @OneToOne
    private ClassificationTaxonPathTaxonEntry entry;

    public long getTaxonId() {
        return taxonId;
    }

    public void setTaxonId(long taxonId) {
        this.taxonId = taxonId;
    }

    public ClassificationTaxonPath getTaxonPath() {
        return taxonPath;
    }

    public void setTaxonPath(ClassificationTaxonPath taxonPath) {
        this.taxonPath = taxonPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public ClassificationTaxonPathTaxonEntry getEntry() {
        return entry;
    }

    public void setEntry(ClassificationTaxonPathTaxonEntry entry) {
        this.entry = entry;
    }

}
