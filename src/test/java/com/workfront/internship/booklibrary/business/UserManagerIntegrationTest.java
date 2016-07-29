package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.business.UserManager;
import com.workfront.internship.booklibrary.business.UserManagerImpl;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.dao.DataSource;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;
import org.junit.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ${Sona} on 7/29/2016.
 */
public class UserManagerIntegrationTest {

    private static UserManager userManager;
    private User testUser;
    DataSource dataSource = DataSource.getInstance();
    UserDAO userDAO;

    @Before
    public void setup() throws Exception {
        userManager = new UserManagerImpl(dataSource);
        testUser = getRandomUser();
        userDAO = new UserDAOImpl(dataSource);
    }

    @After
    public void tearDown(){
        testUser = null;
        userDAO.deleteAll();
    }

    @Test
    public void registration() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //test
        userManager.registration(testUser);

        User actualUser = userManager.findUserByID(testUser.getId());
        assertNotNull(actualUser);

    }

    @Test
    public void loginWithUsername() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pass = testUser.getPassword();
        userManager.registration(testUser);

        //test
        User actualUser = userManager.loginWithUsername(testUser.getUsername(), pass);

        assertEquals(testUser, actualUser);
    }
}
