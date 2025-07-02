<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>CarPick 로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .login-container {
            background-color: white;
            padding: 40px 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
            box-sizing: border-box;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #555;
            font-weight: 600;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 14px;
        }

        .button-row {
            display: flex;
            justify-content: space-between;
            gap: 10px;
        }

        .button-row button,
        .button-row a {
            flex: 1;
            padding: 12px;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            text-align: center;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .login-btn {
            background-color: #2d89ef;
            color: white;
        }

        .login-btn:hover {
            background-color: #1b61c1;
        }

        .register-btn {
            background-color: #6c757d;
            color: white;
        }

        .register-btn:hover {
            background-color: #5a6268;
        }

        .message {
            color: red;
            text-align: center;
            margin-bottom: 15px;
            font-weight: 600;
        }
    </style>
</head>
<body>

<div class="login-container">
    <h2>CarPick 로그인</h2>

    <!-- 로그인 실패 메시지 출력 -->
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/member/login.do" method="post">
        <label for="id">아이디</label>
        <input type="text" name="id" id="id" required />

        <label for="pwd">비밀번호</label>
        <input type="password" name="pwd" id="pwd" required />

        <div class="button-row">
            <a href="${pageContext.request.contextPath}/member/registerView.do" class="register-btn">회원가입</a>
            <button type="submit" class="login-btn">로그인</button>
        </div>
    </form>
</div>

</body>
</html>