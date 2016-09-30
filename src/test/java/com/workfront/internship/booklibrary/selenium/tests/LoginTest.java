package com.workfront.internship.booklibrary.selenium.tests;

import com.workfront.internship.booklibrary.AllTestUtil;
import com.workfront.internship.booklibrary.common.User;
import com.workfront.internship.booklibrary.selenium.pages.AbstractPage;
import com.workfront.internship.booklibrary.selenium.pages.HomePage;
import com.workfront.internship.booklibrary.selenium.pages.Login;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.openqa.selenium.WebDriver;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LoginTest extends AbstractPage{
    private static Login login;
    private static HomePage homePage;
//    static User user;
    @BeforeClass
    public static void setUp() throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        user = AllTestUtil.registerRandomUser();

        login = new Login();
        homePage = new HomePage();
        login.init();
    }

    @AfterClass
    public static void tearDown(){
       login.getWebDriver().close();
    }

    @Test
    public void login_success() throws InterruptedException {
        homePage.clickLogin();
        login.typeUsername("sonamikayelyan");
        login.typePassword("sona");

//        login.typeUsername(user.getName());
//        login.typePassword(user.getPassword());
        login.clickSignin();

//        assertTrue("user-page window is not opened", getWebDriver().getCurrentUrl().equals("http://localhost:8080/User.jsp"));
        assertTrue("user-page window is not opened", getWebDriver().getCurrentUrl().equals("http://localhost:8080/User"));
//        assertFalse("login window is not closed", loggedin.isDisplayed());
 //       assertNotNull("logout button is not displayed", homePage.getLogoutButton());
    }
}
