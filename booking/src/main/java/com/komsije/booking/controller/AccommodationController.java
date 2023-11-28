package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AvailabilityDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.service.AccommodationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/accommodations")
public class AccommodationController {
    private final AccommodationServiceImpl accommodationService;

    @Autowired
    public AccommodationController(AccommodationServiceImpl accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccommodationDto>> getAllAccommodations() {
        List<AccommodationDto> accommodations = accommodationService.findAll();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/type")
    public ResponseEntity<List<AccommodationDto>> getByAccommodationType(@RequestParam String type) {
        try {
            List<AccommodationDto> accommodations = accommodationService.getByAccommodationType(AccommodationType.valueOf(type));


            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccommodationDto> getAccommodation(@PathVariable Long id) {

        AccommodationDto accommodation = accommodationService.findById(id);

        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accommodation, HttpStatus.OK);
    }

  /*  @GetMapping(value = "/search")
    public ResponseEntity<List<AccommodationDto>> getByLocationGuestNumberAndDate(@RequestParam String location, @RequestParam int guests) {
        try {
            List<AccommodationDto> accommodations = accommodationService.getByLocationNumOfGuestsAndDate(location,guests);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }*/

    @GetMapping(value = "/amenities")
    public ResponseEntity<List<AccommodationDto>> getByAmenities(@RequestParam List<String> amenities) {
        try {
            List<AccommodationDto> accommodations = accommodationService.getByAmenities(amenities);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationDto> saveAccommodation(@RequestBody AccommodationDto accommodationDTO) {

        AccommodationDto accommodation = accommodationService.save(accommodationDTO);
        return new ResponseEntity<>(accommodation, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}/approve")
    public ResponseEntity<AccommodationDto> approveAccommodation(@PathVariable("id") Long id) {
        AccommodationDto accommodationDto = accommodationService.findById(id);
        if (accommodationDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accommodationDto.setApproved(true);
        accommodationDto = accommodationService.update(accommodationDto);
        return new ResponseEntity<>(accommodationDto, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}/changeApproval")
    public ResponseEntity<AccommodationDto> changeApproval(@PathVariable("id") Long id, @RequestParam boolean auto) {
        AccommodationDto accommodationDto = accommodationService.findById(id);
        if (accommodationDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accommodationDto.setAutoApproval(auto);
        accommodationDto = accommodationService.update(accommodationDto);
        return new ResponseEntity<>(accommodationDto, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<AccommodationDto> updateAccommodation(@RequestBody AccommodationDto accommodationDto) {
        AccommodationDto resultAccommodation = accommodationService.update(accommodationDto);
        if (resultAccommodation==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(resultAccommodation, HttpStatus.OK);
    }

    @PutMapping(value = "/add-availability/{id}")
    public ResponseEntity<AccommodationDto> updateAvailability(@PathVariable ("id") Long id, @RequestBody AvailabilityDto availabilityDto){
        AccommodationDto accommodationDto = accommodationService.updateAvailability(id, availabilityDto);
        if (accommodationDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(accommodationDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {

        AccommodationDto accommodation = accommodationService.findById(id);

        if (accommodation != null) {
            accommodationService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
