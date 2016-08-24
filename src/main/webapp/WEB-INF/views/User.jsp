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
    <h1>Welcome!</h1>

    <div class="buttons" style="position:absolute; bottom:10px; display: inline;">
        <a href="/"><button id="home" type="button">home</button></a>
        <%--&nbsp;--%>

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
