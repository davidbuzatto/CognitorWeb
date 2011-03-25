/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.sensocomum;

/**
 *
 * @author David
 */
public class AnalogyPart {

    private String predicate;
    private String expert1;
    private String expert2;
    private String commonSense1;
    private String commonSense2;

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public String getExpert1() {
        return expert1;
    }

    public void setExpert1(String expert1) {
        this.expert1 = expert1;
    }

    public String getExpert2() {
        return expert2;
    }

    public void setExpert2(String expert2) {
        this.expert2 = expert2;
    }

    public String getCommonSense1() {
        return commonSense1;
    }

    public void setCommonSense1(String commonSense1) {
        this.commonSense1 = commonSense1;
    }

    public String getCommonSense2() {
        return commonSense2;
    }

    public void setCommonSense2(String commonSense2) {
        this.commonSense2 = commonSense2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( "(" + predicate );
        sb.append( " \"" + expert1 + "\"" );
        sb.append( " \"" + expert2 + "\"" );
        sb.append( " \"" + commonSense1 + "\"" );
        sb.append( " \"" + commonSense2 + "\")" );
        return sb.toString();
    }

}
