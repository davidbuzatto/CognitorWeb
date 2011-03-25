/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.springframework.transaction.annotation.*;

/**
 * Asset do tipo som.
 * @author David Buzatto
 */
@Entity
@Transactional
public class Som extends Asset {

    @ManyToMany( mappedBy = "sons" )
    private List< Pagina > paginasQueContem;

    @ManyToOne
    @NotNull
    private Usuario usuario;

    @OneToOne
    @NotNull
    private Metadata metadata;

    public List<Pagina> getPaginasQueContem() {
        return paginasQueContem;
    }

    public void setPaginasQueContem( List<Pagina> paginasQueContem ) {
        this.paginasQueContem = paginasQueContem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

}
