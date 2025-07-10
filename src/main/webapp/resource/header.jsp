<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 💡 로그인 여부 확인 후 페이지 이동 제어 -->
<script>
  const isLoggedIn = ${not empty sessionScope.loginUser ? 'true' : 'false'};
</script>

<script>
  function handleProtectedLink(event, url) {
    if (!isLoggedIn) {
      event.preventDefault();
      alert("로그인이 필요합니다.");
    } else {
      window.location.href = url;
    }
  }
</script>
<header>

  <div class="header-bar">
    <div class="header-nav">
      <a href="${pageContext.request.contextPath}/main/main.do">
        <img src="${pageContext.request.contextPath}/image/carpick.png" alt="CARPICK" style="height:100px; vertical-align:middle;">
      </a>

      <a href="${pageContext.request.contextPath}/car/list.do">전체 차량 모델</a>
      <a href="${pageContext.request.contextPath}/retailer/all.do">리테일러 찾기</a>
      <a href="#" onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/drive/form.do')">시승 신청</a>
      <a href="#" onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/repair/form.do')">정비 신청</a>
      <a href="${pageContext.request.contextPath}/board/doRetrieve.do">자유게시판</a>
      <a href="${pageContext.request.contextPath}/event/doRetrieve.do">이벤트</a>
    </div>

    <div class="header-right">
      <c:choose>
        <c:when test="${not empty sessionScope.loginUser}">
          <span class="login-icon">👤</span>
          <a href="${pageContext.request.contextPath}/member/mypage.do">${sessionScope.loginUser.nickname}님</a>
          <a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
        </c:when>
        <c:otherwise>
          <span class="login-icon">🔒</span>
          <a href="${pageContext.request.contextPath}/member/loginView.do">로그인</a>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</header>