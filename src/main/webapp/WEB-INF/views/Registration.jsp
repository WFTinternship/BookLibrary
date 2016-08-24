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
<%--style="background-color: #ffffcc"--%>





<%----%>

<%--<a href="#openModal" class="hi">register</a>--%>

<div id="openModal" class="modalDialog">
    <div>   <a href="#close" title="Close" class="close">X</a>

        <div class="registration">
            <form method="post" action="/Registration">

                    <%--<legend>Create account</legend>--%>
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
                    <br/><input type="submit" value= "create account"><br/>

            </form>
        </div>

        <br/> <br/> <br/>


            <ul>
                <br/><li><a href="/">cancel</a></li>
            </ul>



    </div>
</div>

<%----%>







<%--<div class="registration">--%>
    <%--<form method="post" action="/Registration">--%>
        <%--<fieldset>--%>
            <%--<legend>Create account</legend>--%>
            <%--name<br/>--%>
            <%--<input type="text" name="name"><br/>--%>
            <%--<br/>surname<br/>--%>
            <%--<input type="text" name="surname"><br/>--%>
            <%--<br/>e-mail<br/>--%>
            <%--<input type="text" name="e-mail"><br/>--%>
            <%--<br/>address<br/>--%>
            <%--<input type="text" name="address"><br/>--%>
            <%--<br/>phone<br/>--%>
            <%--<input type="text" name="phone"><br/>--%>
            <%--<br/>username<br/>--%>
            <%--<input type="text" name="username"><br/>--%>
            <%--<br/>password<br/>--%>
            <%--<input type="password" name="password"><br/>--%>
            <%--<br/>access privilege<br/>--%>
            <%--<input type="text" name="access-privilege">--%>
            <%--<br/><input type="submit" value= "Submit"><br/>--%>
        <%--</fieldset>--%>
    <%--</form>--%>
<%--</div>--%>


<%--<br/>--%>
<%--<div class="buttons" style="position:absolute; bottom:10px;">--%>
    <%--<a href="/index.jsp"><button type="button">home</button></a>--%>
    <%--<button type="button">contacts</button>--%>
<%--</div>--%>
</body>
</html>
