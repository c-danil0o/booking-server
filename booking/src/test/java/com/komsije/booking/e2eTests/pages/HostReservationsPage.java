package com.komsije.booking.e2eTests.pages;

import com.komsije.booking.e2eTests.tests.TestBase;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HostReservationsPage extends TestBase { private WebDriver driver;
    public HostReservationsPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    @FindBy(xpath = "//span[@class='title']")
    private WebElement loadedText;

    @FindBy(xpath = "//tr/td[5][contains(text(), '300')]/../td[7]")
    private WebElement reservationStatus;
    @FindBy(xpath = "//tr/td[5][contains(text(), '300')]/..//button[@label='Approve']")
    private WebElement approveButton;

    @FindBy(xpath = "//tr/td[5]/../td[7]")
    private List<WebElement> statuses;
    public boolean isPageLoaded(){
        return (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.textToBePresentInElement(loadedText, "Current reservations"));
    }
    public String getTestingAccommodationStatus(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOf(reservationStatus)).getText();
    }

    public void clickOnApproveOnTestingAccommodation(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(approveButton)).click();
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
            wait.until(ExpectedConditions.textToBePresentInElement(reservationStatus, "Approved"));
        }catch (TimeoutException ignored){

        }
    }

    public int countDeniedReservations(){
        int i =0;
        for (WebElement status: statuses){
            if (status.getText().equals("Denied"))
                i++;
        }
        return i;
    }


    public void waitIndefinitely(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/fdf")));
    }

}
