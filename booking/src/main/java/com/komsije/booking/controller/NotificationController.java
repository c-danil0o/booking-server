package com.komsije.booking.controller;

import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notificationDtos = notificationService.findAll();
        return new ResponseEntity<>(notificationDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable Long id) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = notificationService.findById(id);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(notificationDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<NotificationDto> saveNotification(@RequestBody NotificationDto notificationDTO) {
        NotificationDto notificationDto = null;
        try {
            notificationDto = notificationService.save(notificationDTO);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(notificationDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {

        try {
            notificationService.delete(id);
        } catch (HasActiveReservationsException | ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
