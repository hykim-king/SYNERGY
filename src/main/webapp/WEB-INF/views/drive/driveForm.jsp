<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>시승 신청</title>
</head>
<body>
	<h2>🚗 시승 신청 양식</h2>
	<form action="<c:url value='/drive/apply.do'/>" method="post">
		<!-- 아이디 -->
		<label for="id">아이디:</label> <input type="text" name="id"
			value="${loginId}" readonly /><br><br>

		<!-- 이름 -->
		<label for="name">이름:</label> <input type="text" name="name"
			maxlength="30" /><br><br>

		<!-- 연락처 -->
		<label for="phone">휴대폰 번호:</label> <input type="text" name="phone"
			pattern="\d{3}-\d{4}-\d{4}" required /><br><br>

		<!-- 시승 날짜 -->
		<label for="driveDate">시승 희망 날짜:</label> <input type="date"
			name="driveDate" required /><br><br>

	<!-- 제조사 선택 -->
<label>자동차 브랜드:</label>
<select id="carMf" name="dummy">
  <option value="">-- 선택 --</option>
</select><br><br>

<!-- 차량 선택 -->
<label>제품명</label>
<select id="product" name="dummy">
  <option value="">-- 선택 --</option>
</select><br><br>

<!-- 업체 선택 -->
<table border="1" id="retailerTable">
  <thead>
    <tr>
      <th>선택</th><th>업체명</th><th>지역</th><th>주소</th><th>전화</th>
    </tr>
  </thead>
  <tbody></tbody>
</table>

<!--  실제 전송하는 히든 필드 -->
<input type="hidden" name="carCode" id="carCode"/>
<input type="hidden" name="retailerCode" id="retailerCode"/>

		<button type="submit">시승 신청</button>
	</form>
	
<!-- 	숨어있는 코드 -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(function(){
	  // (1) 제조사 목록 로딩
	  $.getJSON('mfList.do', function(mfList){
	    const $carMf = $('#carMf');
	    mfList.forEach(function(mf){
	      $carMf.append($('<option>').val(mf).text(mf));
	    });
	  });

	  // (2) 제조사 → 제품명 연결
	  $('#carMf').change(function(){
	    const mf = $(this).val();
	    $('#product').empty().append('<option value="">-- 선택 --</option>');
	    $('#carCode, #retailerCode').val('');
	    $('#retailerTable tbody').empty();

	    if (!mf) return;

	    $.getJSON('productList.do', {carMf: mf}, function(list){
	      list.forEach(function(car){
	        $('#product').append(
	          $('<option>').val(car.carCode).text(car.productName)
	        );
	      });
	    });
	  });

	  // (3) 제품명 선택 시: carCode 설정 + 업체 리스트 표시
	  $('#product').change(function(){
	    const mf   = $('#carMf').val();
	    const prod = $(this).find('option:selected').text();
	    const carCode = $(this).val();

	    if (carCode) {
	      $('#carCode').val(carCode);
	    } else {
	      $('#carCode').val('');
	    }

	    $('#retailerCode').val('');
	    $('#retailerTable tbody').empty();

	    if (!prod) return;

	    $.getJSON('retailerList.do', {productName: prod}, function(list){
	      list.forEach(function(r){
	        const $tr = $('<tr>')
	          .append($('<td>').append(
	            $('<input type="radio" name="retailerRadio"/>')
	              .val(r.retailerCode)
	              .click(function(){
	                $('#retailerCode').val(r.retailerCode);
	              })
	          ))
	          .append($('<td>').text(r.retailerName))
	          .append($('<td>').text(r.area))
	          .append($('<td>').text(r.address))
	          .append($('<td>').text(r.telephone));
	        $('#retailerTable tbody').append($tr);
	      });
	    });
	  });
	});
	</script>
	
	
	
</body>
</html>