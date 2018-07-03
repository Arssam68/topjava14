<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table border="1" cellpadding="5">
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Количество калорий</th>
    </tr>

    <c:forEach var="meal" items="${meals}">
        <fmt:formatDate value="${meal.getDate()}" var="formatted" pattern="dd.MM.yyyy HH:mm:ss"/>
        <tr>
            <td align = center>${formatted}</td>
            <td align = center>${meal.getDescription()}</td>
            <c:if test="${meal.isExceed()}">
                <td align = center bgcolor="red"><font color="white">${meal.getCalories()}</font></td>
            </c:if>
            <c:if test="${!meal.isExceed()}">
                <td align = center bgcolor="green"><font color="white">${meal.getCalories()}</fon></td>
            </c:if>
        </tr>
    </c:forEach>
</table>

</body>
</html>
