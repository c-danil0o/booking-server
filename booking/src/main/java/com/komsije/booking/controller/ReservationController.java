package com.komsije.booking.controller;

import com.komsije.booking.dto.HostPropertyDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.dto.ReservationViewDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.exceptions.PendingReservationException;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<ReservationViewDto>> getAllReservations() {
        List<ReservationViewDto> reservationDtos = reservationService.getAll();
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/host/{id}")
    public ResponseEntity<List<ReservationViewDto>> getByHostId(@PathVariable Long id) {
        try {
            List<ReservationViewDto> reservationViewDtos = reservationService.getByHostId(id);
            return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/guest/{id}")
    public ResponseEntity<List<ReservationViewDto>> getByGuestId(@PathVariable Long id) {
        try {
            List<ReservationViewDto> reservationViewDtos = reservationService.getByGuestId(id);
            return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {
        ReservationDto reservationDto = null;
        try {
            reservationDto = reservationService.findById(id);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        ReservationDto reservationDto = null;
        try {
            reservationDto = reservationService.save(reservationDTO);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reservationDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {

        try {
            reservationService.delete(id);
        } catch (HasActiveReservationsException | ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/request/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {

        try {
            reservationService.deleteRequest(id);
        } catch (ElementNotFoundException | PendingReservationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PatchMapping(value = "/{id}/changeStatus")
    public ResponseEntity<ReservationDto> changeStatus(@PathVariable("id") Long id, @RequestParam String status) {
        try {
            ReservationStatus newStatus = ReservationStatus.valueOf(status);
            ReservationDto reservationDto = reservationService.updateStatus(id, newStatus);
            return new ResponseEntity<>(reservationDto, HttpStatus.OK);
        } catch (IllegalArgumentException | ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping(value = "/{id}/approve")
    public ResponseEntity<Void> approveReservationRequest(@PathVariable("id") Long id) {
        try {
            reservationService.acceptReservationRequest(id);
        } catch (ElementNotFoundException | PendingReservationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/deny")
    public ResponseEntity<Void> denyReservationRequest(@PathVariable("id") Long id) {
        try {
            reservationService.denyReservationRequest(id);
        } catch (ElementNotFoundException | PendingReservationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<Void> cancelReservationRequest(@PathVariable("id") Long id) {
        try {
            reservationService.cancelReservationRequest(id);
        } catch (ElementNotFoundException | PendingReservationException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
