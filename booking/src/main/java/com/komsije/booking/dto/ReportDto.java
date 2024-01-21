package com.komsije.booking.dto;

import com.komsije.booking.validators.IdentityConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ReportDto {
    private Long id;
    @NotNull
    private String reason;
    @IdentityConstraint
    private Long authorId;
    @IdentityConstraint
    private Long reportedUserId;
    private LocalDateTime date;
}
