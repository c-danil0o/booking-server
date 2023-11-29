package com.komsije.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReportDto {
    private Long id;
    private String reason;
    private UserDto author;
    private UserDto reportedUser;
    private LocalDateTime date;

}
