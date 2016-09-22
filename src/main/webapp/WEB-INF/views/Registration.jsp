<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/registration.css"/>">
</head>
<body>

<div class="registration">
    <form method="post" action="/Registration">
        <fieldset>
            <legend>Create account</legend>
            name<br/>
            <input type="text" name="name" required><br/>
            <br/>surname<br/>
            <input type="text" name="surname" required><br/>
            <br/>e-mail<br/>
            <input type="text" name="e-mail" required><br/>
            <br/>address<br/>
            <input type="text" name="address" required><br/>
            <br/>phone<br/>
            <input type="text" name="phone" required><br/>
            <br/>username<br/>
            <input type="text" name="username" required><br/>
            <br/>password<br/>
            <input type="password" name="password" required><br/>
            <br/>access privilege<br/>
            <input type="text" name="access-privilege" required>
            <br/><input type="submit" value= "Submit">
            <input type="reset" value="cancel"><br/>
        </fieldset>
    </form>
</div>


<br/>
<div class="buttons" style="position:absolute; bottom:10px;">
    <a href="/"><button type="button">home</button></a>

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
