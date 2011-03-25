/**
 * Arquivo de scripts da tela de analogias
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      04 de Setembro de 2009
 *
 */

Analogias.initComponents = function() {

    var editorTexto = null;

    /*
     * Separador.
     */
    var separadorBranco = new Ext.Panel({
        height: 4,
        border: false
    });

    /*
     * Painel dos valores que serão processados.
     */
    var painelValores = new Ext.FormPanel({
        url: Application.contextPath + '/servlets/GerarAnalogiasServlet',
        title: Application.i18n.getMsg( 'analogias.valores.desc' ),
        bodyStyle: 'padding: 4px;',
        iconCls: 'iconeAnalogias',
        height: 400,
        width: 600,
        autoScroll: true,
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.valores.conc' ),
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding: 4px;',
            labelWidth: 150,
            items: [{
                xtype: 'textfield',
                name: 'conceito',
                fieldLabel: Application.i18n.getMsg( 'conceito' ),
                width : 405
            }]
        },
            separadorBranco.cloneConfig(),
        {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.valores.cFun' ),
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding: 4px;',
            labelWidth: 150,
            items: [{
                xtype: 'textfield',
                name: 'usadoPara',
                fieldLabel: Application.i18n.getMsg( 'analogias.usadoPara' ),
                width : 405
            }]
        },
            separadorBranco.cloneConfig(),
        {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.valores.cCom' ),
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding: 4px;',
            labelWidth: 150,
            items: [{
                xtype: 'textfield',
                name: 'capazDe',
                fieldLabel: Application.i18n.getMsg( 'analogias.capazDe' ),
                width : 405
            }]
        },
            separadorBranco.cloneConfig(),
        {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.valores.cTemp' ),
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding: 4px;',
            labelWidth: 150,
            items: [{
                xtype: 'textfield',
                name: 'preReq',
                fieldLabel: Application.i18n.getMsg( 'analogias.preReq' ),
                width : 405
            }, {
                xtype: 'textfield',
                name: 'primeiroPassoPara',
                fieldLabel: Application.i18n.getMsg( 'analogias.primeiroPassoPara' ),
                width : 405
            }, {
                xtype: 'textfield',
                name: 'passoPara',
                fieldLabel: Application.i18n.getMsg( 'analogias.passoPara' ),
                width : 405
            }, {
                xtype: 'textfield',
                name: 'ultimoPassoPara',
                fieldLabel: Application.i18n.getMsg( 'analogias.ultimoPassoPara' ),
                width : 405
            }]
        },
            separadorBranco.cloneConfig(),
        {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.valores.cFis' ),
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding: 4px;',
            labelWidth: 150,
            items: [{
                xtype: 'textfield',
                name: 'tipoDe',
                fieldLabel: Application.i18n.getMsg( 'analogias.tipoDe' ),
                width : 405
            }, {
                xtype: 'textfield',
                name: 'adjetivos',
                fieldLabel: Application.i18n.getMsg( 'analogias.adjetivos' ),
                width : 405
            }, {
                xtype: 'textfield',
                name: 'parteDe',
                fieldLabel: Application.i18n.getMsg( 'analogias.parteDe' ),
                width : 405
            }, {
                xtype: 'textfield',
                name: 'feitoDe',
                fieldLabel: Application.i18n.getMsg( 'analogias.feitoDe' ),
                width : 405
            }, {
                xtype: 'textfield',
                name: 'definidoComo',
                fieldLabel: Application.i18n.getMsg( 'analogias.definidoComo' ),
                width : 405
            }]
        },
            separadorBranco.cloneConfig(),
        {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.valores.cEsp' ),
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding: 4px;',
            labelWidth: 150,
            items: [{
                xtype: 'textfield',
                name: 'encontradoEm',
                fieldLabel: Application.i18n.getMsg( 'analogias.encontradoEm' ),
                width : 405
            }]
        },
            separadorBranco.cloneConfig(),
        {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.valores.cCau' ),
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding: 4px;',
            labelWidth: 150,
            items: [{
                xtype: 'textfield',
                name: 'temConsequencias',
                fieldLabel: Application.i18n.getMsg( 'analogias.temConsequencias' ),
                width : 405
            }]
        },
            separadorBranco.cloneConfig(),
        {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.valores.cAfe' ),
            layout: 'form',
            labelAlign: 'right',
            bodyStyle: 'padding: 4px;',
            labelWidth: 150,
            items: [{
                xtype: 'textfield',
                name: 'motivacaoPara',
                fieldLabel: Application.i18n.getMsg( 'analogias.motivacaoPara' ),
                width : 405
            }, {
                xtype: 'textfield',
                name: 'desejoDe',
                fieldLabel: Application.i18n.getMsg( 'analogias.desejoDe' ),
                width : 405
            }]
        }]
    });


    /*
     * Lista de analogias.
     */
    var dadosAnalogias = new Ext.list.ListView({
        region: 'center',
        height: 298,
        autoScroll: true,
        border: false,
        multiSelect: true,
        hideHeaders: true,
        store: new Ext.data.Store({
            data: [],
            reader: new Ext.data.ArrayReader( {
                idIndex: 0
            },
            Ext.data.Record.create([
                { name: 'conceptTarget' },
                { name: 'conceptBase' },
                { name: 'weight' },
                { name: 'analogyParts' }
            ]))
        }),
        columns: [{
            dataIndex: 'conceptTarget'
        }],
        listeners: {
            click: function( dataView, indice, no, evt ) {

                var record = dadosAnalogias.getRecord( no );

                var cab = String.format( Application.i18n.getMsg( 'analogias.cabecalho' ),
                        '<font class="conceitoBase">' + record.data.conceptBase + '</font>',
                        '<font class="conceitoAlvo">' + record.data.conceptTarget + '</font>' );

                $( '#areaDetalheAnalogiaCab' ).html( cab );

                var ap = record.data.analogyParts;
                var analogias = '';

                for ( var i = 0; i < ap.length; i++ ) {
                    analogias += Uteis.criarItemAnalogia(
                            ap[i],
                            record.data.conceptBase,
                            record.data.conceptTarget,
                            Application.i18n );
                }

                $( '#areaDetalheAnalogiaCont' ).html( analogias );

            }
        }
    });

    /*
     * Painel dos resultados obtidos.
     */
    var painelResultados = new Ext.Panel({
        title: Application.i18n.getMsg( 'analogias.resultados.sugObt' ),
        bodyStyle: 'padding: 4px;',
        iconCls: 'iconeAnalogias',
        height: 400,
        width: 600,
        hidden: true,
        layout: 'table',
        layoutConfig: {
            columns: 3
        },
        items: [{
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.resultados.sugAna' ),
            iconCls: 'iconeSensoComum',
            height: 299,
            width: 180,
            items: [
                dadosAnalogias
            ],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'analogias.copiarAnalogias' ),
                handler: function( btn, evt ) {

                    var dados = dadosAnalogias.getSelectedRecords();
                    var valores = '';

                    if ( dados.length > 0 ) {

                        for ( var i = 0; i < dados.length; i++ ) {

                            var ap = dados[i].data.analogyParts;

                            for ( var j = 0; j < ap.length; j++ ) {
                                valores += Uteis.criarItemAnalogia(
                                        ap[j],
                                        dados[i].data.conceptBase,
                                        dados[i].data.conceptTarget,
                                        Application.i18n );
                            }

                        }

                        editorTexto.insertAtCursor( '<br/>' + valores + '<br/>' );
                        dadosAnalogias.clearSelections();

                    }

                }
            }]
        },
            separadorBranco.cloneConfig( { width: 5 } ),
        {
            xtype: 'panel',
            title: Application.i18n.getMsg( 'analogias.resultados.det' ),
            iconCls: 'iconeDetalheAnalogias',
            autoScroll: true,
            height: 337,
            width: 405,
            html: '<div id="areaDetalheAnalogiaCab" class="divAreaDetalheAnalogiaCab"></div>' +
                '<p><div id="areaDetalheAnalogiaCont" class="divAreaDetalheAnalogiaCont"></div></p>'
        }]
    });

    /*
     * Painel principal.
     */
    var painel = new Ext.Panel({
        border: false,
        bodyStyle: 'padding: 4px;',
        items: [{
            xtype: 'panel',
            border: false,
            items: [
                painelValores,
                painelResultados
            ]
        }]
    });

    /*
     * Botões.
     */
    var btnVoltar = new Ext.Button({
        text: Application.i18n.getMsg( 'voltar' ),
        disabled: true,
        handler: function( btn, evt ) {
            painelValores.show();
            painelResultados.hide();
            btnVoltar.disable();
            btnGerar.enable();
            dadosAnalogias.clearSelections();
            dadosAnalogias.getStore().removeAll();
        }
    });

    var btnGerar = new Ext.Button({
        text: Application.i18n.getMsg( 'analogias.gerarAnalogias' ),
        handler: function( btn, evt ) {
            painelValores.getForm().submit({
                success: function( form, action ) {

                    var r = action.result.results;

                    if ( r.length != 0 ) {

                        for ( var i = 0; i < r.length; i++ ) {

                            var analogia = [ new Ext.data.Record({
                                conceito: r[i].conceptTarget,
                                conceptBase: r[i].conceptBase,
                                conceptTarget: r[i].conceptTarget,
                                weight: r[i].weight,
                                analogyParts: r[i].analogyParts
                            },
                                Ext.id()
                            )];

                            dadosAnalogias.getStore().add( analogia );

                        }

                    } else {
                        limparResultados( Application.i18n.getMsg( 'analogias.resultados.vazio' ) );
                    }

                    painelValores.hide();
                    painelResultados.show();
                    btnVoltar.enable();
                    btnGerar.disable();

                },
                failure: function( form, action ) {
                    Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                },
                waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                waitMsg: Application.i18n.getMsg( 'analogias.gerando' )
            });
        }
    });

    var btnCancelar = new Ext.Button({
        text: Application.i18n.getMsg( 'cancelar' ),
        handler: function( btn, evt ) {
            janela.hide();
        }
    });

    /*
     * Janela.
     */
    var janela = new Ext.Window({
        iconCls: 'iconeAssistente',
        title: Application.i18n.getMsg( 'analogias.titulo' ),
        modal: true,
        resizable: false,
        closeAction: 'hide',
        items: [
            painel
        ],
        fbar: [
            btnVoltar,
            btnGerar,
            btnCancelar
        ]
    });

    /*
     * Limpa a div de resultados.
     */
    var limparResultados = function( conteudo ) {

        $( '#areaDetalheAnalogiaCab' ).html( conteudo );
        $( '#areaDetalheAnalogiaCont' ).html( '' );

    };

    /*
     * Função para resetar o estado do formulário.
     */
    var reset = function() {
        painelValores.show();
        painelResultados.hide();
        btnVoltar.disable();
        btnGerar.enable();
        dadosAnalogias.clearSelections();
        dadosAnalogias.getStore().removeAll();
        painelValores.getForm().reset();
        limparResultados( '' );
    };

    /*
     * Cria e abre a janela de geração de analogias.
     */
    Analogias.abrir = function( editor ) {
        editorTexto = editor;
        reset();
        janela.show();
        janela.center();
    };

};

