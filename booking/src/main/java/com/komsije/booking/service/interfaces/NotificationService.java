package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.model.Notification;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface NotificationService extends CrudService<NotificationDto, Long> {
    List<NotificationDto> findAllUserNotifications(Long userId);

    String saveAndSendNotification(Notification notification);

}
