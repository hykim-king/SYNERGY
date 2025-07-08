<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
<link rel="stylesheet" href="${CP}/resources/assets/css/boardform.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${CP}/resources/assets/js/common.js"></script>
</head>
<body>
  <div class="form-container">
    <h2>게시글 등록</h2>
    <hr class="title-underline">
    <div class="button-area">
      <input type="button" id="doSave" value="등록">
      <input type="button" id="moveToList" value="목록">
    </div>
    <form id="regForm">
      <input type="hidden" name="div" id="div" value="10">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" name="title" id="title" maxlength="200" placeholder="제목">
      </div>
      <div class="form-group">
        <label for="regId">작성자</label>
        <input type="text" name="regId" id="regId" maxlength="20" placeholder="작성자">
      </div>
      <div class="form-group">
        <label for="contents">내용</label>
        <textarea id="contents" name="contents" class="contents" placeholder="내용을 입력하세요"></textarea>
      </div>
    </form>
  </div>
  <script>
    $(document).ready(function(){
      $('#doSave').click(function(){
        if ($('#title').val().trim() === '') {
          alert('제목을 입력하세요.'); $('#title').focus(); return;
        }
        if ($('#regId').val().trim() === '') {
          alert('작성자를 입력하세요.'); $('#regId').focus(); return;
        }
        if ($('#contents').val().trim() === '') {
          alert('내용을 입력하세요.'); $('#contents').focus(); return;
        }

        if (!confirm('등록하시겠습니까?')) return;

        $.post('${CP}/board/doSave.do', $('#regForm').serialize(), function(resp) {
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.messageId === 1) {
            window.location.href = '${CP}/board/doRetrieve.do?div=10';
          }
        });
      });

      $('#moveToList').click(function(){
        window.location.href = '${CP}/board/doRetrieve.do?div=10';
      });
    });
  </script>
</body>
</html>