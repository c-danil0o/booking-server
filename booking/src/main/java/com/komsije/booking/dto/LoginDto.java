package com.komsije.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @Email
    private String email;
    @NotEmpty
    private String password;
}
