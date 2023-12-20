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
        HostDto hostDto = hostService.findById(id);
        return new ResponseEntity<>(hostDto, HttpStatus.OK);
    }


/*    @PostMapping(consumes = "application/json")
    public ResponseEntity<HostDto> saveHost(@RequestBody HostDto hostDTO) {
        HostDto hostDto = hostService.save(hostDTO);
        return new ResponseEntity<>(hostDto, HttpStatus.CREATED);
    }*/

//    @PreAuthorize("hasRole('Admin')")
    @PreAuthorize("hasAnyRole('Host', 'Admin')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {
        hostService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('Host', 'Admin')")
    @PutMapping(value = "/update", consumes = "application/json")
    public ResponseEntity<HostDto> updateAccount(@RequestBody HostDto hostDto){
        HostDto host = hostService.update(hostDto);
        return new ResponseEntity<>(host, HttpStatus.OK);
    }

    @PostMapping(value = "/email", consumes = "application/json")
    public ResponseEntity<HostDto> getByEmail(@RequestBody EmailDto emailDto) {
        HostDto hostDto = hostService.getByEmail(emailDto.getEmail());
        return new ResponseEntity<>(hostDto, HttpStatus.CREATED);
    }


}
