package com.workfront.internship.booklibrary.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Login extends AbstractPage {

    public void typeUsername(String username) throws InterruptedException {
        WebElement usernameField = getWebDriver().findElement(By.name("username/email"));
        Thread.sleep(1000);
        usernameField.sendKeys(username);
    }

    public void typePassword(String password) {
        WebElement passwordField = getWebDriver().findElement(By.name("password"));
        passwordField.sendKeys(password);
    }

    public void clickSignin() {
        WebElement signinButton = getWebDriver().findElement(By.cssSelector("input[type='submit']"));
        signinButton.click();
    }
}
