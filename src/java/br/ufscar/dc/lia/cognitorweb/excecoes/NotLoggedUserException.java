/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.excecoes;

/**
 * Exceção para tipo de usuário não logado.
 *
 * @author David Buzatto
 */
public class NotLoggedUserException extends Exception {

    public NotLoggedUserException( String message ) {
        super( message );
    }

}
