package com.komsije.booking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class NewPasswordDto {
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String email;
    @NotEmpty
    private String oldPassword;
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String confirmedPassword;
}
