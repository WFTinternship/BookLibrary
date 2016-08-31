package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.spring.DevelopmentConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ${Sona} on 8/30/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DevelopmentConfig.class)
@ActiveProfiles("Development")
public class UserControllerIntegrationTest {
    @Autowired
    ApplicationController applicationController;

    private HttpServletRequest testRequest;
    private HttpSession testSession;

    @Before
    public void setUp() {
        testRequest = mock(HttpServletRequest.class);
        testSession = mock(HttpSession.class);

        when(testRequest.getSession()).thenReturn(testSession);
        when(testRequest.getParameter("username/email")).thenReturn("sonamikayelyan");
        when(testRequest.getParameter("password")).thenReturn("sonapass");
    }

    @Test
    public void login() {
        String userPage = applicationController.signinRequest(testRequest);
        assertEquals("Wrong page", userPage, "User");
    }

    @Test
    public void signout() {
        String homePage = applicationController.signoutRequest(testRequest);
        assertEquals("Wrong page", homePage, "redirect:/");
    }

//    @Test
//    public void register(){
//        String userPage = applicationController.registrationRequest(testRequest);
//        assertEquals("Wrong page", userPage, "User");
//    }
}
