/**
 * Arquivo de scripts da tela de estruturação do conhecimento
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      04 de Setembro de 2009
 *
 */

EstrCon.initComponents = function() {

    var estruturaMaterial = null;
    
    // indica se é uma nova estrutura que vai ser montada
    var novaEstrutura = false;

    /*
     * Buffers para guardar os valores das relacoes para apagar.
     * bufferRelacoesHApagar são relações de hierarquia
     * bufferRelacoesApagar são relações normais
     */
    var bufferRelacoesHApagar = new Ext.util.MixedCollection();
    var bufferRelacoesApagar = new Ext.util.MixedCollection();

    /*
     * Relações de Minsky
     */
    var relations = new Ext.data.SimpleStore({
       fields: [ 'relation', 'texto' ],
       data: [ [ 'IsA', Application.i18n.getMsg( 'sc.relation.ia' ) ],
               [ 'PropertyOf', Application.i18n.getMsg( 'sc.relation.po' ) ],
               [ 'PartOf', Application.i18n.getMsg( 'sc.relation.pto' ) ],
               [ 'MadeOf', Application.i18n.getMsg( 'sc.relation.mo' ) ],
               [ 'DefinedAs', Application.i18n.getMsg( 'sc.relation.da' ) ],
               [ 'EffectOf', Application.i18n.getMsg( 'sc.relation.eo' ) ],
               [ 'DesirousEffectOf', Application.i18n.getMsg( 'sc.relation.doe' ) ],
               [ 'CapableOf', Application.i18n.getMsg( 'sc.relation.co' ) ],
               [ 'UsedFor', Application.i18n.getMsg( 'sc.relation.uf' ) ],
               [ 'CapableOfReceivingAction', Application.i18n.getMsg( 'sc.relation.cor' ) ],
               [ 'ConceptuallyRelatedTo', Application.i18n.getMsg( 'sc.relation.crt' ) ],
               [ 'PrerequisiteEventOf', Application.i18n.getMsg( 'sc.relation.pof' ) ],
               [ 'FirstSubeventOf', Application.i18n.getMsg( 'sc.relation.fso' ) ],
               [ 'SubeventOf', Application.i18n.getMsg( 'sc.relation.so' ) ],
               [ 'LastSubeventOf', Application.i18n.getMsg( 'sc.relation.lso' ) ],
               [ 'LocationOf', Application.i18n.getMsg( 'sc.relation.lo' ) ],
               [ 'MotivationOf', Application.i18n.getMsg( 'sc.relation.mto' ) ],
               [ 'DesireOf', Application.i18n.getMsg( 'sc.relation.do' ) ],
               [ 'All', Application.i18n.getMsg( 'sc.relation.todos' ) ] ]
    });

    /*
     * Separador.
     */
    var separadorBranco = new Ext.Panel({
        width: 4,
        border: false
    });

    /*
     * Flags de controle para alteração de estrutura da árvore.
     */
    var movendoNo = false;
    var inserindoNo = false;

    /*
     * Engine para desenho.
     */
    var jg = undefined;


    /*
     * Árvore para o mapa de conceitos.
     */
    var mapaConceitos = new Ext.tree.TreePanel({
        region: 'center',
        autoScroll: true,
        enableDD: true,
        containerScroll: true,
        useArrow: false,
        border: false,
        animate: false,
        height: 335,
        rootVisible: false,
        root: new Ext.tree.TreeNode({
            leaf: false
        }),
        listeners: {
            click: function( no, evt ) {
                if ( Global.DEBUG ) {
                    Uteis.alertNo( no );
                }

                var dados = null;

                if ( no.attributes.idInterno != 0 ) {
                    dados = bufferRelacoesHApagar.get( no.attributes.idInterno );
                }

                if ( dados ) {
                    alert( dados.id + "\n" + dados.idPai );
                }


            },
            beforemovenode: function( tree, node, oldParent, newParent, index ) {

                if ( !inserindoNo ) {
                    
                    // indica que o nó está sendo movido
                    movendoNo = true;

                    // só move se não estiver no nível do filho da raiz
                    if ( newParent != mapaConceitos.getRootNode() ) {

                        // remove a relação de minsky caso seja movido para um
                        // pai diferente
                        if ( oldParent != newParent ) {
                            node.attributes.relacaoMinsky = undefined;
                        }

                        Uteis.reordenarItemsMapaConceitos( tree, node, oldParent, newParent, index, false );

                    } else {
                        return false;
                    }

                }

                return true;

            },
            beforeremove: function( tree, parent, node ) {
                
                // se o nó não estiver sendo movido
                if ( !movendoNo ) {
                    Uteis.reordenarItemsMapaConceitos( tree, node, node.parentNode,
                            node.parentNode, node.attributes.ordem, true );
                }

                // não está movendo o nó
                //movendoNo = false;

            },
            movenode: function( tree, node, oldParent, newParent, index ) {

                if ( !inserindoNo ) {
                    desenharGrafico();
                }

                // verifica se um nó já movido está voltando para o pai original
                if ( node.attributes.movido ) {

                    // obtém os dados
                    var dados = bufferRelacoesHApagar.get( node.attributes.idInterno );

                    // se os dados existem (vão existir sempre)
                    if ( dados ) {

                        // verifica se o id do pai é o id do novo pai
                        if ( dados.idPai == newParent.attributes.idInterno ) {

                            // caso entre, remove o valor do buffer e indica que não foi movido
                            bufferRelacoesHApagar.removeKey( dados.id );
                            node.attributes.movido = false;

                        }

                    }

                    // se o nó ainda não foi movido e se não for um novo nó
                    // e se o novo pai e o pai anterior forem diferentes
                } else if ( !node.attributes.movido && !node.attributes.novo &&
                        oldParent != newParent ) {

                    node.attributes.movido = true;

                    bufferRelacoesHApagar.add( node.attributes.idInterno, {
                        id: node.attributes.idInterno,
                        idPai: oldParent.attributes.idInterno
                    });

                }

            },
            remove: function( tree, parent, node ) {

                if ( !movendoNo ) 
                    desenharGrafico();

                // não está movendo o nó
                movendoNo = false;

                // limpa seleção
                tree.getSelectionModel().clearSelections();

            },
            append: function( tree, parent, node, index ) {

                if ( inserindoNo ) {

                    // atribui o índice ao nó
                    node.attributes.ordem = index;

                    desenharGrafico();

                    inserindoNo = false;

                }

            },
            contextmenu: function( node ) {
                node.select();
                exibirMenuMapaConceitos( node );
            }
        }
    });


    /*
     * Ordenador para o mapa de conceitos
     */
    var sorterMapaConceitos = new Ext.tree.TreeSorter(
        mapaConceitos, {
        dir: 'asc',
        sortType: function ( node ) {
            return node.attributes.ordem;
        }
    });


    /*
     * Exibição do menu de contexto do mapa de conceitos.
     */
    var exibirMenuMapaConceitos = function( no ) {

        var btnBuscar = {
            text: Application.i18n.getMsg( 'buscar' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/magnifier.png',
            handler: function( btn, evt ) {
                criarJanelaBuscaSensoComum( no.text, dadosSensoComum );
            }
        };

        var btnExcluir = {
            text: Application.i18n.getMsg( 'excluir' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/cross.png',
            handler: function( btn, evt ) {
                excluirConceito( no );
            }
        };

        new Ext.menu.Menu({
            items: [
                btnBuscar,
                '-',
                btnExcluir
            ]
        }).show( no.ui.getAnchor() );

    };


    /*
     * Lista para o senso comum.
     */
    var dadosSensoComum = new Ext.list.ListView({
        region: 'center',
        height: 180,
        autoScroll: true,
        border: false,
        singleSelect: true,
        hideHeaders: true,
        store: new Ext.data.Store({
            data: [],
            reader: new Ext.data.ArrayReader( {
                idIndex: 0
            },
            Ext.data.Record.create([
                { name: 'conceito' },
                { name: 'relacaoMinsky' }
            ]))
        }),
        columns: [{
            dataIndex: 'conceito'
        }],
        listeners: {
            click: function( dataView, index, no, evt ) {
                var dado = dadosSensoComum.getRecord( no );
                var valor = Application.i18n.getMsg( 'estrCon.rm.' + dado.data.relacaoMinsky );
                Ext.getCmp( 'fieldRelSugSensoComumEstrCon' ).setValue( valor );
            }
        }
    });

    /*
     * Painéis
     */
    var painelPasso1 = new Ext.FormPanel({
        url: Application.contextPath + '/servlets/CommonSenseServlet',
        title: Application.i18n.getMsg( 'estrCon.passo1.titulo' ),
        bodyStyle: 'padding: 4px;',
        iconCls: 'iconeConceito',
        height: 434,
        width: 546,
        layout: 'table',
        layoutConfig: {
            columns: 3
        },
        items: [{
            xtype: 'panel',
            layout: 'table',
            border: false,
            layoutConfig: {
                columns: 1
            },
            items: [{
                xtype: 'panel',
                layout: 'table',
                bodyStyle: 'padding: 4px;',
                border: false,
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'panel',
                    layout: 'form',
                    labelWidth: 50,
                    labelAlign: 'right',
                    border: false,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: Application.i18n.getMsg( 'conceito' ),
                        id: 'fieldConceitoEstCon',
                        name: 'concept',
                        allowBlank: false,
                        blankText: Application.i18n.getMsg( 'vtype.blank' ),
                        width: 150
                    }, {
                        xtype: 'combo',
                        id: 'comboRelSugSenComumEstrCon',
                        fieldLabel: Application.i18n.getMsg( 'relacao' ),
                        name: 'relations',
                        hiddenName: 'relation',
                        allowBlank: false,
                        editable: false,
                        blankText: Application.i18n.getMsg( 'vtype.blank' ),
                        mode: 'local',
                        store: relations,
                        valueField: 'relation',
                        displayField: 'texto',
                        triggerAction: 'all',
                        emptyText: Application.i18n.getMsg( 'selecione' ),
                        width: 150
                    }/*, {
                        xtype: 'panel',
                        layout: 'hbox',
                        border: false,
                        hideLabel: true,
                        items: [
                            separadorBranco.cloneConfig( { width: 100 } ),
                        {
                            xtype: 'button',
                            text: Application.i18n.getMsg( 'buscar' ),
                            handler: function( btn, evt ) {
                                painelPasso1.getForm().submit({
                                    success: function( form, action ) {

                                        Ext.getCmp( 'painelSugSensoComumEstrCon' ).expand();
                                        Ext.getCmp( 'fieldConcSugSensoComumEstrCon' ).setValue( Ext.getCmp( 'fieldConceitoEstCon' ).getValue() );
                                        Ext.getCmp( 'fieldRelSugSensoComumEstrCon' ).setValue(
                                                Ext.getCmp( 'comboRelSugSenComumEstrCon' ).getRawValue() );
                                        Uteis.preencheDadosSensoComum( action.result.results, dadosSensoComum );

                                    },
                                    failure: function( form, action ) {
                                        Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                                    },
                                    waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                                    waitMsg: Application.i18n.getMsg( 'sc.obtendoDados' )
                                });
                            }
                        }]
                    }*/]
                }, 
                    separadorBranco.cloneConfig(),
                {
                    xtype: 'button',
                    text: Application.i18n.getMsg( 'adicionar' ),
                    handler: function( btn, evt ) {

                        var textoNovoConceito = Ext.getCmp( 'fieldConceitoEstCon' ).getValue().trim();

                        if ( textoNovoConceito != '' ) {

                            var novoConceito = new Ext.tree.TreeNode({
                                idInterno: 0,
                                novo: true,
                                ordem: 0,
                                compartilhado: false,
                                tipo: 'conceito',
                                text: Ext.getCmp( 'fieldConceitoEstCon' ).getValue(),
                                icon: Application.contextPath + '/imagens/icones/lightbulb.png',
                                leaf: false
                            });

                            if ( !Uteis.existeNoIgual( novoConceito, mapaConceitos.getRootNode() ) ) {

                                inserindoNo = true;

                                // o nó raiz nunca vai ser selecionado (está invisível)
                                var noSelecionado = mapaConceitos.getSelectionModel().getSelectedNode();

                                if ( noSelecionado ) {

                                    noSelecionado.appendChild( novoConceito );
                                    noSelecionado.expand();

                                } else {

                                    if ( mapaConceitos.getRootNode().childNodes.length == 0 ) {
                                        mapaConceitos.getRootNode().appendChild( novoConceito );
                                    } else {
                                        mapaConceitos.getRootNode().childNodes[0].appendChild( novoConceito );
                                        mapaConceitos.getRootNode().childNodes[0].expand();
                                    }

                                }

                                painelPasso1.getForm().submit({
                                    success: function( form, action ) {

                                        dadosSensoComum.clearSelections();
                                        dadosSensoComum.getStore().removeAll();

                                        var conceito = Ext.getCmp( 'fieldConceitoEstCon' ).getValue();
                                        var relacao = Ext.getCmp( 'comboRelSugSenComumEstrCon' ).getRawValue();

                                        painelPasso1.getForm().reset();

                                        Ext.getCmp( 'painelSugSensoComumEstrCon' ).expand();
                                        Ext.getCmp( 'fieldConcSugSensoComumEstrCon' ).setValue( conceito );
                                        Ext.getCmp( 'fieldRelSugSensoComumEstrCon' ).setValue( relacao );
                                        Uteis.preencheDadosSensoComum( action.result.results, dadosSensoComum );

                                        Ext.getCmp( 'comboRelSugSenComumEstrCon' ).setValue( 'All' );

                                    },
                                    failure: function( form, action ) {
                                        Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                                    },
                                    waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                                    waitMsg: Application.i18n.getMsg( 'sc.obtendoDados' )
                                });

                            } else {
                                Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.concMesmoNome' ) );
                                painelPasso1.getForm().findField( 'concept' ).focus( true );
                            }
                            
                        }
                    }
                }]
            }, {
                xtype: 'panel',
                border: false,
                layout: 'table',
                layoutConfig: {
                    columns: 2
                },
                items: [
                    separadorBranco.cloneConfig({ width: 15 }),
                {
                    xtype: 'panel',
                    height: 340,
                    width: 250,
                    border: false,
                    items: [{
                        xtype: 'panel',
                        id: 'painelSugSensoComumEstrCon',
                        title: Application.i18n.getMsg( 'estrCon.sugSensoComum' ),
                        iconCls: 'iconeSensoComum',
                        height: 340,
                        width: 250,
                        bodyStyle: 'padding: 4px;',
                        collapsible: true,
                        collapsed: true,
                        items: [{
                            xtype: 'panel',
                            layout: 'form',
                            bodyStyle: 'padding: 4px;',
                            labelAlign: 'right',
                            labelWidth: 50,
                            border: false,
                            items: [{
                                xtype: 'textfield',
                                id: 'fieldConcSugSensoComumEstrCon',
                                fieldLabel: Application.i18n.getMsg( 'conceito' ),
                                disabled: true,
                                width: 170
                            }, {
                                xtype: 'textfield',
                                id: 'fieldRelSugSensoComumEstrCon',
                                fieldLabel: Application.i18n.getMsg( 'relacao' ),
                                disabled: true,
                                width: 170
                            }]
                        }, {
                            xtype: 'panel',
                            title: Application.i18n.getMsg( 'sc.resultados' ),
                            items: [
                                dadosSensoComum
                            ]
                        }],
                        fbar: [{
                            xtype: 'button',
                            text: Application.i18n.getMsg( 'estrCon.copiarParaMapa' ),
                            handler: function( btn, evt ) {

                                var dados = dadosSensoComum.getSelectedRecords();

                                if ( dados.length > 0 ) {
                                    copiarParaMapaConceitos( dados[0] );
                                } else {
                                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneConcCopiar' ) );
                                }
                                
                            }
                        }]
                    }]
                }]
            }]
        }, 
            separadorBranco.cloneConfig({width: 15}),
        {
            xtype: 'panel',
            border: false,
            items: [{
                xtype: 'panel',
                title: Application.i18n.getMsg( 'estrCon.mapaCon' ),
                iconCls: 'iconeMapaConceitos',
                height: 401,
                width: 250,
                items: mapaConceitos,
                fbar: [{
                    text: Application.i18n.getMsg( 'exibirGrafico' ),
                    id: 'btnExibirGraficoEstrConP1',
                    handler: function( btn, evt ) {
                        btn.disable();
                        Ext.getCmp( 'btnExibirGraficoEstrConP2' ).disable();
                        Ext.getCmp( 'separadorPainelGraficoEstCon' ).show();
                        painelGrafico.expand( false );
                        painelGrafico.show();
                        janela.doLayout();
                        janela.setActive( true );
                        janela.center();
                        desenharGrafico();
                    }
                }, {
                    xtype: 'button',
                    text: Application.i18n.getMsg( 'buscar' ),
                    handler: function( btn, evt ) {

                        var no = mapaConceitos.getSelectionModel().getSelectedNode();

                        if ( no ) {
                            criarJanelaBuscaSensoComum( no.text, dadosSensoComum );
                        } else {
                            Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneConcBuscar' ) );
                        }

                        mapaConceitos.getSelectionModel().clearSelections();

                    }
                }, {
                    xtype: 'button',
                    text: Application.i18n.getMsg( 'excluir' ),
                    handler: function( btn, evt ) {

                        var no = mapaConceitos.getSelectionModel().getSelectedNode();

                        if ( no ) {
                            excluirConceito( no );
                        } else {
                            Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneConcExcluir' ) );
                        }

                    }
                }]
            }]
        }]
    });


    /*
     * Função para copiar para o mapa de conceitos.
     */
    var copiarParaMapaConceitos = function( dado ) {

        if ( dado.data.relacaoMinsky ) {

            var novoConceito = new Ext.tree.TreeNode({
                idInterno: 0,
                novo: true,
                ordem: 0,
                compartilhado: false,
                tipo: 'conceito',
                text: dado.data.conceito,
                relacaoMinsky: dado.data.relacaoMinsky,
                icon: Application.contextPath + '/imagens/icones/lightbulb.png',
                leaf: false
            });

            if ( !Uteis.existeNoIgual( novoConceito, mapaConceitos.getRootNode() ) ) {

                inserindoNo = true;

                // o nó raiz nunca vai ser selecionado (está invisível)
                var noSelecionado = mapaConceitos.getSelectionModel().getSelectedNode();

                var vConsulta = '';
                var vNoDestino = '';

                if ( noSelecionado ) {

                    // verifica se o nó que vai ser copiado tem relação com o nó que vai ser o pai,
                    // verificando para isso o valor do conceito da consulta de senso comum
                    // e o título do nó que vai ser inserido. Caso não tenham relação, o valor de relação de minsky é resetado
                    // não considera a caixa dos textos
                    vConsulta = String.toLowerCase( Ext.getCmp( 'fieldConcSugSensoComumEstrCon' ).getValue() );
                    vNoDestino = String.toLowerCase( noSelecionado.text );

                    if ( vConsulta != vNoDestino ) {
                        novoConceito.attributes.relacaoMinsky = undefined;
                    }

                    noSelecionado.appendChild( novoConceito );
                    noSelecionado.expand();

                } else {

                    if ( mapaConceitos.getRootNode().childNodes.length == 0 ) {

                        // é a "raiz", não tem relação com o pai
                        novoConceito.attributes.relacaoMinsky = undefined;
                        mapaConceitos.getRootNode().appendChild( novoConceito );

                    } else {

                        // idem acima
                        vConsulta = String.toLowerCase( Ext.getCmp( 'fieldConcSugSensoComumEstrCon' ).getValue() );
                        vNoDestino = String.toLowerCase( mapaConceitos.getRootNode().childNodes[0].text );

                        if ( vConsulta != vNoDestino ) {
                            novoConceito.attributes.relacaoMinsky = undefined;
                        }

                        mapaConceitos.getRootNode().childNodes[0].appendChild( novoConceito );
                        mapaConceitos.getRootNode().childNodes[0].expand();
                    }

                }

            } else {
                Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.concMesmoNome' ) );
                painelPasso1.getForm().findField( 'concept' ).focus( true );
            }

        } else {
            Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.concInvalido' ) );
        }

        dadosSensoComum.clearSelections();

    };


    /*
     * Função para exclusão de conceitos.
     */
    var excluirConceito = function( no ) {
        Ext.Msg.confirm(
            Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
            Application.i18n.getMsg( 'dialogo.desejaExcluirConceito' ),
            function( btnId ) {
                if ( btnId == 'yes' ) {

                    var espera = Ext.Msg.wait(
                            Application.i18n.getMsg( 'excluindo' ),
                            Application.i18n.getMsg( 'favorAguarde' ) ).getDialog();

                    // se não é um nó novo, remove do banco
                    if ( !no.attributes.novo ) {
                        $.ajax({
                            dataType: 'json',
                            type: 'post',
                            url: Application.contextPath + '/servlets/MapaConceitosServlet',
                            data: {
                                idConceito: no.attributes.idInterno,
                                idMaterial: Application.idMaterialEdicao,
                                acao: 'excluirConceito'
                            },
                            success: function( data, textStatus ) {
                                if ( data.success ) {
                                    no.remove();
                                }
                            },
                            complete: function( data, textStatus ) {
                                espera.close();
                            }
                        });
                    } else {
                        no.remove();
                        espera.close();
                    }
                }
            }
        );
    };


    /*
     * Grade editável.
     */
    var gridRelacoes = new Ext.grid.EditorGridPanel({
        xtype: 'grid',
        frame: true,
        title: Application.i18n.getMsg( 'estrCon.relacionamentos' ),
        stripeRows: true,
        height: 330,
        store: new Ext.data.Store({
            data: [],
            reader: new Ext.data.ArrayReader( {
                idIndex: 0
            }, 
            Ext.data.Record.create([
                { name: 'idInterno1' },
                { name: 'conceito1' },
                { name: 'relacaoMinsky' },
                { name: 'relacaoUsuario' },
                { name: 'idInterno2' },
                { name: 'conceito2' },
                { name: 'relHierarquia' } // indica se é um relacionamento de hierarquia.
            ]))
        }),
        columns: [ {
            header: Application.i18n.getMsg( 'estrCon.tabela.col.conceito' ),
            dataIndex: 'conceito1',
            sortable: false,
            menuDisabled: true,
            width: 168
        }, {
            header: Application.i18n.getMsg( 'estrCon.tabela.col.temRelacao' ),
            dataIndex: 'relacaoUsuario',
            sortable: false,
            menuDisabled: true,
            editor: new Ext.form.TextField({
                maxLength: 120,
                maxLengthText: Application.i18n.getMsg( 'vtype.maximum' )
            }),
            width: 167
        }, {
            header: Application.i18n.getMsg( 'estrCon.tabela.col.comConceito' ),
            dataIndex: 'conceito2',
            sortable: false,
            menuDisabled: true,
            width: 167
        }],
        selModel: new Ext.grid.RowSelectionModel({
            singleSelect: false
        }),
        listeners: {
            afteredit: function( obj ) {
                desenharGrafico();
            }
        }
    });


    /*
     * Painel para exibição do gráfico do mapa de conceitos
     */
    var painelGrafico = new Ext.Panel({
        height: 434,
        width: 300,
        hidden: true,
        collapsible: true,
        title: Application.i18n.getMsg( 'graficoMapaConceitos.titulo' ),
        iconCls: 'iconeGraficoMapaConceitos',
        listeners: {
            collapse: function( painel ) {
                Ext.getCmp( 'separadorPainelGraficoEstCon' ).show();
                painel.hide();
                janela.doLayout();
                janela.setActive( true );
                janela.center();

                /*
                 * Se os botões estão sendo criados (como o preview é beta, isso garante que caso
                 * seja comentado não dê acesso a null.
                 */
                var btnGP1 = Ext.getCmp( 'btnExibirGraficoEstrConP1' );
                var btnGP2 = Ext.getCmp( 'btnExibirGraficoEstrConP2' );

                if ( btnGP1 )
                    btnGP1.enable();
                if ( btnGP2 )
                    btnGP2.enable();

            }
        },
        html: '<div id="areaDesenhoEstrCon" class="divDesenhoGraficoMapaConceito"></div>'
    });

    /*
     * Cria janela para o usuário escolher a relação desejada para a busca de
     * um conceito.
     */
    var criarJanelaBuscaSensoComum = function( conceito, destino ) {

        var janela = new Ext.Window({
            iconCls: 'iconeSensoComum',
            modal: true,
            resisable: false,
            title: Application.i18n.getMsg( 'janelaEscolhaRelacao.titulo' ),
            items: [{
                xtype: 'panel',
                bodyStyle: 'padding: 4px;',
                border: false,
                labelAlign: 'right',
                labelWidth: 50,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: Application.i18n.getMsg( 'conceito' ),
                    disabled: true,
                    value: conceito,
                    width: 150
                }, {
                    xtype: 'combo',
                    fieldLabel: Application.i18n.getMsg( 'relacao' ),
                    id: 'comboRelJanSenComEstrCon',
                    name: 'relations',
                    hiddenName: 'relation',
                    allowBlank: false,
                    editable: false,
                    blankText: Application.i18n.getMsg( 'vtype.blank' ),
                    mode: 'local',
                    store: relations,
                    valueField: 'relation',
                    displayField: 'texto',
                    triggerAction: 'all',
                    emptyText: Application.i18n.getMsg( 'selecione' ),
                    width: 150
                }]
            }],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    var combo = Ext.getCmp( 'comboRelJanSenComEstrCon' );

                    if ( combo.validate() ) {

                        $.ajax({

                            dataType: 'json',
                            type: 'post',
                            url: Application.contextPath + '/servlets/CommonSenseServlet',
                            data: {
                                concept: conceito,
                                relation: combo.getValue()
                            },
                            success: function( data, textStatus ) {
                                if ( data.success ) {
                                    Ext.getCmp( 'painelSugSensoComumEstrCon' ).expand();
                                    Ext.getCmp( 'fieldConcSugSensoComumEstrCon' ).setValue( conceito );
                                    Ext.getCmp( 'fieldRelSugSensoComumEstrCon' ).setValue( combo.getRawValue() );
                                    Uteis.preencheDadosSensoComum( data.results, destino );
                                }
                            },
                            complete: function( data, textStatus ) {
                                janela.close();
                            }

                        });

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

        janela.show();
        janela.center();

    }

    var painelPasso2 = new Ext.FormPanel({
        title: Application.i18n.getMsg( 'estrCon.passo2.titulo' ),
        bodyStyle: 'padding: 4px;',
        iconCls: 'iconeConceito',
        height: 434,
        width: 546,
        hidden: true,
        items: [
            gridRelacoes
        ],
        fbar: [/*{
            xtype: 'button',
            text: 'Teste',
            hidden: !Global.DEBUG,
            handler: function( btn, evt ) {
                var linhas = gridRelacoes.getSelectionModel().getSelections();
                for ( var i = 0; i < linhas.length; i++ ) {
                    alert(
                        linhas[i].data.idInterno1 + '-' +
                        linhas[i].data.conceito1 + '\n' +
                        linhas[i].data.relacaoMinsky + '\n' +
                        linhas[i].data.relacaoUsuario + '\n' +
                        linhas[i].data.idInterno2 + '-' +
                        linhas[i].data.conceito2 + '\n' +
                        linhas[i].data.relHierarquia );
                }
            }
        },*/ {
            text: Application.i18n.getMsg( 'exibirGrafico' ),
            id: 'btnExibirGraficoEstrConP2',
            handler: function( btn, evt ) {
                btn.disable();
                Ext.getCmp( 'btnExibirGraficoEstrConP1' ).disable();
                Ext.getCmp( 'separadorPainelGraficoEstCon' ).show();
                painelGrafico.expand( false );
                painelGrafico.show();
                janela.doLayout();
                janela.setActive( true );
                janela.center();
                desenharGrafico();
            }
        }, {
            xtype: 'button',
            text: Application.i18n.getMsg( 'estrCon.btn.adicionarRel' ),
            handler: function( btn, evt ) {
                criarJanelaNovoRelacionamento( mapaConceitos );
            }
        }, {
            xtype: 'button',
            text: Application.i18n.getMsg( 'estrCon.btn.excluirRel' ),
            handler: function( btn, evt ) {

                var linhas = gridRelacoes.getSelectionModel().getSelections();

                if ( linhas.length > 0 ) {
                    Ext.Msg.confirm(
                        Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                        Application.i18n.getMsg( 'dialogo.desejaExcluirRelacionamento' ),
                        function( btnId ) {
                            if ( btnId == 'yes' ) {
                                var relHie = false;
                                for ( var i = 0; i < linhas.length; i++ ) {
                                    if ( !linhas[i].data.relHierarquia ) {

                                        // armazena no buffer
                                        bufferRelacoesApagar.add(
                                            linhas[i].data.idInterno1 + '-' +
                                            linhas[i].data.idInterno2,
                                        {
                                            id: linhas[i].data.idInterno1,
                                            idPai: linhas[i].data.idInterno2
                                        });

                                        gridRelacoes.getStore().remove( linhas[i] );
                                    } else {
                                        relHie = true;
                                    }
                                }
                                if ( relHie ) {
                                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.relacionamentoHierarquia' ) );
                                }

                                desenharGrafico();

                            }
                        }
                    );
                } else {
                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneRelacionamento' ) )
                }
            }
        }]
    });

    /*
     * Painel
     */
    var painel = new Ext.Panel({
        border: false,
        bodyStyle: 'padding: 4px;',
        layout: 'table',
        layouConfig: {
            columns: 3
        },
        items: [{
            xtype: 'panel',
            border: false,
            items: [
                painelPasso1,
                painelPasso2
            ]
        }, 
            separadorBranco.cloneConfig({
                id: 'separadorPainelGraficoEstCon',
                hidden: true
            }),
            painelGrafico
        ]
    });

    /*
     * Botões da janela.
     */
    var btnProximo = new Ext.Button({
        text: Application.i18n.getMsg( 'proximo' ),
        handler: function( btn, evt ) {
            btnProximo.disable();
            btnAnterior.enable();
            btnConcluir.enable();
            painelPasso1.hide();
            painelPasso2.show();
            prepararGrid( mapaConceitos );
            desenharGrafico();
        }
    });

    var btnAnterior = new Ext.Button({
        text: Application.i18n.getMsg( 'anterior' ),
        disabled: true,
        handler: function( btn, evt ) {
            Ext.Msg.confirm(
                Application.i18n.getMsg( 'dialogo.warning.titulo' ),
                Application.i18n.getMsg( 'dialogo.desejaVoltarEstrCon' ),
                function( btnId ) {
                    if ( btnId == 'yes' ) {
                        btnAnterior.disable();
                        btnConcluir.disable();
                        btnProximo.enable();
                        painelPasso1.show();
                        painelPasso2.hide();

                        // limpa o store, o buffer e desenha
                        gridRelacoes.getStore().removeAll();
                        bufferRelacoesApagar = new Ext.util.MixedCollection();
                        desenharGrafico();
                    }
                }
            );
        }
    });

    var btnConcluir = new Ext.Button({
        text: Application.i18n.getMsg( 'concluir' ),
        disabled: true,
        handler: function( btn, evt ) {
            if ( validarGridRelacoes() ) {
                prepararNosGravacao();
            } else {
                Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.preencherRelacoes' ) );
            }
        }
    });

    var btnCancelar = new Ext.Button({
        text: Application.i18n.getMsg( 'cancelar' ),
        handler: function( btn, evt ) {
            janela.hide();
        }
    });

    /*
     * Janela
     */
    var janela = new Ext.Window( {
        id: 'janelaEstrCon',
        iconCls: 'iconeAssistente',
        title: Application.i18n.getMsg( 'estrCon.titulo' ),
        modal: true,
        resizable: false,
        closeAction: 'hide',
        items: [
            painel
        ],
        fbar: [
            btnAnterior,
            btnProximo,
            btnConcluir,
            btnCancelar
        ],
        listeners : {
            hide: function( jan ) {
                if ( jg ) {
                    jg.clear();
                }
                Application.carregarEstrutura();
            }
        }
    });


    /*
     * Cria a janela para inserção de um novo relacionamento.
     */
    var criarJanelaNovoRelacionamento = function( dadosMapa ) {

        var dadosConceito1 = new Ext.list.ListView({
            region: 'center',
            height: 223,
            width: 200,
            autoScroll: true,
            border: false,
            singleSelect: true,
            hideHeaders: true,
            store: new Ext.data.Store({
                data: [],
                reader: new Ext.data.ArrayReader( {
                    idIndex: 0
                },
                Ext.data.Record.create([
                    { name: 'id' },
                    { name: 'conceito' }
                ]))
            }),
            columns: [{
                dataIndex: 'conceito'
            }]
        });

        var dadosConceito2 = new Ext.list.ListView({
            region: 'center',
            height: 223,
            width: 200,
            autoScroll: true,
            border: false,
            singleSelect: true,
            hideHeaders: true,
            store: new Ext.data.Store({
                data: [],
                reader: new Ext.data.ArrayReader( {
                    idIndex: 0
                },
                Ext.data.Record.create([
                    { name: 'id' },
                    { name: 'conceito' }
                ]))
            }),
            columns: [{
                dataIndex: 'conceito'
            }]
        });

        Uteis.copiarConteudoArvore( dadosMapa, dadosConceito1 );
        Uteis.copiarConteudoArvore( dadosMapa, dadosConceito2 );

        var janela = new Ext.Window({
            modal: true,
            resizable: false,
            iconCls: 'iconeJoin',
            title: Application.i18n.getMsg( 'janelaNovoRelacionamento.titulo' ),
            items: [{
                xtype: 'panel',
                bodyStyle: 'padding: 4px;',
                items: [{
                    xtype: 'panel',
                    layout: 'table',
                    border: false,
                    layoutConfig: {
                        columns: 5
                    },
                    items: [{
                        xtype: 'panel',
                        title: Application.i18n.getMsg( 'conceito' ),
                        iconCls: 'iconeConceito',
                        height: 250,
                        items: [
                            dadosConceito1
                        ]
                    },
                        separadorBranco.cloneConfig({ width: 15 }),
                    {
                        xtype: 'panel',
                        border: false,
                        layout: 'form',
                        labelAlign: 'top',
                        items: [{
                            xtype: 'textfield',
                            id: 'fieldRelacaoEstrCon',
                            fieldLabel: Application.i18n.getMsg( 'estrCon.temRelacao' ),
                            labelSeparator: '',
                            allowBlank: false,
                            maxLength: 120,
                            blankText: Application.i18n.getMsg( 'vtype.blank' ),
                            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                            width: 150
                        }]
                    },
                        separadorBranco.cloneConfig({ width: 15 }),
                    {
                        xtype: 'panel',
                        title: Application.i18n.getMsg( 'estrCon.comConceito' ),
                        iconCls: 'iconeConceito',
                        height: 250,
                        items: [
                            dadosConceito2
                        ]
                    }]
                }]
            }],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    var dadosC1 = dadosConceito1.getSelectedRecords();
                    var dadosC2 = dadosConceito2.getSelectedRecords();
                    var vRelacaoUsuario = Ext.getCmp( 'fieldRelacaoEstrCon' ).getValue();

                    if ( dadosC1.length < 1 ) {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecionePrimeiroConceito' ) );
                        return;
                    }

                    if ( dadosC2.length < 1 ) {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneSegundoConceito' ) );
                        return;
                    }

                    if ( dadosC1[0].data.conceito == dadosC2[0].data.conceito ) {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.escolhaConcDif' ) );
                        return;
                    }

                    var campo = Ext.getCmp( 'fieldRelacaoEstrCon' ) ;
                    if ( !campo.validate() ) {
                        campo.focus();
                        return;
                    }

                    var vConceito1 = dadosC1[0].data.conceito;
                    var vConceito2 = dadosC2[0].data.conceito;
                    var vIdInterno1 = dadosC1[0].data.id;
                    var vIdInterno2 = dadosC2[0].data.id;
                    
                    // verifica se o relacionamento já existe
                    var achouIgual = false;
                    gridRelacoes.getStore().each( function( campo ) {
                        if ( vConceito1 == campo.data.conceito1
                            && vConceito2 == campo.data.conceito2 ) {
                            achouIgual = true;
                            return false;
                        }
                    });

                    if ( !achouIgual ) {

                        var novoConceito = [ new Ext.data.Record({
                            idInterno1: vIdInterno1,
                            conceito1: vConceito1,
                            relacaoMinsky: '',
                            relacaoUsuario: vRelacaoUsuario,
                            idInterno2: vIdInterno2,
                            conceito2: vConceito2,
                            relHierarquia: false
                        },
                            Ext.id()
                        )];

                        gridRelacoes.getStore().add( novoConceito );

                        // remove do buffer caso exista
                        bufferRelacoesApagar.removeKey( vIdInterno1 + '-' + vIdInterno2 );

                        desenharGrafico();
                        janela.close();

                    } else {

                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.relacionamentoJaExistente' ) +
                            ' "' + vConceito1 + '" ' +
                            Application.i18n.getMsg( 'e' ) +
                            ' "' + vConceito2 + '".' );

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

        janela.show();
        janela.center();

    };


    /*
     * Prepara a grade de relacionamentos.
     */
    var prepararGrid = function( mapa ) {

        gridRelacoes.getStore().removeAll();

        Uteis.gerarRelacionamentos( gridRelacoes.getStore(), mapa.getRootNode(),
                mapa.getRootNode(), mapa, novaEstrutura, bufferRelacoesHApagar, Application.i18n );

    }


    /*
     * Prepara os nós para a inserção na árvore e chama o ajax para fazer a gravao.
     */
    var prepararNosGravacao = function() {

        // exibe diálogo de aguarde
        var dialogo = Uteis.exibirMensagemAguarde( 
                Application.i18n.getMsg( 'estrCon.salvandoMapa') );

        var acao = '';

        // armazena os dados do mapa que vai ser gravado
        var dados = '<mapaConceitos>' +
                '<idMaterial>' +
                estruturaMaterial.getRootNode().attributes.idInterno +
                '</idMaterial>';

        dados += Uteis.mapaGravacao.gerarEstrutura( mapaConceitos );
        dados += Uteis.mapaGravacao.gerarRelacionamentos( gridRelacoes.getStore() );

        // se a estrutura é nova, então os dados vão ser gravados pela primeira vez
        if ( novaEstrutura ) {
            acao = 'novo';
        } else {
            acao = 'alterar';
        }

        dados += '</mapaConceitos>';

        // função para extração dos dados do buffer
        var extrairDadosBuffer = function( buffer ) {

            var dados = '';

            buffer.each( function( item, index, length ) {
                dados += item.id + '-' + item.idPai + '$';
            });

            return dados;

        };

        $.ajax({

            dataType: 'json',
            type: 'post',
            asynch: false,
            url: Application.contextPath + '/servlets/MapaConceitosServlet',
            data: {
                acao: acao,
                dadosMapa: dados,
                relacoesHExcluir: extrairDadosBuffer( bufferRelacoesHApagar ),
                relacoesExcluir: extrairDadosBuffer( bufferRelacoesApagar )
            },
            success: function( data, textStatus ) {

                dialogo.close();

                if ( data.success ) {

                    // recarrega o material e fecha essa janela
                    Application.carregarEstrutura();
                    janela.hide();

                } else {

                    Uteis.exibirMensagemErro( Uteis.criarMensagemErro( data, Application.i18n ) );

                }

            },
            complete: function( data, textStatus ) {    
            }

        });

    };


    /*
     * Prepara os nós para a edição.
     */
    var prepararNosEdicao = function() {

        if ( estruturaMaterial.getRootNode().childNodes.length == 0 ) {
            novaEstrutura = true;
        } else {
            novaEstrutura = false;
        }

        estruturaMaterial.expandAll();

        /*
         * Faz a cópia da estrutura de conceitos do material para o mapa
         * de conceitos caso não seja uma nova estrutura que vai ser criada.
         */
        if ( !novaEstrutura ) {
            Uteis.copiarEstruturaConceitos( mapaConceitos.getRootNode(), estruturaMaterial.getRootNode() );
        }

        // gera dos dados do mapa atual caso seja uma nova estrutura
        //prepararDadosMapaAtual();

    };


    /*
     * Valida se todos os campos estão preenchidos.
     */
    var validarGridRelacoes = function() {

       var valido = true;

       gridRelacoes.getStore().each( function( campo ) {
           if ( Ext.util.Format.trim( campo.data.relacaoUsuario ) == '' ) {
               valido = false;
               return false;
           }
       });

       return valido;

    }

    /*
     * Função para desenhar.
     */
    var desenharGrafico = function() {

        if ( painelGrafico.isVisible() ) {

            if ( !jg )
                jg = new jsGraphics( $( '#areaDesenhoEstrCon' )[0] );

            if ( mapaConceitos.getRootNode().childNodes[0] ) {

                if ( painelPasso1.isVisible() ) {
                    Desenhos.desenharMapa( jg, Desenhos.gerarDadosMapa(
                            mapaConceitos.getRootNode().childNodes[0],
                            gridRelacoes.getStore(), false ) );
                } else {
                    Desenhos.desenharMapa( jg, Desenhos.gerarDadosMapa(
                            mapaConceitos.getRootNode().childNodes[0],
                            gridRelacoes.getStore(), true ) );
                }
            }

        } else {

            if ( jg )
                jg.clear();

        }
        
    };

    /*
     * Função para resetar o estado do formulário.
     */
    var reset = function() {

        painelPasso1.show();
        painelPasso2.hide();

        btnAnterior.disable();
        btnProximo.enable();
        btnConcluir.disable();

        Ext.getCmp( 'fieldRelSugSensoComumEstrCon' ).setValue( '' );
        Ext.getCmp( 'fieldConcSugSensoComumEstrCon' ).setValue( '' );

        gridRelacoes.getStore().removeAll();

        dadosSensoComum.clearSelections();
        dadosSensoComum.getStore().removeAll();

        mapaConceitos.getSelectionModel().clearSelections();
        mapaConceitos.setRootNode( new Ext.tree.TreeNode({
            leaf: false
        }));

        novaEstrutura = false;
        inserindoNo = false;
        movendoNo = false;
        bufferRelacoesHApagar = new Ext.util.MixedCollection();
        bufferRelacoesApagar = new Ext.util.MixedCollection();

    };

    /*
     * Abre a janela de estruturação do conhecimento.
     * Existe apenas uma instância dessa janela durante a execução de aplicação.
     */
    EstrCon.abrir = function( arvore ) {
        reset();
        estruturaMaterial = arvore;
        prepararNosEdicao();
        janela.show();
        mapaConceitos.expandAll();
        janela.center();
        desenharGrafico();
        Ext.getCmp( 'comboRelSugSenComumEstrCon' ).setValue( 'All' );
    };

};