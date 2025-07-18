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
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <script>
    function doUpdate() {
      const ecode = "${vo.ecode}";
      location.href = '${CP}/event/doSaveView.do?ecode=' + ecode;
    }

    function doDelete() {
      if (confirm("정말 삭제하시겠습니까?")) {
        $.post("${CP}/event/doDelete.do", { ecode: "${vo.ecode}", regId: "${vo.regId}" }, function (resp) {
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.flag === 1) {
            location.href = "${CP}/event/doRetrieve.do";
          }
        });
      }
    }
  </script>

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
    <p>${vo.title}</p>
  </div>

  <div class="form-group">
    <label>내용</label>
    <p style="white-space: pre-line;">${vo.contents}</p>
  </div>

  <div class="form-group">
    <label>작성자</label>
    <p>
      <c:out value="${empty vo.nickname ? vo.regId : vo.nickname}" />
    </p>
  </div>

  <div class="form-group">
    <label>등록일</label>
    <p><fmt:formatDate value="${vo.regDt}" pattern="yyyy-MM-dd" /></p>
  </div>

  <div class="button-area">
    <button onclick="location.href='${CP}/event/doRetrieve.do'">목록</button>

    <c:if test="${sessionScope.loginUser.id eq 'admin'}">
      <button onclick="doUpdate()">수정</button>
      <button onclick="doDelete()">삭제</button>
    </c:if>
  </div>
</div>

<%@ include file="/resource/footer.jsp" %>

</body>
</html>