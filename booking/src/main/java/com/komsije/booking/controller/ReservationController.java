package com.komsije.booking.controller;

import com.komsije.booking.dto.NewReservationDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.dto.ReservationViewDto;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.GuestService;
import com.komsije.booking.service.interfaces.HostService;
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
    private final GuestService guestService;
    private final AccommodationService accommodationService;
    private final HostService hostService;


    @Autowired
    public ReservationController(ReservationService reservationService, AccommodationService accommodationService, GuestService guestService, AccommodationService accommodationService1, HostService hostService) {
        this.reservationService = reservationService;
        this.guestService = guestService;
        this.accommodationService = accommodationService1;
        this.hostService = hostService;
    }
    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<ReservationViewDto>> getAllReservations() {
        List<ReservationViewDto> reservationDtos = reservationService.getAll();
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/host/{id}")
    public ResponseEntity<List<ReservationViewDto>> getByHostId(@PathVariable Long id) {
        List<ReservationViewDto> reservationViewDtos = reservationService.getByHostId(id);
        return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);

    }

    @GetMapping(value = "/guest/{id}")
    public ResponseEntity<List<ReservationViewDto>> getByGuestId(@PathVariable Long id) {
        List<ReservationViewDto> reservationViewDtos = reservationService.getByGuestId(id);
        return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> getReservation(@PathVariable Long id) {

        ReservationDto reservationDto = reservationService.findById(id);

        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<ReservationDto>> getReservationsByStatus(@RequestParam ReservationStatus reservationStatus) {
        List<ReservationDto> reservationDtos = reservationService.getByReservationStatus(reservationStatus);
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> saveReservation(@RequestBody NewReservationDto reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setReservationStatus(reservationDTO.getReservationStatus());
        reservation.setId(reservationDTO.getId());
        reservation.setGuest(this.guestService.getModelByEmail(reservationDTO.getGuestEmail()));
        reservation.setHost(this.hostService.getModelByEmail(reservationDTO.getHostEmail()));
        reservation.setDays(reservationDTO.getDays());
        reservation.setPrice(reservationDTO.getPrice());
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setAccommodation(accommodationService.findModelById(reservationDTO.getAccommodationId()));
        reservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        reservationService.saveModel(reservation);
        if (reservation.getReservationStatus().equals(ReservationStatus.Approved)){
            accommodationService.reserveTimeslot(reservation.getAccommodation().getId(),reservation.getStartDate(), reservation.getStartDate().plusDays(reservation.getDays()));

        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/request/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@PathVariable Long id) {
        reservationService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @PreAuthorize("hasRole('Admin')")
    @PatchMapping(value = "/{id}/changeStatus")
    public ResponseEntity<ReservationDto> changeStatus(@PathVariable("id") Long id, @RequestParam String status) {
        ReservationStatus newStatus = ReservationStatus.valueOf(status);
        ReservationDto reservationDto = reservationService.updateStatus(id, newStatus);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Host')")
    @PutMapping(value = "/{id}/approve")
    public ResponseEntity<Void> approveReservationRequest(@PathVariable("id") Long id) {
        reservationService.acceptReservationRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Host')")

    @PutMapping(value = "/{id}/deny")
    public ResponseEntity<Void> denyReservationRequest(@PathVariable("id") Long id) {
        reservationService.denyReservationRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Guest')")

    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<Void> cancelReservationRequest(@PathVariable("id") Long id) {

        guestService.cancelReservationRequest(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
