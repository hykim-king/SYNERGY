<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>

<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set>

<%-- ✅ 1. 로그인하지 않은 사용자 차단 (로그인 페이지로 리디렉트) --%>
<c:if test="${empty sessionScope.loginUser}">
  <c:redirect url="${CP}/member/loginView.do" />
</c:if>

<%-- ✅ 2. 관리자 계정이 아닌 경우 접근 차단 (경고 후 메인으로 리디렉트) --%>
<c:if test="${sessionScope.loginUser.id ne 'admin'}">
  <script>
    alert("관리자만 등록 가능합니다.");
    location.href = '${CP}/main/main.do';
  </script>
</c:if>

<c:if test="${empty sessionScope.loginUser}">
  <c:redirect url="${CP}/member/loginView.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>이벤트 등록</title>
  <link rel="stylesheet" href="${CP}/resource/css/boardform1.css?date=${sysDate}">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <script>
    $(document).ready(function () {
      $('#doSave').click(function () {
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

        if (!confirm('이벤트를 등록하시겠습니까?')) return;

        $.post('${CP}/event/doSave.do', $('#regForm').serialize(), function (resp) {
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.messageId === 1 || msg.flag === 1) {
            location.href = '${CP}/event/doRetrieve.do';
          }
        });
      });

      $('#moveToList').click(function () {
        location.href = '${CP}/event/doRetrieve.do';
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
<c:if test="${sessionScope.loginUser.id ne 'admin'}">
  <script>
    alert("관리자만 등록 가능합니다.");
    history.back();
  </script>
</c:if>
<div class="form-container">
  <h2>이벤트 등록</h2>
  <hr class="title-underline">

  <form id="regForm" method="post">
    <input type="hidden" name="regId" value="${sessionScope.loginUser.id}" />
    <input type="hidden" name="nickname" value="${sessionScope.loginUser.nickname}" />
    <input type="hidden" name="email" value="${sessionScope.loginUser.email}" />

    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" name="title" id="title" maxlength="200" />
    </div>

    <div class="form-group">
      <label for="contents">내용</label>
      <textarea name="contents" id="contents"></textarea>
    </div>

    <div class="form-group">
      <label for="div">분류</label>
      <input type="text" name="div" id="div" value="이벤트" readonly />
    </div>

    <div class="button-area">
      <input type="button" id="doSave" value="등록" />
      <input type="button" id="moveToList" value="목록" />
    </div>
  </form>
</div>

<footer>
  ⓒ 2025 TEAM SYNERGY, CarPick Project.<br>
  본 서비스는 교육 목적으로 제작되었습니다.<br>
  홍대 에이콘 아카데미 | 서울특별시 마포구 양화로 122, 3층 · 4층
</footer>

</body>
</html>