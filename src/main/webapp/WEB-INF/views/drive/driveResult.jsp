<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>시승신청 결과</title>
</head>
<body>
    <h2>시승신청 결과</h2>
    <c:choose>
        <c:when test="${success}">
            <p>🎉 ${dto.name}님, 시승 신청이 성공적으로 완료되었습니다!</p>
            <ul>
                <li>예약번호: ${dto.resNo}</li>
                <li>차량 코드: ${dto.carCode}</li>
                <li>리테일러 코드: ${dto.retailerCode}</li>
                <li>희망 시승일: ${dto.driveDate}</li>
            </ul>
        </c:when>
        <c:otherwise>
            <p>❌ 시승 신청에 실패했습니다. 다시 시도해주세요.</p>
        </c:otherwise>
    </c:choose>
   
<div style="margin-top: 20px;">
    <p>
      <a href="<c:url value='/drive/메인주소넣기'/>">메인으로 가기</a> |
      <a href="<c:url value='/drive/list.do'/>">내 신청 목록 보기</a>
    </p>
</body>
</html>
