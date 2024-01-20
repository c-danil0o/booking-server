package com.komsije.booking.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.mapper.NotificationMapper;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Notification;
import com.komsije.booking.model.Settings;
import com.komsije.booking.repository.NotificationRepository;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final NotificationRepository notificationRepository;
    @Autowired
    private NotificationMapper mapper;

    @Autowired
    NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    public NotificationDto findById(Long id) throws ElementNotFoundException {
        Notification notification = notificationRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("Element with given ID doesn't exist!"));
        return mapper.toDto(notification);
    }

    public List<NotificationDto> findAll() {
        return mapper.toDto(notificationRepository.findAll());
    }

    public NotificationDto save(NotificationDto notificationDto) {
        notificationRepository.save(mapper.fromDto(notificationDto));
        return notificationDto;
    }

    @Override
    public List<NotificationDto> findAllUserNotifications(Long userId) {
        return mapper.toDto(this.notificationRepository.findNotificationsByReceiverId(userId));
    }

    @Override
    public NotificationDto update(NotificationDto notificationDto) throws ElementNotFoundException {
        Notification notification = notificationRepository.findById(notificationDto.getId()).orElseThrow(() -> new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(notification, notificationDto);
        notificationRepository.save(notification);
        return notificationDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
        } else {
            throw new ElementNotFoundException("Element with given ID doesn't exist!");
        }
    }
    @Override
    public String saveAndSendNotification(Notification notification) {
        notificationRepository.save(notification);
        this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + notification.getReceiver().getId(),
                mapper.toDto(notification));
        // android
        String topic = notification.getReceiver().getId().toString();
        Message message = Message.builder()
                .putData("message", notification.getMessage())
                .putData("date", notification.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm")))
                .setTopic(topic)
                .build();

        try {
            return FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            System.out.println("Sending message to firebase failed!");
            return null;
        }

    }


}
