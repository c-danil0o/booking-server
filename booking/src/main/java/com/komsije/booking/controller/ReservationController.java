package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.service.AccommodationServiceImpl;
import com.komsije.booking.service.ReservationServiceImpl;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/reservations")
public class ReservationController {
    private final ReservationService reservationService;

//    private final AccommodationService accommodationService;

    @Autowired
    public ReservationController(ReservationService reservationService, AccommodationService accommodationService) {
        this.reservationService = reservationService;
//        this.accommodationService = accommodationService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        List<ReservationDto> reservationDtos = reservationService.findAll();
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {
        ReservationDto reservationDto = reservationService.findById(id);

        if (reservationDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<ReservationDto>> getReservationsByStatus(@RequestParam ReservationStatus reservationStatus) {
        try {
            List<ReservationDto> reservationDtos = reservationService.getByReservationStatus(reservationStatus);
            return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReservationDto> saveReservation(@RequestBody ReservationDto reservationDTO) {
        ReservationDto reservationDto = reservationService.save(reservationDTO);
        return new ResponseEntity<>(reservationDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        ReservationDto reservationDto = reservationService.findById(id);

        if (reservationDto != null) {
            reservationService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/request/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {

        if (reservationService.deleteRequest(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/{id}/changeStatus")
    public ResponseEntity<ReservationDto> changeStatus(@PathVariable("id") Long id, @RequestParam String status) {
        try{
            ReservationStatus newStatus = ReservationStatus.valueOf(status);
            ReservationDto reservationDto = reservationService.updateStatus(id, newStatus);
            if (reservationDto == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(reservationDto, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping(value = "/{id}/approve")
    public ResponseEntity<Void> approveReservationRequest(@PathVariable("id")Long id){
        if (reservationService.acceptReservationRequest(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
