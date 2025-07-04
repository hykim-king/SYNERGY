<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>차량 정보 수정</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
            padding: 20px;
        }
        h2 {
            color: #2c3e50;
        }
        form {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="number"] {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border-radius: 4px;
            border: 1px solid #ccc;
        }
        .button-group {
            margin-top: 20px;
            text-align: right;
        }
        .button-group button {
            background-color: #2980b9;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }
        .button-group button:hover {
            background-color: #3498db;
        }
        .msg {
            color: red;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<h2>차량 정보 수정</h2>

<!-- 실패 메시지 출력 -->
<c:if test="${not empty msg}">
    <div class="msg">${msg}</div>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/admin/car/update.do">
    <!-- 읽기 전용: 차량 코드 -->
    <label for="carCode">차량 코드</label>
    <input type="text" id="carCode" name="carCode" value="${car.carCode}" readonly />

    <label for="productName">차량명</label>
    <input type="text" id="productName" name="productName" value="${car.productName}" required />

    <label for="carMf">제조사</label>
    <input type="text" id="carMf" name="carMf" value="${car.carMf}" required />

    <label for="cartype">차종</label>
    <input type="text" id="cartype" name="cartype" value="${car.cartype}" required />

    <label for="mfDt">제조년도</label>
    <input type="text" id="mfDt" name="mfDt" value="${car.mfDt}" required />

    <label for="price">가격</label>
    <input type="number" id="price" name="price" value="${car.price}" required />

    <label for="fuel">연료</label>
    <input type="text" id="fuel" name="fuel" value="${car.fuel}" />

    <label for="ef">효율</label>
    <input type="text" id="ef" name="ef" value="${car.ef}" />

    <label for="engine">엔진</label>
    <input type="text" id="engine" name="engine" value="${car.engine}" />

    <label for="battery">배터리</label>
    <input type="text" id="battery" name="battery" value="${car.battery}" />

    <div class="button-group">
        <button type="submit">수정 완료</button>
    </div>
</form>

</body>
</html>