/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.educational;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Representa a categoria Educational do IEEE LOM.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Educational implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Lom lom;

    @Element( required = false )
    @OneToOne
    private EducationalInteractivityType interactivityType;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "educational" )
    @Size( min = 0, max = 10 )
    private List< EducationalLearningResourceType > learningResourceTypes;

    @Element( required = false )
    @OneToOne
    private EducationalInteractivityLevel interactivityLevel;

    @Element( required = false )
    @OneToOne
    private EducationalSemanticDensity semanticDensity;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "educational" )
    @Size( min = 0, max = 10 )
    private List< EducationalIntendedEndUserRole > intendedEndUserRoles;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "educational" )
    @Size( min = 0, max = 10 )
    private List< EducationalContext > contexts;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "educational" )
    @Size( min = 0, max = 5 )
    private List< EducationalTypicalAgeRange > typicalAgeRanges;

    @Element( required = false )
    @OneToOne
    private EducationalDifficulty difficulty;

    @Element( required = false )
    @OneToOne
    private EducationalTypicalLearningTime typicalLearningTime;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "educational" )
    @Size( min = 0, max = 10 )
    private List< EducationalDescription > descriptions;

    @ElementList( inline = true, required = false )
    @OneToMany( mappedBy = "educational" )
    @Size( min = 0, max = 10 )
    private List< EducationalLanguage > languages;

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

    public EducationalInteractivityType getInteractivityType() {
        return interactivityType;
    }

    public void setInteractivityType(EducationalInteractivityType interactivityType) {
        this.interactivityType = interactivityType;
    }

    public List<EducationalLearningResourceType> getLearningResourceTypes() {
        return learningResourceTypes;
    }

    public void setLearningResourceTypes(List<EducationalLearningResourceType> learningResourceTypes) {
        this.learningResourceTypes = learningResourceTypes;
    }

    public EducationalInteractivityLevel getInteractivityLevel() {
        return interactivityLevel;
    }

    public void setInteractivityLevel(EducationalInteractivityLevel interactivityLevel) {
        this.interactivityLevel = interactivityLevel;
    }

    public EducationalSemanticDensity getSemanticDensity() {
        return semanticDensity;
    }

    public void setSemanticDensity(EducationalSemanticDensity semanticDensity) {
        this.semanticDensity = semanticDensity;
    }

    public List<EducationalIntendedEndUserRole> getIntendedEndUserRoles() {
        return intendedEndUserRoles;
    }

    public void setIntendedEndUserRoles(List<EducationalIntendedEndUserRole> intendedEndUserRoles) {
        this.intendedEndUserRoles = intendedEndUserRoles;
    }

    public List<EducationalContext> getContexts() {
        return contexts;
    }

    public void setContexts(List<EducationalContext> contexts) {
        this.contexts = contexts;
    }

    public List<EducationalTypicalAgeRange> getTypicalAgeRanges() {
        return typicalAgeRanges;
    }

    public void setTypicalAgeRanges(List<EducationalTypicalAgeRange> typicalAgeRanges) {
        this.typicalAgeRanges = typicalAgeRanges;
    }

    public EducationalDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(EducationalDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public EducationalTypicalLearningTime getTypicalLearningTime() {
        return typicalLearningTime;
    }

    public void setTypicalLearningTime(EducationalTypicalLearningTime typicalLearningTime) {
        this.typicalLearningTime = typicalLearningTime;
    }

    public List<EducationalDescription> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<EducationalDescription> descriptions) {
        this.descriptions = descriptions;
    }

    public List<EducationalLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(List<EducationalLanguage> languages) {
        this.languages = languages;
    }

}
