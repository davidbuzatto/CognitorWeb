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
 * Página de um material.
 *
 * @author David Buzatto
 */
@Entity
@Transactional
public class Pagina extends ObjetoAprendizagem {

    @Column( columnDefinition="longtext" )
    @NotNull
    private String conteudoHtml;

    @ManyToMany
    private List< Imagem > imagens;

    @ManyToMany
    private List< Video > videos;

    @ManyToMany
    private List< Som > sons;

    /*
     * Quando uma página é uma página principal, quer dizer que independente
     * da ordem especificada, ela sempre fica logo em seguida ao conceito,
     * ou seja, é o primeiro filho do nó representado pelo conceito.
     *
     * Um conceito sempre tem uma página relacionada a ele e a página não pode
     * ser removida. Uma página principal não pode ser removida, nem pode ser
     * compartilhada.
     *
     * Um grupo não tem página principal e essa pagina pode ou não ser
     * compartilhada.
     */
    @NotNull
    private boolean principal;

    @ManyToOne
    @NotNull
    private Usuario usuario;

    @OneToOne
    @NotNull
    private Metadata metadata;

    @OneToMany( mappedBy = "pagina" )
    private List<ConceitoComPagina> conceitosComPaginas;

    @OneToMany( mappedBy = "pagina" )
    private List<GrupoComPagina> gruposComPaginas;
    
    public String getConteudoHtml() {
        return conteudoHtml;
    }

    public void setConteudoHtml(String conteudoHtml) {
        this.conteudoHtml = conteudoHtml;
    }
    
    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Som> getSons() {
        return sons;
    }

    public void setSons(List<Som> sons) {
        this.sons = sons;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal( boolean principal ) {
        this.principal = principal;
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

    public List<ConceitoComPagina> getConceitosComPaginas() {
        return conceitosComPaginas;
    }

    public void setConceitosComPaginas( List<ConceitoComPagina> conceitosComPaginas ) {
        this.conceitosComPaginas = conceitosComPaginas;
    }

    public List<GrupoComPagina> getGruposComPaginas() {
        return gruposComPaginas;
    }

    public void setGruposComPaginas( List<GrupoComPagina> gruposComPaginas ) {
        this.gruposComPaginas = gruposComPaginas;
    }

}
