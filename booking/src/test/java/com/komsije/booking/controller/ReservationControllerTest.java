package com.komsije.booking.controller;

import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.dto.NewReservationDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.dto.TokenDto;
import com.komsije.booking.model.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration")
@ActiveProfiles("test")
public class ReservationControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;
    @BeforeEach
    public void login() {
        LoginDto dto = new LoginDto();
        dto.setEmail("guest1@example.com");
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
    public void saveNewReservation_ShouldSavePending_PendingReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        NewReservationDto reservationDto = new NewReservationDto(null, LocalDate.now().plusDays(3), 3, 300, ReservationStatus.Pending, 2L, 6L, 1L, 3);
        ResponseEntity<ReservationDto> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<ReservationDto>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ReservationDto reservationDto1 = responseEntity.getBody();
        
    }

    @Test
    public void saveNewReservation_ShouldSaveApproved_ApprovedReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        NewReservationDto reservationDto = new NewReservationDto(null, LocalDate.now().plusDays(3), 3, 300, ReservationStatus.Approved, 1L, 6L, 1L, 3);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<Void>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void saveNewReservation_ShouldSaveActive_ApprovedReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        NewReservationDto reservationDto = new NewReservationDto(null, LocalDate.now(), 3, 300, ReservationStatus.Approved, 1L, 6L, 1L, 3);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<Void>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void saveNewReservation_ShouldSaveDone_ApprovedReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        NewReservationDto reservationDto = new NewReservationDto(null, LocalDate.now().minusDays(3), 3, 300, ReservationStatus.Approved, 1L, 6L, 1L, 3);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<Void>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void saveNewReservation_ShouldSaveDenied_ApprovedReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        NewReservationDto reservationDto = new NewReservationDto(null, LocalDate.now().minusDays(3), 3, 300, ReservationStatus.Pending, 2L, 6L, 1L, 3);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<Void>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

}
