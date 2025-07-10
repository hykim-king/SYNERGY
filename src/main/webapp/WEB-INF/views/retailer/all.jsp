<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>리테일러(정비소) 전체 목록</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
  <%@ include file="/resource/header.jsp" %>


<style>




.page-title {
    background: #00274d;
    color: #fff; 
    margin: 0;
    padding: 38px 0 38px 70px;
    font-size: 2rem;
    font-weight: bold;
    letter-spacing: -1.5px;
}




/* 전체 메인 중앙정렬! */
.main-center-wrap {
    max-width: 1100px;
    margin: 0 auto;
    margin-top: 36px;
}



/* 검색폼 중앙정렬 & 라운드 사각형 */
.search-form-wrapper {
    display: flex;
    justify-content: flex-end;
    margin-bottom: 18px;
}



.search-form {
    display: flex;
    gap: 8px;
    align-items: center;
}



.search-form select {
    padding: 8px 10px;
    border-radius: 5px 0 0 5px;
    border: 1px solid #ccd4e2;
    background: #fff;
    font-size: 1rem;
    height: 42px;
}



.search-form input[type="text"] {
    padding: 7px 1px;
    border: 1px solid #ccd4e2;
    border-radius: 0;
    width: 230px;
    font-size: 1rem;
    background: #fff;
    height: 28px;
}



.search-btn {
    background: #3498db;
    border: none;
    border-radius: 0 5px 5px 0;
    padding: 0 18px;
    height: 42px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-size: 1.35rem;
    cursor: pointer;
    transition: background 0.15s;
    margin-left: -1px;
}



.search-btn:hover { background: #2378b9; }




/* 테이블 중앙 정렬 및 스타일 */
.table-wrap {
    width: 100%;
    margin: 0 auto;
}



table {
    width: 100%;
    border-collapse: collapse;
    background: #fff;
}



th, td {
    border: 1px solid #ccc;
    padding: 8px;
    text-align: left;
}



th {
    background-color: #34495e;
    color: white;
}



tr:nth-child(even) {
    background-color: #ecf0f1;
}



.car-img {
    width: 100px;
    height: auto;
    border-radius: 6px;
    box-shadow: 0 2px 8px #e0e0e0;
    object-fit: contain;
    background: #fff;
}



/* 페이징 중앙 정렬 */
.paging {
    margin: 18px 0 30px 0;
    text-align: center;
}



.paging a {
    margin: 0 5px;
    padding: 6px 13px;
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




@media (max-width: 1100px) {
    .main-center-wrap { max-width: 97vw; }
}


@media (max-width: 700px) {
  .header-bar, .page-title { padding-left: 20px; padding-right: 10px; }
  .page-title { font-size: 1.3rem; padding: 25px 0 18px 16px; }
  .main-center-wrap { padding-left: 5px; padding-right: 5px; }
}


</style>
</head>



<body>
 
  <!-- 상단 타이틀 바 -->
  <div class="page-title">리테일러 전체 목록</div>

  <!-- 1. 상태 메시지 -->
  <c:if test="${not empty msg}">
    <script>alert('${msg}');</script>
  </c:if>

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

  <!-- 2. 검색 폼 -->
  <!-- 검색 form -->
<div class="main-center-wrap">
  <!-- 2. 검색 폼 -->
  <div class="search-form-wrapper">
    <form class="search-form" method="get" action="${pageContext.request.contextPath}/retailer/all.do">
      <select name="searchType">
        <option value="retailerName" ${searchType eq 'retailerName' ? 'selected' : ''}>업체명</option>
        <option value="area" ${searchType eq 'area' ? 'selected' : ''}>지역</option>
        <option value="carMf" ${searchType eq 'carMf' ? 'selected' : ''}>제조사</option>
        <option value="productName" ${searchType eq 'productName' ? 'selected' : ''}>취급차량</option>
      </select>
      <input type="text" name="searchWord" value="${searchWord}" placeholder="검색어">
      <button type="submit" class="search-btn">
        <i class="fa-solid fa-magnifying-glass"></i>
      </button>
    </form>
  </div>

  <!-- 3. 테이블 -->
  <div class="table-wrap">
    <table>
      <thead>
        <tr>
          <th>번호</th>
          <th>제품명</th>
          <th>업체명</th>
          <th>제조사</th>
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
                <td><c:out value="${retailer.productName}" /></td>
                <td><c:out value="${retailer.retailerName}" /></td>
                <td><c:out value="${retailer.carMf}" /></td>
                <td><c:out value="${retailer.area}" /></td>
                <td><c:out value="${retailer.address}" /></td>
                <td><c:out value="${retailer.telephone}" /></td>
              </tr>
            </c:forEach>
          </c:otherwise>
        </c:choose>
      </tbody>
    </table>
  </div>

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

  <%@ include file="/resource/footer.jsp" %>

</body>
</html>
