<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.workfront.internship.booklibrary.business.GenreManager" %>
<%@ page import="com.workfront.internship.booklibrary.business.GenreManagerImpl" %>
<%@ page import="com.workfront.internship.booklibrary.common.Genre" %>
<%@ page import="java.util.List" %>
<%@ page import="com.workfront.internship.booklibrary.common.Author" %>
<%@ page import="com.workfront.internship.booklibrary.common.User" %>
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

    <div id="searchHeader">
      <form id="newSearch" method="get" action="#">
        <input type="text" class="textInput" name="q" size="21" maxlength="250" autofocus autocomplete="on" autocapitalize="off" aria-autocomplete="list" aria-expanded="false"><input type="submit" value="search" class="searchButton">
      </form>
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
          <a href="#" class="dropbtn"  style="text-decoration: none">books</a>
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
        <li><img src="/resources/image/download1.jpg" alt="changing images" style="position: absolute; top: 0; /*left: 100px;*/"></li>
        <li><img src="/resources/image/download2.jpg" alt="changing images" style="position: absolute; top: 0; left: 200px;"></li>
        <li><img src="/resources/image/download3.jpg" alt="changing images" style="position: absolute; top: 0; left: 400px;"></li>
        <li><img src="/resources/image/download4.jpg" alt="changing images" style="position: absolute; top: 0; left: 600px;"></li>
        <li><img src="/resources/image/download5.jpg" alt="changing images" style="position: absolute; top: 0; left: 800px;"></li>
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

  </body>
</html>
