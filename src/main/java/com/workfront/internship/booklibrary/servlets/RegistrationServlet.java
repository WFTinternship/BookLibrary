package com.workfront.internship.booklibrary.servlets;

import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.spring.BookLibraryApplication;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by ${Sona} on 8/10/2016.
 */
public class RegistrationServlet extends HttpServlet{
    private UserManager userManager;
    private DataSource dataSource;

//    public RegistrationServlet() throws Exception {
//        super();
//        dataSource = DataSource.getInstance();
//        userManager = new UserManagerImpl(dataSource);
//    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init();
        userManager = BookLibraryApplication.getApplicationContext(servletConfig.getServletContext()).getBean(UserManager.class);
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

        int id = 0;
        try {
            id = userManager.register(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if(id == 0){
            String errorString = "User with this e-mail already exists";
            response.sendRedirect("/jsp/Registration.jsp");
        }
        request.setAttribute("user", user);

//        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
//        dispatcher.forward(request, response);

        response.sendRedirect("/jsp/User.jsp");
    }
}
