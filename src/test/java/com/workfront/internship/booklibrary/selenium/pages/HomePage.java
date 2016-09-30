package com.workfront.internship.booklibrary.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class HomePage extends AbstractPage {

    public HomePage() {
        //getWebDriver().get("http://localhost:8080");
    }

    public WebElement clickLogin() throws InterruptedException {
//        WebElement loginButton = getWebDriver().findElement(By.name("sign-in"));
        WebElement loginButton = getWebDriver().findElement(By.id("login"));
//        WebElement loginButton = getWebDriver().findElement(By.tagName("Submit"));

//        loginButton.SendKeys(Keys.Retur‌​n) ;
        loginButton.click();
        return getLogin();
    }

    public WebElement getLogin() throws InterruptedException {
        Thread.sleep(2000);
        WebElement login = getWebDriver().findElement(By.className("signin"));  //By.cssSelector("input[type='text']"));
        return login;
    }

    public WebElement pointAtAuthors() throws InterruptedException {
//        WebElement pointer = getWebDriver().findElement(By.className("dropbtn"));
        WebElement pointer = getWebDriver().findElement(By.className("authorList"));
//        WebElement pointer = getWebDriver().findElement(By.className("main-functions"));
        return pointer;
    }

    public WebElement pointAtContacts() {
        WebElement pointer = getWebDriver().findElement(By.id("contacts"));
        return pointer;
    }


//    public WebElement getLogoutButton() {
//        return getWebDriver().findElement(By.id("logout_button"));
//    }
}
