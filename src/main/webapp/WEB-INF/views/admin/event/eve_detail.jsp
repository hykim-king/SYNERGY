<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="CP" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>이벤트 상세 보기</title>
  <link rel="stylesheet" href="${CP}/resource/css/boardform1.css">
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
      white-space: pre-line;
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
      margin: 0 5px;
    }

    .button-area button:hover {
      background-color: #0066cc;
    }
  </style>
</head>
<body>

<div class="form-container">
  <h2>이벤트 상세</h2>
  <hr>

  <div class="form-group">
    <label>제목</label>
    <p><c:out value="${vo.title}" /></p>
  </div>

  <div class="form-group">
    <label>내용</label>
    <p><c:out value="${vo.contents}" /></p>
  </div>

  <div class="form-group">
    <label>작성자</label>
    <p><c:out value="${vo.regId}" /> (<c:out value="${vo.nickname}" />)</p>
  </div>

  <div class="form-group">
    <label>등록일</label>
    <p><fmt:formatDate value="${vo.regDt}" pattern="yyyy-MM-dd" /></p>
  </div>

  <div class="form-group">
    <label>조회수</label>
    <p><c:out value="${vo.readCnt}" /></p>
  </div>

  <div class="button-area">
    <button onclick="location.href='${CP}/admin/event/eve_list.do'">목록</button>
    <c:if test="${sessionScope.loginUser.id eq 'admin'}">
      <button onclick="location.href='${CP}/admin/event/eve_mod.do?ecode=${vo.ecode}'">수정</button>
    </c:if>
  </div>
</div>

<%@ include file="/resource/footer.jsp" %>
</body>
</html>