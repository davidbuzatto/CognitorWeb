/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.enumeracoes;

/**
 * Enumeração para especificar o layout do material.
 *
 * @author David Buzatto
 */
public enum LayoutMaterial {

    /**
     * Páginas com link e com barra de navegação.
     */
    PCLCBN(),

    /**
     * Páginas com link e sem barra de navegação.
     */
    PCLSBN(),

    /**
     * Páginas sem link e com barra de navegação.
     */
    PSLCBN(),

    /**
     * Páginas sem link e sem barra de navegação.
     */
    PSLSBN();

}
