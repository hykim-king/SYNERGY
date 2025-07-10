<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>메인 홈페이지</title>

<!-- 공용 헤더.jsp /css -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
<%@ include file="/resource/header.jsp" %>

<!-- ✅ 로그인 여부 자바스크립트 변수 전달 -->
<script>
  const isLoggedIn = ${not empty sessionScope.loginUser ? 'true' : 'false'};
</script>


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
    width: 800px;;
    height: 430px;
    object-fit: contain;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
}
</style>


<!-- 💡 로그인 여부 확인 후 페이지 이동 제어 -->
<script>
      function handleProtectedLink(event, url) {
        if (!isLoggedIn) {
          event.preventDefault();
          alert("로그인을 시도해 주세요.");
        } else {
          window.location.href = url;
        }
      }
    </script>
</head>


<body>
    <main>
        <section class="slider">
            <div class="slide active">
                <img src="${pageContext.request.contextPath}/image/2024kiaK9.png"
                    alt="2024kiaK9">
            </div>
            <div class="slide">
                <img
                    src="${pageContext.request.contextPath}/image/2024 테슬라 모델 S.png"
                    alt="2024 테슬라 모델 S">
            </div>
            <div class="slide">
                <img src="${pageContext.request.contextPath}/image/2025 팰리세이드.png"
                    alt="2025 팰리세이드">
            </div>
            <div class="slide">
                <img
                    src="${pageContext.request.contextPath}/image/2025 메르세데스-벤츠 G클래스.png"
                    alt="2025 메르세데스-벤츠 G클래스">
            </div>
            <div class="slide">
                <img src="${pageContext.request.contextPath}/image/볼보_XC40.png"
                    alt="볼보 XC40">
            </div>
            <div class="slide">
                <img src="${pageContext.request.contextPath}/image/지프_어벤저 EV.png"
                    alt="지프 어벤저 EV">
            </div>

        </section>

        <!-- 기존 환영 문구 (필요시 삭제하거나 슬라이더 아래로 이동) -->
        <section style="text-align: center; margin-bottom: 40px">
            <h2>환영합니다!</h2>
            <p>당신의 프리미엄 드라이빙 경험, 지금 시작하세요.</p>
        </section>

        <!-- 차량 관리 섹션 생략 -->
    </main>


    <!-- 기존 js 파일 링크 제거 후, 아래처럼 삽입 -->
    <script>
document.addEventListener("DOMContentLoaded", () => {
    
     const isLoggedIn = ${not empty sessionScope.loginUser ? 'true' : 'false'};
      if (isLoggedIn) {
        alert("환영합니다, ${sessionScope.loginUser.nickname}님!");
      }
  // --- 슬라이더 자동 전환 로직 ---
  const slides = document.querySelectorAll(".slide");
  const prevBtn = document.getElementById("prev");
  const nextBtn = document.getElementById("next");
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


  // 자동 슬라이드 (3초)
  function startInterval() {
    intervalId = setInterval(nextSlide, 3000);
  }

  // 최초 초기화
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
});
</script>

<!-- 공용 푸터 -->
  <%@ include file="/resource/footer.jsp" %>

</body>
</html>