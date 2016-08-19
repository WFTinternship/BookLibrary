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
 * Created by ${Sona} on 8/9/2016.
 */
public class LoginServlet extends HttpServlet{
    private UserManager userManager;
    private DataSource dataSource;

//    public LoginServlet() throws Exception {
//        super();
//        dataSource = DataSource.getInstance();
//        userManager = new UserManagerImpl(dataSource);
//    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init();
        userManager = BookLibraryApplication.getApplicationContext(servletConfig.getServletContext()).getBean(UserManager.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username=request.getParameter("username/email");
        String password=request.getParameter("password");

        try {
            User user = userManager.loginWithUsername(username, password);
            request.setAttribute("user", user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

//      ays dzevovrequesty hishvum e, yev jsp-uminch-vor objekt new aneluc khaskana
//      RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/jsp/User.jsp");
//        dispatcher.forward(request, response);

        //ays dzevov requesty korum e
        response.sendRedirect("/jsp/User.jsp");
    }


/**
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    Boolean hasError = false;
    String errorString = null;
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    User user = new User();

    user = userManager.login(username, password);
    if (user == null) {

            errorString = "Username or password invalid";


            // If error, forward to /signin.jsp

            user = new User();
            user.setUsername(username);
            user.setPassword(password);


            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);


            // Forward to/signin.jsp
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/signin.jsp");

            dispatcher.forward(request, response);
        } else {
            request.getSession().setAttribute("user", user);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
        }
    }
 */
}
