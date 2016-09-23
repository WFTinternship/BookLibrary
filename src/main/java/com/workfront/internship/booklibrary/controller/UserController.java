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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

//    @RequestMapping("/showBooks")
//    public String showAllBooks(HttpServletRequest request){
//        List<Book> bookList = bookManager.viewAll();
//        request.getSession().setAttribute("books", bookList);
//
//        return "User";
//    }

    @RequestMapping("/showBooksDetails")
    public String showBookDetailsInUserPage(HttpServletRequest request){
        List<Book> bookList = bookManager.viewAll();
        request.setAttribute("books", bookList);
        return "redirect:/User";
    }

    private boolean lastTimePickedSoonerThanNow(int userId, int bookId){
        List<PickBook> pickedBooksOfUser = pickBookManager.viewAllPickedBooksByUser(userId);
        for(PickBook pickedBook : pickedBooksOfUser){
            if(pickedBook.getBook().getId() == bookId){
                DateFormat dateFormat =
                        new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 0);
                Date now = cal.getTime();
                String nowDate = dateFormat.format(now);
                if(pickedBook.getReturnDate().compareTo(Timestamp.valueOf(nowDate)) <= 0){ //yete return  date-y aveli shut e qan stugman pahy
                    return true;
                } else{return false;}
            } //else{return true;} //ays girqy pick chi arac
        }
        return true;
    }

    @RequestMapping(value="/pickBook", method = RequestMethod.POST)
    public String pickBookMethod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PickBook pickBook = new PickBook();

        int bookId = getIntegerFromString(request.getParameter("bookId"));
        int userId = getIntegerFromString(request.getParameter("userId"));

        Book book = bookManager.findBookByID(bookId);
        User user = userManager.findUserByID(userId);

        DateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Date todate1 = cal.getTime();
        String fromdate = dateFormat.format(todate1);
        cal.add(Calendar.DATE, 10);
        Date todate2 = cal.getTime();
        String toDate = dateFormat.format(todate2);
        pickBook.setPickingDate(Timestamp.valueOf(fromdate));
        pickBook.setReturnDate(Timestamp.valueOf(toDate));
        pickBook.setBook(book);
        pickBook.setUser(user);

        /* todo if there are pendings for this book then assign the book to the user
         who's been pending for it first. If there are many of them at the same time,
         then choose one of them randomly and inform(send an e-mail) the user that he is given the access to pick the book
         */
        try{
            if(lastTimePickedSoonerThanNow(userId, bookId)) {
                pickBookManager.add(pickBook);
            }

        }catch (Exception e) {
            String errorString = "No book available at this moment.\nYou can pend for it now";
            request.setAttribute("errorString", errorString);
            return "redirect:/User";
        }

        request.setAttribute("book", book);
        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write("{\"success\":true, \"book\":{\"count\":" + book.getCount() + "}}");
        writer.close();

        return "redirect:/User";
    }

    private boolean isPended(int userId, int bookId){
        List<Pending> pendingList = pendingsManager.viewAllPendingByUser(userId);
        for(Pending pendingBook : pendingList){
            if(pendingBook.getBook().getId() == bookId){
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value="/pendBook", method = RequestMethod.POST)
    public String pendBookMethod(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Pending pend = new Pending();

        int bookId = getIntegerFromString(request.getParameter("bookId"));
        int userId = getIntegerFromString(request.getParameter("userId"));

        Book book = bookManager.findBookByID(bookId);
        User user = userManager.findUserByID(userId);

        DateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Date todate1 = cal.getTime();
        String fromdate = dateFormat.format(todate1);
        pend.setPendingDate(Timestamp.valueOf(fromdate));
        pend.setBook(book);
        pend.setUser(user);

        try{
            if ((!isPended(userId, bookId)) && book.getCount()==0) {
                pendingsManager.add(pend);
            }
        }catch (Exception e) {
            String errorString = "No book available at this moment.";
            request.getSession().setAttribute("errorString", errorString);
            return "ErrorPage";
        }

        request.setAttribute("book", book);

        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write("{\"success\":true}");
        writer.close();

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
