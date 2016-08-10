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
import java.security.NoSuchAlgorithmException;

/**
 * Created by ${Sona} on 8/10/2016.
 */
public class RegistrationServlet extends HttpServlet{
    private UserManager userManager;
    private DataSource dataSource;

    public RegistrationServlet() throws Exception {
        super();
        dataSource = DataSource.getInstance();
        userManager = new UserManagerImpl(dataSource);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        String name=request.getParameter("name");
        String surname=request.getParameter("surname");
        String email=request.getParameter("e-mail");
        String address=request.getParameter("address");
        String phone=request.getParameter("phone");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String accessPrivilege=request.getParameter("access-privilege");

        User user = new User().setName(name).setSurname(surname).setUsername(username).setPassword(password).setAddress(address).seteMail(email).setPhone(phone).setAccessPrivilege(accessPrivilege);

        try {
            int id = userManager.register(user);
            request.setAttribute("user", user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/MainPage.jsp");
        dispatcher.forward(request, response);
    }
}
