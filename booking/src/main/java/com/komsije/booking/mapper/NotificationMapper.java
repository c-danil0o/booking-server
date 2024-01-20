package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.NotificationDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Notification;
import com.komsije.booking.repository.NotificationRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AccountMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class NotificationMapper {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AccountMapper accountMapper;
    public NotificationDto toDto(Notification notification) {
        if ( notification == null ) {
            return null;
        }

        NotificationDto notificationDto = new NotificationDto();

        notificationDto.setId( notification.getId() );
        notificationDto.setMessage( notification.getMessage() );
        notificationDto.setDate( notification.getDate() );
        try {
            notificationDto.setReceiver(notification.getReceiver().getId());
        }
        catch ( ElementNotFoundException e ) {
            throw new RuntimeException( e );
        }

        return notificationDto;
    }

    public Notification fromDto(NotificationDto notificationDto) {
        if ( notificationDto == null ) {
            return null;
        }
        if (notificationRepository.existsById(notificationDto.getId())){
            return notificationRepository.findById(notificationDto.getId()).orElse(null);
        }

        Notification notification = new Notification();

        notification.setId( notificationDto.getId() );
        notification.setMessage( notificationDto.getMessage() );
        notification.setDate( notificationDto.getDate() );
        AccountDto accountDto = new AccountDto();
        accountDto.setId(notification.getReceiver().getId());
        try {
            notification.setReceiver(accountMapper.fromDto(accountDto));
        }
        catch ( ElementNotFoundException e ) {
            throw new RuntimeException( e );
        }

        return notification;
    }
    public abstract List<NotificationDto> toDto(List<Notification> notificationList);
    public abstract void update(@MappingTarget Notification notification, NotificationDto notificationDto);
    Account map(Long value){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(value);
        return this.accountMapper.fromDto(accountDto);
    }
}
