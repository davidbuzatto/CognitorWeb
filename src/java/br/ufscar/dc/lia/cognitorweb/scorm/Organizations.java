/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.scorm;

import java.util.*;
import org.simpleframework.xml.*;

/**
 * Tag organizations.
 *
 * @author David Buzatto
 */
public class Organizations {

    @Attribute( name = "default" )
    private String defaultValue;

    @ElementList( inline = true )
    private List< Organization > organizations;

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue( String defaultValue ) {
        this.defaultValue = defaultValue;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations( List<Organization> organizations ) {
        this.organizations = organizations;
    }

}
