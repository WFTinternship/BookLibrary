package com.workfront.internship.booklibrary.dao;

import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.business.UserManagerImpl;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;

import org.junit.*;
import org.mockito.Mockito;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static junit.framework.TestCase.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import org.mockito.internal.util.reflection.Whitebox;


/**
 * Created by ${Sona} on 7/27/2016.
 */
public class UserManagerUnitTest {
    DataSource dataSource;
    private User testUser;

    private UserDAO userDAO;
    private UserManager userManager;

    @BeforeClass
    public static void setUpClass(){
//        testUser = new User();
//        testUser = TestUtil.getRandomUser();
    }

    @AfterClass
    public static void tearDownClass(){

    }

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        dataSource = Mockito.mock(DataSource.class);

        Connection connection = Mockito.mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);

        userDAO = new UserDAOImpl(dataSource);
        userManager = new UserManagerImpl(dataSource);

        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);

        testUser = new User();
        testUser = TestUtil.getRandomUser();
    }

    @After
    public void tearDown(){
//        userDAO.deleteAll();
    }


    @Test
    public void registration_ValidUser_EncryptedPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String expectedPassword = userManager.getHashedPassword(testUser.getPassword());

        userManager.registration(testUser);

        //method under test
        String actualPassword = testUser.getPassword();
        assertEquals("Unable to hash password", actualPassword, expectedPassword);
    }

    @Test
    public void registration_nullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id;
        //test
        id = userManager.registration(null);

        assertEquals("Checking if returned id is 0", 0, id);

    }

    @Test
    public void registration_nonNullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id;
        testUser.setId(5);

        //test
        id = userManager.registration(testUser);

        //test
        Mockito.verify(userDAO).add(testUser);

        assertTrue("user is added", id > 0);
    }

    @Test
    public void registration_nonNullUser_invalidEmail() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id;
        testUser.seteMail("bla");

        //test
        id = userManager.registration(testUser);

        //test
