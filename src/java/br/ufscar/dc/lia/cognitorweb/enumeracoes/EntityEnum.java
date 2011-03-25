/*
 * EntityEnum.java
 *
 * Created on May 27, 2009, 08:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.enumeracoes;

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

/**
 * Enumerações das entidades.
 *
 * @author David Buzatto
 */
public enum EntityEnum {
    
    // entidades
    ASSET( Asset.class ),
    CIDADE( Cidade.class ),
    CONCEITO( Conceito.class ),
    CONCEITO_COM_CONCEITO( ConceitoComConceito.class ),
    CONCEITO_COM_CONCEITO_PK( ConceitoComConceitoPK.class ),
    CONCEITO_COM_PAGINA( ConceitoComPagina.class ),
    CONCEITO_COM_PAGINA_PK( ConceitoComPaginaPK.class ),
    CONFIGURACAO( Configuracao.class ),
    ESTADO( Estado.class ),
    GRUPO( Grupo.class ),
    GRUPO_COM_GRUPO( GrupoComGrupo.class ),
    GRUPO_COM_GRUPO_PK( GrupoComGrupoPK.class ),
    GRUPO_COM_PAGINA( GrupoComPagina.class ),
    GRUPO_COM_PAGINA_PK( GrupoComPaginaPK.class ),
    I18N_TABLE( I18NTable.class ),
    IMAGEM( Imagem.class ),
    LOG( Log.class ),
    MATERIAL( Material.class ),
    MATERIAL_COM_CONCEITO( MaterialComConceito.class ),
    MATERIAL_COM_CONCEITO_PK( MaterialComConceitoPK.class ),
    MATERIAL_COM_GRUPO( MaterialComGrupo.class ),
    MATERIAL_COM_GRUPO_PK( MaterialComGrupoPK.class ),
    MATERIAL_COM_MATERIAL( MaterialComMaterial.class ),
    MATERIAL_COM_MATERIAL_PK( MaterialComMaterialPK.class ),
    OBJETO_APRENDIZAGEM( ObjetoAprendizagem.class ),
    PAGINA( Pagina.class ),
    PAIS( Pais.class ),
    RELACAO_MAPA_CONCEITO( RelacaoMapaConceito.class ),
    SOM( Som.class ),
    USUARIO( Usuario.class ),
    VIDEO( Video.class ),

    // metadados
    METADATA( Metadata.class ),
    LOM( Lom.class ),

    LANG_STRING( LangString.class ),

    GENERAL( General.class ),
    GENERAL_IDENTIFIER( GeneralIdentifier.class ),
    GENERAL_TITLE( GeneralTitle.class ),
    GENERAL_LANGUAGE( GeneralLanguage.class ),
    GENERAL_DESCRIPTION( GeneralDescription.class ),
    GENERAL_KEYWORD( GeneralKeyword.class ),
    GENERAL_COVERAGE( GeneralCoverage.class ),
    GENERAL_STRUCTURE( GeneralStructure.class ),
    GENERAL_AGGREGATION_LEVEL( GeneralAggregationLevel.class ),

    LIFE_CYCLE( LifeCycle.class ),
    LIFE_CYCLE_VERSION( LifeCycleVersion.class ),
    LIFE_CYCLE_STATUS( LifeCycleStatus.class ),
    LIFE_CYCLE_CONTRIBUTE( LifeCycleContribute.class ),
    LIFE_CYCLE_CONTRIBUTE_ROLE( LifeCycleContributeRole.class ),
    LIFE_CYCLE_CONTRIBUTE_ENTITY( LifeCycleContributeEntity.class ),
    LIFE_CYCLE_CONTRIBUTE_DATE( LifeCycleContributeDate.class ),
    LIFE_CYCLE_CONTRIBUTE_DATE_DESCRIPTION( LifeCycleContributeDateDescription.class ),

    META_METADATA( MetaMetadata.class ),
    META_METADATA_IDENTIFIER( MetaMetadataIdentifier.class ),
    META_METADATA_CONTRIBUTE( MetaMetadataContribute.class ),
    META_METADATA_CONTRIBUTE_ROLE( MetaMetadataContributeRole.class ),
    META_METADATA_CONTRIBUTE_ENTITY( MetaMetadataContributeEntity.class ),
    META_METADATA_CONTRIBUTE_DATE( MetaMetadataContributeDate.class ),
    META_METADATA_CONTRIBUTE_DATE_DESCRIPTION( MetaMetadataContributeDateDescription.class ),
    META_METADATA_METADATA_SCHEMA( MetaMetadataMetadataSchema.class ),

