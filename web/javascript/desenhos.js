/**
 * Funções de desenho.
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      01 de Dezembro de 2009
 *
 */


/*
 * Classes de desenho.
 */

/*
 * Classe para desenho do conceito.
 */
Desenhos.Conceito = function( titulo, x, y, largura, altura, margem ) {

    this.titulo = titulo;
    this.x = x;
    this.y = y;
    this.largura = largura;
    this.altura = altura;
    this.margem = margem;

    // retorna um objeto com as coordenadas x e y do centro desse conceito.
    this.getCenter = function() {
        return {
            x: this.x + ( this.largura / 2 ),
            y: this.y + ( this.altura / 2 )
        };
    };

    // desenha o conceito
    this.paintMe = function( jg ) {

        jg.setColor( '#DAEEFF' );
        jg.fillRect( this.x, this.y, this.largura, this.altura );

        jg.setColor( '#BAD0EE' );
        jg.setStroke( 2 );
        jg.drawRect( x, y, this.largura, this.altura );

        jg.setColor( '#000000' );
        jg.drawString( this.titulo, this.x + this.margem, this.y + 7 );

    };

};

/*
 * Classe para desenho da relação.
 */
Desenhos.Relacao = function( conceito1, conceito2, labelRelacao ) {

    this.conceito1 = conceito1;
    this.conceito2 = conceito2;
    this.labelRelacao = labelRelacao;

    // gera a coordenada do meio para desenhar o label da relação
    this.middle = function() {

        var c1 = this.conceito1.getCenter();
        var c2 = this.conceito2.getCenter();

        var rX = c1.x > c2.x ? c1.x - c2.x : c2.x - c1.x
        rX /= 2;

        var x = c1.x > c2.x ? this.conceito2.getCenter().x + rX : this.conceito1.getCenter().x + rX;

        var rY = c1.y > c2.y ? c1.y - c2.y : c2.y - c1.y;
        rY /= 2;

        var y = this.conceito1.getCenter().y + rY;

        return {
            x: x,
            y: y
        };
    }

    // desenha o relacionamento
    this.paintMe = function( jg ) {

        jg.setColor( '#000000' );
        jg.setStroke( 1 );

        jg.drawLine(
            this.conceito1.getCenter().x,
            this.conceito1.getCenter().y,
            this.conceito2.getCenter().x,
            this.conceito2.getCenter().y );

        var meio = this.middle();

        jg.drawString( this.labelRelacao, meio.x, meio.y );

    }

};


/*
 * Função para desenhar o mapa de conceitos
 */
Desenhos.desenharMapa = function( jg, dadosGerados ) {

    var dados = Ext.util.JSON.decode( dadosGerados );

    // conceitos e relacoes
    var c = dados.conceitos;
    var r = dados.relacoes;


    // limpando
    jg.clear();


    // criando os conceitos
    var margemX = 10;
    var margemY = 10;
    var margemConceito = 5;
    var espacamentoHorizontal = 80;
    var espacamentoVertical = 80;
    var espEmNivel = [];
    var totalNivel = [];
    var max = 0;
    var conceitos = [];

    // fazendo cálculos para a criação dos conceitos
    // tamanho total em pixels de cada nível da árvore
    for ( var i = 0; i < c.length; i++ ) {

        var la = c[i].largura + ( c[i].largura / 2 ) + ( margemConceito * 2 );

        if ( !totalNivel[c[i].nivel] ) {
            totalNivel[c[i].nivel] = 0;
        }

        totalNivel[c[i].nivel] += la + espacamentoHorizontal;

    }

    // maior dos níveis
    for ( i = 0; i < totalNivel.length; i++  ) {
        if ( totalNivel[i] > max )
            max = totalNivel[i];
    }

    // criando os conceitos
    for ( i = 0; i < c.length; i++ ) {

        // largura e altura para os conceitos
        var largura = c[i].largura + ( c[i].largura / 2 ) + ( margemConceito * 2 );
        var altura = 25;

        // se não existe espaçamento para um determinado nível, zera ele
        if ( !espEmNivel[c[i].nivel] ) {
            espEmNivel[c[i].nivel] = 0;
        }

        // calcula o x com base na margem e no espacamento já existente
        var x = margemX + espEmNivel[c[i].nivel];

        // armazena o espaçamento em nível que já foi feito
        espEmNivel[c[i].nivel] += largura + espacamentoHorizontal;

        var y = margemY + ( ( altura + espacamentoVertical ) * c[i].nivel );

        // instanciando o conceito e colocando na lista - indexando pelo título
        var con = new Desenhos.Conceito( c[i].titulo, x, y, largura, altura, margemConceito );
        conceitos[con.titulo] = con;

    }

    var relPadrao = Application.i18n.getMsg( 'estrCon.rm.undefined' );

    for ( i = 0; i < r.length; i++ ) {

        /*
         * Cria as relações, usando o título dos conceitos para identificar
         * quais são os conceitos de origem e destino.
         */
        var vRelU = r[i].relacaoUsuario;
        var vRelM = r[i].relacaoMinsky;
        var vRel = relPadrao;

        if ( vRelU != 'undefined' )
            vRel = vRelU;
        else if ( vRelM != 'undefined'  )
            vRel = Application.i18n.getMsg( 'estrCon.rm.' + vRelM );

        var re = new Desenhos.Relacao(
                conceitos[r[i].conceito1],
                conceitos[r[i].conceito2],
                vRel );
        re.paintMe( jg );
    }

    // desenhando os conceitos - está indexado pelo título!
    for ( i = 0; i < c.length; i++ ) {
        conceitos[c[i].titulo].paintMe( jg );
    }

    // desenhando
    jg.paint();

};


