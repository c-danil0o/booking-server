package com.komsije.booking.controller;

import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.dto.ReservationViewDto;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.GuestService;
import com.komsije.booking.service.interfaces.HostService;
import com.komsije.booking.service.interfaces.ReservationService;
import com.komsije.booking.validators.IdentityConstraint;
import com.komsije.booking.validators.ReservationStatusConstraint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/reservations")
@Validated
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
    public ResponseEntity<List<ReservationViewDto>> getByHostId(@IdentityConstraint  @PathVariable Long id) {
        List<ReservationViewDto> reservationViewDtos = reservationService.getByHostId(id);
        return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/guest/{id}")
    public ResponseEntity<List<ReservationViewDto>> getByGuestId(@IdentityConstraint @PathVariable Long id) {
        List<ReservationViewDto> reservationViewDtos = reservationService.getByGuestId(id);
        return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/decided/host/{id}")
    public ResponseEntity<List<ReservationViewDto>> getDecidedByHostId( @IdentityConstraint @PathVariable Long id) {
        List<ReservationViewDto> reservationViewDtos = reservationService.getDecidedByHostId(id);
        return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/decided/guest/{id}")
    public ResponseEntity<List<ReservationViewDto>> getDecidedByGuestId(@IdentityConstraint @PathVariable Long id) {
        List<ReservationViewDto> reservationViewDtos = reservationService.getDecidedByGuestId(id);
        return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReservationDto> getReservation(@IdentityConstraint @PathVariable Long id) {

        ReservationDto reservationDto = reservationService.findById(id);

        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<List<ReservationDto>> getReservationsByStatus(@ReservationStatusConstraint @RequestParam String reservationStatus) {
        List<ReservationDto> reservationDtos = reservationService.getByReservationStatus(ReservationStatus.valueOf(reservationStatus));
        return new ResponseEntity<>(reservationDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/requests/host/{id}")
    public ResponseEntity<List<ReservationViewDto>> getRequestsByHostId(@IdentityConstraint @PathVariable Long id) {
        List<ReservationViewDto> reservationViewDtos = reservationService.getRequestsByHostId(id);
        return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/requests/guest/{id}")
    public ResponseEntity<List<ReservationViewDto>> getRequestsByGuestId(@IdentityConstraint @PathVariable Long id) {
        List<ReservationViewDto> reservationViewDtos = reservationService.getRequestsByGuestId(id);
        return new ResponseEntity<>(reservationViewDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ReservationDto> saveReservation(@Valid @RequestBody ReservationDto reservationDTO) {
        ReservationDto reservationDto = reservationService.saveNewReservation(reservationDTO);
        return new ResponseEntity<>(reservationDto,HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReservation(@IdentityConstraint @PathVariable Long id) {
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping(value = "/request/{id}")
    public ResponseEntity<Void> deleteReservationRequest(@IdentityConstraint @PathVariable Long id) {
        reservationService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @PreAuthorize("hasRole('Admin')")
    @PatchMapping(value = "/{id}/changeStatus")
    public ResponseEntity<ReservationDto> changeStatus(@IdentityConstraint @PathVariable("id") Long id,@ReservationStatusConstraint @RequestParam String status) {
        ReservationStatus newStatus = ReservationStatus.valueOf(status);
        ReservationDto reservationDto = reservationService.updateStatus(id, newStatus);
        return new ResponseEntity<>(reservationDto, HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Host')")
    @PutMapping(value = "/{id}/approve")
    public ResponseEntity<Void> approveReservationRequest(@IdentityConstraint @PathVariable("id") Long id) {
        reservationService.acceptReservationRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Host')")
    @PutMapping(value = "/{id}/deny")
    public ResponseEntity<Void> denyReservationRequest(@IdentityConstraint @PathVariable("id") Long id) {
        reservationService.denyReservationRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Admin', 'Guest')")
    @PutMapping(value = "/{id}/cancel")
    public ResponseEntity<Void> cancelReservationRequest(@IdentityConstraint @PathVariable("id") Long id) {
        guestService.cancelReservationRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
