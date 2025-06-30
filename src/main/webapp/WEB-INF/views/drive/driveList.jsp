<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>시승 신청 목록</title>
    <style>
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f5f5f5; }
    </style>
</head>
<body>
    <h2>시승 신청 목록</h2>
    <table>
        <thead>
            <tr>
                <th>예약번호</th>
                <th>회원ID</th>
                <th>이름</th>
                <th>연락처</th>
                <th>차량코드</th>
                <th>업체코드</th>
                <th>시승일</th>
                <th>등록일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="dto" items="${driveList}">
                <tr>
                    <td>${dto.resNo}</td>
                    <td>${dto.id}</td>
                    <td>${dto.name}</td>
                    <td>${dto.phone}</td>
                    <td>${dto.carCode}</td>
                    <td>${dto.retailerCode}</td>
                    <td><fmt:formatDate value="${dto.driveDate}" pattern="yyyy-MM-dd"/></td>
                    <td><fmt:formatDate value="${dto.regDt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
