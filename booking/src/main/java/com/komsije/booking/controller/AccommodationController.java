package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDTO;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.service.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
