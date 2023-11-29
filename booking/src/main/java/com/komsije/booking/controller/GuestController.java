package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Guest;
import com.komsije.booking.service.GuestServiceImpl;
import com.komsije.booking.service.interfaces.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/guests")
public class GuestController {
    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<GuestDto>> getAllGuests() {
        List<GuestDto> guestsDtos = guestService.findAll();
        return new ResponseEntity<>(guestsDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDto> getGuest(@PathVariable Long id) {
        GuestDto guestDto = null;
        try {
            guestDto = guestService.findById(id);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(guestDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<GuestDto> saveGuest(@RequestBody GuestDto guestDTO) {
        GuestDto guestDto = null;
        try {
            guestDto = guestService.save(guestDTO);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(guestDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {

        try {
            guestService.delete(id);
        } catch (HasActiveReservationsException | ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/favorites/{id}")
    public ResponseEntity<List<AccommodationDto>> getFavorites(@PathVariable Long id){
        List<AccommodationDto> favorites = null;
        try {
            favorites = guestService.getFavoritesByGuestId(id);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    @PutMapping(value = "/favorites/{id}")
    public ResponseEntity<List<AccommodationDto>> addToFavorites(@PathVariable Long id, @RequestBody AccommodationDto accommodationDto){
        List<AccommodationDto> favorites = null;
        try {
            favorites = guestService.addToFavorites(id, accommodationDto);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

}
