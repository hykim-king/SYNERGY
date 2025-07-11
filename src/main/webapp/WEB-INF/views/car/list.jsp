<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>전체 차량 목록</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/style.css">
  <%@ include file="/resource/header.jsp" %>



<style>
.page-title {
    background: #00274d;
    color: #fff; 
    margin: 0;
    padding: 38px 0 38px 70px;
    font-size: 2rem;
    font-weight: bold;
    letter-spacing: -1.5px;
}



@media (max-width: 700px) {
  .header-bar, .page-title { padding-left: 20px; padding-right: 10px; }
  .page-title { font-size: 1.3rem; padding: 25px 0 18px 16px; }
}



main {
    padding: 20px 5vw;

}



table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 10px;
    background: #fff;
}




th, td {
    border: 1px solid #ccc;
    padding: 8px;
    text-align: left;
}




th {
    background-color: #34495e;
    color: white;
}



tr:nth-child(even) {
    background-color: #ecf0f1;
}



.paging {
    margin-top: 10px;
    text-align: center;
}



.paging a {
    margin: 0 5px;
    padding: 6px 12px;
    background-color: #3498db;
    color: white;
    text-decoration: none;
    border-radius: 3px;
    display: inline-block;
   
}



.paging a.current {
    background-color: #2c3e50;
    font-weight: bold;
    cursor: default;
}



.paging a.disabled {
    background-color: #bdc3c7;
    pointer-events: none;
    cursor: default;
}

.btn-main {
    float: right;
    margin-bottom: 10px;
}

.btn-main a {
    text-decoration: none;
    background-color: #2c3e50;
    color: white;
    padding: 8px 15px;
    border-radius: 3px;
    font-weight: bold;
}

.btn-main a:hover {
    background-color: #34495e;
}

/* 검색폼을 왼쪽 상단에 배치하는 래퍼 */
.search-form-wrapper {
    width: 100%;
    display: flex;
    justify-content: flex-end;  /* 왼쪽 정렬 */
    margin-bottom: 18px;
    margin-top: 18px;
}

.search-form {
    display: flex;
    gap: 8px;
    align-items: center;
}
.search-form select {
    padding: 8px 10px;
    border-radius: 5px;
    border: 1px solid #ccd4e2;
    background: #fff;
    font-size: 1rem;
    height: 42px;
}
.search-form input[type="text"] {
    padding: 7px 1px;
    border: 1px solid #ccd4e2;
    border-radius: 5px;
    width: 150px;
    font-size: 1rem;
    background: #fff;
    height: 28px;
}
.search-btn {
    background: #3498db;
    border: none;
    border-radius: 5px;
    padding: 0 18px;
    height: 42px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #fff;
    font-size: 1.25rem;
    cursor: pointer;
    transition: background 0.15s;
}
.search-btn:hover {
    background: #2378b9;
}

/* 차량 목록의 이미지에 통일 적용 */
.car-img {
    width: 100px;
    height: auto;
    border-radius: 6px;
    box-shadow: 0 2px 8px #e0e0e0;
    object-fit: contain;
    background: #fff;
}


</style>

<!-- 로그인 보호 자바스크립트(필요시 사용) -->
<script>
function handleProtectedLink(event, url) {
    // 로그인이 안 된 상태라면 alert
    var isLoggedIn = '${not empty sessionScope.loginUser}' === 'true';
    if (!isLoggedIn) {
        event.preventDefault();
        alert('로그인 후 이용해 주세요.');
    } else {
        window.location.href = url;
    }
}
</script>

</head>
<body>

  <!-- 상단 타이틀 바 -->
  <div class="page-title">차량 전체 목록</div>

