/**
 * Arquivo de scripts da tela inicial, onde o usuário pode criar um
 * novo material ou abrir um material já existente.
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      14 de Outubro de 2009
 *
 */

TelaInicial.initComponents = function() {

    /*
     * Guarda o id do material que está selecionado
     */
    var idMaterialSelecionado = undefined;

    /*
     * Cabeçalho da tela
     */
    var cabecalho = new Ext.Panel({
        height: 104,
        width: 700,
        region: 'north',
        border: false,
        bodyCssClass: 'cabecalhoTelaInicial'
    });

    /*
     * Separador para os painéis da janela
     */
    var separadorJanela = new Ext.Panel({
        height: 15,
        border: false,
        bodyCssClass: 'corFundoJanela'
    });

    /*
     * Separador para organizar os painéis que mostrar os ícones do layout
     */
    var separadorOrganizacao = new Ext.Panel({
        height: 15,
        width: 15,
        border: false
    });

    /*
     * Formulário
     */
    var form = new Ext.FormPanel({
        url: Application.contextPath + '/servlets/MaterialServlet',
        border: false,
        hideBorders: true,
        items: [
            cabecalho,
        {
            xtype: 'hidden',
            name: 'acao',
            value: 'novo'
        }, {
            xtype: 'panel',
            region: 'center',
            bodyStyle: 'padding: 15px;',
            bodyCssClass: 'corFundoJanela',
            items: [{
                xtype: 'panel',
                title: Application.i18n.getMsg( 'oQueDeseja.titulo' ),
                bodyStyle: 'padding: 4px;',
                items: [{
                    xtype: 'radio',
                    name: 'radioMaterial',
                    checked: true,
                    boxLabel: Application.i18n.getMsg( 'oQueDeseja.criar' ),
                    listeners: {
                        check: function( check, checked ) {
                            if ( checked ) {
                                Ext.getCmp( 'panelCriarNovoMaterialTelaInicial' ).setVisible( true );
                                Ext.getCmp( 'panelAbrirMaterialTelaInicial' ).setVisible( false );
                                janela.setActive( true );
                            }
                        }
                    }
                }, {
                    xtype: 'radio',
                    name: 'radioMaterial',
                    boxLabel: Application.i18n.getMsg( 'oQueDeseja.abrir' ),
                    listeners: {
                        check: function( check, checked ) {
                            if ( checked ) {
                                Ext.getCmp( 'panelCriarNovoMaterialTelaInicial' ).setVisible( false );
                                Ext.getCmp( 'panelAbrirMaterialTelaInicial' ).setVisible( true );
                                janela.doLayout();
                                janela.setActive( true );
                            }
                        }
                    }
                }]
            }, 
                separadorJanela.cloneConfig(),
            {
                xtype: 'panel',
                id: 'panelCriarNovoMaterialTelaInicial',
                iconCls: 'iconeNovo',
                title: Application.i18n.getMsg( 'telaInicial.criar.titulo' ),
                bodyStyle: 'padding: 4px;',
                labelAlign: 'right',
                labelWidth: 180,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    name: 'titulo',
                    fieldLabel: Application.i18n.getMsg( 'titulo' ),
                    allowBlank: false,
                    maxLength: 100,
                    blankText: Application.i18n.getMsg( 'vtype.blank' ),
                    maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                    width: 200
                }, {
                    xtype: 'combo',
                    fieldLabel: Application.i18n.getMsg( 'compartilhado' ),
                    name: 'compartilhados',
                    hiddenName: 'compartilhado',
                    mode: 'local',
                    store: new Ext.data.SimpleStore({
                       fields: [ 'opcao', 'texto' ],
                       data: [ [ true, Application.i18n.getMsg( 'sim' ) ],
                               [ false, Application.i18n.getMsg( 'nao' ) ] ]
                    }),
                    valueField: 'opcao',
                    displayField: 'texto',
                    triggerAction: 'all',
                    editable: false,
                    width: 100,
                    emptyText: Application.i18n.getMsg( 'selecione' ),
                    allowBlank: false,
                    blankText: Application.i18n.getMsg( 'vtype.blank' )
                }, {
                    xtype: 'combo',
                    id: 'comboEstrConTelaIni',
                    fieldLabel: Application.i18n.getMsg( 'estrCon' ),
                    name: 'estrsCon',
                    hiddenName: 'estrCon',
                    mode: 'local',
                    store: new Ext.data.SimpleStore({
                       fields: [ 'opcao', 'texto' ],
                       data: [ [ true, Application.i18n.getMsg( 'sim' ) ],
                               [ false, Application.i18n.getMsg( 'nao' ) ] ]
                    }),
                    valueField: 'opcao',
                    displayField: 'texto',
                    triggerAction: 'all',
                    editable: false,
                    width: 100,
                    emptyText: Application.i18n.getMsg( 'selecione' ),
                    allowBlank: false,
                    blankText: Application.i18n.getMsg( 'vtype.blank' )
                }, {
                    xtype: 'fieldset',
                    colspan: 3,
                    title: Application.i18n.getMsg( 'organizacaoMaterial' ),
                    layout: 'table',
                    layoutConfig: {
                        columns: 3
                    },
                    items: [{
                        xtype: 'checkbox',
                        checked: true,
                        name: 'organizacaoPagina',
                        boxLabel: Application.i18n.getMsg( 'linksEntrePaginas' )
                    }, 
                        separadorOrganizacao.cloneConfig(),
                    {
                        xtype: 'checkbox',
                        checked: true,
                        name: 'organizacaoBarra',
                        boxLabel: Application.i18n.getMsg( 'barraNavegacao' )
                    }, {
                        xtype: 'panel',
                        width: 104,
                        height: 104,
                        border: false,
                        bodyCssClass: 'layoutComLink'
                    }, 
                        separadorOrganizacao.cloneConfig(),
                    {
                        xtype: 'panel',
                        width: 104,
                        height: 104,
                        border: false,
                        bodyCssClass: 'layoutComBarra'
                    }]
                }]
            }, {
                xtype: 'panel',
                id: 'panelAbrirMaterialTelaInicial',
                iconCls: 'iconeAbrir',
                title: Application.i18n.getMsg( 'telaInicial.abrir.titulo' ),
                hidden: true,
                bodyStyle: 'padding: 4px;',
                layout: 'form',
                items: [{
                    xtype: 'fieldset',
                    title: Application.i18n.getMsg( 'materiaisDisponiveis' ),
                    html: Uteis.gerarListaMateriais( 6 )
                }],
                listeners: {
                    // após executar o layout, monta a tabela de materiais
                    afterlayout: function( cmp ) {

                        $( '.materialDisponivel' ).click( function( evt ){
                            $( '.materialDisponivel' ).css( 'background', '#FFFFFF' );
                            $(this).css( Global.fundoBtnClick );
                            idMaterialSelecionado = evt.target.id;
                        });

                        $( '.materialDisponivel' ).hover(
                            function( evt ){
                                if ( idMaterialSelecionado != evt.target.id )
                                    $(this).css( Global.fundoBtnOver );
                            },
                            function( evt ){
                                if ( idMaterialSelecionado != evt.target.id )
                                    $(this).css( Global.fundoBtnOut );
                            }
                        );

                    }
                }
            }]
        }]
    });

    /*
     * Janela da tela inicial
     */
    var janela = new Ext.Window( {
        id: 'janelaTelaInicial',
        modal: true,
        hideBorders: true,
        draggable: false,
        resizable: false,
        title: Application.i18n.getMsg( 'telaInicial.titulo' ),
        items: [
            form
        ],
        fbar: [{
            xtype: 'button',
            text: Application.i18n.getMsg( 'ok' ),
            handler: function( btn, evt ) {

                if ( Ext.getCmp( 'panelCriarNovoMaterialTelaInicial' ).isVisible() ) {
                    
                    form.getForm().submit({
                        success: function( form, action ) {

                            // seta o id do material sendo editado
                            Application.idMaterialEdicao = action.result.idMaterialGerado;

                            // faz a carga do mapa de conceitos
                            Application.carregarEstrutura();

                            // fecha essa janela
                            janela.close();

                        },
                        failure: function( form, action ) {
                            Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                        },
                        waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                        waitMsg: Application.i18n.getMsg( 'criandoMaterial' )
                    });

                } else {

                    if ( idMaterialSelecionado ) {

                        // extrai o id da string
                        var id = idMaterialSelecionado.substring(
                            idMaterialSelecionado.indexOf( '-' ) + 1,
                            idMaterialSelecionado.length
                        );

                        // seta o id do material sendo editado
                        Application.idMaterialEdicao = Number( id );

                        // faz a carga do mapa de conceitos
                        Application.carregarEstrutura();

                        // fecha essa janela
                        janela.close();

                    } else {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneMaterialAbrir' ) );
                    }

                }

            }
        }, {
            xtype: 'button',
            text: Application.i18n.getMsg( 'cancelar' ),
            handler: function( btn, evt ) {
                janela.close();
            }
        }]
    });

    /*
     * Abre a tela inicial. Após ser fechada, é destruída.
     */
    TelaInicial.abrir = function() {
        janela.show();
        janela.center();

        // seta o valor como true (existe true e false dentro do store)
        Ext.getCmp( 'comboEstrConTelaIni' ).setValue( true );
    };

};