<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>마이페이지</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f7f9fc;
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #2c3e50;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #333;
        }

        input[type="text"], input[type="email"], input[type="tel"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[readonly] {
            background-color: #eee;
        }

        .form-buttons {
            text-align: center;
            margin-top: 30px;
        }

        .form-buttons button {
            background-color: #2c3e50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            margin: 0 10px;
        }

        .form-buttons button:hover {
            background-color: #34495e;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>마이페이지</h2>

    <form method="post" action="${pageContext.request.contextPath}/member/updateInfo.do">
        <!-- 아이디(수정 불가) -->
        <div class="form-group">
            <label>아이디</label>
            <input type="text" name="id" value="${memberInfo.id}" readonly>
        </div>

        <!-- 닉네임 -->
        <div class="form-group">
            <label for="nickname">닉네임</label>
            <input type="text" name="nickname" id="nickname" value="${memberInfo.nickname}">
        </div>

        <!-- 전화번호 -->
        <div class="form-group">
            <label for="phone">전화번호</label>
            <input type="tel" name="phone" id="phone" value="${memberInfo.phone}">
        </div>

        <!-- 이메일 -->
        <div class="form-group">
            <label for="email">이메일</label>
            <input type="email" name="email" id="email" value="${memberInfo.email}">
        </div>

        <!-- 등록일 (읽기 전용, 마지막에 위치) -->
        <div class="form-group">
            <label>등록일</label>
            <input type="text" value="<fmt:formatDate value='${memberInfo.regDt}' pattern='yyyy-MM-dd' />" readonly>
        </div>

        <div class="form-buttons">
            <button type="submit">정보 수정</button>
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/main/main.do'">메인으로</button>
        </div>
    </form>
</div>

</body>
</html>