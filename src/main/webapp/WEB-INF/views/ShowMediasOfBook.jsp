<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.workfront.internship.booklibrary.common.Book" %>
<%@ page import="com.workfront.internship.booklibrary.common.Media" %>
<%@ page import="java.util.List" %>
<%--
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
<div id="showMediasOfBook">
    Medias of
    <% Book book = (Book)request.getAttribute("book");
        out.print(" " + book.getTitle() + ":");
    %><br/><br/>
    <%
        List<Media> medias = (List<Media>)request.getAttribute("view medias");
        for(Media media : medias){
    %>
    <a href="#" ><%=media.getLink()%></a><br/>
    <%out.print("media type:  " + media.getType());%><br/><br/>
    <%}%>
</div>
<br/><br/>

<div id="back">
    <a href="/administrator"><button type="button">back</button></a>
</div>
</body>
</html>
