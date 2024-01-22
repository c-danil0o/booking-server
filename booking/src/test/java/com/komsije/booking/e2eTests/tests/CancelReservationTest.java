package com.komsije.booking.e2eTests.tests;

import com.komsije.booking.e2eTests.pages.*;
import com.komsije.booking.model.*;
import com.komsije.booking.repository.AccommodationRepository;
import com.komsije.booking.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class CancelReservationTest extends TestBase {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;


    @Test
    public void testCancelReservation_ShouldBeSuccessful_CancellationDeadlineValid() {
        LocalDate referenceDate = LocalDate.now();
        //cancellation deadline is 7
        Accommodation accommodation = accommodationRepository.getReferenceById(3L); // Testing accommodation
        Reservation reservation = new Reservation(null, referenceDate.plusDays(5), referenceDate, 3, 3, 300, 1L, 7L, accommodation, ReservationStatus.Approved);
        reservationRepository.saveAndFlush(reservation);


        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.clickOnLogin();
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageLoaded());
        loginPage.inputGuestLoginCredentials();
        UserLandingPage landingPage = new UserLandingPage(driver);
        assertTrue(landingPage.isPageLoaded());
        landingPage.clickOnProfileButton();
        GuestProfilePage profilePage = new GuestProfilePage(driver);
        assertTrue(profilePage.isPageLoaded());
        profilePage.clickOnReservationsButton();
        GuestReservationsPage reservationsPage = new GuestReservationsPage(driver);
        assertTrue(reservationsPage.isPageLoaded());
        assertEquals("Approved", reservationsPage.getTestingAccommodationStatus());
        reservationsPage.clickOnCancelOnTestingAccommodation();
        reservationsPage.acceptDialog();
        reservationsPage.waitForRefresh();
        assertEquals("Cancelled", reservationsPage.getTestingAccommodationStatus());
    }

    @Test
    public void testCancelReservation_ShouldNotCancel_CancellationDeadlinePast(){
        LocalDate referenceDate = LocalDate.now();
        //cancellation deadline is 7
        Accommodation accommodation = accommodationRepository.getReferenceById(3L); // Testing accommodation
        Reservation reservation = new Reservation(null, referenceDate.plusDays(5), referenceDate.minusDays(10), 3, 3, 300, 1L, 7L, accommodation, ReservationStatus.Approved);
        reservationRepository.saveAndFlush(reservation);


        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.clickOnLogin();
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageLoaded());
        loginPage.inputGuestLoginCredentials();
        UserLandingPage landingPage = new UserLandingPage(driver);
        assertTrue(landingPage.isPageLoaded());
        landingPage.clickOnProfileButton();
        GuestProfilePage profilePage = new GuestProfilePage(driver);
        assertTrue(profilePage.isPageLoaded());
        profilePage.clickOnReservationsButton();
        GuestReservationsPage reservationsPage = new GuestReservationsPage(driver);
        assertTrue(reservationsPage.isPageLoaded());
        assertEquals("Approved", reservationsPage.getTestingAccommodationStatus());
        reservationsPage.clickOnCancelOnTestingAccommodation();
        reservationsPage.acceptDialog();
        reservationsPage.waitForRefresh();
        assertEquals("Approved", reservationsPage.getTestingAccommodationStatus());
    }
}
