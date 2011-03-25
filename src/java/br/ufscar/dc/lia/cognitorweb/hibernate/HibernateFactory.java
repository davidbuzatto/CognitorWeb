/*
 * HibernateFactory.java
 *
 * Created on May 27, 2009, 08:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.hibernate;

import br.ufscar.dc.lia.cognitorweb.enumeracoes.EntityEnum;
import org.hibernate.*;
import org.hibernate.cfg.*;

/**
 * Fábrica de sessões do hibernate.
 *
 * Utiliza uma enumeração de classes anotadas para popular a configuração.
 *
 * @author David Buzatto
 */
public class HibernateFactory {
    
    // fábrica de sessões
    private static final SessionFactory factory;
    
    /* 
     * AnnotationConfiguration, responsável em armazenar as configurações
     * das classes anotadas.
     */
    private static final AnnotationConfiguration config;
    
    /*
     * Permite que as sessions associadas sejam locais a thread que a estão executando.
     */
    private static final ThreadLocal< Session > threadLocal = 
            new ThreadLocal< Session >();
    
    // bloco de inicialização estático (executado apenas UMA vez)
    static {
        
        // cria a AnnotationConfiguration, evitando uso do arquivo hibernate.cfg.xml
        config = new AnnotationConfiguration();
        
        // adiciona as classes anotadas
        EntityEnum[] entities = EntityEnum.values();
        
        for ( int i = 0; i < entities.length; i++ ) {
            config.addAnnotatedClass( entities[ i ].getEntity() );
        }
        
        // cria a factory
        factory = config.buildSessionFactory();
        
    }
    
    public SessionFactory getFactory() {
        return factory;
    }
    
    public AnnotationConfiguration getConfig() {
        return config;
    }
    
    public Session getSession() {
        Session session = factory.openSession();
        threadLocal.set( session );
        return factory.openSession();
    }
    
}
