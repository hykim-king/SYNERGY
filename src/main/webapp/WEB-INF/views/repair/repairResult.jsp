<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>정비 신청 결과</title>
</head>
<body>
	  <h2>정비신청 결과</h2>
    <c:choose>
        <c:when test="${success}">
            <p>🎉 ${dto.name}님, 정비 신청이 성공적으로 완료되었습니다!</p>
            <ul>
                <li>예약번호: ${dto.repairNo}</li>
                <li>희망 정비일: <fmt:formatDate value="${dto.repairDate}" pattern="yyyy-MM-dd"/></li>
                <li>신청 브랜드: ${dto.carMf}</li>
                <li>제품명: ${dto.productName}</li>
                <li>정비요청사항: ${dto.repairDesc}</li>                
                <li>신청 업체: ${dto.retailerName}</li>
            </ul>
        </c:when>
        <c:otherwise>
            <p>❌ 정비 신청에 실패했습니다. 다시 시도해주세요.</p>
        </c:otherwise>
    </c:choose>


    <p>
      <a href="<c:url value='/main/main.do'/>">메인으로 가기</a> |
      <a href="<c:url value='/repair/list.do'/>">내 신청 목록 보기</a>
    </p>
</body>
</html>
