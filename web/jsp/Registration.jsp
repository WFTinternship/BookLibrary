<%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 8/10/2016
  Time: 11:22 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" type="text/css" href="/css/registration.css">
</head>
<body style="background-color: #ffffcc">
<div class="registration">
    <form method="post" action="/MainPage">
        <fieldset>
            <legend>Create account</legend>
            name<br/>
            <input type="text" name="name"><br/>
            <br/>surname<br/>
            <input type="text" name="surname"><br/>
            <br/>e-mail<br/>
            <input type="text" name="e-mail"><br/>
            <br/>address<br/>
            <input type="text" name="address"><br/>
            <br/>phone<br/>
            <input type="text" name="phone"><br/>
            <br/>username<br/>
            <input type="text" name="username"><br/>
            <br/>password<br/>
            <input type="password" name="password"><br/>
            <br/>access privilege<br/>
            <input type="text" name="access-privilege">
            <br/><input type="submit" value= "Submit"><br/>
        </fieldset>
    </form>
</div>


<br/>
<div class="buttons" style="position:absolute; bottom:10px;">
    <a href="/jsp/MainPage.jsp"><button type="button">home</button></a>
    <button type="button">contacts</button>
</div>
</body>
</html>
