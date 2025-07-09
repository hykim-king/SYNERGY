<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>시승 신청</title>
   <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f8;
        }

        header {
            background-color: #00274d;
            color: white;
            padding: 20px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .nav-left, .nav-right {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        a {
            color: white;
            text-decoration: none;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        main {
            padding: 60px 40px;
            text-align: center;
        }

        footer {
            background-color: #ddd;
            padding: 20px;
            text-align: center;
            font-size: 14px;
        }
    </style>

</head>
<body>

<header>
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/car/allModels.do">전체 차량 모델</a>
        <a href="${pageContext.request.contextPath}/retailer/search.do">리테일러 찾기</a>
        <a href="#" onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/drive/form.do')">시승 신청</a>
        <a href="#" onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/repair/form.do')">정비 신청</a>
        <a href="${pageContext.request.contextPath}/board/doRetrieve.do">자유게시판</a>
        <a href="${pageContext.request.contextPath}/event/doRetrieve.do">이벤트</a>
    </div>

    <div class="nav-right">
        <c:choose>
            <c:when test="${not empty sessionScope.loginUser}">
                <span>👤 ${sessionScope.loginUser.nickname}님</span>
                <a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/member/loginView.do">🔐 로그인</a>
            </c:otherwise>
        </c:choose>
    </div>
</header>


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
		<label for="phone">휴대폰 번호:</label> <input type="text" name="phone"
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
             
<footer>
    ⓒ 2025 자동차 브랜드. All rights reserved.
</footer>

</body>
</html>