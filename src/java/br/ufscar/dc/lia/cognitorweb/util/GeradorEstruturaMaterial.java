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
public class GeradorEstruturaMaterial {

    private String iconeMaterial;
    private String iconeConceito;
    private String iconeGrupo;
    private String iconePagina;

    public GeradorEstruturaMaterial(
            String iconeMaterial,
            String iconeConceito,
            String iconeGrupo,
            String iconePagina ) {
        this.iconeMaterial = iconeMaterial;
        this.iconeConceito = iconeConceito;
        this.iconeGrupo = iconeGrupo;
        this.iconePagina = iconePagina;

    }

    /*
     * Gera a estrutura do material de forma recursiva.
     */
    public String gerarEstruturaMaterial( Material m ) {

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
                sb.append( jsonToConceito( c, mc.getOrdem() ) );
                extrairDados( c, sb );
                sb.append( "]}," );
            }

            for ( MaterialComGrupo mg : m.getMateriaisComGrupos() ) {
                parada = true;
                Grupo g = mg.getGrupo();
                sb.append( jsonToGrupo( g, mg.getOrdem() ) );
                extrairDados( g, sb );
                sb.append( "]}," );
            }

            for ( MaterialComMaterial mm : m.getMateriais() ) {
                parada = true;
                Material mi = mm.getMaterialQueContem();
                sb.append( jsonToMaterial( mi, mm.getOrdem() ) );
                extrairDados( mi, sb );
                sb.append( "]}," );
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
                sb.append( jsonToPagina( p, cp.getOrdem() ) );
            }

            for ( ConceitoComConceito cc : c.getConceitos() ) {
                parada = true;
                Conceito ci = cc.getConceitoQueContem();
                sb.append( jsonToConceito( ci, cc.getOrdem() ) );
                extrairDados( ci, sb );
                sb.append( "]}," );
            }

            if ( !parada )
                return;

        }

        if ( oa instanceof Grupo ) {

            Grupo g = ( Grupo ) oa;
            boolean parada = false;

            for ( GrupoComPagina gp : g.getGruposComPaginas() ) {
                parada = true;
                Pagina p = gp.getPagina();
                sb.append( jsonToPagina( p, gp.getOrdem() ) );
            }

            for ( GrupoComGrupo gg : g.getGrupos() ) {
                parada = true;
                Grupo gi = gg.getGrupoQueContem();
                sb.append( jsonToGrupo( gi, gg.getOrdem() ) );
                extrairDados( gi, sb );
                sb.append( "]}," );
            }

            if ( !parada )
                return;

        }

    }

    /*
     * Métodos para criação de json a partir de objetos.
     */
    private String jsonToPagina( Pagina p, int ordem ) {
        return "{" +
                "idInterno: " + p.getId() + ", " +
                "ordem: " + ordem + ", " +
                "compartilhado: " + p.isCompartilhado() + ", " +
                "principal: " + p.isPrincipal() + ", " +
                "tipo: 'pagina', " +
                "text: '" + p.getTitulo() + "', " +
                "icon: '" + iconePagina + "', " +
                "leaf: true " +
                "}, ";
    }

    private String jsonToConceito( Conceito c, int ordem ) {
        return "{" +
                "idInterno: " + c.getId() + ", " +
                "novo: false, " +
                "ordem: " + ordem + ", " +
                "compartilhado: " + c.isCompartilhado() + ", " +
                "tipo: 'conceito', " +
                "text: '" + c.getTitulo() + "', " +
                "icon: '" + iconeConceito + "', " +
                "relacoesHierarquia: " + jsonToHiearquiaConceito( c ) + ", " +
                "relacoesMapa: " + jsonToRelacoesConceito( c ) + ", " +
                "leaf: false," +
                "children: [";
    }

    private String jsonToGrupo( Grupo g, int ordem ) {
        return "{" +
                "idInterno: " + g.getId() + ", " +
                "ordem: " + ordem + ", " +
                "compartilhado: " + g.isCompartilhado() + ", " +
                "tipo: 'grupo', " +
                "text: '" + g.getTitulo() + "', " +
                "icon: '" + iconeGrupo + "', " +
                "leaf: false, " +
                "children: [";
    }

    private String jsonToMaterial( Material m, int ordem ) {
        return "{" +
                "idInterno: " + m.getId() + ", " +
                "ordem: " + ordem + ", " +
                "compartilhado: " + m.isCompartilhado() + ", " +
                "estrCon: " + m.isConhecimentoEstruturado() + ", " +
                "tipo: 'material', " +
                "layout: '" + String.valueOf( m.getLayout() ) + "', " +
                "text: '" + m.getTitulo() + "', " +
                "icon: '" + iconeMaterial + "', " +
                "leaf: false, " +
                "children: [";
    }

    private String jsonToHiearquiaConceito( Conceito con ) {

        StringBuilder sb = new StringBuilder( "[" );

        for ( ConceitoComConceito c : con.getConceitos() ) {

            sb.append( "{" +
                    "idInterno: " + c.getConceitoQueContem().getId() + ", " +
                    "titulo: '" + c.getConceitoQueContem().getTitulo() + "', " +
                    "relacaoMinsky: '" + c.getRelacaoMinsky() + "', " +
                    "relacaoUsuario: '" + c.getRelacaoUsuario() + "'}, " );

        }

        return sb.append( "]" ).toString();

    }

    private String jsonToRelacoesConceito( Conceito con ) {

        StringBuilder sb = new StringBuilder( "[" );

        for ( RelacaoMapaConceito r : con.getConceitosOrigem() ) {

            sb.append( "{" +
                    "idInterno: " + r.getConceitoDestino().getId() + ", " +
                    "titulo: '" + r.getConceitoDestino().getTitulo() + "', " +
                    "relacaoMinsky: '" + r.getRelacaoMinsky() + "', " +
                    "relacaoUsuario: '" + r.getRelacaoUsuario() +  "'}, " );

        }

        return sb.append( "]" ).toString();

    }

}
