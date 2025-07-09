// 카드 클릭 시 상세 정보 로드 및 Drawer 열기
document.querySelectorAll(".card").forEach((card) => {
  card.addEventListener("click", () => {
    const code = card.getAttribute("data-car-code");
    fetch(`/car/detail?carCode=${code}`)
      .then((res) => res.text())
      .then((html) => {
        document.getElementById("drawer-content").innerHTML = html;
        document.getElementById("drawer").classList.add("open");
      })
      .catch((err) => console.error(err));
  });
});
// Drawer 닫기 버튼
document.getElementById("btn-close").addEventListener("click", () => {
  document.getElementById("drawer").classList.remove("open");
});
