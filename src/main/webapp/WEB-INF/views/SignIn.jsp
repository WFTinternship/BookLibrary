<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by Sona
  Date: 8/9/2016
  Time: 4:17 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign-in Page</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/sign-in.css"/>">
</head>

<body style="background-color: #ffffcc">
<div class="signin">
<form method="post" action="/SignIn">
    username/email:<br/>
    <input type="text" name="username/email"><br/>
    <br/>password:<br/>
    <input type="password" name="password"><br/>
    <br/><input type="submit" value= "Submit"><br/>

</form>
</div>

<br/>
<div class="buttons" style="position:absolute; bottom:10px;">
    <a href="/index.jsp"><button type="button">home</button></a>
    <button type="button">contacts</button>
</div>
</body>

</html>
