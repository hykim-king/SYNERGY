<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>시승 신청</title>
</head>
<body>
    <h2>🚗 시승 신청 약식</h2>
    <form action="<c:url value='/drive/apply.do'/>" method="post">
        <label for="id">아이디:</label><br>
        <input type="text" id="id" name="id" ><br><br>

        <label for="name">이름:</label><br>
        <input type="text" id="name" name="name" required><br><br>

        <label for="phone">휴대폰 번호:</label><br>
        <input type="text" id="phone" name="phone" required><br><br>

        <label for="carCode">차량 코드:</label><br>
        <input type="number" id="carCode" name="carCode" required><br><br>

        <label for="retailerCode">리테일러 코드:</label><br>
        <input type="number" id="retailerCode" name="retailerCode" required><br><br>

        <label for="driveDate">시승 희망 날짜:</label><br>
        <input type="date" id="driveDate" name="driveDate" required><br><br>

        <button type="submit">시승 신청</button>
    </form>
</body>
</html>