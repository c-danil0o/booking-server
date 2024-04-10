package com.komsije.booking.e2eTests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    @FindBy(xpath = "//input[@type='email']")
    private WebElement loginField;
    @FindBy(xpath = "//input[@type='password']")
    private WebElement passwordField;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;
    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    public boolean isPageLoaded(){
        return (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".sign_in"), "Sign In"));
    }
    public void inputGuestLoginCredentials(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(loginField));
        loginField.clear();
        loginField.sendKeys("guest2@example.com");
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys("123456");
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();
    }
    public void inputHostLoginCredentials(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(loginField));
        loginField.clear();
        loginField.sendKeys("host2@example.com");
        wait.until(ExpectedConditions.visibilityOf(passwordField));
        passwordField.clear();
        passwordField.sendKeys("123456");
        wait.until(ExpectedConditions.visibilityOf(loginButton));
        loginButton.click();
    }
}
