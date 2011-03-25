/**
 * Arquivo de scripts da tela de consultas.
 *
 * @author    David Buzatto
 * @copyright (c) 2010, by David Buzatto
 * @date      07 de janeiro de 2010
 *
 */

/*
 * Contexto precisa ser global para ser acessado pela funçã download.
 */
var contexto = '';

function start( c ) {

    contexto = c;

    var idCamadaErro = '#camadaAjuda';
    var idMsgErro = '#mAjuda';
    var tipoPesquisa = 'pn';

    /*
     * Esconde as camadas que não são usadas no início
     */
    $( '#overlay' ).hide();
    $( '#divPesquisaAvancada' ).hide();
    $( '#divCarregando' ).hide();

    /*
     * Faz a carga da tabela de Strings através do loadI18N e associa
     * ao. Após a carga a função getMsg pode ser usada.
     */
    $.loadI18N({
        resourceBundle: contexto + '/i18n/TabelaStringsConsultas',
        resourceExt: '.jsp'
    });

    $( '#btnPesquisaAvancada' ).click( function( evt ) {
        $( '#divPesquisaNormal' ).fadeOut( "def", function(){
            $( '#divPesquisaAvancada' ).fadeIn();
            tipoPesquisa = 'pa';
        });
    });

    $( '#btnPesquisaNormal' ).click( function( evt ){
        $( '#divPesquisaAvancada' ).fadeOut( "def", function(){
            $( '#divPesquisaNormal' ).fadeIn();
            tipoPesquisa = 'pn';
        });
    });

    /*
     * Caso o usuário tecle enter em algum campo de pesquisa, executa a pesquisa
     */
    $( '#campoPesq' ).keypress( function( evt ) {
        if ( evt.keyCode == 13 ) {
            $( '#btnPesquisar' ).click();
        }
    });

    $( '#campoPesqTit' ).keypress( function( evt ) {
        if ( evt.keyCode == 13 ) {
            $( '#btnPesquisar' ).click();
        }
    });

    $( '#campoPesqDesc' ).keypress( function( evt ) {
        if ( evt.keyCode == 13 ) {
            $( '#btnPesquisar' ).click();
        }
    });

    $( '#campoPesqKey' ).keypress( function( evt ) {
        if ( evt.keyCode == 13 ) {
            $( '#btnPesquisar' ).click();
        }
    });

    /*
     * Eventos de exibição da ajuda.
     */
    $( '#ajudaTit').mouseover( function( evt ) {
        $( idMsgErro ).html( $.getMsg( 'ajudaTit' ) );
        $( idCamadaErro ).css( 'left', evt.clientX + 10 );
        $( idCamadaErro ).css( 'top', evt.clientY - 20 );
        $( idCamadaErro ).fadeIn();
    });

    $( '#ajudaTit' ).mouseout( function( evt ) {
        $( idMsgErro ).html('');
        $( idCamadaErro ).hide();
    });

    $( '#ajudaDesc').mouseover( function( evt ) {
        $( idMsgErro ).html( $.getMsg( 'ajudaDesc' ) );
        $( idCamadaErro ).css( 'left', evt.clientX + 10 );
        $( idCamadaErro ).css( 'top', evt.clientY - 20 );
        $( idCamadaErro ).fadeIn();
    });

    $( '#ajudaDesc' ).mouseout( function( evt ) {
        $( idMsgErro ).html('');
        $( idCamadaErro ).hide();
    });

    $( '#ajudaKey').mouseover( function( evt ) {
        $( idMsgErro ).html( $.getMsg( 'ajudaKey' ) );
        $( idCamadaErro ).css( 'left', evt.clientX + 10 );
        $( idCamadaErro ).css( 'top', evt.clientY - 20 );
        $( idCamadaErro ).fadeIn();
    });

    $( '#ajudaKey' ).mouseout( function( evt ) {
        $( idMsgErro ).html('');
        $( idCamadaErro ).hide();
    });

    /*
     * Variáveis para i18n.
     */
    var strRes = $.getMsg( 'resultados' );
    var strTit = $.getMsg( 'titulor' );
    var strAut = $.getMsg( 'autorr' );
    var strDesc = $.getMsg( 'descricaor' );
    var strKey = $.getMsg( 'palavrasChaver' );
    var strDataC = $.getMsg( 'dataCriacao' );
    var strDataA = $.getMsg( 'dataAtualizacao' );
    var strBaixar = $.getMsg( 'baixar' );
    var strMsg = $.getMsg( 'mensagemResultado' );
    var strNDef = $.getMsg( 'naoDefinido' );

    /*
     * Execução do botão pesquisar.
     */
    $( '#btnPesquisar' ).click( function( evt ) {

        $( '#overlay' ).fadeIn();
        $( '#divCarregando' ).fadeIn();
        $( '#tbResultados' ).html( '' );

        $.ajax( {
            dataType: 'json',
            cache: false,
            type: 'post',
            url: contexto + '/servlets/PesquisaMaterialServlet',
            data: {
                tipoPesquisa: tipoPesquisa,
                pesq: $( '#campoPesq' )[0].value,
                pesqTit: $( '#campoPesqTit' )[0].value,
                pesqDesc: $( '#campoPesqDesc' )[0].value,
                pesqKey: $( '#campoPesqKey' )[0].value
            },
            success: function( data, textStatus ) {

                var teveResultado = false;
                var dados = '<tr><th class="labelCabTbResultados">' + strRes + '</th><tr>';

                if ( data.success ) {

                    var m = data.dados;

                    for ( var i = 0; i < m.length; i++ ) {

                        teveResultado = true;

                        if ( i % 2 == 0 )
                            dados += '<tr class="linhaPar"><td>';
                        else
                            dados += '<tr class="linhaImpar"><td>';

                        dados += '<table class="tbItemMaterial">';

                        dados += '<tr>';
                        dados += '<td><font class="labelItemMaterial">' + strTit + '</font><br/>';
                        dados += m[i].titulo + '</td>';
                        dados += '</tr>';
                        
                        dados += '<tr>';
                        dados += '<td><font class="labelItemMaterial">' + strAut + '</font><br/>';
                        dados += m[i].autor + '</td>';
                        dados += '</tr>';

                        dados += '<tr>';
                        dados += '<td><font class="labelItemMaterial">' + strDesc + '</font><br/>';
                        dados += m[i].descricao == '' ? strNDef : m[i].descricao + '</td>';
                        dados += '</tr>';

                        dados += '<tr>';
                        dados += '<td><font class="labelItemMaterial">' + strKey + '</font><br/>';
                        dados += m[i].palavrasChave == '' ? strNDef : m[i].palavrasChave + '</td>';
                        dados += '</tr>';

                        dados += '<tr>';
                        dados += '<td><br/><font class="labelPeqItemMaterial">' + strDataC + '</font> ' + m[i].dataCriacao +
                            '<br/><font class="labelPeqItemMaterial">' + strDataA + '</font> ' + m[i].dataAtualizacao + '</td>';
                        dados += '</tr>';

                        dados += '<tr>';
                        dados += '<td><font class="labelPeqItemMaterial">' + strBaixar + '</font><br/>' +
                            '<a href="javascript:download(\'scorm\',' + m[i].id + ');"><img src="' + contexto + '/imagens/btnExportacaoSCORM.png"/></a> ' +
                            '<a href="javascript:download(\'html\',' + m[i].id + ');"><img src="' + contexto + '/imagens/btnExportacaoHTML.png"/></a> ';
                        dados += '</tr>';

                        dados += '</table>';
                        dados += '</tr></td>';

                    }

                }

                if ( !teveResultado ) {
                    dados += '<tr>';
                        dados += '<td>' + strMsg + '</td>';
                        dados += '</tr>';
                }

                $( '#tbResultados' ).html( dados );

            },
            complete: function( data, textStatus ) {
                $( '#divCarregando' ).fadeOut();
                $( '#overlay' ).fadeOut();
            }
        });

    });

    /*
     * Internacionaliza todos os labels.
     */
    $( '#lblConsMat' ).html( $.getMsg( 'tituloConsulta' ) );
    $( '#btnPesquisar' )[0].value = $.getMsg( 'pesquisar' );
    $( '#lblAjuda' ).html( $.getMsg( 'ajuda' ) );
    $( '#lblCarregando' ).html( $.getMsg( 'aguarde' ) );
    $( '#lblPesqAvan' ).html( $.getMsg( 'pesqAvan' ) );
    $( '#lblPesqNorm' ).html( $.getMsg( 'pesqNorm' ) );
    $( '#lblTit' ).html( $.getMsg( 'titulo' ) );
    $( '#lblDesc' ).html( $.getMsg( 'descricao' ) );
    $( '#lblKey' ).html( $.getMsg( 'palavrasChave' ) );

}

/*
 * Função para download de pacotes html e scorm.
 */
function download( tipo, id ) {
    window.open(
            contexto +
            '/servlets/ExportacaoMaterialServlet?idMaterial=' + id +
            '&tipo=' + tipo,
            '_blank' );
}