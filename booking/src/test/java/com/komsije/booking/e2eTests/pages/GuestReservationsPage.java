package com.komsije.booking.e2eTests.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GuestReservationsPage {
    private WebDriver driver;
    public GuestReservationsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//div[@class='header-card']/h1")
    private WebElement loadedText;
    @FindBy(xpath = "//div[@class='reservations-table']//tbody")
    private WebElement reservationsTable;
    @FindBy(xpath = "//tr//p[contains(text(), 'Testing accommodation')]/../../../td[6]")
    private WebElement reservationStatus;
    @FindBy(xpath = "//tr//p[contains(text(), 'Testing accommodation')]/../../..//button[@label='Cancel']")
    private WebElement cancelButton;


    public boolean isPageLoaded(){
        return (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(loadedText, "My Reservations"));
    }
    public String getTestingAccommodationStatus(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(reservationStatus)).getText();
    }

    public void clickOnCancelOnTestingAccommodation(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(cancelButton)).click();
    }

    public void acceptDialog(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public void waitForRefresh(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.textToBePresentInElement(reservationStatus, "Cancelled"));
        }catch (TimeoutException ignored){

        }


    }

    public void waitIndefinitely(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/fdf")));
    }
}
