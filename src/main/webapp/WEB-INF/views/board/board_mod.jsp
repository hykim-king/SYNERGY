<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<link rel="stylesheet" href="${CP}/resources/assets/css/boardform.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${CP}/resources/assets/js/common.js"></script>
</head>
<body>
  <div class="form-container">
    <h2>게시글 수정</h2>
    <hr class="title-underline">
    <div class="button-area">
      <input type="button" id="doUpdate" value="수정">
      <input type="button" id="doDelete" value="삭제">
      <input type="button" id="moveToList" value="목록">
    </div>
    <form id="modForm">
      <input type="hidden" name="seq" id="seq" value="${vo.seq}" />
      <input type="hidden" name="div" id="div" value="${divValue}" />
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" name="title" id="title" value="${vo.title}" />
      </div>
      <div class="form-group">
        <label for="readCnt">조회수</label>
        <input type="text" id="readCnt" value="${vo.readCnt}" disabled />
      </div>
      <div class="form-group">
        <label for="regDt">등록일</label>
        <input type="text" id="regDt" value="<fmt:formatDate value='${vo.regDt}' pattern='yyyy-MM-dd'/>" disabled />
      </div>
      <div class="form-group">
        <label for="regId">작성자</label>
        <input type="text" id="regId" value="${vo.regId}" disabled />
      </div>
      <div class="form-group">
        <label for="contents">내용</label>
        <textarea id="contents" name="contents">${vo.contents}</textarea>
      </div>
    </form>
  </div>
  <script>
    $(document).ready(function(){
      $('#doUpdate').click(function(){
        if ($('#title').val().trim() === '') { alert('제목을 입력하세요'); return; }
        if ($('#contents').val().trim() === '') { alert('내용을 입력하세요'); return; }

        if (!confirm('수정하시겠습니까?')) return;

        $.post('${CP}/board/doUpdate.do', $('#modForm').serialize(), function(resp){
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.messageId === 1) {
            location.href = '${CP}/board/doRetrieve.do?div=${divValue}';
          }
        });
      });

      $('#doDelete').click(function(){
        if (!confirm('삭제하시겠습니까?')) return;

        $.post('${CP}/board/doDelete.do', { seq: $('#seq').val() }, function(resp){
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.messageId === 1) {
            location.href = '${CP}/board/doRetrieve.do?div=${divValue}';
          }
        });
      });

      $('#moveToList').click(function(){
        location.href = '${CP}/board/doRetrieve.do?div=${divValue}';
      });
    });
  </script>
</body>
</html>