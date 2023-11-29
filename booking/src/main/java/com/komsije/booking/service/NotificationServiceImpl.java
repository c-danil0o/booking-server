package com.komsije.booking.service;

import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
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
    @Autowired
    private NotificationMapper mapper;

    @Autowired
    NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    public NotificationDto findById(Long id) throws ElementNotFoundException {
        Notification notification = notificationRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
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
    public NotificationDto update(NotificationDto notificationDto) throws ElementNotFoundException {
        Notification notification = notificationRepository.findById(notificationDto.getId()).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(notification, notificationDto);
        notificationRepository.save(notification);
        return notificationDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        if (notificationRepository.existsById(id)){
            notificationRepository.deleteById(id);
        }else{
           throw new ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }
}
