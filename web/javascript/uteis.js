/**
 * Funções utilitárias
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      02 de Setembro de 2009
 *
 */

/**
 * Obtém o idioma do navegador.
 */
Uteis.getIdioma = function() {

    var idioma = navigator.language || navigator.userLanguage;
    idioma = idioma.substring( 0, 2 );

    if ( idioma != 'pt' )
        idioma = 'en';

    return idioma;

};

/**
 * Evita o timeout da sessão configurada.
 */
Uteis.evitarTimeout = function() {
    
    $.ajax( {
        url: Application.contextPath + '/dummy.jsp',
        dataType: 'html',
        success: function( data ) {
        }
    });

};

/**
 * Cria a mensagem de erro para ser apresentada.
 * Caso sejam passados os dados diretamente em action (quando se usa o jquery)
 * o switch vai ir direto para o default.
 */
Uteis.criarMensagemErro = function( action, i18n ) {

    var errorData = undefined;
    var mensagem = '';

    switch ( action.failureType ) {

        case Ext.form.Action.CLIENT_INVALID:
            return i18n.getMsg( 'dialogo.erro.camposInvalidos' );
            break;

        case Ext.form.Action.SERVER_INVALID:
            return i18n.getMsg( 'dialogo.erro.server' );
            break;

        case Ext.form.Action.CONNECT_FAILURE:
            return i18n.getMsg( 'dialogo.erro.ajax' );
            break;

        case Ext.form.Action.LOAD_FAILURE:
            return i18n.getMsg( 'dialogo.erro.load' );
            break;

        default:
            if ( action.result )
                errorData = action.result;
            else
                errorData = action;
            mensagem = i18n.getMsg( 'dialogo.erro.mensagem' ) + '<br/>' + errorData.errorMsg;
            if ( errorData.debug ) {
                mensagem += "<br/><br/>StackTrace:<br/>" + errorData.stackTrace;
            }
            return mensagem;
            break;
            
    }

};

/*
 * Retorna true caso um tipo de usuário possa visualizar certa
 * feature do sistema. Note que essa validação pode ser burlada facilmente
 * por usuários que conheçam javascript, entratanto existe validação
 * no lado do servidor. Caso alguém tente fazer algo ilegal não será
 * permitido.
 *
 * Obs: Note que é necessára a carga inicial das configurações na inicialização
 * da aplicação.
 *
 */
Uteis.ehPermitido = function( feature ) {

    // Application.permissoes é o objeto xmlhttp completo
    var p = Application.permissoes.permissoes;
    var tipo = Application.permissoes.tipoUsuario;

    for ( var i = 0; i < p.length; i++ )
        if ( p[i].feature == feature )
            return p[i][tipo];

    return false;

};

/*
 * Cria a lista de materiais do usuário logado.
 * O registro de eventos nos itens da lista (tabela) é feito pela
 * janela que usa essa função.
 */
Uteis.gerarListaMateriais = function( quantidadeLinhas ) {

    var valor = '<div class="divMateriaisDisponiveis"><table id="tabelaMateriaisDisponiveis" class="tabelaMateriaisDisponiveis">';

    $.ajax( {
        async: false,
        dataType: 'json',
        cache: false,
        type: 'post',
        url: Application.contextPath + '/ajax/materiais/materiaisPorUsuarioLogado.jsp',
        success: function( data, textStatus ) {

            var materiais = data.materiais;
            var linhas = [];

            for ( var i = 0; i < quantidadeLinhas; i++ ) {
                linhas[i] = '<tr>';
            }

            for ( i = 0; i < materiais.length; i++ ) {

                var j = i % linhas.length;

                linhas[ j ] += '<td>' +
                        '<div class="materialDisponivel" id="material-' + materiais[i].id + '">' +
                        '<img src="' + Application.contextPath;
                if ( materiais[i].compartilhado )
                    linhas[ j ] += '/imagens/icones/book_go.png" /> ';
                else
                    linhas[ j ] += '/imagens/icones/book_key.png" /> ';

                linhas[ j ] += Ext.util.Format.ellipsis( materiais[i].titulo, 30 ) + '</div></td>';
            }

            for ( i = 0; i < linhas.length; i++ ) {
                valor += linhas[i] + '</tr>';
            }

        }
    });

    valor += '</table></div>';

    return valor;

};


/*
 * Cria a lista de imagens de um material
 * O registro de eventos nos itens da lista (tabela) é feito pela
 * janela que usa essa função.
 */
