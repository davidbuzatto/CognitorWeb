/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.mapaconceitos;

import java.util.*;
import org.simpleframework.xml.*;

/**
 * Um conceito do mapa de conceitos.
 * Utilizado na deserialização.
 *
 * @author David Buzatto
 */
@Root( name = "conceito" )
public class ConceitoMapa {

    @Element
    private long id;

    @Element
    private String titulo;

    @Element
    private boolean novo;

    @Element
    private int ordem;

    @Element
    private boolean raiz;

    @ElementList( required = false )
    private List< ConceitoMapa > filhos;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo( String titulo ) {
        this.titulo = titulo;
    }

    public boolean isRaiz() {
        return raiz;
    }

    public void setRaiz( boolean raiz ) {
        this.raiz = raiz;
    }

    public boolean isNovo() {
        return novo;
    }

    public void setNovo( boolean novo ) {
        this.novo = novo;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem( int ordem ) {
        this.ordem = ordem;
    }

    public List<ConceitoMapa> getFilhos() {
        return filhos;
    }

    public void setFilhos( List<ConceitoMapa> filhos ) {
        this.filhos = filhos;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append( "id: " + id + ", título: " + titulo +
                ", ordem: " + ordem + ", raiz: " + raiz );
        
        if ( filhos != null && filhos.size() > 0 ) {
            sb.append( "\nfilhos:\n" );
            for ( ConceitoMapa c : filhos ) {
                sb.append( c + "\n" );
            }
        }

        return sb.toString();

    }
    
}
