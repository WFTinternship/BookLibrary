package com.workfront.internship.booklibrary.selenium.tests;

import com.workfront.internship.booklibrary.selenium.pages.HomePage;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class HomePageTest {
    static private HomePage homePage;

    @BeforeClass
    public static void setUp() {
        homePage = new HomePage();
        homePage.init();
    }

    @AfterClass
    static public void tearDown() {
        homePage.getWebDriver().close();
    }

    @Test
    public void loginButtonClick() throws InterruptedException {
        WebElement login = homePage.clickLogin();
        Assert.assertNotNull("Login Page is not displayed", login);
    }
}
