/*
 * GeraTabelas.java
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
 * Esta classe executável gera as tabelas do banco.
 *
 * @author David Buzatto
 */
public class GeraTabelas {
    
    /**
     * Gera as tabelas configuradas na AnnotationConfiguration.
     */
    public static void create( AnnotationConfiguration config ) {
        new SchemaExport( config ).execute( true, true, false, true );
    }
    
    public static void main( String[] args ) {
        
        // cria uma AnnotationConfiguration
        AnnotationConfiguration config = new HibernateFactory().getConfig();
        
        // cria as tabelas
        create( config );
        
    }
    
}