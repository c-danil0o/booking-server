package com.komsije.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmailDto {
    @NotNull
    private String email;
}
