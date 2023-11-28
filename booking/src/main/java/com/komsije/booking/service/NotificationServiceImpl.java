package com.komsije.booking.service;

import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.mapper.NotificationMapper;
import com.komsije.booking.model.Notification;
import com.komsije.booking.repository.NotificationRepository;
import com.komsije.booking.service.interfaces.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private NotificationMapper mapper;

    @Autowired
    NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;

    }


    public NotificationDto findById(Long id) {
        return mapper.toDto(notificationRepository.findById(id).orElseGet(null));
    }

    public List<NotificationDto> findAll() {
        return mapper.toDto(notificationRepository.findAll());
    }

    public NotificationDto save(NotificationDto notificationDto) {
        notificationRepository.save(mapper.fromDto(notificationDto));
        return notificationDto;
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}