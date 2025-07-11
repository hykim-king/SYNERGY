<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
<head>
    <title>게시글 수정</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/boardform.css'/>">
    <style>
        .container {
            max-width: 800px;
            margin: 60px auto;
            padding: 20px;
            background-color: #f8f9fa;
            border: 1px solid #ddd;
            border-radius: 8px;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            font-size: 1.8rem;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            display: block;
            margin-bottom: 6px;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            font-size: 1rem;
            border: 1px solid #ccc;
            border-radius: 4px;
            resize: vertical;
            box-sizing: border-box;
        }

        input[readonly] {
            background-color: #e9ecef;
        }

        textarea {
            height: 200px;
        }

        .btn-group {
            text-align: center;
            margin-top: 30px;
        }

        .btn {
            padding: 10px 20px;
            background-color: #007bff;
            border: none;
            color: white;
            font-size: 1rem;
            border-radius: 4px;
            margin: 0 5px;
            cursor: pointer;
        }

        .btn-cancel {
            background-color: #6c757d;
        }

        .btn:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

<!-- 관리자 공통 헤더 -->
<jsp:include page="/resource/adminHeader.jsp" />

<div class="container">
    <h2>게시글 수정</h2>

    <form action="<c:url value='/admin/updateBoard.do'/>" method="post">
        <input type="hidden" name="boardCode" value="${board.boardCode}" />

        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" name="title" id="title" value="${fn:escapeXml(board.title)}" required />
        </div>

        <div class="form-group">
            <label for="nickname">작성자</label>
            <input type="text" name="nickname" id="nickname" value="${fn:escapeXml(board.nickname)}" readonly />
        </div>

        <div class="form-group">
            <label for="contents">내용</label>
            <textarea name="contents" id="contents" required>${fn:escapeXml(board.contents)}</textarea>
        </div>

        <div class="btn-group">
            <button type="submit" class="btn">수정 완료</button>
            <button type="button" class="btn btn-cancel" onclick="location.href='<c:url value='/admin/boardList.do'/>'">취소</button>
        </div>
    </form>
</div>

<!-- 관리자 공통 푸터 -->
<jsp:include page="/resource/footer.jsp" />

</body>
</html>