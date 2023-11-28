package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDto;
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

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationDto> saveAccommodation(@RequestBody AccommodationDto accommodationDTO) {

        AccommodationDto accommodation = accommodationService.save(accommodationDTO);
        return new ResponseEntity<>(accommodation, HttpStatus.CREATED);
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
