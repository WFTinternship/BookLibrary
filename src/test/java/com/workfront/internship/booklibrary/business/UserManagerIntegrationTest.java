package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static com.workfront.internship.booklibrary.dao.TestUtil.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by ${Sona} on 7/29/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ManagerTestConfig.class)
public class UserManagerIntegrationTest {

    @Autowired
    private UserManager userManager;

    private User testUser;

    @Before
    public void setup() throws Exception {
        testUser = getRandomUser();
    }

    @After
    public void tearDown(){
        userManager.deleteAccount(testUser.getId());
    }

    @Test
    public void registration() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //test
        userManager.register(testUser);

        User actualUser = userManager.findUserByID(testUser.getId());
        assertNotNull(actualUser);

    }

    @Test
    public void loginWithUsername() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String pass = testUser.getPassword();
        userManager.register(testUser);

        //test
        User actualUser = userManager.loginWithUsername(testUser.getUsername(), pass);

        assertEquals(testUser, actualUser);
    }
}
