/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.lia.cognitorweb.testes;

import br.ufscar.dc.lia.cognitorweb.sensocomum.*;
import java.net.MalformedURLException;
import java.util.*;
import java.util.ArrayList;

/**
 * Testes de obtenção de conhecimento de senso comum.
 *
 * @author David Buzatto
 */
public class TestesSensoComum {

    public static void main( String[] args ) {

        testeGetDisplayNodes();

    }

    private static void testeGetContext() {

        try {

            OMCSClient cliente = new OMCSClient( "http://lia.dc.ufscar.br:8000" );

            List<String> conceitos = new Vector<String>();
            conceitos.add( "bola" );

            for ( Context c : cliente.getContext( conceitos, false ) ) {
                System.out.println( c );
            }

        } catch ( MalformedURLException exc ) {
            exc.printStackTrace();
        }

    }

    private static void testeGetDisplayNodes() {

        try {

            OMCSClient cliente = new OMCSClient( "http://lia.dc.ufscar.br:8001" );

            for ( Relation r : cliente.getDisplayNodes( "bola", "UsedFor" ) ) {
                System.out.println( r );
            }

        } catch ( MalformedURLException exc ) {
            exc.printStackTrace();
        }

    }

    private static void testeGetDisplayNodeTo() {

        try {

            OMCSClient cliente = new OMCSClient( "http://lia.dc.ufscar.br:8000" );

            for ( Relation r : cliente.getDisplayNodeTo( "bola", "LocationOf" ) ) {
                System.out.println( r );
            }

        } catch ( MalformedURLException exc ) {
            exc.printStackTrace();
        }

    }

    private static void testeGetDisplayNodeFrom() {

        try {

            OMCSClient cliente = new OMCSClient( "http://lia.dc.ufscar.br:8000" );

            for ( Relation r : cliente.getDisplayNodeFrom( "bola", null ) ) {
                System.out.println( r );
            }

        } catch ( MalformedURLException exc ) {
            exc.printStackTrace();
        }

    }

    private static void testeGetBrowse() {

        try {

            OMCSClient cliente = new OMCSClient( "http://lia.dc.ufscar.br:8000" );

            System.out.println( cliente.getBrowse( "bola" ) );

        } catch ( MalformedURLException exc ) {
            exc.printStackTrace();
        }

    }

    private static void testeGetAllProjections() {

        try {

            OMCSClient cliente = new OMCSClient( "http://lia.dc.ufscar.br:8000" );

            List<String> l = new ArrayList<String>();
            l.add( "bola" );
            
            List< Projection > lp = cliente.getAllProjections( l );

            for ( Projection p : lp ) {
                System.out.println( p );
            }


        } catch ( MalformedURLException exc ) {
            exc.printStackTrace();
        }

    }

}
