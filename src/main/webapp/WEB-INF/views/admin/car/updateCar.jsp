<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>차량 정보 수정</title>
<style>
body {
    font-family: Arial, sans-serif;
    background-color: #f7f9fc;
    margin: 0;
    padding: 0;
}

header {
    background-color: #2c3e50;
    color: white;
    padding: 15px 20px;
}

main {
    padding: 20px;
}

form {
    background-color: white;
    padding: 20px;
    border: 1px solid #ccc;
    max-width: 600px;
    margin: 0 auto;
    border-radius: 4px;
}

label {
    display: block;
    margin-top: 10px;
    font-weight: bold;
}

input[type="text"], input[type="number"] {
    width: 100%;
    padding: 8px;
    margin-top: 4px;
    border-radius: 3px;
    border: 1px solid #ccc;
    box-sizing: border-box;
}

/* 버튼 공통 스타일 */
.action-btn {
    margin-top: 15px;
    padding: 10px 15px;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    font-size: 14px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
}

/* 수정 완료 버튼 스타일 */
.action-btn.submit {
    background-color: #27ae60;
}

.action-btn.submit:hover {
    background-color: #2ecc71;
}

/* 취소 버튼 스타일 */
.action-btn.cancel {
    background-color: #c0392b;
    margin-left: 10px;
}

.action-btn.cancel:hover {
    background-color: #e74c3c;
}
</style>
</head>
<body>
<header>
    <h1>차량 정보 수정</h1>
</header>

<main>
    <form action="${pageContext.request.contextPath}/admin/car/update.do" method="post">
        <input type="hidden" name="carCode" value="${car.carCode}" />

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

        <button type="submit" class="action-btn submit">수정 완료</button>
        <a href="${pageContext.request.contextPath}/admin/car/list.do" class="action-btn cancel">취소</a>
    </form>
</main>
</body>
</html>