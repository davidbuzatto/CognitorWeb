/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.util;

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
import org.springframework.orm.hibernate3.*;

/**
 * Classe utilitária para execução de exclusões.
 * As entidades podem ser configuradas um pouco melhor para executar
 * a exclusão em cascata, mas por falta de tempo foi implementada essa classe.
 *
 * @author David Buzatto
 */
public class DeleteUtils {

    /*
     * Exclui um OA, removendo toda a hierarquia de metadados.
     */
    public static void deleteOA( HibernateTemplate ht, ObjetoAprendizagem oa ) throws Exception {

        if ( oa instanceof Material )
            deleteMaterial( ht, ( Material ) oa );
        else if ( oa instanceof Grupo )
            deleteGrupo( ht, ( Grupo ) oa );
        else if ( oa instanceof Conceito )
            deleteConceito( ht, ( Conceito ) oa, false );
        else if ( oa instanceof Pagina )
            deletePagina( ht, ( Pagina ) oa, true );
        else if ( oa instanceof Imagem )
            deleteImagem( ht, ( Imagem ) oa );
        else if ( oa instanceof Video )
            deleteVideo( ht, ( Video ) oa );
        else if ( oa instanceof Som )
            deleteSom( ht, ( Som ) oa );

    }

    /*
     * Exclui um material.
     */
    public static void deleteMaterial( HibernateTemplate ht, Material m ) throws Exception {

        // desamarra
        for ( MaterialComMaterial e : m.getMateriais() ) {
            ht.delete( e );
            deleteMaterial( ht, e.getMaterialQueContem() );
        }

        for ( MaterialComConceito e : m.getMateriaisComConceitos() ) {
            ht.delete( e );
            deleteConceito( ht, e.getConceito(), false );
        }

        for ( MaterialComGrupo e : m.getMateriaisComGrupos() ) {
            ht.delete( e );
            deleteGrupo( ht, e.getGrupo() );
        }

        // obtém os metadados
        Metadata mt = m.getMetadata();

        // apaga o material
        ht.delete( m );

        // apaga os metadados
        deleteMetadata( ht, mt );

    }

    /*
     * Exclui um grupo.
     */
    public static void deleteGrupo( HibernateTemplate ht, Grupo g ) throws Exception {

        for ( GrupoComGrupo e : g.getGrupos() ) {
            ht.delete( e );
            deleteGrupo( ht, e.getGrupoQueContem() );
        }

        for ( GrupoComPagina e : g.getGruposComPaginas() ) {
            ht.delete( e );
            deletePagina( ht, e.getPagina(), true );
        }

        Metadata mt = g.getMetadata();
        ht.delete( g );
        deleteMetadata( ht, mt );

    }

    /*
     * Exclui um conceito.
     */
    public static void deleteConceito( HibernateTemplate ht, Conceito c,
            boolean excluirTodosRelacionamentos ) throws Exception {

        for ( RelacaoMapaConceito e : c.getConceitosOrigem() ) {
            ht.delete( e );
        }

        for ( RelacaoMapaConceito e : c.getConceitosDestino() ) {
            ht.delete( e );
        }

        for ( ConceitoComConceito e : c.getConceitos() ) {
            ht.delete( e );
            deleteConceito( ht, e.getConceitoQueContem(), false );
        }

        if ( excluirTodosRelacionamentos ) {
            for ( ConceitoComConceito e : c.getConceitosQueContem() ) {
                ht.delete( e );
            }
            for ( MaterialComConceito e : c.getMateriaisComConceitos() ) {
                ht.delete( e );
            }
        }

        for ( ConceitoComPagina e : c.getConceitosComPaginas() ) {
            ht.delete( e );
            deletePagina( ht, e.getPagina(), false );
        }

        Metadata mt = c.getMetadata();
        ht.delete( c );
        deleteMetadata( ht, mt );

    }

    /*
     * Exclui uma página.
     * Quando um conceito e uma página estão ligados, a exclusão dos metadados
     * fica a cargo do conceito.
     */
    public static void deletePagina( HibernateTemplate ht, Pagina p,
            boolean removeMetadados ) throws Exception {

        for ( Imagem i : p.getImagens() ) {
            deleteImagem( ht, i );
        }

        for ( Video v : p.getVideos() ) {
            deleteVideo( ht, v );
        }

        for ( Som s : p.getSons() ) {
            deleteSom( ht, s );
        }

        if ( removeMetadados ) {
            Metadata mt = p.getMetadata();
            ht.delete( p );
            deleteMetadata( ht, mt );
        } else {
            ht.delete( p );
        }

    }

