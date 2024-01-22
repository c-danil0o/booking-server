package com.komsije.booking.e2eTests.tests;

import com.komsije.booking.e2eTests.pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchAndFilterTest extends TestBase{
    @Test
    public void test(){
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
    }

}
