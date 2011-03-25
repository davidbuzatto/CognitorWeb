/**
 * @author David Buzatto
 * @class Ext.ux.form.HtmlEditor.Video
 * @extends Ext.util.Observable
 * <p>Plugin para criação de um botão no HtmlEditor para inserção de videos.</p>
 */
Ext.ux.form.HtmlEditorCog.Video = Ext.extend(Ext.util.Observable, {
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
            iconCls: 'x-edit-videos',
            handler: this.selectImage,
            scope: this
        });
    },
    selectImage: function( btn, evt ) {
        if ( !this.janela ) {
            this.janela = new Ext.Window({
                title: Application.i18n.getMsg( 'editorTex.plugins.vid.titulo' ),
                iconCls: 'iconeInserirVideo',
                closeAction: 'hide',
                modal: true,
                resizable: false,
                items: [{
                    xtype: 'form',
                    id: 'formInserirVideo',
                    layout: 'form',
                    border: false,
                    bodyStyle: 'padding: 4px;',
                    labelAlign: 'right',
                    items: [{
                        xtype: 'textfield',
                        id: 'fieldEndInserirVid',
                        name: 'nomeArquivo',
                        fieldLabel: Application.i18n.getMsg( 'editorTex.plugins.vid.end' ),
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
                                id: 'btnEnviarVideo',
                                text: Application.i18n.getMsg( 'editorTex.plugins.vid.envVid' ),
                                icon: Application.contextPath + '/imagens/icones/arrow_up.png',
                                hidden: false,
                                disabled: false,
                                isSingle: true,
                                filesizelimit: '512 KB',
                                debug: Global.DEBUG,
                                filetypes: '*.flv',
                                filetypesdescription: Application.i18n.getMsg( 'editorTex.plugins.vid.descricaoTipo' ),
                                iconpath: Application.contextPath + '/imagens/icones/',
                                postparams: {
                                    name: 'envio video',
                                    tipo: 'video',
                                    idMaterial: Application.idMaterialEdicao
                                },
                                uploadurl: Application.contextPath + '/servlets/UploadServlet',
                                flashurl: Application.contextPath + '/javascript/ext-plugins/SWFUpload/swfupload.swf'
                            }),
                            new Ext.Spacer({width: 5}),
                        {
                            xtype: 'button',
                            text: Application.i18n.getMsg( 'editorTex.plugins.vid.selVid' ),
                            icon: Application.contextPath + '/imagens/icones/folder.png',
                            handler: function( btn, evt ) {
                                this.selecionarVideo();
                            },
                            scope: this
                        }]
                    }, {
                        xtype: 'textfield',
                        id: 'fieldTitInserirVid',
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
                        if ( Ext.getCmp( 'formInserirVideo' ).getForm().isValid() ) {

                            var espera = Ext.Msg.wait(
                                    Application.i18n.getMsg( 'editorTex.plugins.vid.inserindoVideo' ),
                                    Application.i18n.getMsg( 'favorAguarde' ) ).getDialog();

                            var nomeArquivo = Ext.getCmp( 'fieldEndInserirVid' ).getValue();
                            var sucesso = false;
                            var dados = undefined;
                            var idVideoGerado = undefined;

                            $.ajax( {
                                async: false,
                                dataType: 'json',
                                cache: false,
                                type: 'post',
                                url: Application.contextPath + '/servlets/CriarOAServlet',
                                data: {
                                    acao: 'criar-video',
                                    material: Application.idMaterialEdicao,
                                    idPagina: Application.idPaginaEdicao,
                                    nomeArquivo: nomeArquivo,
                                    titulo: Ext.getCmp( 'fieldTitInserirVid' ).getValue(),
                                    externo: false // sempre interno
                                },
                                success: function( data, textStatus ) {
                                    sucesso = data.success;
                                    idVideoGerado = data.idVideo;
                                    dados = data;
                                }
                            });

                            if ( sucesso ) {

                                // faz a carga dos objetos da página
                                Application.carregarObjetosPagina( Application.idPaginaEdicao );

                                // inserir
                                this.insertVideo(
                                    Ext.ux.form.HtmlEditorCog.Video.gerarVideo(
                                        'video-' +
                                         Application.idMaterialEdicao + '-' +
                                         Application.idPaginaEdicao + '-' +
                                         idVideoGerado )
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

        Ext.getCmp( 'formInserirVideo' ).getForm().reset();
        $( '#visualizacaoInserirVideo' ).html( '' );
        this.janela.show();

    },
    insertVideo: function(video) {
        this.cmp.insertAtCursor(
                Ext.ux.form.HtmlEditorCog.Video.gerarMarkup( video ) );
    },
    selecionarVideo: function() {

        var panel = new Ext.Panel({
            bodyStyle: 'padding: 10px;',
            border: false,
            labelAlign: 'right',
            layout: 'form',
            items: [{
                xtype: 'fieldset',
                title: Application.i18n.getMsg( 'editorTex.plugins.vid.vidDisp' ),
                html: '<div id="divExternaVideosDisponiveis">' + Uteis.gerarListaVideos( 6, Application.idMaterialEdicao ) + '</div>'
            }],
            listeners: {
                afterlayout: function( cmp ) {

                    Ext.ux.form.HtmlEditorCog.Video.registrarEventos();

                }
            }
        });

        var janela = new Ext.Window({
            modal: true,
            resizable: false,
            iconCls: 'iconeAbrir',
            title: Application.i18n.getMsg( 'editorTex.plugins.vid.selVid' ),
            items: [
                panel
            ],
            fbar: [{
                xtype: 'button',
                text: Application.i18n.getMsg( 'ok' ),
                handler: function( btn, evt ) {

                    if ( Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado ) {

                        // extrai o nome do video
                        var nome = Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado.substring(
                                Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado.indexOf( '-' ) + 1,
                                Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado.length );

                        // seta no campo
                        Ext.getCmp( 'fieldEndInserirVid' ).setValue( nome );

                        janela.close();

                    }

                }
            }, {
                xtype: 'button',
                text: Application.i18n.getMsg( 'excluir' ),
                handler: function( btn, evt ) {

                    if ( Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado ) {

                        var nome = Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado.substring(
                                Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado.indexOf( '-' ) + 1,
                                Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado.length );

                        Ext.Msg.confirm(
                            Application.i18n.getMsg( 'dialogo.confirm.titulo' ),
                            Application.i18n.getMsg( 'dialogo.desejaExcluirVideo' ),
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
                                            tipo: 'video',
                                            idMaterial: Application.idMaterialEdicao,
                                            nomeArquivo: nome
                                        },
                                        success: function( data, textStatus ) {

                                            if ( data.success ) {

                                                $( '#divExternaVideosDisponiveis' ).html( Uteis.gerarListaVideos( 6, Application.idMaterialEdicao ) );
                                                Ext.ux.form.HtmlEditorCog.Video.registrarEventos();

                                                Ext.getCmp( 'formInserirVideo' ).getForm().reset();
                                                $( '#visualizacaoInserirVideo' ).html( '' );

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
Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado = undefined;

/*
 * Função utilirária para gerar o objeto do video.
 */
Ext.ux.form.HtmlEditorCog.Video.gerarVideo = function( idVideo ) {

    var vid = {
        id: idVideo,
        endereco: 'imagens/previewVideo.png',
        titulo: Ext.getCmp( 'fieldTitInserirVid' ).getValue()
    }

    return vid;

};


/*
 * Função para gerar o markup de um video.
 */
Ext.ux.form.HtmlEditorCog.Video.gerarMarkup = function( vid ) {
    
    var vImg = '<img id="' + vid.id + '" ' +
            'src="' + vid.endereco + '" ' +
            'width="425" height="300" ' +
            'title="' + vid.titulo + '"/>';

    return vImg;

};


/*
 * Função para registrar os eventos novamente
 */
Ext.ux.form.HtmlEditorCog.Video.registrarEventos = function() {

    $( '.videoDisponivel' ).click( function( evt ){
        $( '.videoDisponivel' ).css( 'background', '#FFFFFF' );
        $(this).css( Global.fundoBtnClick );
        Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado = evt.target.id;
    });

    $( '.videoDisponivel' ).hover(
        function( evt ){
            if ( Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado != evt.target.id )
                $(this).css( Global.fundoBtnOver );
        },
        function( evt ){
            if ( Ext.ux.form.HtmlEditorCog.Video.idVideoSelecionado != evt.target.id )
                $(this).css( Global.fundoBtnOut );
        }
    );

};
