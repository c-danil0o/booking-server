package com.komsije.booking.e2eTests.pages;

import com.komsije.booking.e2eTests.helper.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchedAccommodationsPage {
    private final WebDriver driver;
    @FindBy(xpath = "//div[@class='fields-checkbox']/h5[1]")
    private WebElement someText;

    @FindBy(css = ".apartment-details")
    private WebElement detailsButtonText;

    @FindBy(css = "p-checkbox[ng-reflect-input-id='Room']")
    private WebElement filterRoomCheckBox;
    @FindBy(css = "p-checkbox[ng-reflect-input-id='Apartment']")
    private WebElement filterApartmentCheckBox;
    @FindBy(css = "p-checkbox[ng-reflect-input-id='Hotel']")
    private WebElement filterHotelCheckBox;

    @FindBy(css = "p-checkbox[ng-reflect-input-id='TV']")
    private WebElement tvCheckBox;
    @FindBy(css = "p-checkbox[ng-reflect-input-id='Parking']")
    private WebElement parkingCheckBox;
    @FindBy(css = "p-checkbox[ng-reflect-input-id='AC']")
    private WebElement acCheckBox;
    @FindBy(css = "p-checkbox[ng-reflect-input-id='Fridge']")
    private WebElement fridgeCheckBox;
    @FindBy(css = "p-checkbox[ng-reflect-input-id='Lift']")
    private WebElement liftCheckBox;
    @FindBy(css = "p-checkbox[ng-reflect-input-id='Pet Friendly']")
    private WebElement petFriendlyCheckBox;
    @FindBy(css = "p-checkbox[ng-reflect-input-id='Kitchen']")
    private WebElement kitchenCheckBox;

    @FindBy(css = "span[data-pc-section='endHandler']")
    private WebElement sliderRight;
    @FindBy(css = "span[data-pc-section='startHandler']")
    private WebElement sliderLeft;

    public SearchedAccommodationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public boolean isOpen(){
        Duration duration = Duration.ofSeconds(10);
        boolean isLoaded = true;
        try {
            isLoaded = (new WebDriverWait(driver, duration))
                    .until(ExpectedConditions.textToBePresentInElement(someText,"Filter by type"));
        }
        catch (Exception e){
            isLoaded = false;
        }
        Helper.takeScreenshoot(driver, "ucitaoStanicu2");
        return isLoaded;
    }

    public boolean loadedSomething(int seconds){
        Duration duration = Duration.ofSeconds(seconds);
        boolean isLoaded = true;
        try {
            isLoaded = (new WebDriverWait(driver, duration))
                    .until(ExpectedConditions.textToBePresentInElement(detailsButtonText,"Details"));
        }
        catch (Exception e){
            isLoaded = false;
        }
        Helper.takeScreenshoot(driver, "ucitaoStanicu2");
        return isLoaded;
    }

    public boolean filterByType(){
        boolean isValid = true;
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver,duration);
        wait.until(ExpectedConditions.elementToBeClickable(filterApartmentCheckBox));
        Actions actions = new Actions(driver);
        actions.moveToElement(filterApartmentCheckBox).click().perform();                           //selected apartment

        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(filterApartmentCheckBox).click().perform();                           //deselected apartment
        actions.moveToElement(filterRoomCheckBox).click().perform();                                //selected room
        if(loadedSomething(1))
            isValid=false;

        actions.moveToElement(filterApartmentCheckBox).click().perform();                           //selected apartment
        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(filterHotelCheckBox).click().perform();                               //selected hotel
        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(filterApartmentCheckBox).click().perform();                           //deselected apartment
        if(loadedSomething(1))
            isValid=false;

        actions.moveToElement(filterHotelCheckBox).click().perform();                               //deselected hotel
        actions.moveToElement(filterApartmentCheckBox).click().perform();                           //deselected apartment
        if(!loadedSomething(1))
            isValid=false;

        Helper.takeScreenshoot(driver, "filters1");


        return isValid;
    }

    public boolean filterByAmenities(){
        boolean isValid = true;
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver,duration);
        wait.until(ExpectedConditions.elementToBeClickable(tvCheckBox));
        Actions actions = new Actions(driver);
        actions.moveToElement(tvCheckBox).click().perform();                                        //selected tv

        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(parkingCheckBox).click().perform();                                   //select parking
        if(loadedSomething(1))
            isValid=false;

        actions.moveToElement(parkingCheckBox).click().perform();                                   //deselected parking
        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(acCheckBox).click().perform();                                        //selected AC
        if(loadedSomething(1))
            isValid=false;

        actions.moveToElement(acCheckBox).click().perform();                                        //deselected AC
        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(fridgeCheckBox).click().perform();                                    //selected fridge
        if(loadedSomething(1))
            isValid=false;

        actions.moveToElement(fridgeCheckBox).click().perform();                                    //deselected fridge
        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(liftCheckBox).click().perform();                                      //selected lift
        if(loadedSomething(1))
            isValid=false;

        actions.moveToElement(liftCheckBox).click().perform();                                      //deselected lift
        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(petFriendlyCheckBox).click().perform();                               //selected pet friendly
        if(loadedSomething(1))
            isValid=false;

        actions.moveToElement(petFriendlyCheckBox).click().perform();                               //deselected pet friendly
        if(!loadedSomething(1))
            isValid=false;

        actions.moveToElement(tvCheckBox).click().perform();                                        //selected tv

        if(!loadedSomething(1))
            isValid=false;

        Helper.takeScreenshoot(driver, "filters2");

        return isValid;
    }

    public boolean filterByPrice(int leftSlide, int rightSlide){
        boolean isValid = true;
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver,duration);
        wait.until(ExpectedConditions.elementToBeClickable(sliderLeft));
        Actions actions = new Actions(driver);

        actions.moveToElement(sliderLeft).click().perform();
        for (int i = 0; i < leftSlide; i++) {
            sliderLeft.sendKeys(Keys.ARROW_RIGHT);
        }
        actions.moveToElement(sliderRight).click().perform();
        if(loadedSomething(1))
            isValid=false;
        actions.moveToElement(sliderLeft).click().perform();
        for (int i = 0; i < leftSlide; i++) {
            sliderLeft.sendKeys(Keys.ARROW_LEFT);
        }
        actions.moveToElement(sliderLeft).click().perform();

        if(!loadedSomething(3))
            isValid=false;


        actions.moveToElement(sliderRight).click().perform();
        for (int i = 0; i < rightSlide; i++) {
            sliderRight.sendKeys(Keys.ARROW_LEFT);
        }
        actions.moveToElement(sliderLeft).click().perform();

        if(loadedSomething(1))
            isValid=false;

        actions.moveToElement(sliderRight).click().perform();
        for (int i = 0; i < rightSlide; i++) {
            sliderRight.sendKeys(Keys.ARROW_RIGHT);
        }
        actions.moveToElement(sliderLeft).click().perform();

        if(!loadedSomething(3))
            isValid=false;

        return isValid;
    }




}
