package com.komsije.booking.e2eTests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserLandingPage {
    private WebDriver driver;
    public UserLandingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
    @FindBy(xpath = "//div[@class='main-text']/span[1]")
    private WebElement loadedText;

    @FindBy(xpath = "//button[contains(@class, 'profile-button')]")
    private WebElement profileButton;

    public boolean isPageLoaded() {
        return (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(loadedText, "Make your holiday"));
    }

    public void clickOnProfileButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(profileButton)).click();
    }
}
