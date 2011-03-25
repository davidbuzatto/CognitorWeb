<%-- 
    Document   : usuarioPorId
    Created on : Sep 23, 2009, 7:30:11 AM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">
        <c:choose>
            <c:when test="${sessionScope.usuario.tipo eq 'A'}">

                <htag:load var="usuario"
                           scope="page"
                           classe="Usuario"
                           identificador="${param.id}"/>

                {
                    success: true,
                    data: {
                        id: '${usuario.id}',
                        email: '${usuario.email}',
                        senha: '${usuario.senha}',
                        senhaR: '${usuario.senha}',
                        tipo: '${usuario.tipo}',
                        primeiroNome: '${usuario.primeiroNome}',
                        nomeMeio: '${usuario.nomeMeio}',
                        ultimoNome: '${usuario.ultimoNome}',
                        dataNascimento: '<fmt:formatDate pattern="yyyy-MM-dd" value="${usuario.dataNascimento}"/>',
                        sexo: '${usuario.sexo}',
                        escolaridade: '${usuario.escolaridade}',
                        ocupacao: '${usuario.ocupacao}',
                        rua: '${usuario.rua}',
                        numero: '${usuario.numero}',
                        complemento: '${usuario.complemento}',
                        pais: '${usuario.pais.id}',
                        estado: '${usuario.estado.id}',
                        cidade: '${usuario.cidade.id}'
                    }
                }

            </c:when>
            <c:otherwise>
                {
                    success: false,
                    errorMsg: 'Não permitido - Not Allowed'
                }
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        {
            success: false,
            errorMsg: 'Não logado - Not Logged In'
        }
    </c:otherwise>
</c:choose>
