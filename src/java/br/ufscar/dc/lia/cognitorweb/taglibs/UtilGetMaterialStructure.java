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
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import org.apache.taglibs.standard.tag.common.core.*;

/**
 * A partir de um material gera a sua estrutura em JSON.
 *
 * @author David Buzatto
 */
public class UtilGetMaterialStructure extends BodyTagSupport  {
    
    private int scope;
    private Material material;
    private String iconeMaterial;
    private String iconeConceito;
    private String iconeGrupo;
    private String iconePagina;
    private String var;
    
    public UtilGetMaterialStructure() {
        scope = 1;
    }

    public void setScope( String value ){
        scope = Util.getScope( value );
    }

    public void setMaterial( Material value ){
        material = value;
    }

    public void setIconeMaterial( String value ) {
        iconeMaterial = value;
    }

    public void setIconeConceito( String value ) {
        iconeConceito = value;
    }

    public void setIconeGrupo( String value ) {
        iconeGrupo = value;
    }

    public void setIconePagina( String value ) {
        iconePagina = value;
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

        GeradorEstruturaMaterial gerador = new GeradorEstruturaMaterial(
                iconeMaterial, iconeConceito, iconeGrupo, iconePagina );

        pageContext.setAttribute(
                var,
                gerador.gerarEstruturaMaterial( material ),
                scope );
        return super.doEndTag();
        
    }
    
}