<%-- 
    Document   : usuarioLogado
    Created on : Sep 23, 2009, 7:30:11 AM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">
        {
            success: true,
            data: {
                id: '${sessionScope.usuario.id}',
                email: '${sessionScope.usuario.email}',
                senha: '${sessionScope.usuario.senha}',
                senhaR: '${sessionScope.usuario.senha}',
                primeiroNome: '${sessionScope.usuario.primeiroNome}',
                nomeMeio: '${sessionScope.usuario.nomeMeio}',
                ultimoNome: '${sessionScope.usuario.ultimoNome}',
                dataNascimento: '<fmt:formatDate pattern="yyyy-MM-dd" value="${sessionScope.usuario.dataNascimento}"/>',
                sexo: '${sessionScope.usuario.sexo}',
                escolaridade: '${sessionScope.usuario.escolaridade}',
                ocupacao: '${sessionScope.usuario.ocupacao}',
                rua: '${sessionScope.usuario.rua}',
                numero: '${sessionScope.usuario.numero}',
                complemento: '${sessionScope.usuario.complemento}',
                pais: '${sessionScope.usuario.pais.id}',
                estado: '${sessionScope.usuario.estado.id}',
                cidade: '${sessionScope.usuario.cidade.id}'
            }
        }
    </c:when>
    <c:otherwise>
        {
            success: false,
            errorMsg: 'Não logado - Not Logged In'
        }
    </c:otherwise>
</c:choose>
