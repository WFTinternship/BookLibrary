package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.*;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import static com.workfront.internship.booklibrary.controller.ControllerUtil.correctSearchText;


/**
 * Created by ${Sona} on 8/23/2016.
 */

@Controller
public class ApplicationController {
    @Autowired
    private UserManager userManager;

    @Autowired
    private GenreManager genreManager;

    @Autowired
    private AuthorManager authorManager;

    @Autowired
    private BookManager bookManager;

    @Autowired
    private MediaManager mediaManager;


    @RequestMapping("/")
    public String simpleRequest(HttpServletRequest request) {
        List<Author> authorList = authorManager.viewAllAuthors();
        request.getSession().setAttribute("authors", authorList);

        List<Book> bookList = bookManager.viewAll();
        request.setAttribute("books", bookList);

        request.setAttribute("mediaManager", mediaManager);

        List<Genre> genreList = genreManager.viewAll();
        request.getSession().setAttribute("genres", genreList);

        return "home";
    }

    @RequestMapping("/login")
    public String getSigninPage()
    {
        return "SignIn";
    }

    @RequestMapping("/SignIn")
    public String signinRequest(HttpServletRequest request){
        User user;

        String username=request.getParameter("username/email");
        String password=request.getParameter("password");

        try {
            user = userManager.loginWithUsername(username, password);

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return "ErrorPage";
        }
        if(user==null) {
            return "SignIn";
        }

        request.getSession().setAttribute("user", user);
        return "redirect:/User";
    }

    @RequestMapping("/signout")
    public String signoutRequest(HttpServletRequest request){
        request.getSession().setAttribute("user", null);
        request.getSession().invalidate();

        return "redirect:/";
    }

    @RequestMapping("activeUser")
    public String goToActiveUserPage(HttpServletRequest request) throws IOException {
        if(request.getSession().getAttribute("user") != null){
            return "User";
        }

        return "redirect:/";
    }

    @RequestMapping("/register")
    public String getRegistration(){
        return "Registration";
    }

    @RequestMapping("/Registration")
    public String registrationRequest(HttpServletRequest request){
        String name=request.getParameter("name");
        String surname=request.getParameter("surname");
        String email=request.getParameter("e-mail");
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
//        String accessPrivilege=request.getParameter("access-privilege");

        User user = new User().setName(name).setSurname(surname).setUsername(username).setPassword(password).setAddress(address).seteMail(email).setPhone(phone).setAccessPrivilege("user");

        int id = 0;
        try {
            id = userManager.register(user);
            if(id == 0){
                String errorString = "User with this e-mail already exists";
                request.setAttribute("errorString", errorString);
                return "Registration";
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return "ErrorPage";
        }

        request.getSession().setAttribute("user", user);
        return "redirect:/User";
    }

    @RequestMapping("/searchHome")
    public String searchInputHome(HttpServletRequest request){

        List<Book> existingBooks = new ArrayList<>();

        String bookTitle = request.getParameter("q");
//        bookTitle = correctSearchText(bookTitle);
        List<Book> bookList = bookManager.viewAllWithCondition(bookTitle);

        if (!bookTitle.isEmpty()) {
            for (Book book : bookList) {
                if (book.getTitle().contains(bookTitle)) {
                    existingBooks.add(book);
                }
            }
        }

        request.getSession().setAttribute("searchedBooks", existingBooks);
        return "redirect:/";
    }

    @RequestMapping("/ErrorPage")
    public String getErrorPage(){
        return "ErrorPage";
    }
}
