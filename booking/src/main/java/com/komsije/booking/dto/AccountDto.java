package com.komsije.booking.dto;

import com.komsije.booking.model.Role;
import jakarta.persistence.Transient;
import lombok.*;

@Data
public class AccountDto {
    private Long id;
    private String email;
    private String password;
    private boolean isBlocked;
    private Role role;
    private boolean isActivated;
    @Transient
    private String jwt;
}
