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
        List<Guest> guests = guestService.findAll();

        List<GuestDto> guestDtos = new ArrayList<>();
        for (Guest guest : guests) {
            guestDtos.add(new GuestDto(guest));
        }
        return new ResponseEntity<>(guestDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDto> getGuest(@PathVariable Long id) {
        Guest guest = guestService.findOne(id);

        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new GuestDto(guest), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<GuestDto> saveGuest(@RequestBody GuestDto guestDTO) {

        Guest guest = new Guest();
        guest.setEmail(guestDTO.getEmail());
        guest.setPassword(guestDTO.getPassword());
        guest.setBlocked(guestDTO.isBlocked());
        guest.setAccountType(AccountType.Guest);
        guest.setAddress(guestDTO.getAddress());
        guest.setFirstName(guestDTO.getFirstName());
        guest.setLastName(guestDTO.getLastName());
        guest.setPhone(guestDTO.getPhone());
        guest.setTimesCancelled(guestDTO.getTimesCancelled());


        guest = guestService.save(guest);
        return new ResponseEntity<>(new GuestDto(guest), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {

        Guest guest = guestService.findOne(id);

        if (guest != null) {
            guestService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