Uteis.gerarListaImagens = function( quantidadeLinhas, idMaterial ) {

    var valor = '<div class="divImagensDisponiveis"><table id="tabelaImagensDisponiveis" class="tabelaImagensDisponiveis">';

    $.ajax( {
        async: false,
        dataType: 'json',
        cache: false,
        type: 'post',
        url: Application.contextPath + '/ajax/imagens/imagensDeMaterialPorId.jsp',
        data: {
            idMaterial: idMaterial
        },
        success: function( data, textStatus ) {

            var imagens = data.imagens;
            var linhas = [];

            for ( var i = 0; i < quantidadeLinhas; i++ ) {
                linhas[i] = '<tr>';
            }

            for ( i = 0; i < imagens.length; i++ ) {

                var j = i % linhas.length;

                linhas[ j ] += '<td>' +
                        '<div class="imagemDisponivel" id="imagem-' + imagens[i].nome + '">' +
                        '<img src="' + Application.contextPath + '/imagens/icones/picture.png" /> ';

                linhas[ j ] += Ext.util.Format.ellipsis( imagens[i].nome, 30 ) + '</div></td>';
            }

            for ( i = 0; i < linhas.length; i++ ) {
                valor += linhas[i] + '</tr>';
            }

        }
    });

    valor += '</table></div>';

    return valor;

};


/*
 * Cria a lista de videos de um material
 * O registro de eventos nos itens da lista (tabela) é feito pela
 * janela que usa essa função.
 */
Uteis.gerarListaVideos = function( quantidadeLinhas, idMaterial ) {

    var valor = '<div class="divVideosDisponiveis"><table id="tabelaVideosDisponiveis" class="tabelaVideosDisponiveis">';

    $.ajax( {
        async: false,
        dataType: 'json',
        cache: false,
        type: 'post',
        url: Application.contextPath + '/ajax/videos/videosDeMaterialPorId.jsp',
        data: {
            idMaterial: idMaterial
        },
        success: function( data, textStatus ) {

            var videos = data.videos;
            var linhas = [];

            for ( var i = 0; i < quantidadeLinhas; i++ ) {
                linhas[i] = '<tr>';
            }

            for ( i = 0; i < videos.length; i++ ) {

                var j = i % linhas.length;

                linhas[ j ] += '<td>' +
                        '<div class="videoDisponivel" id="video-' + videos[i].nome + '">' +
                        '<img src="' + Application.contextPath + '/imagens/icones/film.png" /> ';

                linhas[ j ] += Ext.util.Format.ellipsis( videos[i].nome, 30 ) + '</div></td>';
            }

            for ( i = 0; i < linhas.length; i++ ) {
                valor += linhas[i] + '</tr>';
            }

        }
    });

    valor += '</table></div>';

    return valor;

};


/*
 * Cria a lista de sons de um material
 * O registro de eventos nos itens da lista (tabela) é feito pela
 * janela que usa essa função.
 */
Uteis.gerarListaSons = function( quantidadeLinhas, idMaterial ) {

    var valor = '<div class="divSonsDisponiveis"><table id="tabelaSonsDisponiveis" class="tabelaSonsDisponiveis">';

    $.ajax( {
        async: false,
        dataType: 'json',
        cache: false,
        type: 'post',
        url: Application.contextPath + '/ajax/sons/sonsDeMaterialPorId.jsp',
        data: {
            idMaterial: idMaterial
        },
        success: function( data, textStatus ) {

            var sons = data.sons;
            var linhas = [];

            for ( var i = 0; i < quantidadeLinhas; i++ ) {
                linhas[i] = '<tr>';
            }

            for ( i = 0; i < sons.length; i++ ) {

                var j = i % linhas.length;

                linhas[ j ] += '<td>' +
                        '<div class="somDisponivel" id="som-' + sons[i].nome + '">' +
                        '<img src="' + Application.contextPath + '/imagens/icones/music.png" /> ';

                linhas[ j ] += Ext.util.Format.ellipsis( sons[i].nome, 30 ) + '</div></td>';
            }

            for ( i = 0; i < linhas.length; i++ ) {
                valor += linhas[i] + '</tr>';
            }

        }
    });

    valor += '</table></div>';

    return valor;

};


/*
 * Cria e exibe uma mensagem de erro.
 */
Uteis.exibirMensagemErro = function( mensagem ) {
    Ext.Msg.show({
        title: Application.i18n.getMsg( 'dialogo.erro.titulo' ),
        msg: mensagem,
        buttons: Ext.Msg.OK,
        icon: Ext.Msg.ERROR
    });
}


/*
 * Cria e exibe uma mensagem de informação.
 */
Uteis.exibirMensagemInfo = function( mensagem ) {
    Ext.Msg.show({
        title: Application.i18n.getMsg( 'dialogo.info.titulo' ),
        msg: mensagem,
        buttons: Ext.Msg.OK,
        icon: Ext.Msg.INFO
    });
}

/*
 * Cria e exibe uma mensagem de aguarde. Retorna a referência para o diálogo
 */
