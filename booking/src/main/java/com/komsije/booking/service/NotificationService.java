package com.komsije.booking.service;

import com.komsije.booking.model.Notification;
import com.komsije.booking.model.Report;
import com.komsije.booking.repository.NotificationRepository;
import com.komsije.booking.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;

    }


    public Notification findOne(Long id) {
        return notificationRepository.findById(id).orElseGet(null);
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public void remove(Long id) {
        notificationRepository.deleteById(id);
    }
}
