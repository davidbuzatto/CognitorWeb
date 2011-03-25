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

        <htag:load var="pagina"
                   scope="page"
                   classe="Pagina"
                   identificador="${param.id}"/>

        {
            success: true,
            oas: [

            <c:forEach var="imagem" items="${pagina.imagens}">
                {
                    idInterno: ${imagem.id},
                    tipo: 'imagem',
                    text: '${imagem.titulo}',
                    icon: '${pageContext.request.contextPath}/imagens/icones/page_white_picture.png'
                },
            </c:forEach>

            <c:forEach var="video" items="${pagina.videos}">
                {
                    idInterno: ${video.id},
                    tipo: 'video',
                    text: '${video.titulo}',
                    icon: '${pageContext.request.contextPath}/imagens/icones/page_white_film.png'
                },
            </c:forEach>

            <c:forEach var="som" items="${pagina.sons}">
                {
                    idInterno: ${som.id},
                    tipo: 'som',
                    text: '${som.titulo}',
                    icon: '${pageContext.request.contextPath}/imagens/icones/page_white_music.png'
                },
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
