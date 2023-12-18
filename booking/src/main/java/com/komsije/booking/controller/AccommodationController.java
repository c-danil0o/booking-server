package com.komsije.booking.controller;

import com.komsije.booking.dto.*;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.model.AccommodationStatus;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.service.interfaces.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "api/accommodations")
public class AccommodationController {
    private final AccommodationService accommodationService;

    @Autowired
    public AccommodationController(AccommodationService accommodationService) {
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
        try {
            AccommodationDto accommodation = accommodationService.findById(id);
            return new ResponseEntity<>(accommodation, HttpStatus.OK);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<List<HostPropertyDto>> getByHostId(@PathVariable Long hostId) {
        try {
            List<HostPropertyDto> accommodations = accommodationService.findByHostId(hostId);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/unapproved")
    public ResponseEntity<List<HostPropertyDto>> getUnapproved() {
        try {
            List<HostPropertyDto> accommodations = accommodationService.getUnapprovedAccommodations();
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<AccommodationDto>> getByLocationGuestNumberAndDate(@RequestParam(required = false) String location, @RequestParam(required = false) Integer guests, @RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate) {
        try {
            List<AccommodationDto> accommodations = accommodationService.getByLocationNumOfGuestsAndDate(location, guests, startDate, endDate);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/amenities")
    public ResponseEntity<List<AccommodationDto>> getByAmenities(@RequestParam List<String> amenities) {
        try {
            List<AccommodationDto> accommodations = accommodationService.getByAmenities(amenities);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('Host','Admin')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccommodationDto> saveAccommodation(@RequestBody AccommodationDto accommodationDTO) {

        AccommodationDto accommodation = null;
        try {
            accommodation = accommodationService.save(accommodationDTO);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(accommodation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('Admin')")
    @PatchMapping(value = "/{id}/approve")
    public ResponseEntity<AccommodationDto> approveAccommodation(@PathVariable("id") Long id) {
        AccommodationDto accommodationDto = null;
        try {
            accommodationDto = accommodationService.findById(id);
            accommodationDto.setStatus(AccommodationStatus.Active);
            accommodationDto = accommodationService.update(accommodationDto);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(accommodationDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Admin')")
    @PatchMapping(value = "/{id}/deny")
    public ResponseEntity<AccommodationDto> denyAccommodation(@PathVariable("id") Long id) {
        AccommodationDto accommodationDto = null;
        try {
            accommodationDto = accommodationService.findById(id);
            accommodationDto.setStatus(AccommodationStatus.Inactive);
            accommodationDto = accommodationService.update(accommodationDto);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(accommodationDto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Host')")
    @PatchMapping(value = "/{id}/changeApproval")
    public ResponseEntity<AccommodationDto> changeApproval(@PathVariable("id") Long id, @RequestParam boolean auto) {
        AccommodationDto accommodationDto = null;
        try {
            accommodationDto = accommodationService.findById(id);
            accommodationDto.setAutoApproval(auto);
            accommodationDto = accommodationService.update(accommodationDto);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(accommodationDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('Host','Admin')")
    @PutMapping(value = "/update")
    public ResponseEntity<AccommodationDto> updateAccommodation(@RequestBody AccommodationDto accommodationDto) {
        AccommodationDto resultAccommodation = null;
        try {
            resultAccommodation = accommodationService.update(accommodationDto);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(resultAccommodation, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Host')")
    @PutMapping(value = "/add-availability/{id}")
    public ResponseEntity<AccommodationDto> updateAvailability(@PathVariable("id") Long id, @RequestBody AvailabilityDto availabilityDto) {
        AccommodationDto accommodationDto = null;
        try {
            accommodationDto = accommodationService.updateAvailability(id, availabilityDto);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(accommodationDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('Host','Admin')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {

        AccommodationDto accommodation = null;
        try {
            accommodationService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ElementNotFoundException | HasActiveReservationsException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/search" , consumes = "application/json")
    public ResponseEntity<List<SearchedAccommodationDto>> searchAccommodations(@RequestBody SearchAccommodationsDto searchAccommodationsDto) {
        try {
            List<SearchedAccommodationDto> accommodations = accommodationService.getSearchedAccommodations(searchAccommodationsDto);
            return new ResponseEntity<>(accommodations, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
