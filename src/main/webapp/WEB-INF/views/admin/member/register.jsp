<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>회원등록</title>
<style>
body {
    font-family: Arial, sans-serif;
    background: #f4f6f8;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
}
.form-container {
    background: white;
    padding: 40px 30px;
    border-radius: 10px;
    box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    width: 100%;
    max-width: 500px;
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
input[type="text"], input[type="password"], input[type="email"] {
    width: 100%;
    padding: 10px;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-size: 14px;
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
.error-message {
    color: red;
    margin-bottom: 15px;
    font-weight: bold;
    text-align: center;
}
</style>

<script>
function checkId() {
    const id = document.getElementById('id').value.trim();
    if (!id) {
        alert('아이디를 입력해주세요.');
        return;
    }

    fetch('${pageContext.request.contextPath}/admin/member/checkId.do?id=' + encodeURIComponent(id))
        .then(response => response.text())
        .then(result => {
            if (result === 'available') {
                alert('사용 가능한 아이디입니다.');
            } else if (result === 'duplicate') {
                alert('이미 사용 중인 아이디입니다.');
            } else {
                alert('오류가 발생했습니다.');
            }
        })
        .catch(() => alert('서버와 통신 중 오류가 발생했습니다.'));
}
</script>
</head>
<body>

<div class="form-container">
    <h2>회원등록</h2>

    <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/member/register.do" method="post">
        <label for="id">아이디</label>
        <div class="id-check-group">
            <input type="text" name="id" id="id" required maxlength="320" />
            <button type="button" class="id-check-btn" onclick="checkId()">중복확인</button>
        </div>

        <label for="pwd">비밀번호 (최대 30글자)</label>
        <input type="password" name="pwd" id="pwd" required maxlength="30" />

        <label for="nickname">닉네임 (최대 10글자, 한글 기준)</label>
        <input type="text" name="nickname" id="nickname" maxlength="30" />

        <label for="name">이름</label>
        <input type="text" name="name" id="name" required maxlength="30" />

        <label for="phone">전화번호 ('-' 포함)</label>
        <input type="text" name="phone" id="phone" maxlength="13" />

        <label for="email">이메일</label>
        <input type="email" name="email" id="email" required maxlength="320" />

        <input type="submit" value="회원등록" />
    </form>
</div>

</body>
</html>