<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>관리자 게시판 목록</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/board.css'/>">
    <style>
        .container {
            max-width: 1200px;
            margin: 60px auto;
            padding: 20px;
        }

        h2 {
            font-size: 1.8rem;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f1f1f1;
        }

        .btn {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .btn-danger {
            background-color: #dc3545;
            color: white;
        }

        .btn-edit {
            background-color: #007bff;
            color: white;
        }

        .search-group {
            float: right;
            margin-bottom: 15px;
        }

        .pagination {
            margin-top: 20px;
            text-align: center;
        }

        .pagination .btn {
            margin: 0 3px;
        }
    </style>
</head>
<body>

<!-- 상단 공통 헤더 -->
<jsp:include page="/resource/adminHeader.jsp" />

<div class="container">
    <h2>관리자 게시판 목록</h2>

    <form id="deleteForm" method="post">
        <button type="button" class="btn btn-danger" onclick="deleteSelected()">선택 삭제</button>

        <div class="search-group">
            <form action="<c:url value='/admin/boardList.do'/>" method="get">
                <select name="searchDiv">
                    <option value="" <c:if test="${empty search.searchDiv}">selected</c:if>>--선택--</option>
                    <option value="10" <c:if test="${search.searchDiv == '10'}">selected</c:if>>제목</option>
                    <option value="20" <c:if test="${search.searchDiv == '20'}">selected</c:if>>작성자</option>
                </select>
                <input type="text" name="searchWord" value="${search.searchWord}" placeholder="검색어 입력" />
                <button type="submit" class="btn">검색</button>
            </form>
        </div>

        <table>
            <thead>
                <tr>
                    <th><input type="checkbox" id="checkAll" onclick="toggleAll(this)"></th>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>등록일</th>
                    <th>수정</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty list}">
                        <c:forEach var="board" items="${list}">
                            <tr>
                                <td><input type="checkbox" name="boardCodes" value="${board.boardCode}" /></td>
                                <td>${board.boardCode}</td>
                                <td>${fn:escapeXml(board.title)}</td>
                                <td>${fn:escapeXml(board.nickname)}</td>
                                <td>${board.readCnt}</td>
                                <td><fmt:formatDate value="${board.regDt}" pattern="yyyy-MM-dd" /></td>
                                <td>
                                    <a href="<c:url value='/admin/boardMod.do?boardCode=${board.boardCode}'/>" class="btn btn-edit">수정</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="7">등록된 게시글이 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </form>

    <!-- 페이징 처리 -->
    <div class="pagination">
        <c:if test="${totalCnt > 0}">
            <c:set var="pageSize" value="${search.pageSize}" />
            <c:set var="totalPage" value="${(totalCnt + pageSize - 1) / pageSize}" />
            <c:set var="pageNo" value="${search.pageNo}" />
            
            <c:forEach begin="1" end="${totalPage}" var="page">
                <a href="<c:url value='/admin/boardList.do?pageNo=${page}&searchDiv=${search.searchDiv}&searchWord=${fn:escapeXml(search.searchWord)}'/>"
                   class="${page == pageNo ? 'btn btn-danger' : 'btn'}">${page}</a>
            </c:forEach>
        </c:if>
    </div>
</div>

<!-- 하단 공통 푸터 -->
<jsp:include page="/resource/footer.jsp" />

<script>
function toggleAll(source) {
    const checkboxes = document.getElementsByName('boardCodes');
    for (let checkbox of checkboxes) {
        checkbox.checked = source.checked;
    }
}

function deleteSelected() {
    const checkboxes = document.querySelectorAll('input[name="boardCodes"]:checked');
    if (checkboxes.length === 0) {
        alert('삭제할 게시글을 선택하세요.');
        return;
    }

    const codes = Array.from(checkboxes).map(cb => cb.value);

    fetch('<c:url value="/admin/deleteBoards.do"/>', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(codes)
    })
    .then(response => response.json())
    .then(data => {
        alert(data.msg);
        if (data.msg.includes("정상")) {
            location.reload();
        }
    });
}
</script>

</body>
</html>