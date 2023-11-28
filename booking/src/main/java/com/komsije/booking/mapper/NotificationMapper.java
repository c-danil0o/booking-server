package com.komsije.booking.mapper;

import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserDtoMapper.class})
public interface NotificationMapper {
    NotificationDto toDto(Notification notification);
    Notification fromDto(NotificationDto notificationDto);
    List<NotificationDto> toDto(List<Notification> notificationList);
    void update(@MappingTarget Notification notification, NotificationDto notificationDto);
}
