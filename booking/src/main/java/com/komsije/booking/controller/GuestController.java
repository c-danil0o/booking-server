package com.komsije.booking.controller;

import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Guest;
import com.komsije.booking.service.GuestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/guests")
public class GuestController {
    private final GuestServiceImpl guestService;

    @Autowired
    public GuestController(GuestServiceImpl guestService) {
        this.guestService = guestService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<GuestDto>> getAllGuests() {
        List<GuestDto> guestsDtos = guestService.findAll();
        return new ResponseEntity<>(guestsDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDto> getGuest(@PathVariable Long id) {
        GuestDto guestDto = guestService.findById(id);

        if (guestDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(guestDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<GuestDto> saveGuest(@RequestBody GuestDto guestDTO) {
        GuestDto guestDto = guestService.save(guestDTO);
        return new ResponseEntity<>(guestDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {

        GuestDto guestDto = guestService.findById(id);
        if (guestDto != null) {
            guestService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
