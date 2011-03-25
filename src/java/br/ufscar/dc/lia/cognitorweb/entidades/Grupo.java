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
 * Nó de um material. Pode ser um grupo ou um conceito.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Grupo extends ObjetoAprendizagem {

    @OneToMany( mappedBy = "grupo" )
    private List<GrupoComPagina> gruposComPaginas;

    @OneToMany( mappedBy = "grupo" )
    private List<MaterialComGrupo> materiaisComGrupos;


    // ligação entre grupos
    @OneToMany( mappedBy = "grupoPrincipal" )
    private List<GrupoComGrupo> grupos;

    @OneToMany( mappedBy = "grupoQueContem" )
    private List<GrupoComGrupo> gruposQueContem;


    @ManyToOne
    @NotNull
    private Usuario usuario;

    @OneToOne
    @NotNull
    private Metadata metadata;

    public List<GrupoComPagina> getGruposComPaginas() {
        return gruposComPaginas;
    }

    public void setGruposComPaginas( List<GrupoComPagina> gruposComPaginas ) {
        this.gruposComPaginas = gruposComPaginas;
    }

    public List<MaterialComGrupo> getMateriaisComGrupos() {
        return materiaisComGrupos;
    }

    public void setMateriaisComGrupos( List<MaterialComGrupo> materiaisComGrupos ) {
        this.materiaisComGrupos = materiaisComGrupos;
    }

    public List<GrupoComGrupo> getGrupos() {
        return grupos;
    }

    public void setGrupos( List<GrupoComGrupo> grupos ) {
        this.grupos = grupos;
    }

    public List<GrupoComGrupo> getGruposQueContem() {
        return gruposQueContem;
    }

    public void setGruposQueContem( List<GrupoComGrupo> gruposQueContem ) {
        this.gruposQueContem = gruposQueContem;
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
