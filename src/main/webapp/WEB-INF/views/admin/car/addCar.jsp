<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>차량 등록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 700px;
            margin: 40px auto;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            color: #2c3e50;
        }
        form label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }
        form input[type="text"],
        form input[type="number"],
        form input[type="date"],
        form input[type="submit"],
        form input[type="reset"] {
            width: 100%;
            padding: 8px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .radio-group {
            margin-top: 6px;
        }
        .radio-group input {
            margin-right: 8px;
        }
        .btn-group {
            margin-top: 25px;
            text-align: center;
        }
        .btn-group input {
            width: 30%;
            margin: 0 10px 10px 10px;
            background-color: #3498db;
            color: white;
            border: none;
            font-size: 16px;
        }
        .btn-group input:hover {
            background-color: #2980b9;
        }
        .btn-link {
            display: inline-block;
            width: 30%;
            padding: 8px 0;
            margin: 0 10px;
            background-color: #2c3e50;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            font-size: 16px;
        }
        .btn-link:hover {
            background-color: #34495e;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>차량 등록</h2>
    <form action="${pageContext.request.contextPath}/admin/car/add.do" method="post">
        <label for="productName">모델명</label>
        <input type="text" id="productName" name="productName" required>

        <label for="carMf">제조사</label>
        <input type="text" id="carMf" name="carMf" required>

        <label for="cartype">차종</label>
        <input type="text" id="cartype" name="cartype" required>

        <label for="price">가격</label>
        <input type="number" id="price" name="price" required min="0">

        <label>연료</label>
        <div class="radio-group">
            <label><input type="radio" name="fuel" value="가솔린" required> 가솔린</label>
            <label><input type="radio" name="fuel" value="디젤"> 디젤</label>
            <label><input type="radio" name="fuel" value="전기"> 전기</label>
        </div>

        <label for="ef">연비</label>
        <input type="number" step="0.1" id="ef" name="ef" required min="0">

        <label for="engine">엔진</label>
        <input type="text" id="engine" name="engine" required>

        <label for="dpm">배기량 (선택)</label>
        <input type="number" id="dpm" name="dpm" min="0">

        <label for="battery">배터리 (선택)</label>
        <input type="number" step="0.1" id="battery" name="battery" min="0">

        <label for="mfDt">제조년도</label>
        <input type="number" id="mfDt" name="mfDt" required min="1990" max="2099">

        <div class="btn-group">
            <input type="submit" value="등록">
            <input type="reset" value="초기화">
            <a href="${pageContext.request.contextPath}/admin/main.do" class="btn-link">메인으로 이동</a>
        </div>
    </form>
</div>

</body>
</html>