    /*
     * Exclui uma imagem.
     * O arquivo não é excluído fisicamente, pois isso é feito na exclusão do
     * material ou no assistente de envio de imagens.
     */
    public static void deleteImagem( HibernateTemplate ht, Imagem i ) throws Exception {

        Metadata mt = i.getMetadata();
        ht.delete( i );
        deleteMetadata( ht, mt );

    }

    /*
     * Exclui um video.
     * O arquivo não é excluído fisicamente, pois isso é feito na exclusão do
     * material ou no assistente de envio de imagens.
     */
    public static void deleteVideo( HibernateTemplate ht, Video v ) throws Exception {
    
        Metadata mt = v.getMetadata();
        ht.delete( v );
        deleteMetadata( ht, mt );

    }

    /*
     * Exclui uma imagem.
     * O arquivo não é excluído fisicamente, pois isso é feito na exclusão do
     * material ou no assistente de envio de imagens.
     */
    public static void deleteSom( HibernateTemplate ht, Som s ) throws Exception {

        Metadata mt = s.getMetadata();
        ht.delete( s );
        deleteMetadata( ht, mt );

    }

    /*
     * Apaga toda a hierarquia de metadados.
     * Foi TUDO feito na mão. Poderia ter configurado as entidades com
     * anotações, mas infelizmente faltou tempo para desenvolver.
     */
    public static void deleteMetadata( HibernateTemplate ht, Metadata mt ) throws Exception {

        // obtém o lom
        Lom lom = mt.getLom();

        // apaga os metadados
        ht.delete( mt );

        /*
         * Sempre precisa pegar os dados antes de apagar o pai
         * precisa ir por ordem. primeiro os que dependem do lom
         */

        // educational
        for ( Educational e : lom.getEducationals() ) {

            // simples 1
            ht.delete( e.getInteractivityType() );
            ht.delete( e.getInteractivityLevel() );
            ht.delete( e.getSemanticDensity() );
            ht.delete( e.getDifficulty() );

            // simples multi
            for ( EducationalLearningResourceType ee : e.getLearningResourceTypes() )
                ht.delete( ee );
            for ( EducationalIntendedEndUserRole ee : e.getIntendedEndUserRoles() )
                ht.delete( ee );
            for ( EducationalContext ee : e.getContexts() )
                ht.delete( ee );
            for ( EducationalLanguage ee : e.getLanguages() )
                ht.delete( ee );

            // composto 1
            for ( EducationalTypicalLearningTimeDescription t : e.getTypicalLearningTime().getDescriptions() ) {
                for ( LangString s : t.getStrings() ) {
                    ht.delete( s );
                }
                ht.delete( t );
            }
            ht.delete( e.getTypicalLearningTime() );


            // composto multi
            for ( EducationalTypicalAgeRange ee : e.getTypicalAgeRanges() ) {
                for ( LangString s : ee.getStrings() ) {
                    ht.delete( s );
                }
                ht.delete( ee );
            }
            for ( EducationalDescription ee : e.getDescriptions() ) {
                for ( LangString s : ee.getStrings() ) {
                    ht.delete( s );
                }
                ht.delete( ee );
            }

            ht.delete( e );

        }
        

        // relation
        for ( Relation r : lom.getRelations() ) {

            ht.delete( r.getKind() );

            for ( RelationResourceDescription rr : r.getResource().getDescriptions() ) {
                for ( LangString s : rr.getStrings() ) {
                    ht.delete( s );
                }
                ht.delete( rr );
            }
            for ( RelationResourceIdentifier rr : r.getResource().getIdentifiers() )
                ht.delete( rr );
            ht.delete( r.getResource() );

            ht.delete( r );
            
        }


        // annotation
        for ( Annotation a : lom.getAnnotations() ) {

            ht.delete( a.getEntity() );

            for ( LangString s : a.getDate().getDescription().getStrings() )
                ht.delete( s );
            ht.delete( a.getDate().getDescription() );
            ht.delete( a.getDate() );

            for ( LangString s : a.getDescription().getStrings() )
                ht.delete( s );
            ht.delete( a.getDescription() );

            ht.delete( a );

        }


        // classification
        for ( Classification c : lom.getClassifications() ) {

            ht.delete( c.getPurpose() );

            for ( ClassificationTaxonPath cc : c.getTaxonPaths() ) {

                for ( LangString s : cc.getSource().getStrings() )
                    ht.delete( s );
                ht.delete( cc.getSource() );

                for ( ClassificationTaxonPathTaxon ccc : cc.getTaxons() ) {
                    for ( LangString s : ccc.getEntry().getStrings() )
                        ht.delete( s );
                    ht.delete( ccc.getEntry() );
                    ht.delete( ccc );
                }

                ht.delete( cc );

            }

            for ( LangString s : c.getDescription().getStrings() )
                ht.delete( s );
            ht.delete( c.getDescription() );

            for ( ClassificationKeyword cc : c.getKeywords() ) {
                for ( LangString s : cc.getStrings() )
                    ht.delete( s );
                ht.delete( cc );
            }

            ht.delete( c );

        }

        // nesse ponto o lom pode ser apagado, entratanto só vai ser no final


        // general
        General general = lom.getGeneral();
        for ( GeneralIdentifier gg : general.getIdentifiers() )
            ht.delete( gg );

        for ( LangString s : general.getTitle().getStrings() )
            ht.delete( s );
        ht.delete( general.getTitle() );

        for ( GeneralLanguage gg : general.getLanguages() )
            ht.delete( gg );

        for ( GeneralDescription gg : general.getDescriptions() ) {
            for ( LangString s : gg.getStrings() )
                ht.delete( s );
            ht.delete( gg );
        }

        for ( GeneralKeyword gg : general.getKeywords() ) {
            for ( LangString s : gg.getStrings() )
                ht.delete( s );
            ht.delete( gg );
        }

        for ( GeneralCoverage gg : general.getCoverages() ) {
            for ( LangString s : gg.getStrings() )
                ht.delete( s );
            ht.delete( gg );
        }

        ht.delete( general.getStructure() );
        ht.delete( general.getAggregationLevel() );

        ht.delete( general );


        // lifeCycle
        LifeCycle lifeCycle = lom.getLifeCycle();
        for ( LangString s : lifeCycle.getVersion().getStrings() )
            ht.delete( s );
        ht.delete( lifeCycle.getVersion() );
        
        ht.delete( lifeCycle.getStatus() );
        
        for ( LifeCycleContribute ll : lifeCycle.getContributes() ) {
            ht.delete( ll.getRole() );
            for ( LifeCycleContributeEntity lll : ll.getEntities() )
                ht.delete( lll );
            for ( LangString s : ll.getDate().getDescription().getStrings() )
                ht.delete( s );
            ht.delete( ll.getDate().getDescription() );
            ht.delete( ll.getDate() );
            ht.delete( ll );
        }

        ht.delete( lifeCycle );


        // metaMetadata
        MetaMetadata metaMetadata = lom.getMetaMetadata();
        for ( MetaMetadataIdentifier mm : metaMetadata.getIdentifiers() )
            ht.delete( mm );

        for ( MetaMetadataContribute mm : metaMetadata.getContributes() ) {
            ht.delete( mm.getRole() );
            for ( MetaMetadataContributeEntity mmm : mm.getEntities() )
                ht.delete( mmm );
            for ( LangString s : mm.getDate().getDescription().getStrings() )
                ht.delete( s );
            ht.delete( mm.getDate().getDescription() );
            ht.delete( mm.getDate() );
            ht.delete( mm );
        }

        for ( MetaMetadataMetadataSchema mm : metaMetadata.getMetadataSchemas() )
            ht.delete( mm );

        ht.delete( metaMetadata );


        // techincal
        Technical technical = lom.getTechnical();
        for ( TechnicalFormat tt : technical.getFormats() )
            ht.delete( tt );

        for ( TechnicalLocation tt : technical.getLocations() )
            ht.delete( tt );

        for ( TechnicalRequirement tt : technical.getRequirements() ) {
            for ( TechnicalRequirementOrComposite ttt : tt.getOrComposites() ) {
                ht.delete( ttt.getType() );
                ht.delete( ttt.getName() );
                ht.delete( ttt );
            }
            ht.delete( tt );
        }

        for ( LangString s : technical.getInstallationRemarks().getStrings() )
            ht.delete( s );
        ht.delete( technical.getInstallationRemarks() );

        for ( LangString s : technical.getOtherPlatformRequirements().getStrings() )
            ht.delete( s );
        ht.delete( technical.getOtherPlatformRequirements() );

        for ( LangString s : technical.getDuration().getDescription().getStrings() )
            ht.delete( s );
        ht.delete( technical.getDuration().getDescription() );
        ht.delete( technical.getDuration() );
        
        ht.delete( technical );


        // rights
        Rights rights = lom.getRights();
        ht.delete( rights.getCost() );
        ht.delete( rights.getCopyrightAndOtherRestrictions() );

        for ( LangString s : rights.getDescription().getStrings() )
            ht.delete( s );
        ht.delete( rights.getDescription() );
        ht.delete( rights );


        ht.delete( lom );

    }

}
