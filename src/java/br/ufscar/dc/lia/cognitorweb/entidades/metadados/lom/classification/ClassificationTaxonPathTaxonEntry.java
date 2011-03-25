/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.classification;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.comum.LangString;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Entry da tag Taxon da tag TaxonPath de Classification.
 *
 * @author David Buzatto
 */
@Root( name = "entry" )
@Entity
@Transactional
public class ClassificationTaxonPathTaxonEntry implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "entry" )
    private ClassificationTaxonPathTaxon taxon;

    @ElementList( inline = true, required = false )
    @OneToMany
    private List< LangString > strings;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClassificationTaxonPathTaxon getTaxon() {
        return taxon;
    }

    public void setTaxon(ClassificationTaxonPathTaxon taxon) {
        this.taxon = taxon;
    }

    public List< LangString > getStrings() {
        return strings;
    }

    public void setStrings(List< LangString > strings) {
        this.strings = strings;
    }

}
