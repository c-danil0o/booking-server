package com.komsije.booking.controller;

import com.komsije.booking.dto.*;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.service.interfaces.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/guests")
public class GuestController {
    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<GuestDto>> getAllGuests() {
        List<GuestDto> guestsDtos = guestService.findAll();
        return new ResponseEntity<>(guestsDtos, HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('Admin','Host')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDto> getGuest(@PathVariable Long id) {
        GuestDto guestDto = guestService.findById(id);
        return new ResponseEntity<>(guestDto, HttpStatus.OK);
    }

/*    @PostMapping(consumes = "application/json")
    public ResponseEntity<GuestDto> saveGuest(@RequestBody GuestDto guestDTO) {
        GuestDto guestDto = guestService.save(guestDTO);
        return new ResponseEntity<>(guestDto, HttpStatus.CREATED);
    }*/

//    @PreAuthorize("hasRole('Admin')")
//    @PreAuthorize("hasAnyRole('Guest', 'Admin')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        guestService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('Guest')")
    @GetMapping(value = "/favorites/{id}")
    public ResponseEntity<List<AccommodationDto>> getFavorites(@PathVariable Long id){
        List<AccommodationDto> favorites = guestService.getFavoritesByGuestId(id);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Guest')")
    @PutMapping(value = "/favorites/{id}")
    public ResponseEntity<List<AccommodationDto>> addToFavorites(@PathVariable Long id, @RequestBody AccommodationDto accommodationDto){
        List<AccommodationDto> favorites = guestService.addToFavorites(id, accommodationDto);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('Guest', 'Admin')")
    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<GuestDto> updateAccount(@RequestBody GuestDto guestDto){
        GuestDto guest = guestService.update(guestDto);
        return new ResponseEntity<>(guest, HttpStatus.OK);
    }

    @PostMapping(value = "/email", consumes = "application/json")
    public ResponseEntity<GuestDto> getByEmail(@RequestBody EmailDto emailDto) {
        GuestDto guestDto = guestService.getByEmail(emailDto.getEmail());
        return new ResponseEntity<>(guestDto, HttpStatus.CREATED);
    }

}
