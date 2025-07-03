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
/* 관리자 메인 버튼 고정 스타일 */
.admin-main-btn {
    position: fixed;
    top: 10px;
    left: 10px;
    padding: 8px 15px;
    background-color: #007bff;
    color: white;
    text-decoration: none;
    font-weight: bold;
    border-radius: 5px;
    z-index: 1000;
}

/* 회원 정보 조회 제목 고정 스타일 */
.fixed-title {
    position: fixed;
    top: 50px;
    left: 10px;
    margin: 0;
    font-weight: bold;
    color: #333;
    font-size: 1.5em;
    z-index: 1000;
}

/* 테이블 기본 스타일 */
table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 100px; /* fixed 요소 아래 공간 확보 */
}

th, td {
    border: 1px solid #ccc;
    padding: 8px 12px;
    text-align: center;
}

th {
    background-color: #eee;
}

/* 페이징 */
.pagination {
    text-align: center;
    margin-top: 20px;
}

.pagination a, .pagination strong {
    margin: 0 5px;
    color: #007bff;
    text-decoration: none;
    font-weight: bold;
}

.pagination strong {
    color: black;
}
</style>
</head>
<body>

<!-- 관리자 메인 버튼 -->
<a href="${pageContext.request.contextPath}/admin/main.do" class="admin-main-btn">관리자 메인</a>

<!-- 회원 정보 조회 고정 제목 -->
<h2 class="fixed-title">회원 정보 조회</h2>

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
                <td><a href="${pageContext.request.contextPath}/admin/member/updateView.do?id=${member.id}">수정</a></td>
                <td><a href="${pageContext.request.contextPath}/admin/member/delete.do?id=${member.id}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

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

</body>
</html>