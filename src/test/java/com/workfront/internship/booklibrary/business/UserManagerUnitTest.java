package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.dao.TestUtil;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static com.workfront.internship.booklibrary.dao.TestUtil.getRandomUser;
import static junit.framework.TestCase.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;

import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by ${Sona} on 7/27/2016.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = ManagerTestConfig.class)
public class UserManagerUnitTest {

    private UserManager userManager;

    private User testUser;
    private UserDAO userDAO;


    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {
        userManager = new UserManagerImpl();
        userDAO = Mockito.mock(UserDAOImpl.class);
        Whitebox.setInternalState(userManager, "userDAO", userDAO);

        testUser = getRandomUser();
    }

    @After
    public void tearDown(){
        testUser = null;
        userDAO = null;
    }

    @Test
    public void registration_ValidUser_EncryptedPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String expectedPassword = userManager.getHashedPassword(testUser.getPassword());

        userManager.register(testUser);

        //method under test
        String actualPassword = testUser.getPassword();
        assertEquals("Unable to hash password", actualPassword, expectedPassword);
    }

    @Test
    public void registration_nullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id;
        //test
        id = userManager.register(null);

        assertEquals("Checking if returned id is 0", 0, id);

    }

    @Test
    public void registration_nonNullUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id;
        testUser.setId(5);

        //test
        id = userManager.register(testUser);

        //test
        Mockito.verify(userDAO).add(testUser);

        assertTrue("user is added", id > 0);
    }

    @Test
    public void registration_nonNullUser_invalidEmail() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int id;
        testUser.seteMail("bla");

        //test
        id = userManager.register(testUser);

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

        //assertTrue("user details are updated", userid < 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteAccount_nullUser(){
        boolean b;
        //test
        b = userManager.deleteAccount(0);

        assertEquals("user is not null", true, b);
    }

    @Test
    public void deleteAccount_successOn_nonNullUser(){
        testUser.setId(2);
        boolean b;

        //Test
        b = userManager.deleteAccount(testUser.getId());

        assertEquals("null user", true, b);
    }

    @Test
    public void deleteAccount_cannotDeleteNonNullUser(){
        testUser.setId(3);
        User user = TestUtil.getRandomUser();
        when(userDAO.getUserByID(anyInt())).thenReturn(user);

        boolean b;
        //test
        b = userManager.deleteAccount(testUser.getId());

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
