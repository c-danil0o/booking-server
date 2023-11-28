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
        List<Host> hosts = hostService.findAll();

        List<HostDto> hostDtos = new ArrayList<>();
        for (Host host : hosts) {
            hostDtos.add(new HostDto(host));
        }
        return new ResponseEntity<>(hostDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<HostDto> getHost(@PathVariable Long id) {
        Host host = hostService.findById(id);

        if (host == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new HostDto(host), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<HostDto> saveHost(@RequestBody HostDto hostDTO) {

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
        return new ResponseEntity<>(new HostDto(host), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHost(@PathVariable Long id) {

        Host host = hostService.findById(id);

        if (host != null) {
            hostService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
