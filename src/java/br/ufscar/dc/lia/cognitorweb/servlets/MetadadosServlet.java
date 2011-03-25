/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.servlets;

import br.ufscar.dc.lia.cognitorweb.dao.*;
import br.ufscar.dc.lia.cognitorweb.entidades.*;
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
import br.ufscar.dc.lia.cognitorweb.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.*;

/**
 * Servlet para gravação dos metadados de um objeto de aprendizagem.
 *
 * @author David Buzatto
 */
public class MetadadosServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @SuppressWarnings( value = "unchecked" )
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ResourceBundle rs = Uteis.getBundle( request.getLocale().getLanguage() );

        Map< String, String > configs = ( HashMap< String, String > )
                getServletContext().getAttribute( "configs" );

        out.print( "{ success: " );

        try {

            Dao dao = ( Dao ) SpringUtil.getBean( "dao" );
            HibernateTemplate ht = dao.getHibernateTemplate();
            Transaction t = ht.getSessionFactory().getCurrentSession().beginTransaction();
            t.begin();

            long id = Long.parseLong( request.getParameter( "id" ) );
            String tipo = request.getParameter( "tipo" );
            String acao = request.getParameter( "acao" );

            Metadata mt = null;

            if ( tipo.equals( "material" ) ) {
                mt = ( ( Material ) ht.load( Material.class, id ) ).getMetadata();
            } else if ( tipo.equals( "grupo" ) ) {
                mt = ( ( Grupo ) ht.load( Grupo.class, id ) ).getMetadata();
            } else if ( tipo.equals( "pagina" ) ) {
                mt = ( ( Pagina ) ht.load( Pagina.class, id ) ).getMetadata();
            } else if ( tipo.equals( "imagem" ) ) {
                mt = ( ( Imagem ) ht.load( Imagem.class, id ) ).getMetadata();
            } else if ( tipo.equals( "video" ) ) {
                mt = ( ( Video ) ht.load( Video.class, id ) ).getMetadata();
            } else if ( tipo.equals( "som" ) ) {
                mt = ( ( Som ) ht.load( Som.class, id ) ).getMetadata();
            }

            Lom lom = mt.getLom();

            if ( acao.equals( "salvar" ) ) {

                boolean print = new Boolean( configs.get( "debug" ) );

                if ( print ) {

                    System.out.println( lom.getXmlns() );

                    System.out.println( "idOA: " + id );
                    System.out.println( "tipoOA: " + tipo );

                }

                /*
                 * O campo language de meta-metadata representa o idioma de TODAS
                 * as LangStrings do conjunto de metadados.
                 */
                String language = request.getParameter( "mtLang" );

                doGeneral( request, ht, lom, language, print );
                doLifeCycle( request, ht, lom, language, print );
                doMetaMetadata( request, ht, lom, language, print );
                doTechnical( request, ht, lom, language, print );
                doEducational( request, ht, lom, language, print );
                doRights( request, ht, lom, language, print );
                doRelation( request, ht, lom, language, print );
                doAnnotation( request, ht, lom, language, print );
                doClassification( request, ht, lom, language, print );

                t.commit();

                out.print( "true" );

            } else if ( acao.equals( "obter" ) ) {

                StringBuilder sb = new StringBuilder();

                out.print( "true, " );
                out.print( "data: { " );
                out.print( gerarSaida( lom ) );
                out.print( "}" );

            }

        } catch ( Exception exc ) {
            
            out.print( Uteis.createErrorMessage( exc, new Boolean( configs.get( "debug" ) ) ) );

        } finally {
            out.print( "}" );
            out.close();
        }
        
    }

    private void doGeneral( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String genCat = request.getParameter( "genCat" );
        String genEntry = request.getParameter( "genEntry" );
        String genTitulo = request.getParameter( "genTitulo" );
        String genLang = request.getParameter( "genLang" );
        String genDesc = request.getParameter( "genDesc" );
        String genPc = request.getParameter( "genPc" );
        String genCob = request.getParameter( "genCob" );
        String genEstructSrc = request.getParameter( "genEstructSrc" );
        String genEstruct = request.getParameter( "genEstruct" );
        String genAgLevelSrc = request.getParameter( "genAgLevelSrc" );
        String genAgLevel = request.getParameter( "genAgLevel" );

        // DEVE exitir a garantia que existe uma instancia de cada objeto
        General general = lom.getGeneral();
        GeneralIdentifier gIdent = general.getIdentifiers().get( 0 );
        GeneralTitle gTitle = general.getTitle();
        GeneralLanguage gLang = general.getLanguages().get( 0 );
        GeneralDescription gDesc = general.getDescriptions().get( 0 );
        GeneralKeyword gKey = general.getKeywords().get( 0 );
        GeneralCoverage gCov = general.getCoverages().get( 0 );
        GeneralStructure gStruc = general.getStructure();
        GeneralAggregationLevel gAgg = general.getAggregationLevel();

        gIdent.setCatalog( genCat );
        gIdent.setEntry( genEntry );
        ht.update( gIdent );

        LangString lsTitle = gTitle.getStrings().get( 0 );
        lsTitle.setLanguage( language );
        lsTitle.setValue( genTitulo );
        ht.update( lsTitle );

        gLang.setValue( genLang );
        ht.update( gLang );

        LangString lsDesc = gDesc.getStrings().get( 0 );
        lsDesc.setLanguage( language );
        lsDesc.setValue( genDesc );
        ht.update( lsDesc );

        LangString lsKey = gKey.getStrings().get( 0 );
        lsKey.setLanguage( language );
        lsKey.setValue( genPc );
        ht.update( lsKey );

        LangString lsCov = gCov.getStrings().get( 0 );
        lsCov.setLanguage( language );
        lsCov.setValue( genCob );
        ht.update( lsCov );

        gStruc.setSource( genEstructSrc );
        gStruc.setValue( genEstruct );
        ht.update( gStruc );

        gAgg.setSource( genAgLevelSrc );
        gAgg.setValue( genAgLevel );
        ht.update( gAgg );

        if ( print ) {
            System.out.println( "general" );
            System.out.println( genCat  );
            System.out.println( genEntry );
            System.out.println( genTitulo );
            System.out.println( genLang );
            System.out.println( genDesc );
            System.out.println( genPc );
            System.out.println( genCob );
            System.out.println( genEstructSrc );
            System.out.println( genEstruct );
            System.out.println( genAgLevelSrc );
            System.out.println( genAgLevel );
        }

    }

    private void doLifeCycle( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String lcyVer = request.getParameter( "lcVer" );
        String lcyStatSrc = request.getParameter( "lcStatSrc" );
        String lcyStat = request.getParameter( "lcStat" );
        String lcyRoleSrc = request.getParameter( "lcRoleSrc" );
        String lcyRole = request.getParameter( "lcRole" );
        String lcyEnt = request.getParameter( "lcEnt" );
        String lcyDate = request.getParameter( "lcDate" );
        String lcyDesc = request.getParameter( "lcDesc" );

        LifeCycle lifeCycle = lom.getLifeCycle();

        LifeCycleVersion lcVersion = lifeCycle.getVersion();
        LifeCycleStatus lcStatus = lifeCycle.getStatus();
        LifeCycleContribute lcContrib = lifeCycle.getContributes().get( 0 );

        LangString lsVersion = lcVersion.getStrings().get( 0 );
        lsVersion.setLanguage( language );
        lsVersion.setValue( lcyVer );
        ht.update( lsVersion );

        lcStatus.setSource( lcyStatSrc );
        lcStatus.setValue( lcyStat );
        ht.update( lcStatus );
        
        LifeCycleContributeRole lcRole = lcContrib.getRole();
        LifeCycleContributeEntity lcEntity = lcContrib.getEntities().get( 0 );
        LifeCycleContributeDate lcDate = lcContrib.getDate();

        lcRole.setSource( lcyRoleSrc );
        lcRole.setValue( lcyRole );
        ht.update( lcRole );

        lcEntity.setValue( lcyEnt );
        ht.update( lcEntity );

        lcDate.setDateTime( lcyDate );
        ht.update( lcDate );

        LangString lcDateDesc = lcDate.getDescription().getStrings().get( 0 );
        lcDateDesc.setLanguage( language );
        lcDateDesc.setValue( lcyDesc );
        ht.update( lcDateDesc );

        if ( print ) {
            System.out.println( "life-cycle" );
            System.out.println( lcyVer );
            System.out.println( lcyStatSrc );
            System.out.println( lcyStat );
            System.out.println( lcyRoleSrc );
            System.out.println( lcyRole );
            System.out.println( lcyEnt );
            System.out.println( lcyDate );
            System.out.println( lcyDesc );
        }

    }

    private void doMetaMetadata( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String mtCat = request.getParameter( "mtCat" );
        String mtEntryVal = request.getParameter( "mtEntryVal" );
        String mtRoleSrc = request.getParameter( "mtRoleSrc" );
        String mtRole = request.getParameter( "mtRole" );
        String mtEnt = request.getParameter( "mtEnt" );
        String mtDate = request.getParameter( "mtDate" );
        String mtDesc = request.getParameter( "mtDesc" );
        String mtSch = request.getParameter( "mtSch" );
        String mtLang = request.getParameter( "mtLang" );

        MetaMetadata metaMetadata = lom.getMetaMetadata();

        MetaMetadataIdentifier mIdent = metaMetadata.getIdentifiers().get( 0 );
        MetaMetadataContribute mContrib = metaMetadata.getContributes().get( 0 );
        MetaMetadataMetadataSchema mMtSchema = metaMetadata.getMetadataSchemas().get( 0 );
        
        // ou o parâmetro language
        metaMetadata.setLanguage( mtLang );
        ht.update( metaMetadata );

        mIdent.setCatalog( mtCat );
        mIdent.setEntry( mtEntryVal );
        ht.update( mIdent );

        MetaMetadataContributeRole mRole = mContrib.getRole();
        MetaMetadataContributeEntity mEnt = mContrib.getEntities().get( 0 );
        MetaMetadataContributeDate mDate = mContrib.getDate();

        mRole.setSource( mtRoleSrc );
        mRole.setValue( mtRole );
        ht.update( mRole );

        mEnt.setValue( mtEnt );
        ht.update( mEnt );

        mDate.setDateTime( mtDate );
        ht.update( mDate );

        MetaMetadataContributeDateDescription mDateDesc = mDate.getDescription();
        LangString lsDateDesc = mDateDesc.getStrings().get( 0 );
        lsDateDesc.setLanguage( language );
        lsDateDesc.setValue( mtDesc );
        ht.update( lsDateDesc );

        mMtSchema.setValue( mtSch );
        ht.update( mMtSchema );

        if ( print ) {
            System.out.println( "meta-metadata" );
            System.out.println( mtCat );
            System.out.println( mtEntryVal );
            System.out.println( mtRoleSrc );
            System.out.println( mtRole );
            System.out.println( mtEnt );
            System.out.println( mtDate );
            System.out.println( mtDesc );
            System.out.println( mtSch );
            System.out.println( mtLang );
        }

    }

    private void doTechnical( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String tcFor = request.getParameter( "tcFor" );
        String tcSize = request.getParameter( "tcSize" );
        String tcLoc = request.getParameter( "tcLoc" );
        String tcTypeSrc = request.getParameter( "tcTypeSrc" );
        String tcType = request.getParameter( "tcType" );
        String tcNameSrc = request.getParameter( "tcNameSrc" );
        String tcName = request.getParameter( "tcName" );
        String tcMinVer = request.getParameter( "tcMinVer" );
        String tcMaxVer = request.getParameter( "tcMaxVer" );
        String tcInstRem = request.getParameter( "tcInstRem" );
        String tcInstOthInst = request.getParameter( "tcInstOthInst" );
        String tcDur = request.getParameter( "tcDur" );
        String tcDesc = request.getParameter( "tcDesc" );

        Technical technical = lom.getTechnical();

        TechnicalFormat tFor = technical.getFormats().get( 0 );
        technical.setSize( tcSize );
        TechnicalLocation tLoc = technical.getLocations().get( 0 );
        TechnicalRequirement tReq = technical.getRequirements().get( 0 );
        TechnicalInstallationRemarks tInst = technical.getInstallationRemarks();
        TechnicalOtherPlatformRequirements tOth = technical.getOtherPlatformRequirements();
        TechnicalDuration tDur = technical.getDuration();
        ht.update( technical );

        tFor.setValue( tcFor );
        ht.update( tFor );

        tLoc.setValue( tcLoc );
        ht.update( tLoc );

        TechnicalRequirementOrComposite tReqOrComp = tReq.getOrComposites().get( 0 );
        TechnicalRequirementOrCompositeType tReqOrComType = tReqOrComp.getType();
        TechnicalRequirementOrCompositeName tReqOrComName = tReqOrComp.getName();
        tReqOrComp.setMinimumVersion( tcMinVer );
        tReqOrComp.setMaximumVersion( tcMaxVer );
        ht.update( tReqOrComp );

        tReqOrComType.setSource( tcTypeSrc );
        tReqOrComType.setValue( tcType );
        ht.update( tReqOrComType );

        tReqOrComName.setSource( tcNameSrc );
        tReqOrComName.setValue( tcName );
        ht.update( tReqOrComName );

        LangString lsInst = tInst.getStrings().get( 0 );
        lsInst.setLanguage( language );
        lsInst.setValue( tcInstRem );
        ht.update( lsInst );

        LangString lsOth = tOth.getStrings().get( 0 );
        lsOth.setLanguage( language );
        lsOth.setValue( tcInstOthInst );
        ht.update( lsOth );

        tDur.setDuration( tcDur );
        ht.update( tDur );
        TechnicalDurationDescription tDurDesc = tDur.getDescription();
        LangString lsDurDesc = tDurDesc.getStrings().get( 0 );
        lsDurDesc.setLanguage( language );
        lsDurDesc.setValue( tcDesc );
        ht.update( lsDurDesc );

        if ( print ) {
            System.out.println( "technical" );
            System.out.println( tcFor );
            System.out.println( tcSize );
            System.out.println( tcLoc );
            System.out.println( tcTypeSrc );
            System.out.println( tcType );
            System.out.println( tcNameSrc );
            System.out.println( tcName );
            System.out.println( tcMinVer );
            System.out.println( tcMaxVer );
            System.out.println( tcInstRem );
            System.out.println( tcInstOthInst );
            System.out.println( tcDur );
            System.out.println( tcDesc );
        }

    }

    private void doEducational( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String edTiSrc = request.getParameter( "edTiSrc" );
        String edTi = request.getParameter( "edTi" );
        String edTrSrc = request.getParameter( "edTrSrc" );
        String edTr = request.getParameter( "edTr" );
        String edNiSrc = request.getParameter( "edNiSrc" );
        String edNi = request.getParameter( "edNi" );
        String edDsSrc = request.getParameter( "edDsSrc" );
        String edDs = request.getParameter( "edDs" );
        String edPdSrc = request.getParameter( "edPdSrc" );
        String edPd = request.getParameter( "edPd" );
        String edConSrc = request.getParameter( "edConSrc" );
        String edCon = request.getParameter( "edCon" );
        String edTar = request.getParameter( "edTar" );
        String edDiSrc = request.getParameter( "edDiSrc" );
        String edDi = request.getParameter( "edDi" );
        String edDur = request.getParameter( "edDur" );
        String edDurDesc = request.getParameter( "edDurDesc" );
        String edDesc = request.getParameter( "edDesc" );
        String edLang = request.getParameter( "edLang" );

        Educational educational = lom.getEducationals().get( 0 );

        EducationalInteractivityType eIType = educational.getInteractivityType();
        EducationalLearningResourceType eLRT = educational.getLearningResourceTypes().get( 0 );
        EducationalInteractivityLevel eILevel = educational.getInteractivityLevel();
        EducationalSemanticDensity eSem = educational.getSemanticDensity();
        EducationalIntendedEndUserRole eInt = educational.getIntendedEndUserRoles().get( 0 );
        EducationalContext eCont = educational.getContexts().get( 0 );
        EducationalTypicalAgeRange eTar = educational.getTypicalAgeRanges().get( 0 );
        EducationalDifficulty eDif = educational.getDifficulty();
        EducationalTypicalLearningTime eTlt = educational.getTypicalLearningTime();
        EducationalDescription eDesc = educational.getDescriptions().get( 0 );
        EducationalLanguage eLang = educational.getLanguages().get( 0 );

        eIType.setSource( edTiSrc );
        eIType.setValue( edTi );
        ht.update( eIType );

        eLRT.setSource( edTrSrc );
        eLRT.setValue( edTr );
        ht.update( eLRT );

        eILevel.setSource( edNiSrc );
        eILevel.setValue( edNi );
        ht.update( eILevel );

        eSem.setSource( edDsSrc );
        eSem.setValue( edDs );
        ht.update( eSem );

        eInt.setSource( edPdSrc );
        eInt.setValue( edPd );
        ht.update( eInt );

        eCont.setSource( edConSrc );
        eCont.setValue( edCon );
        ht.update( eCont );

        LangString lsTar = eTar.getStrings().get( 0 );
        lsTar.setLanguage( language );
        lsTar.setValue( edTar );
        ht.update( lsTar );

        eDif.setSource( edDiSrc );
        eDif.setValue( edDi );
        ht.update( eDif );

        eTlt.setDuration( edDur );
        ht.update( eTlt );
        EducationalTypicalLearningTimeDescription eTltDesc = eTlt.getDescriptions().get( 0 );
        LangString lsTltDesc = eTltDesc.getStrings().get( 0 );
        lsTltDesc.setLanguage( language );
        lsTltDesc.setValue( edDurDesc );
        ht.update( lsTltDesc );

        LangString lsDesc = eDesc.getStrings().get( 0 );
        lsDesc.setLanguage( language );
        lsDesc.setValue( edDesc );
        ht.update( lsDesc );

        eLang.setValue( edLang );
        ht.update( eLang );

        if ( print ) {
            System.out.println( "educational" );
            System.out.println( edTiSrc );
            System.out.println( edTi );
            System.out.println( edTrSrc );
            System.out.println( edTr );
            System.out.println( edNiSrc );
            System.out.println( edNi );
            System.out.println( edDsSrc );
            System.out.println( edDs );
            System.out.println( edPdSrc );
            System.out.println( edPd );
            System.out.println( edConSrc );
            System.out.println( edCon );
            System.out.println( edTar );
            System.out.println( edDiSrc );
            System.out.println( edDi );
            System.out.println( edDur );
            System.out.println( edDurDesc );
            System.out.println( edDesc );
            System.out.println( edLang );
        }

    }

    private void doRights( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String rigCostSrc = request.getParameter( "rigCostSrc" );
        String rigCost = request.getParameter( "rigCost" );
        String rigCopSrc = request.getParameter( "rigCopSrc" );
        String rigCop = request.getParameter( "rigCop" );
        String rigDesc = request.getParameter( "rigDesc" );

        Rights rights = lom.getRights();

        RightsCost rCost = rights.getCost();
        RightsCopyrightAndOtherRestrictions rCopy = rights.getCopyrightAndOtherRestrictions();
        RightsDescription rDesc = rights.getDescription();

        rCost.setSource( rigCostSrc );
        rCost.setValue( rigCost );
        ht.update( rCost );

        rCopy.setSource( rigCopSrc );
        rCopy.setValue( rigCop );
        ht.update( rCopy );

        LangString lsDesc = rDesc.getStrings().get( 0 );
        lsDesc.setLanguage( language );
        lsDesc.setValue( rigDesc );
        ht.update( lsDesc );

        if ( print ) {
            System.out.println( "rights" );
            System.out.println( rigCostSrc );
            System.out.println( rigCost );
            System.out.println( rigCopSrc );
            System.out.println( rigCop );
            System.out.println( rigDesc );
        }

    }

    private void doRelation( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String reKinSrc = request.getParameter( "reKinSrc" );
        String reKin = request.getParameter( "reKin" );
        String reCat = request.getParameter( "reCat" );
        String reEntry = request.getParameter( "reEntry" );
        String reDesc = request.getParameter( "reDesc" );

        Relation relation = lom.getRelations().get( 0 );

        RelationKind rKind = relation.getKind();
        RelationResource rRes = relation.getResource();

        rKind.setSource( reKinSrc );
        rKind.setValue( reKin );
        ht.update( rKind );

        RelationResourceIdentifier rResIdent = rRes.getIdentifiers().get( 0 );
        RelationResourceDescription rResDesc = rRes.getDescriptions().get( 0 );

        rResIdent.setCatalog( reCat );
        rResIdent.setEntry( reEntry );
        ht.update( rResIdent );

        LangString lsResDesc = rResDesc.getStrings().get( 0 );
        lsResDesc.setLanguage( language );
        lsResDesc.setValue( reDesc );
        ht.save( lsResDesc );
        
        if ( print ) {
            System.out.println( "relation" );
            System.out.println( reKinSrc );
            System.out.println( reKin );
            System.out.println( reCat );
            System.out.println( reEntry );
            System.out.println( reDesc );
        }

    }

    private void doAnnotation( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String anEnt = request.getParameter( "anEnt" );
        String anDate = request.getParameter( "anDate" );
        String anDateDesc = request.getParameter( "anDateDesc" );
        String anDesc = request.getParameter( "anDesc" );

        Annotation annotation = lom.getAnnotations().get( 0 );

        AnnotationEntity aEnt = annotation.getEntity();
        AnnotationDate aDate = annotation.getDate();
        AnnotationDescription aDesc = annotation.getDescription();

        aEnt.setValue( anEnt );
        ht.update( aEnt );

        aDate.setDateTime( anDate );
        AnnotationDateDescription aDateDesc = aDate.getDescription();
        LangString lsDateDesc = aDateDesc.getStrings().get( 0 );
        lsDateDesc.setLanguage( language );
        lsDateDesc.setValue( anDateDesc );
        ht.update( lsDateDesc );

        LangString lsDesc = aDesc.getStrings().get( 0 );
        lsDesc.setLanguage( language );
        lsDesc.setValue( anDesc );
        ht.update( lsDesc );

        if ( print ) {
            System.out.println( "annotation" );
            System.out.println( anEnt );
            System.out.println( anDate );
            System.out.println( anDateDesc );
            System.out.println( anDesc );
        }

    }

    private void doClassification( HttpServletRequest request,
            HibernateTemplate ht,
            Lom lom,
            String language,
            boolean print ) {

        String clPurpSrc = request.getParameter( "clPurpSrc" );
        String clPurp = request.getParameter( "clPurp" );
        String clTaxSrc = request.getParameter( "clTaxSrc" );
        String clTaxTaxonId = request.getParameter( "clTaxTaxonId" );
        String clTaxTaxonEntry = request.getParameter( "clTaxTaxonEntry" );
        String clDesc = request.getParameter( "clDesc" );
        String clKey = request.getParameter( "clKey" );

        Classification classification = lom.getClassifications().get( 0 );

        ClassificationPurpose cPurp = classification.getPurpose();
        ClassificationTaxonPath cTaxonPath = classification.getTaxonPaths().get( 0 );
        ClassificationDescription cDesc = classification.getDescription();
        ClassificationKeyword cKey = classification.getKeywords().get( 0 );

        cPurp.setSource( clPurpSrc );
        cPurp.setValue( clPurp );
        ht.update( cPurp );

        ClassificationTaxonPathSource cTaxonPathSource = cTaxonPath.getSource();
        ClassificationTaxonPathTaxon cTaxonPathTaxon = cTaxonPath.getTaxons().get( 0 );
        LangString lsTaxonPathSource = cTaxonPathSource.getStrings().get( 0 );
        lsTaxonPathSource.setLanguage( language );
        lsTaxonPathSource.setValue( clTaxSrc );

        cTaxonPathTaxon.setId( clTaxTaxonId );
        ht.update( cTaxonPathTaxon );
        ClassificationTaxonPathTaxonEntry cTaxonPathTaxonEntry = cTaxonPathTaxon.getEntry();
        LangString lsTaxonPathTaxonEntry = cTaxonPathTaxonEntry.getStrings().get( 0 );
        lsTaxonPathTaxonEntry.setLanguage( language );
        lsTaxonPathTaxonEntry.setValue( clTaxTaxonEntry );
        ht.update( lsTaxonPathTaxonEntry );

        LangString lsDesc = cDesc.getStrings().get( 0 );
        lsDesc.setLanguage( language );
        lsDesc.setValue( clDesc );
        ht.update( lsDesc );

        LangString lsKey = cKey.getStrings().get( 0 );
        lsKey.setLanguage( language );
        lsKey.setValue( clKey );
        ht.update( lsKey );

        if ( print ) {
            System.out.println( "classification" );
            System.out.println( clPurpSrc );
            System.out.println( clPurp );
            System.out.println( clTaxSrc );
            System.out.println( clTaxTaxonId );
            System.out.println( clTaxTaxonEntry );
            System.out.println( clDesc );
            System.out.println( clKey );
        }

    }

    private String gerarSaida( Lom lom ) throws Exception {

        StringBuilder sb = new StringBuilder();

        // general
        General general = lom.getGeneral();
        GeneralIdentifier gIdent = general.getIdentifiers().get( 0 );
        LangString lsTitle = general.getTitle().getStrings().get( 0 );
        GeneralLanguage gLang = general.getLanguages().get( 0 );
        LangString lsDesc = general.getDescriptions().get( 0 ).getStrings().get( 0 );
        LangString lsKey = general.getKeywords().get( 0 ).getStrings().get( 0 );
        LangString lsCov = general.getCoverages().get( 0 ).getStrings().get( 0 );
        GeneralStructure gStruc = general.getStructure();
        GeneralAggregationLevel gAgg = general.getAggregationLevel();

        sb.append( "genCat: '" + gIdent.getCatalog() + "'," );
        sb.append( "genEntry: '" + gIdent.getEntry() + "'," );
        sb.append( "genTitulo: '" + lsTitle.getValue() + "'," );
        sb.append( "genLang: '" + gLang.getValue() + "'," );
        sb.append( "genDesc: '" + lsDesc.getValue().replace( "\n", "\\n" ) + "'," );
        sb.append( "genPc: '" + lsKey.getValue() + "'," );
        sb.append( "genCob: '" + lsCov.getValue().replace( "\n", "\\n" ) + "'," );
        sb.append( "genEstructSrc: '" + gStruc.getSource() + "'," );
        sb.append( "genEstruct: '" + gStruc.getValue() + "'," );
        sb.append( "genAgLevelSrc: '" + gAgg.getSource() + "'," );
        sb.append( "genAgLevel: '" + gAgg.getValue() + "'," );


        // lifeCycle
        LifeCycle lifeCycle = lom.getLifeCycle();
        LangString lsVersion = lifeCycle.getVersion().getStrings().get( 0 );
        LifeCycleStatus lcStatus = lifeCycle.getStatus();
        LifeCycleContribute lcContrib = lifeCycle.getContributes().get( 0 );
        LifeCycleContributeRole lcRole = lcContrib.getRole();
        LifeCycleContributeEntity lcEntity = lcContrib.getEntities().get( 0 );
        LifeCycleContributeDate lcDate = lcContrib.getDate();
        LangString lsDateDesc = lcDate.getDescription().getStrings().get( 0 );

        sb.append( "lcVer: '" + lsVersion.getValue() + "', " );
        sb.append( "lcStatSrc: '" + lcStatus.getSource() + "', " );
        sb.append( "lcStat: '" + lcStatus.getValue() + "', " );
        sb.append( "lcRoleSrc: '" + lcRole.getSource() + "', " );
        sb.append( "lcRole: '" + lcRole.getValue() + "', " );
        sb.append( "lcEnt: '" + lcEntity.getValue() + "', " );
        sb.append( "lcDate: '" + lcDate.getDateTime() + "', " );
        sb.append( "lcDesc: '" + lsDateDesc.getValue().replace( "\n", "\\n" ) + "', " );

        
        // meta-metadata
        MetaMetadata metaMetadata = lom.getMetaMetadata();
        MetaMetadataIdentifier mIdent = metaMetadata.getIdentifiers().get( 0 );
        MetaMetadataContribute mContrib = metaMetadata.getContributes().get( 0 );
        MetaMetadataMetadataSchema mMtSchema = metaMetadata.getMetadataSchemas().get( 0 );
        MetaMetadataContributeRole mRole = mContrib.getRole();
        MetaMetadataContributeEntity mEnt = mContrib.getEntities().get( 0 );
        MetaMetadataContributeDate mDate = mContrib.getDate();
        LangString lsMtDateDesc = mDate.getDescription().getStrings().get( 0 );

        sb.append( "mtCat: '" + mIdent.getCatalog() + "', " );
        sb.append( "mtEntryVal: '" + mIdent.getEntry() + "', " );
        sb.append( "mtRoleSrc: '" + mRole.getSource() + "', " );
        sb.append( "mtRole: '" + mRole.getValue() + "', " );
        sb.append( "mtEnt: '" + mEnt.getValue() + "', " );
        sb.append( "mtDate: '" + mDate.getDateTime() + "', " );
        sb.append( "mtDesc: '" + lsMtDateDesc.getValue().replace( "\n", "\\n" ) + "', " );
        sb.append( "mtSch: '" + mMtSchema.getValue() + "', " );
        sb.append( "mtLang: '" + metaMetadata.getLanguage() + "', " );


        // technical
        Technical technical = lom.getTechnical();
        TechnicalFormat tFor = technical.getFormats().get( 0 );
        TechnicalLocation tLoc = technical.getLocations().get( 0 );
        TechnicalRequirement tReq = technical.getRequirements().get( 0 );
        LangString lsInst = technical.getInstallationRemarks().getStrings().get( 0 );
        LangString lsOth = technical.getOtherPlatformRequirements().getStrings().get( 0 );
        TechnicalDuration tDur = technical.getDuration();
        TechnicalRequirementOrComposite tReqOrComp = tReq.getOrComposites().get( 0 );
        TechnicalRequirementOrCompositeType tReqOrComType = tReqOrComp.getType();
        TechnicalRequirementOrCompositeName tReqOrComName = tReqOrComp.getName();
        LangString lsDurDesc = tDur.getDescription().getStrings().get( 0 );

        sb.append( "tcFor: '" + tFor.getValue() + "', " );
        sb.append( "tcSize: '" + technical.getSize() + "', " );
        sb.append( "tcLoc: '" + tLoc.getValue() + "', " );
        sb.append( "tcTypeSrc: '" + tReqOrComType.getSource() + "', " );
        sb.append( "tcType: '" + tReqOrComType.getValue() + "', " );
        sb.append( "tcNameSrc: '" + tReqOrComName.getSource() + "', " );
        sb.append( "tcName: '" + tReqOrComName.getValue() + "', " );
        sb.append( "tcMinVer: '" + tReqOrComp.getMinimumVersion() + "', " );
        sb.append( "tcMaxVer: '" + tReqOrComp.getMinimumVersion() + "', " );
        sb.append( "tcInstRem: '" + lsInst.getValue().replace( "\n", "\\n" ) + "', " );
        sb.append( "tcInstOthInst: '" + lsOth.getValue().replace( "\n", "\\n" ) + "', " );
        sb.append( "tcDur: '" + tDur.getDuration() + "', " );
        sb.append( "tcDesc: '" + lsDurDesc.getValue().replace( "\n", "\\n" ) + "', " );


        // educational
        Educational educational = lom.getEducationals().get( 0 );
        EducationalInteractivityType eIType = educational.getInteractivityType();
        EducationalLearningResourceType eLRT = educational.getLearningResourceTypes().get( 0 );
        EducationalInteractivityLevel eILevel = educational.getInteractivityLevel();
        EducationalSemanticDensity eSem = educational.getSemanticDensity();
        EducationalIntendedEndUserRole eInt = educational.getIntendedEndUserRoles().get( 0 );
        EducationalContext eCont = educational.getContexts().get( 0 );
        LangString lsTar = educational.getTypicalAgeRanges().get( 0 ).getStrings().get( 0 );
        EducationalDifficulty eDif = educational.getDifficulty();
        EducationalTypicalLearningTime eTlt = educational.getTypicalLearningTime();
        LangString lsTcDesc = educational.getDescriptions().get( 0 ).getStrings().get( 0 );
        EducationalLanguage eLang = educational.getLanguages().get( 0 );
        LangString lsTltDesc = eTlt.getDescriptions().get( 0 ).getStrings().get( 0 );

        sb.append( "edTiSrc: '" + eIType.getSource() + "', " );
        sb.append( "edTi: '" + eIType.getValue() + "', " );
        sb.append( "edTrSrc: '" + eLRT.getSource() + "', " );
        sb.append( "edTr: '" + eLRT.getValue() + "', " );
        sb.append( "edNiSrc: '" + eILevel.getSource() + "', " );
        sb.append( "edNi: '" + eILevel.getValue() + "', " );
        sb.append( "edDsSrc: '" + eSem.getSource() + "', " );
        sb.append( "edDs: '" + eSem.getValue() + "', " );
        sb.append( "edPdSrc: '" + eInt.getSource() + "', " );
        sb.append( "edPd: '" + eInt.getValue() + "', " );
        sb.append( "edConSrc: '" + eCont.getSource() + "', " );
        sb.append( "edCon: '" + eCont.getValue() + "', " );
        sb.append( "edTar: '" + lsTar.getValue() + "', " );
        sb.append( "edDiSrc: '" + eDif.getSource() + "', " );
        sb.append( "edDi: '" + eDif.getValue() + "', " );
        sb.append( "edDur: '" + eTlt.getDuration() + "', " );
        sb.append( "edDurDesc: '" + lsTltDesc.getValue().replace( "\n", "\\n" ) + "', " );
        sb.append( "edDesc: '" + lsTcDesc.getValue().replace( "\n", "\\n" ) + "', " );
        sb.append( "edLang: '" + eLang.getValue() + "', " );


        // rights
        Rights rights = lom.getRights();
        RightsCost rCost = rights.getCost();
        RightsCopyrightAndOtherRestrictions rCopy = rights.getCopyrightAndOtherRestrictions();
        LangString lsRDesc = rights.getDescription().getStrings().get( 0 );

        sb.append( "rigCostSrc: '" + rCost.getSource() + "', " );
        sb.append( "rigCost: '" + rCost.getValue() + "', " );
        sb.append( "rigCopSrc: '" + rCopy.getSource() + "', " );
        sb.append( "rigCop: '" + rCopy.getValue() + "', " );
        sb.append( "rigDesc: '" + lsRDesc.getValue().replace( "\n", "\\n" ) + "', " );


        // relation
        Relation relation = lom.getRelations().get( 0 );
        RelationKind rKind = relation.getKind();
        RelationResource rRes = relation.getResource();
        RelationResourceIdentifier rResIdent = rRes.getIdentifiers().get( 0 );
        LangString lsRResDesc = rRes.getDescriptions().get( 0 ).getStrings().get( 0 );

        sb.append( "reKinSrc: '" + rKind.getSource() + "', " );
        sb.append( "reKin: '" + rKind.getValue() + "', " );
        sb.append( "reCat: '" + rResIdent.getCatalog() + "', " );
        sb.append( "reEntry: '" + rResIdent.getEntry() + "', " );
        sb.append( "reDesc: '" + lsRResDesc.getValue().replace( "\n", "\\n" ) + "', " );


        // annotation
        Annotation annotation = lom.getAnnotations().get( 0 );
        AnnotationEntity aEnt = annotation.getEntity();
        AnnotationDate aDate = annotation.getDate();
        LangString lsADateDesc = aDate.getDescription().getStrings().get( 0 );
        LangString lsADesc = annotation.getDescription().getStrings().get( 0 );

        sb.append( "anEnt: '" + aEnt.getValue() + "', " );
        sb.append( "anDate: '" + aDate.getDateTime() + "', " );
        sb.append( "anDateDesc: '" + lsADateDesc.getValue().replace( "\n", "\\n" ) + "', " );
        sb.append( "anDesc: '" + lsADesc.getValue().replace( "\n", "\\n" ) + "', " );

        
        // classification
        Classification classification = lom.getClassifications().get( 0 );
        ClassificationPurpose cPurp = classification.getPurpose();
        ClassificationTaxonPath cTaxonPath = classification.getTaxonPaths().get( 0 );
        LangString lsCDesc = classification.getDescription().getStrings().get( 0 );
        LangString lsCKey = classification.getKeywords().get( 0 ).getStrings().get( 0 );
        LangString lsTaxonPathSource = cTaxonPath.getSource().getStrings().get( 0 );
        ClassificationTaxonPathTaxon cTaxonPathTaxon = cTaxonPath.getTaxons().get( 0 );
        LangString lsTaxonPathTaxonEntry = cTaxonPathTaxon.getEntry().getStrings().get( 0 );

        sb.append( "clPurpSrc: '" + cPurp.getSource() + "', " );
        sb.append( "clPurp: '" + cPurp.getValue() + "', " );
        sb.append( "clTaxSrc: '" + lsTaxonPathSource.getValue() + "', " );
        sb.append( "clTaxTaxonId: '" + cTaxonPathTaxon.getId() + "', " );
        sb.append( "clTaxTaxonEntry: '" + lsTaxonPathTaxonEntry.getValue() + "', " );
        sb.append( "clDesc: '" + lsCDesc.getValue().replace( "\n", "\\n" ) + "', " );
        sb.append( "clKey: '" + lsCKey.getValue() + "'" );
        

        return sb.toString();

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
