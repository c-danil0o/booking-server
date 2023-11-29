package com.komsije.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class NotificationDto {
    private Long id;
    private String message;
    private LocalDateTime date;
    private UserDto receiver;
}
