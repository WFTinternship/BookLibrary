package com.workfront.internship.booklibrary.controller;

import com.workfront.internship.booklibrary.business.*;
import com.workfront.internship.booklibrary.common.Author;
import com.workfront.internship.booklibrary.common.Book;
import com.workfront.internship.booklibrary.common.Genre;
import com.workfront.internship.booklibrary.common.User;
import org.junit.*;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.InvalidObjectException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomAuthor;
import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomGenre;
import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomUser;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by ${Sona} on 8/30/2016.
 */
public class ApplicationControllerUnitTest {
    private static ApplicationController applicationController;

    private UserManager userManager;
    private User testUser;

    private HttpServletRequest testRequest;
    private HttpSession testSession;

    private AuthorManager authorManager;
    private GenreManager genreManager;
    private MediaManager mediaManager;
    private BookManager bookManager;

    private Author testAuthor;
    private Genre testGenre;

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
        testAuthor = getRandomAuthor();
        testGenre = getRandomGenre();

        userManager = mock(UserManager.class);
        Whitebox.setInternalState(applicationController, "userManager", userManager);

        authorManager = mock(AuthorManager.class);
        genreManager = mock(GenreManager.class);
        bookManager = mock(BookManager.class);
        Whitebox.setInternalState(applicationController, "authorManager", authorManager);
        Whitebox.setInternalState(applicationController, "genreManager", genreManager);
        Whitebox.setInternalState(applicationController, "bookManager", bookManager);

        testRequest = mock(HttpServletRequest.class);
        testSession = mock(HttpSession.class);

        when(testRequest.getParameter("username/email")).thenReturn(testUser.getUsername());
        when(testRequest.getParameter("password")).thenReturn(testUser.getPassword());
        when(testRequest.getSession()).thenReturn(testSession);
    }

    @After
    public void tearDown() {
        //delete test user object
        testUser = null;
        userManager = null;
    }

    @Test
    public void simpleRequest_Success() {
        List<Author> authorList = new ArrayList<>();
//        authorList.add(testAuthor);

        List<Genre> genreList = new ArrayList<>();
//        genreList.add(testGenre);

        List<Book> bookList = new ArrayList<>();

        when(authorManager.viewAllAuthors()).thenReturn(authorList);
        when(genreManager.viewAll()).thenReturn(genreList);
        when(bookManager.viewAll()).thenReturn(bookList);

        //testing method
        applicationController.simpleRequest(testRequest);

        verify(testSession).setAttribute("authors", authorList);
        verify(testSession).setAttribute("genres", genreList);
//        verify(testSession).setAttribute("books", bookList);
//        verify(testSession).setAttribute("mediaManager", mediaManager);

    }

    @Test
    public void login_Success() throws UnsupportedEncodingException, NoSuchAlgorithmException{
        when(userManager.loginWithUsername(anyString(), anyString())).thenReturn(testUser);
        String result = applicationController.signinRequest(testRequest);
        verify(testSession).setAttribute("user", testUser);
        assertEquals(result, "redirect:/User");
    }

    @Test
    public void login_Fail() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        when(userManager.loginWithUsername(anyString(), anyString())).thenReturn(null);
        String result = applicationController.signinRequest(testRequest);
        assertEquals(result, "SignIn");
    }

    @Test
    public void signOut(){
        String result = applicationController.signoutRequest(testRequest);
        verify(testSession).setAttribute("user", null);
        assertEquals(result, "redirect:/");
    }

    @Test
    public void register_Success() throws UnsupportedEncodingException, NoSuchAlgorithmException{
        when(userManager.register(any(User.class))).thenReturn(1);

        String result = applicationController.registrationRequest(testRequest);
        verify(testSession).setAttribute(eq("user"), any(User.class));
        assertEquals(result, "redirect:/User");
    }

    @Test
    public void register_Fail() throws UnsupportedEncodingException, NoSuchAlgorithmException{
        String errorString = "User with this e-mail already exists";
        String result = applicationController.registrationRequest(testRequest);
        verify(testRequest).setAttribute("errorString", errorString);

        assertEquals(result, "Registration");
    }

}
