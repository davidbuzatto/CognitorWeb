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
 * A partir de um material gera os OAs da sua estrutura em JSON.
 *
 * @author David Buzatto
 */
public class UtilGetMaterialLOs extends BodyTagSupport  {
    
    private int scope;
    private Material material;
    private String iconeGrupo;
    private String iconePagina;
    private String iconeImagem;
    private String iconeVideo;
    private String iconeSom;

    private String var;
    
    public UtilGetMaterialLOs() {
        scope = 1;
    }

    public void setScope( String value ){
        scope = Util.getScope( value );
    }

    public void setMaterial( Material value ){
        material = value;
    }

    public void setIconeGrupo( String iconeGrupo ) {
        this.iconeGrupo = iconeGrupo;
    }

    public void setIconePagina( String value ) {
        iconePagina = value;
    }

    public void setIconeImagem( String iconeImagem ) {
        this.iconeImagem = iconeImagem;
    }

    public void setIconeVideo( String iconeVideo ) {
        this.iconeVideo = iconeVideo;
    }

    public void setIconeSom( String iconeSom ) {
        this.iconeSom = iconeSom;
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

        GeradorOAsMaterial gerador = new GeradorOAsMaterial(
                iconeGrupo,
                iconePagina,
                iconeImagem,
                iconeVideo,
                iconeSom );

        pageContext.setAttribute(
                var,
                gerador.gerarOAsMaterial( material ),
                scope );
        return super.doEndTag();
        
    }
    
}