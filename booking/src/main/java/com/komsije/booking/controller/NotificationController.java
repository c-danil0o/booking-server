package com.komsije.booking.controller;

import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Settings;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.NotificationService;
import com.komsije.booking.validators.IdentityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/notifications")
@Validated
public class NotificationController {
    private final NotificationService notificationService;
    private final AccountService accountService;

    @Autowired
    public NotificationController(NotificationService notificationService, AccountService accountService) {
        this.notificationService = notificationService;
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<NotificationDto>> getAllNotifications() {
        List<NotificationDto> notificationDtos = notificationService.findAll();
        return new ResponseEntity<>(notificationDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationDto> getNotification(@IdentityConstraint  @PathVariable Long id) {
        NotificationDto notificationDto = notificationService.findById(id);
        return new ResponseEntity<>(notificationDto, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<NotificationDto>> findByUserId(@IdentityConstraint @PathVariable Long id){
        List<NotificationDto> notifications = notificationService.findAllUserNotifications(id);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
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
    @PostMapping(value = "/settings/{id}")
    public ResponseEntity<Void> applyNotificationSettings(@PathVariable Long id, @RequestBody List<String> settings){
        this.accountService.applySettings(id, settings);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/settings/get/{id}")
    public ResponseEntity<Set<Settings>> getNotificationSettings(@PathVariable Long id){
        Account account = this.accountService.findModelById(id);
        return new ResponseEntity<>(account.getSettings(), HttpStatus.OK);
    }


}
