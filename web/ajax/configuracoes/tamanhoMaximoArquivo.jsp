<%-- 
    Document   : configuracaoPorId
    Created on : Sep 28, 2009, 7:30:11 AM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">
        {
            success: true,
            valor: '${applicationScope.configs.tamanhoMaximoArquivo}'
        }
    </c:when>
    <c:otherwise>
        {
            success: false,
            errorMsg: 'Não logado - Not Logged In'
        }
    </c:otherwise>
</c:choose>
