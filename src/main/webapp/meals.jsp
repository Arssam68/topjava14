<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {
            /*color: green;*/
            /*color: white;*/
            background-color: lightgreen;
            text-align: center
        }

        .exceeded {
            /*color:red;*/
            /*color: white;*/
            background-color: lightcoral;
            text-align: center
        }
    </style>
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
        <tr class="${meal.isExceed() ? 'exceeded' : 'normal'}">
            <td>${fn:replace(meal.getDateTime(), 'T', ' ')}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
