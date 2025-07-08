<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>관리자 차량 목록</title>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f7f9fc;
    margin: 0;
    padding: 0;
}

header {
    background-color: #2c3e50;
    color: white;
    padding: 15px 20px;
}

main {
    padding: 20px;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 10px;
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

/* 수정/삭제 버튼 */
.action-btn {
    margin-right: 5px;
    padding: 4px 8px;
    background-color: #27ae60;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    font-size: 12px;
    text-decoration: none;
}

.action-btn.delete {
    background-color: #c0392b;
}

.action-btn:hover {
    opacity: 0.8;
}
</style>
</head>
<body>
  <h1>차량 전체 목록</h1>
  
  <!-- 1. 상태 메시지 -->
  <c:if test="${not empty msg}">
    <script>alert('${msg}');</script>
  </c:if>
  
  <!-- 2. 검색 폼 -->
  <form method="get" action="list.do">
    <select name="searchType">
      <option value="productName" ${searchType eq 'productName' ? 'selected' : ''}>차량명</option>
      <option value="carMf" ${searchType eq 'carMf' ? 'selected' : ''}>제조사</option>
      <option value="cartype" ${searchType eq 'cartype' ? 'selected' : ''}>차종</option>
    </select>
    <input type="text" name="searchWord" value="${searchWord}" placeholder="검색어">
    <button type="submit">검색</button>
  </form>
  
  <!-- 3. 차량 테이블 -->
  <table>
    <thead>
      <tr>
        <th>번호</th>
        <th>차량명</th>
        <th>제조사</th>
        <th>차종</th>
        <th>제조년도</th>
        <th>가격</th>
        <th>연료</th>
        <th>효율</th>
        <th>엔진</th>
        <th>배터리</th>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${empty carList}">
          <tr><td colspan="10">등록된 차량이 없습니다.</td></tr>
        </c:when>
        <c:otherwise>
          <c:forEach var="car" items="${carList}" varStatus="status">
            <tr>
              <td>${(currentPage-1) * pageSize + status.index + 1}</td>
              <td>
                <a href="detail.do?carCode=${car.carCode}">
                  <c:out value="${car.productName}" />
                </a>
              </td>
              <td><c:out value="${car.carMf}" /></td>
              <td><c:out value="${car.cartype}" /></td>
              <td><c:out value="${car.mfDt}" /></td>
              <td><fmt:formatNumber value="${car.price}" type="currency" currencySymbol="₩" /></td>
              <td><c:out value="${car.fuel}" /></td>
              <td><c:out value="${car.ef}" /></td>
              <td><c:out value="${car.engine}" /></td>
              <td><c:out value="${car.battery != null ? car.battery : '-'}" /></td>
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
        <a href="list.do?pageNum=${currentPage - 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">&laquo; 이전</a>
      </c:otherwise>
    </c:choose>
    <c:forEach begin="${startPage}" end="${endPage}" var="i">
      <c:choose>
        <c:when test="${i == currentPage}">
          <a href="#" class="current">${i}</a>
        </c:when>
        <c:otherwise>
          <a href="list.do?pageNum=${i}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    <c:choose>
      <c:when test="${currentPage == totalPages || totalPages == 0}">
        <a href="#" class="disabled">다음 &raquo;</a>
      </c:when>
      <c:otherwise>
        <a href="list.do?pageNum=${currentPage + 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">다음 &raquo;</a>
      </c:otherwise>
    </c:choose>
  </div>
</body>
</html>