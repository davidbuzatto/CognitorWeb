/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lia.cognitorweb.util;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.annotation.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.classification.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.comum.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.educational.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.general.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.lifecycle.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.metametadata.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.relation.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.rights.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.technical.*;
import java.util.*;
import org.springframework.orm.hibernate3.*;

/**
 * Classe com métodos utilitários para os metadados.
 *
 * @author David Buzatto
 */
public class MetadataUtils {

    /*
     * Cria uma instância da hierarquia de metadados, salvando os elementos.
     */
    public static Metadata createMetadataInstance( HibernateTemplate ht,
            String titulo, String idioma, String source ) throws Exception {

        Metadata meta = new Metadata();

        try {

            Lom lom = new Lom();
            lom.setXmlns( "http://ltsc.ieee.org/xsd/LOM" );
            ht.save( lom );

            meta.setSchema( "ADL SCORM" );
            meta.setSchemaVersion( "2004 4th Edition" );
            meta.setLom( lom );
            ht.save( meta );

            lom.setGeneral( doGeneral( ht, titulo, idioma, source ) );
            lom.setLifeCycle( doLifeCycle( ht, idioma, source ) );
            lom.setMetaMetadata( doMetaMetadata( ht, idioma, source ) );
            lom.setTechnical( doTechnical( ht, idioma, source ) );
            doEducational( ht, lom, idioma, source );
            lom.setRights( doRights( ht, idioma, source ) );
            doRelation( ht, lom, idioma, source );
            doAnnotation( ht, lom, idioma );
            doClassification( ht, lom, idioma, source );

            ht.saveOrUpdate( lom );

        } catch ( Exception exc ) {

            exc.printStackTrace();

        }

        return meta;

    }


    /*
     * Prepara a hierarquia da tag General.
     */
    private static General doGeneral( HibernateTemplate ht, String titulo,
            String idioma, String source ) throws Exception {

        General general = new General();
        ht.save( general );


        /*
         * identifier
         */
        GeneralIdentifier gIdentifier = new GeneralIdentifier();
        gIdentifier.setCatalog( "" );
        gIdentifier.setEntry( "" );
        gIdentifier.setGeneral( general );
        ht.save( gIdentifier );


        /*
         * title
         */
        GeneralTitle gTitle = new GeneralTitle();
        LangString lsTitle = new LangString();
        lsTitle.setLanguage( idioma );
        lsTitle.setValue( titulo );
        ht.save( lsTitle );

        List<LangString> lsTitles = new ArrayList<LangString>();
        lsTitles.add( lsTitle );
        gTitle.setStrings( lsTitles );
        ht.save( gTitle );
        general.setTitle( gTitle );


        /*
         * language
         */
        GeneralLanguage gLang = new GeneralLanguage();
        gLang.setValue( "" );
        gLang.setGeneral( general );
        ht.save( gLang );


        /*
         * description
         */
        GeneralDescription gDesc = new GeneralDescription();
        LangString lsDesc = new LangString();
        lsDesc.setLanguage( idioma );
        lsDesc.setValue( "" );
        ht.save( lsDesc );

        List<LangString> gDescs = new ArrayList<LangString>();
        gDescs.add( lsDesc );
        gDesc.setStrings( gDescs );
        gDesc.setGeneral( general );
        ht.save( gDesc );


        /*
         * keyword
         */
        GeneralKeyword gKey = new GeneralKeyword();
        LangString lsKey = new LangString();
        lsKey.setLanguage( idioma );
        lsKey.setValue( "" );
        ht.save( lsKey );

        List<LangString> gKeys = new ArrayList<LangString>();
        gKeys.add( lsKey );
        gKey.setStrings( gKeys );
        gKey.setGeneral( general );
        ht.save( gKey );


        /*
         * coverage
         */
        GeneralCoverage gCov = new GeneralCoverage();
        LangString lsCov = new LangString();
        lsCov.setLanguage( idioma );
        lsCov.setValue( "" );
        ht.save( lsCov );

        List<LangString> gCovs = new ArrayList<LangString>();
        gCovs.add( lsCov );
        gCov.setStrings( gCovs );
        gCov.setGeneral( general );
        ht.save( gCov );


        /*
         * structure
         */
        GeneralStructure gStructure = new GeneralStructure();
        gStructure.setSource( source );
        gStructure.setValue( "" );
        ht.save( gStructure );
        general.setStructure( gStructure );


        /*
         * aggregationLevel
         */
        GeneralAggregationLevel gAggreagationLevel = new GeneralAggregationLevel();
        gAggreagationLevel.setSource( source );
        gAggreagationLevel.setValue( "" );
        ht.save( gAggreagationLevel );
        general.setAggregationLevel( gAggreagationLevel );


        ht.saveOrUpdate( general );
        return general;

    }


