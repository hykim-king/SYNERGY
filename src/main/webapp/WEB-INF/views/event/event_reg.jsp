<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate">
  <fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" />
</c:set>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>이벤트 등록</title>
  <link rel="stylesheet" href="${CP}/resources/assets/css/event.css?ver=${sysDate}" />
  <link rel="stylesheet" href="${CP}/resources/assets/css/eventform.css?ver=${sysDate}" />
</head>
<body>
  <div class="layout">
    <aside class="sidebar">
      <div class="logo">CarPick</div>
      <ul class="menu">
        <li><a href="${CP}/notice/list.do">공지사항</a></li>
        <li><a href="${CP}/board/doRetrieve.do">자유 게시판</a></li>
        <li><a href="#">구매 후기</a></li>
        <li><a href="${CP}/event/doRetrieve.do">이벤트</a></li>
      </ul>
    </aside>
    <main class="main-content">
      <h2 class="page-title">이벤트 등록</h2>
      <form name="eventForm" action="${CP}/event/doSave.do" method="post">
        <input type="hidden" name="regId" value="admin" />
        <input type="hidden" name="modId" value="admin" />

        <div class="form-group">
          <label for="email">이메일</label>
          <input type="email" id="email" name="email" placeholder="you@example.com" required />
        </div>
        <div class="form-group">
          <label for="title">제목</label>
          <input type="text" id="title" name="title" required />
        </div>
        <div class="form-group">
          <label for="contents">내용</label>
          <textarea id="contents" name="contents" rows="10" required></textarea>
        </div>

        <div class="form-actions">
          <button type="submit">등록</button>
          <button type="button" onclick="location.href='${CP}/event/doRetrieve.do'">목록</button>
        </div>
      </form>
    </main>
  </div>
</body>
</html>