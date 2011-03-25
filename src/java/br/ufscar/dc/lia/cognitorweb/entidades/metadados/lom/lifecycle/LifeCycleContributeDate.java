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
 * Tag Date da tag Contribute da tag LifeCycle.
 *
 * @author David Buzatto
 */
@Root( name = "date" )
@Entity
@Transactional
public class LifeCycleContributeDate implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Element
    @Length( max = 100 )
    @NotNull
    private String dateTime;

    @OneToOne( mappedBy = "date" )
    private LifeCycleContribute contribute;

    @Element
    @OneToOne
    private LifeCycleContributeDateDescription description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public LifeCycleContribute getContribute() {
        return contribute;
    }

    public void setContribute(LifeCycleContribute contribute) {
        this.contribute = contribute;
    }

    public LifeCycleContributeDateDescription getDescription() {
        return description;
    }

    public void setDescription(LifeCycleContributeDateDescription description) {
        this.description = description;
    }

}
