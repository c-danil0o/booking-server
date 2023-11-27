package com.komsije.booking.controller;

import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.model.*;
import com.komsije.booking.service.AccommodationServiceImpl;
import com.komsije.booking.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reservations")
public class ReservationController {
    private final ReservationServiceImpl reservationService;

    private final AccommodationServiceImpl accommodationService;

    @Autowired
    public ReservationController(ReservationServiceImpl reservationService, AccommodationServiceImpl accommodationService) {
        this.reservationService = reservationService;
        this.accommodationService = accommodationService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAll();

        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDtos.add(new ReservationDto(reservation));
        }
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findOne(id);

        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ReservationDto(reservation), HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<ReservationDto>> getReservationsByStatus(@RequestParam ReservationStatus reservationStatus){
        try{
            List<Reservation> reservations = reservationService.getByReservationStatus(reservationStatus);

            List<ReservationDto> reservationDtos = new ArrayList<>();
            for (Reservation reservation : reservations) {
                reservationDtos.add(new ReservationDto(reservation));
            }
            return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReservationDto> saveReservation(@RequestBody ReservationDto reservationDTO) {

        Reservation reservation = new Reservation();
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setDays(reservationDTO.getDays());
        reservation.setPrice(reservationDTO.getPrice());
        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setAccommodation(accommodationService.findOne(reservationDTO.getAccommodationId()));
        reservation = reservationService.save(reservation);
        return new ResponseEntity<>(new ReservationDto(reservation), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        Reservation reservation = reservationService.findOne(id);

        if (reservation != null) {
            reservationService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
