<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
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

input[type="text"], input[type="password"] {
    width: 100%;
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ccc;
    border-radius: 6px;
    box-sizing: border-box;
    font-size: 14px;
}

.login-btn {
    background-color: #2d89ef;
    color: white;
    border: none;
    border-radius: 6px;
    padding: 12px;
    font-size: 16px;
    cursor: pointer;
    width: 100%;
    transition: background-color 0.3s ease;
}

.login-btn:hover {
    background-color: #1b61c1;
}

.register-btn {
    background-color: #6c757d;
    color: white;
    border-radius: 6px;
    padding: 12px 0;
    text-align: center;
    text-decoration: none;
    font-size: 16px;
    transition: background-color 0.3s ease;
    display: block;
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

.links-container {
    margin-top: 15px;
    display: flex;
    justify-content: space-between;
    gap: 10px;
}

.links-container a {
    flex: 1;
}
</style>
</head>
<body>

<c:if test="${not empty sessionScope.message}">
    <script>
        alert('<c:out value="${sessionScope.message}" />');
    </script>
    <c:remove var="message" scope="session" />
</c:if>

<div class="login-container">
    <h2>CarPick 로그인</h2>

    <form action="${pageContext.request.contextPath}/member/login.do" method="post">
        <label for="id">아이디</label> 
        <input type="text" name="id" id="id" required /> 

        <label for="pwd">비밀번호</label> 
        <input type="password" name="pwd" id="pwd" required />

        <div style="margin-top: 20px;">
            <button type="submit" class="login-btn">로그인</button>
        </div>
    </form>

    <div class="links-container">
        <a href="${pageContext.request.contextPath}/member/registerView.do" class="register-btn">회원가입</a>
        <a href="${pageContext.request.contextPath}/member/passwordFind.do" class="register-btn">비밀번호 찾기</a>
    </div>
</div>

</body>
</html>