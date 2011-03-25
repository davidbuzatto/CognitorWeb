/*
 * AtualizaTabelas.java
 *
 * Created on May 27, 2009, 08:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.util;

import br.ufscar.dc.lia.cognitorweb.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.tool.hbm2ddl.*;

/**
 * Classe executável que atualiza as tabelas do banco.
 *
 * @author David Buzatto
 */
public class AtualizaTabelas {

    /**
     * Atualiza as tabelas configuradas na AnnotationConfiguration.
     */
    private static void update( AnnotationConfiguration config ) {
        new SchemaUpdate( config ).execute( true, true );
    }

    public static void main( String[] args ) {

        // cria uma AnnotationConfiguration
        AnnotationConfiguration config = new HibernateFactory().getConfig();

        // atualiza as tabelas
        update( config );

    }

}
