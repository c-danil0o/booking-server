package com.komsije.booking.dto;

import com.komsije.booking.model.Role;
import lombok.Data;

@Data
public class RegistrationDto {
    private String email;
    private String password;
    private AddressDto address;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
