package com.komsije.booking.e2eTests.pages;

import com.komsije.booking.e2eTests.helper.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final static String PAGE_URL = " http://localhost:4200/";

    @FindBy(css = "input[placeholder='Where to?']")
    private WebElement placeInput;

    @FindBy(css = "input[placeholder='Start date']")
    private WebElement startDateInput;

    @FindBy(css = "input[placeholder='End date']")
    private WebElement endDateInput;


    @FindBy(css = "button[data-pc-section='incrementbutton']")
    private WebElement incrementGuests;


    @FindBy(css = "button[label='Search']")
    private WebElement submitButton;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(this.driver, this);
    }

    public boolean isOpen(){
        Duration duration = Duration.ofSeconds(10);
        boolean isLoaded = (new WebDriverWait(driver, duration))
                .until(ExpectedConditions.textToBePresentInElementLocated(By.id("title"), "Make your holiday"));
        Helper.takeScreenshoot(driver, "ucitaoStanicu");
        return isLoaded;
    }

    public void insertPlace(String place){
        placeInput.clear();
        placeInput.sendKeys(place);
    }

    public void insertStartDate(String startDate){
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver,duration);
        wait.until(ExpectedConditions.elementToBeClickable(startDateInput));
        Actions actions = new Actions(driver);
        actions.moveToElement(startDateInput).click().perform();

        startDateInput.clear();
        startDateInput.sendKeys(startDate);

    }

    public void insertEndDate(String endDate){
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver,duration);
        wait.until(ExpectedConditions.elementToBeClickable(endDateInput));
        Actions actions = new Actions(driver);
        actions.moveToElement(endDateInput).click().perform();

        endDateInput.clear();
        endDateInput.sendKeys(endDate);

    }

    public void insertNumberOfGuests(int number){
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver,duration);
        wait.until(ExpectedConditions.elementToBeClickable(incrementGuests));
        Actions actions = new Actions(driver);
        for (int i = 0; i < number; i++) {
            actions.moveToElement(incrementGuests).click().perform();
        }
    }

    public void submit(){
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver,duration);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        Actions actions = new Actions(driver);
        actions.moveToElement(submitButton).click().perform();
        Helper.takeScreenshoot(driver, "ide");
    }




}
