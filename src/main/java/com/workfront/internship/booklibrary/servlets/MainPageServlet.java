package com.workfront.internship.booklibrary.servlets;

import com.workfront.internship.booklibrary.business.AuthorManager;
import com.workfront.internship.booklibrary.business.GenreManager;
import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.spring.BookLibraryApplication;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by ${Sona} on 8/10/2016.
 */
public class MainPageServlet extends HttpServlet {
//    private GenreManager genreManager;
//    private AuthorManager authorManager;
//    private DataSource dataSource;
//
//    @Override
//    public void init(ServletConfig servletConfig) throws ServletException {
//        super.init();
//        genreManager = BookLibraryApplication.getApplicationContext(servletConfig.getServletContext()).getBean(GenreManager.class);
//        authorManager = BookLibraryApplication.getApplicationContext(servletConfig.getServletContext()).getBean(AuthorManager.class);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//
//
//        List<Author> authorList = authorManager.viewAllAuthors();
//        request.setAttribute("authors", authorList);
//
//
//        List<Genre> genreList = genreManager.viewAll();
//        request.setAttribute("genres", genreList);
//
//        ays dzevovrequesty hishvum e, yev jsp-uminch-vor objekt new aneluc khaskana
//        request.getRequestDispatcher("home.jsp").forward(request, response);
//
//        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher();
//        dispatcher.forward(request, response);


//    }

}
