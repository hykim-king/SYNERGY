<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>리테일러 등록</title>
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
        form input[type="tel"],
        form textarea,
        form input[type="submit"],
        form input[type="reset"] {
            width: 100%;
            padding: 8px;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }
        form textarea {
            resize: vertical;
        }
        .btn-group {
            margin-top: 25px;
            text-align: center;
        }
        .btn-group input {
            width: 30%;
            margin: 0 10px 10px 10px;
            background-color: #27ae60;
            color: white;
            border: none;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
        }
        .btn-group input.reset-btn {
            background-color: #c0392b;
        }
        .btn-group input:hover {
            opacity: 0.9;
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
    <h2>리테일러 등록</h2>
    <form action="${pageContext.request.contextPath}/admin/retailer/add.do" method="post">
        <label for="productName">제품명</label>
        <input type="text" id="productName" name="productName" required>

        <label for="retailerName">업체명</label>
        <input type="text" id="retailerName" name="retailerName" required>

        <label for="carMf">제조사</label>
        <input type="text" id="carMf" name="carMf" required>

        <label for="area">지역</label>
        <input type="text" id="area" name="area" required>

        <label for="address">상세 주소</label>
        <textarea id="address" name="address" rows="3" required></textarea>

        <label for="telephone">전화번호</label>
        <input type="tel" id="telephone" name="telephone" required pattern="[0-9\-+ ]+">

        <div class="btn-group">
            <input type="submit" value="등록">
            <input type="reset" value="초기화" class="reset-btn">
            <a href="${pageContext.request.contextPath}/admin/main.do" class="btn-link">메인으로 이동</a>
        </div>
    </form>
</div>

</body>
</html>