//        Mockito.verifyZeroInteractions(userDAO);
        Mockito.verify(userDAO, Mockito.never()).add(testUser);

        assertTrue("user is not added", id < 1);
    }

    @Test
    public void loginWithUsername_nullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user;
        when(userDAO.getUserByUsername(anyString())).thenReturn(null);

        //test
        user = userManager.loginWithUsername(testUser.getUsername(), testUser.getPassword());

        assertEquals(null, user);
    }

    @Test
    public void loginWithUsername_nonNullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String password = testUser.getPassword();
        testUser.setPassword(userManager.getHashedPassword(testUser.getPassword()));
        when(userDAO.getUserByUsername(anyString())).thenReturn(testUser);

        //test
        User user = userManager.loginWithUsername(testUser.getUsername(), password);

        assertNotNull("Login works incorrectly", user);
    }

    @Test
    public void loginWithUsername_nonNullUser_incorrectUsernameOrPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String password1 = testUser.getPassword();
        testUser.setPassword(userManager.getHashedPassword(testUser.getPassword()));
        when(userDAO.getUserByUsername(anyString())).thenReturn(testUser);

        //test
        User user = userManager.loginWithUsername(testUser.getUsername(), password1);

        assertEquals("Login works incorrectly", testUser, user);
    }

    @Test
    public void loginWithEMail_nullUser () throws UnsupportedEncodingException, NoSuchAlgorithmException{
        User user;
        when(userDAO.getUserByeMail(anyString())).thenReturn(null);

        //test
        user = userManager.loginWithEMail(testUser.geteMail(), testUser.getPassword());

        assertEquals(null, user);
    }

    @Test
    public void loginWithEMail_nonNullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String password = testUser.getPassword();
        testUser.setPassword(userManager.getHashedPassword(testUser.getPassword()));
        when(userDAO.getUserByeMail(anyString())).thenReturn(testUser);

        //test
        User user = userManager.loginWithEMail(testUser.geteMail(), password);

        assertNotNull("Login works incorrectly", user);
    }

    @Test
    public void loginWithEMail_nonNullUser_incorrectEmailOrPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String password1 = testUser.getPassword();
        testUser.setPassword(userManager.getHashedPassword(password1));
        when(userDAO.getUserByeMail(anyString())).thenReturn(testUser);

        //test
        User user = userManager.loginWithEMail(testUser.geteMail(), password1);

        assertEquals("Login works incorrectly", testUser, user);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findUserByID_idLessThan1(){
        testUser.setId(0);
        int id = testUser.getId();

        //test
        userManager.findUserByID(id);
    }

    @Test
    public void findUserByID_nullUser(){
        when(userDAO.getUserByID(5)).thenReturn(null);
        User user;

        //test method
        user = userManager.findUserByID(5);
        assertNull(user);
    }

    @Test
    public void findUserByID_nonNullUser(){
        testUser.setId(5);
        when(userDAO.getUserByID(5)).thenReturn(testUser);

        User user;
        //test
        user = userManager.findUserByID(testUser.getId());

        assertEquals("Null user returned", user, testUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findUserByUserName_noUsername() {
        testUser.setUsername(null);
        String userName = testUser.getUsername();

        //test
        userManager.findUserByUserName(userName);
    }

    @Test
    public void findUserByUserName_nullUser() {
        when(userDAO.getUserByUsername("sonamikayelyan")).thenReturn(null);
        User user;

        //test
        user = userManager.findUserByUserName("sonamikayelyan");
        assertNull(user);
    }

    @Test
    public void findUserByUserName_nonNullUser(){
        testUser.setUsername("sonamikayelyan");
        when(userDAO.getUserByUsername("sonamikayelyan")).thenReturn(testUser);

        User user;

        //test
        user = userManager.findUserByUserName(testUser.getUsername());

        assertEquals("Null user returned", user, testUser);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findUserByeMail_noEmail() {
        testUser.seteMail(null);
        String email = testUser.geteMail();

        //test
        userManager.findUserByeMail(email);
    }

    @Test
    public void findUserByeMail_nullUser(){
        when(userDAO.getUserByeMail("sona@yahoo.com")).thenReturn(null);
        User user;

        //test
        user = userManager.findUserByeMail("sona@yahoo.com");
        assertNull(user);
    }

    @Test
    public void findUserByeMail_nonNullUser() {
        testUser.seteMail("sona@yahoo.com");
        when(userDAO.getUserByeMail("sona@yahoo.com")).thenReturn(testUser);

        User user;

        //Test
        user = userManager.findUserByeMail(testUser.geteMail());

        assertEquals("Null user returned", user, testUser);
    }

    @Test
    public void update_nullUser(){
        User user;
        //test
        user = userManager.update(null);

        assertEquals("user is not null", null, user);
    }

    @Test
    public void update_valid_nonNullUser(){
        testUser.setName("Lilit");
        User user;
        user = userManager.update(testUser);

        assertEquals(testUser.getName(), user.getName());
    }

    @Test
    public void update_nonNull_invalidUser(){

        testUser.seteMail(null);


        //test
        userManager.update(testUser);

        //test
        Mockito.verify(userDAO, Mockito.never()).updateUser(testUser);

        //assertTrue("user details are updated", usertid < 1);
    }

    @Test
    public void deleteAccount_nullUser(){
        boolean b;
        //test
        b = userManager.deleteAccount(null);

        assertEquals("user is not null", true, b);
    }

    @Test
    public void deleteAccount_nonNullUser(){
        boolean b;

        //Test
        b = userManager.deleteAccount(testUser);

        assertEquals("null user", true, b);
    }

    @Test
    public void deleteAccount_cannotDeleteNonNullUser(){
        User user = TestUtil.getRandomUser();
        when(userDAO.getUserByID(anyInt())).thenReturn(user);

        boolean b;
        //test
        b = userManager.deleteAccount(testUser);

        assertEquals(false, b);
    }






/**
    @Test(expected = NoSuchAlgorithmException.class)
    public void getHashedPassword_InvalidAlgorithmForEncriptionIsUsed() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String expectedPassword = userManager.getHashedPassword(testUser.getPassword());

        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);

        userManager.registration(testUser);
        //  Mockito.verify(userDAO).addUser(testUser);

        //method under test
        String actualPassword = testUser.getPassword();
        assertEquals("Unable to hash password", actualPassword, expectedPassword);
    }
*/

/**
    @Test(expected = UnsupportedEncodingException.class)
    public void getHashedPassword_InvalidEncoding() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //UserManagerImpl userManagerImpl = spy(UserManagerImpl.class);
        String expectedPassword = userManager.getHashedPassword(testUser.getPassword());


        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);

        userManager.registration(testUser);
        //  Mockito.verify(userDAO).addUser(testUser);

        //method under test
        String actualPassword = testUser.getPassword();
        assertEquals("Unable to hash password", actualPassword, expectedPassword);
    }
*/



/**    @Test(expected = OperationFailedException.class)
    public void addAccount_InvalidUser(){
        // TODO: 7/27/16 implement
    }
*/

/**    @Test
    public void addAccount_ValidUser_Inserted_EmailSent() {

        EmailService emailService = Mockito.mock(EmailServiceImpl.class);
        UserDAO userDAO = Mockito.mock(UserDAOImpl.class);

        Whitebox.setInternalState(userManager, "userDAO", userDAO);
        Whitebox.setInternalState(userManager, "emailService", emailService);

        when(userDAO.addUser(testUser)).thenReturn(10);
        when(emailService.sendVerificationEmail(testUser)).thenReturn(true);

        //test method
        userService.addAccount(testUser);

        Mockito.verify(emailService).sendVerificationEmail(testUser);
    }
*/

/**    @Test(expected = OperationFailedException.class)
    public void addAccount_ValidUser_Inserted_EmailSent_False() throws {

        UserServiceImpl userService  = new UserServiceImpl();
        EmailService emailService = Mockito.mock(EmailServiceImpl.class);
        UserDAO userDAO = Mockito.mock(UserDAOImpl.class);

        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Whitebox.setInternalState(userService, "emailService", emailService);

        when(userDAO.addUser(testUser)).thenReturn(10);
        when(emailService.sendVerificationEmail(testUser)).thenReturn(false);

        //test method
        userService.addAccount(testUser);

        Mockito.verify(emailService).sendVerificationEmail(testUser);
    }
*/

/**
    @Test(expected = OperationFailedException.class)
    public void addAccount_Insert_Fail_Throws_Exception() throws OperationFailedException, DAOException {

        UserServiceImpl userService  = new UserServiceImpl();
        EmailService emailService = Mockito.mock(EmailServiceImpl.class);
        UserDAO userDAO = Mockito.mock(UserDAOImpl.class);

        Whitebox.setInternalState(userService, "userDAO", userDAO);
        Whitebox.setInternalState(userService, "emailService", emailService);

        when(userDAO.addUser(testUser)).thenThrow(OperationFailedException.class);

        //test method
        userService.addAccount(testUser);
    }
*/




}
