/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.sensocomum;

/**
 * Classe TaggedToken.
 *
 * @author David Buzatto
 */
public class TaggedToken {

    private String token;
    private String tag;

    public TaggedToken() {
    }

    public TaggedToken(String token, String tag) {
        this.token = token;
        this.tag = tag;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