    /*
     * Prepara a hierarquia da tag LifeCycle.
     */
    private static LifeCycle doLifeCycle( HibernateTemplate ht,
            String idioma, String source ) throws Exception {

        LifeCycle lifeCycle = new LifeCycle();
        ht.save( lifeCycle );


        /*
         * version
         */
        LifeCycleVersion lcVersion = new LifeCycleVersion();
        LangString lsVer = new LangString();
        lsVer.setLanguage( idioma );
        lsVer.setValue( "" );
        ht.save( lsVer );

        List<LangString> lsVers = new ArrayList<LangString>();
        lsVers.add( lsVer );
        lcVersion.setStrings( lsVers );
        ht.save( lcVersion );
        lifeCycle.setVersion( lcVersion );


        /*
         * status
         */
        LifeCycleStatus lcStatus = new LifeCycleStatus();
        lcStatus.setSource( source );
        lcStatus.setValue( "" );
        ht.save( lcStatus );
        lifeCycle.setStatus( lcStatus );


        /*
         * contribute
         */
        LifeCycleContribute lcContrib = new LifeCycleContribute();
        LifeCycleContributeRole lcRole = new LifeCycleContributeRole();
        lcRole.setSource( source );
        lcRole.setValue( "" );
        ht.save( lcRole );

        LifeCycleContributeEntity lcEnt = new LifeCycleContributeEntity();
        lcEnt.setValue( "" );

        LifeCycleContributeDate lcDate = new LifeCycleContributeDate();
        lcDate.setDateTime( "" );

        LifeCycleContributeDateDescription lcDesc = new LifeCycleContributeDateDescription();
        LangString lcDateStr = new LangString();
        lcDateStr.setLanguage( idioma );
        lcDateStr.setValue( "" );
        ht.save( lcDateStr );

        List<LangString> lcDateStrs = new ArrayList<LangString>();
        lcDateStrs.add( lcDateStr );
        lcDesc.setStrings( lcDateStrs );
        ht.save( lcDesc );
        
        lcDate.setDescription( lcDesc );
        ht.save( lcDate );

        lcContrib.setRole( lcRole );
        lcContrib.setDate( lcDate );
        lcContrib.setLifeCycle( lifeCycle );
        ht.save( lcContrib );

        lcEnt.setContribute( lcContrib );
        ht.save( lcEnt );


        ht.saveOrUpdate( lifeCycle );
        return lifeCycle;

    }


