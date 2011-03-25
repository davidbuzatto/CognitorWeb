<%-- 
    Document   : estadosPorPais
    Created on : Sep 22, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<htag:query var="estados" scope="page">
    from Estado where pais = ${param.idPais}
</htag:query>

{
    success: true,
    estados: [
        <c:forEach items="${estados}" var="estado">
            { id: '${estado.id}', nome: '${estado.nome}' },
        </c:forEach>
    ]
}