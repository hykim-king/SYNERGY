<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>정비 신청 조회</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
            margin: 0;
            padding: 0;
        }
        main {
            padding: 10px 40px 40px 40px; /* 위 10px, 좌우 40px, 아래 40px */
            width: 100%;
            box-sizing: border-box;
        }
        table {
            width: 100%;
            min-width: 1100px; /* 최소 너비 유지 */
            border-collapse: collapse;
            margin-bottom: 10px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px 10px; /* 세로 패딩 줄임 */
            text-align: left;
            white-space: nowrap; /* 줄 바꿈 방지 */
        }
        th {
            background-color: #34495e;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #ecf0f1;
        }
        .search-form input[type="text"],
        .search-form select {
            padding: 6px;
            border-radius: 3px;
            border: 1px solid #ccc;
            margin-right: 5px;
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
    </style>
</head>
<body>
<jsp:include page="/resource/adminHeader.jsp" />

<main>
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
        <form method="get" action="list.do" class="search-form" style="margin: 0;">
            <select name="searchDiv">
                <option value="" <c:if test="${empty searchDiv}">selected</c:if>>전체</option>
                <option value="name" <c:if test="${searchDiv == 'name'}">selected</c:if>>이름</option>
                <option value="id" <c:if test="${searchDiv == 'id'}">selected</c:if>>회원ID</option>
                <option value="phone" <c:if test="${searchDiv == 'phone'}">selected</c:if>>전화번호</option>
            </select>
            <input type="text" name="searchWord" value="${fn:escapeXml(searchWord)}" placeholder="검색어 입력"/>
            <button type="submit">검색</button>
        </form>
    </div>

    <table>
        <thead>
            <tr>
                <th>예약번호</th>
                <th>회원ID</th>
                <th>이름</th>
                <th>전화번호</th>
                <th>차량명</th>
                <th>제조사</th>
                <th>지점명</th>
                <th>정비일</th>
                <th>정비 내용</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty list}">
                    <tr>
                        <td colspan="9" style="text-align:center;">조회된 정비 신청이 없습니다.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="dto" items="${list}">
                        <tr>
                            <td>${dto.repairNo}</td>
                            <td>${dto.id}</td>
                            <td>${dto.name}</td>
                            <td>${dto.phone}</td>
                            <td>${dto.productName}</td>
                            <td>${dto.carMf}</td>
                            <td>${dto.retailerName}</td>
                            <td><fmt:formatDate value="${dto.repairDate}" pattern="yyyy-MM-dd"/></td>
                            <td>${dto.repairDesc}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <div class="paging">
        <c:set var="pageBlock" value="10"/>
        <c:set var="startPage" value="${((pageNo - 1) / pageBlock) * pageBlock + 1}"/>
        <c:set var="endPage" value="${startPage + pageBlock - 1}"/>
        <c:if test="${endPage > totalPages}">
            <c:set var="endPage" value="${totalPages}"/>
        </c:if>

        <c:choose>
            <c:when test="${pageNo <= 1}">
                <a href="#" class="disabled">&laquo; 이전</a>
            </c:when>
            <c:otherwise>
                <a href="list.do?pageNo=${pageNo - 1}&searchDiv=${searchDiv}&searchWord=${fn:escapeXml(searchWord)}">&laquo; 이전</a>
            </c:otherwise>
        </c:choose>

        <c:forEach begin="${startPage}" end="${endPage}" var="i">
            <c:choose>
                <c:when test="${i == pageNo}">
                    <a href="#" class="current">${i}</a>
                </c:when>
                <c:otherwise>
                    <a href="list.do?pageNo=${i}&searchDiv=${searchDiv}&searchWord=${fn:escapeXml(searchWord)}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:choose>
            <c:when test="${pageNo >= totalPages}">
                <a href="#" class="disabled">다음 &raquo;</a>
            </c:when>
            <c:otherwise>
                <a href="list.do?pageNo=${pageNo + 1}&searchDiv=${searchDiv}&searchWord=${fn:escapeXml(searchWord)}">다음 &raquo;</a>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<jsp:include page="/resource/adminFooter.jsp" />
</body>
</html>