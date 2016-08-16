package com.workfront.internship.booklibrary.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class AbstractPage {
    static private WebDriver webDriver;

//    AbstractPage() {
////        webDriver = new FirefoxDriver();
////        webDriver.get("http://localhost:8080");
//        webDriver = new ChromeDriver();
//        webDriver.get("http://localhost:8080");
//    }

    static public void init () {
        webDriver = new ChromeDriver();
//        webDriver.get("http://localhost:8080/index.jsp");
        webDriver.get("http://localhost:8080");
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

}
