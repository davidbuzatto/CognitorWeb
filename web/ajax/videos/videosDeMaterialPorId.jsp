<%-- 
    Document   : materiaisPorUsuarioLogado
    Created on : Sep 22, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>
<%@taglib uri="/WEB-INF/UtilTags.tld" prefix="util" %>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">

        <htag:load var="material"
                   scope="page"
                   classe="Material"
                   identificador="${param.idMaterial}"/>

        <util:getMaterialFiles var="videos"
                               material="${material}"
                               configs="${applicationScope.configs}"
                               tipo="video"
                               scope="page"/>

        {
            success: true,
            videos: ${videos}
        }

    </c:when>
    <c:otherwise>
        {
            success: false,
            errorMsg: 'Não logado - Not Logged In'
        }
    </c:otherwise>
</c:choose>