    /*
     * Prepara a hierarquia da tag Meta-Metadata.
     */
    private static MetaMetadata doMetaMetadata( HibernateTemplate ht,
            String idioma, String source ) throws Exception {

        MetaMetadata metaMetadata = new MetaMetadata();
        metaMetadata.setLanguage( idioma );
        ht.save( metaMetadata );


        /*
         * identifier
         */
        MetaMetadataIdentifier mtIdentifier = new MetaMetadataIdentifier();
        mtIdentifier.setCatalog( "" );
        mtIdentifier.setEntry( "" );
        mtIdentifier.setMetaMetadata( metaMetadata );
        ht.save( mtIdentifier );


        /*
         * contribute
         */
        MetaMetadataContribute mtContrib = new MetaMetadataContribute();
        MetaMetadataContributeRole mtRole = new MetaMetadataContributeRole();
        mtRole.setSource( source );
        mtRole.setValue( "" );
        ht.save( mtRole );

        MetaMetadataContributeEntity mtEnt = new MetaMetadataContributeEntity();
        mtEnt.setValue( "" );
        
        MetaMetadataContributeDate mtDate = new MetaMetadataContributeDate();
        mtDate.setDateTime( "" );

        MetaMetadataContributeDateDescription mtDesc = new MetaMetadataContributeDateDescription();
        LangString mtDateStr = new LangString();
        mtDateStr.setLanguage( idioma );
        mtDateStr.setValue( "" );
        ht.save( mtDateStr );

        List<LangString> mtDateStrs = new ArrayList<LangString>();
        mtDateStrs.add( mtDateStr );
        mtDesc.setStrings( mtDateStrs );
        ht.save( mtDesc );

        mtDate.setDescription( mtDesc );
        ht.save( mtDate );

        mtContrib.setRole( mtRole );
        mtContrib.setDate( mtDate );
        mtContrib.setMetaMetadata( metaMetadata );
        ht.save( mtContrib );

        mtEnt.setContribute( mtContrib );
        ht.save( mtEnt );


        /*
         * metadataSchema
         */
        MetaMetadataMetadataSchema mtSch = new MetaMetadataMetadataSchema();
        mtSch.setValue( "" );
        mtSch.setMetaMetadata( metaMetadata );
        ht.save( mtSch );


        ht.saveOrUpdate( metaMetadata );
        return metaMetadata;

    }


    /*
     * Prepara a hierarquia da tag Technical.
     */
    private static Technical doTechnical( HibernateTemplate ht,
            String idioma, String source ) throws Exception {

        Technical technical = new Technical();
        technical.setSize( "" );
        ht.save( technical );


        /*
         * format
         */
        TechnicalFormat tFor = new TechnicalFormat();
        tFor.setValue( "" );
        tFor.setTechnical( technical );
        ht.save( tFor );


        /*
         * location
         */
        TechnicalLocation tLoc = new TechnicalLocation();
        tLoc.setValue( "" );
        tLoc.setTechnical( technical );
        ht.save( tLoc );


        /*
         * orComposite
         */
        TechnicalRequirement tR = new TechnicalRequirement();
        TechnicalRequirementOrComposite tOr = new TechnicalRequirementOrComposite();
        TechnicalRequirementOrCompositeType tT = new TechnicalRequirementOrCompositeType();
        TechnicalRequirementOrCompositeName tN = new TechnicalRequirementOrCompositeName();
        tT.setSource( source );
        tT.setValue( "" );
        ht.save( tT );
        tN.setSource( source );
        tN.setValue( "" );
        ht.save( tN );

        tOr.setType( tT );
        tOr.setName( tN );
        tOr.setMinimumVersion( "" );
        tOr.setMaximumVersion( "" );

        tR.setTechnical( technical );
        ht.save( tR );

        tOr.setRequirement( tR );
        ht.save( tOr );


        /*
         * installationRemarks
         */
        TechnicalInstallationRemarks tIR = new TechnicalInstallationRemarks();
        LangString lsIR = new LangString();
        lsIR.setLanguage( idioma );
        lsIR.setValue( "" );
        ht.save( lsIR );

        List<LangString> tIRs = new ArrayList<LangString>();
        tIRs.add( lsIR );
        tIR.setStrings( tIRs );
        ht.save( tIR );
        technical.setInstallationRemarks( tIR );


        /*
         * otherPlatformRequirements
         */
        TechnicalOtherPlatformRequirements tOR = new TechnicalOtherPlatformRequirements();
        LangString lsOR = new LangString();
        lsOR.setLanguage( idioma );
        lsOR.setValue( "" );
        ht.save( lsOR );

        List<LangString> tORs = new ArrayList<LangString>();
        tORs.add( lsOR );
        tOR.setStrings( tORs );
        ht.save( tOR );
        technical.setOtherPlatformRequirements( tOR );

        
        /*
         * duration
         */
        TechnicalDuration tDu = new TechnicalDuration();
        tDu.setDuration( "" );

        TechnicalDurationDescription tDesc = new TechnicalDurationDescription();
        LangString lsDesc = new LangString();
        lsDesc.setLanguage( idioma );
        lsDesc.setValue( "" );
        ht.save( lsDesc );

        List<LangString> lsDes = new ArrayList<LangString>();
        lsDes.add( lsDesc );
        tDesc.setStrings( lsDes );
        ht.save( tDesc );
        
        tDu.setDescription( tDesc );
        ht.save( tDu );
        technical.setDuration( tDu );


        ht.saveOrUpdate( technical );
        return technical;

    }


