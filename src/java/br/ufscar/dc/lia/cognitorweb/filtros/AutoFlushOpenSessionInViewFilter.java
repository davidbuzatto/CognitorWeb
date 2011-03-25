/*
 * AutoFlushOpenSessionInViewFilter.java
 *
 * Created on May 27, 2009, 08:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.filtros;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

/**
 * Filtro para o Spring.
 *
 * @author David Buzatto
 */

public class AutoFlushOpenSessionInViewFilter extends OpenSessionInViewFilter{
    
    /**
     * Busca uma Session do Hibernate e aplica flush mode para auto.
     *
     */
    @Override
    protected Session getSession( SessionFactory sessionFactory ) 
            throws DataAccessResourceFailureException {
        
        Session session = SessionFactoryUtils.getSession( sessionFactory, true );
        session.setFlushMode( FlushMode.AUTO );
        return session;
        
    }
    
}