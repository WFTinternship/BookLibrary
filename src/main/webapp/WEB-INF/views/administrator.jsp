<%@ page import="com.workfront.internship.booklibrary.business.GenreManager" %>
<%@ page import="com.workfront.internship.booklibrary.business.GenreManagerImpl" %>
<%@ page import="com.workfront.internship.booklibrary.common.Genre" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <%--<script type="text/javascript" src="/resources/js/jquery-3.1.0.js"></script>--%>
    <script src="<c:url value="/resources/js/jquery-3.1.0.js" />"></script>
    <script src="<c:url value="/resources/js/admin.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/admin.css"/>">


</head>

<body>

<input type='button' id='addAuthor' value='add author'>

<input type="button" id='addGenre' value='add genre'>

<input type='button' id='addBook' value='add book'>


<div id='addAuthorContent' class="addAuthor">
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
        <br/><input type="submit" value="add author">
        <input type="reset" value="cancel"><br/>
    </form>
</div>

<div id='addGenreContent' class="addGenre">
    <form method="get" action="/addGenre">
        genre:<br/>
        <input type="text" name="genre" required><br/>
        <br/><input type="submit" value="add genre">
        <input type="reset" value="cancel"><br/>
    </form>
</div>

<div id ='addBookContent'>
    <form method="get" action="/addBook">
        genre:<br/>
        <select id="genre" name="genre">
            <%--<%--%>
            <%--List<Genre> genreList = (List<Genre>)request.getAttribute("genres"); // "genres"-y ApplicationController-i /Administrator-i mijits e--%>
            <%--if(!genreList.isEmpty()){--%>
                <%--for(Genre genre: genreList){ %>--%>
            <%--<option value="<%=genre.getGenre()%>"><%=genre.getGenre()%></option>--%>
            <%--<%   }--%>
            <%--}--%>
            <%--%>--%>

            <%
                List<Genre> genreList = (List<Genre>)request.getAttribute("genres"); // "genres"-y ApplicationController-i /Administrator-i mijits e
                if(!genreList.isEmpty()){
                    for(Genre genre: genreList){ %>
            <option value="<%=genre.getId()%>"><%=genre.getGenre()%></option>
            <%   }
            }
            %>
        </select>

        <br/><br/>
        author name:<br/>
        <input type="text" name="authorName" required><br/><br/>
        author surname:<br/>
        <input type="text" name="authorSurname" required><br/><br/>

        title:<br/>
        <input type="text" name="title" required><br/><br/>
        volume:<br/>
        <input type="text" name="volume"><br/><br/>
        abstract:<br/>
        <input type="text" name="abstract" required><br/><br/>
        language:<br/>
        <input type="text" name="language" required><br/><br/>
        count:<br/>
        <input type="text" name="count" required><br/><br/>
        edition year:<br/>
        <input type="text" name="editionYear" required><br/><br/>
        pages:<br/>
        <input type="text" name="pages" required><br/><br/>
        country of edition:<br/>
        <input type="text" name="countryOfEdition" required><br/><br/>


        <br/><input type="submit" value="add book">
        <input type="reset" value="cancel"><br/>
    </form>
</div>


<%--<ul>--%>
    <%--<li><a href="addAuthor" id="addAuthor" style="text-decoration: none">add author</a></li>--%>
    <%--<li><a href="addBook" id="addBook" style="text-decoration: none">add book</a></li>--%>
    <%--<li><a href="addGenre" id="addGenre" style="text-decoration: none">add genre</a></li>--%>
    <%--<li><a href="addMediaType" id="addMediaType" style="text-decoration: none">add media type</a></li>--%>
    <%--<li><a href="addMedia" id="addMedia" style="text-decoration: none">add media</a></li>--%>
<%--</ul>--%>


</body>
</html>
