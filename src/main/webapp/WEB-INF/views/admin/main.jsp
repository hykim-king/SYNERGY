<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>관리자 메인 페이지</title>
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
        nav {
            background-color: #34495e;
            padding: 10px 20px;
        }
        nav a {
            color: white;
            margin-right: 15px;
            text-decoration: none;
            font-weight: bold;
            cursor: pointer;
        }
        nav a:hover {
            text-decoration: underline;
        }
        .submenu {
            background-color: #ecf0f1;
            padding: 10px 20px;
            display: none;
        }
        .submenu a {
            margin-right: 15px;
            color: #34495e;
            font-weight: normal;
            text-decoration: none;
        }
        .submenu a:hover {
            text-decoration: underline;
        }
        main {
            padding: 20px;
        }
        .welcome {
            margin-bottom: 20px;
        }
        footer {
            background-color: #2c3e50;
            color: white;
            text-align: center;
            padding: 10px;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
    <script>
        function toggleSubMenu(id) {
            const submenus = document.querySelectorAll('.submenu');
            submenus.forEach(menu => {
                if(menu.id !== id){
                    menu.style.display = 'none';
                }
            });
            const target = document.getElementById(id);
            if(target){
                target.style.display = (target.style.display === 'block') ? 'none' : 'block';
            }
        }
    </script>
</head>
<body>
<header>
    <h1>관리자 페이지</h1>
</header>
<nav>
    <a href="javascript:void(0)" onclick="toggleSubMenu('memberSubmenu')">회원 관리</a>
    <a href="javascript:void(0)" onclick="toggleSubMenu('carSubmenu')">차량 관리</a>
    <a href="#">리테일러 정보 관리</a>
    <a href="#">시승 신청 관리</a>
    <a href="#">정비 신청 관리</a>
    <a href="#">게시판 관리</a>
    <a href="#">이벤트 관리</a>
    <a href="${pageContext.request.contextPath}/member/logout.do" style="float:right;">로그아웃</a>
</nav>

<!-- 회원 관리 서브 메뉴 -->
<div id="memberSubmenu" class="submenu">
    <a href="${pageContext.request.contextPath}/admin/member/registerView.do">회원 등록</a>
    <a href="${pageContext.request.contextPath}/admin/member/list.do">회원 조회</a>
</div>

<!-- 🚗 차량 관리 서브 메뉴 -->
<div id="carSubmenu" class="submenu">
    <a href="${pageContext.request.contextPath}/admin/car/add.do">차량 등록</a>
    <a href="${pageContext.request.contextPath}/admin/car/list.do">차량 목록</a>
</div>

<main>
    <div class="welcome">
        <h2>환영합니다, <c:out value="${loginUser.nickname}" /> 님!</h2>
        <p>관리자 페이지에 오신 것을 환영합니다.</p>
    </div>
    <p>여기서 회원 관리, 차량 정보 관리 등 여러 관리 작업을 수행할 수 있습니다.</p>
</main>

<footer>
    &copy; 2025 Your Company. All rights reserved.
</footer>
</body>
</html>