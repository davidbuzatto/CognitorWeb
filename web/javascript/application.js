/**
 * Arquivo principal da aplicação
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      20 de Agosto de 2009
 *
 */

Application.initComponents = function() {

    /*
     * Guarda o id do material que está em edição
     * essa variável é global para poder ser acessada pela tela inicial.
     */
    Application.idMaterialEdicao = undefined;

    /*
     * Guarda o id da página que está sendo editada no momento
     * essa variável é global para poder ser acessada pelos plugins do editor
     * de texto. É configurado no evento beforetabchange do componente de abas.
     */
    Application.idPaginaEdicao = undefined;

    /*
     * Guarda se o material foi ou não salvo desde a última alteração.
     */
    var materialEdicaoSalvo = true;

    /*
     * Guarda o nó selecionado na árvore de estrutura.
     */
    var noSelecionadoEstrutura = undefined;

    /*
     * Buffer para guardar o conteúdo das páginas.
     */
    var dadosPaginas = new Ext.util.MixedCollection();

    /*
     * Janela de espera.
     */
    var janelaEspera = null;

    /*
     * Flags para guardar o estado do que está acontecendo
     * na árvore durante a reestruturação.
     *
     * A ordem de execução é
     * inserir
     * mover
     * remover
     */
    var removendoNo = false;
    var movendoNo = false;
    var inserindoNo = false;

    /*
     * Menu principal
     */
    var menu = new Ext.Toolbar({
        items: [{
            xtype: 'button',
            text: Application.i18n.getMsg( 'arquivo' ),
            menu: [{
                text: Application.i18n.getMsg( 'novo' ),
                icon: Application.contextPath + '/imagens/icones/page_white_add.png',
                handler: function( b, e ) {
                    novoMaterial( b, e );
                }
            }, {
                text: Application.i18n.getMsg( 'abrir' ),
                icon: Application.contextPath + '/imagens/icones/folder.png',
                handler: function( b, e ) {
                    abrirMaterial( b, e );
                }
            }, {
                id: 'btnMenuArquivoSalvar',
                text: Application.i18n.getMsg( 'salvar' ),
                icon: Application.contextPath + '/imagens/icones/disk.png',
                handler: function( b, e ) {
                    salvarPaginaIndividual();
                }
            }, {
                id: 'btnMenuArquivoSalvarTudo',
                text: Application.i18n.getMsg( 'salvarTudo' ),
                icon: Application.contextPath + '/imagens/icones/disk_multiple.png',
                handler: function( b, e ) {
                    salvarMaterial( b, e, true );
                }
            }, {
                id: 'btnMenuArquivoGerenciar',
                text: Application.i18n.getMsg( 'excluir' ),
                icon: Application.contextPath + '/imagens/icones/bin_closed.png',
                handler: function( b, e ) {
                    excluirMateriais( b, e );
                }
            }, '-', {
                id: 'btnMenuArquivoFechar',
                text: Application.i18n.getMsg( 'fechar' ),
                icon: Application.contextPath + '/imagens/icones/cancel.png',
                handler: function( b, e ) {
                    fecharMaterial( b, e );
                }
            }]
        }, '-', {
            xtype: 'button',
            text: Application.i18n.getMsg( 'exportacao' ),
            menu: [{
                id: 'btnMenuExportacaoSCORM',
                text: Application.i18n.getMsg( 'scorm' ),
                icon: Application.contextPath + '/imagens/icones/package_go.png',
                handler: function( btn, evt ) {
                    exportarMaterial( 'scorm' );
                }
            }, {
                id: 'btnMenuExportacaoHTML',
                text: Application.i18n.getMsg( 'html' ),
                icon: Application.contextPath + '/imagens/icones/package_go.png',
                handler: function( btn, evt ) {
                    exportarMaterial( 'html' );
                }
            }]
        }, '-', {
            xtype: 'button',
            hidden: !Uteis.ehPermitido( 'rel' ),
            text: Application.i18n.getMsg( 'relatorios' ),
            menu: [{
                text: Application.i18n.getMsg( 'rel.estatitiscas' ),
                icon: Application.contextPath + '/imagens/icones/report.png',
                handler: function( evt ) {
                    Uteis.abrirRelatorio( 'estatisticas', null );
                }
            }]
        }, {
            xtype: 'tbseparator',
            hidden: !Uteis.ehPermitido( 'rel' )
        }, {
            xtype: 'button',
            hidden: !Uteis.ehPermitido( 'conf' ),
            text: Application.i18n.getMsg( 'configuracoes' ),
            menu: [{
                text: Application.i18n.getMsg( 'globalConfigs' ),
                icon: Application.contextPath + '/imagens/icones/wrench.png',
                listeners:{
                    click: function(evt) {
                        Configs.abrir();
                    }
                }
            }, {
                text: Application.i18n.getMsg( 'gerUsuarios' ),
                icon: Application.contextPath + '/imagens/icones/group.png',
                listeners:{
                    click: function(evt) {
                        Usuarios.abrir();
                    }
                }
            }]
        }, {
            xtype: 'tbseparator',
            hidden: !Uteis.ehPermitido( 'conf' )
        }, {
            xtype: 'button',
            text: Application.i18n.getMsg( 'ajuda' ),
            menu: [{
                text: Application.i18n.getMsg( 'topAjuda' ),
                icon: Application.contextPath + '/imagens/icones/help.png',
                listeners: {
                    click: function(evt) {
                        Ext.Msg.show({
                            title: Application.i18n.getMsg( 'dialogoAjuda.titulo' ),
                            msg: Application.i18n.getMsg( 'dialogoAjuda.desc' ),
                            buttons: {
                                ok: true
                            }
                        })
                    }
                }
            }, '-', {
                text: Application.i18n.getMsg( 'sobre' ),
                icon: Application.contextPath + '/imagens/icones/information.png',
                listeners: {
                    click: function(evt) {
                        Ext.Msg.show({
                            title: Application.i18n.getMsg( 'dialogoSobre.titulo' ),
                            msg: Application.i18n.getMsg( 'dialogoSobre.desc' ),
                            icon: 'iconeSobre',
                            buttons: {
                                ok: true
                            }
                        })
                    }
                }
            } ]
        }, '->', {
            xtype: 'button',
            hidden: !Uteis.ehPermitido( 'dadosPessoais' ),
            text: Application.i18n.getMsg( 'dadosPessoais' ),
            cls: 'x-btn-text-icon',
            icon: Application.contextPath + '/imagens/icones/user_edit.png',
            listeners:{
                click: function(evt) {
                    DadosPessoais.abrir();
                }
            }
        }, {
            xtype: 'tbseparator',
            hidden: !Uteis.ehPermitido( 'dadosPessoais' )
        }, {
            xtype: 'button',
            text: Application.i18n.getMsg( 'sair' ),
            cls: 'x-btn-text-icon',
            icon: Application.contextPath + '/imagens/icones/exit.png',
            handler: function( b, t ) {
                sair( b, t );
            }
        }]
    });

    /*
     * Barra de ferramentas
     */
    var barraFerramentas = new Ext.Toolbar({
        items: [{
            xtype: 'button',
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/page_white_add.png',
            handler: function( b, e ) {
                novoMaterial( b, e );
            }
        }, {
            xtype: 'button',
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/folder.png',
            handler: function( b, e ) {
                abrirMaterial( b, e );
            }
        }, {
            xtype: 'button',
            id: 'btnBarraSalvar',
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/disk.png',
            handler: function( b, e ) {
                salvarPaginaIndividual();
            }
        }, {
            xtype: 'button',
            id: 'btnBarraSalvarTudo',
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/disk_multiple.png',
            handler: function( b, e ) {
                salvarMaterial( b, e, true );
            }
        }, '-', {
            xtype: 'button',
            id: 'btnBarraSCORM',
            cls: 'x-btn-text-icon',
            text: Application.i18n.getMsg( 'scorm' ),
            icon: Application.contextPath + '/imagens/icones/package_go.png',
            handler: function( btn, evt ) {
                exportarMaterial( 'scorm' );
            }
        }, {
            xtype: 'button',
            id: 'btnBarraHTML',
            cls: 'x-btn-text-icon',
            text: Application.i18n.getMsg( 'html' ),
            icon: Application.contextPath + '/imagens/icones/package_go.png',
            handler: function( btn, evt ) {
                exportarMaterial( 'html' );
            }
        }, '-', {
            xtype: 'button',
            id: 'btnBarraMetadados',
            cls: 'x-btn-text-icon',
            text: Application.i18n.getMsg( 'metadados' ),
            icon: Application.contextPath + '/imagens/icones/page_green.png',
            listeners: {
                click: function() {
                    Metadados.abrir( estruturaMaterial );
                }
            }
        }, {
            xtype: 'button',
            id: 'btnBarraPropriedades',
            cls: 'x-btn-text-icon',
            text: Application.i18n.getMsg( 'propriedades' ),
            icon: Application.contextPath + '/imagens/icones/page_edit.png',
            listeners: {
                click: function() {

                    var no = estruturaMaterial.getSelectionModel().getSelectedNode();

                    if ( no )  {
                        if ( !Uteis.materialEntreNoERaiz( no, estruturaMaterial.getRootNode() ) ) {
                            abrirJanelaPropriedadesOA( no );
                        } else {
                            Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.alterarItemSubMaterial' ) );
                        }
                    } else {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecione' ) );
                    }

                }
            }
        }, '-', {
            xtype: 'button',
            id: 'btnBarraAnalogias',
            cls: 'x-btn-text-icon',
            text: Application.i18n.getMsg( 'analogias' ),
            icon: Application.contextPath + '/imagens/icones/comment.png',
            listeners: {
                click: function() {
                    if ( abasPaginas.items.length != 0 ) {
                        Analogias.abrir( editorTexto );
                    } else {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.paginaNaoAbertaAnalogias' ) );
                    }
                }
            }
        }]
    });

    /*
     * Barra de status
     */
    var barraStatus = new Ext.ux.StatusBar({
        defaultText: Application.i18n.getMsg( 'barraStatus.pronto' )
    });

    /*
     * Árvore para manter a estrutura do material.
     * Uma nova janela para cada chamada.
     */
    var estruturaMaterial = new Ext.tree.TreePanel({
        region: 'center',
        autoScroll: true,
        containerScroll: true,
        enableDD: true,
        useArrow: true,
        border: false,
        animate: false,
        root: new Ext.tree.AsyncTreeNode({
            text: Application.i18n.getMsg( 'raizVazia' ),
            icon: Application.contextPath + '/imagens/icones/book_open.png',
            leaf: true
        }),
        listeners: {
            click: function( no, evt ) {
                noSelecionadoEstrutura = no;

                if ( no.attributes.tipo == 'pagina' ) {

                    // verifica se já foi inserido
                    if ( !abasPaginas.findById( 'tab-' + no.attributes.idInterno) ) {
                        
                        $.ajax({
                            async: false,
                            dataType: 'html',
                            type: 'post',
                            url: Application.contextPath + '/ajax/paginas/paginaPorId.jsp',
                            data: {
                                idPagina: no.attributes.idInterno
                            },
                            beforeSend: function( data ) {
                                barraStatus.setText( Application.i18n.getMsg( 'barraStatus.carregandoPagina' ) );
                                estruturaMaterial.disable();
                            },
                            complete: function( data, textStatus ) {
                                barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );
                                estruturaMaterial.enable();
                            },
                            success: function( data, textStatus ) {

                                if ( abasPaginas.hidden ) {
                                    abasPaginas.show();
                                }

                                var painel = new Ext.Panel({
                                    id: 'tab-' + no.attributes.idInterno,
                                    iconCls: 'iconePagina',
                                    closable: true,
                                    title: Ext.util.Format.ellipsis( no.attributes.text, 22 )
                                });

                                // armazena os dados no buffer
                                dadosPaginas.add( 'tab-' + no.attributes.idInterno, {
                                    salvo: true,
                                    id: no.attributes.idInterno,
                                    titulo: no.attributes.text,
                                    conteudoHtml: Ext.util.Format.trim( data )
                                });

                                abasPaginas.add( painel );
                                
                                abasPaginas.setActiveTab( painel.id );

                                /*
                                 * A aparição dos dados no editor fica por conta
                                 * do evento beforetabchange da aba das páginas.
                                 */

                            }

                        });
                        
                    } else {
                        abasPaginas.setActiveTab( abasPaginas.findById( 'tab-' + no.attributes.idInterno) );
                    }

                    // atenção: a carga dos objetos da página é feita no beforetabchange

                    // liga o editor
                    editorTexto.enable();

                }

                if ( Global.DEBUG ) {
                    Uteis.alertNo( no );
                }

            }, 
            beforemovenode: function( tree, node, oldParent, newParent, index ) {

                tree.disable();
                barraStatus.setText( Application.i18n.getMsg( 'barraStatus.movendoItem' ) );

                var mudou = false;

                // se o nó não estiver sendo inserido pelo usuário
                if ( !inserindoNo ) {
                    
                    // indica que o nó está sendo movido
                    movendoNo = true;

                    // obtém os vizinhos da posição que vai ser inserido o nó (ordenação)

                    // não permite mover nada para dentro de um submaterial
                    if ( Uteis.materialEntreNoERaiz( newParent, tree.getRootNode() ) ) {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.moverParaSubmaterial' ) );
                        tree.enable();
                        barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );
                        return false;
                    }

                    // não permite que um item de um submaterial seja movido para o material principal
                    if ( Uteis.quantidadeMateriaisPercurso( oldParent, tree.getRootNode() ) > 0 ) {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.alterarEstruturaSubMaterial' ) );
                        tree.enable();
                        barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );
                        return false;
                    }

                    // não permite que uma página seja inserida na raiz de um material
                    if ( node.attributes.tipo == 'pagina' && newParent.attributes.tipo == 'material' ) {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.moverPaginaMaterial' ) );
                        tree.enable();
                        barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );
                        return false;
                    }

                    // não permite que uma página principal seja movida
                    if ( node.attributes.tipo == 'pagina' && node.attributes.principal ) {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.moverPaginaPrincipal' ) );
                        tree.enable();
                        barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );
                        return false;
                    }

                    // não permite mover nada para o local da página principal
                    // isso não existe mais pois a alteração da estrutura do mapa de conceitos
                    // só pode ser feita dentro do assistente de estruturação do conhecimento
                    /*if ( newParent.attributes.tipo == 'conceito' && index == 0 ) {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.moverLocalPaginaPrincipal' ) );
                        tree.enable();
                        barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );
                        return false;
                    }*/

                    // verifica qual será a mudança a se realizar
                    var retirar = oldParent.attributes.tipo + '-' + node.attributes.tipo;
                    var inserir = newParent.attributes.tipo + '-' + node.attributes.tipo;

                    // se os pais são iguais, apenas reordena
                    if ( oldParent == newParent ) {
                        mudou = true;
                    } else {
                        $.ajax( {
                            async: false,
                            dataType: 'json',
                            type: 'post',
                            url: Application.contextPath + '/servlets/AlteraEstruturaMaterialServlet',
                            data: {
                                acao: 'alterarEstrutura',
                                retirarDe: retirar,
                                inserirEm: inserir,
                                idMaterial: tree.root.attributes.idInterno,
                                idNo: node.attributes.idInterno,
                                tipoNo: node.attributes.tipo,
                                idPaiVelho: oldParent.attributes.idInterno,
                                tipoPaiVelho: oldParent.attributes.tipo,
                                idPaiNovo: newParent.attributes.idInterno,
                                tipoPaiNovo: newParent.attributes.tipo
                            },
                            success: function( data, textStatus ) {
                                mudou = data.success;
                            }
                        });
                    }

                    if ( mudou ) {
                        mudou = Uteis.reordenarItemsMaterial( tree, node, oldParent, newParent, index, false );
                    }

                    tree.enable();
                    barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );

                    return mudou;

                }

                // não está mais movendo
                movendoNo = false;

                estruturaMaterial.enable();
                barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );

                return mudou;

            },
            beforeremove: function( tree, parent, node ) {

                // se não estiver movendo os nós
                if ( !movendoNo && !parent.isLoading() ) {
                    
                    tree.disable();
                    barraStatus.setText( Application.i18n.getMsg( 'barraStatus.removendoItem' ) );

                    var mudou = false;

                    // verifica qual será a mudança a se realizar
                    var retirar = parent.attributes.tipo + '-' + node.attributes.tipo;

                    $.ajax({
                        async: false,
                        dataType: 'json',
                        type: 'post',
                        url: Application.contextPath + '/servlets/AlteraEstruturaMaterialServlet',
                        data: {
                            acao: 'removerNo',
                            retirarDe: retirar,
                            idMaterial: tree.root.attributes.idInterno,
                            idNo: node.attributes.idInterno,
                            tipoNo: node.attributes.tipo,
                            idPai: parent.attributes.idInterno,
                            tipoPai: parent.attributes.tipo
                        },
                        success: function( data, textStatus ) {
                            mudou = data.success;
                        }
                    });
                    
                    // reordena os nós
                    if ( mudou ) {
                        mudou = Uteis.reordenarItemsMaterial( tree, node, node.parentNode,
                                node.parentNode, node.attributes.ordem, true );
                    }

                    tree.enable();
                    barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );

                    return mudou;

                }

                // não está movendo o nó
                movendoNo = false;

                return true;
                
            },
            append: function( tree, parent, node, index ) {
                
                var mudou = false;

                // se não estiver movendo os nós
                if ( inserindoNo ) {

                    tree.disable();
                    barraStatus.setText( Application.i18n.getMsg( 'barraStatus.inserindoItem' ) );

                    // verifica qual será a mudança a se realizar
                    var inserir = parent.attributes.tipo + '-' + node.attributes.tipo;

                    // atribui o índice ao nó
                    node.attributes.ordem = index;

                    /*
                     * Na inserção não precisa ordenar, pois já se sabe a localização onde
                     * o novo nó vai ser inserido (sempre a última), não havendo necessidade
                     * de reordenar os indices dos outros nós.
                     */

                    $.ajax({
                        async: false,
                        dataType: 'json',
                        type: 'post',
                        url: Application.contextPath + '/servlets/AlteraEstruturaMaterialServlet',
                        data: {
                            acao: 'inserirNo',
                            idMaterial: tree.getRootNode().attributes.idInterno,
                            inserirEm: inserir,
                            ordem: index,
                            idNo: node.attributes.idInterno,
                            tipoNo: node.attributes.tipo,
                            idPai: parent.attributes.idInterno,
                            tipoPai: parent.attributes.tipo
                        },
                        success: function( data, textStatus ) {
                            mudou = data.success;
                        }
                    });

                    // não está mais inserindo o nó
                    inserindoNo = false;

                    tree.enable();
                    barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );

                    return mudou;

                }

                return mudou;

            },
            contextmenu: function( node ) {
                if ( Application.idMaterialEdicao ) {
                    node.select();
                    exibirMenuArvore( node );
                }
            }
        }
    });


    /*
     * Ordenador para a árvore
     */
    var sorterEstruturaMaterial = new Ext.tree.TreeSorter(
        estruturaMaterial, {
        dir: 'asc',
        sortType: function ( node ) {
            // retorna a ordem do nó dentro do pai
            return node.attributes.ordem;
        }
    });


    /*
     * Exibição do menu de contexto da árvore.
     */
    var exibirMenuArvore = function( no ) {

        var btnMetadados = {
            text: Application.i18n.getMsg( 'metadados' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/page_green.png',
            handler: function( btn, evt ) {
                if ( !Uteis.materialEntreNoERaiz( no, estruturaMaterial.getRootNode() ) ) {
                    Metadados.abrir( estruturaMaterial );
                } else {
                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.alterarMetadadosItemSubMaterial' ) );
                }
            }
        };

        var btnPropriedades = {
            text: Application.i18n.getMsg( 'propriedades' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/page_edit.png',
            handler: function( btn, evt ) {
                if ( !Uteis.materialEntreNoERaiz( no, estruturaMaterial.getRootNode() ) ) {
                    abrirJanelaPropriedadesOA( no );
                } else {
                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.alterarItemSubMaterial' ) );
                }
            }
        };

        var btnGrupo = {
            text: Application.i18n.getMsg( 'novoGrupo' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/book_add.png',
            handler: function( btn, evt ) {
                if ( no )
                    no.expand();
                inserirNo( estruturaMaterial, no, 'grupo', Application.contextPath + '/imagens/icones/book.png' );
            }
        };

        var btnPagina = {
            text: Application.i18n.getMsg( 'novaPagina' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/page_add.png',
            handler: function( btn, evt ) {
                if ( no )
                    no.expand();
                inserirNo( estruturaMaterial, no, 'pagina', Application.contextPath + '/imagens/icones/page.png' );
            }
        };

        var btnExcluir = {
            text: Application.i18n.getMsg( 'excluir' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/cross.png',
            handler: function( btn, evt ) {
                if ( Uteis.nivelMaterial( no ) == 1 || !Uteis.materialEntreNoERaiz( no, estruturaMaterial.getRootNode() )  ) {
                    excluirNo( estruturaMaterial, no );
                } else {
                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.excluirItemSubMaterial' ) );
                }
            }
        };

        switch ( no.attributes.tipo ) {

            case 'material': {

                var btns = null;

                if ( no.attributes.estrCon ) {
                    btns = [
                        btnMetadados,
                        btnPropriedades
                    ]
                } else {
                    btns = [
                        btnGrupo,
                        '-',
                        btnMetadados,
                        btnPropriedades
                    ]
                }
                new Ext.menu.Menu({
                    items: btns
                }).show( no.ui.getAnchor() );
                break;
            }

            case 'conceito': {
                new Ext.menu.Menu({
                    items: [
                        btnPropriedades
                    ]
                }).show( no.ui.getAnchor() );
                break;
            }

            case 'grupo': {
                new Ext.menu.Menu({
                    items: [
                        btnGrupo,
                        btnPagina,
                        '-',
                        btnMetadados,
                        btnPropriedades,
                        '-',
                        btnExcluir
                    ]
                }).show( no.ui.getAnchor() );
                break;
            }

            case 'pagina': {

                if ( no.attributes.principal ) {
                    new Ext.menu.Menu({
                        items: [
                            btnMetadados,
                            btnPropriedades
                        ]
                    }).show( no.ui.getAnchor() );
                } else {
                    new Ext.menu.Menu({
                        items: [
                            btnMetadados,
                            btnPropriedades,
                            '-',
                            btnExcluir
                        ]
                    }).show( no.ui.getAnchor() );
                }
                break;
            }

        }

    };


    /*
     * Exibição do menu de contexto da árvore de objetos.
     */
    var exibirMenuArvoreObjetos = function( no ) {

        var btnMetadados = {
            text: Application.i18n.getMsg( 'metadados' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/page_green.png',
            handler: function( btn, evt ) {
                Metadados.abrir( estruturaMaterial );
            }
        };

        var btnPropriedades = {
            text: Application.i18n.getMsg( 'propriedades' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/page_edit.png',
            handler: function( btn, evt ) {
                abrirJanelaPropriedadesOA( no );
            }
        };

        var btnExcluir = {
            text: Application.i18n.getMsg( 'excluir' ),
            cls: 'x-btn-icon',
            icon: Application.contextPath + '/imagens/icones/cross.png',
            handler: function( btn, evt ) {
                excluirNo( objetosPagina, no );
            }
        };

        new Ext.menu.Menu({
            items: [
                btnMetadados,
                btnPropriedades,
                '-',
                btnExcluir
            ]
        }).show( no.ui.getAnchor() );

    };


    /*
     * Cria e abre janela de propriedades dos OAs da estrutura.
     * Uma nova janela para cada chamada.
     */
    var abrirJanelaPropriedadesOA = function( no ) {

        var titulo = '';
        var icone = '';
        var editarCompartilhado = false;
        var tipo = no.attributes.tipo;

        switch ( tipo ) {
            case 'material':
                titulo = Application.i18n.getMsg( 'editorProp.titulo.mat' );
                icone = 'iconeMaterial';
                break;
            case 'conceito':
                titulo = Application.i18n.getMsg( 'editorProp.titulo.con' );
                icone = 'iconeConceito';
                break;
            case 'grupo':
                titulo = Application.i18n.getMsg( 'editorProp.titulo.gru' );
                icone = 'iconeGrupo';
                break;
            case 'pagina':
                titulo = Application.i18n.getMsg( 'editorProp.titulo.pag' );
                icone = 'iconePagina';
                break;
            case 'imagem':
                titulo = Application.i18n.getMsg( 'editorProp.titulo.img' );
                icone = 'iconeImagem';
                break;
            case 'video':
                titulo = Application.i18n.getMsg( 'editorProp.titulo.vid' );
                icone = 'iconeVideo';
                break;
            case 'som':
                titulo = Application.i18n.getMsg( 'editorProp.titulo.som' );
                icone = 'iconeSom';
                break;
        }

        var checkLinks = false;
        var checkBarra = true;

        if ( no.attributes.tipo == 'material' ) {
            checkLinks = no.attributes.layout.charAt( 1 ) == 'C' ? true : false;
            checkBarra = no.attributes.layout.charAt( 3 ) == 'C' ? true : false;
        }

        // somente no material pode ser editado o compartilhado
        if ( no.attributes.tipo == 'material' )
            editarCompartilhado = true;

        var form = new Ext.FormPanel({
            url: Application.contextPath + '/servlets/AlteraPropriedadesOAServlet',
            border: false,
            bodyStyle: 'padding: 4px;',
            labelAlign: 'right',
            labelWidth: tipo == 'material' ? 180 : 100,
            items: [{
                xtype: 'hidden',
                name: 'acao',
                value: 'alterar-' + no.attributes.tipo
            }, {
                xtype: 'hidden',
                name: 'id',
                value: no.attributes.idInterno
            }, {
                xtype: 'textfield',
                allowBlank: false,
                fieldLabel: Application.i18n.getMsg( 'titulo' ),
                id: 'fieldTituloEditorPropriedades',
                name: 'titulo',
                value: no.text,
                maxLength: 100,
                blankText: Application.i18n.getMsg( 'vtype.blank' ),
                maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                width: tipo == 'material' ? 200 : 150
            }, {
                xtype: 'checkbox',
                name: 'compartilhado',
                hidden: !editarCompartilhado,
                checked: no.attributes.compartilhado,
                hideLabel: !editarCompartilhado,
                fieldLabel: Application.i18n.getMsg( 'compartilhado' )
            }, {
                xtype: 'fieldset',
                id: 'fsOrganizaEditorPropriedades',
                title: Application.i18n.getMsg( 'organizacaoMaterial' ),
                layout: 'table',
                layoutConfig: {
                    columns: 3
                },
                items: [{
                    xtype: 'checkbox',
                    checked: checkLinks,
                    name: 'organizacaoPagina',
                    boxLabel: Application.i18n.getMsg( 'linksEntrePaginas' )
                },
                    separadorBranco.cloneConfig(),
                {
                    xtype: 'checkbox',
                    checked: checkBarra,
                    name: 'organizacaoBarra',
                    boxLabel: Application.i18n.getMsg( 'barraNavegacao' )
                }, {
                    xtype: 'panel',
                    width: 104,
                    height: 104,
                    border: false,
                    bodyCssClass: 'layoutComLink'
                },
                    separadorBranco.cloneConfig(),
                {
                    xtype: 'panel',
                    width: 104,
                    height: 104,
                    border: false,
                    bodyCssClass: 'layoutComBarra'
                }]
            }]
        });

        var janela = new Ext.Window({
            title: String.format( Application.i18n.getMsg( 'editorProp.titulo' ), titulo ),
            iconCls: icone,
            modal: true,
            items: [
                form
            ],
            fbar: [{
                text: Application.i18n.getMsg( 'salvar' ),
                handler: function( btn, evt ) {
                    form.getForm().submit({
                        success: function( form, action ) {

                            var v = form.getValues();
                            
                            no.setText( Ext.getCmp( 'fieldTituloEditorPropriedades').getValue() );
                            no.attributes.compartilhado = v['compartilhado'] ? true : false;

                            if ( no.attributes.tipo == 'material' ) {
                                no.attributes.layout = Uteis.gerarLayoutMaterial(
                                    v['organizacaoPagina'] ? true : false,
                                    v['organizacaoBarra'] ? true : false
                                );
                            } else {
                                alterarTituloAba( no );
                            }
                            
                            janela.close();

                        },
                        failure: function( form, action ) {
                            Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                        },
                        waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                        waitMsg: Application.i18n.getMsg( 'salvando' )
                    });
                }
            }, {
                text: Application.i18n.getMsg( 'cancelar' ),
                handler: function( btn, evt ) {
                    janela.close();
                }
            }]
        });

        Ext.getCmp( 'fsOrganizaEditorPropriedades' ).setVisible( 
                tipo == 'material' );

        janela.show();
        janela.center();

    }

    /*
     * Editor da estrutura, para poder trocar o título dos nós
     */
    var editorEstruturaMaterial = new Ext.tree.TreeEditor(
        estruturaMaterial,
        new Ext.form.TextField({
            allowBlank: false,
            maxLength: 100,
            blankText: Application.i18n.getMsg( 'vtype.blank' ),
            maxLengthText: Application.i18n.getMsg( 'vtype.maximum' )
        }), {
            listeners: {
                beforecomplete: function( editor, novoValor, valorOriginal ) {
                    if ( !Uteis.materialEntreNoERaiz( editor.editNode, estruturaMaterial.getRootNode() ) ) {
                        $.ajax( {
                            async: false,
                            dataType: 'json',
                            type: 'post',
                            url: Application.contextPath + '/servlets/AlteraEstruturaMaterialServlet',
                            data: {
                                acao: 'alterarTitulo',
                                id: editor.editNode.attributes.idInterno,
                                tipo: editor.editNode.attributes.tipo,
                                titulo: novoValor
                            },
                            success: function( data, textStatus ) {
                                if ( !data.success ) {
                                    editor.cancelEdit();
                                } else {
                                    alterarTituloAba( editor.editNode, novoValor );
                                }
                            }
                        });
                    } else {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.alterarItemSubMaterial' ) );
                    }
                }
            }
        }
    );

    /*
     * Faz a carga da estrutura dinamicamente
     */
    Application.carregarEstrutura = function() {

        /*
         * Faz uma requisição assíncrona para a obtenção da árvore
         * da estrutura do material.
         *
         * Obtém primeiro o título e então configura para a obtenção da
         * estrutura.
         */
        $.ajax( {
            async: false,
            dataType: 'json',
            type: 'post',
            data: {
                id: Application.idMaterialEdicao
            },
            url: Application.contextPath + '/ajax/materiais/materialPorId.jsp',
            success: function( data, textStatus ) {

                /*
                 * Cria uma nova raiz caso a obtenção do título tenha sido
                 * bem sucedida e configura um loader com o caminho correto
                 * para a obtenção dos dados da estrutura.
                 */
                var raiz = new Ext.tree.AsyncTreeNode({
                    idInterno: data.idInterno,
                    ordem: data.ordem,
                    compartilhado: data.compartilhado,
                    estrCon: data.estrCon,
                    tipo: data.tipo,
                    layout: data.layout,
                    text: data.titulo,
                    icon: Application.contextPath + '/imagens/icones/book_open.png',
                    loader: new Ext.tree.TreeLoader({
                        dataUrl: Application.contextPath + '/ajax/materiais/estruturaMaterialPorId.jsp',
                        baseParams: {
                            id: Application.idMaterialEdicao
                        },
                        preloadChildren: true,
                        clearOnLoad: true
                    }),
                    listeners: {
                        beforeload: function( no, noCarregado, callback ) {
                            barraStatus.setText( Application.i18n.getMsg( 'barraStatus.carregandoMaterial' ) );
                            estruturaMaterial.disable();
                        },
                        load: function( no, noCarregado, response ) {
                            barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );
                            estruturaMaterial.enable();
                            Application.ligarDesligarBotoes( Global.LIGAR );
                        }
                    }
                });

                if ( data.estrCon ) {
                    btnConceito.setVisible( true );
                    btnNovoGrupo.setVisible( false );
                    Ext.getCmp( 'sepBtnEstrCon' ).setVisible( false );
                    Ext.getCmp( 'sepBtnExcl' ).setVisible( false );
                    btnExcluirNo.setVisible( false );
                    Ext.getCmp( 'sepBtnExpCont' ).setVisible( true );
                    btnNovaPagina.setVisible( false );
                    estruturaMaterial.dragZone.lock();
                    estruturaMaterial.dropZone.lock();
                } else {
                    btnConceito.setVisible( false );
                    btnNovoGrupo.setVisible( true );
                    Ext.getCmp( 'sepBtnEstrCon' ).setVisible( true );
                    Ext.getCmp( 'sepBtnExcl' ).setVisible( true );
                    btnExcluirNo.setVisible( true );
                    Ext.getCmp( 'sepBtnExpCont' ).setVisible( true );
                    btnNovaPagina.setVisible( true );
                    estruturaMaterial.dragZone.unlock();
                    estruturaMaterial.dropZone.unlock();
                }

                estruturaMaterial.setRootNode( raiz );
                raiz.expand();
            }
        });

    };

    /*
     * Objetos da página selecionada no momento (ainda vai ser alterado)
     */
    var objetosPagina = new Ext.tree.TreePanel({
        region: 'center',
        autoScroll: true,
        containerScroll: true,
        useArrow: true,
        border: false,
        animate: false,
        root: new Ext.tree.TreeNode({
            text: Application.i18n.getMsg( 'latEsquerda.objPagina' ),
            icon: Application.contextPath + '/imagens/icones/objects.png',
            leaf: false
        }),
        listeners: {
            click: function( no ) {

                if ( Global.DEBUG ) {
                    Uteis.alertNo( no );
                }

            },
            beforeremove: function( tree, parent, node ) {
                
                tree.disable();
                barraStatus.setText( Application.i18n.getMsg( 'barraStatus.removendoItem' ) );

                var mudou = false;

                $.ajax({
                    async: false,
                    dataType: 'json',
                    type: 'post',
                    url: Application.contextPath + '/servlets/AlteraEstruturaMaterialServlet',
                    data: {
                        acao: 'removerNo',
                        idMaterial: estruturaMaterial.getRootNode().attributes.idInterno,
                        retirarDe: 'pagina-' + node.attributes.tipo,
                        idNo: node.attributes.idInterno,
                        tipoNo: node.attributes.tipo,
                        idPai: tree.getRootNode().attributes.idInterno,
                        tipoPai: 'pagina'
                    },
                    success: function( data, textStatus ) {
                        if ( data.success ) {
                            mudou = true;
                        }
                    }
                });

                tree.enable();
                barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );

                return mudou;

            },
            contextmenu: function( node ) {

                switch ( node.attributes.tipo ) {
                    case 'imagem':
                    case 'video':
                    case 'som':
                        if ( Application.idMaterialEdicao ) {
                            node.select();
                            exibirMenuArvoreObjetos( node );
                        }
                        break;
                }

            }
        }
    });

    /*
     * Faz a carga dos objetos de uma determinada página
     */
    Application.carregarObjetosPagina = function( idPagina ) {
        objetosPagina.disable();
        Uteis.carregarObjetosPagina( idPagina, objetosPagina );
        objetosPagina.expandAll();
        objetosPagina.enable();
    };

    /*
     * Botões da árvore
     */
    var btnConceito = new Ext.Button({
        cls: 'x-btn-icon',
        icon: Application.contextPath + '/imagens/icones/lightbulb_add.png',
        hidden: true,
        handler: function( btn, evt ) {

            salvarMaterial( btn, evt, true );
            abasPaginas.items.each( function( item ) {
                abasPaginas.remove( item );
            });

            EstrCon.abrir( estruturaMaterial );

        }
    });

    var btnNovoGrupo = new Ext.Button({
        cls: 'x-btn-icon',
        icon: Application.contextPath + '/imagens/icones/book_add.png',
        hidden: true,
        handler: function( btn, evt ) {
            var no = estruturaMaterial.getSelectionModel().getSelectedNode();
            if ( no )
                no.expand();
            inserirNo( estruturaMaterial, no, 'grupo', Application.contextPath + '/imagens/icones/book.png' );
        }
    });

    var btnNovaPagina = new Ext.Button({
        cls: 'x-btn-icon',
        icon: Application.contextPath + '/imagens/icones/page_add.png',
        hidden: true,
        handler: function( btn, evt ) {
            var no = estruturaMaterial.getSelectionModel().getSelectedNode();
            if ( no )
                no.expand();
            inserirNo( estruturaMaterial, no, 'pagina', Application.contextPath + '/imagens/icones/page.png' );
        }
    });

    var btnExcluirNo = new Ext.Button({
        cls: 'x-btn-icon',
        icon: Application.contextPath + '/imagens/icones/cross.png',
        hidden: true,
        handler: function( btn, evt ) {
            var no = estruturaMaterial.getSelectionModel().getSelectedNode();
            if ( Uteis.nivelMaterial( no ) == 1 || !Uteis.materialEntreNoERaiz( no, estruturaMaterial.getRootNode() )  ) {
                excluirNo( estruturaMaterial, no );
            } else {
                Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.excluirItemSubMaterial' ) );
            }
        }
    });

    var btnExpandirEstrutura = new Ext.Button({
        cls: 'x-btn-icon',
        icon: Application.contextPath + '/imagens/icones/expandir.png',
        handler: function( b, evt ) {
            estruturaMaterial.expandAll();
        }
    });

    var btnContrairEstrutura = new Ext.Button({
        cls: 'x-btn-icon',
        icon: Application.contextPath + '/imagens/icones/contrair.png',
        handler: function( b, evt ) {
            estruturaMaterial.collapseAll();
        }
    });

    /*
     * Botões da árvore de objetos
     */
    var btnExpandirObjetosPagina = new Ext.Button({
        cls: 'x-btn-icon',
        icon: Application.contextPath + '/imagens/icones/expandir.png',
        handler: function( b, evt ) {
            objetosPagina.expandAll();
        }
    });

    var btnContrairObjetosPagina = new Ext.Button({
        cls: 'x-btn-icon',
        icon: Application.contextPath + '/imagens/icones/contrair.png',
        handler: function( b, evt ) {
            objetosPagina.collapseAll();
        }
    });

    /*
     * Separadors para a interface
     */
    var separador = new Ext.Panel({
        height: 4,
        border: false,
        bodyCssClass: 'fundoSeparadorBotoesEstrutura'
    });

    var separadorBranco = new Ext.Panel({
        height: 15,
        width: 15,
        border: false
    });

    /*
     * Abas para as páginas abertas no momento.
     */
    var abasPaginas = new Ext.TabPanel({
        anchor: '100%',
        enableTabScroll: true,
        activeTab: 0,
        hidden: true,
        minTabWidth: 200,
        tabWidth: 200,
        resizeTabs: true,
        listeners: {
            remove: function( tabPanel, tab ) {
                
                // remove do buffer
                dadosPaginas.removeKey( tab.id );

                if ( tabPanel.items.length == 0 ) {
                    tabPanel.hide();
                    editorTexto.setValue( '' );
                    editorTexto.disable();

                    // reseta o id da página global
                    Application.idPaginaEdicao = undefined;
                    
                    // redimensiona o editor
                    var t = viewport.getHeight() - ( 56 + barraStatus.getHeight() + 4 );
                    editorTexto.setHeight( t );
                }

            },
            add: function( painelAba, painel, index ) {

                // redimensionamento
                if ( painelAba.items.length == 1 ) {

                    // 56 é o tamanho fixo do painel do cabeçalho (menu + barraFerramentas)
                    var t = viewport.getHeight() - ( painelAba.getHeight() + 56 + barraStatus.getHeight() + 4 );
                    editorTexto.setHeight( t );

                }

            },
            beforeremove: function( tabPanel, tab ) {

                var dados = dadosPaginas.get( tab.id );

                if ( dados ) {

                    /*
                     * Verifica se os valores do buffer e do editor são diferentes
                     * caso seja, atualiza o buffer e marca como não salvo.
                     */
                    if ( tabPanel.getActiveTab() == tab ) {

                        if ( Ext.util.Format.trim( editorTexto.getValue() ) !=
                            dados.conteudoHtml ) {

                            dados.salvo = false;
                            dados.conteudoHtml = Ext.util.Format.trim( editorTexto.getValue );
                            tab.setTitle( Ext.util.Format.ellipsis( '* ' + dados.titulo, 22 ), 'iconePaginaNaoSalva' );
                            materialEdicaoSalvo = false;

                        }

                    }

                    if ( !dados.salvo ) {

                        if ( confirm( Application.i18n.getMsg( 'dialogo.desejaSalvarPagina' ) + dados.titulo + '"?' ) ) {

                            // se a aba atual está ativa, pega o valor do editor
                            if ( tab == tabPanel.getActiveTab() ) {
                                dados.conteudoHtml = Ext.util.Format.trim( editorTexto.getValue() );
                            }

                            salvarPagina( dados, true );

                        }

                    }

                }

            },
            beforetabchange: function( tabPanel, newTab, currentTab ) {
                
                /*
                 * Troca os dados do buffer e do editor.
                 * A aba atual deve existir, bem como mais de uma aba precisa
                 * existir no painel, possibilitando assim a troca de dados.
                 */
                if ( currentTab && abasPaginas.items.length > 1 ) {
                    var dadosAbaAtual = dadosPaginas.get( currentTab.id );
                    if ( dadosAbaAtual ) {
                        var dadosEditor = Ext.util.Format.trim( editorTexto.getValue() );
                        if ( dadosEditor != dadosAbaAtual.conteudoHtml ) {
                            dadosAbaAtual.salvo = false;
                            dadosAbaAtual.conteudoHtml = dadosEditor;
                            currentTab.setTitle( Ext.util.Format.ellipsis( '* ' + dadosAbaAtual.titulo, 22 ), 'iconePaginaNaoSalva' );
                            materialEdicaoSalvo = false;
                        }
                    }
                }

                if ( newTab ) {
                    var dadosNovaAba = dadosPaginas.get( newTab.id );
                    if ( dadosNovaAba ) {
                        editorTexto.enable();
                        editorTexto.setValue( dadosNovaAba.conteudoHtml );

                        // armazena globalmente o id da página em edição
                        Application.idPaginaEdicao = dadosNovaAba.id;

                        // faz a carga dos objetos da página
                        Application.carregarObjetosPagina( dadosNovaAba.id );

                    }
                }

            }
        }
    });

    /*
     * Salva uma página do material.
     */
    var salvarPagina = function( dados, asincrono ) {

        barraStatus.setText( Application.i18n.getMsg( 'barraStatus.salvandoPagina' ) );

        $.ajax({
            async: asincrono,
            dataType: 'json',
            type: 'post',
            url: Application.contextPath + '/servlets/SalvarPaginaServlet',
            data: {
                idMaterial: estruturaMaterial.getRootNode().attributes.idInterno,
                idPagina: dados.id,
                conteudoHtml: dados.conteudoHtml
            },
            success: function( data, textStatus ) {
                if ( data.success ) {

                    dados.salvo = data.success;

                    var aba = abasPaginas.findById( 'tab-' + dados.id );
                    
                    if ( aba != null ) {
                        if ( dados.salvo ) {
                            aba.setTitle( Ext.util.Format.ellipsis( dados.titulo, 22 ), 'iconePagina' );
                        } else {
                            aba.setTitle( Ext.util.Format.ellipsis( '* ' + dados.titulo, 22 ), 'iconePaginaNaoSalva' );
                        }
                    }

                }

                barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );

            }
        });

    };


    /*
     * Salva uma página individualmente. Usado pelos botões salvar.
     */
    var salvarPaginaIndividual = function() {

        if ( Application.idPaginaEdicao ) {

            dadosPaginas.get( 'tab-' + Application.idPaginaEdicao ).conteudoHtml = editorTexto.getValue();

            // salva o novo conteúdo da pagina
            salvarPagina( {
                id: Application.idPaginaEdicao,
                conteudoHtml: dadosPaginas.get( 'tab-' + Application.idPaginaEdicao ).conteudoHtml,
                titulo: dadosPaginas.get( 'tab-' + Application.idPaginaEdicao ).titulo
            }, true );

        }

    }


    /*
     * Editor de texto WYSIWYG
     */
    var editorTexto = new Ext.form.HtmlEditor({
        anchor: '100%',
        border: false,
        hideLabel: true,
        disabled: true,
        plugins: [
            new Ext.ux.form.HtmlEditorCog.IndentOutdent(),
            new Ext.ux.form.HtmlEditorCog.SubSuperScript(),
            new Ext.ux.form.HtmlEditorCog.Divider(),
            new Ext.ux.form.HtmlEditorCog.Imagem(),
            new Ext.ux.form.HtmlEditorCog.Video(),
            new Ext.ux.form.HtmlEditorCog.Som(),
            new Ext.ux.form.HtmlEditorCog.Divider(),
            new Ext.ux.form.HtmlEditorCog.Table(),
            new Ext.ux.form.HtmlEditorCog.HR(),
            new Ext.ux.form.HtmlEditorCog.Divider(),
            new Ext.ux.form.HtmlEditorCog.SpecialCharacters(),
            new Ext.ux.form.HtmlEditorCog.Divider(),
            new Ext.ux.form.HtmlEditorCog.Word(),
            new Ext.ux.form.HtmlEditorCog.RemoveFormat()
        ]
    });

    /*
     * Viewpor onde todos os componentes são inseridos
     */
    var viewport = new Ext.Viewport({
        layout: 'border',
        renderTo: Ext.getBody(),
        items: [{
            region: 'north',
            xtype: 'panel',
            height: 56,
            items: [
                menu,
                barraFerramentas
            ]
        }, {
            region: 'west',
            xtype: 'panel',
            layout: 'border',
            split: true,
            collapseMode: 'mini',
            width: 250,
            maxSize: 250,
            items: [{
                region: 'center',
                xtype: 'panel',
                layout: 'border',
                title: Application.i18n.getMsg( 'estrutura' ),
                height: 300,
                items: [{
                    region: 'west',
                    xtype: 'panel',
                    border: false,
                    bodyStyle: 'padding: 4px;',
                    width: 30,
                    bodyCssClass: 'fundoBarraBotoesEstrutura',
                    items: [
                        btnConceito,
                        btnNovoGrupo,
                        separador.cloneConfig( {id: 'sepBtnEstrCon', hidden: true} ),
                        btnNovaPagina,
                        separador.cloneConfig( {id: 'sepBtnExcl', hidden: true, height: 12} ),
                        btnExcluirNo,
                        separador.cloneConfig( {id: 'sepBtnExpCont', hidden: true, height: 12} ),
                        btnExpandirEstrutura,
                        separador.cloneConfig(),
                        btnContrairEstrutura
                    ]
                },
                    estruturaMaterial
                ]
            }, {
                region: 'south',
                xtype: 'panel',
                layout: 'border',
                title: Application.i18n.getMsg( 'latEsquerda.objPagina' ),
                height: 250,
                items: [{
                    region: 'west',
                    xtype: 'panel',
                    border: false,
                    bodyStyle: 'padding: 4px;',
                    width: 30,
                    bodyCssClass: 'fundoBarraBotoesEstrutura',
                    items: [
                        btnExpandirObjetosPagina,
                        separador.cloneConfig(),
                        btnContrairObjetosPagina
                    ]
                },
                    objetosPagina
                ]
            }],
            listeners: {
                collapse: function( panel ) {
                    //abasPaginas.setWidth( viewport.getWidth() - 7 );
                    //editorTexto.setWidth( viewport.getWidth() - 7 );
                },
                expand: function( panel ) {
                    //abasPaginas.setWidth( viewport.getWidth() - panel.getWidth() - 7 );
                    //editorTexto.setWidth( viewport.getWidth() - panel.getWidth() - 7 );
                }
            }
        }, {
            region: 'center',
            layout: 'anchor',
            xtype: 'panel',
            items: [
                abasPaginas,
                editorTexto
            ]
        }, {
            region: 'south',
            xtype: 'panel',
            bbar: barraStatus
        }]
    });

    /*
     * Função para ligar/desligar os botões da interface
     */
    Application.ligarDesligarBotoes = function( ligar ){

        var componentes = [
            Ext.getCmp( 'btnMenuArquivoSalvar' ),
            Ext.getCmp( 'btnMenuArquivoSalvarTudo' ),
            Ext.getCmp( 'btnMenuArquivoFechar' ),
            Ext.getCmp( 'btnMenuExportacaoSCORM' ),
            Ext.getCmp( 'btnMenuExportacaoHTML' ),
            Ext.getCmp( 'btnBarraSalvar' ),
            Ext.getCmp( 'btnBarraSalvarTudo' ),
            Ext.getCmp( 'btnBarraSCORM' ),
            Ext.getCmp( 'btnBarraHTML' ),
            Ext.getCmp( 'btnBarraPropriedades' ),
            Ext.getCmp( 'btnBarraMetadados' ),
            Ext.getCmp( 'btnBarraAnalogias' ),
            btnConceito,
            btnNovaPagina,
            btnNovoGrupo,
            btnExcluirNo,
            btnExpandirEstrutura,
            btnContrairEstrutura,
            btnExpandirObjetosPagina,
            btnContrairObjetosPagina
        ];

        if ( ligar == Global.LIGAR )
            for ( var i = 0; i < componentes.length; i++ )
                componentes[i].enable();
        else if ( ligar == Global.DESLIGAR )
            for ( i = 0; i < componentes.length; i++ )
                componentes[i].disable();

    };

    /*
     * Limpa as árvores
     */
    var resetarArvores = function() {
        // não dá p/ reaproveitar um nó vazio, é preciso sempre criar um novo.
        estruturaMaterial.setRootNode(
            new Ext.tree.AsyncTreeNode({
                text: Application.i18n.getMsg( 'raizVazia' ),
                icon: Application.contextPath + '/imagens/icones/book_open.png',
                leaf: true
            })
        );
        Application.carregarObjetosPagina( null );
    };

    /*
     * Handler para abrir a janela de novo material
     */
    var novoMaterial = function( btn, evt ) {
        abrirJanelaNovoMaterial();
    };

    /*
     * Handler para abrir a janela de abrir materiais
     */
    var abrirMaterial = function( btn, evt ) {
        abrirJanelaAbrirMaterial();
    };

    /*
     * Handler para salvar um material.
     */
    var salvarMaterial = function( btn, evt, asincrono ) {

        barraStatus.setText( Application.i18n.getMsg( 'barraStatus.salvandoMaterial' ) );

        dadosPaginas.each( function( item, index, length ){
            if ( !item.salvo ) {
                salvarPagina( item, asincrono );
            }
        });

        barraStatus.setText( Application.i18n.getMsg( 'barraStatus.pronto' ) );

        materialEdicaoSalvo = true;

    };

    /*
     * Handler para fechar um material ativo
     */
    var fecharMaterial = function( btn, evt ) {

        if ( !materialEdicaoSalvo ) {
            Ext.Msg.confirm(
                Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                Application.i18n.getMsg( 'dialogo.desejaSalvar' ),
                function( btnId ) {
                    if ( btnId == 'yes' ) {
                        salvarMaterial( btn, evt, true );
                    }
                    Application.idMaterialEdicao = undefined;
                    materialEdicaoSalvo = true;
                    Application.ligarDesligarBotoes( Global.DESLIGAR );

                    btnConceito.setVisible( false );
                    btnNovoGrupo.setVisible( false );
                    Ext.getCmp( 'sepBtnEstrCon' ).setVisible( false );
                    Ext.getCmp( 'sepBtnExcl' ).setVisible( false );
                    btnExcluirNo.setVisible( false );
                    Ext.getCmp( 'sepBtnExpCont' ).setVisible( false );
                    btnNovaPagina.setVisible( false );
                    
                    abasPaginas.items.each( function( item ) {
                        abasPaginas.remove( item );
                    });

                    resetarArvores();
                    btn.hideMenu();
                }
            );
        } else {
            Application.idMaterialEdicao = undefined;
            materialEdicaoSalvo = true;
            Application.ligarDesligarBotoes( Global.DESLIGAR );

            btnConceito.setVisible( false );
            btnNovoGrupo.setVisible( false );
            Ext.getCmp( 'sepBtnEstrCon' ).setVisible( false );
            Ext.getCmp( 'sepBtnExcl' ).setVisible( false );
            btnExcluirNo.setVisible( false );
            Ext.getCmp( 'sepBtnExpCont' ).setVisible( false );
            btnNovaPagina.setVisible( false );

            abasPaginas.items.each( function( item ) {
                abasPaginas.remove( item );
            });

            resetarArvores();
            btn.hideMenu();
        }
        
    };

    /*
     * Handler para gerenciar materiais
     */
    var excluirMateriais = function( btn, evt ) {
        abrirJanelaExcluirMateriais();
    };

    /*
     * Handler para sair da aplicação
     */
    var sair = function( btn, evt ) {

        Ext.Msg.confirm(
            Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
            Application.i18n.getMsg( 'dialogo.desejaSair' ),
            function( btnId ) {
                if ( btnId == 'yes' ) {
                    if ( !materialEdicaoSalvo ) {
                        Ext.Msg.confirm(
                            Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                            Application.i18n.getMsg( 'dialogo.desejaSalvar' ),
                            function( btnId ) {
                                if ( btnId == 'yes' ) {
                                    salvarMaterial( btn, evt, false );
                                }
                                Uteis.fazerLogoff();
                            }
                        );
                    } else {
                        Uteis.fazerLogoff();
                    }
                }
            }
        );
    };


    /*
     * Função para inserir um nó da árvore.
     */
    var inserirNo = function( tree, no, tipo, icone ) {

        if ( tipo == 'grupo' && !no ) {
            no = estruturaMaterial.getRootNode();
        }

        if ( no ) {

            if ( Uteis.quantidadeMateriaisPercurso( no, tree.getRootNode() ) == 0 ) {
                
                var form = new Ext.FormPanel({
                    url: Application.contextPath + '/servlets/CriarOAServlet',
                    border: false,
                    bodyStyle: 'padding: 4px;',
                    labelAlign: 'right',
                    items: [{
                        xtype: 'hidden',
                        name: 'acao',
                        value: 'criar-' + tipo
                    }, {
                        xtype: 'hidden',
                        name: 'icone',
                        value: icone
                    }, {
                        xtype: 'hidden',
                        name: 'material',
                        value: tree.getRootNode().attributes.idInterno
                    }, {
                        xtype: 'textfield',
                        allowBlank: false,
                        fieldLabel: Application.i18n.getMsg( 'titulo' ),
                        id: 'fieldTituloEditorPropriedades',
                        name: 'titulo',
                        maxLength: 100,
                        blankText: Application.i18n.getMsg( 'vtype.blank' ),
                        maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                        width: 150
                    }]
                });

                var janela = new Ext.Window({
                    modal: true,
                    items: [
                        form
                    ],
                    fbar: [{
                        text: Application.i18n.getMsg( 'salvar' ),
                        handler: function( btn, evt ) {
                            form.getForm().submit({
                                success: function( form, action ) {

                                    var r = action.result;

                                    var novoNo = new Ext.tree.TreeNode({
                                        idInterno: r.idInterno,
                                        ordem: r.ordem,
                                        compartilhado: r.compartilhado,
                                        principal: r.principal,
                                        tipo: r.tipo,
                                        text: r.text,
                                        icon: r.icon,
                                        leaf: r.leaf
                                    });

                                    no.appendChild( novoNo );
                                    janela.close();

                                },
                                failure: function( form, action ) {
                                    Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                                },
                                waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                                waitMsg: Application.i18n.getMsg( 'salvando' )
                            });
                        }
                    }, {
                        text: Application.i18n.getMsg( 'cancelar' ),
                        handler: function( btn, evt ) {
                            janela.close();
                        }
                    }],
                    listeners: {
                        close: function( panel ) {
                            inserindoNo = false;
                        }
                    }
                });

                if ( ( ( tipo == 'grupo' || tipo == 'pagina' ) && no.attributes.tipo == 'grupo' ) ||
                     ( tipo == 'grupo' && no.attributes.tipo == 'material' )) {

                    inserindoNo = true;

                    if ( tipo == 'pagina' ) {
                        janela.setTitle( Application.i18n.getMsg( 'novaPagina' ), 'iconeNovaPagina' );
                    } else if ( tipo == 'grupo' ) {
                        janela.setTitle( Application.i18n.getMsg( 'novoGrupo' ), 'iconeNovoGrupo' );
                    }

                    janela.show();
                    janela.center();

                } else {

                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneGrup' ) );

                }

            } else {
                
                Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneGrupMatPrin' ) );

            }

        } else {
            
            Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneGrup' ) );

        }

    };


    /*
     * Função para remover um nó da árvore.
     */
    var excluirNo = function( tree, no ) {

        if ( no ) {

            Ext.Msg.confirm(
                Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                Application.i18n.getMsg( 'dialogo.desejaExcluirItem' ),
                function( btnId ) {
                    if ( btnId == 'yes' ) {

                        var tipo = no.attributes.tipo;

                        switch ( tipo ) {

                            case 'material': {
                                if ( no.id == tree.getRootNode().id ) {
                                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.excluirRaiz' ) );

                                    /* Caso seja um material dentro de um submaterial
                                     * a remoção não pode ser feita, pois a estrutura
                                     * de um submaterial não pode ser mudada.
                                     */
                                } else if ( Uteis.quantidadeMateriaisPercurso( no, tree.getRootNode() ) > 1 ) {
                                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.excluirItemSubMaterial' ) );
                                } else {
                                    no.remove();
                                }
                                break;
                            }

                            case 'conceito': {
                                /*
                                 * Verifica se o conceito é do material ou de um material
                                 * utilizado na estrutura.
                                 */
                                if ( Uteis.materialEntreNoERaiz( no, tree.getRootNode() ) ) {
                                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.excluirItemSubMaterial' ) );
                                } else {
                                    no.remove();
                                }
                                break;
                            }

                            case 'grupo': {
                                if ( Uteis.materialEntreNoERaiz( no, tree.getRootNode() ) ) {
                                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.excluirItemSubMaterial' ) );
                                } else {
                                    no.remove();
                                }
                                break;
                            }

                            case 'pagina': {
                                if ( Uteis.materialEntreNoERaiz( no, tree.getRootNode() ) ) {
                                    Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.excluirItemSubMaterial' ) );
                                } else {
                                    if ( no.attributes.principal ) {
                                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.excluirPaginaPrincipal' ) );
                                    } else {
                                        // fecha a aba
                                        var aba = abasPaginas.findById( 'tab-' + no.attributes.idInterno );
                                        abasPaginas.remove( aba );
                                        no.remove();
                                        Application.carregarObjetosPagina( null );
                                    }
                                }
                                break;
                            }

                            case 'imagem':
                            case 'som':
                            case 'video':
                                no.remove();

                                // remove a imagem do editor
                                var id = tipo + '-' +
                                        Application.idMaterialEdicao + '-' +
                                        Application.idPaginaEdicao + '-' +
                                        no.attributes.idInterno

                                var novoTexto = Uteis.removerMidia( editorTexto.getValue(), id, tipo );
                                dadosPaginas.get( 'tab-' + Application.idPaginaEdicao ).conteudoHtml = novoTexto;
                                editorTexto.setValue( novoTexto );

                                // salva o novo conteúdo da pagina
                                salvarPagina( {
                                    id: Application.idPaginaEdicao,
                                    conteudoHtml: novoTexto,
                                    titulo: dadosPaginas.get( 'tab-' + Application.idPaginaEdicao ).titulo
                                }, true );

                                break;

                        }
                    }
                }
            );

        } else {
            Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecione' ) );
        }

    };


    /*
     * Altera o título de um nó e suas abas.
     * Se novoValor não for especificado, será utilizado o valor de
     * no.text
     */
    var alterarTituloAba = function( no, novoValor ) {

        if ( novoValor ) {
            no.text = novoValor;
        }

        var dados = dadosPaginas.get( 'tab-' + no.attributes.idInterno );
        var titulo = no.text;

        if ( dados ) {
            dados.titulo = titulo;
            if ( !dados.salvo ) {
                titulo = '* ' + no.text;
            }
        }

        if ( no.attributes.tipo == 'pagina' ) {

            try {
                abasPaginas.findById(
                    'tab-' + no.attributes.idInterno ).setTitle(
                    Ext.util.Format.ellipsis( titulo, 22 ) );
            } catch ( erro ) {}

            if ( no.attributes.principal ) {
                no.parentNode.setText( no.text );
            }

        } else if ( no.attributes.tipo == 'conceito' ) {

            var nos = no.childNodes;
            for ( var i = 0; i < nos.length; i++ ) {
                if ( nos[i].attributes.principal ) {
                    nos[i].setText( no.text );

                    try {
                        abasPaginas.findById(
                            'tab-' + nos[i].attributes.idInterno ).setTitle(
                            Ext.util.Format.ellipsis( titulo, 22 ) );
                    } catch ( erro ) {}

                }
            }

        }

    };


    /*
     * Função para abrir a janela para criar um novo material
     */
    var abrirJanelaNovoMaterial = function() {

        var form = new Ext.FormPanel({
            url: Application.contextPath + '/servlets/MaterialServlet',
            bodyStyle: 'padding: 10px;',
            border: false,
            labelAlign: 'right',
            labelWidth: 180,
            items: [{
                xtype: 'hidden',
                name: 'acao',
                value: 'novo'
            }, {
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
                id: 'comboEstrConNovoMat',
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
                    separadorBranco.cloneConfig(),
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
                    separadorBranco.cloneConfig(),
                {
                    xtype: 'panel',
                    width: 104,
                    height: 104,
                    border: false,
                    bodyCssClass: 'layoutComBarra'
                }]
            }]
        });

        var janela = new Ext.Window({
            modal: true,
            resizable: false,
            iconCls: 'iconeNovo',
            title: Application.i18n.getMsg( 'janelaNovoMaterial.titulo' ),
            items: [
                form
            ],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    form.getForm().submit({
                        success: function( form, action ) {

                            // verifica se o material atual esta salvo
                            if ( !materialEdicaoSalvo ) {

                                Ext.Msg.confirm(
                                    Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                                    Application.i18n.getMsg( 'dialogo.desejaSalvar' ),
                                    function( btnId ) {
                                        if ( btnId == 'yes' ) {
                                            salvarMaterial( btn, evt, true );
                                        }

                                        // fecha as abas
                                        abasPaginas.items.each( function( item ) {
                                            abasPaginas.remove( item );
                                        });

                                        // reseta o buffer
                                        dadosPaginas = new Ext.util.MixedCollection();

                                        // seta o id do material sendo editado
                                        Application.idMaterialEdicao = action.result.idMaterialGerado;
                                        materialEdicaoSalvo = true;

                                        // faz a carga do mapa de conceitos
                                        Application.carregarEstrutura();

                                        // fecha essa janela
                                        janela.close();
                                        
                                    }
                                );

                            } else {

                                // fecha as abas
                                abasPaginas.items.each( function( item ) {
                                    abasPaginas.remove( item );
                                });

                                // reseta o buffer
                                dadosPaginas = new Ext.util.MixedCollection();

                                // seta o id do material sendo editado
                                Application.idMaterialEdicao = action.result.idMaterialGerado;
                                materialEdicaoSalvo = true;

                                // faz a carga do mapa de conceitos
                                Application.carregarEstrutura();

                                // fecha essa janela
                                janela.close();

                            }

                        },
                        failure: function( form, action ) {
                            Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                        },
                        waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                        waitMsg: Application.i18n.getMsg( 'criandoMaterial' )
                    });

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
        Ext.getCmp( 'comboEstrConNovoMat' ).setValue( true );
        
    };

    /*
     * Função para criar a janela de abrir material existente
     */
    var abrirJanelaAbrirMaterial = function() {

        var idMaterialSelecionado = undefined;

        var panel = new Ext.Panel({
            bodyStyle: 'padding: 10px;',
            border: false,
            labelAlign: 'right',
            layout: 'form',
            items: [{
                xtype: 'fieldset',
                title: Application.i18n.getMsg( 'materiaisDisponiveis' ),
                html: Uteis.gerarListaMateriais( 6 )
            }],
            listeners: {
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
        });

        var janela = new Ext.Window({
            modal: true,
            resizable: false,
            iconCls: 'iconeAbrir',
            title: Application.i18n.getMsg( 'janelaAbrirMaterial.titulo' ),
            items: [
                panel
            ],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    if ( idMaterialSelecionado ) {

                        // extrai o id da string
                        var id = idMaterialSelecionado.substring(
                            idMaterialSelecionado.indexOf( '-' ) + 1,
                            idMaterialSelecionado.length
                        );

                        if ( id != Application.idMaterialEdicao ) {

                            if ( !materialEdicaoSalvo ) {

                                Ext.Msg.confirm(
                                    Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                                    Application.i18n.getMsg( 'dialogo.desejaSalvar' ),
                                    function( btnId ) {
                                        if ( btnId == 'yes' ) {
                                            salvarMaterial( btn, evt, true );
                                        }

                                        // fecha as abas
                                        abasPaginas.items.each( function( item ) {
                                            abasPaginas.remove( item );
                                        });

                                        // reseta o buffer
                                        dadosPaginas = new Ext.util.MixedCollection();

                                        // seta o id do material sendo editado
                                        Application.idMaterialEdicao = Number( id );
                                        materialEdicaoSalvo = true;

                                        // faz a carga do mapa de conceitos
                                        Application.carregarEstrutura();

                                        // fecha essa janela
                                        janela.close();

                                    }
                                );

                            } else {

                                // fecha as abas
                                abasPaginas.items.each( function( item ) {
                                    abasPaginas.remove( item );
                                });

                                // reseta o buffer
                                dadosPaginas = new Ext.util.MixedCollection();

                                // seta o id do material sendo editado
                                Application.idMaterialEdicao = Number( id );
                                materialEdicaoSalvo = true;

                                // faz a carga do mapa de conceitos
                                Application.carregarEstrutura();

                                // fecha essa janela
                                janela.close();

                            }

                        } else {
                            Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.materialJaAberto' ) );
                        }

                    } else {

                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneMaterialAbrir' ) );

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
     * Função para criar a janela de gerenciar materiais.
     */
    var abrirJanelaExcluirMateriais = function() {

        var idMaterialSelecionado = undefined;

        // conta as instancias dos fieldsets
        var cont = 0;

        var form = new Ext.form.FormPanel({
            url: Application.contextPath + '/servlets/MaterialServlet',
            border: false,
            items: [{
                xtype: 'hidden',
                name: 'acao',
                value: 'excluir'
            }, {
                xtype: 'hidden',
                name: 'id',
                id: 'fieldIdMaterialExcluir'
            }]
        });

        var panel = new Ext.Panel({
            bodyStyle: 'padding: 10px;',
            border: false,
            labelAlign: 'right',
            layout: 'form',
            items: [{
                xtype: 'fieldset',
                title: Application.i18n.getMsg( 'materiaisDisponiveis' ),
                id: 'fieldsetMaterialExcluir' + cont,
                html: Uteis.gerarListaMateriais( 6 )
            }, form ],
            listeners: {
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
        });

        var janela = new Ext.Window({
            modal: true,
            resizable: false,
            iconCls: 'iconeExcluirMateriais',
            title: Application.i18n.getMsg( 'janelaExcluirMaterial.titulo' ),
            items: [
                panel
            ],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'excluir' ),
                handler: function( btn, evt ) {

                    if ( idMaterialSelecionado ) {

                        // extrai o id da string
                        var id = idMaterialSelecionado.substring(
                            idMaterialSelecionado.indexOf( '-' ) + 1,
                            idMaterialSelecionado.length
                        );

                        if ( id != Application.idMaterialEdicao ) {

                            // remove
                            Ext.Msg.confirm(
                                Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                                Application.i18n.getMsg( 'dialogo.desejaExcluirMaterial' ),
                                function( btnId ) {
                                    if ( btnId == 'yes' ) {

                                        Ext.getCmp( 'fieldIdMaterialExcluir' ).setValue( id );

                                        form.getForm().submit({
                                            success: function( form, action ) {

                                                cont++;

                                                var fieldSet = new Ext.form.FieldSet({
                                                    xtype: 'fieldset',
                                                    title: Application.i18n.getMsg( 'materiaisDisponiveis' ),
                                                    id: 'fieldsetMaterialExcluir' + cont,
                                                    html: Uteis.gerarListaMateriais( 6 )
                                                });

                                                panel.remove( 'fieldsetMaterialExcluir' + ( cont - 1 ) );
                                                panel.add( fieldSet );
                                                panel.doLayout();

                                                // verifica se o material que vai ser excluido é o que está aberto
                                                if ( id == estruturaMaterial.getRootNode().attributes.idInterno ) {
                                                    Application.idMaterialEdicao = undefined;
                                                    materialEdicaoSalvo = true;
                                                    Application.ligarDesligarBotoes( Global.DESLIGAR );
                                                    resetarArvores();
                                                }


                                            },
                                            failure: function( form, action ) {
                                                Uteis.exibirMensagemErro( Uteis.criarMensagemErro( action, Application.i18n ) );
                                            },
                                            waitTitle: Application.i18n.getMsg( 'favorAguarde' ),
                                            waitMsg: Application.i18n.getMsg( 'excluindo' )
                                        });

                                    }
                                }
                            );

                        } else {
                            Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.materialJaAbertoExcluir' ) );
                        }

                    } else {
                        Uteis.exibirMensagemErro( Application.i18n.getMsg( 'dialogo.erro.selecioneMaterialExcluir' ) );
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
     * Função para executar a exportação.
     */
    var exportarMaterial = function( tipo ) {

        window.open(
                Application.contextPath +
                '/servlets/ExportacaoMaterialServlet?idMaterial=' + Application.idMaterialEdicao +
                '&tipo=' + tipo,
                '_blank' );

    }

    /*
     * Prepara o editor de texto quando abre o ambiente, setando a sua altura.
     */
    Application.prepararEditorTexto = function() {
        var t = viewport.getHeight() - ( 56 + barraStatus.getHeight() + 4 );
        editorTexto.setHeight( t );
    };

};

/*
 * Inicia a aplicação
 */
$( document ).ready( function() {

    /*
     * Caso seja usado os arquivos de internacionalização
     * O uso seria Application.i18n.getMsg( 'chave' )
     */
    var bundle = new Ext.i18n.Bundle({
        bundle: 'TabelaStringsApplication',
        path: Application.contextPath + '/i18n',
        lang: Uteis.getIdioma(),
        resourceExt: '.jsp'
    });

    // quando o bundle está pronto, continua a execução
    bundle.onReady( function(){
        
        // armazena strings de internacionalização
        Application.i18n = bundle;

        /*
         * Carrega as permissões de forma síncrona
         * O utilitário de obtenção de permissões só pode ser
         * usado após essa carga. Note que esses componentes estão
         * altamente acoplados.
         */
        $.ajax({
            type: 'post',
            url: Application.contextPath + '/ajax/permissoes/permissoes.jsp',
            async: false,
            dataType: 'json',
            cache: false,
            success: function( data, textStatus ) {
                Application.permissoes = data;
            }
        });

        // inicia os componentes
        VTypes.initComponents();
        Application.initComponents();
        Usuarios.initComponents();
        TelaInicial.initComponents();
        DadosPessoais.initComponents();
        Configs.initComponents();
        Metadados.initComponents();
        EstrCon.initComponents();
        Analogias.initComponents();
        
        $('#loading').fadeOut();
        $('#loading-mask').fadeOut();

        // evita timeout executando a função a cada 2 minutos
        Ext.TaskMgr.start({
            run: Uteis.evitarTimeout,
            interval: 120000
        });

        // prepara a interface
        Application.ligarDesligarBotoes( Global.DESLIGAR );
        Application.carregarObjetosPagina( null );

        Application.prepararEditorTexto();

        TelaInicial.abrir();

    });

});
