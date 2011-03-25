/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.rights;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag CopyrightAndOtherRestrictions de Rights.
 * 
 * @author David Buzatto
 */
@Root( name = "copyrightAndOtherRestrictions" )
@Entity
@Transactional
public class RightsCopyrightAndOtherRestrictions implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 30 )
    @NotNull
    private String source;

    @Element
    @Column( name = "rvalue" )
    @Length( max = 30 )
    @NotNull
    private String value;

    @OneToOne( mappedBy = "copyrightAndOtherRestrictions" )
    private Rights rights;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Rights getRights() {
        return rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }

}
