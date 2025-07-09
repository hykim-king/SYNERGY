<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>
<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate">
    <fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" />
</c:set>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판</title>
<link rel="stylesheet" href="${CP}/resources/assets/css/board.css?date=${sysDate}">
<link rel="stylesheet" href="${CP}/resources/assets/css/boardform.css?ver=${sysDate}">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${CP}/resources/assets/js/common.js"></script>
</head>
<body>
<div class="form-container">
  <c:choose>
    <c:when test="${20 == divValue}"><h2>자유 게시판 - 관리</h2></c:when>
    <c:otherwise><h2>공지사항 - 관리</h2></c:otherwise>
  </c:choose>
  <hr class="title-underline">
  <div class="button-area">
    <input type="button" id="moveToList" value="목록">
    <input type="button" id="doUpdate" value="수정">
    <input type="button" id="doDelete" value="삭제">
  </div>
  <form>
    <input type="hidden" name="seq" id="seq" value="<c:out value='${vo.seq}'/>">
    <input type="hidden" name="div" id="div" value='<c:out value="${divValue}" />'>
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
      <input type="text" id="regDt" value="<fmt:formatDate value='${vo.modDt}' pattern='yyyy-MM-dd'/>" disabled />
    </div>
    <div class="form-group">
      <label for="regId">등록자</label>
      <input type="text" id="regId" value="${sessionScope.user.userId}" disabled />
    </div>
    <div class="form-group">
      <label for="contents">내용</label>
      <textarea class="contents" id="contents" name="contents" placeholder="내용을 입력하세요">${vo.contents}</textarea>
    </div>
  </form>
</div>
<script>
document.addEventListener('DOMContentLoaded', function() {
  const seqInput = document.querySelector("#seq");
  const titleInput = document.querySelector("#title");
  const contentsTextarea = document.querySelector("#contents");
  const moveToListButton = document.querySelector("#moveToList");
  const doUpdateButton = document.querySelector("#doUpdate");
  const doDeleteButton = document.querySelector("#doDelete");

  moveToListButton.addEventListener('click', function() {
    if(confirm('목록으로 이동 하시겠습니까?') === false) return;
    window.location.href = '${CP}/board/doRetrieve.do?div=' + ${divValue};
  });

  doUpdateButton.addEventListener('click', function() {
    if(titleInput.value.trim() === '') { alert('제목을 입력 하세요'); titleInput.focus(); return; }
    if(contentsTextarea.value.trim() === '') { alert('내용을 입력 하세요'); contentsTextarea.focus(); return; }
    if(confirm('게시글을 수정 하시겠습니까?') === false) return;

    $.ajax({
      type: "POST",
      url: "${CP}/board/doUpdate.do",
      dataType: "html",
      data: {
        "seq": seqInput.value,
        "title": titleInput.value,
        "contents": contentsTextarea.value,
        "div": '${divValue}',
        "modId": '${vo.modId}'
      },
      success: function(response) {
        const message = JSON.parse(response);
        alert(message.message);
        if(message.messageId === 1) {
          window.location.href = '${CP}/board/doRetrieve.do?div=' + ${divValue};
        }
      },
      error: function(response) {
        console.log("error:" + response);
      }
    });
  });

  doDeleteButton.addEventListener('click', function() {
    if(seqInput.value.trim() === '') { alert("SEQ를 확인 하세요."); return; }
    if(confirm('게시글을 삭제 하시겠습니까?') === false) return;

    $.ajax({
      type: "POST",
      url: "${CP}/board/doDelete.do",
      dataType: "html",
      data: { "seq": seqInput.value },
      success: function(response) {
        const message = JSON.parse(response);
        alert(message.message);
        if(message.messageId === 1) {
          window.location.href = '${CP}/board/doRetrieve.do?div=' + ${divValue};
        }
      },
      error: function(response) {
        console.log("error:" + response);
      }
    });
  });
});
</script>
</body>
</html>