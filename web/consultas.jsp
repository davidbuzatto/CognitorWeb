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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/javascript/ext-3.1.1/resources/css/reset-min.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/javascript/ext-3.1.1/resources/css/ext-all.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilosConsultas.css"/>
    </head>
    <body>

        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery/jquery-1.3.2.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/jquery-plugins/jquery.i18n.min.js"></script>
        <%--script type="text/javascript" src="${pageContext.request.contextPath}/javascript/consultas.js"></script--%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/javascript/consultas.min.js"></script>
        <script type="text/javascript">
            $( document ).ready( function() {
                start( '${pageContext.request.contextPath}' );
            });
        </script>

        <div id="overlay" class="overlay">
        </div>

        <div id="divCarregando" class="divCarregando">
            <div class="divIndicadorCarregando">
                <img alt="carregando" src="/CognitorWeb/imagens/logoCognitorCarregando.png" style="float:left;vertical-align:top;"/>
                <br/>
                <span id="loading-msg" class="mensagemCarregando"><label id="lblCarregando"></label></span>
            </div>
        </div>

        <div id="camadaAjuda" class="camadaAjuda">
            <table>
                <tr>
                    <td>
                        <img src="${pageContext.request.contextPath}/imagens/icones/help.png"/>
                        <label id="lblAjuda" class="cabMsgAjuda"></label>
                        <br/>
                        <div id="mAjuda" class="msgAjuda"></div>
                    </td>
                </tr>
            </table>
        </div>

        <center style="padding-top: 30px;">
            <span id="body">
                <center>
                    <br clear="all" id="lgpd"/>
                    <img src="${pageContext.request.contextPath}/imagens/logoCognitor.png" alt="Cognitor" title="Cognitor"/>
                    <br/>
                    <br/>
                    <center><label id="lblConsMat"></label></center>
                    <table cellspacing="0" cellpadding="5">
                        <tbody>
                            <tr valign="top">
                                <td width="25%">
                                </td>
                                <td align="center">
                                    <div id="divPesquisaNormal" class="divPesquisa">
                                        <table cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td>
                                                    <input id="campoPesq" value="" type="text" size="55"/>
                                                </td>
                                                <td valign="top">
                                                    <div class="divBtnPesquisa">
                                                        <a id="btnPesquisaAvancada" href="#"><label id="lblPesqAvan" class="btn"></label></a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div id="divPesquisaAvancada" class="divPesquisa">
                                        <table cellpadding="0" cellspacing="0">
                                            <tr>
                                                <td>
                                                    <table cellpadding="0" cellspacing="0" class="tbCamposPesquisa">
                                                        <tr>
                                                            <td class="tdLabelPesquisa">
                                                                <label id="lblTit"></label>
                                                            </td>
                                                            <td>
                                                                <input id="campoPesqTit" value="" type="text" size="35"/>
                                                                <img id="ajudaTit" src="${pageContext.request.contextPath}/imagens/icones/help.png" alt="ajuda/help" class="btn"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdLabelPesquisa">
                                                                <label id="lblDesc"></label>
                                                            </td>
                                                            <td>
                                                                <input id="campoPesqDesc" value="" type="text" size="50"/>
                                                                <img id="ajudaDesc" src="${pageContext.request.contextPath}/imagens/icones/help.png" alt="ajuda/help" class="btn"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td class="tdLabelPesquisa">
                                                                <label id="lblKey"></label>
                                                            </td>
                                                            <td>
                                                                <input id="campoPesqKey" value="" type="text" size="30"/>
                                                                <img id="ajudaKey" src="${pageContext.request.contextPath}/imagens/icones/help.png" alt="ajuda/help" class="btn"/>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td valign="top">
                                                    <div class="divBtnPesquisa">
                                                        <a id="btnPesquisaNormal" href="#"><label id="lblPesqNorm" class="btn"></label></a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                    <div class="divPesquisa">
                                        <input id="btnPesquisar" type="button" value=""/>
                                    </div>
                                </td>
                                <td width="25%">
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </center>
            </span>

            <table id="tbResultados" class="tbResultados" cellspacing="0" cellpadding="5">
            </table>

            <jsp:include page="/fragmentos/logotipos.jsp"/>
            
        </center>

    </body>

</html>
