/*
 * SpringUtil.java
 *
 * Created on May, 2009, 9:20 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.util;

import org.springframework.beans.*;
import org.springframework.context.*;

/**
 * Classe utilitária para acessar os beans criados pelo Spring.
 *
 * @author David Buzatto
 */
public class SpringUtil implements ApplicationContextAware {
    
    private static ApplicationContext appContext;
    
    public static Object getBean(String beanName) {
        return appContext.getBean(beanName);
    }
    
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        appContext = context;
    }
    
}