<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>차량 상세 정보</title>
<style>
body { font-family: Arial, sans-serif; background: #f7f9fc; }
.detail-box {
  background: #fff; border-radius: 12px; padding: 24px; box-shadow: 0 4px 12px #ddd; margin: 40px auto; max-width: 540px;
}
.detail-row { margin-bottom: 14px; font-size: 16px; }
.label { font-weight: bold; width: 120px; display: inline-block; color: #34495e; }
.value { color: #444; }
.back-btn {
  display: inline-block; margin-top: 22px; padding: 9px 22px; border-radius: 3px; background: #34495e; color: #fff;
  text-decoration: none; font-weight: bold; transition: background 0.15s;
}
.back-btn:hover { background: #2c3e50; }
</style>
</head>
<body>
  <header>
    <h1>차량 상세 정보</h1>
  </header>
  <main>
    <div class="detail-box">
      <div class="detail-row"><span class="label">차량명</span> <span class="value"><c:out value="${car.productName}"/></span></div>
      <div class="detail-row"><span class="label">제조사</span> <span class="value"><c:out value="${car.carMf}"/></span></div>
      <div class="detail-row"><span class="label">차종</span> <span class="value"><c:out value="${car.cartype}"/></span></div>
      <div class="detail-row"><span class="label">제조년도</span> <span class="value"><c:out value="${car.mfDt}"/></span></div>
      <div class="detail-row"><span class="label">가격</span> <span class="value"><fmt:formatNumber value="${car.price}" type="currency" currencySymbol="₩"/></span></div>
      <div class="detail-row"><span class="label">연료</span> <span class="value"><c:out value="${car.fuel}"/></span></div>
      <div class="detail-row"><span class="label">효율</span> <span class="value"><c:out value="${car.ef}"/></span></div>
      <div class="detail-row"><span class="label">엔진</span> <span class="value"><c:out value="${car.engine != null ? car.engine : '-'}"/></span></div>
      <div class="detail-row"><span class="label">배터리</span> <span class="value"><c:out value="${car.battery != null ? car.battery : '-'}"/></span></div>
      <!-- 필요한 상세 정보 더 추가 가능 -->
      <a href="list.do" class="back-btn">목록으로</a>
    </div>
  </main>
</body>
</html>
