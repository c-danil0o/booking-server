package com.komsije.booking.controller;

import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.dto.NewReservationDto;
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
    public void saveNewReservation(){
        LocalDate startDate = LocalDate.of(2024,2,3);
        NewReservationDto reservationDto = new NewReservationDto(null, LocalDate.now().plusDays(3), 3, 300, ReservationStatus.Pending, 1L, 6L, 1L, 3);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                new HttpEntity<>(reservationDto, getHttpHeaders()),
                new ParameterizedTypeReference<Void>() {
                });
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

    }

}