Uteis.exibirMensagemAguarde = function( mensagem ) {
    return Ext.Msg.wait(
        mensagem,
        Application.i18n.getMsg( 'dialogo.aguarde.titulo' )
    ).getDialog();
}


/*
 * Gera a string que identifica o layout do material,
 */
Uteis.gerarLayoutMaterial = function( checkLinks, checkBarra ) {
    var layout = '';
    layout += checkLinks ? 'PCL' : 'PSL';
    layout += checkBarra ? 'CBN' : 'SBN';
    return layout;
};


/*
 * Função para reordenar os itens da árvore da estrutura do material.
 */
Uteis.reordenarItemsMaterial = function( tree, node, oldParent, newParent, index, exclusao ) {

    var nosop = oldParent.childNodes;
    var nosnp = newParent.childNodes;

    /*
     * Essa variável armazena os dados de reordenação que serão passados
     * no seguinte formato:
     *
     * tipoPai-tipoFilho:idPai-idFilho:ordem$tipoPai-tipoFilho:idPai-idFilho:ordem$...
     */
    var dados = '';
    var i = 0;

    var getIndice = function( no, nos ) {
        for ( var i = 0; i < nos.length; i++ ) {
            if ( nos[i] == no )
                return i;
        }
        return 0;
    }

    var debug = function( nos ) {
        var mensagem = '';
        for ( i = 0; i < nosnp.length; i++ ) {
            mensagem += nosnp[i].text + '\n';
        }
        alert( mensagem );
    }

    // se não é para reordenar a exclusão, executa os algoritmos
    if ( !exclusao ) {

        // reordena no mesmo nível
        if ( oldParent == newParent ) {

            // se o indice do nó for maior, é que a mudança é para cima
            var cima = getIndice( node, nosop ) > index ? true : false;
            var con = 0;

            node.attributes.ordem = index;

            if ( cima ) {

                con = index + 1;

                // reordena elementos
                for ( i = index; i < nosnp.length; i++ ) {
                    if ( nosnp[i] != node ) {
                        nosnp[i].attributes.ordem = con++;
                    }
                }

            } else {

                // reordena elementos
                for ( i = index; i < nosnp.length; i++ ) {
                    nosnp[i].attributes.ordem = i + 1;
                }

                var achou = false;

                for ( i = 0; i < nosnp.length; i++ ) {
                    if ( nosnp[i] == node ) {
                        achou = true;
                    }
                    if ( achou )
                        nosnp[i].attributes.ordem--;
                }

            }

            // extrai os dados
            for ( i = 0; i < nosnp.length; i++ ) {
                dados += newParent.attributes.tipo + '-' + nosnp[i].attributes.tipo + ':' +
                    newParent.attributes.idInterno + '-' + nosnp[i].attributes.idInterno + ':' +
                    nosnp[i].attributes.ordem + '$';
            }

        } else {

            var cont = 0;

            node.attributes.ordem = index;

            // reordena novo pai
            for ( i = index; i < nosnp.length; i++ ) {
                nosnp[i].attributes.ordem = i + 1;
            }

            // reordena pai antigo
            for ( i = 0; i < nosop.length; i++ ) {
                if ( nosop[i] != node ) {
                    nosop[i].attributes.ordem = cont++;
                }
            }

            // extrai os dados
            for ( i = 0; i < nosnp.length; i++ ) {
                dados += newParent.attributes.tipo + '-' + nosnp[i].attributes.tipo + ':' +
                    newParent.attributes.idInterno + '-' + nosnp[i].attributes.idInterno + ':' +
                    nosnp[i].attributes.ordem + '$';
            }

            dados += newParent.attributes.tipo + '-' + node.attributes.tipo + ':' +
                newParent.attributes.idInterno + '-' + node.attributes.idInterno + ':' +
                node.attributes.ordem + '$';

            for ( i = 0; i < nosop.length; i++ ) {
                if ( nosop[i] != node ) {
                    dados += oldParent.attributes.tipo + '-' + nosop[i].attributes.tipo + ':' +
                        oldParent.attributes.idInterno + '-' + nosop[i].attributes.idInterno + ':' +
                        nosop[i].attributes.ordem + '$';
                }
            }

        }

        // reordenar nós para a exclusão
    } else {

        var c = 0;

        for ( i = 0; i < nosnp.length; i++ ) {

            if ( ( node.attributes.idInterno + node.attributes.tipo ) !=
                ( nosnp[i].attributes.idInterno + nosnp[i].attributes.tipo ) ) {

                nosnp[i].attributes.ordem = c++;

                dados += newParent.attributes.tipo + '-' + nosnp[i].attributes.tipo + ':' +
                    newParent.attributes.idInterno + '-' + nosnp[i].attributes.idInterno + ':' +
                    nosnp[i].attributes.ordem + '$';

            }

        }

    }

    // altera a estrutura
    $.ajax( {
         async: false,
         dataType: 'json',
         type: 'post',
         url: Application.contextPath + '/servlets/AlteraEstruturaMaterialServlet',
         data: {
             acao: 'alterarOrdem',
             dados: dados,
             idMaterial: tree.getRootNode().attributes.idInterno
         },
         success: function( data, textStatus ) {
             return data.success;
         }
     });

};


