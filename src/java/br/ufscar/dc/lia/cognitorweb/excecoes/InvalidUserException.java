/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.excecoes;

/**
 * Exceção para tipo de usuário não permitido.
 *
 * @author David Buzatto
 */
public class InvalidUserException extends Exception {

    public InvalidUserException( String message ) {
        super( message );
    }

}
