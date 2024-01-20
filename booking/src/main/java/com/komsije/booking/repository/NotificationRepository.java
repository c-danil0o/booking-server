package com.komsije.booking.repository;

import com.komsije.booking.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findNotificationsByReceiverId(Long id);
}