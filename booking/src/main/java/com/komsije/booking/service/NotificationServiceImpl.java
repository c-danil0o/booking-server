package com.komsije.booking.service;

import com.komsije.booking.model.Notification;
import com.komsije.booking.repository.NotificationRepository;
import com.komsije.booking.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;

    }


    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElseGet(null);
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}
