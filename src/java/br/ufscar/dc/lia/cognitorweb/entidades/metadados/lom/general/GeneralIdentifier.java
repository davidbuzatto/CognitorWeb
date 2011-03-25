/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.general;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Identifier de General.
 * 
 * @author David Buzatto
 */
@Root( name = "identifier" )
@Entity
@Transactional
public class GeneralIdentifier implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private General general;

    @Element
    @Column( name = "gcatalog" )
    @Length( max = 1000 )
    @NotNull
    private String catalog;

    @Element
    @Length( max = 1000 )
    @NotNull
    private String entry;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

}
