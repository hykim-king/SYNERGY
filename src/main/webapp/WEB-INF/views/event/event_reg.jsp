<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>

<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set>

<!-- ✅ 로그인하지 않은 경우 로그인 페이지로 리디렉트 -->
<c:choose>
  <c:when test="${empty sessionScope.loginUser}">
    <c:redirect url="${CP}/member/loginView.do" />
  </c:when>
  <c:if test="${sessionScope.loginUser.id ne 'admin'}">
  <script>
    alert("관리자만 등록 가능합니다.");
    location.href = '${CP}/main/main.do';
  </script>
</c:if>
</c:choose>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>이벤트 등록</title>
  <link rel="stylesheet" href="${CP}/resource/css/boardform1.css?date=${sysDate}">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <script>
    $(document).ready(function () {
      // 등록
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
          try {
            const msg = JSON.parse(resp);
            alert(msg.message);
            if (msg.flag === 1 || msg.messageId === 1) {
              location.href = '${CP}/event/doRetrieve.do';
            }
          } catch (e) {
            alert("응답 처리 중 오류 발생: " + e);
          }
        });
      });

      // 목록 이동
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

<!-- ✅ HEADER -->
<header>
  <div style="display:flex; justify-content:space-between; align-items:center; background:#00264d; color:white; padding:10px 20px;">
    <div style="display:flex; gap:15px; align-items:center;">
      <a href="${CP}/main/main.do"><img src="${CP}/image/carpick.png" style="height:50px;" alt="CarPick"></a>
      <a href="${CP}/car/list.do" style="color:white;">차량 전체 모델</a>
      <a href="${CP}/retailer/all.do" style="color:white;">리테일러 찾기</a>
      <a href="#" onclick="alert('로그인이 필요합니다.');" style="color:white;">시승 신청</a>
      <a href="#" onclick="alert('로그인이 필요합니다.');" style="color:white;">정비 신청</a>
      <a href="${CP}/board/doRetrieve.do" style="color:white;">자유게시판</a>
      <a href="${CP}/event/doRetrieve.do" style="color:white;">이벤트</a>
    </div>
    <div>
      <span>👤</span>
      <span style="margin-left:5px;">${sessionScope.loginUser.nickname}님</span>
      <a href="${CP}/member/logout.do" style="color:white; margin-left:10px;">로그아웃</a>
    </div>
  </div>
</header>

<!-- ✅ 등록 폼 -->
<div class="form-container">
  <h2>이벤트 등록</h2>
  <hr class="title-underline" />

  <form id="regForm" method="post" autocomplete="off">
    <input type="hidden" name="regId" value="${sessionScope.loginUser.id}" />
    <input type="hidden" name="nickname" value="${sessionScope.loginUser.nickname}" />
    <input type="hidden" name="email" value="${sessionScope.loginUser.email}" />
    <input type="hidden" name="div" value="이벤트" />

    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" name="title" id="title" maxlength="200" />
    </div>

    <div class="form-group">
      <label for="contents">내용</label>
      <textarea name="contents" id="contents"></textarea>
    </div>

    <div class="button-area">
      <input type="button" id="doSave" value="등록" />
      <input type="button" id="moveToList" value="목록" />
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