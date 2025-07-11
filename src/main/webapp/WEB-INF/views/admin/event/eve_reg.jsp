<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>이벤트 등록</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/boardform.css'/>" />
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

<jsp:include page="/resource/adminHeader.jsp" />

<div class="container">
    <h2>이벤트 등록</h2>

    <form id="regForm" action="<c:url value='/admin/event/save.do'/>" method="post">
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" required />
        </div>

        <div class="form-group">
            <label for="contents">내용</label>
            <textarea id="contents" name="contents" required></textarea>
        </div>

        <div class="form-group">
            <label for="nickname">작성자</label>
            <input type="text" id="nickname" name="nickname" value="${sessionScope.loginUser.nickname}" readonly />
        </div>

        <input type="hidden" name="regId" value="${sessionScope.loginUser.id}" />
        <input type="hidden" name="email" value="${sessionScope.loginUser.email}" />
        <input type="hidden" name="div" value="이벤트" />

        <div class="btn-group">
            <button type="submit" class="btn">등록</button>
            <button type="button" class="btn btn-cancel" onclick="location.href='<c:url value='/admin/event/eve_list.do'/>'">취소</button>
        </div>
    </form>
</div>

<jsp:include page="/resource/footer.jsp" />

</body>
</html>