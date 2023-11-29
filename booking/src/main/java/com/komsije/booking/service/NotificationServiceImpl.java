package com.komsije.booking.service;

import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.mapper.NotificationMapper;
import com.komsije.booking.model.Notification;
import com.komsije.booking.repository.NotificationRepository;
import com.komsije.booking.service.interfaces.NotificationService;
import jakarta.validation.constraints.Null;
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
        try {
            return mapper.toDto(notificationRepository.findById(id).orElseGet(null));
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public List<NotificationDto> findAll() {
        return mapper.toDto(notificationRepository.findAll());
    }

    public NotificationDto save(NotificationDto notificationDto) {
        notificationRepository.save(mapper.fromDto(notificationDto));
        return notificationDto;
    }

    @Override
    public NotificationDto update(NotificationDto notificationDto) {
        Notification notification = notificationRepository.findById(notificationDto.getId()).orElseGet(null);
        if (notification == null){
            return null;
        }
        mapper.update(notification, notificationDto);
        notificationRepository.save(notification);
        return notificationDto;
    }

    public void delete(Long id) {
        notificationRepository.deleteById(id);
    }
}
