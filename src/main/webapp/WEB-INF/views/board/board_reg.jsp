<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CarPick | 게시글 등록</title>
<link rel="stylesheet" href="/ehr/resources/assets/css/form.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="/ehr/resources/assets/js/common.js"></script>

<script>
	document.addEventListener('DOMContentLoaded', function() {
		const titleInput = document.querySelector("#title");
		const regIdInput = document.querySelector("#regId");
		const contentsTextarea = document.querySelector("#contents");
		const divInput = document.querySelector("#div");
		const doSaveButton = document.querySelector("#doSave");
		const moveToListButton = document.querySelector("#moveToList");

		doSaveButton.addEventListener("click", function() {
			if (isEmpty(titleInput.value)) {
				alert("제목을 입력하세요.");
				titleInput.focus();
				return;
			}

			if (isEmpty(regIdInput.value)) {
				alert("등록자를 입력하세요.");
				regIdInput.focus();
				return;
			}

			if (isEmpty(contentsTextarea.value)) {
				alert("내용을 입력하세요.");
				contentsTextarea.focus();
				return;
			}

			if (!confirm("등록하시겠습니까?"))
				return;

			$.ajax({
				type : "POST",
				url : "/ehr/board/doSave.do",
				async : true,
				dataType : "html",
				data : {
					title : titleInput.value,
					regId : regIdInput.value,
					contents : contentsTextarea.value,
					div : divInput.value
				},
				success : function(response) {
					const message = JSON.parse(response);
					if (message.messageId === 1) {
						alert(message.message);
						window.location.href = '/ehr/board/doRetrieve.do?div='
								+ divInput.value;
					} else {
						alert("등록 실패: " + message.message);
					}
				},
				error : function(xhr, status, error) {
					alert("에러 발생: " + error);
				}
			});
		});

		moveToListButton.addEventListener("click", function() {
			window.location.href = "/ehr/board/doRetrieve.do?div="
					+ divInput.value;
		});
	});
</script>
</head>

<body>
	<div class="form-container">
		<h2>게시글 등록</h2>
		<hr class="title-underline">

		<!-- 버튼 영역 -->
		<div class="button-area">
			<input type="button" id="doSave" value="등록"> <input
				type="button" id="moveToList" value="목록">
		</div>

		<!-- form 영역 -->
		<form id="regForm">
			<input type="hidden" name="div" id="div" value="10">
			<div class="form-group">
				<label for="title">제목</label> <input type="text" name="title"
					id="title" maxlength="200" required placeholder="제목 입력">
			</div>
			<div class="form-group">
				<label for="regId">등록자</label> <input type="text" name="regId"
					id="regId" maxlength="20" required placeholder="등록자 ID">
			</div>
			<div class="form-group">
				<label for="contents">내용</label>
				<textarea id="contents" name="contents" rows="10"
					placeholder="내용을 입력하세요."></textarea>
			</div>
		</form>
	</div>
</body>
</html>