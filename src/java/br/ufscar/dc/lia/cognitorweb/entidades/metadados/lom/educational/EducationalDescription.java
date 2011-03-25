/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.educational;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.lom.comum.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.simpleframework.xml.*;
import org.springframework.transaction.annotation.*;

/**
 * Tag Description de Educational.
 *
 * @author David Buzatto
 */
@Root( name = "description" )
@Entity
@Transactional
public class EducationalDescription implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @NotNull
    private Educational educational;

    @ElementList( inline = true, required = false )
    @OneToMany
    private List< LangString > strings;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Educational getEducational() {
        return educational;
    }

    public void setEducational(Educational educational) {
        this.educational = educational;
    }

    public List< LangString > getStrings() {
        return strings;
    }

    public void setStrings(List< LangString > strings) {
        this.strings = strings;
    }

}
