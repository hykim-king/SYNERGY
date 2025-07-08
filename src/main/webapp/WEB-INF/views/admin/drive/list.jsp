<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시승 신청 조회</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>예약번호</th>
				<th>회원ID</th>
				<th>이름</th>
				<th>전화번호</th>
				<th>차량</th>
				<th>제조사</th>
				<th>지점명</th>
				<th>시승일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dto" items="${list}" varStatus="status">
				<tr>
					<td>${dto.resNo}</td>
					<td>${dto.id}</td>
					<td>${dto.name}</td>
					<td>${dto.phone}</td>
					<td>${dto.productName}</td>
					<td>${dto.carMf}</td>
					<td>${dto.retailerName}</td>
					<td><fmt:formatDate value="${dto.driveDate}"
							pattern="yyyy-MM-dd" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</body>
</html>