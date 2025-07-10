<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/resource/header.jsp" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>메인 홈페이지</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/mainSlider.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/main.css">

</head>

<body>
  <main>
    <section class="slider">
      <div class="slide active">
        <img src="${pageContext.request.contextPath}/resource/image/2024kiaK9.png" alt="2024kiaK9">
      </div>
      <div class="slide">
        <img src="${pageContext.request.contextPath}/resource/image/2024 테슬라 모델 S.png" alt="2024 테슬라 모델 S">
      </div>
      <div class="slide">
        <img src="${pageContext.request.contextPath}/resource/image/2025 팰리세이드.png" alt="2025 팰리세이드">
      </div>
      <div class="slide">
        <img src="${pageContext.request.contextPath}/resource/image/2025 메르세데스-벤츠 G클래스.png" alt="2025 메르세데스-벤츠 G클래스">
      </div>
      <div class="slide">
        <img src="${pageContext.request.contextPath}/resource/image/볼보_XC40.png" alt="볼보 XC40">
      </div>
      <div class="slide">
        <img src="${pageContext.request.contextPath}/resource/image/지프_어벤저 EV.png" alt="지프 어벤저 EV">
      </div>
    </section>
    
<div class="banner-image">
  <img src="${pageContext.request.contextPath}/resource/banner/banner_car_pick.png" alt="Car Pick 소개 이미지" >
</div>

<section class="intro-section">
  <h2>🚘 내 차를 고르는 가장 스마트한 방법, <span class="brand-name">Car Pick!</span></h2>
  <p class="intro-text">
    Car Pick은 자동차 선택과 시승, 정비 예약까지<br>
    한 번에 해결할 수 있는 <strong>올인원 차량 정보 플랫폼</strong>입니다.<br>
    다양한 국내외 브랜드 차량 정보를 확인하고,<br>
    마음에 드는 차량을 <strong>직접 시승</strong>해보세요.
  </p>

  <ul class="features">
    <li>📍 브랜드별 차량 검색</li>
    <li>📍 실시간 시승 가능 업체 확인</li>
    <li>📍 시승 및 정비 신청 즉시 처리</li>
    <li>📍 생생한 후기와 정보가 오가는 커뮤니티</li>
    <li>📍 한 달에 한 번! 시승 고객 대상 경품 이벤트</li>
  </ul>

  <p class="closing-text">
    자동차는 단순히 '사는 것'이 아니라 <strong>'경험하는 것'</strong>이 중요합니다.<br>
    Car Pick은 여러분의 자동차 생활을 <strong>더 간편하게, 더 즐겁게, 더 똑똑하게</strong> 만들어드립니다.<br><br>
    <strong>당신의 다음 차,</strong> 어디서 고를지 고민하지 마세요.<br>
    <strong>Car Pick에서 직접 보고, 타보고, 느껴보세요.</strong>
  </p>
</section>
  </main>




  <script>

    document.addEventListener("DOMContentLoaded", () => {

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


      function startInterval() {
        intervalId = setInterval(nextSlide, 3000);
      }
      function resetInterval() {
        clearInterval(intervalId);
        startInterval();
      }

      showSlide(current);
      startInterval();
    });
  </script>

  <%@ include file="/resource/footer.jsp" %>
</body>
</html>
