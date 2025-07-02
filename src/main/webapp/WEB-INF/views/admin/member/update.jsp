<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<
<title>회원 정보 수정</title>
<style>
table {
	width: 600px;
	margin: 30px auto;
	border-collapse: collapse;
}

th, td {
	padding: 10px;
	border: 1px solid #ddd;
}

th {
	background-color: #f8f8f8;
	text-align: left;
	width: 150px;
}

td input {
	width: 100%;
}

.btns {
	text-align: center;
	margin-top: 20px;
}
</style>
</head>
<body>

	<h2 style="text-align: center;">회원 정보 수정</h2>

	<c:if test="${not empty errorMessage}">
		<div style="color: red; text-align: center;">${errorMessage}</div>
	</c:if>

	<form
		action="${pageContext.request.contextPath}/admin/member/update.do"
		method="post">
		<table>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="id" value="${member.id}" readonly /></td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="pwd" value="${member.pwd}"
					required /></td>
			</tr>
			<tr>
				<th>닉네임</th>
				<td><input type="text" name="nickname"
					value="${member.nickname}" required /></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="name" value="${member.name}"
					required /></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name="phone" value="${member.phone}" /></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="email" name="email" value="${member.email}" /></td>
			</tr>
			<tr>
				<th>회원 구분</th>
				<td><select name="adminRole">
						<option value="0" ${member.adminRole == 0 ? 'selected' : ''}>일반회원</option>
						<option value="1" ${member.adminRole == 1 ? 'selected' : ''}>관리자</option>
				</select></td>
			</tr>
			<tr>
				<th>등록일</th>
				<td><fmt:formatDate value="${member.regDt}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
			<tr>
				<th>최종 수정일</th>
				<td><fmt:formatDate value="${member.modDt}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
			</tr>
		</table>

		<div class="btns">
			<button type="submit">수정 완료</button>
			<a href="${pageContext.request.contextPath}/admin/member/list.do">목록으로</a>
		</div>
	</form>
</body>
</html>