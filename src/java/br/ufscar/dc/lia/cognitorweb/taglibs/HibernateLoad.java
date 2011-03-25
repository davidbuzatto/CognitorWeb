/*
 * HibernateLoad.java
 *
 * Created on May 27, 2009, 9:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.taglibs;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.tag.common.core.*;

/**
 * Classe de taglib para carga de entidade do hibernate.
 *
 * @author David Buzatto
 */
public class HibernateLoad extends BodyTagSupport  {
    
    private int scope;
    private Object resultado = null;
    private String var;
    private long identificador;
    private String nomeDaClasse;
    
    public HibernateLoad() {
        scope = 1;
    }
    
    public void setVar( String valor ) {
        var = valor;
    }
    
    public void setClasse( String valor ) {
        nomeDaClasse = valor;
    }
    
    public void setIdentificador( String valor ) {
        this.identificador = Long.parseLong( valor );
    }
    
    public void setScope( String valor ){
        scope = Util.getScope( valor );
    }
    
    @Override
    public int doStartTag() throws JspException {
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        
        Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
        resultado = dao.getHibernateTemplate().find( "from " + nomeDaClasse + " where id = " + identificador ).get( 0 );
        
        pageContext.setAttribute( var, resultado, scope );
        
        return super.doEndTag();
        
    }
    
}