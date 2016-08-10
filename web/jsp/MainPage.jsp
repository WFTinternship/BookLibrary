<%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 8/10/2016
  Time: 11:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main page</title>
    <link rel="stylesheet" type="text/css" href="/css/main_page.css">
</head>
<body>
    <a href="https://www.workfront.com/" target="_blank">
        <img src="/image/workfront_lazure.png" id="logo" alt="Workfront logo" title="go to Workfront homepage" align="left" style="border-radius: 50%;">
    </a>

    <div id="headerText">
        <h1>Welcome to Workfront<br/>Book Library</h1>
    </div>

    <div class="buttons" style="float: right"><br/>
        <button type="button">home</button>
        <button type="button">books</button>
        <button type="button">authors</button>
        <button type="button">search</button>
        <br/>
    </div>
    <br/>

    <br/>
    <br/>
    <div style="float: right" class="buttons">
        <a href="/jsp/SignIn.jsp" target="_blank"><button name="sign-in">sign-in</button></a><br/>
        <br/><a href="/jsp/Registration.jsp"><button style="background-color: #eff5f5; color: #0086b3; border: none; border-radius: 10px; width: 150px; height: 30px;" name="register">register</button></a>
    </div>

    <br/>

    <div class="floating_media">
        <img src="/image/download1.jpg" alt="changing images" style="position: absolute; top: 0; /*left: 100px;*/">
        <img src="/image/download2.jpg" alt="changing images" style="position: absolute; top: 0; left: 200px;">
        <img src="/image/download3.jpg" alt="changing images" style="position: absolute; top: 0; left: 400px;">
        <img src="/image/download4.jpg" alt="changing images" style="position: absolute; top: 0; left: 600px;">
        <img src="/image/download5.jpg" alt="changing images" style="position: absolute; top: 0; left: 800px;">
    </div>

    <br/>
    <div class="buttons" style="position:absolute; bottom:10px;">
        <a href="/jsp/MainPage.jsp" target="_blank"><button type="button">home</button></a>
        <button type="button">contacts</button>
    </div>
</body>
</html>
