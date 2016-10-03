package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.*;
import com.workfront.internship.booklibrary.common.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.workfront.internship.booklibrary.controller.ControllerUtil.getDateTime;
import static com.workfront.internship.booklibrary.controller.ControllerUtil.getIntegerFromString;

/**
 * Created by ${Sona} on 9/7/2016.
 */
@Controller
public class UserController {
    @Autowired
    private UserManager userManager;

    @Autowired
    private BookManager bookManager;

    @Autowired
    private PickBookManager pickBookManager;

    @Autowired
    private PendingsManager pendingsManager;

    @Autowired
    private GenreManager genreManager;

    @Autowired
    private AuthorManager authorManager;

    @RequestMapping("/showBooksDetails")
    public String showBookDetailsInUserPage(HttpServletRequest request){
        List<Book> bookList = bookManager.viewAll();
        request.setAttribute("books", bookList);
        return "redirect:/User";
    }

    @RequestMapping(value="/pickBook", method = RequestMethod.POST)
    public String pickBookMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String result = null;

        int bookId = getIntegerFromString(request.getParameter("bookId"));
        int userId = getIntegerFromString(request.getParameter("userId"));

        PrintWriter writer = response.getWriter();
        try{
            result = pickBookManager.addPickOrPend(bookId, userId);
        }catch (Exception e) {
            String errorString = "No book available at this moment.\nYou can pend for it now";
            request.setAttribute("errorString", errorString);
            return "redirect:/User";
        }

        writer.write(result);

        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        writer.close();

        return "redirect:/User";
    }

    @RequestMapping("/search")
    public String searchInput(HttpServletRequest request){
        List<Book> bookList = bookManager.viewAll();
        List<Book> existingBooks = new ArrayList<>();

        String bookTitle = request.getParameter("q");

//        if (!bookTitle.isEmpty()) {
        if(ControllerUtil.isNotEmpty(bookTitle.trim())){
            for (Book book : bookList) {
                if (book.getTitle().contains(bookTitle)) {
                    existingBooks.add(book);
                }
            }
        }

        request.getSession().setAttribute("searchedBooks", existingBooks);
        return "redirect:/User";
    }


    @RequestMapping("/User")
    public String getUserPage(HttpServletRequest request){
        List<Genre> genreList = genreManager.viewAll();
        List<Author> authorList = authorManager.viewAllAuthors();
        List<Book> bookList = bookManager.viewAll();

        request.setAttribute("genres", genreList);
        request.setAttribute("authors", authorList);
        request.setAttribute("books", bookList);

        return "User";
    }
}
