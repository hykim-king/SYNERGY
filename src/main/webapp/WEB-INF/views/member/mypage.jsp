<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>마이페이지</title>
<style>
body {
    font-family: 'Segoe UI', sans-serif;
    background-color: #f7f9fc;
    margin: 0;
    padding: 0;
    display: flex;
    min-height: 100vh;
}

/* 사이드 메뉴 */
.sidebar {
    width: 200px;
    background-color: #2c3e50;
    color: white;
    padding: 20px;
    box-sizing: border-box;
}

.sidebar h3 {
    color: #ecf0f1;
    margin-bottom: 20px;
}

.sidebar ul {
    list-style: none;
    padding: 0;
}

.sidebar li {
    margin-bottom: 10px;
}

.sidebar a {
    color: #ecf0f1;
    text-decoration: none;
    display: block;
    padding: 8px;
    border-radius: 4px;
}

.sidebar a:hover {
    background-color: #34495e;
}

.sidebar .submenu {
    margin-left: 10px;
    font-size: 0.9em;
}

.sidebar .submenu li {
    margin-top: 5px;
}

/* 메인 컨텐츠 */
.container {
    flex: 1;
    max-width: 600px;
    margin: 40px auto;
    background: white;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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

input[type="text"], input[type="email"], input[type="tel"], input[type="password"] {
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

    <!-- 사이드 메뉴 -->
    <div class="sidebar">
    <h3>내 정보</h3>
    <ul>
        <li><a href="${pageContext.request.contextPath}/member/mypage.do">마이페이지</a></li>
        <li>
            <a href="#">신청 현황</a>
            <ul class="submenu">
                <li><a href="${pageContext.request.contextPath}/drive/list.do">시승 신청 현황</a></li>
                <li><a href="${pageContext.request.contextPath}/repair/list.do">정비 신청 현황</a></li>
            </ul>
        </li>
        <li><a href="${pageContext.request.contextPath}/board/myDiary.do">내가 쓴 글</a></li>
    </ul>
</div>

    <!-- 메인 내용 -->
    <div class="container">
        <h2>마이페이지</h2>

        <form method="post" action="${pageContext.request.contextPath}/member/updateInfo.do">
            <div class="form-group">
                <label>아이디</label>
                <input type="text" name="id" value="${Info.id}" readonly>
            </div>

            <div class="form-group">
                <label for="pwd">비밀번호</label>
                <input type="password" name="pwd" id="pwd" placeholder="변경할 비밀번호를 입력하세요">
            </div>

            <div class="form-group">
                <label>이름</label>
                <input type="text" name="name" value="${Info.name}" readonly>
            </div>

            <div class="form-group">
                <label for="nickname">닉네임</label>
                <input type="text" name="nickname" id="nickname" value="${Info.nickname}">
            </div>

            <div class="form-group">
                <label for="phone">전화번호</label>
                <input type="tel" name="phone" id="phone" value="${Info.phone}">
            </div>

            <div class="form-group">
                <label for="email">이메일</label>
                <input type="email" name="email" id="email" value="${Info.email}">
            </div>

            <div class="form-group">
                <label>등록일</label>
                <input type="text"
                    value="<fmt:formatDate value='${Info.regDt}' pattern='yyyy-MM-dd' />"
                    readonly>
            </div>

            <div class="form-buttons">
                <button type="submit">정보 수정</button>
                <button type="button"
                    onclick="location.href='${pageContext.request.contextPath}/main/main.do'">메인으로</button>
            </div>
        </form>
    </div>

</body>
</html>