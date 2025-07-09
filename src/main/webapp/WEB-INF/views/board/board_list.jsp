<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.pcwk.ehr.cmn.PcwkString"%>
<%@ page import="com.pcwk.ehr.cmn.SearchDTO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="CP" value="${pageContext.request.contextPath}" />
<c:set var="now" value="<%=new Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss"/></c:set>

<%
    int bottomCount = 10;
    int pageSize = 0;
    int pageNo = 0;
    int maxNum = 0;

    String url = "";
    String scriptName = "";

    String totalCntString = request.getAttribute("totalCnt").toString();
    maxNum = Integer.parseInt(totalCntString);

    SearchDTO paramVO = (SearchDTO) request.getAttribute("search");
    pageSize = paramVO.getPageSize();
    pageNo = paramVO.getPageNo();

    String cp = request.getContextPath();
    url = cp + "/board/doRetrieve.do";
    scriptName = "pagerDoRetrieve";
    String pageHtml = PcwkString.renderingPager(maxNum, pageNo, pageSize, bottomCount, url, scriptName);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>자유 게시판</title>
<link rel="stylesheet" href="${CP}/resource/SNERGY/css/board.css?date=${sysDate}">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    document.querySelector("#doRetrieve").addEventListener("click", function() {
        document.userForm.pageNo.value = 1;
        document.userForm.submit();
    });

    document.querySelector("#moveToReg").addEventListener("click", function() {
        if(confirm("등록 화면으로 이동하시겠습니까?")) {
            location.href = "${CP}/board/doSaveView.do?div=" + document.querySelector("#div").value;
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

<!-- 사이드바 -->
<div class="sidebar">
    <div class="logo">CarPick</div>
    <ul class="menu">
        <li><a href="${CP}/main/main.do">메인홈페이지</a></li>
        <li><a href="${CP}/retailer/list.do">리테일러 찾기</a></li>
        <li><a href="${CP}/drive/form.do">시승 신청</a></li>
        <li><a href="${CP}/repair/form.do">정비 신청</a></li>
        <li><a href="${CP}/board/doRetrieve.do">자유 게시판</a></li>
        <li><a href="${CP}/event/doRetrieve.do">이벤트</a></li>
    </ul>
</div>

<!-- 메인 콘텐츠 -->
<div class="main-container">
    <div class="top-nav">
        <a href="#">전체모델</a>&nbsp;|&nbsp;
        <a href="#">리테일러 찾기</a>&nbsp;|&nbsp;
        <a href="#">마이페이지</a>&nbsp;|&nbsp;
        <a href="#">나만의 차량 만들기</a>&nbsp;|&nbsp;
        <a href="#">Logout</a>
    </div>

    <h2>자유 게시판</h2>
    <div class="banner">
        <img src="${CP}/resource/SNERGY/Board_image/board_cover.png" alt="배너 이미지">
    </div>

    <form name="userForm" method="get">
        <input type="hidden" name="pageNo" id="pageNo">
        <input type="hidden" name="div" id="div" value="<c:out value='${divValue}' />">

        <div class="search-group">
            <select name="searchDiv" id="searchDiv">
                <option value="">검색 구분</option>
                <option value="10" <c:if test="${search.searchDiv eq '10'}">selected</c:if>>제목</option>
                <option value="20" <c:if test="${search.searchDiv eq '20'}">selected</c:if>>SUV</option>
                <option value="30" <c:if test="${search.searchDiv eq '30'}">selected</c:if>>세단</option>
                <option value="40" <c:if test="${search.searchDiv eq '40'}">selected</c:if>>리테일러</option>
            </select>

            <input type="text" name="searchWord" placeholder="검색어 입력" value="${search.searchWord}">
            <button type="button" id="doRetrieve">조회</button>
            <button type="button" id="moveToReg">글 작성하기</button>
        </div>
    </form>

    <table class="table" id="listTable">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>별명</th>
                <th>작성일</th>
                <th>조회수</th>
            </tr>
        </thead>
        <tbody>
    <c:choose>
        <c:when test="${not empty list}">
            <c:forEach var="vo" items="${list}">
                <tr>
                    <td>${vo.no}</td>
                    <td>
                        <a href="${CP}/board/doSelectOne.do?div=${divValue}&boardCode=${vo.boardCode}&regId=${vo.regId}">
                            ${vo.title}
                        </a>
                    </td>
                    <td>${vo.nickname}</td>  <%-- ✅ 별명 추가 --%>
                    <td><fmt:formatDate value="${vo.regDt}" pattern="yyyy-MM-dd" /></td> <%-- ✅ 등록일 추가 --%>
                    <td>${vo.readCnt}</td>
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

    <div class="pagination">
        <%= pageHtml %>
    </div>
</div>
</body>
</html>