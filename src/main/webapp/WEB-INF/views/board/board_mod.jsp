<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="CP" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 관리</title>
<link rel="stylesheet" href="${CP}/resource/css/SNERGY/css/board.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${CP}/resource/js/board.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function(){
    const boardCodeInput = document.querySelector("#boardCode");
    const titleInput = document.querySelector("#title");
    const contentsTextarea = document.querySelector("#contents");
    const moveToListButton = document.querySelector("#moveToList");
    const doUpdateButton = document.querySelector("#doUpdate");
    const doDeleteButton = document.querySelector("#doDelete");
    const regIdInput = document.querySelector("#regId");
    const divInput = document.querySelector("#div");

    // 목록 이동
    moveToListButton.addEventListener('click', function(){
        if(confirm('목록으로 이동 하시겠습니까?') === false) return;
        location.href = '${CP}/board/board_list.do?div=' + divInput.value;
    });

    // 수정
    doUpdateButton.addEventListener('click', function(){
        if(isEmpty(titleInput.value)){
            alert('제목을 입력 하세요');
            titleInput.focus();
            return;
        }
        if(isEmpty(contentsTextarea.value)){
            alert('내용을 입력 하세요');
            contentsTextarea.focus();
            return;
        }

        if(confirm('게시글을 수정 하시겠습니까?') === false) return;

        $.ajax({
            type: "POST",
            url: "${CP}/board/doUpdate.do",
            async: true,
            dataType: "html",
            data: {
            	boardCode: boardCodeInput.value,
                title: titleInput.value,
                contents: contentsTextarea.value,
                div: divInput.value,
                modId: regIdInput.value
            },
            success: function(response){
                const message = JSON.parse(response);
                alert(message.message);
                if(message.messageId === 1){
                    location.href = '${CP}/board/board_list.do?div=' + divInput.value;
                }
            },
            error: function(error){
                console.log("error:", error);
            }
        });
    });

    // 삭제
    doDeleteButton.addEventListener('click', function(){
        if(isEmpty(boardCodeInput.value)){
            alert("게시글 번호를 확인 하세요.");
            return;
        }
        if(confirm('게시글을 삭제 하시겠습니까?') === false) return;

        $.ajax({
            type: "POST",
            url: "${CP}/board/doDelete.do",
            async: true,
            dataType: "html",
            data: {
            	boardCode: boardCodeInput.value
            },
            success: function(response){
                const message = JSON.parse(response);
                alert(message.message);
                if(message.messageId === 1){
                    location.href = '${CP}/board/board_list.do?div=' + divInput.value;
                }
            },
            error: function(error){
                console.log("error:", error);
            }
        });
    });
});
</script>
</head>
<body>
<div class="form-container">
    <c:choose>
        <c:when test="${divValue == 20}">
            <h2>자유 게시판 - 수정</h2>
        </c:when>
        <c:otherwise>
            <h2>공지사항 - 수정</h2>
        </c:otherwise>
    </c:choose>

    <hr class="title-underline">

    <!-- 버튼 -->
    <div class="button-area">
        <input type="button" id="moveToList" value="목록">
        <input type="button" id="doUpdate" value="수정">
        <input type="button" id="doDelete" value="삭제">
    </div>

    <!-- 폼 -->
    <form>
        <input type="hidden" name="boardCode" id="boardCode" value="${vo.boardCode}">
        <input type="hidden" name="div" id="div" value="${divValue}">
        
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" name="title" id="title" maxlength="200" value="${vo.title}" required>
        </div>

        <div class="form-group">
            <label for="readCnt">조회수</label>
            <input type="text" name="readCnt" id="readCnt" value="${vo.readCnt}" disabled>
        </div>
        <div class="form-group">
            <label for="nickname">별명</label>
            <input type="text" name="nickname" id="nickname" value="${vo.nickname}" disabled>
        </div>

        <div class="form-group">
            <label for="regDt">등록일</label>
            <fmt:formatDate value="${vo.regDt}" pattern="yyyy-MM-dd HH:mm:ss" var="formattedRegDt"/>
            <input type="text" name="regDt" id="regDt" value="${formattedRegDt}" disabled>
        </div>

        <div class="form-group">
            <label for="regId">등록자</label>
            <input type="text" name="regId" id="regId" maxlength="30" value="${vo.regId}" required>
        </div>

        <div class="form-group">
            <label for="contents">내용</label>
            <textarea class="contents" id="contents" name="contents" required>${vo.contents}</textarea>
        </div>
    </form>
</div>
</body>
</html>