    /*
     * Prepara a hierarquia da tag Educational.
     */
    private static Educational doEducational( HibernateTemplate ht, Lom lom,
            String idioma, String source ) throws Exception {

        Educational educational = new Educational();
        educational.setLom( lom );
        ht.save( educational );


        /*
         * interactivityType
         */
        EducationalInteractivityType eTi = new EducationalInteractivityType();
        eTi.setSource( source );
        eTi.setValue( "" );
        ht.save( eTi );
        educational.setInteractivityType( eTi );


        /*
         * learningResourceType
         */
        EducationalLearningResourceType eLr = new EducationalLearningResourceType();
        eLr.setSource( source );
        eLr.setValue( "" );
        eLr.setEducational( educational );
        ht.save( eLr );


        /*
         * interactivityLevel
         */
        EducationalInteractivityLevel eIl = new EducationalInteractivityLevel();
        eIl.setSource( source );
        eIl.setValue( "" );
        ht.save( eIl );
        educational.setInteractivityLevel( eIl );


        /*
         * semanticDensity
         */
        EducationalSemanticDensity eSd = new EducationalSemanticDensity();
        eSd.setSource( source );
        eSd.setValue( "" );
        ht.save( eSd );
        educational.setSemanticDensity( eSd );


        /*
         * intendedEndUserRole
         */
        EducationalIntendedEndUserRole eIe = new EducationalIntendedEndUserRole();
        eIe.setSource( source );
        eIe.setValue( "" );
        eIe.setEducational( educational );
        ht.save( eIe );

        /*
         * context
         */
        EducationalContext eC = new EducationalContext();
        eC.setSource( source );
        eC.setValue( "" );
        eC.setEducational( educational );
        ht.save( eC );
        

        /*
         * typicalAgeRange
         */
        EducationalTypicalAgeRange eTar = new EducationalTypicalAgeRange();
        
        List<LangString> lsTar = new ArrayList<LangString>();
        LangString lsT = new LangString();
        lsT.setLanguage( idioma );
        lsT.setValue( "" );
        ht.save( lsT );
        
        lsTar.add( lsT );
        eTar.setStrings( lsTar );
        
        eTar.setEducational( educational );
        ht.save( eTar );


        /*
         * difficulty
         */
        EducationalDifficulty eD = new EducationalDifficulty();
        eD.setSource( source );
        eD.setValue( "" );
        ht.save( eD );
        educational.setDifficulty( eD );

        
        /*
         * typicalLearningTime
         */
        EducationalTypicalLearningTime eTlt = new EducationalTypicalLearningTime();
        eTlt.setDuration( "" );
        ht.save( eTlt );

        EducationalTypicalLearningTimeDescription eDt = new EducationalTypicalLearningTimeDescription();
        LangString lsdd = new LangString();
        lsdd.setLanguage( idioma );
        lsdd.setValue( "" );
        ht.save( lsdd );

        List<LangString> lsdds = new ArrayList<LangString>();
        lsdds.add( lsdd );
        eDt.setStrings( lsdds );
        
        eDt.setTypicalLearningTime( eTlt );
        ht.save( eDt );
        
        educational.setTypicalLearningTime( eTlt );


        /*
         * description
         */
        EducationalDescription eDes = new EducationalDescription();
        
        LangString lsd = new LangString();
        lsd.setLanguage( idioma );
        lsd.setValue( "" );
        ht.save( lsd );
        
        List<LangString> lsds = new ArrayList<LangString>();
        lsds.add( lsd );
        eDes.setStrings( lsds );
        eDes.setEducational( educational );
        ht.save( eDes );
        

        /*
         * language
         */
        EducationalLanguage eL = new EducationalLanguage();
        eL.setValue( "" );
        eL.setEducational( educational );
        ht.save( eL );


        ht.saveOrUpdate( educational );
        return educational;

    }


