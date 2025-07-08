<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>

<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate">
	<fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" />
</c:set>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판</title>
<link rel="stylesheet"
	href="${CP}/resources/assets/css/board.css?date=${sysDate}">
<link rel="stylesheet"
	href="${CP}/resources/assets/css/boardform.css?ver=${sysDate}">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<div class="layout">
		<!-- 사이드바 -->
		<aside class="sidebar">
			<div class="logo">CarPick</div>
			<ul class="menu">
				<li><a href="${CP}/notice/list.do">공지사항</a></li>
				<li><a href="${CP}/board/doRetrieve.do">자유 게시판</a></li>
				<li><a href="#">구매 후기</a></li>
				<li><a href="#">이벤트</a></li>
			</ul>
		</aside>

		<!-- 메인 콘텐츠 -->
		<main class="main-content">
			<!-- 상단 메뉴 -->
			<header class="top-nav">
				<input type="text" class="search-box"
					placeholder="Search for models, etc.">
				<nav class="top-menu">
					<a href="#">전체모델</a> <a href="#">리테일러 찾기</a> <a href="#">마이페이지</a>
					<a href="#">나만의 차량 만들기</a> <a href="#">Logout</a>
				</nav>
			</header>

			<!-- 타이틀 -->
			<h2 class="page-title">자유 게시판</h2>

			<!-- 이미지 배너 -->
			<div class="banner">
				<img src="${CP}/resource/SNERGY/Board_image/board_cover.png"
					alt="게시판 표지 이미지">
			</div>

			<!-- 검색/필터 -->
			<section class="filter-section">
				<form name="searchForm" action="${CP}/board/doRetrieve.do"
					method="get">
					<select name="div">
						<option value="">차량 구분</option>
						<option value="10">SUV</option>
						<option value="20">세단</option>
					</select> <select name="searchDiv">
						<option value="">제목</option>
						<option value="10">내용</option>
						<option value="20">작성자</option>
					</select> <input type="text" name="searchWord" placeholder="검색어 입력">
					<button type="submit">🔍</button>
					<button type="button"
						onclick="location.href='${CP}/board/board_reg.jsp'">구매후기
						작성하기</button>
				</form>
			</section>

			<!-- 게시판 테이블 -->
			<table class="board-table">
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty list}">
							<c:forEach var="vo" items="${list}" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td><a
										href="${CP}/board/doSelectOne.do?boardCode=${vo.boardCode}&regId=${vo.regId}">
											${vo.title} </a></td>
									<td>${vo.nickname}</td>
									<td><fmt:formatDate value="${vo.regDt}"
											pattern="yyyy-MM-dd" /></td>
									<td>${vo.readCnt}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5" style="text-align: center;">조회된 게시글이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>

			<!-- 페이징 -->
			<div class="pagination">
				<c:forEach var="i" begin="1" end="${totalPages}">
					<a href="${CP}/board/doRetrieve.do?pageNo=${i}"
						class="${i == pageNo ? 'active' : ''}">${i}</a>
				</c:forEach>
			</div>
		</main>
	</div>
</body>
</html>