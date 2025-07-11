<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="CP" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>자유게시판 상세</title>
  <link rel="stylesheet" href="${CP}/resources/css/boardform.css">
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
      background-color: #007bff;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      margin: 0 5px;
    }
    .button-area button:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>

<jsp:include page="/resource/header.jsp" />

<div class="form-container">
  <h2>게시글 상세</h2>
  <hr>

  <div class="form-group">
    <label>제목</label>
    <p><c:out value="${board.title}" /></p>
  </div>

  <div class="form-group">
    <label>내용</label>
    <p><c:out value="${board.contents}" /></p>
  </div>

  <div class="form-group">
    <label>작성자</label>
    <p><c:out value="${board.regId}" /> (<c:out value="${board.nickname}" />)</p>
  </div>

  <div class="form-group">
  <label>구분</label>
  <p><c:out value="${vo['div']}" /></p>
</div>

  <div class="form-group">
    <label>등록일</label>
    <p><fmt:formatDate value="${board.regDt}" pattern="yyyy-MM-dd" /></p>
  </div>

  <div class="form-group">
    <label>조회수</label>
    <p><c:out value="${board.readCnt}" /></p>
  </div>

  <div class="button-area">
    <button onclick="location.href='${CP}/board/doRetrieve.do'">목록</button>
    <c:if test="${sessionScope.loginUser != null && (sessionScope.loginUser.id eq board.regId || sessionScope.loginUser.id eq 'admin')}">
      <button onclick="location.href='${CP}/board/doUpdateForm.do?boardCode=${board.boardCode}'">수정</button>
    </c:if>
  </div>
</div>

<jsp:include page="/resource/footer.jsp" />
</body>
</html>