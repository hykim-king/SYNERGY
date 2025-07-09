<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>리테일러(정비소) 전체 목록</title>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f7f9fc;
    margin: 0;
    padding: 0;
}
/* 네비/헤더 스타일 */
.header-bar {
    background: #00274d;
    color: #fff;
    padding: 0 48px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 64px;
}
.header-nav {
    display: flex;
    gap: 28px;
}
.header-nav a {
    color: #fff;
    font-weight: bold;
    text-decoration: none;
    font-size: 1.04rem;
    letter-spacing: 0.01em;
    transition: color 0.12s;
}
.header-nav a:hover { color: #c0e7ff; }
.header-right {
    display: flex;
    align-items: center;
    gap: 10px;
    font-size: 1rem;
}
.header-right a {
    color: #fff;
    font-weight: bold;
    text-decoration: none;
}
.header-right a:hover { color: #ffe7a2; }
.header-right .login-icon { font-size: 17px; margin-right: 3px; }

.page-title {
    background: #00274d;
    color: #fff;
    margin: 0;
    padding: 38px 0 38px 70px;
    font-size: 2.3rem;
    font-weight: bold;
    letter-spacing: -1.5px;
}

@media (max-width: 700px) {
  .header-bar, .page-title { padding-left: 20px; padding-right: 10px; }
  .page-title { font-size: 1.3rem; padding: 25px 0 18px 16px; }
}

main {
    padding: 20px 5vw;
    max-width: 1100px;
    margin: 0 auto;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 10px;
    background: #fff;
}

th, td {
    border: 1px solid #ccc;
    padding: 8px;
    text-align: left;
}

th {
    background-color: #5a728a;
    color: white;
}

tr:nth-child(even) {
    background-color: #ecf0f1;
}

.paging {
    margin-top: 10px;
    text-align: center;
}

.paging a {
    margin: 0 5px;
    padding: 6px 12px;
    background-color: #3498db;
    color: white;
    text-decoration: none;
    border-radius: 3px;
    display: inline-block;
}

.paging a.current {
    background-color: #2c3e50;
    font-weight: bold;
    cursor: default;
}

.paging a.disabled {
    background-color: #bdc3c7;
    pointer-events: none;
    cursor: default;
}

.btn-main {
    float: right;
    margin-bottom: 10px;
}

.btn-main a {
    text-decoration: none;
    background-color: #2c3e50;
    color: white;
    padding: 8px 15px;
    border-radius: 3px;
    font-weight: bold;
}

.btn-main a:hover {
    background-color: #34495e;
}

.search-form input[type="text"] {
    padding: 6px;
    width: 200px;
    border: 1px solid #ccc;
    border-radius: 3px;
}

.search-form select {
    padding: 6px;
    border-radius: 3px;
}

.search-form button {
    padding: 6px 12px;
    border: none;
    background-color: #3498db;
    color: white;
    border-radius: 3px;
    cursor: pointer;
}

.search-form button:hover {
    background-color: #2980b9;
}
</style>
<!-- 로그인 보호 자바스크립트(필요시 사용) -->
<script>
function handleProtectedLink(event, url) {
    // 로그인이 안 된 상태라면 alert
    var isLoggedIn = '${not empty sessionScope.loginUser}' === 'true';
    if (!isLoggedIn) {
        event.preventDefault();
        alert('로그인 후 이용해 주세요.');
    } else {
        window.location.href = url;
    }
}
</script>
</head>
<body>
  <!-- 네비/헤더 영역 -->
  <div class="header-bar">
    <div class="header-nav">
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
  <!-- 상단 타이틀 바 -->
  <div class="page-title">리테일러 전체 목록</div>

  <!-- 1. 상태 메시지 -->
  <c:if test="${not empty msg}">
    <script>alert('${msg}');</script>
  </c:if>

  <!-- 2. 검색 폼 -->
  <!-- 검색 form -->
<form method="get" action="${pageContext.request.contextPath}/retailer/all.do">

    <select name="searchType">
      <option value="retailerName" ${searchType eq 'retailerName' ? 'selected' : ''}>업체명</option>
      <option value="area" ${searchType eq 'area' ? 'selected' : ''}>지역</option>
      <option value="carMf" ${searchType eq 'carMf' ? 'selected' : ''}>제조사</option>
      <option value="productName" ${searchType eq 'productName' ? 'selected' : ''}>취급차량</option>
    </select>
    <input type="text" name="searchWord" value="${searchWord}" placeholder="검색어">
    <button type="submit">검색</button>
  </form>

 <!-- 3. 리테일러 테이블 -->
<table>
  <thead>
    <tr>
      <th>번호</th>
      <th>업체명</th>
      <th>제조사</th>
      <th>제품명</th>
      <th>지역(도 /시)</th>
      <th>상세주소</th>
      <th>전화번호</th>
    </tr>
  </thead>
  <tbody>
    <c:choose>
      <c:when test="${empty retailerList}">
        <tr><td colspan="7">등록된 리테일러가 없습니다.</td></tr>
      </c:when>
      <c:otherwise>
        <c:forEach var="retailer" items="${retailerList}" varStatus="status">
          <tr>
            <td>${(currentPage-1) * pageSize + status.index + 1}</td>
            <!-- 업체명에만 링크! -->
            <td><c:out value="${retailer.retailerName}" />

            </td>
            <!-- 제조사 -->
            <td><c:out value="${retailer.carMf}" /></td>
            <!-- 제품명(취급차량) -->
            <td><c:out value="${retailer.productName}" /></td>
            <!-- 지역 -->
            <td><c:out value="${retailer.area}" /></td>
            <!-- 상세주소 -->
            <td><c:out value="${retailer.address}" /></td>
            <!-- 전화번호 -->
            <td><c:out value="${retailer.telephone}" /></td>
          </tr>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </tbody>
</table>

<!-- 4. 페이징 영역 -->
<div class="paging">
  <c:set var="pageBlock" value="10" />
  <c:set var="startPage" value="${((currentPage - 1) / pageBlock) * pageBlock + 1}" />
  <c:set var="endPage" value="${startPage + pageBlock - 1}" />
  <c:if test="${endPage > totalPages}">
    <c:set var="endPage" value="${totalPages}" />
  </c:if>
  <c:choose>
    <c:when test="${currentPage == 1}">
      <a href="#" class="disabled">&laquo; 이전</a>
    </c:when>
    <c:otherwise>
      <a href="${pageContext.request.contextPath}/retailer/all.do?pageNum=${currentPage - 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">&laquo; 이전</a>
    </c:otherwise>
  </c:choose>
  <c:forEach begin="${startPage}" end="${endPage}" var="i">
    <c:choose>
      <c:when test="${i == currentPage}">
        <a href="#" class="current">${i}</a>
      </c:when>
      <c:otherwise>
        <a href="${pageContext.request.contextPath}/retailer/all.do?pageNum=${i}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">${i}</a>
      </c:otherwise>
    </c:choose>
  </c:forEach>
  <c:choose>
    <c:when test="${currentPage == totalPages || totalPages == 0}">
      <a href="#" class="disabled">다음 &raquo;</a>
    </c:when>
    <c:otherwise>
      <a href="${pageContext.request.contextPath}/retailer/all.do?pageNum=${currentPage + 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">다음 &raquo;</a>
    </c:otherwise>
  </c:choose>
</div>

</body>
</html>
