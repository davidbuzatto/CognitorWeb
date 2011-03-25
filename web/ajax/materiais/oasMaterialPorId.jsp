<%-- 
    Document   : oasMaterialPorId
    Created on : Dec 16, 2009, 12:41:32 PM
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
                   identificador="${param.id}"/>

        <util:getMaterialLOs var="estrutura"
                             material="${material}"
                             iconeGrupo="${pageContext.request.contextPath}/imagens/icones/book.png"
                             iconePagina="${pageContext.request.contextPath}/imagens/icones/page.png"
                             iconeImagem="${pageContext.request.contextPath}/imagens/icones/page_white_picture.png"
                             iconeVideo="${pageContext.request.contextPath}/imagens/icones/page_white_film.png"
                             iconeSom="${pageContext.request.contextPath}/imagens/icones/page_white_music.png"
                             scope="page"/>
        {
            success: true,
            oas: ${estrutura}
        }

    </c:when>
    <c:otherwise>
        {
            success: false,
            errorMsg: 'Não logado - Not Logged In'
        }
    </c:otherwise>
</c:choose>
