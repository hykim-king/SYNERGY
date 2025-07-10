<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>메인 홈페이지</title>

<!-- 공용 헤더.jsp /css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<%@ include file="/resource/header.jsp" %>

<!--
  로그인 여부 변수는 header.jsp에서 선언하므로
  여기서는 선언하지 않습니다.
-->

<!-- 👇 직접 합친 CSS -->
<style>
.nav-left, .nav-right {
    display: flex;
    align-items: center;
    gap: 20px;
}

a {
    color: white;
    text-decoration: none;
    font-weight: bold;
}

a:hover {
    text-decoration: underline;
}

main {
    padding: 60px 40px;
    text-align: center;
}

/* 슬라이더 컨테이너 설정 */
.slider {
    width: 100%;
    max-width: 1200px;
    min-height: 500px; /* 원하는 만큼 늘리기 */
    margin: 2rem auto;
    overflow: hidden;
    border-radius: 8px;
    background: #f4f6f8;
}

.slide {
    position: static;
    display: none;
    opacity: 0;
    transition: opacity 0.6s ease-in-out;
}

.slide.active {
    display: block;
    opacity: 1;
}

/* 슬라이드 내부 이미지 공통 스타일 */
.slider .slide img {
    display: block;
    margin: 0 auto;
    width: 800px;
    height: 430px;
    object-fit: contain;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
}
</style>

<!-- 💡 로그인 여부 확인 후 페이지 이동 제어 -->
<script>
  // handleProtectedLink() 함수는 header.jsp에 이미 있으니 중복 선언하지 마세요
</script>
</head>

<body>
<main>
    <section class="slider">
        <div class="slide active">
            <img src="${pageContext.request.contextPath}/image/2024kiaK9.png" alt="2024kiaK9">
        </div>
        <div class="slide">
            <img src="${pageContext.request.contextPath}/image/2024 테슬라 모델 S.png" alt="2024 테슬라 모델 S">
        </div>
        <div class="slide">
            <img src="${pageContext.request.contextPath}/image/2025 팰리세이드.png" alt="2025 팰리세이드">
        </div>
        <div class="slide">
            <img src="${pageContext.request.contextPath}/image/2025 메르세데스-벤츠 G클래스.png" alt="2025 메르세데스-벤츠 G클래스">
        </div>
        <div class="slide">
            <img src="${pageContext.request.contextPath}/image/볼보_XC40.png" alt="볼보 XC40">
        </div>
        <div class="slide">
            <img src="${pageContext.request.contextPath}/image/지프_어벤저 EV.png" alt="지프 어벤저 EV">
        </div>
    </section>

    <section style="text-align: center; margin-bottom: 40px">
        <h2>환영합니다!</h2>
        <p>당신의 프리미엄 드라이빙 경험, 지금 시작하세요.</p>
    </section>
</main>

<script>
document.addEventListener("DOMContentLoaded", () => {
    console.log("isLoggedIn:", window.isLoggedIn);
    console.log("userNickname:", window.userNickname);
});

// --- 슬라이더 자동 전환 로직 ---
const slides = document.querySelectorAll(".slide");
let current = 0;
let intervalId;

function showSlide(idx) {
    slides.forEach((slide, i) => {
        slide.classList.toggle("active", i === idx);
    });
}

function nextSlide() {
    current = (current + 1) % slides.length;
    showSlide(current);
}

function startInterval() {
    intervalId = setInterval(nextSlide, 3000);
}

showSlide(current);
startInterval();

// --- 드롭다운 토글 로직 ---
const dropdowns = document.querySelectorAll(".navbar__item--dropdown");
dropdowns.forEach((dropdown) => {
    const btn = dropdown.querySelector(".dropdown__btn");
    const content = dropdown.querySelector(".dropdown__content");

    btn &&
        btn.addEventListener("click", (e) => {
            e.stopPropagation();
            dropdowns.forEach((d) => {
                if (d !== dropdown) d.classList.remove("open");
            });
            dropdown.classList.toggle("open");
        });
});

document.addEventListener("click", () => {
    dropdowns.forEach((d) => d.classList.remove("open"));
});

function logoutAndClear() {
    sessionStorage.removeItem('welcomeShown'); // 환영 메시지 초기화
    location.href = '${pageContext.request.contextPath}/member/logout.do';
}
</script>

<!-- 공용 푸터 -->
<%@ include file="/resource/footer.jsp" %>

</body>
</html>