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
 * Tag MetadataSchema de MetaMetadata.
 *
 * @author David Buzatto
 */
@Root( name = "metadataSchema" )
@Entity
@Transactional
public class MetaMetadataMetadataSchema implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private MetaMetadata metaMetadata;

    @Text
    @Column( name = "mvalue" )
    @Length( max = 30 )
    @NotNull
    private String value;

    public MetaMetadataMetadataSchema() {}

    public MetaMetadataMetadataSchema( String value ) {
        setValue( value );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MetaMetadata getMetaMetadata() {
        return metaMetadata;
    }

    public void setMetaMetadata(MetaMetadata metaMetadata) {
        this.metaMetadata = metaMetadata;
    }
    
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
