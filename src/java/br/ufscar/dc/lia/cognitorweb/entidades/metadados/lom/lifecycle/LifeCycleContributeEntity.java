/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.lifecycle;

import java.io.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Entity da tag Contribute de LifeCycle.
 *
 * @author David Buzatto
 */
@Root( name = "entity" )
@Entity
@Transactional
public class LifeCycleContributeEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private LifeCycleContribute contribute;

    @Text
    @Column( name = "lvalue" )
    @Length( max = 1000 )
    @NotNull
    private String value;

    public LifeCycleContributeEntity() {}

    public LifeCycleContributeEntity( String value ) {
        setValue( value );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LifeCycleContribute getContribute() {
        return contribute;
    }

    public void setContribute(LifeCycleContribute contribute) {
        this.contribute = contribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