/*
 * Função para reordenar os itens da árvore do mapa de conceitos.
 */
Uteis.reordenarItemsMapaConceitos = function( tree, node, oldParent, newParent, index, exclusao ) {

    var nosop = oldParent.childNodes;
    var nosnp = newParent.childNodes;
    var dados = '';
    var i = 0;

    var getIndice = function( no, nos ) {
        for ( var i = 0; i < nos.length; i++ ) {
            if ( nos[i] == no )
                return i;
        }
        return 0;
    }

    // se não é para reordenar a exclusão, executa os algoritmos
    if ( !exclusao ) {

        // reordena no mesmo nível
        if ( oldParent == newParent ) {

            // se o indice do nó for maior, é que a mudança é para cima
            var cima = getIndice( node, nosop ) > index ? true : false;
            var con = 0;

            node.attributes.ordem = index;

            if ( cima ) {

                con = index + 1;

                // reordena elementos
                for ( i = index; i < nosnp.length; i++ ) {
                    if ( nosnp[i] != node ) {
                        nosnp[i].attributes.ordem = con++;
                    }
                }

            } else {

                // reordena elementos
                for ( i = index; i < nosnp.length; i++ ) {
                    nosnp[i].attributes.ordem = i + 1;
                }

                var achou = false;

                for ( i = 0; i < nosnp.length; i++ ) {
                    if ( nosnp[i] == node ) {
                        achou = true;
                    }
                    if ( achou )
                        nosnp[i].attributes.ordem--;
                }

            }

            // extrai os dados
            for ( i = 0; i < nosnp.length; i++ ) {
                dados += newParent.attributes.tipo + '-' + nosnp[i].attributes.tipo + ':' +
                    newParent.attributes.idInterno + '-' + nosnp[i].attributes.idInterno + ':' +
                    nosnp[i].attributes.ordem + '$';
            }

        } else {

            var cont = 0;

            node.attributes.ordem = index;

            // reordena novo pai
            for ( i = index; i < nosnp.length; i++ ) {
                nosnp[i].attributes.ordem = i + 1;
            }

            // reordena pai antigo
            for ( i = 0; i < nosop.length; i++ ) {
                if ( nosop[i] != node ) {
                    nosop[i].attributes.ordem = cont++;
                }
            }

            // extrai os dados
            for ( i = 0; i < nosnp.length; i++ ) {
                dados += newParent.attributes.tipo + '-' + nosnp[i].attributes.tipo + ':' +
                    newParent.attributes.idInterno + '-' + nosnp[i].attributes.idInterno + ':' +
                    nosnp[i].attributes.ordem + '$';
            }

            dados += newParent.attributes.tipo + '-' + node.attributes.tipo + ':' +
                newParent.attributes.idInterno + '-' + node.attributes.idInterno + ':' +
                node.attributes.ordem + '$';

            for ( i = 0; i < nosop.length; i++ ) {
                if ( nosop[i] != node ) {
                    dados += oldParent.attributes.tipo + '-' + nosop[i].attributes.tipo + ':' +
                        oldParent.attributes.idInterno + '-' + nosop[i].attributes.idInterno + ':' +
                        nosop[i].attributes.ordem + '$';
                }
            }

        }

        // reordenar nós para a exclusão
    } else {

        var c = 0;

        for ( i = 0; i < nosnp.length; i++ ) {

            if ( ( node.attributes.idInterno + node.attributes.tipo ) !=
                ( nosnp[i].attributes.idInterno + nosnp[i].attributes.tipo ) ) {

                nosnp[i].attributes.ordem = c++;

                dados += newParent.attributes.tipo + '-' + nosnp[i].attributes.tipo + ':' +
                    newParent.attributes.idInterno + '-' + nosnp[i].attributes.idInterno + ':' +
                    nosnp[i].attributes.ordem + '$';

            }

        }

    }

};


/*
 * Função para executar o logoff.
 */
Uteis.fazerLogoff = function() {
    Ext.Ajax.request({
        url: Application.contextPath + '/servlets/LogoffServlet',
        success: function() {
            window.location = 'index.jsp';
        },
        failure: function() {
            window.location = 'index.jsp';
        }
    });
};


/*
 * Verifica se um determinado nó faz parte de um submaterial.
 * Quando isso é verdade, existe um material entre o material
 * principal e o nó que será verificado.
 */
