/**
 * Arquivo de scripts da tela de gerenciamento de usuários.
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      23 de Setembro de 2009
 *
 */

Usuarios.initComponents = function() {

    /*
     * Sexos
     */
    var sexos = new Ext.data.SimpleStore({
       fields: [ 'sexo', 'texto' ],
       data: [ [ 'M', Application.i18n.getMsg( 'sexo.masc' ) ],
               [ 'F', Application.i18n.getMsg( 'sexo.fem' ) ] ]
    });

    /*
     * Tipos de usuário
     */
    var tipos = new Ext.data.SimpleStore({
       fields: [ 'tipo', 'texto' ],
       data: [ [ 'T', Application.i18n.getMsg( 'usuarios.tipo.t' ) ],
               [ 'E', Application.i18n.getMsg( 'usuarios.tipo.e' ) ],
               [ 'P', Application.i18n.getMsg( 'usuarios.tipo.p' ) ],
               [ 'A', Application.i18n.getMsg( 'usuarios.tipo.a' ) ] ]
    });

    /*
     * Escolaridade
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
     * Usuários
     */
    var usuarios = new Ext.data.Store({
        reader: new Ext.data.JsonReader({
            fields: [ 'id', 'email', 'nome', 'dataNascimento' ],
            root: 'usuarios'
        }),
        proxy: new Ext.data.HttpProxy({
            url: Application.contextPath + '/ajax/usuarios/usuarios.jsp'
        })
    });

    /*
     * Formulário
     */
    var form = new Ext.FormPanel({
        url: Application.contextPath + '/servlets/UsuarioServlet',
        border: false,
        bodyStyle: 'padding: 4px;',
        labelAlign: 'right',
        layout: 'table',
        layoutConfig: {
            columns: 2
        },
        items: [ {
            xtype: 'panel',
            border: false,
            bodyStyle: 'padding: 2px',
            items: [ {
                xtype: 'fieldset',
                title: Application.i18n.getMsg( 'usuarios.autenticacao' ),
                items: [ {
                    xtype: 'hidden',
                    name: 'id'
                }, {
                    xtype: 'hidden',
                    name: 'acao',
                    id: 'fieldAcaoUsuarios'
                }, {
                    xtype: 'textfield',
                    fieldLabel: Application.i18n.getMsg( 'email' ),
                    name: 'email',
                    allowBlank: false,
                    vtype: 'email',
                    //vtype: 'existeEmail',
                    maxLength: 150,
                    blankText: Application.i18n.getMsg( 'vtype.blank' ),
                    maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                    width: 200
                }, {
                    xtype: 'textfield',
                    id: 'senhaUsr',
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
                    initialPassField: 'senhaUsr',
                    width: 100
                }, {
                    xtype: 'combo',
                    fieldLabel: Application.i18n.getMsg( 'tipo' ),
                    name: 'tipos',
                    hiddenName: 'tipo',
                    allowBlank: false,
                    blankText: Application.i18n.getMsg( 'vtype.blank' ),
                    mode: 'local',
                    triggerAction: 'all',
                    editable: false,
                    store: tipos,
                    valueField: 'tipo',
                    displayField: 'texto',
                    emptyText: Application.i18n.getMsg( 'selecione' ),
                    width: 100
                }]
            }, {
                xtype: 'fieldset',
                title: Application.i18n.getMsg( 'dadosPessoais' ),
                items: [ {
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
                    xtype: 'fieldset',
                    title: Application.i18n.getMsg( 'endereco' ),
                    items: [ {
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
                                            Ext.getCmp('comboEstadosUsr').reset();
                                            Ext.getCmp('comboCidadesUsr').reset();
                                        }
                                    }
                                });
                            }
                        }
                    }, {
                        xtype: 'combo',
                        id: 'comboEstadosUsr',
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
                                            Ext.getCmp('comboCidadesUsr').reset();
                                        }
                                    }
                                });
                            }
                        }
                    }, {
                        xtype: 'combo',
                        id: 'comboCidadesUsr',
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
                }]
            }]
        }, {
            xtype: 'panel',
            border: false,
            bodyStyle: 'padding: 2px',
            items: [ {
                xtype: 'grid',
                id: 'gradeUsuarios',
                frame: true,
                title: Application.i18n.getMsg( 'usuarios.tabela.titulo' ),
                height: 550,
                width: 500,
                stripeRows: true,
                store: usuarios,
                columns: [ {
                    header: Application.i18n.getMsg( 'usuarios.tabela.col.id' ), dataIndex: 'id', width: 40
                }, {
                    header: Application.i18n.getMsg( 'usuarios.tabela.col.email' ), dataIndex: 'email', width: 160, sortable: true
                }, {
                    header: Application.i18n.getMsg( 'usuarios.tabela.col.nome' ), dataIndex: 'nome', width: 160, sortable: true
                }, {
                    header: Application.i18n.getMsg( 'usuarios.tabela.col.dataNasc' ), dataIndex: 'dataNascimento', width: 100, sortable: true,
                    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                        return Ext.util.Format.date( value.replace( /-/g, '/'), 'd/m/Y' );
                    }
                }],
                sm: new Ext.grid.RowSelectionModel({
                    singleSelect: true,
                    listeners: {
                        rowselect: {
                            // série de callbacks para selecionar os combos corretos
                            fn: function( sm, index, record ) {
                                form.getForm().load({
                                    url: Application.contextPath + '/ajax/usuarios/usuarioPorId.jsp',
                                    params: {
                                        id: record.data.id
                                    },
                                    success: function( form, action ) {
                                        estados.load({
                                            params: {
                                                idPais: action.result.data.pais
                                            },
                                            callback: function( records, options, success ) {
                                                if ( success ) {
                                                    Ext.getCmp('comboEstadosUsr').setValue( action.result.data.estado );
                                                    cidades.load({
                                                        params: {
                                                            idEstado: action.result.data.estado
                                                        },
                                                        callback: function( records, options, success ) {
                                                            if ( success ) {
                                                                Ext.getCmp('comboCidadesUsr').setValue( action.result.data.cidade );
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    },
                                    waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                                    waitMsg: Application.i18n.getMsg( 'carregando' )
                                });
                            }
                        }
                    }
                })
            }]
        }]
    });

    /*
     * Janela
     */
    var janela = new Ext.Window( {
        id: 'janelaUsuarios',
        iconCls: 'iconeUsuarios',
        title: Application.i18n.getMsg( 'usuarios.titulo' ),
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

                Ext.getCmp( 'gradeUsuarios' ).getSelectionModel().clearSelections();
                Ext.getCmp( 'fieldAcaoUsuarios' ).setValue( 'salvar' );

                form.getForm().submit({
                    clientValidation: true,
                    success: function( form, action ){
                        form.reset();
                        usuarios.load();
                        Uteis.exibirMensagemInfo( Application.i18n.getMsg( 'usuarios.usuarioSalvo' ) );
                    },
                    failure: function( form, action ){
                        Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                    },
                    waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                    waitMsg: Application.i18n.getMsg( 'usuarios.salvandoUsuario' )
                });
            }
        }, {
            id: 'btnExcluirUsr',
            text: Application.i18n.getMsg( 'excluir' ),
            handler: function( btn, evt ) {

                var registro = Ext.getCmp( 'gradeUsuarios' ).getSelectionModel().getSelected();

                if ( registro ) {
                    Ext.Msg.confirm(
                        Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                        Application.i18n.getMsg( 'usuarios.desejaExcluir' ),
                        function( btnId ) {
                            if ( btnId == 'yes' ) {
                                Ext.Ajax.request({
                                    url: Application.contextPath + '/servlets/UsuarioServlet',
                                    success: function(){
                                        Ext.getCmp( 'gradeUsuarios' ).getSelectionModel().clearSelections();
                                        form.getForm().reset();
                                        usuarios.load();
                                        Uteis.exibirMensagemInfo( Application.i18n.getMsg( 'usuarios.usuarioExcluido' ) );
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
                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'usuarios.selecioneUsuario' ) );
                }

            }
        }, {
            id: 'btnLimparUsr',
            text: Application.i18n.getMsg( 'usuarios.btn.limpar' ),
            handler: function( btn, evt ) {
                form.getForm().reset();
            }
        }]
    });

    /*
     * Abre a janela de usuários. Existe apenas uma instância dessa janela
     * durante a execução de aplicação.
     */
    Usuarios.abrir = function() {
        janela.show();
        janela.center();
        paises.load();
        usuarios.load();
    };

};