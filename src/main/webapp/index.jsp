<%@ page  import="by.ivanchenko.carrental.bean.user.User" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%--<%@ page isELIgnored="true" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <title>Main</title>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="local" var="localization"/> <%-- "local" - стандатрные данные  --%>

    <fmt:message bundle="${localization}" key="local.cars" var="name"/>
    <fmt:message bundle="${localization}" key="local.registration" var="reg"/>
    <fmt:message bundle="${localization}" key="local.locbutton.name.en" var="en_button"/>
    <fmt:message bundle="${localization}" key="local.locbutton.name.ru" var="ru_button"/>
    <fmt:message bundle="${localization}" key="local.locbutton.name.by" var="by_button"/>
</head>
<body>
<form action="mainpage" method="post">
    <input type="hidden" name="local" value="en"/>
    <input type="submit" value="${en_button}" />
</form>

<form action="mainpage" method="post">
    <input type="hidden" name="local" value="ru"/>
    <input type="submit" value="${ru_button}" />
</form>

<form action="mainpage" method="post">
    <input type="hidden" name="local" value="by"/>
    <input type="submit" value="${by_button}" />
</form>

<h3>${name}</h3>

<br/>
<br/>
<a href="authorization.jsp">logIn</a>
<a href="registration.jsp">${reg}</a>
<br/>

<%--<form action="registration.jsp" method="post">  &lt;%&ndash; post/get      // action  = "/registration" &ndash;%&gt;--%>
<%--    <input type="hidden" name="command" value="GoToRegistration"/>--%>
<%--    <input type="submit" value="Registration"/>--%>
<%--</form>--%>

<table cellpadding="5">
<tr>
    <th scope="col">id</th>
    <th scope="col">Car</th>
    <th scope="col">Transmission</th>
    <th scope="col">Year</th>
    <th scope="col">Drive</th>
    <th scope="col">Fuel</th>
    <th scope="col">Engine Capacity</th>
    <th scope="col">Tank</th>
    <th scope="col">Consumption</th>
    <th scope="col">Body Type</th>
    <th scope="col">Price</th>
    <th scope="col">Mileage</th>
</tr>

<c:forEach  var="cars" items="${cars}">
    <tr scope="row">
        <td>${cars.id}</td>
        <td>${cars.name}</td>
        <td>${cars.transmission}</td>
        <td>${cars.year}</td>
        <td>${cars.drive}</td>
        <td>${cars.fuel}</td>
        <td>${cars.engineCapacity}</td>
        <td>${cars.tank}</td>
        <td>${cars.consumption}</td>
        <td>${cars.bodyType}</td>
        <td>${cars.price}</td>
        <td>${cars.mileage}</td>
    </tr>
</c:forEach>
    </table>




<br/>
<button onclick="location='registration.jsp'">Registration</button>
<%--<tr>--%>
<%--    <td>${car1.id}</td>--%>
<%--    <td>${car1.name}</td>--%>
<%--    <td>${cars[1].name}</td>--%>
<%--</tr>--%>


<%--форма авторизации  method="get" или post  --%>
<%--<form action="mainpage" method="get">  &lt;%&ndash; post &ndash;%&gt;--%>
<%--    <input type="hidden" name="command" value="authorization"/>--%>

<%--    Enter email:<br/>--%>
<%--    <input type="text" name="email" value=""/> <br/>--%>

<%--    Enter password:<br/>--%>
<%--    <input type="password" name="password" value=""/> <br/>--%>

<%--    <input type="submit" value="Send"/>--%>
<%--</form>--%>



<c:set var="admin" value="admin_user" scope="session"/>
<c:set var="manger" value="manager_user" scope="session"/>
<c:set var="customer" value="customer_user" scope="session"/>
<c:choose>
    <c:when test="${user.role==2}">
        <button onclick="location='customer_home.jsp'">Customer page</button>
    </c:when>
</c:choose>

<br/>



</body>
</html>