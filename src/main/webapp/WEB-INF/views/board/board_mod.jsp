<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath }" />
<c:set var="now" value="<%=new java.util.Date()%>" />
<c:set var="sysDate"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd_HH:mm:ss" /></c:set> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
<link rel="stylesheet" href="${CP}/resources/assets/css/form.css?ver=${sysDate}">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${CP}/resources/assets/js/common.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function(){
    const seqInput = document.querySelector("#seq");
    const titleInput = document.querySelector("#title");
    const contentsTextarea = document.querySelector("#contents");
    const moveToListButton = document.querySelector("#moveToList");
    const doUpdateButton = document.querySelector("#doUpdate");
    const doDeleteButton = document.querySelector("#doDelete");
    const divValue = document.querySelector("#div").value;
    const modIdValue = document.querySelector("#regId").value;

    moveToListButton.addEventListener('click', function(){
        if(confirm('목록으로 이동 하시겠습니까?') === false) return;
        window.location.href = '${CP}/board/doRetrieve.do?div=' + divValue;
    });

    doUpdateButton.addEventListener('click', function(){
        if(isEmpty(titleInput.value)) {
            alert('제목을 입력하세요.');
            titleInput.focus();
            return;
        }

        if(isEmpty(contentsTextarea.value)) {
            alert('내용을 입력하세요.');
            contentsTextarea.focus();
            return;
        }

        if(confirm('수정하시겠습니까?') === false) return;

        $.ajax({
            type: "POST",
            url: "${CP}/board/doUpdate.do",
            dataType: "html",
            data: {
                "seq": seqInput.value,
                "title": titleInput.value,
                "contents": contentsTextarea.value,
                "div": divValue,
                "modId": modIdValue
            },
            success: function(response) {
                const message = JSON.parse(response);
                alert(message.message);
                if (message.messageId === 1) {
                    window.location.href = '${CP}/board/doRetrieve.do?div=' + divValue;
                }
            },
            error: function(err) {
                console.error("Error:", err);
                alert("수정 중 오류가 발생했습니다.");
            }
        });
    });

    doDeleteButton.addEventListener('click', function(){
        if(confirm('삭제하시겠습니까?') === false) return;

        $.ajax({
            type: "POST",
            url: "${CP}/board/doDelete.do",
            dataType: "html",
            data: {
                "seq": seqInput.value
            },
            success: function(response) {
                const message = JSON.parse(response);
                alert(message.message);
                if (message.messageId === 1) {
                    window.location.href = '${CP}/board/doRetrieve.do?div=' + divValue;
                }
            },
            error: function(err) {
                console.error("Error:", err);
                alert("삭제 중 오류가 발생했습니다.");
            }
        });
    });
});
</script>
</head>
<body>
<div class="form-container">
    <h2>게시글 관리</h2>
    <hr class="title-underline" />

    <!-- 버튼 영역 -->
    <div class="button-area">
        <input type="button" id="moveToList" value="목록" />
        <input type="button" id="doUpdate" value="수정" />
        <input type="button" id="doDelete" value="삭제" />
    </div>

    <!-- 폼 영역 -->
    <form>
        <input type="hidden" name="seq" id="seq" value="${vo.seq}" />
        <input type="hidden" name="div" id="div" value="${divValue}" />

        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" name="title" id="title" value="${vo.title}" maxlength="200" />
        </div>

        <div class="form-group">
            <label for="readCnt">조회수</label>
            <input type="text" name="readCnt" id="readCnt" value="${vo.readCnt}" disabled />
        </div>

        <div class="form-group">
            <label for="regDt">등록일</label>
            <input type="text" name="regDt" id="regDt" value="${vo.regDt}" disabled />
        </div>

        <div class="form-group">
            <label for="regId">등록자</label>
            <input type="text" name="regId" id="regId" value="${vo.regId}" disabled />
        </div>

        <div class="form-group">
            <label for="contents">내용</label>
            <textarea name="contents" id="contents" class="contents">${vo.contents}</textarea>
        </div>
    </form>
</div>
</body>
</html>