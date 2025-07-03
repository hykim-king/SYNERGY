<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
<style>
body {
    font-family: Arial, sans-serif;
    background: #f4f6f8;
    margin: 0;
    padding: 30px;
}
h2 {
    margin-bottom: 20px;
}
table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}
th, td {
    border: 1px solid #ccc;
    padding: 8px 12px;
    text-align: center;
}
th {
    background-color: #eee;
}
.pagination {
    text-align: center;
    margin: 20px 0;
}
.pagination a {
    margin: 0 4px;
    text-decoration: none;
    color: #007bff;
}
.pagination strong {
    margin: 0 4px;
    color: #000;
}
.action-buttons {
    text-align: center;
    margin-top: 20px;
}
.action-buttons a {
    background-color: #6c757d;
    color: white;
    padding: 10px 20px;
    text-decoration: none;
    border-radius: 6px;
}
.action-buttons a:hover {
    background-color: #5a6268;
}
</style>
</head>
<body>

    <h2>회원 정보 조회</h2>

    <c:if test="${not empty errorMessage}">
        <div style="color: red; font-weight: bold;">${errorMessage}</div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>아이디</th>
                <th>닉네임</th>
                <th>이름</th>
                <th>전화번호</th>
                <th>이메일</th>
                <th>등록일</th>
                <th>관리자 여부</th>
                <th>수정</th>
                <th>삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="member" items="${members}">
                <tr>
                    <td>${member.id}</td>
                    <td>${member.nickname}</td>
                    <td>${member.name}</td>
                    <td>${member.phone}</td>
                    <td>${member.email}</td>
                    <td><fmt:formatDate value="${member.regDt}" pattern="yyyy-MM-dd" /></td>
                    <td>
                        <c:choose>
                            <c:when test="${member.adminRole == 1}">관리자</c:when>
                            <c:otherwise>일반회원</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/member/updateView.do?id=${member.id}">수정</a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/member/delete.do?id=${member.id}"
                           onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- ✅ 페이징 -->
    <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <strong>[${i}]</strong>
                </c:when>
                <c:otherwise>
                    <a href="?pageNum=${i}&searchDiv=${searchDiv}&searchWord=${searchWord}">[${i}]</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    <!-- ✅ 관리자 메인으로 이동 버튼 (맨 아래) -->
    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/admin/main.do">관리자 메인으로</a>
    </div>

</body>
</html>