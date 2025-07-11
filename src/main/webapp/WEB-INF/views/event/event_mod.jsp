<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>

<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>이벤트 상세</title>
  <link rel="stylesheet" href="${CP}/resource/css/boardform1.css?date=${sysDate}">
  <style>
    .form-container {
      max-width: 800px;
      margin: 40px auto;
      padding: 20px;
      background-color: #f9f9f9;
      border: 1px solid #ddd;
      border-radius: 10px;
    }

    .form-group {
      margin-bottom: 20px;
    }

    .form-group label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }

    .form-group p {
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      background: #fff;
    }

    .button-area {
      text-align: center;
      margin-top: 20px;
    }

    .button-area button {
      padding: 8px 20px;
      background-color: #004080;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }

    .button-area button:hover {
      background-color: #0066cc;
    }

    footer {
      margin-top: 60px;
      background-color: #f4f4f4;
      padding: 20px 10px;
      text-align: center;
      font-size: 14px;
      color: #555;
      border-top: 1px solid #ccc;
    }
  </style>
</head>
<body>

<div class="form-container">
  <h2>이벤트 상세</h2>
  <hr>

  <div class="form-group">
    <label>제목</label>
    <p>${vo.title}</p>
  </div>

  <div class="form-group">
    <label>내용</label>
    <p style="white-space: pre-line;">${vo.contents}</p>
  </div>

  <div class="form-group">
    <label>작성자</label>
    <p>${vo.nickname}</p>
  </div>

  <div class="form-group">
    <label>등록일</label>
    <p><fmt:formatDate value="${vo.regDt}" pattern="yyyy-MM-dd" /></p>
  </div>

 <c:if test="${sessionScope.loginUser.id eq 'admin'}">
  <div class="button-area">
    <button onclick="location.href='${CP}/event/doRetrieve.do'">목록</button>
    <button onclick="doUpdate()">수정</button>
    <button onclick="doDelete()">삭제</button>
  </div>
</c:if>
<c:if test="${sessionScope.loginUser.id ne 'admin'}">
  <div class="button-area">
    <button onclick="location.href='${CP}/event/doRetrieve.do'">목록</button>
  </div>
</c:if>
</div>

<footer>
  ⓒ 2025 TEAM SYNERGY, CarPick Project.<br>
  본 서비스는 교육 목적으로 제작되었습니다.<br>
  홍대 에이콘 아카데미 | 서울특별시 마포구 양화로 122, 3층 · 4층
</footer>

</body>
</html>