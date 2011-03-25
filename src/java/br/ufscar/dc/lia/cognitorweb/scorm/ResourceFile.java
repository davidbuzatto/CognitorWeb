/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.scorm;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import org.simpleframework.xml.*;

/**
 * Tag file.
 *
 * @author David Buzatto
 */
@Root( name = "file" )
public class ResourceFile {

    @Attribute
    private String href;

    @Element( required = false )
    private Metadata metadata;

    public String getHref() {
        return href;
    }

    public void setHref( String href ) {
        this.href = href;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata( Metadata metadata ) {
        this.metadata = metadata;
    }

}
