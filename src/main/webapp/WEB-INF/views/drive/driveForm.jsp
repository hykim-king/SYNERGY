<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>시승 신청</title>
</head>
<body>
    <h2>🚗 시승 신청 약식</h2>
    <form action="<c:url value='/drive/apply.do'/>" method="post">
       <!-- 아이디 -->
<input type="text" name="id" value="${loginId}" readonly />

<!-- 이름 -->
<input type="text" name="name" maxlength="30" />

<!-- 연락처 -->
<input type="text" name="phone" pattern="\\d{3}-\\d{4}-\\d{4}" required />

<!-- 시승 날짜 -->
<input type="date" name="driveDate" required />

<!-- 제조사 선택 -->
<select id="carMf" name="carMf">
   <option value="">제조사 선택</option>
</select>

<!-- 차량 선택 -->
<select id="carCode" name="carCode">
   <option value="">차량 선택</option>
</select>

<!-- 업체 선택 -->
<select id="retailerCode" name="retailerCode">
   <option value="">업체 선택</option>
</select>

        <button type="submit">시승 신청</button>
    </form>
</body>
</html>