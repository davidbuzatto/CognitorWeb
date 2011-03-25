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
        <c:choose>
            <c:when test="${sessionScope.usuario.tipo eq 'A'}">

                <htag:load var="config"
                           scope="page"
                           classe="Configuracao"
                           identificador="${param.id}"/>

                {
                    success: true,
                    data: {
                        id: '${config.id}',
                        propriedade: '${config.propriedade}',
                        valor: '${config.valor}'
                    }
                }

            </c:when>
            <c:otherwise>
                {
                    success: false,
                    errorMsg: 'Não permitido - Not Allowed'
                }
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        {
            success: false,
            errorMsg: 'Não logado - Not Logged In'
        }
    </c:otherwise>
</c:choose>
