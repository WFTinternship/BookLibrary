package com.workfront.internship.booklibrary.business;

import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.LegacyDataSource;
import com.workfront.internship.booklibrary.dao.UserDAO;
import com.workfront.internship.booklibrary.dao.UserDAOImpl;
import com.workfront.internship.booklibrary.spring.DevelopmentConfig;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
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
@ContextConfiguration(classes = DevelopmentConfig.class)
@ActiveProfiles("Development")
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



    /**
     import static org.junit.Assert.*;
     import java.util.Arrays;
     import java.util.Collection;
     import org.junit.BeforeClass;
     import org.junit.Test;
     import org.junit.runner.RunWith;
     import org.junit.runners.Parameterized;
     import org.junit.runners.Parameterized.Parameters;

     @RunWith(Parameterized.class)

     public class EmailFormatValidatorTest {

     private String arg;
     private static EmailFormatValidator emailFormatValidator;//
     private Boolean expectedValidation;

     public EmailFormatValidatorTest(String str, Boolean expectedValidation) {//
     this.arg = str;//
     this.expectedValidation = expectedValidation;//
     }


     @BeforeClass//
     public static void initialize() {//
     emailFormatValidator = new EmailFormatValidator();//
     }



     @Parameters//
     public static Collection<Object[]> data() {

     Object[][] data = new Object[][] {

     { "javacodegeeks@gmail.com.2j",false },    // it's not allowed to have a digit in the second level tld

     { "java@java@oracle.com", false },         // you cannot have @ twice in the address

     { "java!!!@example.com", false },          // you cannot the have special character '!' in the address

     { "mysite@.com", false },                  // tld cannot start with a dot

     { "javacodegees.com", false },             // must contain a @ character and a tld

     { ".javacodegees.com@at.com", false },     // the address cannot start with a dot

     { "javacodegees..javacom@at.com", false }, // you cannot have double dots in your address

     { "javacodegeeks@gmail.com",true },

     { "nikos+mylist@gmail.com", true },

     { "abc.efg-900@gmail-list.com", true },

     { "abc123@example.com.gr", true } };

     return Arrays.asList(data);

     }

     @Test
     public void test() {
     Boolean res = emailFormatValidator.validate(this.arg);
     String validv = (res) ? "valid" : "invalid";
     System.out.println("Hex Color Code "+arg+ " is " + validv);
     assertEquals("Result", this.expectedValidation, res);
     }
     }
     */

}
