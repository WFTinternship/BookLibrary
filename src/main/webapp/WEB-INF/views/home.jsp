<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.workfront.internship.booklibrary.business.GenreManager" %>
<%@ page import="com.workfront.internship.booklibrary.business.GenreManagerImpl" %>
<%@ page import="java.util.List" %>
<%@ page import="com.workfront.internship.booklibrary.common.*" %>
<%@ page import="com.workfront.internship.booklibrary.business.MediaManagerImpl" %>
<%@ page import="com.workfront.internship.booklibrary.business.MediaManager" %>
<%@ page import="org.springframework.beans.factory.annotation.Autowired" %>
<%@ page import="com.workfront.internship.booklibrary.controller.ApplicationController" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Main page</title>
      <%--<meta http-equiv="Cache-Control" content="no-cache">--%>
      <%--<META HTTP-EQUIV="Pragma" CONTENT="no-cache">--%>
      <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main_page.css"/>">
    <script src="<c:url value="/resources/js/user.js"/>"></script>
  </head>
  <body>

  <div id="header">
      <a href="https://www.workfront.com/" target="_blank">
        <img src="/resources/image/workfront_lazure.png" id="logo" alt="Workfront logo" title="go to Workfront homepage" align="left" style="border-radius: 50%;">
      </a>

      <div id="headerText">
        <h1>Welcome to Workfront<br/>Book Library</h1>
      </div>
<%if(request.getSession().getAttribute("user") == null ){%>
      <ul style="float: right">
        <li style="padding: 0px 16px"><a href="/login" id="login"  style="text-decoration: none">sign-in</a></li>
        <li style="padding: 0px 16px"><a href="/register" name="register"  style="text-decoration: none">register</a></li>
      </ul>


  <%}else{
  %>
  <ul style="float:right">
      <li style="padding: 0px 20px"><a href="/activeUser" id="activeUser"  style="text-decoration: none"><%User user = (User)request.getSession().getAttribute("user");
          out.print(user.getName());%></a></li>
      <li style="padding: 0px 20px"><a href="/signout" name="signout"  style="text-decoration: none">sign out</a></li>
  </ul>
  <%}%>
  </div>

    <div id="searchHeaderHome">
      <form id="newSearchHome" method="get" action="/searchHome">
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
            </tr>
            </tbody >
            <%}%>
        </table>
        <%
            }
        %>
      <div class="clear"></div>
    </div>


    <div class="main-functions" style="float:right;"><br/>
      <ul>
        <li class="authorList" style="padding: 14px 20px">
          <a href="/author" class="dropbtn" style="text-decoration: none">authors</a>
            <div class="dropdown-content">
                <%
                    List<Author> authors = (List<Author>)request.getSession().getAttribute("authors");
                    for(Author author : authors){
                %>
                <a href="/author"><%out.print(author.getName() + " " + author.getSurname());%></a>
                <%}%>
            </div>
        </li>

        <li class="book-genres" style="padding: 14px 30px">
          <a href="#" class="dropbtn"  style="text-decoration: none">genres</a>
          <div class="dropdown-content">
            <%
              List<Genre> genres = (List<Genre>)request.getSession().getAttribute("genres");
              for(Genre genre : genres){
            %>
              <a href="#"><%out.print(genre.getGenre());%></a>
            <%}%>
          </div>
        </li>
      </ul>
    </div>


    <br/>

    <br/>
    <br/> <br/> <br/> <br/> <br/> <br/>

    <br/>

    <div class="floating_media">
      <ul style="list-style-type: none;">
          <%
              List<Book> bookListMedia = (List<Book>)request.getAttribute("books");
              MediaManager mediaManager = (MediaManager)request.getAttribute("mediaManager");
              int i = 0;
              if (bookListMedia != null && !bookListMedia.isEmpty()) {
                  for(Book book : bookListMedia){
                      List<Media> medialistb = mediaManager.viewAllMediaByBookId(book.getId());
                      for(Media media : medialistb){
                          if(i < 1500){
                          String path = media.getLink();
                          path = "/resources/upload/" + path.substring(path.lastIndexOf(File.separator));
          %>
          <li><img src="<%=path%>" alt="changing images" style="position: absolute; top: 0; left: <%=i%>"></li>
          <% i += 230;}}}}%>


        <%--<li><img src="/resources/upload/" alt="changing images" style="position: absolute; top: 0; /*left: 100px;*/"></li>--%>
        <%--<li><img src="/resources/image/download2.jpg" alt="changing images" style="position: absolute; top: 0; left: 200px;"></li>--%>
        <%--<li><img src="/resources/image/download3.jpg" alt="changing images" style="position: absolute; top: 0; left: 400px;"></li>--%>
        <%--<li><img src="/resources/image/download4.jpg" alt="changing images" style="position: absolute; top: 0; left: 600px;"></li>--%>
        <%--<li><img src="/resources/image/download5.jpg" alt="changing images" style="position: absolute; top: 0; left: 800px;"></li>--%>
      </ul>
    </div>

    <br/>


    <div class="buttons" style="position:absolute; bottom:10px; display: inline;">
          <%--<a href="/"><button id="home" type="button">home</button></a>--%>
          <%--&nbsp;--%>

        <div class="dialogueContainer">
          <button id="contacts" type="button">contacts</button>
        <div id="dialogue" title="Basic dialogue">
          <p>Address: Armenia, 0001, Yerevan<br/>Hyusisayin Ave., 1 Building, Office 14<br/>(Kentron adm. district)<br/>phone: +374-60-619828</p>
          <p id="WF">Workfront Armenia</p>
        </div>
          <div class="clear"></div>
      </div>
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

  </body>
</html>
