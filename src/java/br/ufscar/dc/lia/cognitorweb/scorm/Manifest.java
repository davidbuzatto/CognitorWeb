/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.scorm;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import org.simpleframework.xml.*;

/**
 * Classe que representa o nó raiz do arquivo de manifesto do SCORM.
 *
 * A implementação das tags do arquivo de manifesto é parcial, contemplando
 * apenas o modelo de agregação de conteúdo sem nenhuma ligação com as outras
 * especificações.
 *
 * A versão parcialmente implementada da especificação é a 2004 v4.
 * Para verificar o que foi feito ou não, compare a implementação com a
 * especificação - SCORM_2004_4ED_CAM_20090407.pdf
 *
 * @author David Buzatto
 */
@Root
public class Manifest {

    @Attribute
    private String identifier;

    @Attribute
    private String version;

    @Attribute( name = "xml:base" )
    private String base;

    @Attribute
    private String xmlns;

    @Attribute( name = "xmlns:adlcp" )
    private String adlcp;

    @Attribute( name = "xmlns:adlseq" )
    private String adlseq;

    @Attribute( name = "xmlns:xsi" )
    private String xsi;

    @Attribute( name = "xsi:schemaLocation" )
    private String schemaLocation;

    @Element( required = true )
    private Metadata metadata;

    public Manifest() {
        xmlns = "http://www.imsglobal.org/xsd/imscp_v1p1";
        adlcp = "http://www.adlnet.org/xsd/adlcp_v1p3";
        adlseq = "http://www.adlnet.org/xsd/adlseq_v1p3";
        xsi = "http://www.w3.org/2001/XMLSchema-instance";
        schemaLocation = "http://www.imsglobal.org/xsd/imscp_v1p1\n" +
                                      "imscp_v1p1.xsd\n" +
                                      "http://www.adlnet.org/xsd/adlcp_v1p3\n" +
                                      "adlcp_v1p3.xsd\n" +
                                      "http://www.adlnet.org/xsd/adlseq_v1p3\n" +
                                      "adlseq_v1p3.xsd\n" +
                                      "http://ltsc.ieee.org/xsd/LOM\n" +
                                      "lom.xsd\n";
    }

    @Element( required = true )
    private Organizations organizations;

    @Element( required = true )
    private Resources resources;

    public String getBase() {
        return base;
    }

    public void setBase( String base ) {
        this.base = base;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier( String identifier ) {
        this.identifier = identifier;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion( String version ) {
        this.version = version;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata( Metadata metadata ) {
        this.metadata = metadata;
    }

    public Organizations getOrganizations() {
        return organizations;
    }

    public void setOrganizations( Organizations organizations ) {
        this.organizations = organizations;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources( Resources resources ) {
        this.resources = resources;
    }
    
}
