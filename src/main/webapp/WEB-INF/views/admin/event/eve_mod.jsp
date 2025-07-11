<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="CP" value="${pageContext.request.contextPath}" />

<html>
<head>
  <title>이벤트 수정</title>
  <link rel="stylesheet" href="${CP}/resources/css/boardform.css" />
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

<jsp:include page="/resource/adminHeader.jsp" />

<div class="container">
  <h2>이벤트 수정</h2>

  <form action="${CP}/admin/event/update.do" method="post">
    <!-- 필수 hidden 필드 -->
    <input type="hidden" name="ecode" value="${event.ecode}" />
    <input type="hidden" name="regId" value="${fn:escapeXml(event.regId)}" />
    <input type="hidden" name="email" value="${fn:escapeXml(event.email)}" />
    <input type="hidden" name="div" value="이벤트" />

    <!-- 제목 -->
    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" name="title" id="title" value="${fn:escapeXml(event.title)}" required />
    </div>

    <!-- 작성자 -->
    <div class="form-group">
      <label for="nickname">작성자</label>
      <input type="text" name="nickname" id="nickname"
             value="${fn:escapeXml(event.nickname)}" readonly />
    </div>

    <!-- 내용 -->
    <div class="form-group">
      <label for="contents">내용</label>
      <textarea name="contents" id="contents" required>${fn:escapeXml(event.contents)}</textarea>
    </div>

    <!-- 버튼 영역 -->
    <div class="btn-group">
      <button type="submit" class="btn">수정 완료</button>
      <button type="button" class="btn btn-cancel"
              onclick="location.href='${CP}/admin/event/eve_list.do'">취소</button>
    </div>
  </form>
</div>

<jsp:include page="/resource/footer.jsp" />
</body>
</html>