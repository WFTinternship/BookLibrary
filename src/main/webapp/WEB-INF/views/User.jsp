<%@ page import="com.workfront.internship.booklibrary.common.User" %>
<%@ page import="com.workfront.internship.booklibrary.common.Book" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Workfront
  Date: 8/10/2016
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User page</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/user.css"/>">
</head>

<body>

        <p> <%User user = (User)request.getSession().getAttribute("user");
        out.print("hi " + user.getName());
        %></p>

        <div id="searchHeader">
            <form id="newSearch" method="get" action="#">
                <input type="text" class="textInput" name="q" size="21" maxlength="250" autofocus autocomplete="on" autocapitalize="off" aria-autocomplete="list" aria-expanded="false"><input type="submit" value="search" class="searchButton">
            </form>
            <div class="clear"></div>
        </div>

        <a href="/signout"><button type="button" >Sign out</button></a>


    <div class="buttons" style="position:absolute; bottom:10px; display: inline;">
        <a href="/"><button id="home" type="button">home</button></a>
    </div>
        <%--&nbsp;--%>

    <div>
        <form method="get" action="/showBooks">
            <button type="button">Show all books</button>
        </form>

        <table>
            <thead>
                <tr>
                    <th>title</th>
                    <th>author</th>
                    <th>genre</th>
                    <th>language</th>
                    <th>publication year</th>
                    <th>number of pages</th>
                    <th>available count</th>
                </tr>
            </thead>
            <tbody>
            <%--<% List<Book> bookList = (List<Book>)request.getSession().getAttribute("books");--%>
                <%--for(Book book : bookList){%>--%>
                <%--<tr>--%>
                    <%--<td><%out.print(book.getTitle());%></td>--%>
                    <%--<td><%out.print(book.getAuthors());%></td>--%>
                    <%--<td><%out.print(book.getGenre());%></td>--%>
                    <%--<td><%out.print(book.getLanguage());%></td>--%>
                    <%--<td><%out.print(book.getEditionYear());%></td>--%>
                    <%--<td><%out.print(book.getPages());%></td>--%>
                    <%--<td><%out.print(book.getCount());%></td>--%>
                <%--</tr>--%>
            <%--<%}%>--%>
            </tbody>
        </table>
    </div>


</body>
</html>
