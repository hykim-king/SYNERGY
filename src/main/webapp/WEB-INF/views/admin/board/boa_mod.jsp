<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="CP" value="${pageContext.request.contextPath}" />

<html>
<head>
  <title>자유게시판 수정</title>
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

    .form-group input[type="text"],
    .form-group textarea {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      font-size: 1rem;
      box-sizing: border-box;
    }

    .form-group textarea {
      height: 180px;
      resize: vertical;
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
      font-size: 1rem;
    }

    .button-area button:hover {
      background-color: #0066cc;
    }
  </style>
</head>
<body>

<jsp:include page="/resource/adminHeader.jsp" />

<div class="form-container">
  <h2>자유게시판 수정</h2>

  <form action="${CP}/admin/board/update.do" method="post">
    <!-- Hidden 필드 -->
    <input type="hidden" name="boardCode" value="${board.boardCode}" />
    <input type="hidden" name="id" value="${board.id}" />
    <input type="hidden" name="regId" value="${board.regId}" />
    <input type="hidden" name="modId"
           value="${sessionScope.loginUser != null ? sessionScope.loginUser.id : 'admin'}" />
    <input type="hidden" name="div" value="10" />

    <!-- 제목 -->
    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" name="title" id="title"
             value="${fn:escapeXml(board.title)}" required />
    </div>

    <!-- 작성자 -->
    <div class="form-group">
      <label for="nickname">작성자</label>
      <input type="text" name="nickname" id="nickname"
             value="${fn:escapeXml(board.nickname)}" readonly />
    </div>

    <!-- 내용 -->
    <div class="form-group">
      <label for="contents">내용</label>
      <textarea name="contents" id="contents" required><c:out value="${board.contents}" /></textarea>
    </div>

    <!-- 버튼 -->
    <div class="button-area">
      <button type="submit">수정 완료</button>
      <button type="button" onclick="location.href='${CP}/admin/board/boa_list.do'">취소</button>
    </div>
  </form>
</div>

<jsp:include page="/resource/footer.jsp" />

</body>
</html>