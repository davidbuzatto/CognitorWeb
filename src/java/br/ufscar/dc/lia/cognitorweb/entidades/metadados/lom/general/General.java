/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.general;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Representa a categoria General do IEEE LOM.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class General implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne( mappedBy = "general" )
    private Lom lom;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "general" )
    @Size( min = 0, max = 10 )
    private List< GeneralIdentifier > identifiers;

    @Element( required = false )
    @OneToOne
    private GeneralTitle title;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "general" )
    @Size( min = 0, max = 10 )
    private List< GeneralLanguage > languages;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "general" )
    @Size( min = 0, max = 10 )
    private List< GeneralDescription > descriptions;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "general" )
    @Size( min = 0, max = 10 )
    private List< GeneralKeyword > keywords;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "general" )
    @Size( min = 0, max = 10 )
    private List< GeneralCoverage > coverages;

    @Element( required = false )
    @OneToOne
    private GeneralStructure structure;

    @Element( required = false )
    @OneToOne
    private GeneralAggregationLevel aggregationLevel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Lom getLom() {
        return lom;
    }

    public void setLom(Lom lom) {
        this.lom = lom;
    }

    public List<GeneralIdentifier> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(List<GeneralIdentifier> identifiers) {
        this.identifiers = identifiers;
    }

    public GeneralTitle getTitle() {
        return title;
    }

    public void setTitle(GeneralTitle title) {
        this.title = title;
    }

    public List<GeneralLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<GeneralLanguage> languages) {
        this.languages = languages;
    }

    public List<GeneralDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<GeneralDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public List<GeneralKeyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<GeneralKeyword> keywords) {
        this.keywords = keywords;
    }

    public List<GeneralCoverage> getCoverages() {
        return coverages;
    }

    public void setCoverages(List<GeneralCoverage> coverages) {
        this.coverages = coverages;
    }

    public GeneralStructure getStructure() {
        return structure;
    }

    public void setStructure(GeneralStructure structure) {
        this.structure = structure;
    }

    public GeneralAggregationLevel getAggregationLevel() {
        return aggregationLevel;
    }

    public void setAggregationLevel(GeneralAggregationLevel aggregationLevel) {
        this.aggregationLevel = aggregationLevel;
    }

}
