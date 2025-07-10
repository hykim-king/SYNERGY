<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>

<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set>
<c:set var="isEditable" value="${sessionScope.loginUser.id eq vo.regId or sessionScope.loginUser.id eq 'admin'}" />
<c:if test="${empty sessionScope.loginUser}">
  <c:redirect url='${CP}/member/loginView.do' />
</c:if>

<c:if test="${sessionScope.loginUser.id ne 'admin' and sessionScope.loginUser.id ne vo.regId}">
  <script>
    alert("본인 또는 관리자만 수정 가능합니다.");
    location.href = '${CP}/event/doRetrieve.do';
  </script>
</c:if>



<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>이벤트 수정</title>
  <link rel="stylesheet" href="${CP}/resource/css/boardform1.css?date=${sysDate}">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <script>
    $(document).ready(function () {
      $('#doUpdate').click(function () {
        if (!confirm('수정하시겠습니까?')) return;

        const title = $('#title').val().trim();
        const contents = $('#contents').val().trim();

        if (title === '') {
          alert('제목을 입력하세요.');
          $('#title').focus();
          return;
        }
        if (contents === '') {
          alert('내용을 입력하세요.');
          $('#contents').focus();
          return;
        }

        $.post('${CP}/event/doUpdate.do', $('#modForm').serialize(), function (resp) {
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.flag === 1) {
            window.location.href = '${CP}/event/doRetrieve.do';
          }
        });
      });

      $('#doDelete').click(function () {
        if (!confirm('삭제하시겠습니까?')) return;

        $.post('${CP}/event/doDelete.do', { ecode: $('#ecode').val() }, function (resp) {
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.flag === 1) {
            window.location.href = '${CP}/event/doRetrieve.do';
          }
        });
      });

      $('#moveToList').click(function () {
        window.location.href = '${CP}/event/doRetrieve.do';
      });
    });
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

    .form-group input[type="text"],
    .form-group textarea {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    .form-group textarea {
      height: 150px;
      resize: vertical;
    }

    .button-area {
      text-align: right;
    }

    .button-area input[type="button"] {
      padding: 8px 20px;
      margin-left: 10px;
      background-color: #004080;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }

    .button-area input[type="button"]:hover {
      background-color: #0066cc;
    }

    footer {
      margin-top: 60px;
      background-color: #f4f4f4;
      padding: 20px 10px;
      text-align: center;
      font-size: 14px;
      color: #555;
      border-top: 1px solid #ccc;
    }
  </style>
</head>
<body>

<div class="form-container">
  <h2>이벤트 수정</h2>
  <form id="modForm" method="post">
    <input type="hidden" name="ecode" id="ecode" value="${vo.ecode}" />
    <input type="hidden" name="modId" value="${sessionScope.loginUser.id}" />
    <input type="hidden" name="email" value="${vo.email}" />
    <input type="hidden" name="nickname" value="${vo.nickname}" />

    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" name="title" id="title" value="${vo.title}" maxlength="200"
             <c:if test="${!isEditable}">readonly</c:if> />
    </div>

    <div class="form-group">
      <label for="email">이메일</label>
      <input type="text" id="email_display" value="${vo.email}" readonly />
    </div>

    <div class="form-group">
      <label for="nickname">작성자</label>
      <input type="text" id="nickname_display" value="${vo.nickname}" readonly />
    </div>

    <div class="form-group">
      <label for="regDt">등록일</label>
      <input type="text" id="regDt" value="<fmt:formatDate value='${vo.regDt}' pattern='yyyy-MM-dd' />" readonly />
    </div>

    <div class="form-group">
      <label for="readCnt">조회수</label>
      <input type="text" id="readCnt" value="${vo.readCnt}" readonly />
    </div>

    <div class="form-group">
      <label for="contents">내용</label>
      <textarea id="contents" name="contents" <c:if test="${!isEditable}">readonly</c:if>>${vo.contents}</textarea>
    </div>

    <div class="button-area">
      <c:if test="${isEditable}">
        <input type="button" id="doUpdate" value="수정">
      </c:if>
      <input type="button" id="doDelete" value="삭제">
      <input type="button" id="moveToList" value="목록">
    </div>
  </form>
</div>

<!-- ✅ FOOTER -->
<footer>
  ⓒ 2025 TEAM SYNERGY, CarPick Project.<br>
  본 서비스는 교육 목적으로 제작되었습니다.<br>
  홍대 에이콘 아카데미 | 서울특별시 마포구 양화로 122, 3층 · 4층
</footer>

</body>
</html>