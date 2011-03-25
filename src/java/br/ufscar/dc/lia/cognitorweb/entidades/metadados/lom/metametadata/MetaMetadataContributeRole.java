/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.metametadata;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Role da tag Contribute de MetaMetadata.
 *
 * @author David Buzatto
 */
@Root( name = "role" )
@Entity
@Transactional
public class MetaMetadataContributeRole implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 30 )
    @NotNull
    private String source;

    @Element
    @Column( name = "mvalue" )
    @Length( max = 30 )
    @NotNull
    private String value;

    @OneToOne( mappedBy = "role" )
    private MetaMetadataContribute contribute;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MetaMetadataContribute getContribute() {
        return contribute;
    }

    public void setContribute(MetaMetadataContribute contribute) {
        this.contribute = contribute;
    }

}
