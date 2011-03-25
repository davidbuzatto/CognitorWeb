/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.listeners;

import br.ufscar.dc.lia.cognitorweb.util.Uteis;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Obtém as configurações do sistema e armazena como um atributo da aplicação.
 *
 * @author David Buzatto
 */

public class ConfiguracoesListener implements ServletContextListener {

    public void contextInitialized( ServletContextEvent sce ) {
        sce.getServletContext().setAttribute( "configs",
                Uteis.obtemConfiguracoes() );
    }

    public void contextDestroyed( ServletContextEvent sce ) {
    }
    
}