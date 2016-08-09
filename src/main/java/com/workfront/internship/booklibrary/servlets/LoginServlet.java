package com.workfront.internship.booklibrary.servlets;

import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.business.UserManagerImpl;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.DataSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;


/**
 * Created by ${Sona} on 8/9/2016.
 */
public class LoginServlet extends HttpServlet{
    UserManager userManager;
    DataSource dataSource;

    public LoginServlet() throws Exception {
        super();
        dataSource = DataSource.getInstance();
        userManager = new UserManagerImpl(dataSource);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username=request.getParameter("username");
        String password=request.getParameter("password");
        User user = null;
        try {
            user = userManager.loginWithUsername(username, password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/SignIn.jsp");
        dispatcher.forward(request, response);
    }
}
