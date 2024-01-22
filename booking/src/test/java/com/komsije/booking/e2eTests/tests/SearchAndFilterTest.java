package com.komsije.booking.e2eTests.tests;

import com.komsije.booking.e2eTests.pages.HomePage;
import com.komsije.booking.e2eTests.pages.SearchedAccommodationsPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchAndFilterTest extends TestBase{
    @Test
    public void searchAccommodations_shouldSeeAvailableStays_GoodParams(){
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.insertPlace("City1");
        homePage.insertStartDate("1.2.2024");
        homePage.insertEndDate("7.2.2024");
        homePage.insertNumberOfGuests(3);
        homePage.submit();

        SearchedAccommodationsPage searchedAccommodationsPage = new SearchedAccommodationsPage(driver);
        assertTrue(searchedAccommodationsPage.isOpen());
        assertTrue(searchedAccommodationsPage.loadedSomething(10));
    }

    @Test
    public void searchAccommodations_shouldNotSeeAvailableStays_BadParams(){
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.insertPlace("City3");
        homePage.insertStartDate("1.2.2024");
        homePage.insertEndDate("7.2.2024");
        homePage.insertNumberOfGuests(3);
        homePage.submit();

        SearchedAccommodationsPage searchedAccommodationsPage = new SearchedAccommodationsPage(driver);
        assertTrue(searchedAccommodationsPage.isOpen());
        assertFalse(searchedAccommodationsPage.loadedSomething(10));
    }

    @Test
    public void searchAccommodations_shouldFailToSearch_BadDates(){
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.insertPlace("City1");
        homePage.insertStartDate("3.2.2024");
        homePage.insertEndDate("1.2.2024");
        homePage.insertNumberOfGuests(3);
        homePage.submit();

        SearchedAccommodationsPage searchedAccommodationsPage = new SearchedAccommodationsPage(driver);
        assertFalse(searchedAccommodationsPage.isOpen());
    }

    @Test
    public void filterAccommodations_shouldFilterByType_AllTypeParams(){
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.insertPlace("City1");
        homePage.insertStartDate("1.2.2024");
        homePage.insertEndDate("7.2.2024");
        homePage.insertNumberOfGuests(3);
        homePage.submit();

        SearchedAccommodationsPage searchedAccommodationsPage = new SearchedAccommodationsPage(driver);
        assertTrue(searchedAccommodationsPage.isOpen());
        assertTrue(searchedAccommodationsPage.loadedSomething(10));

        assertTrue(searchedAccommodationsPage.filterByType());
    }
    @Test
    public void filterAccommodations_shouldFilterByAmenities_AllAmenities(){
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.insertPlace("City1");
        homePage.insertStartDate("1.2.2024");
        homePage.insertEndDate("7.2.2024");
        homePage.insertNumberOfGuests(3);
        homePage.submit();

        SearchedAccommodationsPage searchedAccommodationsPage = new SearchedAccommodationsPage(driver);
        assertTrue(searchedAccommodationsPage.isOpen());
        assertTrue(searchedAccommodationsPage.loadedSomething(10));

        assertTrue(searchedAccommodationsPage.filterByAmenities());
    }

    @Test
    public void filterAccommodations_shouldFilterByPrice_CommonPrices(){
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.insertPlace("City1");
        homePage.insertStartDate("1.2.2024");
        homePage.insertEndDate("7.2.2024");
        homePage.insertNumberOfGuests(3);
        homePage.submit();

        SearchedAccommodationsPage searchedAccommodationsPage = new SearchedAccommodationsPage(driver);
        assertTrue(searchedAccommodationsPage.isOpen());
        assertTrue(searchedAccommodationsPage.loadedSomething(10));

        assertTrue(searchedAccommodationsPage.filterByPrice(330,100));
    }

}
