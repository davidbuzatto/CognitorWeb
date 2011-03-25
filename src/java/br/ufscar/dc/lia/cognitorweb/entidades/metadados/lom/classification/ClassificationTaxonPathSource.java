/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.classification;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.comum.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Source da tag TaxonPath de Classification.
 *
 * @author David Buzatto
 */
@Root( name = "source" )
@Entity
@Transactional
public class ClassificationTaxonPathSource implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "source" )
    private ClassificationTaxonPath taxonPath;

    @ElementList( inline = true, required = false )
    @OneToMany
    private List< LangString > strings;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ClassificationTaxonPath getTaxonPath() {
        return taxonPath;
    }

    public void setTaxonPath(ClassificationTaxonPath taxonPath) {
        this.taxonPath = taxonPath;
    }

    public List< LangString > getStrings() {
        return strings;
    }

    public void setStrings(List< LangString > strings) {
        this.strings = strings;
    }

}
