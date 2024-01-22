package com.komsije.booking.e2eTests.pages;

import com.komsije.booking.e2eTests.helper.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final static String PAGE_URL = " http://localhost:4200/";
    @FindBy(css = "button[label='Login']")
    private WebElement loginButton;
    @FindBy(xpath = "//div[@class='main-text']/span[1]")
    private WebElement loadedText;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(this.driver, this);
    }

    public boolean isOpen(){
        Duration duration = Duration.ofSeconds(10);
        boolean isLoaded = (new WebDriverWait(driver, duration))
                .until(ExpectedConditions.textToBePresentInElement(loadedText, "Make your holiday"));
        Helper.takeScreenshoot(driver, "ucitaoStanicu");
        return isLoaded;
    }

    public void clickOnLogin(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();

    }


}
