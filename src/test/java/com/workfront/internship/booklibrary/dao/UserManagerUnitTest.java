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

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static junit.framework.TestCase.assertEquals;

import org.mockito.internal.util.reflection.Whitebox;


/**
 * Created by ${Sona} on 7/27/2016.
 */
public class UserManagerUnitTest {
    DataSource dataSource;
    private static User testUser;

    private UserDAO userDAO;
    private UserManager userManager;

    @BeforeClass
    public static void setUpClass(){
        testUser = new User();
        testUser = TestUtil.getRandomUser();
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
        when(connection.prepareStatement(any(String.class))).thenThrow(SQLException.class);
        when(connection.prepareStatement(any(String.class), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenThrow(SQLException.class);

        userDAO = new UserDAOImpl(dataSource);
        userManager = new UserManagerImpl(dataSource);
    }

    @After
    public void tearDown(){

    }


/**
    @Test(expected = RuntimeException.class)
    public void register_emailError(){
        userManager.
        userDAO.add(TestUtil.getRandomUser());
    }
*/

    @Test
    public void registration_ValidUser_EncryptedPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String expectedPassword = userManager.getHashedPassword(testUser.getPassword());

        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);

        userManager.registration(testUser);
        //  Mockito.verify(userDAO).addUser(testUser);

        //method under test
        String actualPassword = testUser.getPassword();
        assertEquals("Unable to hash password", actualPassword, expectedPassword);
    }

    @Test
    public void registration_nullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id;
        id = userManager.registration(null);

        assertEquals("Checking if returned id is 0", 0, id);

    }

    @Test
    public void registration_nonNullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id;
        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);
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
        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);
        testUser.seteMail("bla");

        //test
        id = userManager.registration(testUser);

        //test
//        Mockito.verifyZeroInteractions(userDAO);
        Mockito.verify(userDAO, Mockito.never()).add(testUser);

        assertTrue("user is not added", id < 1);
    }

    @Test (expected = RuntimeException.class)
    public void loginWithUsername_nullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        testUser = userManager.loginWithUsername(null, null);
        //assertEquals("Checking if returned null", null, null);
    }

    @Test
    public void loginWithUsername_nonNullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);
        String password = testUser.getPassword();
        testUser.setPassword(userManager.getHashedPassword(testUser.getPassword()));
        when(userDAO.getUserByUsername(anyString())).thenReturn(testUser);

        //test
        User user = userManager.loginWithUsername(testUser.getUsername(), password);

        assertNotNull("Login works incorrectly", user);
    }

    @Test
    public void loginWithUsername_nonNullUser_incorrectUsernameOrPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);
        String password1 = testUser.getPassword();
        testUser.setPassword(userManager.getHashedPassword(testUser.getPassword()));
        when(userDAO.getUserByUsername(anyString())).thenReturn(testUser);

        //test
        User user = userManager.loginWithUsername(testUser.getUsername(), password1);

        assertEquals("Login works incorrectly", testUser, user);
    } //todo ashxatum e, bayts stugel logican






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
