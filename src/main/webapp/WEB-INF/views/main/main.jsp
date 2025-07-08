<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인 홈페이지</title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f8;
        }

        header {
            background-color: #00274d;
            color: white;
            padding: 20px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .nav-left, .nav-right {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        main {
            padding: 60px 40px;
            text-align: center;
        }

        footer {
            background-color: #ddd;
            padding: 20px;
            text-align: center;
            font-size: 14px;
        }
    </style>

    <!-- 로그인 여부를 자바스크립트에 전달 (필요시 활용 가능) -->
    <script>
        const isLoggedIn = ${not empty sessionScope.loginUser}; // true 또는 false
    </script>

    <!-- 로그인 여부 확인 후 보호된 링크 접근 제어 -->
    <script>
        function handleProtectedLink(event, url) {
            if (!isLoggedIn) {
                event.preventDefault();
                alert("로그인을 시도해 주세요.");
            } else {
                window.location.href = url;
            }
        }
    </script>
</head>
<body>

<header>
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/car/allModels.do">전체 차량 모델</a>
        <a href="${pageContext.request.contextPath}/retailer/search.do">리테일러 찾기</a>
        <a href="#" onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/drive/form.do')">시승 신청</a>
        <a href="#" onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/repair/form.do')">정비 신청</a>
        <a href="${pageContext.request.contextPath}/board/doRetrieve.do">자유게시판</a>
        <a href="${pageContext.request.contextPath}/event/doRetrieve.do">이벤트</a>
    </div>

    <div class="nav-right">
        <c:choose>
            <c:when test="${not empty sessionScope.loginUser}">
                <span>👤 ${sessionScope.loginUser.nickname}님</span>
                <a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/member/loginView.do">🔐 로그인</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<!-- 로그인 직후 한 번만 뜨는 환영 메시지 alert -->
<c:if test="${not empty sessionScope.welcomeMessage}">
    <script>
        alert('${sessionScope.welcomeMessage}');
    </script>
    <c:remove var="welcomeMessage" scope="session" />
</c:if>

<main>
    <h2>환영합니다!</h2>
    <p>당신의 프리미엄 드라이빙 경험, 지금 시작하세요.</p>
    <!-- 배너 이미지, 공지사항, 프로모션 등 추가 가능 -->
</main>

<footer>
    ⓒ 2025 자동차 브랜드. All rights reserved.
</footer>

</body>
</html>