Uteis.materialEntreNoERaiz = function( no, raiz ) {

    if ( no.attributes.tipo == 'material' ) {
        if ( no != raiz )
            return true;
        else
            return false;
    } else {
        return Uteis.materialEntreNoERaiz( no.parentNode, raiz );
    }

};


/*
 * Conta a quantidade de materiais no percurso de um nó até a raiz.
 * Não conta a raiz.
 */
Uteis.quantidadeMateriaisPercurso = function( no, raiz ) {

    if ( no != null ) {
        if ( no.attributes.tipo == 'material' ) {
            if ( no != raiz )
                return 1 + Uteis.quantidadeMateriaisPercurso( no.parentNode, raiz );
            else
                return 0;
        } else {
            return 0 + Uteis.quantidadeMateriaisPercurso( no.parentNode, raiz );
        }
    } else {
        return 0;
    }

};


/*
 * Verifica se um determinado nó tem o mesmo nome e o mesmo tipo de um nó da
 * mesma árvore.
 */
Uteis.existeNoIgual = function( no, raiz ) {
    
    var achou = false;

    raiz.cascade( function(){
        if ( String.toLowerCase( this.text ) == String.toLowerCase( no.text )
             && this.attributes.tipo == no.attributes.tipo ) {
            achou = true;
            return false;
        }
    });

    return achou;

};


/*
 * Retorna o nível de um material.
 * Caso 0, raiz.
 * Caso 1, nível 1.
 * Não vai mais a fundo na hierarquia, apenas verifica os primeiros níveis.
 */
Uteis.nivelMaterial = function( no ) {

    if ( no.attributes.tipo == 'material' ) {
        if ( !no.parentNode ) {
            return 0;
        } else if ( !no.parentNode.parentNode ) {
            return 1;
        } else if ( !no.parentNode.parentNode.parentNode ) {
            return 2;
        } else {
            return 0;
        }
    } else {
        return 0;
    }

};


/*
 * Preenche um lista com os conceitos do senso comum.
 */
Uteis.preencheDadosSensoComum = function( dados, lista ) {

    lista.getStore().removeAll();

    for ( var i = 0; i < dados.length; i++ ) {
        var novoConceito = [ new Ext.data.Record({
            conceito: dados[i].conceito,
            relacaoMinsky: dados[i].relacao
        },
            Ext.id()
        )];
        lista.getStore().add( novoConceito );
    }

};


/*
 * Copia dados de uma árvore para uma lista.
 */
Uteis.copiarConteudoArvore = function( arvore, lista ) {

    lista.clearSelections();
    lista.getStore().removeAll();

    arvore.getRootNode().cascade( function(){

        if ( this != arvore.getRootNode() ) {

            var novoConceito = [ new Ext.data.Record({
                id: this.attributes.idInterno,
                conceito: this.text
            },
                Ext.id()
            )];
            lista.getStore().add( novoConceito );
            
        }
        
    });

};


/*
 * Insere os relacionamentos de uma hiearaquia de conceitos no store fornecido.
 * Se a estrutura for nova, quer dizer que as relações de hierarquia e do mapa
 * (inseridas no nó) não devem ser processadas.
 *
 * Não insere o que estiver no bufferApagar
 *
 * Precisa refatorar esse método... muitos parâmetros...
 */
