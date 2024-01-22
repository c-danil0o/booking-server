package com.komsije.booking.e2eTests.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver driver;

    @BeforeAll
    public static void initializeWebDriver() {
        if (System.getProperty("os.name").equals("Linux")){
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        }else{
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        }

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void quitDriver() {
        driver.quit();
    }

    @Test
    public void test(){
        System.out.println("run");
    }
}
