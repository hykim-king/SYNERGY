<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>현대 차량 목록</title>
</head>
<body>
  <h2>현대 차량 목록</h2>
  <table border="1" width="60%">
    <tr>
      <th>모델</th>
      <th>가격</th>
    </tr>
    <c:forEach var="car" items="${carList}">
      <tr>
        <td>${car.model}</td>
        <td>${car.price}</td>
      </tr>
    </c:forEach>
  </table>
</body>
</html>s