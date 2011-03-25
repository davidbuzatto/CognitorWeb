<%-- 
    Document   : permissoes
    Created on : Oct 07, 2009, 12:41:32 PM
    Author     : David Buzatto
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/HibernateTags.tld" prefix="htag" %>

<%--
    Permissões:
    rel: relatórios
    conf: configurações

    Tipos de usuário:
    A: administrador
    P: pesquisador
    E: educador
    T: testados
--%>

<c:choose>
    <c:when test="${not empty sessionScope.usuario}">

        {
            success: true,
            tipoUsuario: '${sessionScope.usuario.tipo}',
            permissoes: [
                { feature: 'rel', A: true, P: true, E: false, T: false },
                { feature: 'conf', A: true, P: false, E: false, T: false },
                { feature: 'dadosPessoais', A: true, P: true, E: true, T: false }
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
