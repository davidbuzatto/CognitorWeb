/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.scorm;

import java.util.*;
import org.simpleframework.xml.*;

/**
 * Tag resources.
 *
 * @author David Buzatto
 */
public class Resources {

    @ElementList( inline = true )
    private List< Resource > resources;

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources( List<Resource> resources ) {
        this.resources = resources;
    }

}