    /*
     * Prepara a hierarquia da tag Rights.
     */
    private static Rights doRights( HibernateTemplate ht,
            String idioma, String source ) throws Exception {

        Rights rights = new Rights();
        ht.save( rights );


        /*
         * cost
         */
        RightsCost rCost = new RightsCost();
        rCost.setSource( source );
        rCost.setValue( "" );
        ht.save( rCost );
        rights.setCost( rCost );


        /*
         * copyrightAndOtherRestrictions
         */
        RightsCopyrightAndOtherRestrictions rCopy = new RightsCopyrightAndOtherRestrictions();
        rCopy.setSource( source );
        rCopy.setValue( "" );
        ht.save( rCopy );
        rights.setCopyrightAndOtherRestrictions( rCopy );


        /*
         * description
         */
        RightsDescription rDesc = new RightsDescription();
        LangString lDesc = new LangString();
        lDesc.setLanguage( idioma );
        lDesc.setValue( "" );
        ht.save( lDesc );

        List<LangString> lDescs = new ArrayList<LangString>();
        lDescs.add( lDesc );
        rDesc.setStrings( lDescs );
        ht.save( rDesc );
        rights.setDescription( rDesc );


        ht.saveOrUpdate( rights );
        return rights;

    }


    /*
     * Prepara a hierarquia da tag Relation.
     */
    private static Relation doRelation( HibernateTemplate ht, Lom lom,
            String idioma, String source ) throws Exception {

        Relation relation = new Relation();
        relation.setLom( lom );
        ht.save( relation );


        /*
         * kind
         */
        RelationKind rK = new RelationKind();
        rK.setSource( source );
        rK.setValue( "" );
        ht.save( rK );
        relation.setKind( rK );


        /*
         * resource
         */
        RelationResource rRes = new RelationResource();
        ht.save( rRes );

        RelationResourceIdentifier rIden = new RelationResourceIdentifier();
        rIden.setCatalog( "" );
        rIden.setEntry( "" );
        rIden.setResource( rRes );
        ht.save( rIden );
        
        RelationResourceDescription rDesc = new RelationResourceDescription();
        List<LangString> lDescs = new ArrayList<LangString>();
        LangString lDesc = new LangString();
        lDesc.setLanguage( idioma );
        lDesc.setValue( "" );
        ht.save( lDesc );

        lDescs.add( lDesc );
        rDesc.setStrings( lDescs );
        rDesc.setResource( rRes );
        ht.save( rDesc );
        
        relation.setResource( rRes );


        ht.saveOrUpdate( relation );
        return relation;

    }


