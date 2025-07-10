<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>리테일러 정보 수정</title>
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

input[type="text"], input[type="tel"], textarea {
    width: 100%;
    padding: 8px;
    margin-top: 4px;
    border-radius: 3px;
    border: 1px solid #ccc;
    box-sizing: border-box;
}

textarea {
    resize: vertical;
}

.btn-group {
    margin-top: 15px;
}

input[type="submit"], input[type="reset"], .btn-link {
    padding: 10px 15px;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
    font-size: 14px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    margin-right: 10px;
}

input[type="submit"] {
    background-color: #27ae60;
}

input[type="submit"]:hover {
    background-color: #2ecc71;
}

input[type="reset"] {
    background-color: #c0392b;
}

input[type="reset"]:hover {
    background-color: #e74c3c;
}

.btn-link {
    background-color: #2980b9;
    line-height: 1.8;
}

.btn-link:hover {
    background-color: #3498db;
}
</style>
</head>
<body>
<header>
    <h1>리테일러 정보 수정</h1>
</header>

<main>
    <form action="${pageContext.request.contextPath}/admin/retailer/update.do" method="post">
        <input type="hidden" name="retailerCode" value="${retailer.retailerCode}" />

        <label for="retailerName">업체명</label>
        <input type="text" id="retailerName" name="retailerName" value="${retailer.retailerName}" required />

        <label for="productName">제품명</label>
        <input type="text" id="productName" name="productName" value="${retailer.productName}" required />

        <label for="carMf">제조사</label>
        <input type="text" id="carMf" name="carMf" value="${retailer.carMf}" required />

        <label for="area">지역</label>
        <input type="text" id="area" name="area" value="${retailer.area}" required />

        <label for="address">상세 주소</label>
        <textarea id="address" name="address" rows="3" required>${retailer.address}</textarea>

        <label for="telephone">전화번호</label>
        <input type="tel" id="telephone" name="telephone" value="${retailer.telephone}" required pattern="[0-9\-+ ]+" />

        <div class="btn-group">
            <input type="submit" value="수정 완료" />
            <input type="reset" value="초기화" class="reset-btn" />
            <a href="${pageContext.request.contextPath}/admin/main.do" class="btn-link">메인으로 이동</a>
        </div>
    </form>
</main>
</body>
</html>