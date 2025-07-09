const slides = document.querySelectorAll(".slide");
let idx = 0;
setInterval(() => {
  slides[idx].classList.remove("active");
  idx = (idx + 1) % slides.length;
  slides[idx].classList.add("active");
}, 3000);

// 더보기 버튼과 컨테이너 선택
const dropdownBtn = document.querySelector(".dropdown__btn");
const dropdownItem = document.querySelector(".navbar__item--dropdown");

// 클릭 시 open 클래스 토글
dropdownBtn.addEventListener("click", () => {
  dropdownItem.classList.toggle("open");
});

// 외부 클릭 시 닫기
document.addEventListener("click", (e) => {
  if (!dropdownItem.contains(e.target)) {
    dropdownItem.classList.remove("open");
  }
});
