package com.workfront.internship.booklibrary.selenium.tests;

import com.workfront.internship.booklibrary.selenium.pages.HomePage;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HomePageTest {
    static private HomePage homePage;

    @BeforeClass
    public static void setUp() {
        homePage = new HomePage();
        homePage.init();
    }

    @AfterClass
    public static void tearDown() {
        homePage.getWebDriver().close();
    }

    @After
    public void afterMethod(){
        homePage.getWebDriver().get("http://localhost:8080/index.jsp");
    }


    @Test
    public void loginButtonClick() throws InterruptedException {
        WebElement login = homePage.clickLogin();
        Assert.assertNotNull("Login Page is not displayed", login);
    }

    @Test
    public void bookButtonHover() throws InterruptedException {
        WebElement overButton = homePage.pointAtBooks();

        Actions action = new Actions(homePage.getWebDriver());
        action.moveToElement(overButton).perform();
        Thread.sleep(2000);
//        action.moveToElement(show).click().build().perform();
//        Thread.sleep(2000);

        WebElement dropdown = homePage.getWebDriver().findElement(By.className("dropdown-content"));
        assertTrue("Genres window is not shown", dropdown.isDisplayed());
    }

    @Test
    public void contactsButtonHover() throws InterruptedException {
        WebElement overButton = homePage.pointAtContacts();

        Actions action = new Actions(homePage.getWebDriver());
        action.moveToElement(overButton).perform();
        Thread.sleep(2000);

        WebElement dropDown = homePage.getWebDriver().findElement(By.id("dialogue"));
        assertTrue("Information is not shown", dropDown.isDisplayed());
    }

}