    /*
     * Prepara a hierarquia da tag Annotation.
     */
    private static Annotation doAnnotation( HibernateTemplate ht, Lom lom,
            String idioma ) throws Exception {

        Annotation annotation = new Annotation();
        annotation.setLom( lom );
        ht.save( annotation );


        /*
         * entity
         */
        AnnotationEntity aEnt = new AnnotationEntity();
        aEnt.setValue( "" );
        ht.save( aEnt );
        annotation.setEntity( aEnt );


        /*
         * date
         */
        AnnotationDate aDat = new AnnotationDate();
        aDat.setDateTime( "" );

        AnnotationDateDescription aDatDesc = new AnnotationDateDescription();
        List<LangString> lDateDescs = new ArrayList<LangString>();
        LangString lDateDesc = new LangString();
        lDateDesc.setLanguage( idioma );
        lDateDesc.setValue( "" );
        ht.save( lDateDesc );

        lDateDescs.add( lDateDesc );
        aDatDesc.setStrings( lDateDescs );
        ht.save( aDatDesc );

        aDat.setDescription( aDatDesc );
        ht.save( aDat );

        annotation.setDate( aDat );


        /*
         * description
         */
        AnnotationDescription aDesc = new AnnotationDescription();
        List<LangString> lDescs = new ArrayList<LangString>();
        LangString lDesc = new LangString();
        lDesc.setLanguage( idioma );
        lDesc.setValue( "" );
        ht.save( lDesc );

        lDescs.add( lDesc );
        aDesc.setStrings( lDescs );
        ht.save( aDesc );

        annotation.setDescription( aDesc );


        ht.saveOrUpdate( annotation );
        return annotation;

    }


    /*
     * Prepara a hierarquia da tag Classification.
     */
    private static Classification doClassification( HibernateTemplate ht, 
            Lom lom, String idioma, String source ) throws Exception {

        Classification classification = new Classification();
        classification.setLom( lom );
        ht.save( classification );


        /*
         * purpose
         */
        ClassificationPurpose cPur = new ClassificationPurpose();
        cPur.setSource( source );
        cPur.setValue( "" );
        ht.save( cPur );
        classification.setPurpose( cPur );


        /*
         * taxonPath
         */
        ClassificationTaxonPath cTaxonPath = new ClassificationTaxonPath();
        cTaxonPath.setClassification( classification );

        ClassificationTaxonPathSource cSource = new ClassificationTaxonPathSource();

        List<LangString> lSources = new ArrayList<LangString>();
        LangString lSource = new LangString();
        lSource.setLanguage( idioma );
        lSource.setValue( "" );
        ht.save( lSource );

        lSources.add( lSource );
        cSource.setStrings( lSources );
        ht.save( cSource );

        cTaxonPath.setSource( cSource );
        ht.save( cTaxonPath );

        ClassificationTaxonPathTaxon taxon = new ClassificationTaxonPathTaxon();
        taxon.setTaxonPath( cTaxonPath );
        taxon.setId( "" );
        
        ClassificationTaxonPathTaxonEntry cEnt = new ClassificationTaxonPathTaxonEntry();
        LangString cEnts = new LangString();
        cEnts.setLanguage( idioma );
        cEnts.setValue( "" );
        ht.save( cEnts );

        List<LangString> lEnts = new ArrayList<LangString>();
        lEnts.add( cEnts );
        cEnt.setStrings( lEnts );
        ht.save( cEnt );
        
        taxon.setEntry( cEnt );
        ht.save( taxon );


        /*
         * description
         */
        ClassificationDescription cDesc = new ClassificationDescription();
        List<LangString> lDescs = new ArrayList<LangString>();
        LangString lDesc = new LangString();
        lDesc.setLanguage( idioma );
        lDesc.setValue( "" );
        ht.save( lDesc );

        lDescs.add( lDesc );
        cDesc.setStrings( lDescs );
        ht.save( cDesc );

        classification.setDescription( cDesc );


        /*
         * keyword
         */
        ClassificationKeyword cKey = new ClassificationKeyword();
        List<LangString> lKeys = new ArrayList<LangString>();
        LangString lKey = new LangString();
        lKey.setLanguage( idioma );
        lKey.setValue( "" );
        ht.save( lKey );

        lKeys.add( lKey );
        cKey.setStrings( lKeys );
        cKey.setClassification( classification );
        ht.save( cKey );


        ht.saveOrUpdate( classification );
        return classification;

    }

}