Uteis.gerarRelacionamentos = function( store, no, root, arvore, novaEstrutura, 
        bufferApagar, i18n ) {

    /*
     * Função para busca de dados iguais no store
     * retorna true caso tenha enontrado algo, false caso contrário.
     */
    var busca = function( idC1, idC2, tC1, tC2, rM, rU ) {

        var encontrou = false;

        store.each( function( campo ) {

            if ( campo.data.idInterno1 == idC1 &&
                 campo.data.idInterno2 == idC2 &&
                 campo.data.conceito1 == tC1 &&
                 campo.data.conceito2 == tC2 ) {

                 encontrou = true;

                 // para a iteração
                 return false;

            }

        });

        return encontrou;

    };


    if ( no.hasChildNodes() ) {

        var nos = no.childNodes;

        for ( var i = 0; i < nos.length; i++ ) {

            if ( no != root ) {

                var relUsu = '';

                if ( novaEstrutura ) {

                    // gera a relação do usuário baseado na relação de minsky
                    relUsu = i18n.getMsg( 'estrCon.rm.' + nos[i].attributes.relacaoMinsky );

                    store.add([
                        new Ext.data.Record({
                            idInterno1: no.attributes.idInterno,
                            conceito1: no.text,
                            relacaoMinsky: nos[i].attributes.relacaoMinsky,
                            relacaoUsuario: relUsu,
                            idInterno2: nos[i].attributes.idInterno,
                            conceito2: nos[i].text,
                            relHierarquia: true
                        },
                            Ext.id()
                        )
                    ]);

                } else {

                    /*
                     * Adiciona os relacionamentos existentes
                     */
                    var relacoesHierarquia = nos[i].attributes.relacoesHierarquia;
                    if ( relacoesHierarquia ) {
                        for ( var j = 0; j < relacoesHierarquia.length; j++ ) {

                            var noRelH = Uteis.buscarNoPorIdInterno( arvore,
                                    relacoesHierarquia[j].idInterno, 'conceito' );

                            // caso o nó ainda exista
                            if ( noRelH ) {

                                // caso esse não deva ser apagado
                                var achou = false;
                                bufferApagar.each( function( item, index, length ){
                                    if ( item.id == noRelH.attributes.idInterno ) {
                                        achou = true;
                                        return;
                                    }
                                });

                                if ( !achou ) {
                                    store.add([
                                        new Ext.data.Record({
                                            idInterno1: nos[i].attributes.idInterno,
                                            conceito1: nos[i].text,
                                            relacaoMinsky: relacoesHierarquia[j].relacaoMinsky,
                                            relacaoUsuario: relacoesHierarquia[j].relacaoUsuario,
                                            idInterno2: noRelH.attributes.idInterno,
                                            conceito2: noRelH.text,
                                            relHierarquia: true
                                        },
                                            Ext.id()
                                        )
                                    ]);
                                }
                            }
                        }
                    }

                    // relacionamento em níve de mapeamento
                    var relacoesMapa = nos[i].attributes.relacoesMapa;
                    if ( relacoesMapa ) {
                        for ( var k = 0; k < relacoesMapa.length; k++ ) {

                            var noRelM = Uteis.buscarNoPorIdInterno( arvore,
                                    relacoesMapa[k].idInterno, 'conceito' );

                            // caso o nó ainda exista
                            if ( noRelM ) {
                                store.add([
                                    new Ext.data.Record({
                                        idInterno1: nos[i].attributes.idInterno,
                                        conceito1: nos[i].text,
                                        relacaoMinsky: relacoesMapa[k].relacaoMinsky,
                                        relacaoUsuario: relacoesMapa[k].relacaoUsuario,
                                        idInterno2: noRelM.attributes.idInterno,
                                        conceito2: noRelM.text,
                                        relHierarquia: false
                                    },
                                        Ext.id()
                                    )
                                ]);
                            }
                        }
                    }

                    /*
                     * Faz cópia dos relacionamentos do mapa que não foram
                     * feitos na cópia acima.
                     */
                    relUsu = nos[i].attributes.relacaoUsuario;

                    if ( !relUsu )
                        relUsu = i18n.getMsg( 'estrCon.rm.' + nos[i].attributes.relacaoMinsky );

                    if ( !busca( no.attributes.idInterno,
                                 nos[i].attributes.idInterno,
                                 no.text,
                                 nos[i].text ) ) {

                        store.add([
                            new Ext.data.Record({
                                idInterno1: no.attributes.idInterno,
                                conceito1: no.text,
                                relacaoMinsky: nos[i].attributes.relacaoMinsky,
                                relacaoUsuario: relUsu,
                                idInterno2: nos[i].attributes.idInterno,
                                conceito2: nos[i].text,
                                relHierarquia: true
                            },
                                Ext.id()
                            )
                        ]);
                    }
                    
                }
            }

            Uteis.gerarRelacionamentos( store, nos[i], root, arvore, novaEstrutura, bufferApagar, i18n );

        }

    }

};


/*
 * Função para buscar um nó pelo seu idInterno a partir de uma raiz
 */
Uteis.buscarNoPorIdInterno = function( arvore, id, tipo ) {

    var no = null;

    arvore.getRootNode().cascade( function(){
        if ( this.attributes.idInterno == id &&
             this.attributes.tipo == tipo ) {
             no = this;
             return false;
        }
    });

    return no;

};


/*
 * Faz a cópia da estrutura dos conceitos do material.
 */
Uteis.copiarEstruturaConceitos = function( raizMapa, raizEstrutura ) {
    
    if ( raizEstrutura.hasChildNodes() ) {

        var nos = raizEstrutura.childNodes;

        for ( var i = 0; i < nos.length; i++ ) {

            if ( nos[i].attributes.tipo == 'conceito' ) {
                
                var novoNo = new Ext.tree.TreeNode({
                    idInterno: nos[i].attributes.idInterno,
                    novo: nos[i].attributes.novo,
                    movido: false,
                    ordem: nos[i].attributes.ordem,
                    compartilhado: nos[i].attributes.compartilhado,
                    tipo: 'conceito',
                    text: nos[i].text,
                    icon: Application.contextPath + '/imagens/icones/lightbulb.png',
                    relacoesHierarquia: nos[i].attributes.relacoesHierarquia,
                    relacoesMapa: nos[i].attributes.relacoesMapa,
                    leaf: false
                });

                raizMapa.appendChild( novoNo );
                
                Uteis.copiarEstruturaConceitos( novoNo, nos[i] );

            }

        }

    }

};


