<%-- 
    Document   : paginaPorId
    Created on : Oct 28, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">

        <htag:load var="pagina"
                   scope="page"
                   classe="Pagina"
                   identificador="${param.idPagina}"/>

        ${pagina.conteudoHtml}

    </c:when>
    <c:otherwise>
        <p>Não logado - Not Logged In</p>
    </c:otherwise>
</c:choose>
