/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.metametadata;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Representa a categoria Meta-Metadata do IEEE LOM.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class MetaMetadata implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "metaMetadata" )
    private Lom lom;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "metaMetadata" )
    @Size( min = 0, max = 10 )
    private List< MetaMetadataIdentifier > identifiers;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "metaMetadata" )
    @Size( min = 0, max = 10 )
    private List< MetaMetadataContribute > contributes;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "metaMetadata" )
    @Size( min = 0, max = 10 )
    private List< MetaMetadataMetadataSchema > metadataSchemas;

    @Element
    @Column( name = "mlanguage" )
    @Length( max = 100 )
    private String language;

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

    public List<MetaMetadataIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<MetaMetadataIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    public List<MetaMetadataContribute> getContributes() {
        return contributes;
    }

    public void setContributes(List<MetaMetadataContribute> contributes) {
        this.contributes = contributes;
    }

    public List<MetaMetadataMetadataSchema> getMetadataSchemas() {
        return metadataSchemas;
    }

    public void setMetadataSchemas(List<MetaMetadataMetadataSchema> metadataSchemas) {
        this.metadataSchemas = metadataSchemas;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
