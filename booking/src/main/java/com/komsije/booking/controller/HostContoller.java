package com.komsije.booking.controller;

import com.komsije.booking.dto.GuestDTO;
import com.komsije.booking.dto.HostDTO;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Guest;
import com.komsije.booking.model.Host;
import com.komsije.booking.service.GuestService;
import com.komsije.booking.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/hosts")
public class HostContoller {
    private final HostService hostService;

    @Autowired
    public HostContoller(HostService hostService) {
        this.hostService = hostService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<HostDTO>> getAllHosts() {
        List<Host> hosts = hostService.findAll();

        List<HostDTO> hostDTOs = new ArrayList<>();
        for (Host host : hosts) {
            hostDTOs.add(new HostDTO(host));
        }
        return new ResponseEntity<>(hostDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HostDTO> getHost(@PathVariable Long id) {
        Host host = hostService.findOne(id);

        if (host == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new HostDTO(host), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<HostDTO> saveHost(@RequestBody HostDTO hostDTO) {

        Host host = new Host();
        host.setEmail(hostDTO.getEmail());
        host.setPassword(hostDTO.getPassword());
        host.setBlocked(hostDTO.isBlocked());
        host.setAccountType(AccountType.Host);
        host.setAddress(hostDTO.getAddress());
        host.setFirstName(hostDTO.getFirstName());
        host.setLastName(hostDTO.getLastName());
        host.setPhone(hostDTO.getPhone());

        host = hostService.save(host);
        return new ResponseEntity<>(new HostDTO(host), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {

        Host host = hostService.findOne(id);

        if (host != null) {
            hostService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
