/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.scorm;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import java.util.*;
import org.simpleframework.xml.*;

/**
 * Tag resource.
 *
 * @author David Buzatto
 */
public class Resource {

    public enum ScormType {

        SCO( "sco" ),
        ASSET( "asset" );

        private String descricao;

        ScormType( String descricao ) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @Attribute
    private String identifier;

    @Attribute
    private String type;

    @Attribute
    private String href;

    @Attribute( name = "xml:base" )
    private String base;

    @Attribute( name = "adlcp:scormType" )
    private String scormType;

    @ElementList( inline = true, required = false )
    private List<ResourceFile> files;

    @Element( required = false )
    private Metadata metadata;

    public Resource() {
        type = "webcontent";
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier( String identifier ) {
        this.identifier = identifier;
    }

    public String getType() {
        return type;
    }

    public String getHref() {
        return href;
    }

    public void setHref( String href ) {
        this.href = href;
    }

    public String getBase() {
        return base;
    }

    public void setBase( String base ) {
        this.base = base;
    }

    public String getScormType() {
        return scormType;
    }

    public void setScormType( ScormType scormType ) {
        this.scormType = scormType.getDescricao();
    }

    public List<ResourceFile> getFiles() {
        return files;
    }

    public void setFiles( List<ResourceFile> files ) {
        this.files = files;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata( Metadata metadata ) {
        this.metadata = metadata;
    }





}
