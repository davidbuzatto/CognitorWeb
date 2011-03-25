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
 * Tag Entity da tag Contribute de MetaMetadata.
 *
 * @author David Buzatto
 */
@Root( name = "entity" )
@Entity
@Transactional
public class MetaMetadataContributeEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private MetaMetadataContribute contribute;

    @Text
    @Column( name = "mvalue" )
    @Length( max = 1000 )
    @NotNull
    private String value;

    public MetaMetadataContributeEntity() {}

    public MetaMetadataContributeEntity( String value ) {
        setValue( value );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MetaMetadataContribute getContribute() {
        return contribute;
    }

    public void setContribute(MetaMetadataContribute contribute) {
        this.contribute = contribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
