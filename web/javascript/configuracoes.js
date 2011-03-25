/**
 * Arquivo de scripts da tela de gerenciamento de configurações
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      28 de Setembro de 2009
 *
 */

Configs.initComponents = function() {

    /*
     * Carga das configurações
     */
    var configs = new Ext.data.Store({
        reader: new Ext.data.JsonReader({
            fields: [ 'id', 'propriedade', 'valor' ],
            root: 'configuracoes'
        }),
        proxy: new Ext.data.HttpProxy({
            url: Application.contextPath + '/ajax/configuracoes/configuracoes.jsp'
        })
    });

    /*
     * Painel de criação/ediçãio de configurações
     */
    var form = new Ext.FormPanel({
        url: Application.contextPath + '/servlets/ConfiguracaoServlet',
        border: false,
        bodyStyle: 'padding: 4px;',
        labelAlign: 'right',
        items: [ {
            xtype: 'grid',
            id: 'gradeConfigs',
            frame: true,
            title: Application.i18n.getMsg( 'configs.tabela.titulo' ),
            height: 200,
            width: 375,
            stripeRows: true,
            store: configs,
            columns: [ {
                header: Application.i18n.getMsg( 'configs.tabela.col.id' ), dataIndex: 'id', width: 40
            }, {
                header: Application.i18n.getMsg( 'configs.tabela.col.propriedade' ), dataIndex: 'propriedade', width: 100
            }, {
                header: Application.i18n.getMsg( 'configs.tabela.col.valor' ), dataIndex: 'valor', width: 180
            }],
            sm: new Ext.grid.RowSelectionModel({
                singleSelect: true,
                listeners: {
                    rowselect: {
                        fn: function( sm, index, record ) {
                            form.getForm().load({
                                url: Application.contextPath + '/ajax/configuracoes/configuracaoPorId.jsp',
                                params: {
                                    id: record.data.id
                                },
                                waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                                waitMsg: Application.i18n.getMsg( 'carregando' )
                            });
                        }
                    }
                }
            })
        }, {
            xtype: 'fieldset',
            title: Application.i18n.getMsg( 'configs.dadosConfig' ),
            items: [ {
                xtype: 'hidden',
                name: 'id'
            }, {
                xtype: 'hidden',
                name: 'acao',
                id: 'fieldAcaoConfiguracoes'
            }, {
                xtype: 'textfield',
                fieldLabel: Application.i18n.getMsg( 'configs.propriedade' ),
                name: 'propriedade',
                allowBlank: false,
                maxLength: 50,
                blankText: Application.i18n.getMsg( 'vtype.blank' ),
                maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                width: 100
            }, {
                xtype: 'textfield',
                fieldLabel: Application.i18n.getMsg( 'configs.valor' ),
                name: 'valor',
                allowBlank: false,
                maxLength: 200,
                blankText: Application.i18n.getMsg( 'vtype.blank' ),
                maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                width: 200
            }]
        }]
    });

    /*
     * Janela para manutenção das configurações
     */
    var janela = new Ext.Window( {
        id: 'janelaConfigs',
        iconCls: 'iconeConfiguracoes',
        title: Application.i18n.getMsg( 'configs.titulo' ),
        modal: true,
        resizable: false,
        closeAction: 'hide',
        items: [
            form
        ],
        fbar: [{
            id: 'btnNovoUsr',
            text: Application.i18n.getMsg( 'novo' ),
            handler: function( btn, evt ) {
                form.getForm().reset();
            }
        }, {
            id: 'btnSalvarUsr',
            text: Application.i18n.getMsg( 'salvar' ),
            handler: function( btn, evt ) {

                Ext.getCmp( 'gradeConfigs' ).getSelectionModel().clearSelections();
                Ext.getCmp( 'fieldAcaoConfiguracoes' ).setValue( 'salvar' );

                form.getForm().submit({
                    success: function( form, action ){
                        form.reset();
                        configs.load();
                        Uteis.exibirMensagemInfo( Application.i18n.getMsg( 'configs.configSalva' ) );
                    },
                    failure: function( form, action ){
                        Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                    },
                    waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                    waitMsg: Application.i18n.getMsg( 'configs.salvandoConfig' )
                });
            }
        }, {
            id: 'btnExcluirUsr',
            text: Application.i18n.getMsg( 'excluir' ),
            handler: function( btn, evt ) {

                var registro = Ext.getCmp( 'gradeConfigs' ).getSelectionModel().getSelected();

                if ( registro ) {
                    Ext.Msg.confirm(
                        Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                        Application.i18n.getMsg( 'configs.desejaExcluir' ),
                        function( btnId ) {
                            if ( btnId == 'yes' ) {
                                Ext.Ajax.request({
                                    url: Application.contextPath + '/servlets/ConfiguracaoServlet',
                                    success: function(){
                                        Ext.getCmp( 'gradeConfigs' ).getSelectionModel().clearSelections();
                                        form.getForm().reset();
                                        configs.load();
                                        Uteis.exibirMensagemInfo( Application.i18n.getMsg( 'configs.configExcluida' ) );
                                    },
                                    params: {
                                        acao: 'excluir',
                                        id: registro.data.id
                                    }
                                });
                            }
                        }
                    );
                } else {
                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'configs.selecioneConfig' ) );
                }

            }
        }, {
            id: 'btnLimparUsr',
            text: Application.i18n.getMsg( 'configs.btn.limpar' ),
            handler: function( btn, evt ) {
                form.getForm().reset();
            }
        }]
    });

    /*
     * Função para abrir a janela de configurações.
     * Existe uma instância da janela para toda a aplicação.
     */
    Configs.abrir = function() {
        janela.show();
        janela.center();
        configs.load();
    }

};