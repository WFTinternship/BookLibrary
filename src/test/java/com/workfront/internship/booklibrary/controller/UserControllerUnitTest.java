package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.common.User;
import org.junit.*;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.InvalidObjectException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by ${Sona} on 8/30/2016.
 */
public class UserControllerUnitTest {
    private static ApplicationController applicationController;

    private UserManager userManager;
    private User testUser;
    private HttpServletRequest testRequest;
    private HttpSession testSession;
    private Model testModel;

    @BeforeClass
    public static void setUpClass() {
        applicationController = new ApplicationController();
    }

    @AfterClass
    public static void tearDownClass() {
        applicationController = null;
    }

    @Before
    public void setUp() {
        //create test user object
        testUser = getRandomUser();

        userManager = mock(UserManager.class);
        Whitebox.setInternalState(applicationController, "userManager", userManager);

//        testRequest = new MockHttpServletRequest();
//        testRequest.addParameter("username/email", "sonamikayelyan");
//        testRequest.addParameter("password", "sonapass");

        testRequest = mock(HttpServletRequest.class);
        testSession = mock(HttpSession.class);
        testModel = mock(Model.class);

        HttpServletResponse response = mock(HttpServletResponse.class);

        when(testRequest.getParameter("username/email")).thenReturn("sonamikayelyan"); // thenReturn(testUser.getUsername())
        when(testRequest.getParameter("password")).thenReturn("sonapass");  //thenReturn(testUser.getPassword())
        when(testRequest.getSession()).thenReturn(testSession);
    }

    @After
    public void tearDown() {
        //delete test user object
        testUser = null;
        userManager = null;
    }

    @Test
    public void login_Success() throws UnsupportedEncodingException, NoSuchAlgorithmException{

        when(userManager.loginWithUsername(anyString(), anyString())).thenReturn(testUser);
        applicationController.signinRequest(testModel, testRequest);
        verify(testSession).setAttribute("user", testUser);
    }

    @Test
    public void login_Fail() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        doThrow(NoSuchAlgorithmException.class).when(userManager).loginWithUsername(anyString(), anyString());

        applicationController.signinRequest(testModel, testRequest);

        verify(testSession, never()).setAttribute(anyString(), anyString());
    }
}
