<%-- 
    Document   : materialPorId
    Created on : Sep 22, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">

        <htag:load var="material"
                   scope="page"
                   classe="Material"
                   identificador="${param.id}"/>
        
        {
            success: true,
            id: 'raizEstrutura-${material.id}',
            idInterno: '${material.id}',
            ordem: 0,
            compartilhado: ${material.compartilhado},
            estrCon: ${material.conhecimentoEstruturado},
            tipo: 'material',
            layout: '${material.layout}',
            titulo: '${material.titulo}'
        }

    </c:when>
    <c:otherwise>
        {
            success: false,
            errorMsg: 'Não logado - Not Logged In'
        }
    </c:otherwise>
</c:choose>
