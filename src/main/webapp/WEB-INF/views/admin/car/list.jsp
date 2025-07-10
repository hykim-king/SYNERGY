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
	background-color: #F7F9FC;
	margin: 0;
	padding: 0;
}

.wrapper {
	display: flex;
	flex-direction: column;
	min-height: 100vh;
}

main {
	flex: 1;
	padding: 20px;
}

header {
	background-color: #2C3E50;
	color: white;
	padding: 15px 20px;
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
	background-color: #34495E;
	color: white;
}

tr:nth-child(even) {
	background-color: #ECF0F1;
}

.paging {
	margin-top: 10px;
	text-align: center;
}

.paging a {
	margin: 0 5px;
	padding: 6px 12px;
	background-color: #3498DB;
	color: white;
	text-decoration: none;
	border-radius: 3px;
	display: inline-block;
}

.paging a.current {
	background-color: #2C3E50;
	font-weight: bold;
	cursor: default;
}

.paging a.disabled {
	background-color: #BDC3C7;
	pointer-events: none;
	cursor: default;
}

.btn-main {
	float: right;
	margin-bottom: 10px;
}

.btn-main a {
	text-decoration: none;
	background-color: #2C3E50;
	color: white;
	padding: 8px 15px;
	border-radius: 3px;
	font-weight: bold;
}

.btn-main a:hover {
	background-color: #34495E;
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
	background-color: #3498DB;
	color: white;
	border-radius: 3px;
	cursor: pointer;
}

.search-form button:hover {
	background-color: #2980B9;
}
/* 수정/삭제 버튼 */
.action-btn {
	margin-right: 5px;
	padding: 4px 8px;
	background-color: #27AE60;
	color: white;
	border: none;
	border-radius: 3px;
	cursor: pointer;
	font-size: 12px;
	text-decoration: none;
}

.action-btn.delete {
	background-color: #C0392B;
}

.action-btn:hover {
	opacity: 0.8;
}
</style>
</head>
<body>
	<div class="wrapper">
		<!-- 상단 공통 헤더 -->
		<jsp:include page="/resource/adminHeader.jsp" />

		<main>
			<!-- 삭제/수정 결과 메시지 -->
			<c:if test="${not empty msg}">
				<script>
					alert('${msg}');
				</script>
			</c:if>
			<form class="search-form" method="get" action="list.do"
				style="margin-bottom: 15px;">
				<select name="searchType">
					<option value="productName"
						<c:if test="${searchType == 'productName'}">selected</c:if>>차량명</option>
					<option value="carMf"
						<c:if test="${searchType == 'carMf'}">selected</c:if>>제조사</option>
				</select> <input type="text" name="searchWord"
					value="${fn:escapeXml(searchWord)}" placeholder="검색어 입력" />
				<button type="submit">검색</button>
			</form>
			<table>
				<thead>
					<tr>
						<th>번호</th>
						<th>차량명</th>
						<th>제조사</th>
						<th>차종</th>
						<th>제조년도</th>
						<th>가격(만원)</th>
						<th>연료</th>
						<th>효율</th>
						<th>엔진</th>
						<th>배기량(cc)</th>
						<th>배터리(kWh)</th>
						<th>수정/삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty carList}">
						<tr>
							<td colspan="12" style="text-align: center;">등록된 차량 정보가
								없습니다.</td>
						</tr>
					</c:if>
					<c:forEach var="car" items="${carList}" varStatus="status">
						<tr>
							<td>${(currentPage - 1) * pageSize + status.index + 1}</td>
							<td><c:out value="${car.productName}" /></td>
							<td><c:out value="${car.carMf}" /></td>
							<td><c:out value="${car.cartype}" /></td>
							<td><c:out value="${car.mfDt}" /></td>
							<td><fmt:formatNumber value="${car.price}" type="currency"
									currencySymbol="₩" /></td>
							<td><c:out value="${car.fuel}" /></td>
							<td><c:out value="${car.ef}" /></td>
							<td><c:out value="${car.engine}" /></td>
							<td><c:out value="${car.dpm != null ? car.dpm : '-'}" /></td>
							<td><c:out
									value="${car.battery != null ? car.battery : '-'}" /></td>
							<td>
								<form
									action="${pageContext.request.contextPath}/admin/car/delete.do"
									method="post" style="display: inline;"
									onsubmit="return confirm('정말 삭제하시겠습니까?');">
									<input type="hidden" name="carCode" value="${car.carCode}" />
									<button type="submit" class="action-btn delete">삭제</button>
								</form> <a
								href="${pageContext.request.contextPath}/admin/car/updateView.do?carCode=${car.carCode}"
								class="action-btn">수정</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div
				style="display: flex; justify-content: flex-end; margin-top: 20px;">
				<a href="${pageContext.request.contextPath}/admin/car/add.do"
					style="background-color: #27AE60; color: white; padding: 8px 15px; border-radius: 3px; text-decoration: none; font-weight: bold;">
					차량 등록 </a>
			</div>
			<div class="paging">
				<c:set var="pageBlock" value="10" />
				<c:set var="startPage"
					value="${((currentPage - 1) / pageBlock) * pageBlock + 1}" />
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
						<a
							href="list.do?pageNum=${currentPage - 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">&laquo;
							이전</a>
					</c:otherwise>
				</c:choose>
				<!-- 페이지 번호 -->
				<c:forEach begin="${startPage}" end="${endPage}" var="i">
					<c:choose>
						<c:when test="${i == currentPage}">
							<a href="#" class="current">${i}</a>
						</c:when>
						<c:otherwise>
							<a
								href="list.do?pageNum=${i}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">${i}</a>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<!-- 다음 버튼 -->
				<c:choose>
					<c:when test="${currentPage == totalPages || totalPages == 0}">
						<a href="#" class="disabled">다음 &raquo;</a>
					</c:when>
					<c:otherwise>
						<a
							href="list.do?pageNum=${currentPage + 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">다음
							&raquo;</a>
					</c:otherwise>
				</c:choose>
			</div>
		</main>

		<!-- 하단 공통 푸터 -->
		<jsp:include page="/resource/footer.jsp" />
	</div>
</body>
</html>