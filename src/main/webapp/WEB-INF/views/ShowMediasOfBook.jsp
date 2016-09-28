<%@ page import="com.workfront.internship.booklibrary.common.Book" %>
<%@ page import="com.workfront.internship.booklibrary.common.Media" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 9/28/2016
  Time: 4:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin</title>
    <script src="<c:url value="/resources/js/jquery-3.1.0.js" />"></script>
    <script src="<c:url value="/resources/js/admin.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/admin.css"/>">
</head>
<body>
<div id="showAuthorsOfBook">
    Medias of
    <% Book book = (Book)request.getAttribute("book");
        out.print(" " + book.getTitle() + ":");
    %><br/><br/>
    <%
        List<Media> medias = (List<Media>)request.getAttribute("view medias");
        for(Media media : medias){
    %>
    <a href="#" ><%=media.getLink() + "  " + media.getType()%></a><br/>
    <%}%>
</div>
</body>
</html>
