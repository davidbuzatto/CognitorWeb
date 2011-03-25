/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.scorm;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import java.util.*;
import org.simpleframework.xml.*;

/**
 * Tag item.
 *
 * @author David Buzatto
 */
public class Item {

    @Attribute
    private String identifier;

    @Attribute( required = false )
    private String identifierref;

    @Element( required = true )
    private String title;

    @ElementList( inline = true, required = false )
    private List< Item > items;

    @Element( required = false )
    private Metadata metadata;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier( String identifier ) {
        this.identifier = identifier;
    }

    public String getIdentifierref() {
        return identifierref;
    }

    public void setIdentifierref( String identifierref ) {
        this.identifierref = identifierref;
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
