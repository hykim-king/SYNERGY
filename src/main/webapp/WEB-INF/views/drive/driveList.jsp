<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/resource/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>시승 신청 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/drive.css">
    <style>
        table { border-collapse: collapse; width: 100%;  font-size: 13px;}
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background-color: #f5f5f5; }
    </style>
</head>
<body>
  <div class="form-container">
    <h2>나의 시승 신청 목록</h2>
    <table>
        <thead>
            <tr>
                <th>예약번호</th>
                <th>회원ID</th>
                <th>이름</th>
                <th>연락처</th>
                <th>브랜드</th>
                <th>차량명</th>
                <th>업체명</th>
                <th>시승 희망일</th>
                <th>예약 취소</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="dto" items="${driveList}">
                <tr>
                    <td>${dto.resNo}</td>
                    <td>${dto.id}</td>
                    <td>${dto.name}</td>
                    <td>${dto.phone}</td>
                    <td>${dto.carMf}</td>
                    <td>${dto.productName}</td>
                    <td>
                        ${dto.retailerName}
                         <button class="retailer-detail-btn" data-code="${dto.retailerCode}" style="margin-left: 5px;">
                                              상세보기  </button>
                     </td>
                    <td><fmt:formatDate value="${dto.driveDate}" pattern="yyyy-MM-dd" /></td>
                        <td>
                        <button onclick="cancelConfirm(${dto.resNo})" title="시승신청 취소" style="border:none; background:none; cursor:pointer; font-size:18px; color:gray;">
    🗑️</button>
                       </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    </div>
    
    
    <!-- 📌 모달 -->
<!-- 📌 모달창 -->
<div id="retailerModal" style="display:none; position:fixed; top:20%; left:30%; width:400px; background:#fff; border:1px solid #ccc; padding:20px; box-shadow:2px 2px 10px #999; z-index:1000;">
  <h3>🏪 업체 상세 정보</h3>
  <p><strong>업체명:</strong> <span id="modalName"></span></p>
  <p><strong>지역:</strong> <span id="modalArea"></span></p>
  <p><strong>주소:</strong> <span id="modalAddress"></span></p>
  <p><strong>전화번호:</strong> <span id="modalPhone"></span></p>
  <button onclick="document.getElementById('retailerModal').style.display='none'">닫기</button>
</div>


<!-- 📌 이벤트 스크립트 -->

<!-- jQuery CDN  -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
$(document).ready(function() {
  $('.retailer-detail-btn').on('click', function() {
    let code = $(this).data('code');

    $.ajax({
      url: '/ehr/drive/retailer-detail.do',
      type: 'GET',
      data: { retailerCode: code },
      success: function(data) {
        $('#modalName').text(data.retailerName);
        $('#modalArea').text(data.area);
        $('#modalAddress').text(data.address);
        $('#modalPhone').text(data.telephone);
        $('#retailerModal').show();
      },
      error: function() {
        alert('업체 정보를 불러오는 데 실패했습니다.');
      }
    });
  });
});
</script>
<script>
  function cancelConfirm(resNo) {
    if (confirm("정말로 해당 시승 신청을 취소하시겠습니까?")) {
      location.href = '/ehr/drive/delete.do?resNo=' + resNo;
    }
  }
</script>
<c:if test="${not empty msg}">
  <script>
    alert("${msg}");
  </script>
</c:if>
<%@ include file="/resource/footer.jsp" %>
</body>
</html>
