/**
 * @author David Buzatto
 * @class Ext.ux.form.HtmlEditor.Imagem
 * @extends Ext.util.Observable
 * <p>Plugin para criação de um botão no HtmlEditor para inserção de imagens.</p>
 */
Ext.ux.form.HtmlEditorCog.Imagem = Ext.extend(Ext.util.Observable, {
    urlSizeVars: ['width','height'],
    init: function(cmp){
        this.cmp = cmp;
        this.cmp.on('render', this.onRender, this);
        this.cmp.on('initialize', this.onInit, this, {delay:100, single: true});
    },
    onEditorMouseUp : function(e){
        Ext.get(e.getTarget()).select('img').each(function(el){
            var w = el.getAttribute('width'), h = el.getAttribute('height'), src = el.getAttribute('src')+' ';
            src = src.replace(new RegExp(this.urlSizeVars[0]+'=[0-9]{1,5}([&| ])'), this.urlSizeVars[0]+'='+w+'$1');
            src = src.replace(new RegExp(this.urlSizeVars[1]+'=[0-9]{1,5}([&| ])'), this.urlSizeVars[1]+'='+h+'$1');
            el.set({src:src.replace(/\s+$/,"")});
        }, this);
    },
    onInit: function(){
        Ext.EventManager.on(this.cmp.getDoc(), {
            'mouseup': this.onEditorMouseUp,
            buffer: 100,
            scope: this
        });
    },
    onRender: function() {
        var btn = this.cmp.getToolbar().addButton({
            iconCls: 'x-edit-pictures',
            handler: this.selectImage,
            scope: this
        });
    },
    selectImage: function( btn, evt ) {
        if ( !this.janela ) {
            this.janela = new Ext.Window({
                title: Application.i18n.getMsg( 'editorTex.plugins.img.titulo' ),
                iconCls: 'iconeInserirImagem',
                closeAction: 'hide',
                modal: true,
                resizable: false,
                items: [{
                    xtype: 'form',
                    id: 'formInserirImagem',
                    layout: 'form',
                    border: false,
                    bodyStyle: 'padding: 4px;',
                    labelAlign: 'right',
                    items: [{
                        xtype: 'textfield',
                        id: 'fieldEndInserirImg',
                        name: 'nomeArquivo',
                        fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.img.end' ),
                        allowBlank: false,
                        blankText: Application.i18n.getMsg( 'vtype.blank' ),
                        maxLength: 150,
                        maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                        width: 250,
                        listeners: {
                            blur: function( field ) {
                                Ext.ux.form.HtmlEditorCog.Imagem.atualizarVisualizacao();
                            }
                        }
                    }, {
                        xtype: 'panel',
                        layout: 'hbox',
                        border: false,
                        bodyStyle: 'padding-bottom: 4px;',
                        items: [ new Ext.Spacer({width: 135}),
                            new Ext.ux.swfbtn({
                                id: 'btnEnviarImagem',
                                text: Application.i18n.getMsg( 'editorTex.plugins.img.envImg' ),
                                icon: Application.contextPath + '/imagens/icones/arrow_up.png',
                                hidden: false,
                                disabled: false,
                                isSingle: true,
                                filesizelimit: '512 KB',
                                debug: Global.DEBUG,
                                filetypes: '*.jpg; *.gif; *.png; *.bmp',
                                filetypesdescription: Application.i18n.getMsg( 'editorTex.plugins.img.descricaoTipo' ),
                                iconpath: Application.contextPath + '/imagens/icones/',
                                postparams: {
                                    name: 'envio imagens',
                                    tipo: 'imagem',
                                    idMaterial: Application.idMaterialEdicao
                                },
                                uploadurl: Application.contextPath + '/servlets/UploadServlet',
                                flashurl: Application.contextPath + '/javascript/ext-plugins/SWFUpload/swfupload.swf'
                            }),
                            new Ext.Spacer({width: 5}),
                        {
                            xtype: 'button',
                            text: Application.i18n.getMsg( 'editorTex.plugins.img.selImg' ),
                            icon: Application.contextPath + '/imagens/icones/folder.png',
                            handler: function( btn, evt ) {
                                this.selecionarImagem();
                            },
                            scope: this
                        }]
                    }, {
                        xtype: 'textfield',
                        id: 'fieldTitInserirImg',
                        name: 'titulo',
                        fieldLabel: Application.i18n.getMsg( 'titulo' ),
                        allowBlank: false,
                        blankText: Application.i18n.getMsg( 'vtype.blank' ),
                        maxLength: 100,
                        maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                        width: 200,
                        listeners: {
                            blur: function( field ) {
                                Ext.ux.form.HtmlEditorCog.Imagem.atualizarVisualizacao();
                            }
                        }
                    }, {
                        xtype: 'textfield',
                        id: 'fieldDescInserirImg',
                        fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.img.desc' ),
                        width: 200,
                        listeners: {
                            blur: function( field ) {
                                Ext.ux.form.HtmlEditorCog.Imagem.atualizarVisualizacao();
                            }
                        }
                    }, {
                        xtype: 'numberfield',
                        id: 'fieldLargInserirImg',
                        fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.img.larg' ),
                        width: 50,
                        listeners: {
                            blur: function( field ) {
                                Ext.ux.form.HtmlEditorCog.Imagem.atualizarVisualizacao();
                            }
                        }
                    }, {
                        xtype: 'numberfield',
                        id: 'fieldAltInserirImg',
                        fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.img.alt' ),
                        width: 50,
                        listeners: {
                            blur: function( field ) {
                                Ext.ux.form.HtmlEditorCog.Imagem.atualizarVisualizacao();
                            }
                        }
                    }],
                    scope: this
                }, {
                    xtype: 'panel',
                    layout: 'form',
                    border: false,
                    bodyStyle: 'padding: 4px;',
                    items: [{
                        xtype: 'panel',
                        autoScroll: true,
                        title: Application.i18n.getMsg( 'editorTex.plugins.img.visu' ),
                        height: 250,
                        width: 360,
                        html: '<div id="visualizacaoInserirImagem"></div>'
                    }]
                }],
                fbar: [{
                    text: Application.i18n.getMsg( 'inserir' ),
                    handler: function( btn, evt ) {

                        /*
                         * Poderia ter feito usando a sumissão do formulário,
                         * mas o escopo das funções usadas está um pouco
                         * bagunçado. optei por não organizar (falta de tempo)
                         */
                        if ( Ext.getCmp( 'formInserirImagem' ).getForm().isValid() ) {
                        
                            var espera = Ext.Msg.wait(
                                    Application.i18n.getMsg( 'editorTex.plugins.img.inserindoImagem' ),
                                    Application.i18n.getMsg( 'favorAguarde' ) ).getDialog();

                            var nomeArquivo = Ext.getCmp( 'fieldEndInserirImg' ).getValue();
                            var http = nomeArquivo.substring( 0, nomeArquivo.indexOf( ':' ) );
                            var sucesso = false;
                            var dados = undefined;
                            var idImagemGerada = undefined;

                            $.ajax( {
                                async: false,
                                dataType: 'json',
                                cache: false,
                                type: 'post',
                                url: Application.contextPath + '/servlets/CriarOAServlet',
                                data: {
                                    acao: 'criar-imagem',
                                    material: Application.idMaterialEdicao,
                                    idPagina: Application.idPaginaEdicao,
                                    nomeArquivo: nomeArquivo,
                                    titulo: Ext.getCmp( 'fieldTitInserirImg' ).getValue(),
                                    externo: http == 'http'
                                },
                                success: function( data, textStatus ) {
                                    sucesso = data.success;
                                    idImagemGerada = data.idImagem;
                                    dados = data;
                                }
                            });

                            if ( sucesso ) {

                                // faz a carga dos objetos da página
                                Application.carregarObjetosPagina( Application.idPaginaEdicao );

                                // inserir
                                this.insertImage(
                                    Ext.ux.form.HtmlEditorCog.Imagem.gerarImagem(
                                        'imagem-' +
                                         Application.idMaterialEdicao + '-' +
                                         Application.idPaginaEdicao + '-' +
                                         idImagemGerada )
                                );

                                espera.close();

                                this.janela.hide();

                            } else {

                                espera.close();
                                Uteis.exibirMensagemErro( Uteis.criarMensagemErro( dados, Application.i18n ) );

                            }

                        }

                        
                        
                    },
                    scope: this
                }, {
                    text: Application.i18n.getMsg( 'cancelar' ),
                    handler: function( btn, evt ) {
                        this.janela.hide();
                    },
                    scope: this
                }]
            });
        } else{
            this.janela.getEl().frame();
        }

        Ext.getCmp( 'formInserirImagem' ).getForm().reset();
        $( '#visualizacaoInserirImagem' ).html( '' );
        this.janela.show();
        
    },
    insertImage: function(img) {
        this.cmp.insertAtCursor( 
                Ext.ux.form.HtmlEditorCog.Imagem.gerarMarkup( img ) );
    },
    selecionarImagem: function() {

        var panel = new Ext.Panel({
            bodyStyle: 'padding: 10px;',
            border: false,
            labelAlign: 'right',
            layout: 'form',
            items: [{
                xtype: 'fieldset',
                title: Application.i18n.getMsg( 'editorTex.plugins.img.imgDisp' ),
                html: '<div id="divExternaImagensDisponiveis">' + Uteis.gerarListaImagens( 6, Application.idMaterialEdicao ) + '</div>'
            }],
            listeners: {
                afterlayout: function( cmp ) {

                    Ext.ux.form.HtmlEditorCog.Imagem.registrarEventos();

                }
            }
        });

        var janela = new Ext.Window({
            modal: true,
            resizable: false,
            iconCls: 'iconeAbrir',
            title: Application.i18n.getMsg( 'editorTex.plugins.img.selImg' ),
            items: [
                panel
            ],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    if ( Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada ) {

                        // extrai o nome da imagem
                        var nome = Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada.substring(
                                Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada.indexOf( '-' ) + 1,
                                Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada.length );

                        // seta no campo
                        Ext.getCmp( 'fieldEndInserirImg' ).setValue( nome );

                        // atualiza a visualização
                        Ext.ux.form.HtmlEditorCog.Imagem.atualizarVisualizacao();

                        janela.close();

                    }

                }
            }, {
                xtype: 'button',
                text: Application.i18n.getMsg( 'excluir' ),
                handler: function( btn, evt ) {

                    if ( Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada ) {
                        
                        var nome = Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada.substring(
                                Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada.indexOf( '-' ) + 1,
                                Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada.length );

                        Ext.Msg.confirm(
                            Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                            Application.i18n.getMsg( 'dialogo.desejaExcluirImagem' ),
                            function( btnId ) {
                                if ( btnId == 'yes' ) {

                                    var espera = Ext.Msg.wait(
                                            Application.i18n.getMsg( 'excluindoArquivo' ),
                                            Application.i18n.getMsg( 'favorAguarde' ) ).getDialog();

                                    $.ajax( {
                                        async: false,
                                        dataType: 'json',
                                        cache: false,
                                        type: 'post',
                                        url: Application.contextPath + '/servlets/ApagaArquivoServlet',
                                        data: {
                                            tipo: 'imagem',
                                            idMaterial: Application.idMaterialEdicao,
                                            nomeArquivo: nome
                                        },
                                        success: function( data, textStatus ) {

                                            if ( data.success ) {

                                                $( '#divExternaImagensDisponiveis' ).html( Uteis.gerarListaImagens( 6, Application.idMaterialEdicao ) );
                                                Ext.ux.form.HtmlEditorCog.Imagem.registrarEventos();

                                                Ext.getCmp( 'formInserirImagem' ).getForm().reset();
                                                $( '#visualizacaoInserirImagem' ).html( '' );

                                            } else {

                                                Uteis.exibirMensagemErro( Uteis.criarMensagemErro( data, Application.i18n ) );

                                            }

                                            espera.close();

                                        }
                                    });
                                    
                                }
                            }
                        );

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
});

/*
 * Armazena o valor selecionado.
 */
Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada = undefined;

/*
 * Função utilitária para atualizar a visualização.
 */
Ext.ux.form.HtmlEditorCog.Imagem.atualizarVisualizacao = function() {
    $( '#visualizacaoInserirImagem' ).html(
        Ext.ux.form.HtmlEditorCog.Imagem.gerarMarkup(
            Ext.ux.form.HtmlEditorCog.Imagem.gerarImagem( Ext.id() )
        )
    );
};

/*
 * Função utilirária para gerar o objeto da imagem.
 */
Ext.ux.form.HtmlEditorCog.Imagem.gerarImagem = function( idImagem ) {

    var endereco = Ext.getCmp( 'fieldEndInserirImg' ).getValue();
    var http = endereco.substring( 0, endereco.indexOf( ':' ) );

    // se não for externo, usa o servlet
    if ( http != 'http' )
        endereco = 'servlets/LeitorMidiasServlet?tipo=imagem&idMaterial=' +
                Application.idMaterialEdicao + '&nomeArquivo=' + endereco;

    var img = {
        id: idImagem,
        endereco: endereco,
        descricao: Ext.getCmp( 'fieldDescInserirImg' ).getValue(),
        titulo: Ext.getCmp( 'fieldTitInserirImg' ).getValue(),
        width: Ext.getCmp( 'fieldLargInserirImg' ).getValue(),
        height: Ext.getCmp( 'fieldAltInserirImg' ).getValue()
    }

    return img;

};


/*
 * Função para gerar o markup de uma imagem.
 */
Ext.ux.form.HtmlEditorCog.Imagem.gerarMarkup = function( img ) {

    var vImg = '<img id="' + img.id + '" ' +
            'src="' + img.endereco + '" ';

    if ( img.width && img.width != '' && img.width >= 0 )
        vImg += 'width="' + img.width + '" ';

    if ( img.height && img.height != '' && img.height >= 0 )
        vImg += 'height="' + img.height + '" ';

    vImg += 'alt="' + img.descricao + '" ' +
            'title="' + img.titulo + '"/>';

    return vImg;

};


/*
 * Função para registrar os eventos novamente
 */
Ext.ux.form.HtmlEditorCog.Imagem.registrarEventos = function() {

    $( '.imagemDisponivel' ).click( function( evt ){
        $( '.imagemDisponivel' ).css( 'background', '#FFFFFF' );
        $(this).css( Global.fundoBtnClick );
        Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada = evt.target.id;
    });

    $( '.imagemDisponivel' ).hover(
        function( evt ){
            if ( Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada != evt.target.id )
                $(this).css( Global.fundoBtnOver );
        },
        function( evt ){
            if ( Ext.ux.form.HtmlEditorCog.Imagem.idImagemSelecionada != evt.target.id )
                $(this).css( Global.fundoBtnOut );
        }
    );

};
