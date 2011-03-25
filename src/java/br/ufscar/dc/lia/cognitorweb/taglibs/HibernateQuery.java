/*
 * HibernateQuery.java
 *
 * Created on May 27, 2009, 9:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.taglibs;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.tag.common.core.*;

/**
 * Classe de taglib para consultas usando hibernate.
 *
 * @author David Buzatto
 */
public class HibernateQuery extends BodyTagSupport  {
    
    private int scope;
    private List resultado = new ArrayList();
    private String var;
    
    public HibernateQuery() {
        scope = 1;
    }
    
    public void setVar( String value ){
        var = value;
    }
    
    public void setScope( String value ){
        scope = Util.getScope( value );
    }

    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        
        String corpoTag = bodyContent.getString();
            
        Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
        resultado = dao.getHibernateTemplate().find( corpoTag );
        
        pageContext.setAttribute( var, resultado, scope );
        
        return super.doEndTag();
        
    }
    
}