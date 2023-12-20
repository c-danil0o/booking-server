package com.komsije.booking.controller;

import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notificationDtos = notificationService.findAll();
        return new ResponseEntity<>(notificationDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationDto> getNotification(@PathVariable Long id) {
        NotificationDto notificationDto = notificationService.findById(id);
        return new ResponseEntity<>(notificationDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<NotificationDto> saveNotification(@RequestBody NotificationDto notificationDTO) {

        NotificationDto notificationDto = notificationService.save(notificationDTO);

        return new ResponseEntity<>(notificationDto, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
