<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 목록</title>
    <style>
        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
        }

        body {
            display: flex;
            flex-direction: column;
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
        }

        main {
            flex: 1;
            padding: 20px;
            box-sizing: border-box;
        }

        h2 {
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 10px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px 12px;
            text-align: center;
        }

        th {
            background-color: #34495e;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #ecf0f1;
        }

        .pagination {
            margin-top: 10px;
            text-align: center;
        }

        .pagination a {
            margin: 0 5px;
            padding: 6px 12px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 3px;
            display: inline-block;
        }

        .pagination a.current {
            background-color: #2c3e50;
            font-weight: bold;
            cursor: default;
        }

        .pagination a.disabled {
            background-color: #bdc3c7;
            pointer-events: none;
            cursor: default;
        }

        .action-buttons {
            margin-top: 15px;
            text-align: right;
        }

        .action-buttons a {
            background-color: #2c3e50;
            color: white;
            padding: 8px 15px;
            margin-left: 10px;
            text-decoration: none;
            border-radius: 3px;
            font-weight: bold;
        }

        .action-buttons a:hover {
            background-color: #34495e;
        }

        footer {
            background-color: #2c3e50;
            color: white;
            text-align: center;
            padding: 10px;
        }
    </style>
</head>
<body>

<jsp:include page="/resource/adminHeader.jsp" />

<main>
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
                    <td><c:out value="${member.id}" /></td>
                    <td><c:out value="${member.nickname}" /></td>
                    <td><c:out value="${member.name}" /></td>
                    <td><c:out value="${member.phone}" /></td>
                    <td><c:out value="${member.email}" /></td>
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

    <div class="pagination">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <a href="#" class="current">${i}</a>
                </c:when>
                <c:otherwise>
                    <a href="?pageNum=${i}&searchDiv=${searchDiv}&searchWord=${searchWord}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/admin/member/registerView.do">회원 등록</a>
    </div>
</main>

<jsp:include page="/resource/footer.jsp" />

</body>
</html>