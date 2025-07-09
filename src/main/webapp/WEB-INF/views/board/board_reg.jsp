<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
<link rel="stylesheet" href="${CP}/resources/assets/css/boardform.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${CP}/resources/assets/js/common.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function(){
  const titleInput = document.querySelector("#title");
  const regIdInput = document.querySelector("#regId");
  const contentsTextarea = document.querySelector("#contents");
  const divInput = document.querySelector("#div");
  const doSaveButton = document.querySelector("#doSave");
  const moveToListButton = document.querySelector("#moveToList");

  moveToListButton.addEventListener("click", function(){
    if(confirm('목록으로 이동 하시겠습니까?') === false) return;
    window.location.href = '${CP}/board/doRetrieve.do?div=' + divInput.value;
  });

  doSaveButton.addEventListener("click", function(){
    if(isEmpty(titleInput.value)) {
      alert('제목을 입력 하세요');
      titleInput.focus();
      return;
    }
    if(isEmpty(regIdInput.value)) {
      alert('등록자를 입력 하세요');
      regIdInput.focus();
      return;
    }
    if(isEmpty(contentsTextarea.value)) {
      alert('내용을 입력 하세요');
      contentsTextarea.focus();
      return;
    }

    if(confirm('등록 하시겠습니까?') === false) return;

    $.ajax({
      type: "POST",
      url: "${CP}/board/doSave.do",
      async: true,
      dataType: "html",
      data: {
        "title": titleInput.value,
        "regId": regIdInput.value,
        "contents": contentsTextarea.value,
        "div": divInput.value
      },
      success: function(response){
        const message = JSON.parse(response);
        alert(message.message);
        if(1 === message.messageId){
          window.location.href = '${CP}/board/doRetrieve.do?div=' + divInput.value;
        }
      },
      error: function(response){
        console.log("error:" + response);
      }
    });
  });
});
</script>
</head>
<body>
  <div class="form-container">
    <c:choose>
      <c:when test="${'20' == board_div}"><h2>자유 게시판 - 등록</h2></c:when>
      <c:otherwise><h2>공지사항 - 등록</h2></c:otherwise>
    </c:choose>
    <hr class="title-underline">

    <div class="button-area">
      <input type="button" id="doSave" value="등록">
      <input type="button" id="moveToList" value="목록">
    </div>

    <form action="${CP}/board/doSave.do" method="post">
      <input type="hidden" name="div" id="div" value="${board_div}">
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" name="title" id="title" maxlength="200" required placeholder="제목">
      </div>
      <div class="form-group">
        <label for="regId">등록자</label>
        <input type="text" name="regId" id="regId" maxlength="20" required placeholder="등록자" value="${sessionScope.user.userId}" disabled>
      </div>
      <div class="form-group">
        <label for="contents">내용</label>
        <textarea id="contents" name="contents" placeholder="내용" class="contents"></textarea>
      </div>
    </form>
  </div>
</body>
</html>