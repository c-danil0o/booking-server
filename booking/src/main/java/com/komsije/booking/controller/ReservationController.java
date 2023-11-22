package com.komsije.booking.controller;

import com.komsije.booking.dto.HostDTO;
import com.komsije.booking.dto.ReservationDTO;
import com.komsije.booking.model.Host;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.service.HostService;
import com.komsije.booking.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(){
        List<Reservation> reservations = reservationService.findAll();

        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation reservation : reservations) {
            reservationDTOs.add(new ReservationDTO(reservation));
        }
        return new ResponseEntity<>(reservationDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.findOne(id);

        if (reservation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.OK);
    }

}
