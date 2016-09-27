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
        PickBook pickBook = new PickBook();
        Pending pend = new Pending();

        int bookId = getIntegerFromString(request.getParameter("bookId"));
        int userId = getIntegerFromString(request.getParameter("userId"));

        Book book = bookManager.findBookByID(bookId);
        User user = userManager.findUserByID(userId);

        pickBook.setPickingDate(Timestamp.valueOf(getDateTime(0)));
        pickBook.setReturnDate(Timestamp.valueOf(getDateTime(10)));
        pickBook.setBook(book);
        pickBook.setUser(user);

        /* todo if there are pendings for this book then assign the book to the user
         who's been pending for it first. If there are many of them at the same time,
         then choose one of them randomly and inform(send an e-mail) the user that he is given the access to pick the book
         */
        PrintWriter writer = response.getWriter();
        try{
            if(book.getCount() == 0){
                if(pendingsManager.isPended(userId, bookId)){
                    writer.write("{\"success\":false, \"message\":\"You already pend for the book\"}");
                }else if(pickBookManager.isPicked(userId, bookId)) {
                    writer.write("{\"success\":false, \"message\":\"You already picked the book\"}");
                }else {
                    pend.setPendingDate(Timestamp.valueOf(getDateTime(0)));
                    pend.setBook(book);
                    pend.setUser(user);
                    pendingsManager.add(pend);
                    writer.write("{\"success\":false, \"message\":\"The book is not available now. You are set to pend for it.\"}");
                }

            }
            else if(pickBookManager.lastTimePickedSoonerThanNow(userId, bookId)) {
                pickBookManager.add(pickBook);
                writer.write("{\"success\":true, \"book\":{\"count\":" + book.getCount() + "}}");
            } else {
                writer.write("{\"success\":false, \"message\":\"the book is already picked\"}");
            }

        }catch (Exception e) {
            String errorString = "No book available at this moment.\nYou can pend for it now";
            request.setAttribute("errorString", errorString);
            return "redirect:/User";
        }

        request.setAttribute("book", book);
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

        if (!bookTitle.isEmpty()) {
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
