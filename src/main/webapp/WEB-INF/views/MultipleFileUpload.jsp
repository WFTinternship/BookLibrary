<%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 9/29/2016
  Time: 10:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Multiple File Request Page</title>
</head>
<body>
    <form method="POST" action="uploadMultipleFile" enctype="multipart/form-data">
        File1 to upload: <input type="file" name="file">

        <%--Name1: <input type="text" name="name">--%>


        File2 to upload: <input type="file" name="file">

        <%--Name2: <input type="text" name="name">--%>


        <input type="submit" value="Upload"> Press here to upload the file!
    </form>
</body>
</html>
