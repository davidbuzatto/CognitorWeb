/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.metametadata;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.comum.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Description da tag Date da tag Contribute de MetaMetadata.
 *
 * @author David Buzatto
 */
@Root( name = "description" )
@Entity
@Transactional
public class MetaMetadataContributeDateDescription implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "description" )
    private MetaMetadataContributeDate date;

    @ElementList( inline = true )
    @OneToMany
    private List< LangString > strings;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MetaMetadataContributeDate getDate() {
        return date;
    }

    public void setDate(MetaMetadataContributeDate date) {
        this.date = date;
    }

    public List< LangString > getStrings() {
        return strings;
    }

    public void setStrings(List< LangString > strings) {
        this.strings = strings;
    }

}
