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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/sign_in.css"/>">
</head>

<body style="background-color: #ffffcc">
<div class="signin">
<form method="post" action="/SignIn">
    username<span>*</span><br/>
    <input type="text" name="username/email" required autofocus><br/>
    <br/>password<span>*</span><br/>
    <input type="password" name="password" required><br/>
    <br/><input type="submit" value= "Submit">
    <input type="reset" value = "cancel"><br/>

</form>
</div>

<br/>
<div class="buttons" style="position:absolute; bottom:10px; display: inline;">
    <a href="/"><button id="home" type="button">home</button></a>

    <div class="dialogueContainer">
        <button id="contacts" type="button">contacts</button>
        <div id="dialogue" title="Basic dialogue">
            <p>Address: Armenia, 0001, Yerevan<br/>Hyusisayin Ave., 1 Building, Office 14<br/>(Kentron adm. district)<br/>phone: +374-60-619828</p>
            <p id="WF">Workfront Armenia</p>
        </div>
        <div class="clear"></div>
    </div>
</div>
</body>

</html>
