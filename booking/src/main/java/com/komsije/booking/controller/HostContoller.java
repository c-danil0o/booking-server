package com.komsije.booking.controller;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.EmailDto;
import com.komsije.booking.dto.HostDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.model.Host;
import com.komsije.booking.service.interfaces.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/hosts")
public class HostContoller {
    private final HostService hostService;

    @Autowired
    public HostContoller(HostService hostService) {
        this.hostService = hostService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<HostDto>> getAllHosts() {
        List<HostDto> hostDtos = hostService.findAll();
        return new ResponseEntity<>(hostDtos, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<HostDto> getHost(@PathVariable Long id) {
        HostDto hostDto = null;
        try {
            hostDto = hostService.findById(id);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(hostDto, HttpStatus.OK);
    }


    @PostMapping(consumes = "application/json")
    public ResponseEntity<HostDto> saveHost(@RequestBody HostDto hostDTO) {
        HostDto hostDto = null;
        try {
            hostDto = hostService.save(hostDTO);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(hostDto, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {
        try {
            hostService.delete(id);
        } catch (HasActiveReservationsException | ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<HostDto> updateAccount(@RequestBody HostDto hostDto){
        HostDto host = null;
        try {
            host = hostService.update(hostDto);
        } catch (ElementNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(host, HttpStatus.OK);
    }

    @PostMapping(value = "/email", consumes = "application/json")
    public ResponseEntity<HostDto> getByEmail(@RequestBody EmailDto emailDto) {
        HostDto hostDto = null;
        try {
            hostDto = hostService.getByEmail(emailDto.getEmail());
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(hostDto, HttpStatus.CREATED);
    }


}
