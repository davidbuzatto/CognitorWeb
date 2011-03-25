/**
 * Arquivo de scripts da tela de alteração de dados pessoais.
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      7 de Outubro de 2009
 *
 */

DadosPessoais.initComponents = function() {

    /*
     * Sexos
     */
    var sexos = new Ext.data.SimpleStore({
       fields: [ 'sexo', 'texto' ],
       data: [ [ 'M', Application.i18n.getMsg( 'sexo.masc' ) ],
               [ 'F', Application.i18n.getMsg( 'sexo.fem' ) ] ]
    });

    /*
     * Escolaridades
     */
    var escolaridades = new Ext.data.SimpleStore({
       fields: [ 'escolaridade', 'texto' ],
       data: [ [ 'EFC', Application.i18n.getMsg( 'escolaridade.efc' ) ],
               [ 'EFI', Application.i18n.getMsg( 'escolaridade.efi' ) ],
               [ 'EMC', Application.i18n.getMsg( 'escolaridade.emc' ) ],
               [ 'EMI', Application.i18n.getMsg( 'escolaridade.emi' ) ],
               [ 'ESC', Application.i18n.getMsg( 'escolaridade.esc' ) ],
               [ 'ESI', Application.i18n.getMsg( 'escolaridade.esi' ) ],
               [ 'ESP', Application.i18n.getMsg( 'escolaridade.esp' ) ],
               [ 'MSC', Application.i18n.getMsg( 'escolaridade.msc' ) ],
               [ 'PHD', Application.i18n.getMsg( 'escolaridade.phd' ) ] ]
    });

    /*
     * Países
     */
    var paises = new Ext.data.Store({
        reader: new Ext.data.JsonReader({
            fields: [ 'id', 'nome' ],
            root: 'paises'
        }),
        proxy: new Ext.data.HttpProxy({
            url: Application.contextPath + '/ajax/usuarios/paises.jsp'
        })
    });

    /*
     * Estados
     */
    var estados = new Ext.data.Store({
        reader: new Ext.data.JsonReader({
            fields: [ 'id', 'nome' ],
            root: 'estados'
        }),
        proxy: new Ext.data.HttpProxy({
            url: Application.contextPath + '/ajax/usuarios/estadosPorPais.jsp'
        })
    });

    /*
     * Cidades
     */
    var cidades = new Ext.data.Store({
        reader: new Ext.data.JsonReader({
            fields: [ 'id', 'nome' ],
            root: 'cidades'
        }),
        proxy: new Ext.data.HttpProxy({
            url: Application.contextPath + '/ajax/usuarios/cidadesPorEstado.jsp'
        })
    });

    /*
     * Formulário de dados
     */
    var form = new Ext.FormPanel({
        url: Application.contextPath + '/servlets/UsuarioServlet',
        border: false,
        bodyStyle: 'padding: 4px;',
        labelAlign: 'right',
        items: [ {
            xtype: 'hidden',
            name: 'id'
        }, {
            xtype: 'hidden',
            name: 'acao',
            id: 'fieldAcaoDadosPessoais'
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'email' ),
            name: 'email',
            disabled: true,
            width: 200
        }, {
            xtype: 'textfield',
            id: 'senhaDP',
            fieldLabel: Application.i18n.getMsg( 'senha' ),
            inputType: 'password',
            name: 'senha',
            allowBlank: false,
            minLength: 8,
            maxLength: 16,
            blankText: Application.i18n.getMsg( 'vtype.blank' ),
            minLengthText: Application.i18n.getMsg( 'vtype.minimum' ),
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
            width: 100
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'repitaSenha' ),
            inputType: 'password',
            name: 'senhaR',
            vtype: 'password',
            initialPassField: 'senhaDP',
            width: 100
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'primeiroNome' ),
            name: 'primeiroNome',
            allowBlank: false,
            maxLength: 30,
            blankText: Application.i18n.getMsg( 'vtype.blank' ),
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
            width: 120
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'nomeMeio' ),
            name: 'nomeMeio',
            maxLength: 30,
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
            width: 120
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'ultimoNome' ),
            name: 'ultimoNome',
            allowBlank: false,
            maxLength: 50,
            blankText: Application.i18n.getMsg( 'vtype.blank' ),
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
            width: 120
        }, {
            xtype: 'datefield',
            fieldLabel: Application.i18n.getMsg( 'dataNasc' ),
            name: 'dataNascimento',
            format: 'd/m/Y',
            allowBlank: false,
            blankText: Application.i18n.getMsg( 'vtype.blank' ),
            invalidText: Application.i18n.getMsg( 'vtype.date' ),
            width: 100
        }, {
            xtype: 'combo',
            fieldLabel: Application.i18n.getMsg( 'sexo' ),
            name: 'sexos',
            hiddenName: 'sexo',
            allowBlank: false,
            blankText: Application.i18n.getMsg( 'vtype.blank' ),
            mode: 'local',
            triggerAction: 'all',
            editable: false,
            store: sexos,
            valueField: 'sexo',
            displayField: 'texto',
            emptyText: Application.i18n.getMsg( 'selecione' ),
            width: 100
        }, {
            xtype: 'combo',
            fieldLabel: Application.i18n.getMsg( 'escolaridade' ),
            name: 'escolaridades',
            hiddenName: 'escolaridade',
            allowBlank: false,
            blankText: Application.i18n.getMsg( 'vtype.blank' ),
            mode: 'local',
            triggerAction: 'all',
            editable: false,
            store: escolaridades,
            valueField: 'escolaridade',
            displayField: 'texto',
            emptyText: Application.i18n.getMsg( 'selecione' ),
            width: 200
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'ocupacao' ),
            name: 'ocupacao',
            maxLength: 100,
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
            width: 200
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'rua' ),
            name: 'rua',
            maxLength: 100,
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
            width: 200
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'numero' ),
            name: 'numero',
            maxLength: 5,
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
            width: 50
        }, {
            xtype: 'textfield',
            fieldLabel: Application.i18n.getMsg( 'complemento' ),
            name: 'complemento',
            maxLength: 50,
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
            width: 100
        }, {
            xtype: 'combo',
            fieldLabel: Application.i18n.getMsg( 'pais' ),
            name: 'paises',
            hiddenName: 'pais',
            allowBlank: false,
            blankText: Application.i18n.getMsg( 'vtype.blank' ),
            mode: 'remote',
            store: paises,
            valueField: 'id',
            displayField: 'nome',
            triggerAction: 'all',
            loadingText: Application.i18n.getMsg( 'carregando' ),
            autoLoad: true,
            emptyText: Application.i18n.getMsg( 'selecione' ),
            width: 200,
            listeners: {
                select: function(combo, record, numero) {
                    estados.load({
                        params: {
                            idPais: record.data.id
                        },
                        callback: function( records, options, success ) {
                            if ( success ) {
                                Ext.getCmp('comboEstadosDP').reset();
                                Ext.getCmp('comboCidadesDP').reset();
                            }
                        }
                    });
                }
            }
        }, {
            xtype: 'combo',
            id: 'comboEstadosDP',
            fieldLabel: Application.i18n.getMsg( 'estado' ),
            name: 'estados',
            hiddenName: 'estado',
            mode: 'local',
            store: estados,
            valueField: 'id',
            displayField: 'nome',
            triggerAction: 'all',
            loadingText: Application.i18n.getMsg( 'carregando' ),
            emptyText: Application.i18n.getMsg( 'selecione' ),
            width: 200,
            listeners: {
                select: function(combo, record, numero) {
                    cidades.load({
                        params: {
                            idEstado: record.data.id
                        },
                        callback: function( records, options, success ) {
                            if ( success ) {
                                Ext.getCmp('comboCidadesDP').reset();
                            }
                        }
                    });
                }
            }
        }, {
            xtype: 'combo',
            id: 'comboCidadesDP',
            fieldLabel: Application.i18n.getMsg( 'cidade' ),
            name: 'cidades',
            hiddenName: 'cidade',
            mode: 'local',
            store: cidades,
            valueField: 'id',
            displayField: 'nome',
            triggerAction: 'all',
            autoLoad: false,
            emptyText: Application.i18n.getMsg( 'selecione' ),
            width: 200
        }]

    });

    /*
     * Janela dos dados pessoais
     */
    var janela = new Ext.Window( {
        id: 'janelaDadosPessoais',
        iconCls: 'iconeDadosPessoais',
        title: Application.i18n.getMsg( 'usuarios.tituloAltDadosPessoais' ),
        modal: true,
        resizable: false,
        closeAction: 'hide',
        items: [
            form
        ],
        fbar: [{
            id: 'btnSalvarDP',
            text: Application.i18n.getMsg( 'salvar' ),
            handler: function( btn, evt ) {

                Ext.getCmp( 'fieldAcaoDadosPessoais' ).setValue( 'alterarUsuarioLogado' );

                form.getForm().submit({
                    success: function( form, action ){
                        Ext.Msg.show({
                            title: Application.i18n.getMsg( 'dialogo.info.titulo' ),
                            msg: Application.i18n.getMsg( 'usuarios.dadosPessoaisAlterados' ),
                            buttons: Ext.Msg.OK,
                            icon: Ext.Msg.INFO,
                            fn: function( buttonId, text, opt ) {
                                janela.hide();
                            }
                        });
                    },
                    failure: function( form, action ){
                        Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                    },
                    waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                    waitMsg: Application.i18n.getMsg( 'usuarios.salvandoUsuario' )
                });
            }
        }, {
            id: 'btnCancelarDP',
            text: Application.i18n.getMsg( 'cancelar' ),
            handler: function( btn, evt ) {
                janela.hide();
            }
        }]
    });

    /*
     * Faz a carga do usuário logado, arrumando os combos de países,
     * estados e cidades.
     */
    var load = function() {
        form.getForm().load({
            url: Application.contextPath + '/ajax/usuarios/usuarioLogado.jsp',
            success: function( form, action ) {
                estados.load({
                    params: {
                        idPais: action.result.data.pais
                    },
                    callback: function( records, options, success ) {
                        if ( success ) {
                            Ext.getCmp('comboEstadosDP').setValue( action.result.data.estado );
                            cidades.load({
                                params: {
                                    idEstado: action.result.data.estado
                                },
                                callback: function( records, options, success ) {
                                    if ( success ) {
                                        Ext.getCmp('comboCidadesDP').setValue( action.result.data.cidade );
                                    }
                                }
                            });
                        }
                    }
                });
            },
            failure: function( form, action ) {
                Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.naoLogado' ) );
            }
        });
    };

    /*
     * Função para abrir a janela de dados pessoais.
     * Existe uma instância da janela para toda a aplicação.
     */
    DadosPessoais.abrir = function() {
        janela.show();
        janela.center();
        paises.load();
        load();
    };
    
};