<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
     <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f6f8;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background-color: white;
            padding: 50px 40px;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 550px;
            box-sizing: border-box;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }

        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
            color: #555;
        }

        input[type="text"],
        input[type="password"],
        input[type="email"] {
            width: 100%;
            padding: 12px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 15px;
            box-sizing: border-box;
        }

        .id-check-group {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .id-check-group input[type="text"] {
            flex: 1;
        }

        .id-check-btn {
            padding: 10px 15px;
            background-color: #6c757d;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
        }

        .id-check-btn:hover {
            background-color: #5a6268;
        }

        input[type="submit"] {
            width: 100%;
            background-color: #28a745;
            color: white;
            padding: 14px;
            font-size: 16px;
            border: none;
            border-radius: 6px;
            margin-top: 25px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #218838;
        }

        .message {
            color: red;
            text-align: center;
            margin-bottom: 15px;
            font-weight: 600;
        }
    </style>

    <script>
        function checkId() {
            const id = document.getElementById('id').value;
            if (!id) {
                alert('아이디를 입력해주세요.');
                return;
            }

            fetch('${pageContext.request.contextPath}/member/checkId.do?id=' + encodeURIComponent(id))
                .then(response => response.text())
                .then(result => {
                    if (result === 'available') {
                        alert('사용 가능한 아이디입니다.');
                    } else if (result === 'duplicate') {
                        alert('이미 사용 중인 아이디입니다.');
                    } else {
                        alert('오류가 발생했습니다.');
                    }
                });
        }
    </script>
</head>
<body>

<div class="form-container">
    <h2>회원가입</h2>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/member/register.do" method="post">
        <label for="id">아이디</label>
        <input type="text" name="id" id="id" required maxlength="320" />

        <label for="pwd">비밀번호<b>(최대 30글자)</b></label>
        <input type="password" name="pwd" id="pwd" required maxlength="30" />

        <label for="nickname">닉네임<b>(최대 10글자)</b></label>
        <input type="text" name="nickname" id="nickname" maxlength="30" />

        <label for="name">이름</label>
        <input type="text" name="name" id="name" required maxlength="30" />

        <label for="phone">전화번호<b>('-'까지 다 적어주세요)</b></label>
        <input type="text" name="phone" id="phone" maxlength="13" />

        <label for="email">이메일</label>
        <input type="email" name="email" id="email" required maxlength="320" />

        <input type="submit" value="회원가입" />
    </form>
</div>

</body>
</html>