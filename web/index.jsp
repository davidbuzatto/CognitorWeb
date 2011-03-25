<%-- 
    Document   : index
    Created on : May 21, 2009, 9:38:56 AM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>CognitorWeb</title>
        <link href="${pageContext.request.contextPath}/imagens/icones/favicon.ico" type="image/x-icon" rel="icon"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/javascript/ext-3.2.0/resources/css/reset-min.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/javascript/ext-3.2.0/resources/css/ext-all.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosIndex.css"/>
    </head>
    <body>

        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery/jquery-1.3.2.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery-plugins/jquery.i18n.min.js"></script>
        <%--script type="text/javascript" src="${pageContext.request.contextPath}/javascript/index.js"></script--%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/index.min.js"></script>
        <script type="text/javascript">
            $( document ).ready( function() {
                start( '${pageContext.request.contextPath}' );
            });
        </script>
            
        <div id="overlay" class="overlay">
        </div>

        <div class="camadaLogo">
            <img alt="Cognitor" src="${pageContext.request.contextPath}/imagens/logoCognitor.png"/>
        </div>

        <div id="camadaPrincipal" class="camadaPrincipal">
            <table class="tabelaPrincipal">
                <tr>
                    <td colspan="2">
                        <p id="bemVindo" class="titulo">
                        </p>
                        <br/>
                        <p id="texto1" class="justificar">
                        </p>
                        <br/>
                        <p id="texto2" class="justificar">
                        </p>
                        <br/>
                    </td>
                </tr>
                <tr class="centralizar">
                    <td class="direita">
                        <img id="btnTestar" alt="Testar" class="botaoImagem" src="${pageContext.request.contextPath}/imagens/"/>
                    </td>
                    <td>
                        <img id="btnAutenticar" alt="Autenticar" class="botaoImagem" src="${pageContext.request.contextPath}/imagens/"/>
                    </td>
                </tr>
            </table>
        </div>

        <div id="camadaLogin" class="camadaLogin">
            <table class="tabelaLogin">
                <tr>
                    <td>
                        <label id="lblLogin"></label>
                    </td>
                    <td>
                        <label id="lblSenhaLogin"></label>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td>
                        <input id="fieldEmail" type="text" size="30" value=""/>
                    </td>
                    <td>
                        <input id="fieldSenha" type="password" size="12" value=""/>
                    </td>
                    <td>
                        <input id="btnLogin" type="button" value="Login"/>
                    </td>
                </tr>
                <tr class="centralizar">
                    <td colspan="3" class="erro">
                        <font id="mensagemErro"></font>
                    </td>
                </tr>
                <tr class="centralizar">
                    <td colspan="3">
                        <a id="btnCadastrese" href="#"></a>
                        <br/>
                        <a id="btnEsqueciSenha" href="#"></a>
                    </td>
                </tr>
            </table>
        </div>

        <div id="camadaEsqueciSenha" class="camadaEsqueciSenha">
            <table class="tabelaEsqueciSenha tituloEsqueciSenha" cellspacing="0">
                <tr>
                    <td><label id="lblEnviarSenha"></label></td>
                    <td align="right"><img id="btnFecharRecuperarSenha" alt="Fechar - Close" class="botaoImagem" src="${pageContext.request.contextPath}/imagens/btnFechar.png"/></td>
                </tr>
            </table>
            <br/>
            <table class="tabelaEsqueciSenha">
                <tr>
                    <td><label id="lblEmailEnviarSenha"></label></td>
                    <td><input id="fieldRecuperarSenha" type="text" size="30"/></td>
                </tr>
                <tr>
                    <td colspan="2" class="direita">
                        <input id="btnRecuperarSenha" type="button"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div id="mensagemSenha"></div>
                    </td>
                </tr>
            </table>
        </div>

        <div id="camadaCadastrarUsuario" class="camadaCadastrarUsuario">
            <table class="tabelaCamposUsuario tituloCadastrarUsuario" cellspacing="0">
                <tr>
                    <td colspan="2"><label id="lblNovoUsuario"></label></td>
                    <td align="right"><img id="btnFecharCadastrarUsuario" alt="Fechar - Close" class="botaoImagem" src="${pageContext.request.contextPath}/imagens/btnFechar.png"/></td>
                </tr>
            </table>
            <br/>
            <form id="formularioUsuarios">
                <table class="tabelaCamposUsuario">
                    <tr>
                        <td class="direita labelCamposUsuario" ><label id="lblEmail"></label></td>
                        <td>
                            <input id="fieldEmailc" type="text" size="25" class="okCampo"/>
                            <font class="campoOb"> *</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblSenha"></label></td>
                        <td>
                            <input id="fieldSenhac" type="password" size="10" class="okCampo"/>
                            <font class="campoOb"> *</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblRepitaSenha"></label></td>
                        <td><input id="fieldRepitaSenhac" type="password" size="10" class="okCampo"/></td>
                    </tr>
                </table>
                <br/>
                <table class="tabelaCamposUsuario">
                    <tr>
                        <td class="direita labelCamposUsuario"><label id="lblPrimeiroNome"></label></td>
                        <td>
                            <input id="fieldPrimeiroNomec" type="text" size="15" class="okCampo"/>
                            <font class="campoOb"> *</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblNomeMeio"></label></td>
                        <td><input id="fieldNomeMeioc" type="text" size="15" class="okCampo"/></td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblUltimoNome"></label></td>
                        <td>
                            <input id="fieldUltimoNomec" type="text" size="15" class="okCampo"/>
                            <font class="campoOb"> *</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblDataNasc"></label></td>
                        <td>
                            <input id="fieldDataNascc" type="text" size="10" class="okCampo"/>
                            <font class="campoOb"> *</font>
                            <font class="campoObs"> (dd/mm/aaaa)</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblSexo"></label></td>
                        <td>
                            <select id="comboSexoc" class="okCampo">
                                <option id="optSSexo" value="S"></option>
                                <option id="optMSexo" value="M"></option>
                                <option id="optFSexo" value="F"></option>
                            </select>
                            <font class="campoOb"> *</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblEscolaridade"></label></td>
                        <td>
                            <select id="comboEscolaridadec" class="okCampo" style="width:200px">
                                <option id="optSEsc" value="S"></option>
                                <option id="optEFCEsc" value="EFC"></option>
                                <option id="optEFIEsc" value="EFI"></option>
                                <option id="optEMCEsc" value="EMC"></option>
                                <option id="optEMIEsc" value="EMI"></option>
                                <option id="optESCEsc" value="ESC"></option>
                                <option id="optESIEsc" value="ESI"></option>
                                <option id="optESPEsc" value="ESP"></option>
                                <option id="optMSCEsc" value="MSC"></option>
                                <option id="optPHDEsc" value="PHD"></option>
                            </select>
                            <font class="campoOb"> *</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblOcupacao"></label></td>
                        <td><input id="fieldOcupacaoc" type="text" size="20" class="okCampo"/></td>
                    </tr>
                </table>
                <br/>
                <table class="tabelaCamposUsuario">
                    <tr>
                        <td class="direita labelCamposUsuario"><label id="lblRua"></label></td>
                        <td><input id="fieldRuac" type="text" size="25" class="okCampo"/></td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblNumero"></label></td>
                        <td><input id="fieldNumeroc" type="text" size="5" class="okCampo"/></td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblComplemento"></label></td>
                        <td><input id="fieldComplementoc" type="text" size="15" class="okCampo"/></td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblPais"></label></td>
                        <td>
                            <select id="comboPaisc" class="okCampo" style="width:200px">
                                <option id="optSPais" value="S"></option>
                            </select>
                            <font class="campoOb"> *</font>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblEstado"></label></td>
                        <td>
                            <select id="comboEstadoc" class="okCampo" style="width:150px">
                                <option id="optSEstado" value="S"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="direita"><label id="lblCidade"></label></td>
                        <td>
                            <select id="comboCidadec" class="okCampo" style="width:200px">
                                <option id="optSCidade" value="S"></option>
                            </select>
                        </td>
                    </tr>
                </table>
                <p id="pCmpObrigatorio" class="campoObb"></p>
                <br/>
                <table class="tabelaCamposUsuario">
                    <tr>
                        <td colspan="3" class="direita">
                            <input id="btnCadastrarUsuario" type="button"/>
                            <input id="btnLimparFormUsuario" type="button"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <div id="mensagemUsuario"></div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <div id="camadaErro" class="camadaErro">
            <table>
                <tr>
                    <td><img src="${pageContext.request.contextPath}/imagens/icones/exclamation.png"/></td>
                    <td id="mErro"></td>
                </tr>
            </table>
        </div>

        <div id="camadaVersaoPrincipal" class="camadaVersaoPrincipal">
            <label id="lblVersaoPrincipal"></label> ${applicationScope.configs.versao}
        </div>

        <div id="camadaVersaoLogin" class="camadaVersaoLogin">
            <table class="tabelaVersaoLogin">
                <tr>
                    <td class="esquerda">
                        <a id="btnVoltar" href="#"></a>
                    </td>
                    <td class="direita">
                        <label id="lblVersaoLogin"></label> ${applicationScope.configs.versao}
                    </td>
                </tr>
            </table>
        </div>

        <jsp:include page="/fragmentos/logotipos.jsp"/>

    </body>
</html>
