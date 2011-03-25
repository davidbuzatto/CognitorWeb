/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.entidades;

import br.ufscar.dc.lia.cognitorweb.enumeracoes.*;
import br.ufscar.dc.lia.cognitorweb.entidades.metadados.*;
import java.util.*;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.springframework.transaction.annotation.*;

/**
 * Material educacional.
 *
 * @author David Buzatto
 */
@Entity
@org.hibernate.annotations.NamedNativeQuery( 
    name="consultaMateriais",
    query="call consultaMateriais( :titulo, :descricao, :palavraChave, :inicio, :fim )",
    callable = true,
    resultClass = Material.class )
@Transactional
public class Material extends ObjetoAprendizagem {

    @OneToMany( mappedBy = "material" )
    private List<MaterialComConceito> materiaisComConceitos;

    @OneToMany( mappedBy = "material" )
    private List<MaterialComGrupo> materiaisComGrupos;


    // ligação entre materiais
    @OneToMany( mappedBy = "materialPrincipal" )
    private List<MaterialComMaterial> materiais;

    @OneToMany( mappedBy = "materialQueContem" )
    private List<MaterialComMaterial> materiaisQueContem;


    private boolean conhecimentoEstruturado;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LayoutMaterial layout;

    @ManyToOne
    @NotNull
    private Usuario usuario;

    @OneToOne
    @NotNull
    private Metadata metadata;

    public List<MaterialComConceito> getMateriaisComConceitos() {
        return materiaisComConceitos;
    }

    public void setMateriaisComConceitos( List<MaterialComConceito> materiaisComConceitos ) {
        this.materiaisComConceitos = materiaisComConceitos;
    }

    public List<MaterialComGrupo> getMateriaisComGrupos() {
        return materiaisComGrupos;
    }

    public void setMateriaisComGrupos( List<MaterialComGrupo> materiaisComGrupos ) {
        this.materiaisComGrupos = materiaisComGrupos;
    }

    public List<MaterialComMaterial> getMateriais() {
        return materiais;
    }

    public void setMateriais( List<MaterialComMaterial> materiais ) {
        this.materiais = materiais;
    }

    public List<MaterialComMaterial> getMateriaisQueContem() {
        return materiaisQueContem;
    }

    public void setMateriaisQueContem( List<MaterialComMaterial> materiaisQueContem ) {
        this.materiaisQueContem = materiaisQueContem;
    }

    public boolean isConhecimentoEstruturado() {
        return conhecimentoEstruturado;
    }

    public void setConhecimentoEstruturado( boolean conhecimentoEstruturado ) {
        this.conhecimentoEstruturado = conhecimentoEstruturado;
    }

    public LayoutMaterial getLayout() {
        return layout;
    }

    public void setLayout( LayoutMaterial layout ) {
        this.layout = layout;
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