    TECHNICAL( Technical.class ),
    TECHNICAL_FORMAT( TechnicalFormat.class ),
    TECHNICAL_LOCATION( TechnicalLocation.class ),
    TECHNICAL_REQUIREMENT( TechnicalRequirement.class ),
    TECHNICAL_REQUIREMENT_OR_COMPOSITE( TechnicalRequirementOrComposite.class ),
    TECHNICAL_REQUIREMENT_OR_COMPOSITE_TYPE( TechnicalRequirementOrCompositeType.class ),
    TECHNICAL_REQUIREMENT_OR_COMPOSITE_NAME( TechnicalRequirementOrCompositeName.class ),
    TECHNICAL_INSTALLATION_REMARKS( TechnicalInstallationRemarks.class ),
    TECHNICAL_OTHER_PLATFORM_REQUIREMENTS( TechnicalOtherPlatformRequirements.class ),
    TECHNICAL_DURATION( TechnicalDuration.class ),
    TECHNICAL_DURATION_DESCRIPTION( TechnicalDurationDescription.class ),

    EDUCATIONAL( Educational.class ),
    EDUCATIONAL_INTERACTIVITY_TYPE( EducationalInteractivityType.class ),
    EDUCATIONAL_LEARNING_RESOURCE_TYPE( EducationalLearningResourceType.class ),
    EDUCATIONAL_INTERACTIVITY_LEVEL( EducationalInteractivityLevel.class ),
    EDUCATIONAL_SEMANTIC_DENSITY( EducationalSemanticDensity.class ),
    EDUCATIONAL_INTENDED_END_USER_ROLE( EducationalIntendedEndUserRole.class ),
    EDUCATIONAL_CONTEXT( EducationalContext.class ),
    EDUCATIONAL_TYPICAL_AGE_RANGE( EducationalTypicalAgeRange.class ),
    EDUCATIONAL_DIFFICULTY( EducationalDifficulty.class ),
    EDUCATIONAL_TYPICAL_LEARNING_TIME( EducationalTypicalLearningTime.class ),
    EDUCATIONAL_TYPICAL_LEARNING_TIME_DESCRIPTION( EducationalTypicalLearningTimeDescription.class ),
    EDUCATIONAL_DESCRIPTION( EducationalDescription.class ),
    EDUCATIONAL_LANGUAGE( EducationalLanguage.class ),

    RIGHTS( Rights.class ),
    RIGHTS_COST( RightsCost.class ),
    RIGHTS_COPYRIGHT_AND_OTHER_RESTRICTIONS( RightsCopyrightAndOtherRestrictions.class ),
    RIGHTS_DESCRIPTION( RightsDescription.class ),

    RELATION( Relation.class ),
    RELATION_KIND( RelationKind.class ),
    RELATION_RESOURCE( RelationResource.class ),
    RELATION_RESOURCE_IDENTIFIER( RelationResourceIdentifier.class ),
    RELATION_RESOURCE_DESCRIPTION( RelationResourceDescription.class ),

    ANNOTATION( Annotation.class ),
    ANNOTATION_ENTITY( AnnotationEntity.class ),
    ANNOTATION_DATE( AnnotationDate.class ),
    ANNOTATION_DATE_DESCRIPTION( AnnotationDateDescription.class ),
    ANNOTATION_DESCRIPTION( AnnotationDescription.class ),

    CLASSIFICATION( Classification.class ),
    CLASSIFICATION_PURPOSE( ClassificationPurpose.class ),
    CLASSIFICATION_TAXON_PATH( ClassificationTaxonPath.class ),
    CLASSIFICATION_TAXON_PATH_SOURCE( ClassificationTaxonPathSource.class ),
    CLASSIFICATION_TAXON_PATH_TAXON( ClassificationTaxonPathTaxon.class ),
    CLASSIFICATION_TAXON_PATH_TAXON_ENTRY( ClassificationTaxonPathTaxonEntry.class ),
    CLASSIFICATION_DESCRIPTION( ClassificationDescription.class ),
    CLASSIFICATION_KEYWORD( ClassificationKeyword.class );

    private Class entity;
    
    EntityEnum( Class entity ) {
        setEntity( entity );
    }
    
    private void setEntity( Class entity ) {
        this.entity = entity;
    }
    
    public Class getEntity() {
        return entity;
    }
    
}