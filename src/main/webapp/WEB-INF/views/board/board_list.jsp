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
    int pageSize = 0;
    int pageNo = 0;
    int maxNum = 0;

    String totalCntString = request.getAttribute("totalCnt").toString();
    maxNum = Integer.parseInt(totalCntString);

    SearchDTO paramVO = (SearchDTO) request.getAttribute("search");
    pageSize = paramVO.getPageSize();
    pageNo = paramVO.getPageNo();

    String url = request.getContextPath() + "/board/doRetrieve.do";
    String pageHtml = PcwkString.renderingPager(maxNum, pageNo, pageSize, bottomCount, url, "pagerDoRetrieve");
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>자유 게시판</title>
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
        document.userForm.pageNo.value = 1;
        document.userForm.submit();
      });

      $('#moveToReg').click(function () {
        if (confirm("등록 화면으로 이동하시겠습니까?")) {
          location.href = '${CP}/board/doSaveView.do?div=' + $('#div').val();
        }
      });
    });

    function pagerDoRetrieve(url, pageNo) {
      document.userForm.pageNo.value = pageNo;
      document.userForm.action = url;
      document.userForm.submit();
    }
  </script>
</head>
<body>

<div class="main-container" style="max-width: 1000px; margin: 0 auto;">
  <h2>자유 게시판</h2>

  <form name="userForm" method="get">
    <input type="hidden" name="pageNo" id="pageNo">
    <input type="hidden" name="div" id="div" value="<c:out value='${divValue}' default='10'/>">

    <div class="search-group">
      <select name="searchDiv" id="searchDiv">
        <option value="">검색 구분</option>
        <option value="10" <c:if test="${search.searchDiv eq '10'}">selected</c:if>>제목</option>
        <option value="20" <c:if test="${search.searchDiv eq '20'}">selected</c:if>>내용</option>
        <option value="30" <c:if test="${search.searchDiv eq '30'}">selected</c:if>>번호</option>
        <option value="40" <c:if test="${search.searchDiv eq '40'}">selected</c:if>>제목+내용</option>
      </select>
      <input type="text" name="searchWord" placeholder="검색어 입력" value="<c:out value='${search.searchWord}' />">
      <button type="button" id="doRetrieve">조회</button>
      <button type="button" id="moveToReg">글 작성하기</button>
    </div>
  </form>

  <table class="table" id="listTable">
    <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>작성일</th>
        <th>조회수</th>
      </tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${not empty list}">
          <c:forEach var="vo" items="${list}">
            <tr>
              <td><c:out value="${vo.no}" /></td>
              <td>
                <a href="${CP}/board/detail.do?boardCode=${vo.boardCode}">
                  <c:out value="${vo.title}" />
                </a>
              </td>
              <td><c:out value="${vo.nickname}" /></td>
              <td><fmt:formatDate value="${vo.modDt}" pattern="yyyy-MM-dd" /></td>
              <td><c:out value="${vo.readCnt}" /></td>
            </tr>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <tr>
            <td colspan="5">조회된 데이터가 없습니다.</td>
          </tr>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>

  <!-- ✅ 페이징 -->
  <div class="pagination">
    <%= pageHtml %>
  </div>
</div>

<%@ include file="/resource/footer.jsp" %>
</body>
</html>