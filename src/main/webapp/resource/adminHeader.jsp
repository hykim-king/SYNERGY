<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<style>
/* adminHeader 스타일 */
.admin-header header {
    background-color: #2c3e50;
    color: white;
    padding: 15px 20px;
    font-family: Arial, sans-serif;
}

.admin-header nav {
    background-color: #34495e;
    padding: 10px 20px;
    font-family: Arial, sans-serif;
}

.admin-header nav a {
    color: white;
    margin-right: 15px;
    text-decoration: none;
    font-weight: bold;
    cursor: pointer;
}

.admin-header nav a:hover {
    text-decoration: underline;
}

.admin-header .submenu {
    background-color: #ecf0f1;
    padding: 10px 20px;
}

.admin-header .submenu a {
    margin-right: 15px;
    color: #34495e;
    font-weight: normal;
    text-decoration: none;
}

.admin-header .submenu a:hover {
    text-decoration: underline;
}
</style>

<div class="admin-header">
<header>
    <h1 style="cursor:pointer; color: white;" onclick="location.href='${pageContext.request.contextPath}/admin/main.do'">관리자 페이지</h1>
</header>
<nav>
    <a href="javascript:void(0)" onclick="toggleSubMenu('memberSubmenu')">회원 관리</a>
    <a href="javascript:void(0)" onclick="toggleSubMenu('carSubmenu')">차량 관리</a>
    <a href="javascript:void(0)" onclick="toggleSubMenu('retailerSubmenu')">리테일러 관리</a>
    <a href="${pageContext.request.contextPath}/admin/drive/list.do">시승 신청 관리</a>
    <a href="${pageContext.request.contextPath}/admin/repair/list.do">정비 신청 관리</a>
    <a href="${pageContext.request.contextPath}/admin/board/boa_list.do">자유게시판</a>
    <a href="${pageContext.request.contextPath}/admin/event/eve_list.do">이벤트 관리</a>
    <a href="${pageContext.request.contextPath}/member/logout.do" style="float:right;">로그아웃</a>
</nav>

<div id="memberSubmenu" class="submenu" style="display:none;">
    <a href="${pageContext.request.contextPath}/admin/member/registerView.do">회원 등록</a>
    <a href="${pageContext.request.contextPath}/admin/member/list.do">회원 조회</a>
</div>

<div id="carSubmenu" class="submenu" style="display:none;">
    <a href="${pageContext.request.contextPath}/admin/car/add.do">차량 등록</a>
    <a href="${pageContext.request.contextPath}/admin/car/list.do">차량 목록</a>
</div>

<div id="retailerSubmenu" class="submenu" style="display:none;">
    <a href="${pageContext.request.contextPath}/admin/retailer/add.do">리테일러 등록</a>
    <a href="${pageContext.request.contextPath}/admin/retailer/list.do">리테일러 관리</a>
</div>
</div>

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