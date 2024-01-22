package com.komsije.booking.e2eTests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HostProfilePage {
    private WebDriver driver;
    public HostProfilePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(css = ".reviews-text")
    private WebElement loadedText;
    @FindBy(css="button[label='Reservations']")
    private WebElement reservationsButton;

    public boolean isPageLoaded(){
        return (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(loadedText, "Reviews:"));
    }

    public void clickOnReservationsButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(reservationsButton)).click();
    }
}
