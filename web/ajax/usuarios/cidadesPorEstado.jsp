<%-- 
    Document   : cidadesPorEstado
    Created on : Sep 22, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<htag:query var="cidades" scope="page">
    from Cidade where estado = ${param.idEstado}
</htag:query>

{
    success: true,
    cidades: [
        <c:forEach items="${cidades}" var="cidade">
            { id: '${cidade.id}', nome: '${cidade.nome}' },
        </c:forEach>
    ]
}