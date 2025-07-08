<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>메인 홈페이지</title>
    <!-- 공통 스타일 -->
    <link rel="stylesheet" href="css/Car.css" />


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
      <!--차량 슬라이더 이미지-->
      <section class="slider">
        <div class="slide active">
          <img src="Car_image/2024 기아 K9.png" alt="2024 기아 K9">
        </div>
        <div class="slide">
          <img src="Car_image/v.png" alt="Model S" />
        </div>
        <div class="slide">
          <img src="Car_image/2025 팰리세이드.png" alt="Palisade" />
        </div>
        <div class="slide">
          <img src="Car_image/2025 메르세데스-벤츠 G클래스.png" alt="Palisade" />
        </div>
        <div class="slide">
          <img src="Car_image/볼보_XC40.png" alt="Palisade" />
        </div>
        <div class="slide">
          <img src="Car_image/지프_어벤저 EV.png" alt="Palisade" />
        </div>
        <div class="slide">
          <img src="Car_image/2024 테슬라 모델 S.png" alt="Palisade" />
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

    <!-- 슬라이더 스크립트 -->
    <script src="js/Car.js" defer></script>
  </body>
</html>
