<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>차량 등록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 30px;
        }
        form {
            width: 600px;
            margin: 0 auto;
        }
        h2 {
            text-align: center;
        }
        label {
            display: block;
            margin-top: 12px;
            font-weight: bold;
        }
        input[type="text"], input[type="number"], select {
            width: 100%;
            padding: 8px;
            margin-top: 4px;
            box-sizing: border-box;
        }
        .radio-group {
            margin-top: 4px;
        }
        .radio-group label {
            display: inline-block;
            margin-right: 15px;
            font-weight: normal;
        }
        .btn-container {
            text-align: center;
            margin-top: 20px;
        }
        .btn-container button {
            padding: 10px 20px;
            margin: 0 10px;
        }
    </style>
</head>
<body>

    <h2>🚗 차량 등록</h2>
    <form action="${pageContext.request.contextPath}/admin/car/add.do" method="post">
        <label for="productName">제품명</label>
        <input type="text" name="productName" required />

        <label for="carMf">제조사</label>
        <input type="text" name="carMf" required />

        <label for="cartype">차종</label>
        <input type="text" name="cartype" required />

        <label for="orgFn">원본 파일명</label>
        <input type="text" name="orgFn" />

        <label for="modFn">수정된 파일명</label>
        <input type="text" name="modFn" />

        <label for="path">경로</label>
        <input type="text" name="path" />

        <label for="price">가격</label>
        <input type="number" name="price" required />

        <label>연료</label>
        <div class="radio-group">
            <label><input type="radio" name="fuel" value="가솔린" required> 가솔린</label>
            <label><input type="radio" name="fuel" value="디젤"> 디젤</label>
            <label><input type="radio" name="fuel" value="전기"> 전기</label>
        </div>

        <label for="ef">연비</label>
        <input type="number" step="0.1" name="ef" />

        <label for="engine">엔진</label>
        <input type="text" name="engine" />

        <label for="dpm">배기량</label>
        <input type="number" name="dpm" />

        <label for="battery">배터리 용량</label>
        <input type="number" step="0.1" name="battery" />

        <label for="mfDt">제조년도</label>
        <input type="number" name="mfDt" required />

        <div class="btn-container">
            <button type="submit">등록</button>
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/admin/main.do'">취소</button>
        </div>
    </form>

</body>
</html>