/*
 * O objeto mapaGravacao contém as funções para geração do mapa de
 * conceitos e dos relacionamentos do mapa.
 */
Uteis.mapaGravacao = {

    /*
     * Função para extrair a estrutura do mapa de conceitos, gerando os dados da
     * estrutura que será armazenados na base de dados. A estrutura precisa ser
     * gerada em XML, pois será deserializada no servidor.
     */
    gerarEstrutura: function( mapa ) {

        var dados = '<conceitos>';

        mapa.getRootNode().cascade( function(){

            // ignora a raiz
            if ( this != mapa.getRootNode() ) {

                dados += '<conceito>' +
                    '<id>' + this.attributes.idInterno + '</id>' +
                    '<titulo>' + this.text + '</titulo>' +
                    '<novo>' + this.attributes.novo + '</novo>' +
                    '<ordem>' + this.attributes.ordem + '</ordem>';

                if ( this == mapa.getRootNode().childNodes[0] )
                    dados += '<raiz>true</raiz>';
                else
                    dados += '<raiz>false</raiz>';

                dados += '<filhos>'
                var filhos = this.childNodes;

                for ( var i = 0; i < filhos.length; i++ ) {
                    dados += '<conceito>' +
                        '<id>' + filhos[i].attributes.idInterno + '</id>' +
                        '<titulo>' + filhos[i].text + '</titulo>' +
                        '<novo>' + filhos[i].attributes.novo + '</novo>' +
                        '<ordem>' + filhos[i].attributes.ordem + '</ordem>' +
                        '<raiz>false</raiz>' +
                        '<filhos></filhos>' +
                        '</conceito>';
                }

                dados += '</filhos>';
                dados += '</conceito>';

            }

        });

        dados += '</conceitos>';

        return dados;

    },

    /*
     * Função para a geração dos relacionamentos do mapa de conceitos.
     * Usa o Store da grid para isso.
     */
    gerarRelacionamentos: function( store ) {

        var dados = '<relacoes>';

        store.each( function( campo ) {

            var rM = campo.data.relacaoMinsky ? campo.data.relacaoMinsky : '';
            var rU = campo.data.relacaoUsuario ? campo.data.relacaoUsuario : '';

            dados += '<relacao>' +
                '<idC1>' + campo.data.idInterno1 + '</idC1>' +
                '<idC2>' + campo.data.idInterno2 + '</idC2>' +
                '<tituloC1>' + campo.data.conceito1 + '</tituloC1>' +
                '<tituloC2>' + campo.data.conceito2 + '</tituloC2>' +
                '<relacaoMinsky>' + rM + '</relacaoMinsky>' +
                '<relacaoUsuario>' + rU + '</relacaoUsuario>' +
                '<hierarquia>' + campo.data.relHierarquia + '</hierarquia>' +
                '</relacao>';
        });

        dados += '</relacoes>';

        return dados;

    }

};


/*
 * Faz um alert com os dados de um no.
 */
Uteis.alertNo = function( no ) {

    var extraiRelacoes = function( array ) {

        var v = 'undefined';

        if ( array ) {
            
            v = '\n';
            
            if ( array.length == 0 )
                v += '    vazio';

            for ( var i = 0; i < array.length; i++ ) {
                v += '    {' +
                     '\n        idInterno: ' + array[i].idInterno +
                     '\n        titulo: ' + array[i].titulo +
                     '\n        relacaoMinsky: ' + array[i].relacaoMinsky +
                     '\n        relacaoUsuario: ' + array[i].relacaoUsuario +
                     '\n    }, ';
            }
        }

        return v;

    };

    alert( 'idInterno: ' + no.attributes.idInterno +
            '\nnovo: ' + no.attributes.novo +
            '\nmovido: ' + no.attributes.movido +
            '\nordem: ' + no.attributes.ordem +
            '\nprincipal: ' + no.attributes.principal +
            '\ncompartilhado: ' + no.attributes.compartilhado +
            '\nestrCon: ' + no.attributes.estrCon +
            '\ntipo: ' + no.attributes.tipo +
            '\nlayout: ' + no.attributes.layout +
            '\n\n\nrelacoesHierarquia: ' + extraiRelacoes( no.attributes.relacoesHierarquia ) +
            '\n\nrelacoesMapa: ' + extraiRelacoes( no.attributes.relacoesMapa ) +
            '\n\nrelacaoMinsky (com o pai): ' + no.attributes.relacaoMinsky );

};


