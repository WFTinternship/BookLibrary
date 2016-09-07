package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.AuthorManager;
import com.workfront.internship.booklibrary.business.BookManager;
import com.workfront.internship.booklibrary.business.GenreManager;
import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.common.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
    private GenreManager genreManager;

    @Autowired
    private AuthorManager authorManager;

    @RequestMapping("/showBooks")
    public String showAllBooks(HttpServletRequest request){
        List<Book> bookList = bookManager.viewAll();
        request.getSession().setAttribute("books", bookList);

        return "User";
    }
}
