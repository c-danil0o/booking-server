package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDTO;
import com.komsije.booking.dto.GuestDTO;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Guest;
import com.komsije.booking.service.AccommodationService;
import com.komsije.booking.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/guests")
public class GuestController {
    @Autowired
    private GuestService guestService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<GuestDTO>> getAllGuests(){
        List<Guest> guests = guestService.findAll();

        List<GuestDTO> guestDTOs = new ArrayList<>();
        for (Guest guest : guests) {
            guestDTOs.add(new GuestDTO(guest));
        }
        return new ResponseEntity<>(guestDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable Long id) {
        Guest guest = guestService.findOne(id);

        if (guest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new GuestDTO(guest), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<GuestDTO> saveAccommodation(@RequestBody GuestDTO guestDTO) {

        Guest guest = new Guest();
        guest.setId(guestDTO.getId());
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
        return new ResponseEntity<>(new GuestDTO(guest), HttpStatus.CREATED);
    }
}
