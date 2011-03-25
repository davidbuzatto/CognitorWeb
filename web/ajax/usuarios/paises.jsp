<%-- 
    Document   : paises
    Created on : Sep 22, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<htag:query var="paises" scope="page">
    from Pais
</htag:query>

{
    success: true,
    paises: [
        <c:forEach items="${paises}" var="pais">
            { id: '${pais.id}', nome: '${pais.nome}' },
        </c:forEach>
    ]
}