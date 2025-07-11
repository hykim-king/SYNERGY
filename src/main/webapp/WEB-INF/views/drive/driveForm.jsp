<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/resource/header.jsp" %>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>시승 신청</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/drive.css">
</head>
<body>
<div class="banner-drive" style="text-align: center; margin: 10; padding: 0;">
  <img src="${pageContext.request.contextPath}/resource/banner/drive_form.png" 
       alt="시승신청배너" 
       style="width: 80%; max-width: 1000px; height: auto;  display: block; margin: 0 auto;" />
</div>

  <div class="form-container">
	<h2>🚗 시승 신청 양식</h2>
	<form action="<c:url value='/drive/apply.do'/>" method="post">
		<!-- 아이디 -->
		<label for="id">아이디:</label> <input type="text" name="id"
			value="${loginId}" readonly /><br>
		<br>

		<!-- 이름 -->
		<label for="name">이름:</label> <input type="text" name="name"
			maxlength="30" required/><br>
		<br>

		<!-- 연락처 -->
		<label for="phone">연락처:</label> <input type="text" name="phone"
			placeholder="000-0000-0000" pattern="\d{3}-\d{4}-\d{4}" required /><br>
		<br>

		<!-- 시승 날짜 -->
		<label for="driveDate">시승 희망 날짜:</label> <input type="date"
			name="driveDate" required /><br>
		<br>

		<!-- 제조사 선택 -->
		<label>자동차 브랜드:</label> <select id="carMf" name="dummy" required>
			<option value="">-- 선택 --</option>
		</select><br>
		<br>

		<!-- 차량 선택 -->
		<label>제품명</label> <select id="product" name="dummy" required>
			<option value="">-- 선택 --</option>
		</select><br>
		<br>

		<!-- 업체 선택 -->
		<table border="1" id="retailerTable" >
			<thead>
				<tr>
					<th>선택</th>
					<th>업체명</th>
					<th>지역</th>
					<th>주소</th>
					<th>전화</th>
				</tr>
			</thead>
			<tbody></tbody>
		</table>

		<!--  실제 전송하는 히든 필드 -->
		<input type="hidden" name="carCode" id="carCode" /> <input
			type="hidden" name="retailerCode" id="retailerCode" />

		<button type="submit">시승 신청</button>
	</form>
  </div>
	<!-- 	숨어있는 코드 -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script>
		$(function() {
			// (1) 제조사 목록 로딩
			$.getJSON('mfList.do', function(mfList) {
				const $carMf = $('#carMf');
				mfList.forEach(function(mf) {
					$carMf.append($('<option>').val(mf).text(mf));
				});
			});

			// (2) 제조사 → 제품명 연결
			$('#carMf').change(
					function() {
						const mf = $(this).val();
						$('#product').empty().append(
								'<option value="">-- 선택 --</option>');
						$('#carCode, #retailerCode').val('');
						$('#retailerTable tbody').empty();

						if (!mf)
							return;

						$.getJSON('productList.do', {
							carMf : mf
						}, function(list) {
							list.forEach(function(car) {
								$('#product').append(
										$('<option>').val(car.carCode).text(
												car.productName));
							});
						});
					});

			// (3) 제품명 선택 시: carCode 설정 + 업체 리스트 표시
			$('#product').change(function () {
				  const mf = $('#carMf').val();
				  const prod = $(this).find('option:selected').text();
				  const carCode = $(this).val();

				  // carCode 설정
				  $('#carCode').val(carCode || '');

				  // 이전 선택 초기화
				  $('#retailerCode').val('');
				  $('#retailerTable tbody').empty();

				  if (!prod) return;

				  // 업체 리스트 불러오기
				  $.getJSON('retailerList.do', { productName: prod }, function (list) {
				    list.forEach(function (r) {
				      const $radio = $('<input>')
				        .attr('type', 'radio')
				        .attr('name', 'retailerRadio')
				        .val(r.retailerCode)
				        .on('click', function () {
				          $('#retailerCode').val(r.retailerCode);
				        });

				      const $tr = $('<tr>')
				        .append($('<td>').append($radio))
				        .append($('<td>').text(r.retailerName))
				        .append($('<td>').text(r.area))
				        .append($('<td>').text(r.address))
				        .append($('<td>').text(r.telephone));

				      $('#retailerTable tbody').append($tr);
				    });
				  });
				});
                              	});
		
             // 업체명 필수값 (테이블은 리콰이어 불가능)
             $("form").on("submit", function (e) {
               if (!$("input[name='retailerRadio']:checked").length) {
                 alert("업체를 선택해주세요.");
                 e.preventDefault();
               }
             });
             	</script>
             

<%@ include file="/resource/footer.jsp" %>
</body>
</html>