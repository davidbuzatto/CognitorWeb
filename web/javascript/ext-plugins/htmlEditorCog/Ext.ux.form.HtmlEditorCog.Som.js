/**
 * @author David Buzatto
 * @class Ext.ux.form.HtmlEditor.Imagem
 * @extends Ext.util.Observable
 * <p>Plugin para criação de um botão no HtmlEditor para inserção de sons.</p>
 */
Ext.ux.form.HtmlEditorCog.Som = Ext.extend(Ext.util.Observable, {
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
            iconCls: 'x-edit-sounds',
            handler: this.selectImage,
            scope: this
        });
    },
    selectImage: function( btn, evt ) {
        if ( !this.janela ) {
            this.janela = new Ext.Window({
                title: Application.i18n.getMsg( 'editorTex.plugins.som.titulo' ),
                iconCls: 'iconeInserirSom',
                closeAction: 'hide',
                modal: true,
                resizable: false,
                items: [{
                    xtype: 'form',
                    id: 'formInserirSom',
                    layout: 'form',
                    border: false,
                    bodyStyle: 'padding: 4px;',
                    labelAlign: 'right',
                    items: [{
                        xtype: 'textfield',
                        id: 'fieldEndInserirSom',
                        name: 'nomeArquivo',
                        fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.som.end' ),
                        allowBlank: false,
                        blankText: Application.i18n.getMsg( 'vtype.blank' ),
                        maxLength: 150,
                        maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                        readOnly: true,
                        width: 250
                    }, {
                        xtype: 'panel',
                        layout: 'hbox',
                        border: false,
                        bodyStyle: 'padding-bottom: 4px;',
                        items: [ new Ext.Spacer({width: 135}),
                            new Ext.ux.swfbtn({
                                id: 'btnEnviarSom',
                                text: Application.i18n.getMsg( 'editorTex.plugins.som.envSom' ),
                                icon: Application.contextPath + '/imagens/icones/arrow_up.png',
                                hidden: false,
                                disabled: false,
                                isSingle: true,
                                filesizelimit: '512 KB',
                                debug: Global.DEBUG,
                                filetypes: '*.mp3',
                                filetypesdescription: Application.i18n.getMsg( 'editorTex.plugins.som.descricaoTipo' ),
                                iconpath: Application.contextPath + '/imagens/icones/',
                                postparams: {
                                    name: 'envio som',
                                    tipo: 'som',
                                    idMaterial: Application.idMaterialEdicao
                                },
                                uploadurl: Application.contextPath + '/servlets/UploadServlet',
                                flashurl: Application.contextPath + '/javascript/ext-plugins/SWFUpload/swfupload.swf'
                            }),
                            new Ext.Spacer({width: 5}),
                        {
                            xtype: 'button',
                            text: Application.i18n.getMsg( 'editorTex.plugins.som.selSom' ),
                            icon: Application.contextPath + '/imagens/icones/folder.png',
                            handler: function( btn, evt ) {
                                this.selecionarSom();
                            },
                            scope: this
                        }]
                    }, {
                        xtype: 'textfield',
                        id: 'fieldTitInserirSom',
                        name: 'titulo',
                        fieldLabel: Application.i18n.getMsg( 'titulo' ),
                        allowBlank: false,
                        blankText: Application.i18n.getMsg( 'vtype.blank' ),
                        maxLength: 100,
                        maxLengthText: Application.i18n.getMsg( 'vtype.maximum' ),
                        width: 200
                    }],
                    scope: this
                }],
                fbar: [{
                    text: Application.i18n.getMsg( 'inserir' ),
                    handler: function( btn, evt ) {

                        /*
                         * Poderia ter feito usando a sumissão do formulário,
                         * mas o escopo das funções usadas está um pouco
                         * bagunçado. optei por não organizar (falta de tempo)
                         */
                        if ( Ext.getCmp( 'formInserirSom' ).getForm().isValid() ) {

                            var espera = Ext.Msg.wait(
                                    Application.i18n.getMsg( 'editorTex.plugins.som.inserindoSom' ),
                                    Application.i18n.getMsg( 'favorAguarde' ) ).getDialog();

                            var nomeArquivo = Ext.getCmp( 'fieldEndInserirSom' ).getValue();
                            var sucesso = false;
                            var dados = undefined;
                            var idSomGerado = undefined;

                            $.ajax( {
                                async: false,
                                dataType: 'json',
                                cache: false,
                                type: 'post',
                                url: Application.contextPath + '/servlets/CriarOAServlet',
                                data: {
                                    acao: 'criar-som',
                                    material: Application.idMaterialEdicao,
                                    idPagina: Application.idPaginaEdicao,
                                    nomeArquivo: nomeArquivo,
                                    titulo: Ext.getCmp( 'fieldTitInserirSom' ).getValue(),
                                    externo: false // sempre interno
                                },
                                success: function( data, textStatus ) {
                                    sucesso = data.success;
                                    idSomGerado = data.idSom;
                                    dados = data;
                                }
                            });

                            if ( sucesso ) {

                                // faz a carga dos objetos da página
                                Application.carregarObjetosPagina( Application.idPaginaEdicao );

                                // inserir
                                this.insertSom(
                                    Ext.ux.form.HtmlEditorCog.Som.gerarSom(
                                        'som-' +
                                         Application.idMaterialEdicao + '-' +
                                         Application.idPaginaEdicao + '-' +
                                         idSomGerado )
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

        Ext.getCmp( 'formInserirSom' ).getForm().reset();
        $( '#visualizacaoInserirSom' ).html( '' );
        this.janela.show();

    },
    insertSom: function(som) {
        this.cmp.insertAtCursor(
                Ext.ux.form.HtmlEditorCog.Som.gerarMarkup( som ) );
    },
    selecionarSom: function() {

        var panel = new Ext.Panel({
            bodyStyle: 'padding: 10px;',
            border: false,
            labelAlign: 'right',
            layout: 'form',
            items: [{
                xtype: 'fieldset',
                title: Application.i18n.getMsg( 'editorTex.plugins.som.somDisp' ),
                html: '<div id="divExternaSonsDisponiveis">' + Uteis.gerarListaSons( 6, Application.idMaterialEdicao ) + '</div>'
            }],
            listeners: {
                afterlayout: function( cmp ) {

                    Ext.ux.form.HtmlEditorCog.Som.registrarEventos();

                }
            }
        });

        var janela = new Ext.Window({
            modal: true,
            resizable: false,
            iconCls: 'iconeAbrir',
            title: Application.i18n.getMsg( 'editorTex.plugins.som.selSom' ),
            items: [
                panel
            ],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    if ( Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado ) {

                        // extrai o nome do som
                        var nome = Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado.substring(
                                Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado.indexOf( '-' ) + 1,
                                Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado.length );

                        // seta no campo
                        Ext.getCmp( 'fieldEndInserirSom' ).setValue( nome );

                        janela.close();

                    }

                }
            }, {
                xtype: 'button',
                text: Application.i18n.getMsg( 'excluir' ),
                handler: function( btn, evt ) {

                    if ( Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado ) {

                        var nome = Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado.substring(
                                Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado.indexOf( '-' ) + 1,
                                Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado.length );

                        Ext.Msg.confirm(
                            Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                            Application.i18n.getMsg( 'dialogo.desejaExcluirSom' ),
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
                                            tipo: 'som',
                                            idMaterial: Application.idMaterialEdicao,
                                            nomeArquivo: nome
                                        },
                                        success: function( data, textStatus ) {

                                            if ( data.success ) {

                                                $( '#divExternaSonsDisponiveis' ).html( Uteis.gerarListaSons( 6, Application.idMaterialEdicao ) );
                                                Ext.ux.form.HtmlEditorCog.Som.registrarEventos();

                                                Ext.getCmp( 'formInserirSom' ).getForm().reset();
                                                $( '#visualizacaoInserirSom' ).html( '' );

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
Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado = undefined;

/*
 * Função utilirária para gerar o objeto do som.
 */
Ext.ux.form.HtmlEditorCog.Som.gerarSom = function( idSom ) {

    var vid = {
        id: idSom,
        endereco: 'imagens/previewSom.png',
        titulo: Ext.getCmp( 'fieldTitInserirSom' ).getValue()
    }

    return vid;

};


/*
 * Função para gerar o markup de um som.
 */
Ext.ux.form.HtmlEditorCog.Som.gerarMarkup = function( vid ) {

    var vImg = '<img id="' + vid.id + '" ' +
            'src="' + vid.endereco + '" ' +
            'width="425" height="24" ' +
            'title="' + vid.titulo + '"/>';

    return vImg;

};


/*
 * Função para registrar os eventos novamente
 */
Ext.ux.form.HtmlEditorCog.Som.registrarEventos = function() {

    $( '.somDisponivel' ).click( function( evt ){
        $( '.somDisponivel' ).css( 'background', '#FFFFFF' );
        $(this).css( Global.fundoBtnClick );
        Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado = evt.target.id;
    });

    $( '.somDisponivel' ).hover(
        function( evt ){
            if ( Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado != evt.target.id )
                $(this).css( Global.fundoBtnOver );
        },
        function( evt ){
            if ( Ext.ux.form.HtmlEditorCog.Som.idSomSelecionado != evt.target.id )
                $(this).css( Global.fundoBtnOut );
        }
    );

};
