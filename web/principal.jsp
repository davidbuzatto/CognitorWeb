<%-- 
    Document   : principal
    Created on : Jul 21, 2009, 4:01:54 PM
    Author     : David
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>CognitorWeb</title>
        <link href="${pageContext.request.contextPath}/imagens/icones/favicon.ico" type="image/x-icon" rel="icon"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/javascript/ext-3.2.0/resources/css/reset-min.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/javascript/ext-3.2.0/resources/css/ext-all.css"/>
        <c:choose>
            <c:when test="${not empty sessionScope.usuario}">
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosPrincipal.css"/>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/styles.css"/>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/javascript/ext-plugins/SWFUpload/SwfUploadPanel.css"/>
            </c:when>
            <c:otherwise>
                <%-- Usa os estilos do index para mostrar o erro de não estar autenticado --%>
                <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosIndex.css"/>
            </c:otherwise>
        </c:choose>
    </head>
    <body>

        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery/jquery-1.3.2.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery-plugins/jquery.i18n.min.js"></script>

        <%-- Carga de i18n --%>
        <script type="text/javascript">
            $.loadI18N({
                resourceBundle: '${pageContext.request.contextPath}/i18n/TabelaStringsPrincipal',
                resourceExt: '.jsp'
            });
        </script>

        <c:choose>
            <c:when test="${not empty sessionScope.usuario}">

                <div id="loading-mask" class="mascaraCarregando">
                </div>
                <div id="loading" class="carregando">
                    <div class="indicadorCarregando">
                        <img alt="carregando" src="/CognitorWeb/imagens/logoCognitorCarregando.png" style="float:left;vertical-align:top;"/>
                        <br/>
                        <label id="lblVersao"></label> ${applicationScope.configs.versao}
                        <br/>
                        <span id="loading-msg" class="mensagemCarregando"></span>
                    </div>
                </div>

                <%-- ExtJS e jQuery --%>
                <script type="text/javascript">
                    $( '#lblVersao' ).html( $.getMsg( 'versao' ) );
                    $( '#loading-msg' ).html( $.getMsg( 'carregando' ) );
                    $( '#loading-msg' ).html( $.getMsg( 'carregandoExt' ) );
                </script>
                <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-3.2.0/adapter/jquery/ext-jquery-adapter.js"></script>
                <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-3.2.0/ext-all.js"></script>

                <%-- EX-JSGraphics --%>
                <script type="text/javascript">
                    $( '#loading-msg' ).html( $.getMsg( 'carregandoWZJSGraphics' ) );
                </script>
                <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/wz-jsgraphics/wz-jsgraphics-3.05.min.js"></script>

                <%-- FlowPlayer --%>
                <script type="text/javascript">
                    $( '#loading-msg' ).html( $.getMsg( 'carregandoFlowPlayer' ) );
                </script>
                <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/flowplayer/flowplayer-3.1.4.min.js"></script>

                <%-- 
                    Plugins.
                    Em produção é feita a carga dos plugins minimizados.
                --%>
                <script type="text/javascript">
                    $( '#loading-msg' ).html( $.getMsg( 'carregandoPlugins' ) );
                </script>

                <c:choose>
                    <c:when test="${applicationScope.configs.producao}">
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins.min.js"></script>
                    </c:when>
                    <c:otherwise>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/statusBar/Ext.ux.StatusBar.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/i18n/PropertyReader.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/i18n/Bundle.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.MidasCommand.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.Divider.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.HR.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.Imagem.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.Video.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.Som.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.IndentOutdent.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.RemoveFormat.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.SpecialCharacters.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.SubSuperScript.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.Table.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/htmlEditorCog/Ext.ux.form.HtmlEditorCog.Word.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/SWFUpload/swfupload.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/ext-plugins/swfbtn/Ext.ux.swfbtn.js"></script>
                    </c:otherwise>
                </c:choose>
                
                <%--
                    Arquivos de script da aplicação.
                    Em produção é feita a carga da aplicação minimizada.
                --%>
                <script type="text/javascript">
                    $( '#loading-msg' ).html( $.getMsg( 'carregandoAplicacao' ) );
                </script>

                <c:choose>
                    <c:when test="${applicationScope.configs.producao}">
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/application.min.js"></script>
                    </c:when>
                    <c:otherwise>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/global.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/uteis.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/vtypes.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/metadados.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/desenhos.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/estruturacaoConhecimento.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/analogias.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/usuarios.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/dadosPessoais.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/configuracoes.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/telaInicial.js"></script>
                        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/application.js"></script>
                    </c:otherwise>
                </c:choose>

            </c:when>
            <c:otherwise>

                <div class="camadaLogo">
                    <img alt="Cognitor" src="${pageContext.request.contextPath}/imagens/logoCognitor.png"/>
                </div>

                <div id="camadaNaoAutenticado" class="camadaNaoAutenticado">
                    <table class="tabelaPrincipal">
                        <tr>
                            <td colspan="2">
                                <p id="erroNaoAutenticado" class="titulo"></p>
                                <script type="text/javascript">$( '#erroNaoAutenticado' ).html( $.getMsg( 'erroNaoAutenticado' ) );</script>
                                <br/>
                            </td>
                        </tr>
                        <tr class="centralizar">
                            <td>
                                <a href="${pageContext.request.contextPath}/index.jsp">
                                    <img id="btnVoltar" alt="Voltar - Back" class="botaoImagem" src="${pageContext.request.contextPath}/imagens/"/>
                                </a>
                                <script type="text/javascript">
                                    $( '#btnVoltar' ).attr( "src", $( '#btnVoltar').attr( 'src' ) + $.getMsg( 'imagemVoltar' ) );
                                </script>
                            </td>
                        </tr>
                    </table>
                </div>

                <jsp:include page="/fragmentos/logotipos.jsp"/>
                
            </c:otherwise>
        </c:choose>
        
    </body>
</html>
