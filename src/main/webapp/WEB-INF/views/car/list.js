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
