package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.AuthorManager;
import com.workfront.internship.booklibrary.business.GenreManager;
import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


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

    @RequestMapping("/")
    public String simpleRequest(HttpServletRequest request) {
        List<Author> authorList = authorManager.viewAllAuthors();
        request.getSession().setAttribute("authors", authorList);

        List<Genre> genreList = genreManager.viewAll();
        request.getSession().setAttribute("genres", genreList);

        return "home";
    }

    @RequestMapping("/login")
    public String getSigninPage(){
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
        return "User";
    }

    @RequestMapping("/signout")
    public String signoutRequest(HttpServletRequest request){
        request.getSession().setAttribute("user", null);

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
        String accessPrivilege=request.getParameter("access-privilege");

        User user = new User().setName(name).setSurname(surname).setUsername(username).setPassword(password).setAddress(address).seteMail(email).setPhone(phone).setAccessPrivilege(accessPrivilege);

        int id = 0;
        try {
            id = userManager.register(user);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return "ErrorPage";
        }
        if(id == 0){
            String errorString = "User with this e-mail already exists";
            request.setAttribute("errorString", errorString);
            return "Registration";
        }

        request.getSession().setAttribute("user", user);
        return "User";
    }

//    @RequestMapping("/author")
//    public String goToAuthorPage(HttpServletRequest request){
////        Author author = (Author) request.getAttribute("author");
////        request.setAttribute("name", author.getName());
////        request.setAttribute("surname", author.getSurname());
//
//        return "author";
//    }



//    @RequestMapping(value = "/save", method = RequestMethod.GET)
//    public String simpleRequest(@RequestAttribute("id") String id, Model model) {
//        model.addAttribute("message", "Simple Message");
//
//        User user = userManager.findUserByUserName("sonamikayelyan");
//        model.addAttribute("user", user);
//        return "simpleRequest";
//    }


}