<main style="padding: 20px 0 20px 40px;">
  <div style="display: flex; gap: 32px; align-items: flex-start;">
    <aside style="flex: 0 0 220px; position: sticky; top: 100px; align-self: flex-start;">
      <img src="${pageContext.request.contextPath}/resource/image/carbanner.png"
           alt="carbanner"
           style="width: 200px; border-radius: 10px; box-shadow: 0 4px 16px #e0e0e0;" />
    </aside>
    <div style="flex: 1;">
      <div style="max-width: 1100px; margin-left: 130px; margin-right: 0;">
        <!-- 상태 메시지 -->
        <c:if test="${not empty msg}">
          <script>alert('${msg}');</script>
        </c:if>
      
        <div class="search-form-wrapper">
          <!-- 검색 폼 -->
          <form class="search-form" method="get" action="list.do">
            <select name="searchType">
              <option value="productName" ${searchType eq 'productName' ? 'selected' : ''}>차량명</option>
              <option value="carMf" ${searchType eq 'carMf' ? 'selected' : ''}>제조사</option>
              <option value="cartype" ${searchType eq 'cartype' ? 'selected' : ''}>차종</option>
            </select>
            <input type="text" name="searchWord" value="${searchWord}" placeholder="검색어">
            <button type="submit" class="search-btn">
              <i class="fa-solid fa-magnifying-glass"></i>
            </button>
          </form>
        </div>

        <!-- 차량 테이블 -->
        <table>
          <thead>
            <tr>
              <th>번호</th>
              <th>차량명</th>
              <th>제조사</th>
              <th>차종</th>
              <th>가격(만 단위) </th>
              <th>이미지</th>
            </tr>
          </thead>
          <tbody>
            <c:choose>
              <c:when test="${empty carList}">
                <tr><td colspan="10">등록된 차량이 없습니다.</td></tr>
              </c:when>
              <c:otherwise>
                <c:forEach var="car" items="${carList}" varStatus="status">
                  <tr>
                    <td>${(currentPage-1) * pageSize + status.index + 1}</td>
                    <td>
                      <a href="detail.do?carCode=${car.carCode}">
                        <c:out value="${car.productName}" />
                      </a>
                    </td>
                    <td><c:out value="${car.carMf}" /></td>
                    <td><c:out value="${car.cartype}" /></td>
                    <td><fmt:formatNumber value="${car.price}" type="currency" currencySymbol="₩" /></td>
                    <td>
                      <img class="car-img"
                           src="${pageContext.request.contextPath}${car.path}${car.modFn}"
                           alt="${car.productName}"
                           style="width:100px; height:auto;" />
                    </td>
                  </tr>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </tbody>
        </table>

        <!-- 페이징 영역 -->
        <div class="paging">
          <c:set var="pageBlock" value="10" />
          <c:set var="startPage" value="${((currentPage - 1) / pageBlock) * pageBlock + 1}" />
          <c:set var="endPage" value="${startPage + pageBlock - 1}" />
          <c:if test="${endPage > totalPages}">
            <c:set var="endPage" value="${totalPages}" />
          </c:if>
          <c:choose>
            <c:when test="${currentPage == 1}">
              <a href="#" class="disabled">&laquo; 이전</a>
            </c:when>
            <c:otherwise>
              <a href="list.do?pageNum=${currentPage - 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">&laquo; 이전</a>
            </c:otherwise>
          </c:choose>
          <c:forEach begin="${startPage}" end="${endPage}" var="i">
            <c:choose>
              <c:when test="${i == currentPage}">
                <a href="#" class="current">${i}</a>
              </c:when>
              <c:otherwise>
                <a href="list.do?pageNum=${i}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">${i}</a>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          <c:choose>
            <c:when test="${currentPage == totalPages || totalPages == 0}">
              <a href="#" class="disabled">다음 &raquo;</a>
            </c:when>
            <c:otherwise>
              <a href="list.do?pageNum=${currentPage + 1}&pageSize=${pageSize}&searchType=${searchType}&searchWord=${searchWord}">다음 &raquo;</a>
            </c:otherwise>
          </c:choose>
        </div>
        <%@ include file="/resource/footer.jsp" %>
      </div> <!-- 여기가 빠졌던 div 닫힘!! -->
    </div>
  </div>
</main>

</body>
</html>