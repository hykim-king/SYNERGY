<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>

<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate">
  <fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" />
</c:set>
<!-- 수정 권한 여부: 본인 또는 admin -->
<c:set var="isEditable" value="${sessionScope.loginUser.id eq vo.regId or sessionScope.loginUser.id eq 'admin'}" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시글 수정</title>
  <link rel="stylesheet" href="${CP}/resource/css/boardform1.css?date=${sysDate}">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

  <script>
    const isLoggedIn = ${not empty sessionScope.loginUser ? 'true' : 'false'};

    function handleProtectedLink(event, url) {
      if (!isLoggedIn) {
        event.preventDefault();
        alert("로그인이 필요합니다.");
      } else {
        window.location.href = url;
      }
    }

    $(document).ready(function () {
      $('#doUpdate').click(function () {
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

        if (!confirm('수정하시겠습니까?')) return;

        $.post('${CP}/board/doUpdate.do', $('#modForm').serialize(), function (resp) {
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.messageId === 1) {
            window.location.href = '${CP}/board/doRetrieve.do?div=${divValue}';
          }
        });
      });

      $('#doDelete').click(function () {
        if (!confirm('삭제하시겠습니까?')) return;

        $.post('${CP}/board/doDelete.do', { boardCode: $('#boardCode').val() }, function (resp) {
          const msg = JSON.parse(resp);
          alert(msg.message);
          if (msg.messageId === 1) {
            window.location.href = '${CP}/board/doRetrieve.do?div=${divValue}';
          }
        });
      });

      $('#moveToList').click(function () {
        window.location.href = '${CP}/board/doRetrieve.do?div=${divValue}';
      });
    });
  </script>

  <style>
    body {
      margin: 0;
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    .header-bar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background-color: #00264d;
      color: white;
      padding: 10px 20px;
    }

    .header-nav a {
      color: white;
      text-decoration: none;
      margin: 0 10px;
    }

    .header-right a {
      color: white;
      margin-left: 10px;
    }

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
  <div class="header-bar">
    <div class="header-nav">
      <a href="${CP}/main/main.do"><img src="${CP}/image/carpick.png" alt="CARPICK" style="height: 50px;"></a>
      <a href="${CP}/car/list.do">차량 전체 모델</a>
      <a href="${CP}/retailer/all.do">리테일러 찾기</a>
      <a href="#" onclick="handleProtectedLink(event, '${CP}/drive/form.do')">시승 신청</a>
      <a href="#" onclick="handleProtectedLink(event, '${CP}/repair/form.do')">정비 신청</a>
      <a href="${CP}/board/doRetrieve.do">자유게시판</a>
      <a href="${CP}/event/doRetrieve.do">이벤트</a>
    </div>
    <div class="header-right">
      <c:choose>
        <c:when test="${not empty sessionScope.loginUser}">
          <span>👤</span>
          <a href="${CP}/member/mypage.do">${sessionScope.loginUser.nickname}님</a>
          <a href="${CP}/member/logout.do">로그아웃</a>
        </c:when>
        <c:otherwise>
          <span>🔒</span>
          <a href="${CP}/member/loginView.do">로그인</a>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</header>

<!-- ✅ 게시글 수정 폼 -->
<div class="form-container">
  <h2>게시글 수정</h2>
  <hr class="title-underline">

  <form id="modForm" method="post" autocomplete="off">
    <input type="hidden" name="boardCode" id="boardCode" value="${vo.boardCode}" />
    <input type="hidden" name="div" id="div" value="${divValue}" />
    <input type="hidden" name="modId" value="${sessionScope.loginUser.id}" />

    <div class="form-group">
      <label for="title">제목</label>
      <input type="text" name="title" id="title" value="${vo.title}" maxlength="200"
             <c:if test="${!isEditable}">readonly</c:if> />
    </div>

    <div class="form-group">
      <label for="readCnt">조회수</label>
      <input type="text" id="readCnt" value="${vo.readCnt}" readonly />
    </div>

    <div class="form-group">
      <label for="regDt">등록일</label>
      <input type="text" id="regDt" value="<fmt:formatDate value='${vo.regDt}' pattern='yyyy-MM-dd' />" readonly />
    </div>

    <div class="form-group">
      <label for="regId">작성자</label>
      <input type="text" id="regId" value="${vo.regId}" readonly />
    </div>

    <div class="form-group">
      <label for="contents">내용</label>
      <textarea id="contents" name="contents" <c:if test="${!isEditable}">readonly</c:if>>${vo.contents}</textarea>
    </div>

    <div class="button-area">
  <c:if test="${isEditable}">
    <input type="button" id="doUpdate" value="수정">
    <input type="button" id="doDelete" value="삭제">
  </c:if>
  <input type="button" id="moveToList" value="목록">
</div>

<!-- ✅ FOOTER -->
<footer>
  ⓒ 2025 TEAM SYNERGY, CarPick Project.<br>
  본 서비스는 교육 목적으로 제작되었습니다.<br>
  홍대 에이콘 아카데미 | 서울특별시 마포구 양화로 122, 3층 · 4층
</footer>

</body>
</html>