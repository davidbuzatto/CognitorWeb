/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados;

import br.ufscar.dc.lia.cognitorweb.entidades.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Classe que representa a tag principal dos metadados do SCORM.
 *
 * @author David Buzatto
 */
@Root
@Entity
@Transactional
public class Metadata implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Column( name = "mschema" )
    @Length( max = 100 )
    @NotNull
    private String schema;

    @Element( name = "schemaversion" )
    @Length( max = 100 )
    @NotNull
    private String schemaVersion;

    @OneToOne( mappedBy = "metadata" )
    private Material material;

    @OneToOne( mappedBy = "metadata" )
    private Pagina pagina;

    @OneToOne( mappedBy = "metadata" )
    private Conceito conceito;

    @OneToOne( mappedBy = "metadata" )
    private Grupo grupo;

    @OneToOne( mappedBy = "metadata" )
    private Imagem imagem;

    @OneToOne( mappedBy = "metadata" )
    private Video video;

    @OneToOne( mappedBy = "metadata" )
    private Som som;

    @Element
    @OneToOne
    @NotNull
    private Lom lom;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema( String schema ) {
        this.schema = schema;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion( String schemaVersion ) {
        this.schemaVersion = schemaVersion;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial( Material material ) {
        this.material = material;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public void setPagina( Pagina pagina ) {
        this.pagina = pagina;
    }

    public Conceito getConceito() {
        return conceito;
    }

    public void setConceito( Conceito conceito ) {
        this.conceito = conceito;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo( Grupo grupo ) {
        this.grupo = grupo;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem( Imagem imagem ) {
        this.imagem = imagem;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo( Video video ) {
        this.video = video;
    }

    public Som getSom() {
        return som;
    }

    public void setSom( Som som ) {
        this.som = som;
    }

    public Lom getLom() {
        return lom;
    }

    public void setLom(Lom lom) {
        this.lom = lom;
    }

}
