<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>차량 상세 정보</title>
<style>
body { font-family: Arial, sans-serif; background: #f7f9fc; margin:0;}
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
a:hover { text-decoration: underline; }
.detail-box {
  background: #fff; border-radius: 12px; padding: 24px; box-shadow: 0 4px 12px #ddd; margin: 40px auto; max-width: 540px;
}
.detail-row { margin-bottom: 14px; font-size: 16px; }
.label { font-weight: bold; width: 120px; display: inline-block; color: #34495e; }
.value { color: #444; }
.back-btn {
  display: inline-block; margin-top: 22px; padding: 9px 22px; border-radius: 3px; background: #34495e; color: #fff;
  text-decoration: none; font-weight: bold; transition: background 0.15s;
}
.back-btn:hover { background: #2c3e50; }
</style>
</head>
<body>

  <!-- 🚩 메인 홈페이지와 동일한 헤더/네비게이션 바 -->
  <header>
    <div class="nav-left">
      <a href="${pageContext.request.contextPath}/main/main.do">전체 차량 모델</a>
      <a href="${pageContext.request.contextPath}/retailer/search.do">리테일러 찾기</a>
      <a href="#" onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/drive/form.do')">시승 신청</a>
      <a href="#" onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/repair/form.do')">정비 신청</a>
      <a href="${pageContext.request.contextPath}/board/doRetrieve.do">자유게시판</a>
      <a href="${pageContext.request.contextPath}/event/doRetrieve.do">이벤트</a>
    </div>
    <div class="nav-right">
      <c:choose>
        <c:when test="${not empty sessionScope.loginUser}">
          <a href="${pageContext.request.contextPath}/member/mypage.do">👤 ${sessionScope.loginUser.nickname}님</a>
          <a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
        </c:when>
        <c:otherwise>
          <a href="${pageContext.request.contextPath}/member/loginView.do">🔐 로그인</a>
        </c:otherwise>
      </c:choose>
    </div>
  </header>
<body>
  <header>
    <h1>차량 상세 정보</h1>
  </header>
  <main>
    <div class="detail-box">
      <div class="detail-row"><span class="label">차량명</span> <span class="value"><c:out value="${car.productName}"/></span></div>
      <div class="detail-row"><span class="label">제조사</span> <span class="value"><c:out value="${car.carMf}"/></span></div>
      <div class="detail-row"><span class="label">차종</span> <span class="value"><c:out value="${car.cartype}"/></span></div>
      <div class="detail-row"><span class="label">제조년도</span> <span class="value"><c:out value="${car.mfDt}"/></span></div>
      <div class="detail-row"><span class="label">가격</span> <span class="value"><fmt:formatNumber value="${car.price}" type="currency" currencySymbol="₩"/></span></div>
      <div class="detail-row"><span class="label">연료</span> <span class="value"><c:out value="${car.fuel}"/></span></div>
      <div class="detail-row"><span class="label">효율</span> <span class="value"><c:out value="${car.ef}"/></span></div>
      <div class="detail-row"><span class="label">엔진</span> <span class="value"><c:out value="${car.engine != null ? car.engine : '-'}"/></span></div>
      <div class="detail-row"><span class="label">배터리</span> <span class="value"><c:out value="${car.battery != null ? car.battery : '-'}"/></span></div>
      <div class="detail-row"><span class="label">제조연도</span> <span class="value"><c:out value="${car.mfDt != null ? car.mfDt : '-'}"/></span></div>
      
      <!-- 이미지 자동 출력 -->
<div class="detail-row">
  <span class="label">이미지</span>
  <span class="value">
    <img src="${pageContext.request.contextPath}/image/${car.productName}.png" alt="${car.productName}"
         alt="${car.productName}" style="max-width:100%;height:auto;border-radius:8px;box-shadow:0 2px 8px #eee;">
  </span>
</div>
      <!-- 필요한 상세 정보 더 추가 가능 -->
      <a href="list.do" class="back-btn">목록으로</a>
    </div>
  </main>
</body>
</html>
