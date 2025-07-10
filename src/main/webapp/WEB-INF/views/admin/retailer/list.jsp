<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>관리자 리테일러 목록</title>
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
<header>
    <h1>관리자 리테일러 목록</h1>
</header>

<main>
    <!-- 삭제/수정 결과 메시지 -->
    <c:if test="${not empty msg}">
        <script>
            alert('${msg}');
        </script>
    </c:if>

    <div class="btn-main">
        <a href="${pageContext.request.contextPath}/admin/main.do">관리자 메인</a>
        <a href="${pageContext.request.contextPath}/admin/retailer/add.do"
            style="margin-left: 10px; background-color: #27ae60;">리테일러 등록</a>
    </div>

    <form class="search-form" method="get" action="list.do" style="margin-bottom: 15px;">
        <select name="searchType">
            <option value="productName" <c:if test="${searchType == 'productName'}">selected</c:if>>제품명</option>
            <option value="retailerName" <c:if test="${searchType == 'retailerName'}">selected</c:if>>리테일러명</option>
            <option value="carMf" <c:if test="${searchType == 'carMf'}">selected</c:if>>제조사</option>
        </select>
        <input type="text" name="searchWord" value="${fn:escapeXml(searchWord)}" placeholder="검색어 입력" />
        <button type="submit">검색</button>
    </form>

    <table>
        <thead>
    <tr>
        <th>번호</th>
        <th>업체명</th>       
        <th>제조사</th>       
        <th>제품명</th>       
        <th>지역</th>         
        <th>상세 주소</th>    
        <th>전화번호</th>     
        <th>등록일</th>
        <th>수정일</th>
        <th>수정/삭제</th>
    </tr>
</thead>
<tbody>
    <c:if test="${empty retailerList}">
        <tr>
            <td colspan="10" style="text-align: center;">등록된 리테일러 정보가 없습니다.</td>
        </tr>
    </c:if>
    <c:forEach var="retailer" items="${retailerList}" varStatus="status">
        <tr>
            <td>${(currentPage - 1) * pageSize + status.index + 1}</td>
            <td><c:out value="${retailer.productName}" /></td> <!-- 업체명 -->
            <td><c:out value="${retailer.retailerName}" /></td>        <!-- 제조사 -->
            <td><c:out value="${retailer.carMf}" /></td>  <!-- 제품명 -->
            <td><c:out value="${retailer.area}" /></td>         <!-- 지역 -->
            <td><c:out value="${retailer.address}" /></td>      <!-- 상세 주소 -->
            <td><c:out value="${retailer.telephone}" /></td>    <!-- 전화번호 -->
            <td><fmt:formatDate value="${retailer.regDt}" pattern="yyyy-MM-dd" /></td>
            <td><fmt:formatDate value="${retailer.modDt}" pattern="yyyy-MM-dd" /></td>
            <td>
                <form action="${pageContext.request.contextPath}/admin/retailer/delete.do" method="post" style="display:inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                    <input type="hidden" name="retailerCode" value="${retailer.retailerCode}" />
                    <button type="submit" class="action-btn delete">삭제</button>
                </form>
                <a href="${pageContext.request.contextPath}/admin/retailer/updateView.do?retailerCode=${retailer.retailerCode}" class="action-btn">수정</a>
            </td>
        </tr>
    </c:forEach>
</tbody>
    </table>

    <div class="paging">
        <c:set var="pageBlock" value="10" />
        <c:set var="startPage" value="${((currentPage - 1) / pageBlock) * pageBlock + 1}" />
        <c:set var="endPage" value="${startPage + pageBlock - 1}" />
        <c:if test="${endPage > totalPages}">
            <c:set var="endPage" value="${totalPages}" />
        </c:if>

        <!-- 이전 버튼 -->
        <c:choose>
            <c:when test="${currentPage == 1}">
                <a href="#" class="disabled">&laquo; 이전</a>
            </c:when>
            <c:otherwise>
                <a href="list.do?pageNum=${currentPage - 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">&laquo; 이전</a>
            </c:otherwise>
        </c:choose>

        <!-- 페이지 번호 -->
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

        <!-- 다음 버튼 -->
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