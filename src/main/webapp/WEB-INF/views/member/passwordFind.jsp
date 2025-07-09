<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 찾기</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .find-container {
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

        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 14px;
        }

        button {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            background-color: #2d89ef;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #1b61c1;
        }

        .message {
            text-align: center;
            margin-bottom: 15px;
            font-weight: 600;
        }

        .error {
            color: red;
        }

        .link-area {
            text-align: center;
            margin-top: 20px;
        }

        .link-area a {
            color: #2c3e50;
            text-decoration: none;
        }

        .link-area a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="find-container">
    <h2>비밀번호 찾기</h2>

    <!-- 에러 메시지 -->
    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/member/passwordFind.do" method="post">
        <label for="userId">아이디</label>
        <input type="text" id="userId" name="userId" required>

        <button type="submit">확인</button>
    </form>

    <div class="link-area">
        <a href="${pageContext.request.contextPath}/member/loginView.do">로그인 화면으로 돌아가기</a>
    </div>
</div>

</body>
</html>