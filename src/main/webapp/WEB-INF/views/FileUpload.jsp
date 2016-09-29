<%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 9/29/2016
  Time: 10:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File Upload</title>
</head>
<body>
    <form method="POST" action="uploadFile" enctype="multipart/form-data">
        File to upload: <input id="fileNameId" type="file" name="file">

        <%--Name: <input type="text" name="name">--%>

        <input type="submit" value="Upload"> Press here to upload the file!
    </form>

</body>
</html>
