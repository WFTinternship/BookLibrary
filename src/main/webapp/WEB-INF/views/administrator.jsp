<%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 9/9/2016
  Time: 6:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin</title>
</head>
<body>
    <ul>
        <li><a href="/addAuthor" id="addAuthor" style="text-decoration: none">add author</a></li>
        <li><a href="/addBook" id = "addBook" style="text-decoration: none">add book</a></li>
        <li><a href="/addGenre" id = "addGenre" style="text-decoration: none">add genre</a></li>
        <li><a href="/addMediaType" id = "addMediaType" style="text-decoration: none">add media type</a></li>
        <li><a href="/addMedia" id = "addMedia" style="text-decoration: none">add media</a></li>
    </ul>

    <div class="addAuthor">
        <form method="get" action="/addAuthor">
            name:<br/>
            <input type="text" name="name" required><br/><br/>
            surname:<br/>
            <input type="text" name="surname" required><br/><br/>
            email:<br/>
            <input type="email" name="email"><br/><br/>
            webPage:<br/>
            <input type="url" name="web-page"><br/><br/>
            biography:<br/>
            <input type="text" name="biography"><br/>
            <br/><input type="submit" value= "add author">
            <input type="reset" value = "cancel"><br/>
        </form>
    </div>
</body>
</html>
