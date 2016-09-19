<%@ page import="com.workfront.internship.booklibrary.business.GenreManager" %>
<%@ page import="com.workfront.internship.booklibrary.business.GenreManagerImpl" %>
<%@ page import="com.workfront.internship.booklibrary.common.Genre" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.workfront.internship.booklibrary.common.Author" %>
<%@ page import="com.workfront.internship.booklibrary.common.Book" %>
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

<input type="button" id="addAuthorToBook" value="add author to book">

<input type="button" id="viewAuthorsOfBook" value="view all authors of the book">

<input type="button" id="addMediaType" value="add media type">

<input type="button" id="addMediaToBook" value="add media to book">

<input type="button" id="showBooks" value="show books"

<%--<input type="button" id="editBook" value="edit book" formaction="/editBook">--%>


<br/><br/>
<div id='addAuthorContent' class="addAuthor">
    <form method="get" action="/addAuthor">
        name:<br/>
        <input type="text" name="name" required><br/><br/>
        surname:<br/>
        <input type="text" name="surname" required><br/><br/>
        author birth year:<br/>
        <input type="text" name="authorBirthYear" required><br/><br/>
        author birth city:<br/>
        <input type="text" name="authorBirthCity" required><br/><br/>
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
        author birth year:<br/>
        <input type="text" name="authorBirthYear" required><br/><br/>
        author birth city:<br/>
        <input type="text" name="authorBirthCity" required><br/><br/>
        email:<br/>
        <input type="email" name="email"><br/><br/>
        webPage:<br/>
        <input type="url" name="web-page"><br/><br/>
        biography:<br/>
        <input type="text" name="biography"><br/><br/>

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
        <input type="text" name="countryOfEdition" required><br/>


        <br/><input type="submit" value="add book">
        <input type="reset" value="cancel"><br/>
    </form>
</div>


<div id ='addAuthorToBookContent'>
    <form method="get" action="/addAuthorToBook">
        book:<br/>
        <select id="book" name="book">
            <%
                List<Book> bookList = (List<Book>)request.getAttribute("books"); // "books"-y ApplicationController-i /Administrator-i mijits e
                if(!bookList.isEmpty()){
                    for(Book book : bookList){ %>
            <option value="<%=book.getId()%>"><%=book.getTitle()%></option>
            <%   }
            }
            %>
        </select>

        <br/><br/>
        author:<br/>
        <select id="author" name="author">
            <%
                List<Author> authorList = (List<Author>)request.getAttribute("authors");
                if(!authorList.isEmpty()){
                    for(Author author : authorList){ %>
            <option value="<%=author.getId()%>"><%=author.getName() + "  " + author.getSurname() + ",  " + author.getBirthYear() + ",  " + author.getBirthCity()%></option>
            <%   }
            }
            %>
        </select><br/>

        <br/><input type="submit" value="add author to book">
        <input type="reset" value="cancel"><br/>
    </form>
</div>


<div id ='viewBookAuthorsContent'>
    <form method="get" action="/viewAuthorsOfBook">
        book:<br/>
        <select id="bookId" name="bookId">
            <%
                List<Book> bookListForAuthor = (List<Book>)request.getAttribute("books"); // "books"-y ApplicationController-i /Administrator-i mijits e
                if(!bookListForAuthor.isEmpty()){
                    for(Book book : bookListForAuthor){ %>
            <option value="<%=book.getId()%>"><%=book.getTitle()%></option>
            <%   }
            }
            %>
        </select><br/><br/>

        <input type="submit" value="view authors">
        <input type="reset" value="cancel"><br/>
    </form>
</div>

<div id='addMediaTypeContent' class="addMediaType">
    <form method="get" action="/addMediaType">
        media type:<br/>
        <input type="text" name="mediaType" required><br/>
        <br/><input type="submit" value="add media type">
        <input type="reset" value="cancel"><br/>
    </form>
</div>

<div id='addMediaToBookContent' class="addMediaToBook">
    <form method="get" action="/addMediaToBook">
        media:<br/>
        <input type="text" name="media" required><br/>
        <br/><input type="submit" value="add media">
        <%--to book--%>
        <input type="reset" value="cancel"><br/>
    </form>
</div>

<div id="showBooksContent" class="showBooks">
    <%List<Book> books = new ArrayList<>();
        books = (List<Book>)request.getAttribute("books");
    %>
    <table id="showBooksTable">
        <thead>
        <tr>
            <th></th>
            <th>title</th>
            <th>volume</th>
            <th>abstract</th>
            <th>genre</th>
            <th>language</th>
            <th>count</th>
            <th>edition year</th>
            <th>pages</th>
            <th>country of edition</th>
            <th>edit</th>
            <th>delete</th>
        </tr>
        </thead>
        <%for(int i = 0; i < books.size(); i++) {%>
        <tbody>
        <tr>
            <td><input type="checkbox" id="checkbox"></td>
            <td><%out.print(books.get(i).getTitle());%></td>
            <td><%int vol = books.get(i).getVolume(); if(vol != 0) {out.print(vol);}%></td>
            <td><%out.print(books.get(i).getBookAbstract());%></td>
            <td><%out.print(books.get(i).getGenre().getGenre());%></td>
            <td><%out.print(books.get(i).getLanguage());%></td>
            <td><%out.print(books.get(i).getCount());%></td>
            <td><%out.print(books.get(i).getEditionYear());%></td>
            <td><%out.print(books.get(i).getPages());%></td>
            <td><%out.print(books.get(i).getCountryOfEdition());%></td>
            <td><button class='edit' onclick="myEditFunction()">edit</button></td>
            <td><button class='delete'>delete</button></td>
        </tr>
        </tbody >
        <%}%>
    </table>
</div>

<%--<script>--%>
    <%--function myEditFunction(){--%>
        <%--if(document.getElementById('checkbox').checked){--%>

        <%--}--%>
    <%--}--%>

    <%--$(document).ready(function () {--%>
        <%--$('#checkbox').click(function(){--%>
            <%--if ($('#checkbox').attr('checked')) {--%>
                <%--var currentTD = $(this).parents('tr').find('td');--%>
                <%--if ($(this).html() == 'edit') {--%>
                    <%--$.each(currentTD, function () {--%>
                        <%--$(this).prop('contenteditable', true)--%>
                    <%--});--%>
                <%--} else {--%>
                    <%--$.each(currentTD, function () {--%>
                        <%--$(this).prop('contenteditable', false)--%>
                    <%--});--%>
                <%--}--%>

                <%--$(this).html($(this).html() == 'edit' ? 'save' : 'edit');--%>

                <%--if($(this).html()=='delete'){--%>
                    <%--$(this).getAttribute()--%>
                <%--}--%>
            <%--}--%>
        <%--});--%>
    <%--});--%>

<%--//    $(document).ready(function () {--%>
<%--//        $('#checkbox').onclick..click(function () {--%>
<%--//--%>
<%--//--%>
<%--//        });--%>
<%--//--%>
<%--//    });--%>
<%--//    function myDeleteFunction(){}--%>
<%--</script>--%>
</body>
</html>
