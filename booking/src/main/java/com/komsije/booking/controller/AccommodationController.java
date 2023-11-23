package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDTO;
import com.komsije.booking.dto.AccountDTO;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Account;
import com.komsije.booking.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/accommodations")
public class AccommodationController {
    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations(){
        List<Accommodation> accommodations = accommodationService.findAll();

        List<AccommodationDTO> accommodationDTOs = new ArrayList<>();
        for (Accommodation accommodation : accommodations) {
            accommodationDTOs.add(new AccommodationDTO(accommodation));
        }
        return new ResponseEntity<>(accommodationDTOs, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationDTO> getAccommodation(@PathVariable Long id) {

        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccommodationDTO(accommodation), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationDTO> saveAccommodation(@RequestBody AccommodationDTO accommodationDTO) {

        Accommodation accommodation = new Accommodation();
//        accommodation.setId(accommodationDTO.getId());
        accommodation.setName(accommodationDTO.getName());
        accommodation.setDescription(accommodationDTO.getDescription());
        accommodation.setAddress(accommodationDTO.getAddress());
        accommodation.setAccommodationType(accommodationDTO.getAccommodationType());
        accommodation.setAmenities(accommodationDTO.getAmenities());
        accommodation.setMaxGuests(accommodationDTO.getMaxGuests());
        accommodation.setMinGuests(accommodationDTO.getMinGuests());
        accommodation.setPhotos(accommodationDTO.getPhotos());
        accommodation.setPricePerGuest(accommodationDTO.isPricePerGuest());
        accommodation.setCancellationDeadline(accommodationDTO.getCancellationDeadline());
        accommodation.setAutoApproval(true);
        accommodation.setAverageGrade(accommodationDTO.getAverageGrade());

        accommodation = accommodationService.save(accommodation);
        return new ResponseEntity<>(new AccommodationDTO(accommodation), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {

        Accommodation accommodation = accommodationService.findOne(id);

        if (accommodation != null) {
            accommodationService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
