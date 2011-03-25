<%-- 
    Document   : configuracoes
    Created on : Sep 22, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">
        <c:choose>
            <c:when test="${sessionScope.usuario.tipo eq 'A'}">

                <htag:query var="configs" scope="page">
                    from Configuracao
                </htag:query>

                {
                    success: true,
                    configuracoes:[
                        <c:forEach items="${configs}" var="config">
                            { id: '${config.id}', propriedade: '${config.propriedade}', valor: '${config.valor}' },
                        </c:forEach>
                    ]
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
