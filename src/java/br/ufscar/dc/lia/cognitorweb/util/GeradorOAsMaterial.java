/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufscar.dc.lia.cognitorweb.util;

import br.ufscar.dc.lia.cognitorweb.entidades.*;

/**
 * Classe com métodos para a geração da estrutura de um material.
 *
 * @author David Buzatto
 */
public class GeradorOAsMaterial {

    private String iconeGrupo;
    private String iconePagina;
    private String iconeImagem;
    private String iconeVideo;
    private String iconeSom;

    public GeradorOAsMaterial(
            String iconeGrupo,
            String iconePagina,
            String iconeImagem,
            String iconeVideo,
            String iconeSom ) {

        this.iconeGrupo = iconeGrupo;
        this.iconePagina = iconePagina;
        this.iconeImagem = iconeImagem;
        this.iconeVideo = iconeVideo;
        this.iconeSom = iconeSom;

    }

    /*
     * Gera os OAs do material de forma recursiva.
     */
    public String gerarOAsMaterial( Material m ) {

        StringBuilder sb = new StringBuilder();

        sb.append( "[" );
        extrairDados( m, sb );
        sb.append( "]" );

        return sb.toString();

    }

    /*
     * Extrai os dados da estrutura do material.
     */
    private void extrairDados( ObjetoAprendizagem oa, StringBuilder sb ) {

        if ( oa instanceof Material ) {

            Material m = ( Material ) oa;
            boolean parada = false;

            for ( MaterialComConceito mc : m.getMateriaisComConceitos() ) {
                parada = true;
                Conceito c = mc.getConceito();
                extrairDados( c, sb );
            }

            for ( MaterialComGrupo mg : m.getMateriaisComGrupos() ) {
                parada = true;
                Grupo g = mg.getGrupo();
                extrairDados( g, sb );
            }

            if ( !parada )
                return;

        }

        if ( oa instanceof Conceito ) {

            Conceito c = ( Conceito ) oa;
            boolean parada = false;

            for ( ConceitoComPagina cp : c.getConceitosComPaginas() ) {
                parada = true;
                Pagina p = cp.getPagina();
                extrairDados( p, sb );
            }

            for ( ConceitoComConceito cc : c.getConceitos() ) {
                parada = true;
                Conceito ci = cc.getConceitoQueContem();
                extrairDados( ci, sb );
            }

            if ( !parada )
                return;

        }

        if ( oa instanceof Grupo ) {

            Grupo g = ( Grupo ) oa;
            boolean parada = false;

            sb.append( jsonToGrupo( g ) );

            for ( GrupoComPagina gp : g.getGruposComPaginas() ) {
                parada = true;
                Pagina p = gp.getPagina();
                extrairDados( p, sb );
            }

            for ( GrupoComGrupo gg : g.getGrupos() ) {
                parada = true;
                Grupo gi = gg.getGrupoQueContem();
                extrairDados( gi, sb );
            }

            if ( !parada )
                return;

        }

        if ( oa instanceof Pagina ) {

            Pagina p = ( Pagina ) oa;
            boolean parada = false;

            sb.append( jsonToPagina( p ) );

            for ( Imagem i : p.getImagens() ) {
                parada = true;
                sb.append( jsonToImagem( i ) );
            }

            for ( Video v : p.getVideos() ) {
                parada = true;
                sb.append( jsonToVideo( v ) );
            }

            for ( Som s : p.getSons() ) {
                parada = true;
                sb.append( jsonToSom( s ) );
            }

            if ( !parada )
                return;

        }

    }

    /*
     * Métodos para criação de json a partir de objetos.
     */
    private String jsonToGrupo( Grupo g ) {
        return "{" +
                "idInterno: " + g.getId() + ", " +
                "tipo: 'grupo', " +
                "text: '" + g.getTitulo() + "', " +
                "icon: '" + iconeGrupo + "', " +
                "leaf: true " +
                "}, ";
    }
    
    private String jsonToPagina( Pagina p ) {
        return "{" +
                "idInterno: " + p.getId() + ", " +
                "tipo: 'pagina', " +
                "text: '" + p.getTitulo() + "', " +
                "icon: '" + iconePagina + "', " +
                "leaf: true " +
                "}, ";
    }

    private String jsonToImagem( Imagem i ) {
        return "{" +
                "idInterno: " + i.getId() + ", " +
                "tipo: 'imagem', " +
                "text: '" + i.getTitulo() + "', " +
                "icon: '" + iconeImagem + "', " +
                "leaf: true " +
                "}, ";
    }

    private String jsonToVideo( Video v ) {
        return "{" +
                "idInterno: " + v.getId() + ", " +
                "tipo: 'video', " +
                "text: '" + v.getTitulo() + "', " +
                "icon: '" + iconeVideo + "', " +
                "leaf: true " +
                "}, ";
    }

    private String jsonToSom( Som s ) {
        return "{" +
                "idInterno: " + s.getId() + ", " +
                "tipo: 'som', " +
                "text: '" + s.getTitulo() + "', " +
                "icon: '" + iconeSom + "', " +
                "leaf: true " +
                "}, ";
    }

}
