<%@ page import="com.workfront.internship.booklibrary.business.GenreManager" %>
<%@ page import="com.workfront.internship.booklibrary.business.GenreManagerImpl" %>
<%@ page import="com.workfront.internship.booklibrary.dao.DataSource" %>
<%@ page import="com.workfront.internship.booklibrary.common.Genre" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Main page</title>
    <link rel="stylesheet" type="text/css" href="/css/main_page.css">
  </head>
  <body>
    <a href="https://www.workfront.com/" target="_blank">
      <img src="/image/workfront_lazure.png" id="logo" alt="Workfront logo" title="go to Workfront homepage" align="left" style="border-radius: 50%;">
    </a>

    <div id="headerText">
      <h1>Welcome to Workfront<br/>Book Library</h1>
    </div>

<!-- comment
    <div class="buttons" style="float: right"><br/>
      <button type="button">home</button>

      <div class="bookDropdown">
        <button type="button">books</button>

        <%--<%--%>
          <%--GenreManager genreManager = new GenreManagerImpl(DataSource.getInstance());--%>
          <%--List<Genre> genreList = genreManager.viewAll();--%>
          <%--for(int i= 0; i < genreList.size(); i++){--%>
        <%--%>--%>
        <%--<div id="bookGenreList">--%>
          <%--<a href="#"><%genreList.get(i).getGenre();%></a>--%>
        <%--</div>--%>
        <%--<%}%>--%>


      </div>
      <br/>
    </div>
    <button type="button">authors</button>
    <button type="button">search</button>
    <br/>

    end of comment-->

    <div id="searchHeader">
      <form id="newSearch" method="get" action="#">
        <input type="text" class="textInput" name="q" size="21" maxlength="250" autofocus autocomplete="on" autocapitalize="off" aria-autocomplete="list" aria-expanded="false"><input type="submit" value="search" class="searchButton">
      </form>
      <div class="clear"></div>
    </div>


    <div class="main-functions" style="float:right;"><br/>
      <%GenreManager genreManager = new GenreManagerImpl(DataSource.getInstance());
      List<Genre> genreList = genreManager.viewAll();%>
      <ul>
        <%--style="list-style-type: none;"--%>
        <li ><a class="home" href="/index.jsp">Home</a></li>
          <%--style="display: inline-block"--%>
        <li ><a class="authorList" href="#authors">Authors</a></li>
          <%--style="display: inline-block"--%>
        <li class="book-genres" >
          <%--style="display: inline-block"--%>
          <a href="#" class="dropbtn">Books</a>
          <div class="dropdown-content">
            <%--style="text-decoration: none; "--%>
            <%for(int i= 0; i < genreList.size(); i++){
            %>
              <a href="#"><%out.print(genreList.get(i).getGenre());%></a>
            <%}%>
          </div>
        </li>

        <%--<li ><a class="search" href="#search">Search</a></li>--%>
          <%--style="display: inline-block"--%>
      </ul>
    </div>


    <%--<div class="buttons" style="float: right"><br/>--%>
      <%--<button type="button">home</button>--%>
      <%--<button type="button">books</button>--%>
      <%--<button type="button">authors</button>--%>
      <%--<button type="button">search</button>--%>
      <%--<br/>--%>
    <%--</div>--%>

    <br/>

    <br/>
    <br/> <br/> <br/> <br/> <br/> <br/>
    <div style="float: right" class="buttons">
      <a href="/jsp/SignIn.jsp" ><button name="sign-in">sign-in</button></a><br/>
      <br/><a href="/jsp/Registration.jsp"><button style="background-color: #eff5f5; color: #0086b3; border: none; border-radius: 10px; width: 150px; height: 30px;" name="register">register</button></a>
    </div>

    <br/>

    <div class="floating_media">
      <ul style="list-style-type: none;">
        <li><img src="/image/download1.jpg" alt="changing images" style="position: absolute; top: 0; /*left: 100px;*/"></li>
        <li><img src="/image/download2.jpg" alt="changing images" style="position: absolute; top: 0; left: 200px;"></li>
        <li><img src="/image/download3.jpg" alt="changing images" style="position: absolute; top: 0; left: 400px;"></li>
        <li><img src="/image/download4.jpg" alt="changing images" style="position: absolute; top: 0; left: 600px;"></li>
        <li><img src="/image/download5.jpg" alt="changing images" style="position: absolute; top: 0; left: 800px;"></li>
      </ul>

    </div>

    <br/>


      <%--<div class="dialogueContainer">--%>


    <div class="buttons" style="position:absolute; bottom:10px; display: inline;">
          <a href="/index.jsp"><button id="home" type="button">home</button></a>
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



    <%--<div class="buttons" style="position:absolute; bottom:10px;">--%>
    <%--<a href="/index.jsp" ><button type="button" >home</button></a> &nbsp;--%>
    <%--<button type="button" onclick="myFunction()">contacts</button>--%>

    <%--<script>--%>
    <%--function myFunction(){--%>
      <%--alert("Address: Armenia, 0001, Yerevan\nHyusisain Ave., 1 Building, Office 14\n(Kentron adm. district)\nphone: +374-60-619828");--%>
    <%--}--%>
  <%--</script>--%>


  </body>
</html>
