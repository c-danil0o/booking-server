package com.komsije.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportViewDto {
    private Long id;
    private String reason;
    private String authorEmail;
    private String reportedUserEmail;
    private Long reportedUserId;
    private LocalDateTime date;
}
