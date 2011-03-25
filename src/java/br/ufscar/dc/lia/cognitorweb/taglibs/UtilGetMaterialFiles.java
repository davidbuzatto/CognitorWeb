/*
 * HibernateQuery.java
 *
 * Created on May 27, 2009, 9:20
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.taglibs;

import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.util.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.tag.common.core.*;

/**
 * A partir de um material, obtem a lista de arquivos especificados.
 *
 * @author David Buzatto
 */
public class UtilGetMaterialFiles extends BodyTagSupport  {
    
    private int scope;
    private Material material;
    private String tipo;
    private Map< String, String > configs;

    private String var;
    
    public UtilGetMaterialFiles() {
        scope = 1;
    }

    public void setScope( String value ){
        scope = Util.getScope( value );
    }

    public void setMaterial( Material value ){
        material = value;
    }

    public void setConfigs( Map<String, String> configs ) {
        this.configs = configs;
    }

    public void setTipo( String tipo ) {
        this.tipo = tipo;
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

        GeradorArquivosMaterial gerador = new GeradorArquivosMaterial();

        pageContext.setAttribute(
                var,
                gerador.gerarArquivosMaterial( material, tipo, configs ),
                scope );
        return super.doEndTag();
        
    }
    
}