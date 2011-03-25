/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.scorm;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import java.util.*;
import org.simpleframework.xml.*;

/**
 * Tag organization.
 *
 * @author David Buzatto
 */
public class Organization {

    @Attribute
    private String identifier;

    @Attribute
    private String structure;

    @Attribute( name = "adlseq:objectivesGlobalToSystem" )
    private String objectivesGlobalToSystem;

    @Attribute( name = "adlcp:sharedDataGlobalToSystem" )
    private String sharedDataGlobalToSystem;

    @Element( required = true )
    private String title;

    @ElementList( inline = true )
    private List<Item> items;

    @Element( required = false )
    private Metadata metadata;

    public Organization() {
        structure = "hierarchical";
        objectivesGlobalToSystem = "false";
        sharedDataGlobalToSystem = "false";
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier( String identifier ) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems( List<Item> items ) {
        this.items = items;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata( Metadata metadata ) {
        this.metadata = metadata;
    }

}
