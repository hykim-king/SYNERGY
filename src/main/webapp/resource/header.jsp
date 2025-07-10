<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- 💡 로그인 여부 확인 후 페이지 이동 제어 -->
<script>
window.isLoggedIn = ${not empty sessionScope.loginUser};
window.userNickname = '<c:out value="${not empty sessionScope.loginUser ? sessionScope.loginUser.nickname : ''}" />';
console.log("isLoggedIn:", window.isLoggedIn);
console.log("userNickname:", window.userNickname);
</script>

<script>
function handleProtectedLink(event, url) {
    if (!window.isLoggedIn) {  // boolean false 일 때 처리
      event.preventDefault();
      alert("로그인이 필요합니다.");
    } else {
      window.location.href = url;
    }
}

function logoutAndClear() {
    sessionStorage.removeItem('welcomeShown'); // 환영 메시지 초기화
    location.href = '${pageContext.request.contextPath}/member/logout.do';
}
</script>
<header>

  <div class="header-bar">

  <div class="header-nav">
    <!-- 메뉴 영역 -->
    <a href="${CP}/main/main.do"><img src="${CP}/image/carpick.png" alt="CARPICK" style="height:100px;"></a>
    <a href="${CP}/car/list.do">차량 전체 모델</a>
    <a href="${CP}/retailer/all.do">리테일러 찾기</a>
    <a href="#" onclick="handleProtectedLink(event, '${CP}/drive/form.do')">시승 신청</a>
    <a href="#" onclick="handleProtectedLink(event, '${CP}/repair/form.do')">정비 신청</a>
    <a href="${CP}/board/doRetrieve.do">자유게시판</a>
    <a href="${CP}/event/doRetrieve.do">이벤트</a>
  </div>

  <!-- ✅ 로그인 영역은 flex 레이아웃 상에서 가장 오른쪽으로 배치됨 -->
  <div class="header-right">
    <c:choose>
      <c:when test="${not empty sessionScope.loginUser}">
        <span class="login-icon">👤</span>
        <a href="${CP}/member/mypage.do">${sessionScope.loginUser.nickname}님</a>
        <a href="${CP}/member/logout.do">로그아웃</a>
      </c:when>
      <c:otherwise>
        <span class="login-icon">🔒</span>
        <a href="${CP}/member/loginView.do">로그인</a>
      </c:otherwise>
    </c:choose>

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
          <a href="javascript:void(0);" onclick="logoutAndClear()">로그아웃</a>
        </c:when>
        <c:otherwise>
          <span class="login-icon">🔒</span>
          <a href="${pageContext.request.contextPath}/member/loginView.do">로그인</a>
        </c:otherwise>
      </c:choose>
    </div>

  </div>
</div>
</header>