<%@ page import="com.workfront.internship.booklibrary.common.User" %>
<%@ page import="com.workfront.internship.booklibrary.common.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="com.workfront.internship.booklibrary.common.Genre" %>
<%@ page import="java.util.ArrayList" %>
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

    <script src="<c:url value="/resources/js/jquery-3.1.0.js" />"></script>
    <script src="<c:url value="/resources/js/user.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/user.css"/>">
</head>

<body>

    <div>
        <p> <%User user = (User)request.getSession().getAttribute("user");
            out.print("hi " + user.getName());
        %></p>
        <a href="/signout"><button type="button" >Sign out</button></a>

        <%--<span id="userId"><%user.getId();%></span>--%>
        <input type="hidden" name="userID" id="userId" value="<%=user.getId()%>"/>

        <br/><br/>
        <div id="searchHeader">
            <form id="newSearch" method="get" action="/search">
                <input type="text" class="textInput" name="q" size="21" maxlength="250" autofocus autocomplete="on" autocapitalize="off" aria-autocomplete="list" aria-expanded="false"><input type="submit" value="search" class="searchButton">
            </form>
            <%
                List<Book> bookList = (List<Book>)request.getSession().getAttribute("searchedBooks");
                if (bookList != null && !bookList.isEmpty()) {
            %>
                    <table class="showBooksTable">
                        <thead>
                        <tr>
                            <th>title</th>
                            <th>volume</th>
                            <th>abstract</th>
                            <th>genre</th>
                            <th>language</th>
                            <th>count</th>
                            <th>edition year</th>
                            <th>pages</th>
                            <th>country of edition</th>
                            <th>pick book</th>
                        </tr>
                        </thead>
                        <%for(int i = 0; i < bookList.size(); i++) {%>
                        <tbody>
                        <tr>
                            <td><input type="hidden" name="bookID" value="<%=bookList.get(i).getId()%>"/><%out.print(bookList.get(i).getTitle());%></td>
                            <td><%int vol = bookList.get(i).getVolume(); if(vol != 0) {out.print(vol);}%></td>
                            <td><%out.print(bookList.get(i).getBookAbstract());%></td>

                            <td data-genre-id="<%=bookList.get(i).getGenre().getId()%>"><%out.print(bookList.get(i).getGenre().getGenre());%></td>
                            <td><%out.print(bookList.get(i).getLanguage());%></td>
                            <td><%out.print(bookList.get(i).getCount());%></td>
                            <td><%out.print(bookList.get(i).getEditionYear());%></td>
                            <td><%out.print(bookList.get(i).getPages());%></td>
                            <td><%out.print(bookList.get(i).getCountryOfEdition());%></td>
                            <td class="not_editable"><button class='pickBook'>pick</button></td>
                        </tr>
                        </tbody >
                        <%}%>
                    </table>
            <%
                }
            %>
            <div class="clear"></div>
        </div>

        <div class="buttons" style="position:absolute; bottom:10px; display: inline;">
            <a href="/"><button id="home" type="button">home</button></a>
        </div>
            <%--&nbsp;--%>
    </div>


    <br/><br/>
    <input type="button" id="showBooksButton" value="show books">
    <br/><br/>

    <div id="showBooksContent" class="showBooks">

        <form action="/showBooksDetails">
            <%List<Book> books = (List<Book>)request.getAttribute("books");%>
            <table class="showBooksTable">
                <thead>
                <tr>
                    <th>title</th>
                    <th>volume</th>
                    <th>abstract</th>
                    <th>genre</th>
                    <th>language</th>
                    <th>count</th>
                    <th>edition year</th>
                    <th>pages</th>
                    <th>country of edition</th>
                    <th>pick book</th>
                </tr>
                </thead>
                <%for(int i = 0; i < books.size(); i++) {%>
                <tbody>
                <tr>
                    <td><input type="hidden" name="bookID" value="<%=books.get(i).getId()%>"/><%out.print(books.get(i).getTitle());%></td>
                    <td><%int vol = books.get(i).getVolume(); if(vol != 0) {out.print(vol);}%></td>
                    <td><%out.print(books.get(i).getBookAbstract());%></td>

                    <td data-genre-id="<%=books.get(i).getGenre().getId()%>"><%out.print(books.get(i).getGenre().getGenre());%></td>
                    <td><%out.print(books.get(i).getLanguage());%></td>
                    <td><%out.print(books.get(i).getCount());%></td>
                    <td><%out.print(books.get(i).getEditionYear());%></td>
                    <td><%out.print(books.get(i).getPages());%></td>
                    <td><%out.print(books.get(i).getCountryOfEdition());%></td>
                    <td class="not_editable"><button class='pickBook'>pick</button></td>
                </tr>
                </tbody >
                <%}%>
            </table>
        </form>
    </div>


    <script>
        $(document).ready(function () {
            $('button.pickBook').click(function(event) {
                event.preventDefault();

                if (window.confirm("Do you really want to pick this book?")) {
                    var tr = $(this).parents('tr');
                    var bookID = tr.find('input[name="bookID"]').val();
                    var userID = document.getElementById("userId").value;
                    var count = tr.find('td').eq(5);

                    $.ajax('/pickBook', {
                        method: 'POST',
                        data: {
                            userId: userID,
                            bookId: bookID
                        },
                        success: function (responseData) {
                            if (responseData.success) {
                                count.text(responseData.book.count);
//                                tr.find('input:checkbox').trigger('click');
                            } else {
                                window.alert(responseData.message)
                            }
                        }
                    })
                }
            });
        });

    </script>



    <%--<script>--%>
            <%--$(window).load(function() { //document.ready-ov shat dandax er ashxatum, kaxvum er--%>
                <%--document.getElementById("newSearch").addEventListener("submit", function () {--%>
                    <%--var searchExpression = document.getElementsByClassName("textInput");--%>
        <%----%>
                    <%--$.ajax('/search', {--%>
                        <%--method: 'POST',--%>
                        <%--data: {--%>
                            <%--searchText: searchExpression--%>
                        <%--}--%>
        <%--//            success: function (responseData) {--%>
        <%--//                if (responseData.success) {--%>
        <%--//                    ;--%>
        <%--//                }--%>
        <%--//            }--%>
                    <%--})--%>
                <%--});--%>
            <%--});--%>

    <%--</script>--%>



    <%--<input type="button" id="showBooksButton" value="show books">--%>
    <%--<br/><br/>--%>

    <%--<div id="showBooksContent" class="showBooks">--%>

        <%--<form action="/showBooksDetails">--%>
            <%--<%List<Book> books = (List<Book>)request.getAttribute("books");%>--%>
            <%--<table id="showBooksTable">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th></th>--%>
                    <%--<th>title</th>--%>
                    <%--<th>volume</th>--%>
                    <%--<th>abstract</th>--%>
                    <%--<th>genre</th>--%>
                    <%--<th>language</th>--%>
                    <%--<th>count</th>--%>
                    <%--<th>edition year</th>--%>
                    <%--<th>pages</th>--%>
                    <%--<th>country of edition</th>--%>
                    <%--<th>pick book</th>--%>
                    <%--<th>pend book</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<%for(int i = 0; i < books.size(); i++) {%>--%>
                <%--<tbody>--%>
                <%--<tr>--%>
                    <%--<td class="not_editable"><input type="checkbox"><input type="hidden" name="bookID" value="<%=books.get(i).getId()%>"/></td>--%>
                    <%--<td><%out.print(books.get(i).getTitle());%></td>--%>
                    <%--<td><%int vol = books.get(i).getVolume(); if(vol != 0) {out.print(vol);}%></td>--%>
                    <%--<td><%out.print(books.get(i).getBookAbstract());%></td>--%>

                    <%--<td data-genre-id="<%=books.get(i).getGenre().getId()%>"><%out.print(books.get(i).getGenre().getGenre());%></td>--%>
                    <%--<td><%out.print(books.get(i).getLanguage());%></td>--%>
                    <%--<td><%out.print(books.get(i).getCount());%></td>--%>
                    <%--<td><%out.print(books.get(i).getEditionYear());%></td>--%>
                    <%--<td><%out.print(books.get(i).getPages());%></td>--%>
                    <%--<td><%out.print(books.get(i).getCountryOfEdition());%></td>--%>
                    <%--<td class="not_editable"><button class='pickBook'>pick</button></td>--%>
                    <%--<td class="not_editable"><button class='pendForBook'>pend</button></td>--%>
                <%--</tr>--%>
                <%--</tbody >--%>
                <%--<%}%>--%>
            <%--</table>--%>
        <%--</form>--%>
    <%--</div>    --%>

    <%--<script>--%>
        <%--$(document).ready(function () {--%>
            <%--$('input:checkbox').click(function(){--%>
                <%--var tr = $(this).parents('tr');--%>
                <%--if ($(this).prop('checked')) {--%>
                    <%--tr.data('selected', true);--%>
                <%--} else {--%>
                    <%--tr.data('selected', false);--%>
                <%--}--%>
            <%--});--%>

            <%--$('button.pickBook').click(function(event) {--%>
                <%--event.preventDefault();--%>
                <%--var tr = $(this).parents('tr');--%>
                <%--if (!tr.data('selected')) {--%>
                    <%--return false;--%>
                <%--}--%>

                <%--var bookID = tr.find('input[name="bookID"]').val();--%>
                <%--var userID = document.getElementById("userId").value;--%>
                <%--var count = tr.find('td').eq(6);--%>

                <%--$.ajax('/pickBook', {--%>
                    <%--method: 'POST',--%>
                    <%--data: {--%>
                        <%--userId: userID,--%>
                        <%--bookId: bookID--%>
                    <%--},--%>
                    <%--success: function (responseData) {--%>
                        <%--if (responseData.success) {--%>
                            <%--count.text(responseData.book.count);--%>
                            <%--tr.find('input:checkbox').trigger('click');--%>
                        <%--}--%>
                    <%--}--%>
                <%--})--%>
            <%--});--%>

            <%--$('button.pendForBook').click(function (event) {--%>
                <%--event.preventDefault();--%>
                <%--var tr = $(this).parents('tr');--%>
                <%--if (!tr.data('selected')) {--%>
                    <%--return false;--%>
                <%--}--%>

                <%--var bookID = tr.find('input[name="bookID"]').val();--%>
                <%--var userID = document.getElementById("userId").value;--%>

                <%--$.ajax('/pendBook', {--%>
                    <%--method: 'POST',--%>
                    <%--data: {--%>
                        <%--bookId: bookID,--%>
                        <%--userId: userID--%>
                    <%--},--%>
                    <%--success: function (responseData) {--%>
                        <%--if (responseData.success) {--%>
                            <%--tr.find('input:checkbox').trigger('click');--%>
                        <%--}--%>
                    <%--},--%>
                    <%--error: function () {--%>
                        <%--tr.find('input:checkbox').trigger('click');--%>
                    <%--}--%>
                <%--});--%>
            <%--});--%>
        <%--});--%>

    <%--</script>--%>


</body>
</html>
