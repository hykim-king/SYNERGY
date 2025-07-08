<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>메인 홈페이지</title>
 
    <!-- 👇 직접 합친 CSS -->
    <style>
      body {
        margin: 0;
        font-family: "Segoe UI", sans-serif;
        background-color: #f4f6f8;
      }
      header {
        background-color: #00274d;
        color: white;
        padding: 20px 40px;
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
      .nav-left,
      .nav-right {
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
      footer {
        background-color: #ddd;
        padding: 20px;
        text-align: center;
        font-size: 14px;
      }
      /* ── 네비게이션 바 ── */
      .navbar {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0.9rem 1rem;
        background-color: #99b1c9;
        color: #fff;
        position: sticky;
        top: 0;
        z-index: 100;
      }
      .navbar__menu {
        list-style: none;
        display: flex;
        margin: 0;
        padding: 0;
      }
      .navbar__menu > li {
        margin-left: 1rem;
      }
      .navbar__menu a,
      .dropdown__btn {
        text-decoration: none;
        color: #fff;
        background: none;
        border: none;
        font: inherit;
        cursor: pointer;
      }
      .navbar__item--dropdown {
        position: relative;
      }
      .dropdown__content {
        display: none;
        position: absolute;
        left: 0;
        top: 100%;
        background-color: #fff;
        color: #333;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
        list-style: none;
        margin: 0;
        padding: 0.5rem 0;
        z-index: 1000;
      }
      .dropdown__content li a {
        display: block;
        padding: 0.5rem 1rem;
        white-space: nowrap;
        color: #333;
      }
      .navbar__item--dropdown.open .dropdown__content {
        display: block;
      }
/* 슬라이더 컨테이너 설정 */
.slider {
  width: 100%;
  max-width: 1200px;
  min-height: 400px; /* 원하는 만큼 늘리기 */
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
      .slider .nav {
        position: absolute;
        top: 50%;
        width: 100%;
        display: flex;
        justify-content: space-between;
        transform: translateY(-50%);
      }
      .slider .nav button {
        background: rgba(0, 0, 0, 0.4);
        border: none;
        color: #fff;
        font-size: 24px;
        padding: 8px 12px;
        cursor: pointer;
        border-radius: 50%;
      }
/* 슬라이드 내부 이미지 공통 스타일 */
    .slider .slide img {
      display: block;
      margin: 0 auto;
      width: 480px;
      height: 260px;
      object-fit: contain;
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 0 10px rgba(0,0,0,0.05);
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
    <header>
      <div class="nav-left">
        <!-- 전체 차량 모델 드롭다운 -->
        <div class="navbar__item--dropdown">
          <button class="dropdown__btn">전체 차량 모델</button>
        </div>

        <!-- 나머지 메뉴 -->
        <a href="${pageContext.request.contextPath}/retailer/search.do">
          리테일러 찾기
        </a>
        <a
          href="#"
          onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/drive/form.do')"
        >
          시승 신청
        </a>
        <a
          href="#"
          onclick="handleProtectedLink(event, '${pageContext.request.contextPath}/repair/form.do')"
        >
          정비 신청
        </a>
        <a href="${pageContext.request.contextPath}/board/doRetrieve.do">
          자유게시판
        </a>
        <a href="${pageContext.request.contextPath}/event/doRetrieve.do">
          이벤트
        </a>
      </div>

      <div class="nav-right">
        <c:choose>
          <c:when test="${not empty sessionScope.loginUser}">
            <span>👤 ${sessionScope.loginUser.nickname}님</span>
            <a href="${pageContext.request.contextPath}/member/logout.do"
              >로그아웃</a
            > 
          </c:when>
          <c:otherwise>
            <a href="${pageContext.request.contextPath}/member/loginView.do"
              >🔐 로그인</a
            >
          </c:otherwise>
        </c:choose>
      </div>
    </header>


    <!--SQL 디벨로퍼와 연동하기-->
    <nav class="navbar">
      <ul class="navbar__menu">
        <!-- 전체 차량 모델도 드롭다운으로 -->
        <li class="navbar__item--dropdown">
          <button class="dropdown__btn">전체 차량 모델</li>

        <!-- 더보기 ▼ -->
        <li class="navbar__item--dropdown">
          <button class="dropdown__btn">더보기 ▼</button>
          <ul class="dropdown__content">
            <li>
              <a href="${pageContext.request.contextPath}/retailer/search.do"
                >현대</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >기아</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >포르쉐</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >람브로기니</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >BMW</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >토요타</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >벤츠</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >테슬라</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >페라리</a
              >
            </li>

                        <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >포드</a
              >
            </li>            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >쉐보레</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >KGM</a
              >
            </li>

            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >아우디</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >볼보</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >지프</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >혼다</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >미니</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >르노코리아 </a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >렉서스</a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >랜드로버 </a
              >
            </li>

            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >폭스바겐  </a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >푸조  </a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >캐딜락  </a
              >
            </li>
            <li>
              <a href="${pageContext.request.contextPath}/event/doRetrieve.do"
                >링컨  </a
              >
            </li>
          </ul>
        </li>
      </ul>
    </nav>


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
    <img src="${pageContext.request.contextPath}/image/2025 메르세데스-벤츠 G클래스.png" alt="2025 메르세데스-벤츠 G클래스" >
  </div>
  <div class="slide">
    <img src="${pageContext.request.contextPath}/image/볼보_XC40.png" alt="볼보 XC40" >
  </div>
  <div class="slide">
    <img src="${pageContext.request.contextPath}/image/지프_어벤저 EV.png" alt="지프 어벤저 EV" >
  </div>
  <div class="nav">
    <button id="prev">&#10094;</button>
    <button id="next">&#10095;</button>
  </div>
</section>

      <!-- 기존 환영 문구 (필요시 삭제하거나 슬라이더 아래로 이동) -->
      <section style="text-align: center; margin-bottom: 40px">
        <h2>환영합니다!</h2>
        <p>당신의 프리미엄 드라이빙 경험, 지금 시작하세요.</p>
      </section>

      <!-- 차량 관리 섹션 생략 -->
    </main>

    <footer>ⓒ 2025 자동차 브랜드. All rights reserved.</footer>

<!-- 기존 js 파일 링크 제거 후, 아래처럼 삽입 -->
<script>
document.addEventListener("DOMContentLoaded", () => {
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

  function prevSlide() {
    current = (current - 1 + slides.length) % slides.length;
    showSlide(current);
  }

  // 버튼 클릭 이벤트
  prevBtn.addEventListener("click", () => {
    prevSlide();
    resetInterval();
  });
  nextBtn.addEventListener("click", () => {
    nextSlide();
    resetInterval();
  });

  // 자동 슬라이드 (3초)
  function startInterval() {
    intervalId = setInterval(nextSlide, 3000);
  }
  function resetInterval() {
    clearInterval(intervalId);
    startInterval();
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

  </body>
</html>
