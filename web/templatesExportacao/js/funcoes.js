/**
 * Funções para manipulação e criação da estrutura do material.
 *
 * @author    David Buzatto
 * @copyright (c) 2010, by David Buzatto
 * @date      02 de Janeiro de 2010
 *
 */

// estrutura da árvore
var foldersTree;

// indica a página aberta atualmente
var paginaAtual = 0;

// array para armazenar as páginas em ordem
var paginas = [];

// indica se os relacionamentos devem ser gerados
var deveGerarRelacionamentos = false;


// faz a carga de uma página
function carregarPagina( posicao, atualizarBotoes ) {

    var pagina = paginas[posicao];

    $.get(
        pagina.endereco,
        null,
        function( data, textStatus ){

            data = data.replace( '{titulo}', pagina.titulo );

            var dadosRelacionamentos = '';

            if ( deveGerarRelacionamentos ) {
                dadosRelacionamentos = gerarRelacionamentos( pagina );
            }

            data = data.replace( '{rodape}', dadosRelacionamentos );

            $( '#tbConteudo' ).html( data );

        },
        'html'
    );

    paginaAtual = posicao;

    $( '#paginaAtual').html( ' ' + ( paginaAtual + 1 ) + '/' + paginas.length + ' ' );

    if ( atualizarBotoes ) {
        if ( paginaAtual > 0 ) {
            $( '#btnAnterior' ).removeAttr( 'disabled' );
        } else {
            $( '#btnAnterior' ).attr( 'disabled', 'true' );
        }
        if ( paginaAtual < paginas.length - 1 ) {
            $( '#btnProximo' ).removeAttr( 'disabled' );
        } else {
            $( '#btnProximo' ).attr( 'disabled', 'true' );
        }
    }

}


// busca no array de páginas, o índice a partir do endereco;
function buscarPaginaPorEndereco( dados, endereco ) {

    for ( var i = 0; i < dados.length; i++ ) {
        if ( dados[i].endereco == endereco ) {
            return dados[i];
        }
    }

    return null;

}


// mostra a próxima página
function proximaPagina() {

    if ( paginaAtual < paginas.length - 1 ) {

        carregarPagina( ++paginaAtual, false );

        if ( paginaAtual == paginas.length - 1 ) {
            $( '#btnProximo' ).attr( 'disabled', 'true' );
        }

    }

    if ( paginaAtual >= 0 ) {
        $( '#btnAnterior' ).removeAttr( 'disabled' );
    }

}


// mostra a página anterior
function paginaAnterior() {

    if ( paginaAtual > 0 ) {

        carregarPagina( --paginaAtual, false );

        if ( paginaAtual == 0 ) {
            $( '#btnAnterior' ).attr( 'disabled', 'true' );
        }

    }

    if ( paginaAtual < paginas.length ) {
        $( '#btnProximo' ).removeAttr( 'disabled' );
    }

}


// função para gerar os links dos relacionamentos de uma página
function gerarRelacionamentos( pagina ) {

    var html = '';
    var rels = pagina.relacionamentos;

    if ( rels && rels.length != 0 ) {

        html = '<div style="font-size: 20px;">' + dadosMaterial.strings.lblRelacoes +
            ' ' + pagina.titulo + ':</div>';
        html += '<ul>';

        for ( var i = 0; i < rels.length; i++ ) {
            html += '<li>' + rels[i].relacao + gerarLink( rels[i] ) + '</li>';
        }

        html += '</ul>';

    }

    return html;

}


// função para gerar o link de um relacionamento
function gerarLink( rel ) {

    var pagina = buscarPaginaPorEndereco( paginas, rel.endereco );
    return ' <a class="linkRelacao" href="javascript:carregarPagina(' + pagina.ordem + ', true);">' + pagina.titulo + '</a>';

}


// cria a árvore
function criarArvore( dados ) {

    USETEXTLINKS = 1;
    STARTALLOPEN = 1;
    HIGHLIGHT = 1;
    HIGHLIGHT_BG = '#26486A';
    USEFRAMES = 1;
    ICONPATH = "../js/tree/";

    var cp = 0; // contador de páginas

    foldersTree = gFld( '&nbsp;' + dadosMaterial.titulo );
    foldersTree.treeID = dadosMaterial.id;
    foldersTree.xID = 'x' + dadosMaterial.id;
    foldersTree.iconSrc = ICONPATH + "book_open.png";
    foldersTree.iconSrcClosed = ICONPATH + "book.png";

    var montarArvore = function( dados, raiz ) {

        var c = undefined;
        var novoNo = undefined;
        var i = undefined;

        if ( dados.tipo == 'material' ) {
            c = ordenar( dados.componentes );
            if ( !c ) return;

            for ( i = 0; i < c.length; i++ ) {
                montarArvore( c[i], raiz )
            }
        }

        if ( dados.tipo == 'grupo' || dados.tipo == 'conceito' ) {

            novoNo = insFld( raiz, gFld( '&nbsp;' + dados.titulo ) );
            novoNo.xID = dados.id;
            novoNo.iconSrc = ICONPATH + "book_open.png";
            novoNo.iconSrcClosed = ICONPATH + "book.png";

            c = ordenar( dados.componentes );

            if ( !c ) return;

            for ( i = 0; i < c.length; i++ ) {
                montarArvore( c[i], novoNo );
            }

        }

        if ( dados.tipo == 'pagina' ) {

            novoNo = insDoc( raiz, gLnk( 'S', '&nbsp;' + dados.titulo, 'javascript:carregarPagina(' + cp + ', true);' ) );
            novoNo.xID = dados.id;
            novoNo.iconSrc = ICONPATH + "page.png";

            // adiciona uma página
            paginas[ cp ] = {
                titulo: dados.titulo,
                ordem: cp,
                endereco: dados.endereco,
                relacionamentos: dados.relacionamentos
            };
            cp++;

        }

    };

    montarArvore( dadosMaterial, foldersTree );
    initializeDocument();

}


