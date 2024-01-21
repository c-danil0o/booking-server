package com.komsije.booking.dto;

import com.komsije.booking.validators.IdentityConstraint;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PriceRequest {
    @IdentityConstraint
    private Long accommodationId;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private Integer guests;
}
