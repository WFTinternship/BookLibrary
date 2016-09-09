package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.*;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ${Sona} on 9/9/2016.
 */
@Controller
public class AdministratorController {

    @Autowired
    private AuthorManager authorManager;
    @Autowired
    private GenreManager genreManager;
    @Autowired
    private MediaTypeManager mediaTypeManager;
    @Autowired
    private MediaManager mediaManager;
    @Autowired
    private BookManager bookManager;

    @RequestMapping("/addAuthor")
    public String addAuthor(HttpServletRequest request){
        Author author = new Author();

        String name=request.getParameter("name");
        String surname=request.getParameter("surname");
        String email=request.getParameter("email");
        String webPage=request.getParameter("web-page");
        String biography=request.getParameter("biography:e");

        author.setName(name);
        author.setSurname(name);
        author.seteMail(name);
        author.setWebPage(name);
        author.setBiography(name);

        authorManager.uploadAuthorInfo(author);

        if(author==null) {
            return "addAuthor";
        }

        request.getSession().setAttribute("author", author);
        return "administrator";
    }

    @RequestMapping("/addBook")
    public String addBook(HttpServletRequest request){
        return "";
    }

    @RequestMapping("/addGenre")
    public String addGenre(HttpServletRequest request){
        return "";
    }

    @RequestMapping("/addMediaType")
    public String addMediaType(HttpServletRequest request){
        return "";
    }

    @RequestMapping("/addMedia")
    public String addMedia(HttpServletRequest request){
        return "";
    }
}
