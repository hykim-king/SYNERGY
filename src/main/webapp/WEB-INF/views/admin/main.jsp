<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>관리자 메인 페이지</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
            margin: 0;
            padding: 0 0 60px 0; /* footer 공간 위해 아래만 60px */
        }
        main {
            padding: 20px;
        }
        .welcome {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<!-- 헤더 및 네비게이션 include -->
<jsp:include page="/resource/adminHeader.jsp" />

<main>
    <div class="welcome">
        <h2>환영합니다, 
            <c:choose>
                <c:when test="${not empty sessionScope.loginUser}">
                    <c:out value="${sessionScope.loginUser.nickname}" />
                </c:when>
                <c:otherwise>
                    손님
                </c:otherwise>
            </c:choose>
            님!
        </h2>
        <p>관리자 페이지에 오신 것을 환영합니다.</p>
    </div>
    <p>여기서 회원 관리, 차량 정보 관리 등 여러 관리 작업을 수행할 수 있습니다.</p>
</main>

<!-- footer include -->
<jsp:include page="/resource/adminFooter.jsp" />

</body>
</html>