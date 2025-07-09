<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="now" value="<%=new Date()%>" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss" var="currentDate" />
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="${CP}/resource/css/SNERGY/css/event.css">
  <title>이벤트 등록</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="${CP}/resource/js/board.js"></script>
  <script>
    document.addEventListener('DOMContentLoaded', function () {
      const titleInput = document.querySelector("#title");
      const regIdInput = document.querySelector("#regId");
      const contentsTextarea = document.querySelector("#contents");
      const emailInput = document.querySelector("#email");
      const doSaveButton = document.querySelector("#doSave");
      const moveToListButton = document.querySelector("#moveToList");

      moveToListButton.addEventListener("click", function () {
        if (confirm('목록으로 이동 하시겠습니까?') === false) return;
        window.location.href = '/ehr/event/doRetrieve.do';
      });

      doSaveButton.addEventListener("click", function () {
        if (isEmpty(titleInput.value)) {
          alert('제목을 입력 하세요');
          titleInput.focus();
          return;
        }
        if (isEmpty(regIdInput.value)) {
          alert('등록자 ID를 입력 하세요');
          regIdInput.focus();
          return;
        }
        if (isEmpty(emailInput.value)) {
          alert('이메일을 입력 하세요');
          emailInput.focus();
          return;
        }
        if (isEmpty(contentsTextarea.value)) {
          alert('내용을 입력 하세요');
          contentsTextarea.focus();
          return;
        }

        if (confirm('이벤트를 등록하시겠습니까?') === false) return;

        $.ajax({
          type: "POST",
          url: "/ehr/event/doSave.do",
          async: true,
          dataType: "html",
          data: {
            title: titleInput.value,
            regId: regIdInput.value,
            email: emailInput.value,
            contents: contentsTextarea.value
          },
          success: function (response) {
            const message = JSON.parse(response);
            alert(message.message);
            if (message.messageId === 1) {
              window.location.href = '/ehr/event/doRetrieve.do';
            }
          },
          error: function (response) {
            console.log("error:", response);
          }
        });
      });
    });
  </script>
</head>
<body>
  <div class="form-container">
    <h2>이벤트 - 등록</h2>
    <hr class="title-underline">

    <div class="button-area">
      <input type="button" id="doSave" value="등록">
      <input type="button" id="moveToList" value="목록">
    </div>

    <form>
      <div class="form-group">
        <label for="title">제목</label>
        <input type="text" name="title" id="title" maxlength="200" required placeholder="제목">
      </div>

      <div class="form-group">
        <label for="regId">등록자 ID</label>
        <input type="text" name="regId" id="regId" maxlength="20" required placeholder="등록자 ID">
      </div>

      <div class="form-group">
        <label for="email">이메일</label>
        <input type="email" name="email" id="email" maxlength="50" required placeholder="you@example.com">
      </div>

      <div class="form-group">
        <label for="regDt">작성일</label>
        <input type="text" id="regDt" name="regDt" value="${currentDate}" disabled>
      </div>

      <div class="form-group">
        <label for="contents">내용</label>
        <textarea id="contents" name="contents" class="contents" placeholder="내용을 입력하세요."></textarea>
      </div>
    </form>
  </div>
</body>
</html>