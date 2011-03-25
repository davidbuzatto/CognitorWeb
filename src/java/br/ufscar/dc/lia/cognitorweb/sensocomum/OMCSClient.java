/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lia.cognitorweb.sensocomum;

import java.io.*;
import java.net.*;
import java.util.*;
import org.apache.xmlrpc.*;

/**
 * Cliente para consultas XML-RPC na base de conhecimento de Senso Comum.
 * 
 * @author David Buzatto
 */
public class OMCSClient {

    private XmlRpcClient cliente;

    /**
     * Cria um cliente de busca de conhecimento de senso comum.
     *
     * @param urlRemoto URL do servidor XML-RPC de senso comum.
     * @throws java.net.MalformedURLException Caso a URL esteja mal formada.
     */
    public OMCSClient( String urlRemoto ) throws MalformedURLException {
        this.cliente = new XmlRpcClient( urlRemoto );
    }

    /**
     * Obtém as relações entre conceitos existentes na ConceptNet
     * 
     * @param conceito Conceito a ser buscado
     * @return Uma lista de relações
     */
    @SuppressWarnings( value = "unchecked" )
    public List<Relation> getNode( String conceito, String predicate, int arg ) {

        List<Relation> saida = new ArrayList<Relation>();

        try {

            Vector<Object> params;
            params = new Vector<Object>();
            params.add( conceito );
            params.add( predicate );
            params.add( arg );
            List<List> resultado = ( List<List> ) cliente.execute( "get_related_relations", params );

            for ( List l : resultado ) {
                Relation relation = new Relation();
                relation.setPredicate( String.valueOf( l.get( 0 ) ) );
                relation.setConcept1( String.valueOf( l.get( 1 ) ) );
                relation.setConcept2( String.valueOf( l.get( 2 ) ) );
                relation.setFrequency( Integer.valueOf( l.get( 3 ).toString() ) );
                relation.setInference( Integer.valueOf( l.get( 3 ).toString() ) );
                saida.add( relation );
            }

        } catch ( XmlRpcException exc ) {
            exc.printStackTrace();
        } catch ( IOException exc ) {
            exc.printStackTrace();
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

        return saida;

    }

    /**
     * Obtém analogias relacionadas a um conceito.
     *
     * @param conceito Conceito a ser buscado
     * @param expertNet Rede de conceitos especialista utilizada na busca
     * @return Uma lista de analogias.
     */
    @SuppressWarnings( value = "unchecked" )
    public List<Analogy> getAnalogies( String conceito, String expertNet ) {

        List<Analogy> saida = new ArrayList<Analogy>();

        try {

            Vector<String> params = new Vector<String>();
            params.add( conceito );
            params.add( expertNet );
            List<List> resultado = ( List<List> ) cliente.execute( "get_analogy", params );

            for ( List lista : resultado ) {

                String conceitoAlvo = String.valueOf( lista.get( 0 ) );

                if ( conceitoAlvo.equals( "noresult" ) ){
                    return saida;
                }

                Analogy analogy = new Analogy();
                analogy.setConceptBase( conceito );
                analogy.setConceptTarget( conceitoAlvo );
                analogy.setWeight( 0 );

                for ( List parte : ( List< List > ) lista.get( 2 ) ) {
                    AnalogyPart ap = new AnalogyPart();
                    ap.setPredicate( (String) parte.get( 0 ) );
                    ap.setExpert1( (String) parte.get( 1 ) );
                    ap.setCommonSense1( (String) parte.get(2) );
                    ap.setExpert2( (String) parte.get(3) );
                    ap.setCommonSense2( (String) parte.get(4) );
                    analogy.getAnalogyParts().add( ap );
                }

                saida.add(analogy);

            }

        } catch ( XmlRpcException exc ) {
            exc.printStackTrace();
        } catch ( IOException exc ) {
            exc.printStackTrace();
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

        return saida;

    }

    /**
     * Obtém conceitos relacionados a um conceito pesquisado.
     *
     * @param conceitos Conceitos a serem buscados
     * @param maxNodeVisits Quantos nós devem ser visitados
     * @param maxResults Quantos resultados devem ser trazidos
     * @param flowPinch
     * @param linkTypeWeightsDict
     * @param weight
     * @return Uma lista de conceitos.
     */
    @SuppressWarnings( value = "unchecked" )
    public List<Context> getContext(
            List<String> conceitos,
            int maxNodeVisits, int maxResults, int flowPinch,
            boolean linkTypeWeightsDict, boolean weight ) {

        List<Context> saida = new ArrayList<Context>();

        try {

            Vector<Object> params = new Vector<Object>();

            params.add( conceitos.toArray() );
            params.add( maxNodeVisits );
            params.add( maxResults );
            params.add( flowPinch );
            params.add( linkTypeWeightsDict );
            params.add( weight );
            List<List> resultado = ( List<List> ) cliente.execute( "get_context", params );

            for ( List e : resultado ) {
                Context context = new Context();
                context.setConcept( ( String ) e.get( 0 ) );
                context.setValue( ( Double ) e.get( 1 ) );
                saida.add( context );
            }

        } catch ( XmlRpcException exc ) {
            exc.printStackTrace();
        } catch ( IOException exc ) {
            exc.printStackTrace();
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

        return saida;

    }

    /**
     * Obtém conceitos relacionados a um conceito pesquisado, sendo que
     * por padrão visita no máximo 300 nós, trás 50 resultadose flowPinch de 300.
     *
     * @param conceitos Conceitos a serem buscados
     * @param maxNodeVisits Quantos nós devem ser visitados
     * @param maxResults Quantos resultados devem ser trazidos
     * @param flowPinch
     * @param liktypeWeightsDict
     * @param weight
     * @return Uma lista de conceitos.
     */
    public List<Context> getContext( List<String> conceitos, boolean weight ) {
        return getContext( conceitos, 500, 30, 300, false, weight );
    }

    /**
     * Obtém as relações entre conceitos.
     *
     * @param conceito O conceito a ser pesquisado
     * @param relacao A relação a ser pesquisada. Se null, retorna todas.
     * @return Uma lista de relações entre conceitos.
     */
    public List<Relation> getDisplayNodes( String conceito, String relacao ) {

        List<Relation> saida = new ArrayList<Relation>();

        try {

            Vector<Object> params = new Vector<Object>();
            params.add( conceito );
            String valor = ( String ) cliente.execute( "display_node", params );

            int indexOut = valor.indexOf( "**OUT:********" );
            int indexIn = valor.indexOf( "**IN:*********" );
            String valorOut = valor.substring( indexOut + 15, indexIn );
            valorOut = valorOut.replaceAll( "  ==", "" );
            valorOut = valorOut.replaceAll( "==> ", "," );
            valorOut = valorOut.replaceAll( " \\(", "," );
            valorOut = valorOut.replaceAll( ", ", "," );
            valorOut = valorOut.replaceAll( "\\)", "" );
            String valorIn = valor.substring( indexIn + 15 );
            valorIn = valorIn.replaceAll( "  <==", "" );
            valorIn = valorIn.replaceAll( "== ", "," );
            valorIn = valorIn.replaceAll( " \\(", "," );
            valorIn = valorIn.replaceAll( ", ", "," );
            valorIn = valorIn.replaceAll( "\\)", "" );

            if ( !valorOut.trim().equals( "" ) ) {
                for ( String linha : valorOut.split( "\n" ) ) {
                    Relation relation = new Relation();
                    String[] valores = linha.split( "," );
                    if ( relacao == null || relacao.equals( valores[ 0 ] ) ) {
                        relation.setPredicate( valores[ 0 ] );
                        relation.setConcept1( conceito );
                        relation.setConcept2( valores[ 1 ] );
                        relation.setFrequency( Integer.parseInt( valores[ 2 ] ) );
                        relation.setInference( Integer.parseInt( valores[ 3 ] ) );
                        saida.add( relation );
                    }
                }
            }

            if ( !valorIn.trim().equals( "" ) ) {
                for ( String linha : valorIn.split( "\n" ) ) {
                    Relation relation = new Relation();
                    String[] valores = linha.split( "," );
                    if ( relacao == null || relacao.equals( valores[ 0 ] ) ) {
                        relation.setPredicate( valores[ 0 ] );
                        relation.setConcept1( valores[ 1 ] );
                        relation.setConcept2( conceito );
                        relation.setFrequency( Integer.parseInt( valores[ 2 ] ) );
                        relation.setInference( Integer.parseInt( valores[ 3 ] ) );
                        saida.add( relation );
                    }
                }
            }

        } catch ( XmlRpcException exc ) {
            exc.printStackTrace();
        } catch ( IOException exc ) {
            exc.printStackTrace();
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

        return saida;

    }

    /**
     * Obtém as relações entre conceitos de forma conceito pesquisado ==> conceitos obtidos,
     * ou seja, conceitos que o conceito pesquisado tem relação.
     *
     * @param conceito O conceito a ser pesquisado
     * @param relacao A relação a ser pesquisada. Se null, retorna todas.
     * @return Uma lista de relações entre conceitos.
     */
    public List<Relation> getDisplayNodeTo( String conceito, String relacao ) {

        List<Relation> saida = new ArrayList<Relation>();

        try {

            Vector<Object> params = new Vector<Object>();
            params.add( conceito );
            String valor = ( String ) cliente.execute( "display_node", params );

            int indexOut = valor.indexOf( "**OUT:********" );
            int indexIn = valor.indexOf( "**IN:*********" );
            valor = valor.substring( indexOut + 15, indexIn );
            valor = valor.replaceAll( "  ==", "" );
            valor = valor.replaceAll( "==> ", "," );
            valor = valor.replaceAll( " \\(", "," );
            valor = valor.replaceAll( ", ", "," );
            valor = valor.replaceAll( "\\)", "" );

            if ( !valor.trim().equals( "" ) ) {
                for ( String linha : valor.split( "\n" ) ) {
                    Relation relation = new Relation();
                    String[] valores = linha.split( "," );
                    if ( relacao == null || relacao.equals( valores[ 0 ] ) ) {
                        relation.setPredicate( valores[ 0 ] );
                        relation.setConcept1( conceito );
                        relation.setConcept2( valores[ 1 ] );
                        relation.setFrequency( Integer.parseInt( valores[ 2 ] ) );
                        relation.setInference( Integer.parseInt( valores[ 3 ] ) );
                        saida.add( relation );
                    }
                }
            }

        } catch ( XmlRpcException exc ) {
            exc.printStackTrace();
        } catch ( IOException exc ) {
            exc.printStackTrace();
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

        return saida;

    }

    /**
     * Obtém as relações entre conceitos de forma conceito pesquisado <== conceitos obtidos,
     * ou seja, conceitos que tem relação com o conceito pesquisado.
     *
     * @param conceito O conceito a ser pesquisado
     * @param relacao A relação a ser pesquisada. Se null, retorna todas.
     * @return Uma lista de relações entre conceitos.
     */
    public List<Relation> getDisplayNodeFrom( String conceito, String relacao ) {

        List<Relation> saida = new ArrayList<Relation>();

        try {

            Vector<Object> params = new Vector<Object>();
            params.add( conceito );
            String valor = ( String ) cliente.execute( "display_node", params );

            int indexIn = valor.indexOf( "**IN:*********" );
            valor = valor.substring( indexIn + 15 );
            valor = valor.replaceAll( "  <==", "" );
            valor = valor.replaceAll( "== ", "," );
            valor = valor.replaceAll( " \\(", "," );
            valor = valor.replaceAll( ", ", "," );
            valor = valor.replaceAll( "\\)", "" );

            if ( !valor.trim().equals( "" ) ) {
                for ( String linha : valor.split( "\n" ) ) {
                    Relation relation = new Relation();
                    String[] valores = linha.split( "," );
                    if ( relacao == null || relacao.equals( valores[ 0 ] ) ) {
                        relation.setPredicate( valores[ 0 ] );
                        relation.setConcept1( valores[ 1 ] );
                        relation.setConcept2( conceito );
                        relation.setFrequency( Integer.parseInt( valores[ 2 ] ) );
                        relation.setInference( Integer.parseInt( valores[ 3 ] ) );
                        saida.add( relation );
                    }
                }
            }

        } catch ( XmlRpcException exc ) {
            exc.printStackTrace();
        } catch ( IOException exc ) {
            exc.printStackTrace();
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

        return saida;

    }

    /**
     * Obtém os dados de um conceito.
     *
     * @param conceito O conceito a ser pesquisado
     * @return Uma lista de relações entre conceitos.
     */
    public String getBrowse( String conceito ) {

        String saida = "";

        try {

            Vector<Object> params = new Vector<Object>();
            params.add( conceito );
            saida = ( String ) cliente.execute( "display_node", params );

        } catch ( XmlRpcException exc ) {
            exc.printStackTrace();
        } catch ( IOException exc ) {
            exc.printStackTrace();
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

        return saida;

    }

    /**
     * Obtém projeções de um ou vários conceitos.
     *
     * @param conceito O conceito a ser pesquisado
     * @param relacao A relação a ser pesquisada. Se null, retorna todas.
     * @return Uma lista de relações entre conceitos.
     */
    @SuppressWarnings( value = "unchecked" )
    public List<Projection> getAllProjections( List<String> conceitos ) {

        List<Projection> saida = new ArrayList<Projection>();

        try {

            Vector<Object> params = new Vector<Object>();
            params.add( conceitos.toArray() );
            List<List> valores = ( List<List> ) cliente.execute( "get_all_projections", params );

            for ( List v : valores ) {

                Projection p = new Projection();
                p.setName( String.valueOf( v.get( 0 ) ) );

                List< Context > lContexts = new ArrayList< Context >();
                List contexts = ( List ) v.get( 1 );

                for ( int i = 0; i < contexts.size(); i++ ) {
                    Context c = new Context();
                    List context = ( List ) contexts.get( i );
                    c.setConcept( String.valueOf( context.get( 0 ) ) );
                    c.setValue( Double.parseDouble( String.valueOf( context.get( 1 ) ) ) );
                    lContexts.add( c );
                }

                p.setContexts( lContexts );
                saida.add( p );

            }

        } catch ( XmlRpcException exc ) {
            exc.printStackTrace();
        } catch ( IOException exc ) {
            exc.printStackTrace();
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }

        return saida;

    }

}
