<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.pcwk.ehr.cmn.PcwkString"%>
<%@ page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/resource/header.jsp" %>


<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss"/></c:set>

<%
    int bottomCount = 10;
    int pageSize = 10;
    int pageNo = 1;
    int maxNum = 0;

    Object totalCntObj = request.getAttribute("totalCnt");
    if (totalCntObj != null) {
        maxNum = Integer.parseInt(totalCntObj.toString());
    }

    SearchDTO paramVO = (SearchDTO) request.getAttribute("search");
    if (paramVO != null) {
        pageSize = paramVO.getPageSize();
        pageNo = paramVO.getPageNo();
    }

    String url = request.getContextPath() + "/event/doRetrieve.do";
    String pageHtml = PcwkString.renderingPager(maxNum, pageNo, pageSize, bottomCount, url, "pagerDoRetrieve");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>이벤트 게시판</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
  <link rel="stylesheet" href="${CP}/resource/css/board1.css?date=${sysDate}">
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
      $('#doRetrieve').click(function () {
        document.eventForm.pageNo.value = 1;
        document.eventForm.submit();
      });

      $('#moveToReg').click(function () {
        if (confirm("이벤트를 등록하시겠습니까?")) {
          location.href = '${CP}/event/doSaveView.do';
        }
      });
    });

    function pagerDoRetrieve(url, pageNo) {
      document.eventForm.pageNo.value = pageNo;
      document.eventForm.action = url;
      document.eventForm.submit();
    }
  </script>

  <style>
    footer {
      background-color: #f4f4f4;
      text-align: center;
      padding: 20px 10px;
      font-size: 14px;
      color: #555;
      border-top: 1px solid #ccc;
      margin-top: 40px;
    }
  </style>
</head>
<body>
<div class="banner-event" style="text-align: center; margin: 10; padding: 0;">
  <img src="${pageContext.request.contextPath}/resource/banner/event_banner.png" 
       alt="이벤트배너" 
       style="width: 80%; max-width: 1000px; height: auto;  display: block; margin: 0 auto;" />
</div>

<div class="main-container" style="max-width: 1000px; margin: 0 auto;">
  <h2>이벤트 게시판</h2>

  <form name="eventForm" method="get">
  <input type="hidden" name="pageNo" id="pageNo">
  <div class="search-group">
    <select name="searchDiv" id="searchDiv">
      <option value="">검색 구분</option>
      <option value="10" <c:if test="${search.searchDiv eq '10'}">selected</c:if>>제목</option>
      <option value="20" <c:if test="${search.searchDiv eq '20'}">selected</c:if>>내용</option>
      <option value="30" <c:if test="${search.searchDiv eq '30'}">selected</c:if>>이벤트</option>
      <option value="40" <c:if test="${search.searchDiv eq '40'}">selected</c:if>>제목+내용</option>
    </select>
    <input type="text" name="searchWord" placeholder="검색어 입력" value="${search.searchWord}">
    <button type="button" id="doRetrieve">조회</button>
    
    <!-- ✅ 관리자만 등록 버튼 보이게 처리 -->
    <c:if test="${sessionScope.loginUser.id eq 'admin'}">
      <button type="button" id="moveToReg">이벤트 등록</button>
    </c:if>
  </div>
</form>

  <table class="table">
    <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>등록일</th>
        <th>조회수</th>
      </tr>
    </thead>
    <tbody>
  <c:choose>
    <c:when test="${not empty list}">
      <c:forEach var="vo" items="${list}" varStatus="status">
  <tr>
    <td>${(pageSize * (pageNo - 1)) + status.index + 1}</td>
    <td>
      <a href="${CP}/event/doSelectOne.do?ecode=${vo.ecode}">
        ${vo.title}
      </a>
    </td>
    <td>${vo.nickname}</td>
    <td><fmt:formatDate value="${vo.modDt}" pattern="yyyy-MM-dd" /></td>
    <td>${vo.readCnt}</td>
  </tr>
</c:forEach>
    </c:when>
    <c:otherwise>
      <tr>
        <td colspan="5">조회된 이벤트가 없습니다.</td>
      </tr>
    </c:otherwise>
  </c:choose>
</tbody>
  </table>

  <div class="pagination">
    <%= pageHtml %>
  </div>
</div>

    <%@ include file="/resource/footer.jsp" %>
</body>
</html>