/*
 * Cria um item da analogia.
 */
Uteis.criarItemAnalogia = function( ap, cBase, cAlvo, i18n ) {

    var ini = i18n.getMsg( 'analogias.rm.' + ap.predicate + '.ini' );
    var rel = i18n.getMsg( 'analogias.rm.' + ap.predicate + '.rel' );

    var tbm = '';
    if ( ap.expert1 == ap.commonSense1 ) {
        tbm = i18n.getMsg( 'analogias.tbm.val' ) + ' ';
    }

    var analogia = '<li>';

    /* Criando formatação nas tags ao invés de usar estilos, evitando
     * que os estilos precisem ser colocados nos documentos que irão receber
     * a analogia.
     */
    analogia += '<b>' + ini + ' <font color="#004488"><u>' + cAlvo + '</u></font> ' +
            rel + ' <font color="#009292"><u>' + ap.expert2 + '</u></font>' +
            ' ' + i18n.getMsg( 'analogias.assimComo' ) + ' ' + Ext.util.Format.lowercase( ini ) +
            ' <font color="#008844"><u>' + cBase + '</u></font> ' +
            tbm + rel + ' <font color="#009292"><u>'+ ap.commonSense2 + '</u></font>;</b>';

    analogia += '</li><br/>';

    return analogia;

};


/*
 * Função para fazer a carga dos objetos de uma página em uma árvore.
 * Se idPagina não for definido, cria a arvore vazia.
 */
Uteis.carregarObjetosPagina = function( idPagina, arvore ) {

    arvore.getSelectionModel().clearSelections();

    // armazena o id da página em questão
    arvore.setRootNode( new Ext.tree.TreeNode({
        text: Application.i18n.getMsg( 'latEsquerda.objPagina' ),
        icon: Application.contextPath + '/imagens/icones/objects.png',
        idInterno: idPagina,
        leaf: false
    }));

    var noImagens = new Ext.tree.TreeNode({
        leaf: false,
        text: Application.i18n.getMsg( 'objetos.img' ),
        icon: Application.contextPath + '/imagens/icones/picture.png',
        tipo: 'imagens'
    });

    var noVideos = new Ext.tree.TreeNode({
        leaf: false,
        text: Application.i18n.getMsg( 'objetos.vid' ),
        icon: Application.contextPath + '/imagens/icones/film.png',
        tipo: 'videos'
    });

    var noSons = new Ext.tree.TreeNode({
        leaf: false,
        text: Application.i18n.getMsg( 'objetos.sons' ),
        icon: Application.contextPath + '/imagens/icones/music.png',
        tipo: 'sons'
    });

    arvore.getRootNode().appendChild([
        noImagens,
        noVideos,
        noSons
    ]);

    if ( idPagina != null ) {
        
        $.ajax({

            dataType: 'json',
            type: 'post',
            asynch: false,
            cache: false,
            url: Application.contextPath + '/ajax/paginas/objetosPaginaPorId.jsp',
            data: {
                id: idPagina
            },
            success: function( data, textStatus ) {

                if ( data.success ) {

                    var oas = data.oas;

                    for ( var i = 0; i < oas.length; i++ ) {

                        var no = new Ext.tree.TreeNode({
                            leaf: true,
                            idInterno: oas[i].idInterno,
                            text: oas[i].text,
                            icon: oas[i].icon,
                            tipo: oas[i].tipo
                        });
                        
                        switch ( oas[i].tipo ) {

                            case 'imagem':
                                noImagens.appendChild( no );
                                break;

                            case 'video':
                                noVideos.appendChild( no );
                                break;

                            case 'som':
                                noSons.appendChild( no );
                                break;

                        }
                    }
                }
            }
        });

    }

};


/*
 * Função para remover uma mídia do texto do editor.
 * Todos os tipos são tratados da mesma forma.
 * Caso for implementar inserções diferentes para vídeos e
 * sons é necessário alterar a implementação dessa função.
 */
Uteis.removerMidia = function( texto, id, tipo ) {

    switch ( tipo ) {

        case 'imagem':
        case 'video':
        case 'som':
            while ( true ) {

                var i = texto.indexOf( id );

                if ( i == -1 )
                    break;

                var j = texto.indexOf( '>', i );

                // remove antes
                var antes = texto.substring( 0, i - 9 );
                var depois = texto.substring( j + 1, texto.length );

                texto = antes + depois;

            }

    }

    return texto;

};


/*
 * Função para abrir um relatório.
 */
Uteis.abrirRelatorio = function( tipo, configs ) {

    window.open(
            Application.contextPath +
            '/servlets/ReportsServlet?tipo=' + tipo,
            '_blank' );

}
