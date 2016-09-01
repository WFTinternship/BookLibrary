<%@ page import="com.workfront.internship.booklibrary.common.Author" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 9/1/2016
  Time: 1:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>author</title>
</head>
<body>
<p>welcome to author page</p>
<p>author: </p>

<div id="particularAuthor">
    <%
        String name = (String) request.getSession().getAttribute("name");
        String surName = (String) request.getSession().getAttribute("surname");

        out.print(name + " " + surName);
    %>
</div>
</body>
</html>
