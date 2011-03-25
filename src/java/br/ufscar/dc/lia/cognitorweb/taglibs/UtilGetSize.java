/*
 * HibernateQuery.java
 *
 * Created on May 27, 2009, 9:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.taglibs;

import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.tag.common.core.*;

/**
 * A partir de uma coleção, retorna seu tamanho na variável setada.
 *
 * @author David Buzatto
 */
public class UtilGetSize extends BodyTagSupport  {
    
    private int scope;
    private Collection collection;
    private String var;
    
    public UtilGetSize() {
        scope = 1;
    }

    public void setScope( String value ){
        scope = Util.getScope( value );
    }

    public void setCollection( Collection value ){
        collection = value;
    }

    public void setVar( String value ){
        var = value;
    }

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        
        pageContext.setAttribute( var, collection.size(), scope );
        return super.doEndTag();
        
    }
    
}