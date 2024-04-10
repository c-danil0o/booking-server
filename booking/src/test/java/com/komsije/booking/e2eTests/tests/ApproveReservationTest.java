package com.komsije.booking.e2eTests.tests;

import com.komsije.booking.e2eTests.pages.*;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.repository.AccommodationRepository;
import com.komsije.booking.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApproveReservationTest extends TestBase{
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Test
    public void testApproveReservation(){
        LocalDate referenceDate = LocalDate.now();
        Accommodation accommodation = accommodationRepository.getReferenceById(3L); // Testing accommodation
        Reservation reservation = new Reservation(null, referenceDate.plusDays(5), referenceDate, 3, 3, 300, 2L, 7L, accommodation, ReservationStatus.Pending);
        Reservation reservation2 = new Reservation(null, referenceDate.plusDays(3), referenceDate, 7, 3, 600, 2L, 8L, accommodation, ReservationStatus.Pending);
        Reservation reservation3 = new Reservation(null, referenceDate.plusDays(7), referenceDate, 3, 3, 500, 2L, 9L, accommodation, ReservationStatus.Pending);
        reservationRepository.saveAndFlush(reservation);
        reservationRepository.saveAndFlush(reservation2);
        reservationRepository.saveAndFlush(reservation3);
        //testing reservation has price 300


        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isOpen());
        homePage.clickOnLogin();
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageLoaded());
        loginPage.inputHostLoginCredentials();
        UserLandingPage landingPage = new UserLandingPage(driver);
        assertTrue(landingPage.isPageLoaded());
        landingPage.clickOnProfileButton();
        HostProfilePage hostProfilePage = new HostProfilePage(driver);
        assertTrue(hostProfilePage.isPageLoaded());
        hostProfilePage.clickOnReservationsButton();
        HostReservationsPage hostReservationsPage = new HostReservationsPage(driver);
        assertTrue(hostReservationsPage.isPageLoaded());
        assertEquals("Pending", hostReservationsPage.getTestingAccommodationStatus());
        hostReservationsPage.clickOnApproveOnTestingAccommodation();
        hostReservationsPage.acceptDialog();
        hostReservationsPage.waitForRefresh();
        assertEquals("Approved",hostReservationsPage.getTestingAccommodationStatus());
        assertEquals(2, hostReservationsPage.countDeniedReservations());
    }
}
