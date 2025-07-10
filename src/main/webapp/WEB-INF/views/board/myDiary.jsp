<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>

<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>내가 쓴 글</title>
  <link rel="stylesheet" href="${CP}/resource/css/boardform1.css?date=${sysDate}">
  <link rel="stylesheet" href="${CP}/resource/css/board1.css?date=${sysDate}">
  <style>
    body {
      margin-bottom: 100px; /* 푸터와 겹치지 않도록 여백 확보 */
    }

    footer {
      position: fixed;
      bottom: 0;
      left: 0;
      width: 100%;
      background-color: #2c3e50;
      color: white;
      text-align: center;
      padding: 10px 0;
      font-family: Arial, sans-serif;
      box-sizing: border-box;
      z-index: 1000;
    }
  </style>
</head>
<body>

<header>
  <div class="header-bar" style="background: #00264d; color: white; padding: 10px 20px;">
    <a href="${CP}/main/main.do"><img src="${CP}/image/carpick.png" alt="CARPICK" style="height: 50px;"></a>
  </div>
</header>

<div class="main-container">
  <h2>내가 쓴 게시글</h2>

  <table class="table">
    <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성일</th>
        <th>조회수</th>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${not empty list}">
          <c:forEach var="vo" items="${list}">
            <tr>
              <td>${vo.boardCode}</td>
              <td>
                <a href="${CP}/board/doSelectOne.do?boardCode=${vo.boardCode}">
                  ${vo.title}
                </a>
              </td>
              <td><fmt:formatDate value="${vo.regDt}" pattern="yyyy-MM-dd" /></td>
              <td>${vo.readCnt}</td>
            </tr>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="4">작성한 게시글이 없습니다.</td>
          </tr>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>

  <div class="button-area" style="margin-top: 20px;">
    <button onclick="location.href='${CP}/board/doRetrieve.do'" style="padding: 8px 20px;">게시판 목록</button>
  </div>
</div>

<footer>
  ⓒ 2025 TEAM SYNERGY, CarPick Project.<br>
  본 서비스는 교육 목적으로 제작되었습니다.<br>
  홍대 에이콘 아카데미 | 서울특별시 마포구 양화로 122, 3층 · 4층
</footer>

</body>
</html>