/*
 * Essa função extrai todos os dados para poder gerar o gráfico do mapa de
 * conceitos. Raiz é o primeiro nó válido do mapa (não é raiz da árvore).
 */
Desenhos.gerarDadosMapa = function( raiz, store, usarStore ) {

    var dados = '{ conceitos: [';
    var nivel = 0;

    // função para gerar a hierarquia em nível
    var emNivel = function( no ) {

        dados += '{ titulo: "' + no.text + '", ';
        dados += 'nivel: ' + nivel + ', ';
        dados += 'largura: ' + Ext.util.TextMetrics.measure( 'areaDesenhoEstrCon', no.text ).width + ', ';
        dados += '},';

        var filhos = no.childNodes;

        if ( filhos && filhos.length > 0 ) {
            nivel++;
            for ( var i = 0; i < filhos.length; i++ ) {
                emNivel( filhos[i] );
            }
            nivel--;
        }

    };

    // gera os conceitos em nível
    emNivel( raiz );


    dados += '], relacoes: [';

    if ( usarStore ) {
        store.each( function( campo ) {

            var rM = campo.data.relacaoMinsky ? campo.data.relacaoMinsky : '';
            var rU = campo.data.relacaoUsuario ? campo.data.relacaoUsuario : '';

            dados += '{' +
                'conceito1: "' + campo.data.conceito1 + '", ' +
                'conceito2: "' + campo.data.conceito2 + '", ' +
                'relacaoMinsky: "' + rM + '", ' +
                'relacaoUsuario: "' + rU + '" ' +
                '},';
        });
    } else {

        raiz.cascade( function() {

            var i = 0;
            var filhos = [];
            var relsMapa = this.attributes.relacoesMapa;
            var relsHie = this.attributes.relacoesHierarquia;

            // se não tem relações na hierarquia, cria as relações
            if ( !relsHie ) {

                filhos = this.childNodes;

                for ( i = 0; i < filhos.length; i++ ) {

                    dados += '{' +
                        'conceito1: "' + this.text + '", ' +
                        'conceito2: "' + filhos[i].text + '", ' +
                        'relacaoMinsky: "' + filhos[i].attributes.relacaoMinsky + '", ' +
                        'relacaoUsuario: "' + filhos[i].attributes.relacaoUsuario + '" ' +
                        '},';

                }

                // se existem relações na hierarquia
            } else {

                var jaCriou = [];
                var contJaCriou = 0;

                // cria as da hierarquia e as que estão sendo especificadas agora
                for ( i = 0; i < relsHie.length; i++ ) {

                    dados += '{' +
                        'conceito1: "' + this.text + '", ' +
                        'conceito2: "' + relsHie[i].titulo + '", ' +
                        'relacaoMinsky: "' + relsHie[i].relacaoMinsky + '", ' +
                        'relacaoUsuario: "' + relsHie[i].relacaoUsuario + '" ' +
                        '},';

                    jaCriou[contJaCriou++] = this.text + '-' + relsHie[i].titulo;

                }

                filhos = this.childNodes;

                for ( i = 0; i < filhos.length; i++ ) {

                    var gerar = true;

                    for ( var j = 0; j < contJaCriou; j++ ) {
                        if ( ( this.text + '-' + filhos[i].text ) == jaCriou[j] ) {
                            gerar = false;
                            break;
                        }
                    }

                    if ( gerar ) {
                        dados += '{' +
                            'conceito1: "' + this.text + '", ' +
                            'conceito2: "' + filhos[i].text + '", ' +
                            'relacaoMinsky: "' + filhos[i].attributes.relacaoMinsky + '", ' +
                            'relacaoUsuario: "' + filhos[i].attributes.relacaoUsuario + '" ' +
                            '},';
                    }

                }

            }

            // crias as relações do mapa se existirem
            if ( relsMapa ) {
                for ( i = 0; i < relsMapa.length; i++ ) {

                    dados += '{' +
                        'conceito1: "' + this.text + '", ' +
                        'conceito2: "' + relsMapa[i].titulo + '", ' +
                        'relacaoMinsky: "' + relsMapa[i].relacaoMinsky + '", ' +
                        'relacaoUsuario: "' + relsMapa[i].relacaoUsuario + '" ' +
                        '},';

                }
            }

        });

    }

    dados += ']}';

    return dados;

};
