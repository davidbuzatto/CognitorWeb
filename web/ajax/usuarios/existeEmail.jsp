<%-- 
    Document   : existeEmail
    Created on : Sep 24, 2009, 7:30:11 AM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>
<%@taglib uri="/WEB-INF/UtilTags.tld" prefix="util" %>

<htag:query var="usuarios" scope="page">
    from Usuario where email = '${param.email}'
</htag:query>

<util:getSize var="tamanho" collection="${usuarios}" scope="page"/>

{
    success: true,
    existeEmail:

    <c:choose>
        <c:when test="${tamanho > 0}">
            true
        </c:when>
        <c:otherwise>
            false
        </c:otherwise>
    </c:choose>
}
