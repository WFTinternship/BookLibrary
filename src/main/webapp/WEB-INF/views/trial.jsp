<%@ page import="com.workfront.internship.booklibrary.common.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.workfront.internship.booklibrary.common.Genre" %><%--
  Created by IntelliJ IDEA.
  User: Sona Mikayelyan
  Date: 9/21/2016
  Time: 7:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>trial</title>
</head>
<body>
    <%--<input type="button" id="showBooks" value="show books">--%>
    <button type="button" id="showBooks">Show all books</button>



    <div id="showBooksContent" class="showBooks">

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
        <form action="/showBook">
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
                    <td class="not_editable"><input type="checkbox"><input type="hidden" name="bookID" value="<%=books.get(i).getId()%>"/></td>
                    <td><%out.print(books.get(i).getTitle());%></td>
                    <td><%int vol = books.get(i).getVolume(); if(vol != 0) {out.print(vol);}%></td>
                    <td><%out.print(books.get(i).getBookAbstract());%></td>

                    <%--<td id="chooseGenre" class="not_editable"><select name="genre" class="select" disabled>--%>
                        <%--<%--%>
                            <%--List<Genre> genres = (List<Genre>)request.getAttribute("genres"); // "genres"-y AdministratorController-i /Administrator-i mijits e--%>
                            <%--if(!genreList.isEmpty()){--%>
                                <%--for(Genre genre: genreList){ %>--%>
                        <%--<option value="<%=genre.getId()%>" <%=(genre.getId() == books.get(i).getGenre().getId() ? "selected='selected'" : "")%>><%=genre.getGenre()%></option>--%>
                        <%--<%   }--%>
                        <%--}--%>
                        <%--%>--%>
                    <%--</select></td>--%>

                    <td class="not_editable"><%out.print(books.get(i).getGenre().getGenre());%></td>
                    <td><%out.print(books.get(i).getLanguage());%></td>
                    <td><%out.print(books.get(i).getCount());%></td>
                    <td><%out.print(books.get(i).getEditionYear());%></td>
                    <td><%out.print(books.get(i).getPages());%></td>
                    <td><%out.print(books.get(i).getCountryOfEdition());%></td>
                    <td class="not_editable"><button class='pickBook'>pick</button></td>
                    <td class="not_editable"><button class='pendForBook'>pend</button></td>
                </tr>
                </tbody >
                <%}%>
            </table>
        </form>
    </div>




    <script>
        $(document).ready(function () {
            $('input:checkbox').click(function(){
                var tr = $(this).parents('tr');
                if ($(this).prop('checked')) {
                    tr.data('selected', true);
                } else {
                    tr.data('selected', false);
                }

//                if (tr.data('selected')) {
//                    tr.find('td:not(.not_editable)').prop('contenteditable', true);
//                    tr.find('select[name="genre"]').prop('disabled', false);
//                } else{
//                    tr.find('td:not(.not_editable)').prop('contenteditable', false);
//                    tr.find('select[name="genre"]').prop('disabled', true);
//                }

            });

            $('button.pick').click(function(event) {
                event.preventDefault();
                var tr = $(this).parents('tr');
                if (!tr.data('selected')) {
                    return false;
                }

                var bookID = tr.find('input[name="bookID"]').val();
//                var title = tr.find('td').eq(1).text();
//                var volume = tr.find('td').eq(2).text();
//                if(volume==""){volume = 0;}
//                var bookAbstract = tr.find('td').eq(3).text();
//                var genre = tr.find('td').eq(4).find('select[name="genre"]').val();
//                var language = tr.find('td').eq(5).text();
                var count = tr.find('td').eq(6).text();
//                var editionYear = tr.find('td').eq(7).text();
//                var pages = tr.find('td').eq(8).text();
//                var countryOfEdition = tr.find('td').eq(9).text();

                $.ajax('/editBook', {
                    method: 'POST',
                    data: {
                        bookId: bookID,
                        title: title,
                        volume: volume,
                        bookAbstract: bookAbstract,
                        genre: genre,
                        language: language,
                        count: count,
                        editionYear: editionYear,
                        pages: pages,
                        countryOfEdition: countryOfEdition
                    },
                    success: function (responseData) {
                        if (responseData.success) {
                            tr.find('input:checkbox').trigger('click');
                        }
                    }
                })

            });

            $('button.delete').click(function (event) {
                event.preventDefault();
                var tr = $(this).parents('tr');
                if (!tr.data('selected')) {
                    return false;
                }

                var bookID = tr.find('input[name="bookID"]').val();

                $.ajax('/deleteBook', {
                    method: 'POST',
                    data: {
                        bookId: bookID
                    },
                    success: function (responseData) {
                        if (responseData.success) {
                            tr.remove();
                        }
                    },
                    error: function () {
                        tr.find('input:checkbox').trigger('click');
                    }
                });
            });

        });

    </script>

</body>
</html>
