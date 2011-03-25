<%-- 
    Document   : materiaisPorUsuarioLogado
    Created on : Sep 22, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">

        <htag:query var="materiais" scope="page">
            from Material
            where usuario = ${sessionScope.usuario.id}
            order by compartilhado, titulo
        </htag:query>

        {
            success: true,
            materiais: [
                <c:forEach items="${materiais}" var="material">
                    { id: '${material.id}', titulo: '${material.titulo}', compartilhado: ${material.compartilhado} },
                </c:forEach>
            ]
        }

    </c:when>
    <c:otherwise>
        {
            success: false,
            errorMsg: 'Não logado - Not Logged In'
        }
    </c:otherwise>
</c:choose>