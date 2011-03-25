/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lia.cognitorweb.testes;

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
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 * Testes do SimpleXML.
 *
 * @author David Buzatto
 */
public class TestesSimpleXML {

    private static Metadata metadata = new Metadata();
    private static Lom lom = new Lom();
    private static General general = new General();
    private static LifeCycle lifeCycle = new LifeCycle();
    private static MetaMetadata metaMetadata = new MetaMetadata();
    private static Technical technical = new Technical();
    private static Educational educational = new Educational();
    private static Rights rights = new Rights();
    private static Relation relation = new Relation();
    private static Annotation annotation = new Annotation();
    private static Classification classification = new Classification();

    public static void main( String[] args ) {

        doGeneral();
        doLifeCycle();
        doMetaMetadata();
        doTechnical();
        doEducational();
        doRights();
        doRelation();
        doAnnotation();
        doClassification();

        // gera o xml e exibe
        metadata.setSchema( "ADL SCORM" );
        metadata.setSchemaVersion( "2004 4th Edition" );
        lom.setXmlns( "http://ltsc.ieee.org/xsd/LOM" );
        metadata.setLom( lom );

        try {
            Serializer s = new Persister();
            s.write( metadata, System.out );
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

    }

    /*
     * Prepara os dados de teste para a tag general.
     */
    private static void doGeneral() {

        /*
         * identifier
         */
        GeneralIdentifier gIdentifier1 = new GeneralIdentifier();
        GeneralIdentifier gIdentifier2 = new GeneralIdentifier();
        gIdentifier1.setCatalog( "URI" );
        gIdentifier1.setEntry( "http://www.teste.com.br/" );
        gIdentifier2.setCatalog( "URI" );
        gIdentifier2.setEntry( "http://www.java.net/" );
        List<GeneralIdentifier> gIdentifiers = new ArrayList<GeneralIdentifier>();
        gIdentifiers.add( gIdentifier1 );
        gIdentifiers.add( gIdentifier2 );
        general.setIdentifiers( gIdentifiers );

        /*
         * title
         */
        GeneralTitle gTitle = new GeneralTitle();
        LangString lsTitle1 = new LangString();
        LangString lsTitle2 = new LangString();
        lsTitle1.setLanguage( "pt" );
        lsTitle1.setValue( "Material educacional X" );
        lsTitle2.setLanguage( "en" );
        lsTitle2.setValue( "Educational material X" );
        List<LangString> lsTitles = new ArrayList<LangString>();
        lsTitles.add( lsTitle1 );
        lsTitles.add( lsTitle2 );
        gTitle.setStrings( lsTitles );
        general.setTitle( gTitle );

        /*
         * language
         */
        GeneralLanguage gLang1 = new GeneralLanguage();
        GeneralLanguage gLang2 = new GeneralLanguage();
        gLang1.setValue( "pt" );
        gLang2.setValue( "en" );
        List<GeneralLanguage> gLangs = new ArrayList<GeneralLanguage>();
        gLangs.add( gLang1 );
        gLangs.add( gLang2 );
        general.setLanguages( gLangs );

        /*
         * description
         */
        GeneralDescription gDesc1 = new GeneralDescription();
        GeneralDescription gDesc2 = new GeneralDescription();
        LangString lsDesc11 = new LangString();
        LangString lsDesc12 = new LangString();
        LangString lsDesc21 = new LangString();
        LangString lsDesc22 = new LangString();
        lsDesc11.setLanguage( "pt" );
        lsDesc11.setValue( "Material de teste muito legal" );
        lsDesc12.setLanguage( "en" );
        lsDesc12.setValue( "Very nice test material" );
        lsDesc21.setLanguage( "pt" );
        lsDesc21.setValue( "Uma descrição muito interessante..." );
        lsDesc22.setLanguage( "en" );
        lsDesc22.setValue( "A very interesting description..." );
        List<LangString> gDescs1 = new ArrayList<LangString>();
        List<LangString> gDescs2 = new ArrayList<LangString>();
        gDescs1.add( lsDesc11 );
        gDescs1.add( lsDesc12 );
        gDescs2.add( lsDesc21 );
        gDescs2.add( lsDesc22 );
        gDesc1.setStrings( gDescs1 );
        gDesc2.setStrings( gDescs2 );
        List<GeneralDescription> gDescs = new ArrayList<GeneralDescription>();
        gDescs.add( gDesc1 );
        gDescs.add( gDesc2 );
        general.setDescriptions( gDescs );

        /*
         * keyword
         */
        GeneralKeyword gKey1 = new GeneralKeyword();
        GeneralKeyword gKey2 = new GeneralKeyword();
        LangString lsKey11 = new LangString();
        LangString lsKey12 = new LangString();
        LangString lsKey21 = new LangString();
        LangString lsKey22 = new LangString();
        lsKey11.setLanguage( "pt" );
        lsKey11.setValue( "teste" );
        lsKey12.setLanguage( "en" );
        lsKey12.setValue( "test" );
        lsKey21.setLanguage( "pt" );
        lsKey21.setValue( "material educacional" );
        lsKey22.setLanguage( "en" );
        lsKey22.setValue( "educational material" );
        List<LangString> gKeys1 = new ArrayList<LangString>();
        List<LangString> gKeys2 = new ArrayList<LangString>();
        gKeys1.add( lsKey11 );
        gKeys1.add( lsKey12 );
        gKeys2.add( lsKey21 );
        gKeys2.add( lsKey22 );
        gKey1.setStrings( gKeys1 );
        gKey2.setStrings( gKeys2 );
        List<GeneralKeyword> gKeys = new ArrayList<GeneralKeyword>();
        gKeys.add( gKey1 );
        gKeys.add( gKey2 );
        general.setKeywords( gKeys );

        /*
         * coverage
         */
        GeneralCoverage gCov1 = new GeneralCoverage();
        GeneralCoverage gCov2 = new GeneralCoverage();
        LangString lsCov11 = new LangString();
        LangString lsCov12 = new LangString();
        LangString lsCov21 = new LangString();
        LangString lsCov22 = new LangString();
        lsCov11.setLanguage( "pt" );
        lsCov11.setValue( "Aplicado a alunos brasileiros" );
        lsCov12.setLanguage( "en" );
        lsCov12.setValue( "Applicable to brasilian students" );
        lsCov21.setLanguage( "pt" );
        lsCov21.setValue( "Mais um teste..." );
        lsCov22.setLanguage( "en" );
        lsCov22.setValue( "One more test..." );
        List<LangString> gCovs1 = new ArrayList<LangString>();
        List<LangString> gCovs2 = new ArrayList<LangString>();
        gCovs1.add( lsCov11 );
        gCovs1.add( lsCov12 );
        gCovs2.add( lsCov21 );
        gCovs2.add( lsCov22 );
        gCov1.setStrings( gCovs1 );
        gCov2.setStrings( gCovs2 );
        List<GeneralCoverage> gCovs = new ArrayList<GeneralCoverage>();
        gCovs.add( gCov1 );
        gCovs.add( gCov2 );
        general.setCoverages( gCovs );

        /*
         * structure
         */
        GeneralStructure gStructure = new GeneralStructure();
        gStructure.setSource( "LOMv1.0" );
        gStructure.setValue( "atomic" );
        general.setStructure( gStructure );

        /*
         * aggregationLevel
         */
        GeneralAggregationLevel gAggreagationLevel = new GeneralAggregationLevel();
        gAggreagationLevel.setSource( "LOMv1.0" );
        gAggreagationLevel.setValue( "4" );
        general.setAggregationLevel( gAggreagationLevel );

        lom.setGeneral( general );

    }

    /*
     * Prepara os dados de teste para a tag lifeCycle
     */
    private static void doLifeCycle() {

        /*
         * version
         */
        LifeCycleVersion lfVersion = new LifeCycleVersion();
        LangString lsVer1 = new LangString();
        LangString lsVer2 = new LangString();
        lsVer1.setLanguage( "pt" );
        lsVer1.setValue( "1.0 beta" );
        lsVer2.setLanguage( "en" );
        lsVer2.setValue( "1.0 beta" );
        List<LangString> lsVers = new ArrayList<LangString>();
        lsVers.add( lsVer1 );
        lsVers.add( lsVer2 );
        lfVersion.setStrings( lsVers );
        lifeCycle.setVersion( lfVersion );

        /*
         * status
         */
        LifeCycleStatus lfStatus = new LifeCycleStatus();
        lfStatus.setSource( "LOMv1.0" );
        lfStatus.setValue( "draft" );
        lifeCycle.setStatus( lfStatus );

        /*
         * contribute
         */
        LifeCycleContribute lfContrib1 = new LifeCycleContribute();
        LifeCycleContribute lfContrib2 = new LifeCycleContribute();
        LifeCycleContributeRole lfRole1 = new LifeCycleContributeRole();
        LifeCycleContributeRole lfRole2 = new LifeCycleContributeRole();
        lfRole1.setSource( "LOMv1.0" );
        lfRole1.setValue( "author" );
        lfRole2.setSource( "LOMv1.0" );
        lfRole2.setValue( "publisher" );
        lfContrib1.setRole( lfRole1 );
        lfContrib2.setRole( lfRole2 );
        LifeCycleContributeEntity lfEnt11 = new LifeCycleContributeEntity();
        LifeCycleContributeEntity lfEnt12 = new LifeCycleContributeEntity();
        LifeCycleContributeEntity lfEnt21 = new LifeCycleContributeEntity();
        LifeCycleContributeEntity lfEnt22 = new LifeCycleContributeEntity();
        lfEnt11.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Mary Author&#13;&#10;END:VCARD" );
        lfEnt12.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Joe Author&#13;&#10;END:VCARD" );
        lfEnt21.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Mary Author&#13;&#10;END:VCARD" );
        lfEnt22.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Joe Author&#13;&#10;END:VCARD" );
        List<LifeCycleContributeEntity> lfEnts1 = new ArrayList<LifeCycleContributeEntity>();
        lfEnts1.add( lfEnt11 );
        lfEnts1.add( lfEnt12 );
        List<LifeCycleContributeEntity> lfEnts2 = new ArrayList<LifeCycleContributeEntity>();
        lfEnts2.add( lfEnt21 );
        lfEnts2.add( lfEnt22 );
        lfContrib1.setEntities( lfEnts1 );
        lfContrib2.setEntities( lfEnts2 );
        LifeCycleContributeDate lfDate1 = new LifeCycleContributeDate();
        LifeCycleContributeDate lfDate2 = new LifeCycleContributeDate();
        lfDate1.setDateTime( "2009-06-02" );
        lfDate2.setDateTime( "2009-06-03" );
        LifeCycleContributeDateDescription lcDesc1 = new LifeCycleContributeDateDescription();
        LifeCycleContributeDateDescription lcDesc2 = new LifeCycleContributeDateDescription();
        LangString lfDateStr11 = new LangString();
        LangString lfDateStr12 = new LangString();
        LangString lfDateStr21 = new LangString();
        LangString lfDateStr22 = new LangString();
        lfDateStr11.setLanguage( "pt" );
        lfDateStr11.setValue( "Primeiro teste" );
        lfDateStr12.setLanguage( "en" );
        lfDateStr12.setValue( "First test" );
        lfDateStr21.setLanguage( "pt" );
        lfDateStr21.setValue( "Segundo teste" );
        lfDateStr22.setLanguage( "en" );
        lfDateStr22.setValue( "Segundo test" );
        List<LangString> lfDateStrs1 = new ArrayList<LangString>();
        List<LangString> lfDateStrs2 = new ArrayList<LangString>();
        lfDateStrs1.add( lfDateStr11 );
        lfDateStrs1.add( lfDateStr12 );
        lfDateStrs2.add( lfDateStr21 );
        lfDateStrs2.add( lfDateStr22 );
        lcDesc1.setStrings( lfDateStrs1 );
        lcDesc2.setStrings( lfDateStrs2 );
        lfDate1.setDescription( lcDesc1 );
        lfDate2.setDescription( lcDesc2 );
        lfContrib1.setDate( lfDate1 );
        lfContrib2.setDate( lfDate2 );
        List<LifeCycleContribute> lsContribs = new ArrayList<LifeCycleContribute>();
        lsContribs.add( lfContrib1 );
        lsContribs.add( lfContrib2 );
        lifeCycle.setContributes( lsContribs );

        lom.setLifeCycle( lifeCycle );

    }

    /*
     * Prepara os dados de teste para a tag metaMetadata.
     */
    private static void doMetaMetadata() {

        /*
         * identifier
         */
        MetaMetadataIdentifier mtIdentifier1 = new MetaMetadataIdentifier();
        MetaMetadataIdentifier mtIdentifier2 = new MetaMetadataIdentifier();
        mtIdentifier1.setCatalog( "URI" );
        mtIdentifier1.setEntry( "http://www.teste.com.br/a" );
        mtIdentifier2.setCatalog( "URI" );
        mtIdentifier2.setEntry( "http://www.java.net/b" );
        List<MetaMetadataIdentifier> mtIdentifiers = new ArrayList<MetaMetadataIdentifier>();
        mtIdentifiers.add( mtIdentifier1 );
        mtIdentifiers.add( mtIdentifier2 );
        metaMetadata.setIdentifiers( mtIdentifiers );

        /*
         * contribute
         */
        MetaMetadataContribute mtContrib1 = new MetaMetadataContribute();
        MetaMetadataContribute mtContrib2 = new MetaMetadataContribute();
        MetaMetadataContributeRole mtRole1 = new MetaMetadataContributeRole();
        MetaMetadataContributeRole mtRole2 = new MetaMetadataContributeRole();
        mtRole1.setSource( "LOMv1.0" );
        mtRole1.setValue( "creator" );
        mtRole2.setSource( "LOMv1.0" );
        mtRole2.setValue( "validator" );
        mtContrib1.setRole( mtRole1 );
        mtContrib2.setRole( mtRole2 );
        MetaMetadataContributeEntity mtEnt11 = new MetaMetadataContributeEntity();
        MetaMetadataContributeEntity mtEnt12 = new MetaMetadataContributeEntity();
        MetaMetadataContributeEntity mtEnt21 = new MetaMetadataContributeEntity();
        MetaMetadataContributeEntity mtEnt22 = new MetaMetadataContributeEntity();
        mtEnt11.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Mary Author&#13;&#10;END:VCARDa" );
        mtEnt12.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Joe Author&#13;&#10;END:VCARDa" );
        mtEnt21.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Mary Author&#13;&#10;END:VCARDb" );
        mtEnt22.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Joe Author&#13;&#10;END:VCARDb" );
        List<MetaMetadataContributeEntity> mtEnts1 = new ArrayList<MetaMetadataContributeEntity>();
        mtEnts1.add( mtEnt11 );
        mtEnts1.add( mtEnt12 );
        List<MetaMetadataContributeEntity> mtEnts2 = new ArrayList<MetaMetadataContributeEntity>();
        mtEnts2.add( mtEnt21 );
        mtEnts2.add( mtEnt22 );
        mtContrib1.setEntities( mtEnts1 );
        mtContrib2.setEntities( mtEnts2 );
        MetaMetadataContributeDate mtDate1 = new MetaMetadataContributeDate();
        MetaMetadataContributeDate mtDate2 = new MetaMetadataContributeDate();
        mtDate1.setDateTime( "2009-06-05" );
        mtDate2.setDateTime( "2009-06-06" );
        MetaMetadataContributeDateDescription mtDesc1 = new MetaMetadataContributeDateDescription();
        MetaMetadataContributeDateDescription mtDesc2 = new MetaMetadataContributeDateDescription();
        LangString mtDateStr11 = new LangString();
        LangString mtDateStr12 = new LangString();
        LangString mtDateStr21 = new LangString();
        LangString mtDateStr22 = new LangString();
        mtDateStr11.setLanguage( "pt" );
        mtDateStr11.setValue( "ra" );
        mtDateStr12.setLanguage( "en" );
        mtDateStr12.setValue( "ra" );
        mtDateStr21.setLanguage( "pt" );
        mtDateStr21.setValue( "re" );
        mtDateStr22.setLanguage( "en" );
        mtDateStr22.setValue( "re" );
        List<LangString> mtDateStrs1 = new ArrayList<LangString>();
        List<LangString> mtDateStrs2 = new ArrayList<LangString>();
        mtDateStrs1.add( mtDateStr11 );
        mtDateStrs1.add( mtDateStr12 );
        mtDateStrs2.add( mtDateStr21 );
        mtDateStrs2.add( mtDateStr22 );
        mtDesc1.setStrings( mtDateStrs1 );
        mtDesc2.setStrings( mtDateStrs2 );
        mtDate1.setDescription( mtDesc1 );
        mtDate2.setDescription( mtDesc2 );
        mtContrib1.setDate( mtDate1 );
        mtContrib2.setDate( mtDate2 );
        List<MetaMetadataContribute> mtContribs = new ArrayList<MetaMetadataContribute>();
        mtContribs.add( mtContrib1 );
        mtContribs.add( mtContrib2 );
        metaMetadata.setContributes( mtContribs );

        /*
         * metadataSchema
         */
        MetaMetadataMetadataSchema mSch1 = new MetaMetadataMetadataSchema();
        MetaMetadataMetadataSchema mSch2 = new MetaMetadataMetadataSchema();
        mSch1.setValue( "LOMv1.0" );
        mSch2.setValue( "LOMv2.0" );
        List<MetaMetadataMetadataSchema> mSchs = new ArrayList<MetaMetadataMetadataSchema>();
        mSchs.add( mSch1 );
        mSchs.add( mSch2 );
        metaMetadata.setMetadataSchemas( mSchs );

        /*
         * language
         */
        metaMetadata.setLanguage( "pt" );

        lom.setMetaMetadata( metaMetadata );

    }

    /*
     * Prepara os dados de teste para a tag Technical.
     */
    private static void doTechnical() {

        /*
         * format
         */
        TechnicalFormat tFor1 = new TechnicalFormat();
        TechnicalFormat tFor2 = new TechnicalFormat();
        tFor1.setValue( "text/html" );
        tFor2.setValue( "text/xml" );
        List<TechnicalFormat> tFors = new ArrayList<TechnicalFormat>();
        tFors.add( tFor1 );
        tFors.add( tFor2 );
        technical.setFormats( tFors );

        /*
         * size
         */
        technical.setSize( "123456" );

        /*
         * location
         */
        TechnicalLocation tLoc1 = new TechnicalLocation();
        TechnicalLocation tLoc2 = new TechnicalLocation();
        tLoc1.setValue( "http://lia.dc.ufscar.br/david/teste1" );
        tLoc2.setValue( "http://lia.dc.ufscar.br/david/teste2" );
        List<TechnicalLocation> tLocs = new ArrayList<TechnicalLocation>();
        tLocs.add( tLoc1 );
        tLocs.add( tLoc2 );
        technical.setLocations( tLocs );

        /*
         * orComposite
         */
        TechnicalRequirement tR1 = new TechnicalRequirement();
        TechnicalRequirementOrComposite tOr11 = new TechnicalRequirementOrComposite();
        TechnicalRequirementOrComposite tOr12 = new TechnicalRequirementOrComposite();
        TechnicalRequirementOrCompositeType tT111 = new TechnicalRequirementOrCompositeType();
        TechnicalRequirementOrCompositeName tN111 = new TechnicalRequirementOrCompositeName();
        TechnicalRequirementOrCompositeType tT112 = new TechnicalRequirementOrCompositeType();
        TechnicalRequirementOrCompositeName tN112 = new TechnicalRequirementOrCompositeName();
        tT111.setSource( "LOMv1.0" );
        tT111.setValue( "operating system" );
        tN111.setSource( "LOMv1.0" );
        tN111.setValue( "ms-windows" );
        tT112.setSource( "LOMv1.0" );
        tT112.setValue( "browser" );
        tN112.setSource( "LOMv1.0" );
        tN112.setValue( "mozilla firefox" );
        tOr11.setType( tT111 );
        tOr11.setName( tN111 );
        tOr12.setType( tT112 );
        tOr12.setName( tN112 );
        tOr11.setMinimumVersion( "98" );
        tOr11.setMaximumVersion( "XP" );
        tOr12.setMinimumVersion( "2.0" );
        tOr12.setMaximumVersion( "3.0" );
        List<TechnicalRequirementOrComposite> tOrs1 = new ArrayList<TechnicalRequirementOrComposite>();
        tOrs1.add( tOr11 );
        tOrs1.add( tOr12 );
        tR1.setOrComposites( tOrs1 );

        TechnicalRequirement tR2 = new TechnicalRequirement();
        TechnicalRequirementOrComposite tOr21 = new TechnicalRequirementOrComposite();
        TechnicalRequirementOrComposite tOr22 = new TechnicalRequirementOrComposite();
        TechnicalRequirementOrCompositeType tT221 = new TechnicalRequirementOrCompositeType();
        TechnicalRequirementOrCompositeName tN221 = new TechnicalRequirementOrCompositeName();
        TechnicalRequirementOrCompositeType tT222 = new TechnicalRequirementOrCompositeType();
        TechnicalRequirementOrCompositeName tN222 = new TechnicalRequirementOrCompositeName();
        tT221.setSource( "LOMv1.0" );
        tT221.setValue( "operating system" );
        tN221.setSource( "LOMv1.0" );
        tN221.setValue( "unix" );
        tT222.setSource( "LOMv1.0" );
        tT222.setValue( "browser" );
        tN222.setSource( "LOMv1.0" );
        tN222.setValue( "safari" );
        tOr21.setType( tT111 );
        tOr21.setName( tN111 );
        tOr22.setType( tT112 );
        tOr22.setName( tN112 );
        tOr21.setMinimumVersion( "1.0" );
        tOr21.setMaximumVersion( "2.0" );
        tOr22.setMinimumVersion( "4.0" );
        tOr22.setMaximumVersion( "8.0" );
        List<TechnicalRequirementOrComposite> tOrs2 = new ArrayList<TechnicalRequirementOrComposite>();
        tOrs2.add( tOr21 );
        tOrs2.add( tOr22 );
        tR2.setOrComposites( tOrs2 );

        List<TechnicalRequirement> tRs = new ArrayList<TechnicalRequirement>();
        tRs.add( tR1 );
        tRs.add( tR2 );
        technical.setRequirements( tRs );


        /*
         * installationRemarks
         */
        TechnicalInstallationRemarks tIR = new TechnicalInstallationRemarks();
        LangString lsIR1 = new LangString();
        LangString lsIR2 = new LangString();
        lsIR1.setLanguage( "pt" );
        lsIR1.setValue( "Instruções de instalação" );
        lsIR2.setLanguage( "en" );
        lsIR2.setValue( "Installation instructions" );
        List<LangString> tIRs = new ArrayList<LangString>();
        tIRs.add( lsIR1 );
        tIRs.add( lsIR2 );
        tIR.setStrings( tIRs );
        technical.setInstallationRemarks( tIR );

        /*
         * otherPlatformRequirements
         */
        TechnicalOtherPlatformRequirements tOR = new TechnicalOtherPlatformRequirements();
        LangString lsOR1 = new LangString();
        LangString lsOR2 = new LangString();
        lsOR1.setLanguage( "pt" );
        lsOR1.setValue( "Outras coisas..." );
        lsOR2.setLanguage( "en" );
        lsOR2.setValue( "Other things..." );
        List<LangString> tORs = new ArrayList<LangString>();
        tORs.add( lsOR1 );
        tORs.add( lsOR2 );
        tOR.setStrings( tORs );
        technical.setOtherPlatformRequirements( tOR );

        /*
         * duration
         */
        TechnicalDuration tDu = new TechnicalDuration();
        tDu.setDuration( "PT1H30M" );
        TechnicalDurationDescription tDesc = new TechnicalDurationDescription();
        LangString lsDesc1 = new LangString();
        LangString lsDesc2 = new LangString();
        lsDesc1.setLanguage( "pt" );
        lsDesc1.setValue( "Demorou..." );
        lsDesc2.setLanguage( "en" );
        lsDesc2.setValue( "I don´t know..." );
        List<LangString> lsDes = new ArrayList<LangString>();
        lsDes.add( lsDesc1 );
        lsDes.add( lsDesc2 );
        tDesc.setStrings( lsDes );
        tDu.setDescription( tDesc );
        technical.setDuration( tDu );

        lom.setTechnical( technical );

    }


    /*
     * Prepara os dados de teste para a tag Educational.
     */
    private static void doEducational() {

        /*
         * interactivityType
         */
        EducationalInteractivityType eTi = new EducationalInteractivityType();
        eTi.setSource( "LOMv1.0" );
        eTi.setValue( "active" );
        educational.setInteractivityType( eTi );

        /*
         * learningResourceType
         */
        EducationalLearningResourceType eLr1 = new EducationalLearningResourceType();
        eLr1.setSource( "LOMv1.0" );
        eLr1.setValue( "exercise" );
        EducationalLearningResourceType eLr2 = new EducationalLearningResourceType();
        eLr2.setSource( "LOMv1.0" );
        eLr2.setValue( "simulation" );
        List<EducationalLearningResourceType> eLrs = new ArrayList<EducationalLearningResourceType>();
        eLrs.add( eLr1 );
        eLrs.add( eLr2 );
        educational.setLearningResourceTypes( eLrs );

        /*
         * interactivityLevel
         */
        EducationalInteractivityLevel eIl = new EducationalInteractivityLevel();
        eIl.setSource( "LOMv1.0" );
        eIl.setValue( "very low" );
        educational.setInteractivityLevel( eIl );

        /*
         * semanticDensity
         */
        EducationalSemanticDensity eSd = new EducationalSemanticDensity();
        eSd.setSource( "LOMv1.0" );
        eSd.setValue( "low" );
        educational.setSemanticDensity( eSd );

        /*
         * intendedEndUserRole
         */
        EducationalIntendedEndUserRole eIe1 = new EducationalIntendedEndUserRole();
        eIe1.setSource( "LOMv1.0" );
        eIe1.setValue( "learner" );
        EducationalIntendedEndUserRole eIe2 = new EducationalIntendedEndUserRole();
        eIe2.setSource( "LOMv1.0" );
        eIe2.setValue( "manager" );
        List<EducationalIntendedEndUserRole> eIes = new ArrayList<EducationalIntendedEndUserRole>();
        eIes.add( eIe1 );
        eIes.add( eIe2 );
        educational.setIntendedEndUserRoles( eIes );

        /*
         * context
         */
        EducationalContext eC1 = new EducationalContext();
        eC1.setSource( "LOMv1.0" );
        eC1.setValue( "school" );
        EducationalContext eC2 = new EducationalContext();
        eC2.setSource( "LOMv1.0" );
        eC2.setValue( "higher education" );
        List<EducationalContext> eCs = new ArrayList<EducationalContext>();
        eCs.add( eC1 );
        eCs.add( eC2 );
        educational.setContexts( eCs );

        /*
         * typicalAgeRange
         */
        EducationalTypicalAgeRange eTar1 = new EducationalTypicalAgeRange();
        EducationalTypicalAgeRange eTar2 = new EducationalTypicalAgeRange();
        List<LangString> lsTar1 = new ArrayList<LangString>();
        List<LangString> lsTar2 = new ArrayList<LangString>();
        LangString lsT11 = new LangString();
        LangString lsT12 = new LangString();
        LangString lsT21 = new LangString();
        LangString lsT22 = new LangString();
        lsT11.setLanguage( "pt" );
        lsT11.setValue( "18-" );
        lsT12.setLanguage( "en" );
        lsT12.setValue( "18-" );
        lsTar1.add( lsT11 );
        lsTar1.add( lsT12 );
        lsT21.setLanguage( "pt" );
        lsT21.setValue( "19-20" );
        lsT22.setLanguage( "en" );
        lsT22.setValue( "19-20" );
        lsTar2.add( lsT21 );
        lsTar2.add( lsT22 );
        eTar1.setStrings( lsTar1 );
        eTar2.setStrings( lsTar2 );
        List<EducationalTypicalAgeRange> lTars = new ArrayList<EducationalTypicalAgeRange>();
        lTars.add( eTar1 );
        lTars.add( eTar2 );
        educational.setTypicalAgeRanges( lTars );

        /*
         * difficulty
         */
        EducationalDifficulty eD = new EducationalDifficulty();
        eD.setSource( "LOMv1.0" );
        eD.setValue( "easy" );
        educational.setDifficulty( eD );

        /*
         * typicalLearningTime
         */
        EducationalTypicalLearningTime eTlt = new EducationalTypicalLearningTime();
        eTlt.setDuration( "PT1H30M" );
        EducationalTypicalLearningTimeDescription eDt1 = new EducationalTypicalLearningTimeDescription();
        EducationalTypicalLearningTimeDescription eDt2 = new EducationalTypicalLearningTimeDescription();
        LangString lsdd11 = new LangString();
        LangString lsdd12 = new LangString();
        LangString lsdd21 = new LangString();
        LangString lsdd22 = new LangString();
        lsdd11.setLanguage( "pt" );
        lsdd11.setValue( "Demora pouco" );
        lsdd12.setLanguage( "en" );
        lsdd12.setValue( "Don´t know" );
        lsdd21.setLanguage( "pt" );
        lsdd21.setValue( "Demora muito" );
        lsdd22.setLanguage( "en" );
        lsdd22.setValue( "Don´t know" );
        List<LangString> lsdds1 = new ArrayList<LangString>();
        List<LangString> lsdds2 = new ArrayList<LangString>();
        lsdds1.add( lsdd11 );
        lsdds1.add( lsdd12 );
        lsdds2.add( lsdd21 );
        lsdds2.add( lsdd22 );
        eDt1.setStrings( lsdds1 );
        eDt2.setStrings( lsdds2 );
        List<EducationalTypicalLearningTimeDescription> eDts = new ArrayList<EducationalTypicalLearningTimeDescription>();
        eDts.add( eDt1 );
        eDts.add( eDt2 );
        eTlt.setDescriptions( eDts );
        educational.setTypicalLearningTime( eTlt );

        /*
         * description
         */
        EducationalDescription eD1 = new EducationalDescription();
        EducationalDescription eD2 = new EducationalDescription();
        LangString lsd11 = new LangString();
        LangString lsd12 = new LangString();
        LangString lsd21 = new LangString();
        LangString lsd22 = new LangString();
        lsd11.setLanguage( "pt" );
        lsd11.setValue( "Muito legal" );
        lsd12.setLanguage( "en" );
        lsd12.setValue( "Very nice" );
        lsd21.setLanguage( "pt" );
        lsd21.setValue( "Muito chato" );
        lsd22.setLanguage( "en" );
        lsd22.setValue( "Annoying" );
        List<LangString> lsds1 = new ArrayList<LangString>();
        List<LangString> lsds2 = new ArrayList<LangString>();
        lsds1.add( lsd11 );
        lsds1.add( lsd12 );
        lsds2.add( lsd21 );
        lsds2.add( lsd22 );
        eD1.setStrings( lsds1 );
        eD2.setStrings( lsds2 );
        List<EducationalDescription> eDs = new ArrayList<EducationalDescription>();
        eDs.add( eD1 );
        eDs.add( eD2 );
        educational.setDescriptions( eDs );

        /*
         * language
         */
        EducationalLanguage eL1 = new EducationalLanguage();
        EducationalLanguage eL2 = new EducationalLanguage();
        eL1.setValue( "pt" );
        eL2.setValue( "en" );
        List<EducationalLanguage> eLs = new ArrayList<EducationalLanguage>();
        eLs.add( eL1 );
        eLs.add( eL2 );
        educational.setLanguages( eLs );


        List<Educational> lEducationals = new ArrayList<Educational>();
        lEducationals.add( educational );
        lEducationals.add( educational );
        lom.setEducationals( lEducationals );

    }


    /*
     * Prepara os dados de teste para a tag Rights.
     */
    private static void doRights() {

        /*
         * cost
         */
        RightsCost rCost = new RightsCost();
        rCost.setSource( "LOMv1.0" );
        rCost.setValue( "yes" );
        rights.setCost( rCost );

        /*
         * copyrightAndOtherRestrictions
         */
        RightsCopyrightAndOtherRestrictions rCopy = new RightsCopyrightAndOtherRestrictions();
        rCopy.setSource( "LOMv1.0" );
        rCopy.setValue( "no" );
        rights.setCopyrightAndOtherRestrictions( rCopy );

        /*
         * description
         */
        RightsDescription rDesc = new RightsDescription();
        LangString lDesc1 = new LangString();
        LangString lDesc2 = new LangString();
        lDesc1.setLanguage( "pt" );
        lDesc1.setValue( "Uma descrição..." );
        lDesc2.setLanguage( "en" );
        lDesc2.setValue( "A description..." );
        List<LangString> lDescs = new ArrayList<LangString>();
        lDescs.add( lDesc1 );
        lDescs.add( lDesc2 );
        rDesc.setStrings( lDescs );
        rights.setDescription( rDesc );

        lom.setRights( rights );

    }


    /*
     * Prepara os dados de teste para a tag Relation.
     */
    private static void doRelation() {

        /*
         * kind
         */
        RelationKind rK = new RelationKind();
        rK.setSource( "LOMv1.0" );
        rK.setValue( "ispartof" );
        relation.setKind( rK );

        /*
         * resource
         */
        RelationResource rRes = new RelationResource();
        List<RelationResourceIdentifier> lIdens = new ArrayList<RelationResourceIdentifier>();
        List<RelationResourceDescription> lDescs = new ArrayList<RelationResourceDescription>();
        RelationResourceIdentifier rIden1 = new RelationResourceIdentifier();
        RelationResourceIdentifier rIden2 = new RelationResourceIdentifier();
        rIden1.setCatalog( "URI" );
        rIden1.setEntry( "http://www.teste.com.br" );
        rIden2.setCatalog( "URI" );
        rIden2.setEntry( "http://www.teste2.com.br" );
        lIdens.add( rIden1 );
        lIdens.add( rIden2 );
        RelationResourceDescription rDesc1 = new RelationResourceDescription();
        RelationResourceDescription rDesc2 = new RelationResourceDescription();
        List<LangString> lDescs1 = new ArrayList<LangString>();
        List<LangString> lDescs2 = new ArrayList<LangString>();
        LangString lDesc11 = new LangString();
        LangString lDesc12 = new LangString();
        LangString lDesc21 = new LangString();
        LangString lDesc22 = new LangString();
        lDesc11.setLanguage( "pt" );
        lDesc11.setValue( "Testando" );
        lDesc12.setLanguage( "en" );
        lDesc12.setValue( "Testing" );
        lDesc21.setLanguage( "pt" );
        lDesc21.setValue( "Mais testes" );
        lDesc22.setLanguage( "en" );
        lDesc22.setValue( "More tests" );
        lDescs1.add( lDesc11 );
        lDescs1.add( lDesc12 );
        lDescs2.add( lDesc21 );
        lDescs2.add( lDesc22 );
        rDesc1.setStrings( lDescs1 );
        rDesc2.setStrings( lDescs2 );
        lDescs.add( rDesc1 );
        lDescs.add( rDesc2 );
        rRes.setIdentifiers( lIdens );
        rRes.setDescriptions( lDescs );
        relation.setResource( rRes );


        List<Relation> lRelations = new ArrayList<Relation>();
        lRelations.add( relation );
        lRelations.add( relation );
        lom.setRelations( lRelations );

    }


    /*
     * Prepara os dados de teste para a tag Annotation.
     */
    private static void doAnnotation() {

        /*
         * entity
         */
        AnnotationEntity aEnt = new AnnotationEntity();
        aEnt.setValue( "BEGIN:VCARD&#13;&#10;VERSION:2.1&#13;&#10;FN:Joe Author&#13;&#10;END:VCARD" );
        annotation.setEntity( aEnt );

        /*
         * date
         */
        AnnotationDate aDat = new AnnotationDate();
        aDat.setDateTime( "2001-07-30T10:14:35.5+01:00" );
        AnnotationDateDescription aDatDesc = new AnnotationDateDescription();
        List<LangString> lDateDescs = new ArrayList<LangString>();
        LangString lDateDesc1 = new LangString();
        LangString lDateDesc2 = new LangString();
        lDateDesc1.setLanguage( "pt" );
        lDateDesc1.setValue( "Uma descrição legal" );
        lDateDesc2.setLanguage( "en" );
        lDateDesc2.setValue( "A nice description" );
        lDateDescs.add( lDateDesc1 );
        lDateDescs.add( lDateDesc2 );
        aDatDesc.setStrings( lDateDescs );
        aDat.setDescription( aDatDesc );
        annotation.setDate( aDat );

        /*
         * description
         */
        AnnotationDescription aDesc = new AnnotationDescription();
        List<LangString> lDescs = new ArrayList<LangString>();
        LangString lDesc1 = new LangString();
        LangString lDesc2 = new LangString();
        lDesc1.setLanguage( "pt" );
        lDesc1.setValue( "Uma descrição legal 1" );
        lDesc2.setLanguage( "en" );
        lDesc2.setValue( "A nice description 1" );
        lDescs.add( lDesc1 );
        lDescs.add( lDesc2 );
        aDesc.setStrings( lDescs );
        annotation.setDescription( aDesc );

        List<Annotation> lAnnotations = new ArrayList<Annotation>();
        lAnnotations.add( annotation );
        lAnnotations.add( annotation );
        lom.setAnnotations( lAnnotations );

    }


    /*
     * Prepara os dados de teste para a tag Classification.
     */
    private static void doClassification() {

        /*
         * purpose
         */
        ClassificationPurpose cPur = new ClassificationPurpose();
        cPur.setSource( "LOMv1.0" );
        cPur.setValue( "discipline" );
        classification.setPurpose( cPur );

        /*
         * taxonPath
         */
        ClassificationTaxonPath cTaxonPath = new ClassificationTaxonPath();
        ClassificationTaxonPathSource cSource = new ClassificationTaxonPathSource();
        List<LangString> lSources = new ArrayList<LangString>();
        LangString lSource1 = new LangString();
        LangString lSource2 = new LangString();
        lSource1.setLanguage( "pt" );
        lSource1.setValue( "Fonte" );
        lSource2.setLanguage( "en" );
        lSource2.setValue( "Source" );
        lSources.add( lSource1 );
        lSources.add( lSource2 );
        cSource.setStrings( lSources );
        cTaxonPath.setSource( cSource );

        ClassificationTaxonPathTaxon t1 = new ClassificationTaxonPathTaxon();
        ClassificationTaxonPathTaxon t2 = new ClassificationTaxonPathTaxon();
        t1.setId( "A" );
        t2.setId( "A.1" );
        ClassificationTaxonPathTaxonEntry cEnt1 = new ClassificationTaxonPathTaxonEntry();
        ClassificationTaxonPathTaxonEntry cEnt2 = new ClassificationTaxonPathTaxonEntry();
        LangString cEnts11 = new LangString();
        LangString cEnts12 = new LangString();
        LangString cEnts21 = new LangString();
        LangString cEnts22 = new LangString();
        cEnts11.setLanguage( "pt" );
        cEnts11.setValue( "Teste 1" );
        cEnts12.setLanguage( "en" );
        cEnts12.setValue( "Test 1" );
        cEnts21.setLanguage( "pt" );
        cEnts21.setValue( "Teste 2" );
        cEnts22.setLanguage( "en" );
        cEnts22.setValue( "Test 2" );
        List<LangString> cEnts1 = new ArrayList<LangString>();
        List<LangString> cEnts2 = new ArrayList<LangString>();
        cEnts1.add( cEnts11 );
        cEnts1.add( cEnts12 );
        cEnts2.add( cEnts21 );
        cEnts2.add( cEnts22 );
        cEnt1.setStrings( cEnts1 );
        cEnt2.setStrings( cEnts2 );
        t1.setEntry( cEnt1 );
        t2.setEntry( cEnt2 );
        List<ClassificationTaxonPathTaxon> cTs = new ArrayList<ClassificationTaxonPathTaxon>();
        cTs.add( t1 );
        cTs.add( t2 );
        cTaxonPath.setTaxons( cTs );
        List<ClassificationTaxonPath> cTaxonPaths = new ArrayList<ClassificationTaxonPath>();
        cTaxonPaths.add( cTaxonPath );
        cTaxonPaths.add( cTaxonPath );
        classification.setTaxonPaths( cTaxonPaths );

        /*
         * description
         */
        ClassificationDescription cDesc = new ClassificationDescription();
        List<LangString> lDescs = new ArrayList<LangString>();
        LangString lDesc1 = new LangString();
        LangString lDesc2 = new LangString();
        lDesc1.setLanguage( "pt" );
        lDesc1.setValue( "Descrição" );
        lDesc2.setLanguage( "en" );
        lDesc2.setValue( "Description" );
        lDescs.add( lDesc1 );
        lDescs.add( lDesc2 );
        cDesc.setStrings( lDescs );
        classification.setDescription( cDesc );

        /*
         * keyword
         */
        ClassificationKeyword cKey1 = new ClassificationKeyword();
        ClassificationKeyword cKey2 = new ClassificationKeyword();
        List<ClassificationKeyword> lKeys = new ArrayList<ClassificationKeyword>();
        List<LangString> lKeys1 = new ArrayList<LangString>();
        List<LangString> lKeys2 = new ArrayList<LangString>();
        LangString lKey11 = new LangString();
        LangString lKey12 = new LangString();
        LangString lKey21 = new LangString();
        LangString lKey22 = new LangString();
        lKey11.setLanguage( "pt" );
        lKey11.setValue( "Palavra" );
        lKey12.setLanguage( "en" );
        lKey12.setValue( "Word" );
        lKey21.setLanguage( "pt" );
        lKey21.setValue( "Palavra-chave" );
        lKey22.setLanguage( "en" );
        lKey22.setValue( "Keyword" );
        lKeys1.add( lKey11 );
        lKeys1.add( lKey12 );
        lKeys2.add( lKey21 );
        lKeys2.add( lKey22 );
        cKey1.setStrings( lKeys1 );
        cKey2.setStrings( lKeys2 );
        lKeys.add( cKey1 );
        lKeys.add( cKey2 );
        classification.setKeywords( lKeys );

        List<Classification> lClassifications = new ArrayList<Classification>();
        lClassifications.add( classification );
        lClassifications.add( classification );
        lom.setClassifications( lClassifications );

    }

}
