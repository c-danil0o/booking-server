package com.komsije.booking.controller;

import com.komsije.booking.dto.HostDto;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Host;
import com.komsije.booking.service.HostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/hosts")
public class HostContoller {
    private final HostServiceImpl hostService;

    @Autowired
    public HostContoller(HostServiceImpl hostService) {
        this.hostService = hostService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<HostDto>> getAllHosts() {
        List<HostDto> hostDtos = hostService.findAll();
        return new ResponseEntity<>(hostDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HostDto> getHost(@PathVariable Long id) {
        HostDto hostDto = hostService.findById(id);
        if (hostDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(hostDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<HostDto> saveHost(@RequestBody HostDto hostDTO) {
        HostDto hostDto = hostService.save(hostDTO);
        return new ResponseEntity<>(hostDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {

        HostDto hostDto = hostService.findById(id);

        if (hostDto != null) {
            hostService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
