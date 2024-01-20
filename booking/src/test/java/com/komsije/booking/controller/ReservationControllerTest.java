package com.komsije.booking.controller;

import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.dto.NewReservationDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.dto.TokenDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.repository.AccommodationRepository;
import com.komsije.booking.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration")
@ActiveProfiles("test")
public class ReservationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;

    private String accessToken;
    @BeforeEach
    public void login() {
        LoginDto dto = new LoginDto();
        dto.setEmail("admin@example.com");
        dto.setPassword("123456");
        ResponseEntity<TokenDto> responseEntity = restTemplate.exchange("/api/login",
                HttpMethod.POST,
                new HttpEntity<>(dto),
                new ParameterizedTypeReference<TokenDto>() {
                });
        System.out.println(responseEntity.getBody().getToken());
        this.accessToken = "Bearer " + responseEntity.getBody().getToken();
    }
    private HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        return headers;
    }

    @Test
    @Rollback
    public void saveNewReservation_ShouldSavePending_PendingReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        ReservationDto reservationDto = new ReservationDto(null, startDate, LocalDate.now(), 3, 300, ReservationStatus.Pending, 2L, 6L, 1L, 3);
        ResponseEntity<ReservationDto> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<ReservationDto>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ReservationDto returnedReservation = responseEntity.getBody();
        assertThat(reservationDto).usingRecursiveComparison().ignoringFields("id", "reservationStatus").isEqualTo(returnedReservation);
        assertEquals(returnedReservation.getReservationStatus(), ReservationStatus.Pending);
    }

    @Test
    @Rollback
    public void saveNewReservation_ShouldSaveApproved_ApprovedReservation(){
        LocalDate startDate = LocalDate.of(2024,2,20);
        ReservationDto reservationDto = new ReservationDto(null, startDate, LocalDate.now(), 3, 300, ReservationStatus.Approved, 1L, 6L, 1L, 3);
        ResponseEntity<ReservationDto> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<ReservationDto>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ReservationDto returnedReservation = responseEntity.getBody();
        assertThat(reservationDto).usingRecursiveComparison().ignoringFields("id", "reservationStatus").isEqualTo(returnedReservation);
        assertEquals(returnedReservation.getReservationStatus(), ReservationStatus.Approved);
    }

    @Test
    @Rollback
    public void saveNewReservation_ShouldSaveApproved_PendingReservationAutoApprovalAccommodation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        ReservationDto reservationDto = new ReservationDto(null, startDate, LocalDate.now(), 3, 300, ReservationStatus.Pending, 1L, 6L, 1L, 3);
        ResponseEntity<ReservationDto> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<ReservationDto>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ReservationDto returnedReservation = responseEntity.getBody();
        assertThat(reservationDto).usingRecursiveComparison().ignoringFields("id", "reservationStatus").isEqualTo(returnedReservation);
        assertEquals(returnedReservation.getReservationStatus(), ReservationStatus.Approved);
    }

    @Test
    @Rollback
    public void saveNewReservation_ShouldSaveActive_ApprovedReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        ReservationDto reservationDto = new ReservationDto(null, LocalDate.now(), LocalDate.now(), 3, 300, ReservationStatus.Approved, 1L, 6L, 1L, 3);
        ResponseEntity<ReservationDto> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<ReservationDto>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ReservationDto returnedReservation = responseEntity.getBody();
        assertThat(reservationDto).usingRecursiveComparison().ignoringFields("id", "reservationStatus").isEqualTo(returnedReservation);
        assertEquals(returnedReservation.getReservationStatus(), ReservationStatus.Active);
    }


    @Test
    @Rollback
    public void saveNewReservation_ShouldSaveDenied_ApprovedReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        ReservationDto reservationDto = new ReservationDto(null, LocalDate.now(), LocalDate.now(), 3, 300, ReservationStatus.Pending, 2L, 6L, 1L, 3);
        ResponseEntity<ReservationDto> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<ReservationDto>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ReservationDto returnedReservation = responseEntity.getBody();
        assertThat(reservationDto).usingRecursiveComparison().ignoringFields("id", "reservationStatus").isEqualTo(returnedReservation);
        assertEquals(returnedReservation.getReservationStatus(), ReservationStatus.Denied);
    }



    @Test
    @Rollback
    public void approveReservationRequest_ShouldThrowNotFound(){
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/reservations/233/approve",
                HttpMethod.PUT,
                new HttpEntity<>(getHttpHeaders()),
                new ParameterizedTypeReference<Void>() {
                });
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @Rollback
    public void denyReservationRequest_ShouldThrowNotFound(){
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/reservations/233/deny",
                HttpMethod.PUT,
                new HttpEntity<>(getHttpHeaders()),
                new ParameterizedTypeReference<Void>() {
                });
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @Rollback
    public void approveReservationRequest_ShouldReturnPendingReservationException(){
        long VALID_RESERVATION_ID = 3L;
        ResponseEntity<Object> responseEntity = restTemplate.exchange("/api/reservations/"+VALID_RESERVATION_ID+"/approve",
                HttpMethod.PUT,
                new HttpEntity<>(getHttpHeaders()),
                new ParameterizedTypeReference<Object>() {
                });
        assertEquals(HttpStatus.I_AM_A_TEAPOT, responseEntity.getStatusCode());
        LinkedHashMap apiError = (LinkedHashMap) responseEntity.getBody();
        assertEquals("Reservation is not in pending state!", apiError.get("message"));
    }

    @Test
    @Rollback
    public void approveReservationRequest_ShouldSetReservationStatusToApproved(){
        Long VALID_RESERVATION_ID = null;
        Accommodation accommodation = accommodationRepository.findById(1L).orElse(null);
        Reservation reservation = new Reservation(null, LocalDate.now().plusDays(5), LocalDate.now(), 3, 3, 300, 1L, 6L,accommodation , ReservationStatus.Pending);
        VALID_RESERVATION_ID = reservationRepository.saveAndFlush(reservation).getId();
        ResponseEntity<Object> responseEntity = restTemplate.exchange("/api/reservations/"+VALID_RESERVATION_ID+"/approve",
                HttpMethod.PUT,
                new HttpEntity<>(getHttpHeaders()),
                new ParameterizedTypeReference<Object>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Reservation updatedReservation = reservationRepository.findById(VALID_RESERVATION_ID).orElse(null);
        assertEquals(ReservationStatus.Approved, updatedReservation.getReservationStatus());
    }

    @Test
    @Rollback
    public void approveReservationRequest_ShouldSetReservationStatusToDenied(){
        Long VALID_RESERVATION_ID = null;
        Accommodation accommodation = accommodationRepository.findById(1L).orElse(null);
        Reservation reservation = new Reservation(null, LocalDate.now().plusDays(5), LocalDate.now(), 3, 3, 300, 1L, 6L,accommodation , ReservationStatus.Pending);
        VALID_RESERVATION_ID = reservationRepository.saveAndFlush(reservation).getId();
        ResponseEntity<Object> responseEntity = restTemplate.exchange("/api/reservations/"+VALID_RESERVATION_ID+"/deny",
                HttpMethod.PUT,
                new HttpEntity<>(getHttpHeaders()),
                new ParameterizedTypeReference<Object>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Reservation updatedReservation = reservationRepository.findById(VALID_RESERVATION_ID).orElse(null);
        assertEquals(ReservationStatus.Denied, updatedReservation.getReservationStatus());
    }

}
