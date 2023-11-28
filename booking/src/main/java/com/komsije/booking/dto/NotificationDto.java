package com.komsije.booking.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationDto {
    private Long id;
    private String message;
    private Date date;
    private UserDto receiver;
}
