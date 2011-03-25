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
public class Conceito extends ObjetoAprendizagem {

    @OneToMany( mappedBy = "conceito" )
    private List<ConceitoComPagina> conceitosComPaginas;

    @OneToMany( mappedBy = "conceito" )
    private List<MaterialComConceito> materiaisComConceitos;


    // ligação entre conceitos (nível de hierarquia)
    @OneToMany( mappedBy = "conceitoPrincipal" )
    private List<ConceitoComConceito> conceitos;

    @OneToMany( mappedBy = "conceitoQueContem" )
    private List<ConceitoComConceito> conceitosQueContem;


    // ligação entre conceitos (nível de ligação no mapa de conceitos)
    /*
     * Nessa versão da ferramenta, os conceitosOrigem vão conter apenas um
     * conceito. Caso for ser implementado futuramente o reaproveitamento de
     * conceitos, talvez seja necessário inserir o atributo material em
     * RelacaoMapaConceito, para que se possa dizer em qual material um dado
     * conceito tem suas relações.
     */
    @OneToMany( mappedBy = "conceitoOrigem" )
    private List<RelacaoMapaConceito> conceitosOrigem;

    @OneToMany( mappedBy = "conceitoDestino" )
    private List<RelacaoMapaConceito> conceitosDestino;


    @ManyToOne
    @NotNull
    private Usuario usuario;

    @OneToOne
    @NotNull
    private Metadata metadata;

    public List<ConceitoComPagina> getConceitosComPaginas() {
        return conceitosComPaginas;
    }

    public void setConceitosComPaginas( List<ConceitoComPagina> conceitosComPaginas ) {
        this.conceitosComPaginas = conceitosComPaginas;
    }

    public List<MaterialComConceito> getMateriaisComConceitos() {
        return materiaisComConceitos;
    }

    public void setMateriaisComConceitos( List<MaterialComConceito> materiaisComConceitos ) {
        this.materiaisComConceitos = materiaisComConceitos;
    }

    public List<ConceitoComConceito> getConceitos() {
        return conceitos;
    }

    public void setConceitos( List<ConceitoComConceito> conceitos ) {
        this.conceitos = conceitos;
    }

    public List<ConceitoComConceito> getConceitosQueContem() {
        return conceitosQueContem;
    }

    public void setConceitosQueContem( List<ConceitoComConceito> conceitosQueContem ) {
        this.conceitosQueContem = conceitosQueContem;
    }

    public List<RelacaoMapaConceito> getConceitosOrigem() {
        return conceitosOrigem;
    }

    public void setConceitosOrigem( List<RelacaoMapaConceito> conceitosOrigem ) {
        this.conceitosOrigem = conceitosOrigem;
    }

    public List<RelacaoMapaConceito> getConceitosDestino() {
        return conceitosDestino;
    }

    public void setConceitosDestino( List<RelacaoMapaConceito> conceitosDestino ) {
        this.conceitosDestino = conceitosDestino;
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
