package com.komsije.booking.dto;

import lombok.Data;

@Data
public class NewPasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmedPassword;
}
