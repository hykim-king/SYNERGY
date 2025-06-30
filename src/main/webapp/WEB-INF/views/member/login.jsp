<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CarPick</title>
<style>
        body { font-family: Arial, sans-serif; background-color: #f2f2f2; }
        .container {
            width: 300px; padding: 20px; margin: 100px auto;
            background: white; border-radius: 8px; box-shadow: 0 0 10px #ccc;
        }
        h2 { text-align: center; margin-bottom: 20px; }
        .error { color: red; margin-bottom: 10px; text-align: center; }
        label { display: block; margin: 10px 0 5px; }
        input[type="text"], input[type="password"] {
            width: 100%; padding: 8px; box-sizing: border-box;
            border: 1px solid #ccc; border-radius: 4px;
        }
        button {
            width: 100%; padding: 10px; background-color: #4CAF50;
            color: white; border: none; border-radius: 4px;
            cursor: pointer; font-size: 16px;
        }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
<div class="container">
    <h2>로그인</h2>

    <%-- 에러 메시지 출력 --%>
    <c:if test="${not empty message}">
        <div class="error">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/member/login.do" method="post">
        <label for="id">아이디</label>
        <input type="text" id="id" name="id" required autofocus />

        <label for="pwd">비밀번호</label>
        <input type="password" id="pwd" name="pwd" required />

        <button type="submit">로그인</button>
    </form>
</div>

</body>
</html>