// ordenação dos componentes beseado na ordem
function ordenar( componentes ) {
    
    var bound = componentes.length - 1;
    while ( true ) {
        var t = 0;
        for ( var i = 0; i < bound; i++ ) {
            if ( componentes[i].ordem > componentes[i+1].ordem ) {
                var temp = componentes[i];
                componentes[i] = componentes[i+1];
                componentes[i+1] = temp;
                t = i;
            }
        }
        if ( t == 0 )
            break;
        bound = t;
    }

    return componentes;

}


// função para expandir a árvore
function expandirArvore( folderObj ) {

    var childObj;

    if ( !folderObj.isOpen )
        clickOnNodeObj( folderObj );

    for ( var i = 0 ; i < folderObj.nChildren; i++ )  {
        childObj = folderObj.children[i];
        if ( typeof childObj.setState != "undefined" ) {
            expandirArvore( childObj );
        }

    }

}


// Função para contrair a árvore
function contrairArvore( folderObj ) {

    if ( folderObj.isOpen ) {
        // contrai
        clickOnNodeObj( folderObj );
        // abre o primeiro nível
        clickOnNodeObj( folderObj );
    }

}


// função para geração e configuração do documento (após a geração da árvore)
function configurarMaterial( mostrarControles, gerarRelacionamentos ) {

    var arvore = $( '#domRoot' );
    arvore.addClass( 'divArvore' );

    $( 'body' ).html( '<div style="position:absolute; top:0; left:0; "><table border="0"><tr><td><font size="-2"><a style="font-size:7pt;text-decoration:none;color:silver;" href="http://www.treemenu.net/" target="_blank"></a></font></td></tr></table></div>' );

    $( 'body' ).append( '<table id="tb" class="tb">' +
            '<tr>' +
                '<td id="tbMenu" class="tbMenu" rowspan="2"></td>' +
                '<td id="tbConteudo" class="tbConteudo"></td>' +
            '</tr>' +
            '<tr>' +
                '<td id="tbControle" class="tbControle">' +
                    '<div id="divBotoes" class="divBotoes">' +
                        '<input id="btnAnterior" type="button"/>' +
                        '<span id="paginaAtual"></span>' +
                        '<input id="btnProximo" type="button"/>' +
                    '</div>' +
                '</td>' +
            '</tr>' +
        '</table>'
    );

    $( '#tbMenu' ).html( arvore );

    $( '#tbMenu' ).append(
            '<div id="divBotoesArvore" class="divBotoesArvore">' +
                '<input id="btnExpandir" type="button"/> ' +
                '<input id="btnContrair" type="button"/>' +
            '</div>' );

    $( '#tbConteudo' ).html( '' );

    $( '#btnExpandir' ).attr( 'value', dadosMaterial.strings.btnExpandir );
    $( '#btnContrair' ).attr( 'value', dadosMaterial.strings.btnContrair );
    $( '#btnProximo' ).attr( 'value', dadosMaterial.strings.btnProximo );
    $( '#btnAnterior' ).attr( 'value', dadosMaterial.strings.btnAnterior );

    $( '#btnAnterior' ).attr( 'disabled', 'true' );

    $( '#btnExpandir' ).click( function( event ){
        expandirArvore( foldersTree );
    });

    $( '#btnContrair' ).click( function( event ){
        contrairArvore( foldersTree );
    });

    $( '#btnProximo' ).click( function( event ){
        proximaPagina();
    });

    $( '#btnAnterior' ).click( function( event ){
        paginaAnterior();
    });

    if ( !dadosMaterial.configs.mostrarControles ) {
        $( '#tbControle' ).hide();
    }

    $( 'title' ).html( dadosMaterial.titulo );

    deveGerarRelacionamentos = dadosMaterial.configs.gerarRelacionamentos;

    carregarPagina( 0, false );

}
