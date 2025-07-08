<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>관리자 차량 목록</title>
<style>
/* ... 기존 스타일 그대로 (생략 가능) ... */
</style>
</head>
<body>
  <header>
    <h1>차량 전체 목록</h1>
  </header>
  <main>
    <!-- 1. 상태 메시지 -->
    <c:if test="${not empty msg}">
      <script>alert('${msg}');</script>
    </c:if>
    
    <!-- 2. 검색 폼 -->
    <form class="search-form" method="get" action="list.do" aria-label="차량 검색">
      <select name="searchType">
        <option value="productName" <c:if test="${searchType eq 'productName'}">selected</c:if>>차량명</option>
        <option value="carMf" <c:if test="${searchType eq 'carMf'}">selected</c:if>>제조사</option>
        <option value="cartype" <c:if test="${searchType eq 'cartype'}">selected</c:if>>차종</option>
      </select>
      <input type="text" name="searchWord" value="${fn:escapeXml(searchWord)}" placeholder="검색어 입력" aria-label="검색어">
      <button type="submit">검색</button>
    </form>
    
    <!-- 3. 차량 테이블 -->
    <table>
      <caption class="visually-hidden">등록된 차량 목록</caption>
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
                <td><c:out value="${car.engine != null ? car.engine : '-'}" /></td>
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
  </main>
</body>
</html>
