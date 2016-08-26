<%@ page import="com.workfront.internship.booklibrary.common.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 8/10/2016
  Time: 2:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User page</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/user.css"/>">
</head>

<body>
    <div>
        <p> <%User user = (User)request.getSession().getAttribute("user");
        out.print("hi " + user.getName());
        %></p>


        <button type="button" ><a href="/signout">Sign out</a></button>
    </div>

    <div class="buttons" style="position:absolute; bottom:10px; display: inline;">
        <a href="/"><button id="home" type="button">home</button></a>
    </div>
        <%--&nbsp;--%>

</body>
</html>
