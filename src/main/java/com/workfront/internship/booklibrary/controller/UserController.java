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




    @RequestMapping("/showBooks")
    public String showBookDetails(HttpServletRequest request){
        List<Book> bookList = bookManager.viewAll();
        request.getSession().setAttribute("books", bookList);
        return "redirect:/User";
    }

    @RequestMapping(value="/pickBook", method = RequestMethod.POST)
    public String pickBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Book book = new Book();
        User user = new User();
        PickBook pickBook = new PickBook();
//        Genre genre = new Genre();
//        String genreIdString = request.getParameter("genre");
//
//        int genreId = Integer.parseInt(genreIdString);
//        genre = genreManager.findGenreByID(genreId);

        int bookId = getIntegerFromString(request.getParameter("bookId"));
        int userId = getIntegerFromString(request.getParameter("userId"));
//        String title = request.getParameter("title");
//        String volumeString = request.getParameter("volume");
//        int volume = getIntegerFromString(volumeString);
//        String bookAbstract = request.getParameter("bookAbstract");
//        String language = request.getParameter("language");
//        int bookCount = Integer.parseInt(request.getParameter("count"));
//        String editionYear = request.getParameter("editionYear");
//        int pages = Integer.parseInt(request.getParameter("pages"));
//        String countryOfEdition = request.getParameter("countryOfEdition");

        book.setId(bookId);
        user.setId(userId);
//        book.setTitle(title);
//        book.setVolume(volume);
//        book.setBookAbstract(bookAbstract);
//        book.setLanguage(language);
//        book.setCount(bookCount);
//        book.setEditionYear(editionYear);
//        book.setPages(pages);
//        book.setCountryOfEdition(countryOfEdition);
//        book.setGenre(genre);

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
        pickBook.setBook(bookManager.findBookByID(bookId));
        pickBook.setUser(userManager.findUserByID(userId));

        try{
//            if(book.getCount()>0)
            pickBookManager.add(pickBook);

        }catch (Exception e) {
            String errorString = "No book available at this moment.\nYou can pend for it now";
            request.getSession().setAttribute("errorString", errorString);
            return "redirect:/User";
//            e.printStackTrace();
//            return "/ErrorPage";
        }

        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write("{\"success\":true}");
        writer.close();

        return "redirect:/User";
    }

    @RequestMapping(value="/pendBook", method = RequestMethod.POST)
    public String pendBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Book book = new Book();
        User user = new User();
        Pending pend = new Pending();

        int bookId = getIntegerFromString(request.getParameter("bookId"));
        int userId = getIntegerFromString(request.getParameter("userId"));

        book.setId(bookId);
        user.setId(userId);

        DateFormat dateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        Date todate1 = cal.getTime();
        String fromdate = dateFormat.format(todate1);
        pend.setPendingDate(Timestamp.valueOf(fromdate));
        pend.setBook(bookManager.findBookByID(bookId));
        pend.setUser(userManager.findUserByID(userId));

        try{
            pendingsManager.add(pend);
        }catch (Exception e) {
            String errorString = "No book available at this moment.";
            request.getSession().setAttribute("errorString", errorString);
            return "ErrorPage";
//            e.printStackTrace();
//            return "/ErrorPage";
        }

        response.setCharacterEncoding("utf8");
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        writer.write("{\"success\":true}");
        writer.close();

//        request.setAttribute("bookId", 0);
        return "redirect:/User";
    }









    @RequestMapping("/User")
    public String getAdminPage(HttpServletRequest request){
        List<Genre> genreList = genreManager.viewAll();
        List<Author> authorList = authorManager.viewAllAuthors();
        List<Book> bookList = bookManager.viewAll();

        request.getSession().setAttribute("genres", genreList);
        request.getSession().setAttribute("authors", authorList);
        request.getSession().setAttribute("books", bookList);

        return "redirect:/User";
    }
}
