<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/resource/header.jsp" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>정비 신청 결과</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/drive.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/result.css">

</head>
<body>
  <div class="form-container">
  <h2 class="result-title">🧑‍🔧<strong>${dto.name}님, 정비신청이 완료되었습니다!</strong>👩‍🔧</h2>
  <div class="result-box">
    <c:choose>
        <c:when test="${success}">
      <p class="success-msg">
         <strong>${dto.name}님의 신청내용</strong> 
            <ul>
                  <ul class="result-list">
                <li><strong>📌 예약번호:</strong> ${dto.repairNo}</li>
                <li><strong>* 희망 정비일:</strong> <fmt:formatDate value="${dto.repairDate}" pattern="yyyy-MM-dd"/></li>
                <li><strong>* 신청 브랜드:</strong> ${dto.carMf}</li>
                <li><strong>* 제품명:</strong> ${dto.productName}</li>
                <li><strong>* 정비요청사항:</strong> ${dto.repairDesc}</li>                
                <li><strong>* 신청 업체:</strong> ${dto.retailerName}</li><br>
                        <li> 작성하신 내용 확인 후 담당자가 연락드리겠습니다. </li>
            </ul>
        </c:when>
        <c:otherwise>
            <p class="fail-msg">❌ 정비 신청에 실패했습니다. 다시 시도해주세요.</p>
        </c:otherwise>
    </c:choose>
</div>

<div class="result-links">
  <a class="btn-link" href="<c:url value='/main/main.do'/>">🏠 메인으로 가기</a>
  <span class="divider">|</span>
  <a class="btn-link" href="<c:url value='/repair/list.do'/>">📋 내 신청 목록 보기</a>
</div>
    </div>
    
    <%@ include file="/resource/footer.jsp" %>
</body>
</html>
