<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>

<head>

    <meta charset="UTF-8">

    <title>

        <c:choose>

            <c:when test="${not empty car}">

                ${car.productName} 상세 정보

            </c:when>

            <c:otherwise>

                메인 홈페이지

            </c:otherwise>

        </c:choose>

    </title>

    <!-- 통합 CSS 한 번만 불러오기 -->

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main-style.css">

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



<main>

    <c:choose>

        <c:when test="${not empty car}">

            <!-- 상세 정보 (디자인용 div로 감싸기) -->

            <div class="car-detail">

                <h2>${car.productName} 상세 정보</h2>

                <c:if test="${not empty car.modFn}">

                    <img src="${pageContext.request.contextPath}/image/${car.modFn}" alt="${car.productName}" width="350"><br>

                </c:if>

                <ul>

                    <li>브랜드: ${car.carMf}</li>

                    <li>모델명: ${car.productName}</li>

                    <li>차종: ${car.cartype}</li>

                    <li>가격: <fmt:formatNumber value="${car.price}" type="currency" currencySymbol="₩"/></li>

                    <li>연료: ${car.fuel}</li>

                    <li>연비: ${car.ef}</li>

                    <li>엔진: ${car.engine}</li>

                    <li>

                        <c:if test="${not empty car.dpm}">출력: ${car.dpm}마력</c:if>

                        <c:if test="${not empty car.battery}"> / 배터리: ${car.battery} kWh</c:if>

                    </li>

                    <li>출시년도: ${car.mfDt}</li>

                    <li>등록일: <fmt:formatDate value="${car.regDt}" pattern="yyyy-MM-dd"/></li>

                    <li>수정일: <fmt:formatDate value="${car.modDt}" pattern="yyyy-MM-dd"/></li>

                </ul>

                <a class="btn-back" href="${pageContext.request.contextPath}/car/list.do">← 목록으로</a>

            </div>

        </c:when>

    </c:choose>

</main>



<footer>

    ⓒ 2025 자동차 브랜드. All rights reserved.

</footer>





<script src="${pageContext.request.contextPath}/js/main-script.js"></script>

</body>

</html>

