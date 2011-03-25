/**
 * Arquivo de scripts da tela inicial.
 *
 * @author    David Buzatto
 * @copyright (c) 2009, by David Buzatto
 * @date      29 de Setembro de 2009
 *
 */

function start( contexto ) {

    /*
     * Esconde as camadas que não são usadas no início
     */
    $( '#camadaLogin' ).hide();
    $( '#camadaVersaoLogin' ).hide();
    $( '#camadaEsqueciSenha' ).hide();
    $( '#overlay' ).hide();
    $( '#mensagemErro' ).hide();
    $( '#camadaCadastrarUsuario' ).hide();

    /*
     * Registro de evento para o botão testar
     */
    $( '#btnTestar' ).click( function() {
        $.ajax({
            url: contexto + '/servlets/LoginServlet',
            data: {
                tipo: 'testador'
            },
            type: 'post',
            dataType: 'json',
            success: function ( data, textStatus ) {
                if ( data.success ) {
                    $( '#mensagemErro' ).hide();
                    window.location = 'principal.jsp';
                } else {
                    $( '#mensagemErro' ).show();
                }
            }
        });
    });

    /*
     * Abre a janela de autenticação
     */
    $( '#btnAutenticar' ).click( function() {
        $( '#camadaPrincipal' ).fadeOut();
        $( '#camadaVersaoPrincipal' ).fadeOut();
        $( '#camadaLogin' ).fadeIn();
        $( '#camadaVersaoLogin' ).fadeIn();
        $( '#fieldEmail' ).focus();
    });

    /*
     * Volta para a tela de boas vindas
     */
    $( '#btnVoltar' ).click( function() {
        $( '#camadaLogin' ).fadeOut();
        $( '#camadaVersaoLogin' ).fadeOut();
        $( '#camadaPrincipal' ).fadeIn();
        $( '#camadaVersaoPrincipal' ).fadeIn();
    });

    /*
     * Tenta fazer o login
     */
    $( '#btnLogin' ).click( function() {
        $.ajax({
            url: contexto + '/servlets/LoginServlet',
            data: {
                email: $( '#fieldEmail' )[0].value,
                senha: $( '#fieldSenha' )[0].value
            },
            type: 'post',
            dataType: 'json',
            success: function ( data, textStatus ) {
                if ( data.success ) {
                    $( '#mensagemErro' ).hide();
                    window.location = 'principal.jsp';
                } else {
                    $( '#mensagemErro' ).show();
                }
            }
        });
    });

    /*
     * Abre o diálogo de esqueci a senha
     */
    $( '#btnEsqueciSenha' ).click( function() {
        $( '#camadaEsqueciSenha' ).fadeIn();
        $( '#overlay' ).fadeIn();
        $( '#fieldRecuperarSenha' ).focus();
    });

    /*
     * Fecha o diálogo de esqueci a senha
     */
    $( '#btnFecharRecuperarSenha' ).click( function() {
        $( '#camadaEsqueciSenha' ).fadeOut();
        $( '#overlay' ).fadeOut();
    });

    /*
     * Caso o usuário tecle enter no campo de senha, executa o login
     */
    $( '#fieldSenha' ).keypress( function( evt ) {
        if ( evt.keyCode == 13 ) {
            $( '#btnLogin' ).click();
        }
    });

    /*
     * Executa a recuperação de senha
     */
    $( '#btnRecuperarSenha' ).click( function() {
        $.ajax({
            url: contexto + '/servlets/RecuperaSenhaServlet',
            data: {
                email: $( '#fieldRecuperarSenha' )[0].value
            },
            type: 'post',
            dataType: 'json',
            beforeSend: function ( xmlhttp ) {
                $( '#mensagemSenha' ).removeClass( 'ok' );
                $( '#mensagemSenha' ).removeClass( 'erro' );
                $( '#mensagemSenha' ).html( $.getMsg( 'aguarde' ) );
                $( '#btnRecuperarSenha' )[0].disabled = true;
            },
            success: function ( data, textStatus ) {
                if ( data.success ) {
                    $( '#mensagemSenha' ).removeClass( 'erro' );
                    $( '#mensagemSenha' ).addClass( 'ok' );
                    $( '#mensagemSenha' ).html( $.getMsg( 'msgSenhaEnviada' ) );
                    $( '#btnRecuperarSenha' )[0].disabled = false;

                    alert( $.getMsg( 'msgSenhaEnviada' ) );
                    $( '#camadaEsqueciSenha' ).fadeOut();
                    $( '#overlay' ).fadeOut();

                } else {
                    $( '#mensagemSenha' ).removeClass( 'ok' );
                    $( '#mensagemSenha' ).addClass( 'erro' );
                    $( '#mensagemSenha' ).html( $.getMsg( 'msgEmailIncorreto' ) );
                    $( '#btnRecuperarSenha' )[0].disabled = false;
                }
            }
        });
    });

    /*
     * Se o usuário teclar enter no campo de recueperação de senha,
     * executa a recuperação.
     */
    $( '#fieldRecuperarSenha' ).keypress( function( evt ) {
        if ( evt.keyCode == 13 ) {
            $( '#btnRecuperarSenha' ).click();
        }
    });

    /*
     * Abre a janela de cadastro
     */
    $( '#btnCadastrese' ).click( function() {
        carregaPaises( '#comboPaisc' );
        $( '#camadaCadastrarUsuario' ).fadeIn();
        $( '#overlay' ).fadeIn();
    });

    /*
     * Fecha a janela de cadastro
     */
    $( '#btnFecharCadastrarUsuario' ).click( function() {
        $( '#camadaCadastrarUsuario' ).fadeOut();
        $( '#overlay' ).fadeOut();
    });

    /*
     * Executa o cadastro do usuário
     */
    $( '#btnCadastrarUsuario' ).click( function() {

        $( '#mensagemUsuario' ).removeClass( 'ok' );
        $( '#mensagemUsuario' ).removeClass( 'erro' );
        $( '#mensagemUsuario' ).html( $.getMsg( 'aguarde' ) );

        var submeter = validar({
            campos: [
                { id: '#fieldEmailc', requerido: true, verificaTamanho: true, max: 150, verificaEmail: true },
                { id: '#fieldSenhac', requerido: true, verificaTamanho: true, min: 8, max: 16 },
                { id: '#fieldRepitaSenhac', requerido: false, verificaTamanho: false, verificaIgualdade: true, igualA: '#fieldSenhac' },
                { id: '#fieldPrimeiroNomec', requerido: true, verificaTamanho: true, max: 30 },
                { id: '#fieldNomeMeioc', requerido: false, verificaTamanho: true, max: 30 },
                { id: '#fieldUltimoNomec', requerido: true, verificaTamanho: true, max: 50 },
                { id: '#fieldDataNascc', requerido: true, verificaTamanho: false, verificaData: true },
                { id: '#comboSexoc', requerido: true, verificaTamanho: false, ehCombo: true },
                { id: '#comboEscolaridadec', requerido: true, verificaTamanho: false, ehCombo: true },
                { id: '#fieldOcupacaoc', requerido: false, verificaTamanho: true, max: 100 },
                { id: '#fieldComplementoc', requerido: false, verificaTamanho: true, max: 50 },
                { id: '#comboPaisc', requerido: true, verificaTamanho: false, ehCombo: true },
                { id: '#comboEstadoc', requerido: false, verificaTamanho: false, ehCombo: true },
                { id: '#comboCidadec', requerido: false, verificaTamanho: false, ehCombo: true }
            ]
        });

        if ( submeter ) {

            $.post(
                contexto + '/servlets/CadastroNovoUsuarioServlet',
                {
                    email: $( '#fieldEmailc' )[0].value,
                    senha: $( '#fieldSenhac' )[0].value,
                    primeiroNome: $( '#fieldPrimeiroNomec' )[0].value,
                    nomeMeio: $( '#fieldNomeMeioc' )[0].value,
                    ultimoNome: $( '#fieldUltimoNomec' )[0].value,
                    dataNascimento: $( '#fieldDataNascc' )[0].value,
                    sexo: $( '#comboSexoc' )[0].value,
                    escolaridade: $( '#comboEscolaridadec' )[0].value,
                    ocupacao: $( '#fieldOcupacaoc' )[0].value,
                    rua: $( '#fieldRuac' )[0].value,
                    numero: $( '#fieldNumeroc' )[0].value,
                    complemento: $( '#fieldComplementoc' )[0].value,
                    pais: $( '#comboPaisc' )[0].value,
                    estado: $( '#comboEstadoc' )[0].value,
                    cidade: $( '#comboCidadec' )[0].value
                },
                function( data, textStatus ) {
                    if ( data.success ) {
                        var mensagem = $.getMsg( 'msgUsuarioCadastrado' );
                        $( '#mensagemUsuario' ).addClass( 'ok' );
                        if ( data.envioEmail ) {
                            mensagem += '<br/>' + $.getMsg( 'msgEmailDadosEnviado' );
                        } else {
                            mensagem += '<br/>' + $.getMsg( 'msgFalhaEmailDados' );
                        }
                        $( '#mensagemUsuario' ).html( mensagem );

                        alert( mensagem.replace( '<br/>', '\n' ) );
                        $( '#camadaCadastrarUsuario' ).fadeOut();
                        $( '#overlay' ).fadeOut();

                    } else {
                        $( '#mensagemUsuario' ).addClass( 'erro' );
                        $( '#mensagemUsuario' ).html( $.getMsg( 'msgErroCadastro' ) );
                    }
                },
                'json'
            );

        } else {

            $( '#mensagemUsuario' ).addClass( 'erro' );
            $( '#mensagemUsuario' ).html( $.getMsg( 'msgErroValidacao' ) );

        }
        
    });

    /*
     * Limpa o formulário de cadastro
     */
    $( '#btnLimparFormUsuario' ).click( function() {
        $( '#formularioUsuarios' )[0].reset();
    });

    /*
     * Combo de estados carregado quando altera o de paises
     */
    $( '#comboPaisc' ).change( function( evt ) {
        carregaEstados( '#comboEstadoc', evt.target.value );
    });

    /*
     * Combo de cidades carregado quanto altera o de estados
     */
    $( '#comboEstadoc' ).change( function( evt ) {
        carregaCidades( '#comboCidadec', evt.target.value );
    });

    /*
     * Valida o formulário INTEIRO e retorna true ou false.
     * Usa um objeto de configuração para testar os campos.
     */
    var validar = function( opcoes ) {

        var idCamadaErro = '#camadaErro';
        var idMsgErro = '#mErro';
        var c = opcoes.campos;
        var retorno = true;
        var mensagemRequerido = $.getMsg( 'validador.requerido' );
        var mensagemSelecione = $.getMsg( 'validador.selecione' );
        var mensagemTamanhoMaximo = $.getMsg( 'validador.maximo' );
        var mensagemTamanhoMinimo = $.getMsg( 'validador.minimo' );
        var mensagemData = $.getMsg( 'validador.data' );
        var mensagemEmail = $.getMsg( 'validador.email' );
        var mensagemSenha = $.getMsg( 'validador.senha' );

        for ( var i = 0; i < c.length; i++ ) {

            var valor = $( c[i].id )[0].value;
            $( c[i].id ).removeClass( 'erroCampo okCampo' );
            $( c[i].id ).unbind( 'mouseover' );
            $( c[i].id ).unbind( 'mouseout' );

            if ( c[i].requerido ) {
                if ( valor == '' ) {
                    retorno = false;
                    $( c[i].id ).addClass( 'erroCampo' );
                    $( c[i].id ).bind( 'mouseover', function( evt ) {
                        $( idMsgErro ).html( mensagemRequerido );
                        $( idCamadaErro ).css( 'left', evt.clientX + 10 );
                        $( idCamadaErro ).css( 'top', evt.clientY - 20 );
                        $( idCamadaErro ).show();
                    });
                    $( c[i].id ).bind( 'mouseout', function( evt ) {
                        $( idMsgErro ).html('');
                        $( idCamadaErro ).hide();
                    });
                    continue;
                } else if ( c[i].ehCombo ) {
                    if ( valor != 'S' ) {
                        $( c[i].id ).addClass( 'okCampo' );
                    } else {
                        retorno = false;
                        $( c[i].id ).addClass( 'erroCampo' );
                        $( c[i].id ).bind( 'mouseover', function( evt ) {
                            $( idMsgErro ).html( mensagemSelecione );
                            $( idCamadaErro ).css( 'left', evt.clientX + 10 );
                            $( idCamadaErro ).css( 'top', evt.clientY - 20 );
                            $( idCamadaErro ).show();
                        });
                        $( c[i].id ).bind( 'mouseout', function( evt ) {
                            $( idMsgErro ).html('');
                            $( idCamadaErro ).hide();
                        });
                    }
                    continue;
                } else if ( c[i].verificaEmail ) {
                    if ( validaEmail( valor ) ) {
                        $( c[i].id ).addClass( 'okCampo' );
                    } else {
                        retorno = false;
                        $( c[i].id ).addClass( 'erroCampo' );
                        $( c[i].id ).bind( 'mouseover', function( evt ) {
                            $( idMsgErro ).html( mensagemEmail );
                            $( idCamadaErro ).css( 'left', evt.clientX + 10 );
                            $( idCamadaErro ).css( 'top', evt.clientY - 20 );
                            $( idCamadaErro ).show();
                        });
                        $( c[i].id ).bind( 'mouseout', function( evt ) {
                            $( idMsgErro ).html('');
                            $( idCamadaErro ).hide();
                        });
                    }
                    continue;
                } else {
                    $( c[i].id ).addClass( 'okCampo' );
                }
            } else {
                if ( c[i].ehCombo ) {
                    $( c[i].id ).addClass( 'okCampo' );
                    continue;
                }
            }

            $( c[i].id ).removeClass( 'erroCampo okCampo' );

            if ( c[i].verificaTamanho ) {
                if ( !c[i].min || valor.length >= c[i].min ) {
                    if ( valor.length <= c[i].max ) {
                        $( c[i].id ).addClass( 'okCampo' );
                    } else {
                        retorno = false;
                        $( c[i].id ).addClass( 'erroCampo' );
                        $( c[i].id ).bind( 'mouseover', { max: c[i].max }, function( evt ) {
                            $( idMsgErro ).html( mensagemTamanhoMaximo + evt.data.max );
                            $( idCamadaErro ).css( 'left', evt.clientX + 10 );
                            $( idCamadaErro ).css( 'top', evt.clientY - 20 );
                            $( idCamadaErro ).show();
                        });
                        $( c[i].id ).bind( 'mouseout', function( evt ) {
                            $( idMsgErro ).html('');
                            $( idCamadaErro ).hide();
                        });
                    }
                } else {
                    retorno = false;
                    $( c[i].id ).addClass( 'erroCampo' );
                    $( c[i].id ).bind( 'mouseover', { min: c[i].min }, function( evt ) {
                        $( idMsgErro ).html( mensagemTamanhoMinimo + evt.data.min );
                        $( idCamadaErro ).css( 'left', evt.clientX + 10 );
                        $( idCamadaErro ).css( 'top', evt.clientY - 20 );
                        $( idCamadaErro ).show();
                    });
                    $( c[i].id ).bind( 'mouseout', function( evt ) {
                        $( idMsgErro ).html('');
                        $( idCamadaErro ).hide();
                    });
                }
                continue;
            }

            $( c[i].id ).removeClass( 'erroCampo okCampo' );

            if ( c[i].verificaData ) {
                if ( validaData( valor ) ) {
                    $( c[i].id ).addClass( 'okCampo' );
                } else {
                    retorno = false;
                    $( c[i].id ).addClass( 'erroCampo' );
                    $( c[i].id ).bind( 'mouseover', function( evt ) {
                        $( idMsgErro ).html( mensagemData );
                        $( idCamadaErro ).css( 'left', evt.clientX + 10 );
                        $( idCamadaErro ).css( 'top', evt.clientY - 20 );
                        $( idCamadaErro ).show();
                    });
                    $( c[i].id ).bind( 'mouseout', function( evt ) {
                        $( idMsgErro ).html('');
                        $( idCamadaErro ).hide();
                    });
                }
                continue;
            }

            $( c[i].id ).removeClass( 'erroCampo okCampo' );

            if ( c[i].verificaIgualdade ) {
                if ( valor == $( '#fieldSenhac' )[0].value ) {
                    $( c[i].id ).addClass( 'okCampo' );
                } else {
                    retorno = false;
                    $( c[i].id ).addClass( 'erroCampo' );
                    $( c[i].id ).bind( 'mouseover', function( evt ) {
                        $( idMsgErro ).html( mensagemSenha );
                        $( idCamadaErro ).css( 'left', evt.clientX + 10 );
                        $( idCamadaErro ).css( 'top', evt.clientY - 20 );
                        $( idCamadaErro ).show();
                    });
                    $( c[i].id ).bind( 'mouseout', function( evt ) {
                        $( idMsgErro ).html('');
                        $( idCamadaErro ).hide();
                    });
                }
            }

        }
        
        return retorno;

    };

    /*
     * Valida uma data
     */
    var validaData = function( data ) {
        if ( /^[0-3]\d\/[0-1]\d\/\d{4}$/.test( data ) ) {
            var tokens = data.split( '/' );
            var d = Number(tokens[0]);
            var m = Number(tokens[1]);
            if ( d > 31 )
                return false;
            if ( m > 12 )
                return false;
            return true;
        }
        return false;
    };

    /*
     * Valida e-mail
     */
    var validaEmail = function( valor ) {
        var existeEmail = false;
        if ( /^([A-Za-z0-9]{1,}([-_\.'][A-Za-z0-9]{1,}){0,}){1,}@(([A-Za-z0-9]{1,}[-]{0,1})\.){1,}[A-Za-z]{2,6}$/.test( valor ) ) {
            $.ajax({
                url: contexto + '/ajax/usuarios/existeEmail.jsp',
                type: 'post',
                data: {
                    email: valor
                },
                async: false,
                dataType: 'json',
                cache: false,
                success: function( data, textStatus ) {
                    existeEmail = data.existeEmail;
                }
            });
            return !existeEmail;
        } else {
            return false;
        }
    };

    /*
     * Faz a carga de países
     */
    var carregaPaises = function( idCombo ) {
        var itens = '<option value="S">' + $.getMsg( 'selecione' ) + '</option>';
        $.get( contexto + '/ajax/usuarios/paises.jsp',
            null,
            function( data, textStatus ) {
                if ( textStatus == 'success' ) {
                    for ( var i = 0; i < data.paises.length; i++ ) {
                        itens += '<option value="' + data.paises[i].id + '">' + data.paises[i].nome + '</option>'
                    }
                    $( idCombo ).html( itens );
                }
            },
            'json'
        );
    };

    /*
     * Faz a carga de estados
     */
    var carregaEstados = function( idCombo, valorIdPais ) {
        var itens = '<option value="S">' + $.getMsg( 'selecione' ) + '</option>';
        $.get( contexto + '/ajax/usuarios/estadosPorPais.jsp',
            {
                idPais: valorIdPais
            },
            function( data, textStatus ) {
                if ( textStatus == 'success' ) {
                    for ( var i = 0; i < data.estados.length; i++ ) {
                        itens += '<option value="' + data.estados[i].id + '">' + data.estados[i].nome + '</option>'
                    }
                    $( idCombo ).html( itens );
                }
            },
            'json'
        );
    };

    /*
     * Faz a carga de cidades
     */
    var carregaCidades = function( idCombo, valorIdEstado ) {
        var itens = '<option value="S">' + $.getMsg( 'selecione' ) + '</option>';
        $.get( contexto + '/ajax/usuarios/cidadesPorEstado.jsp',
            {
                idEstado: valorIdEstado
            },
            function( data, textStatus ) {
                if ( textStatus == 'success' ) {
                    for ( var i = 0; i < data.cidades.length; i++ ) {
                        itens += '<option value="' + data.cidades[i].id + '">' + data.cidades[i].nome + '</option>'
                    }
                    $( idCombo ).html( itens );
                }
            },
            'json'
        );
    };

    /*
     * Faz a carga da tabela de Strings através do loadI18N e associa
     * ao. Após a carga a função getMsg pode ser usada.
     */
    $.loadI18N({
        resourceBundle: contexto + '/i18n/TabelaStringsIndex',
        resourceExt: '.jsp'
    });

    /*
     * Internacionaliza todos os labels do index.
     */
    // boas vindas
    $( '#bemVindo' ).html( $.getMsg( 'bemVindo' ) );
    $( '#texto1' ).html( $.getMsg( 'texto1' ) );
    $( '#texto2' ).html( $.getMsg( 'texto2' ) );

    // imagens voltar e autenticar
    $( '#btnTestar' ).attr( "src", $( '#btnTestar').attr( 'src' ) + $.getMsg( 'imagemTestar' ) );
    $( '#btnAutenticar' ).attr( "src", $( '#btnAutenticar').attr( 'src' ) + $.getMsg( 'imagemAutenticar' ) );

    // login
    $( '#lblLogin' ).html( $.getMsg( 'login' ) );
    $( '#lblSenhaLogin' ).html( $.getMsg( 'senhaLogin' ) );
    $( '#mensagemErro' ).html( $.getMsg( 'erroSenha' ) );
    $( '#btnCadastrese' ).html( $.getMsg( 'cadastrese' ) );
    $( '#btnEsqueciSenha' ).html( $.getMsg( 'esqueciSenha' ) );

    // recuperar senha
    $( '#lblEnviarSenha' ).html( $.getMsg( 'enviarSenha' ) );
    $( '#lblEmailEnviarSenha' ).html( $.getMsg( 'email' ) );
    $( '#btnRecuperarSenha' )[0].value = $.getMsg( 'enviar' );

    // novo usuário
    $( '#lblNovoUsuario' ).html( $.getMsg( 'novoUsuario' ) );
    $( '#lblEmail' ).html( $.getMsg( 'email' ) );
    $( '#lblSenha' ).html( $.getMsg( 'senha' ) );
    $( '#lblRepitaSenha' ).html( $.getMsg( 'repitaSenha' ) );
    $( '#lblPrimeiroNome' ).html( $.getMsg( 'primeiroNome' ) );
    $( '#lblNomeMeio' ).html( $.getMsg( 'nomeMeio' ) );
    $( '#lblUltimoNome' ).html( $.getMsg( 'ultimoNome' ) );
    $( '#lblDataNasc' ).html( $.getMsg( 'dataNasc' ) );
    $( '#lblSexo' ).html( $.getMsg( 'sexo' ) );
    $( '#lblEscolaridade' ).html( $.getMsg( 'escolaridade' ) );
    $( '#lblOcupacao' ).html( $.getMsg( 'ocupacao' ) );
    $( '#lblRua' ).html( $.getMsg( 'rua' ) );
    $( '#lblNumero' ).html( $.getMsg( 'numero' ) );
    $( '#lblComplemento' ).html( $.getMsg( 'complemento' ) );
    $( '#lblPais' ).html( $.getMsg( 'pais' ) );
    $( '#lblEstado' ).html( $.getMsg( 'estado' ) );
    $( '#lblCidade' ).html( $.getMsg( 'cidade' ) );

    $( '#optSSexo' ).html( $.getMsg( 'selecione' ) );
    $( '#optMSexo' ).html( $.getMsg( 'sexo.masc' ) );
    $( '#optFSexo' ).html( $.getMsg( 'sexo.fem' ) );

    $( '#optSEsc' ).html( $.getMsg( 'selecione' ) );
    $( '#optEFCEsc' ).html( $.getMsg( 'escolaridade.efc' ) );
    $( '#optEFIEsc' ).html( $.getMsg( 'escolaridade.efi' ) );
    $( '#optEMCEsc' ).html( $.getMsg( 'escolaridade.emc' ) );
    $( '#optEMIEsc' ).html( $.getMsg( 'escolaridade.emi' ) );
    $( '#optESCEsc' ).html( $.getMsg( 'escolaridade.esc' ) );
    $( '#optESIEsc' ).html( $.getMsg( 'escolaridade.esi' ) );
    $( '#optESPEsc' ).html( $.getMsg( 'escolaridade.esp' ) );
    $( '#optMSCEsc' ).html( $.getMsg( 'escolaridade.msc' ) );
    $( '#optPHDEsc' ).html( $.getMsg( 'escolaridade.phd' ) );

    $( '#optSPais' ).html( $.getMsg( 'selecione' ) );
    $( '#optSEstado' ).html( $.getMsg( 'selecione' ) );
    $( '#optSCidade' ).html( $.getMsg( 'selecione' ) );

    $( '#pCmpObrigatorio' ).html( $.getMsg( 'camposObrigatorios' ) );

    $( '#btnCadastrarUsuario' )[0].value = $.getMsg( 'cadastrar' );
    $( '#btnLimparFormUsuario' )[0].value = $.getMsg( 'limpar' );

    $( '#btnVoltar' ).html( $.getMsg( 'voltar' ) );
    $( '#lblVersaoPrincipal' ).html( $.getMsg( 'versao' ) );
    $( '#lblVersaoLogin' ).html( $.getMsg( 'versao' ) );

}