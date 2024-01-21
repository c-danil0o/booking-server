package com.komsije.booking.dto;

import com.komsije.booking.model.Role;
import com.komsije.booking.validators.IdentityConstraint;
import jakarta.persistence.Transient;
import lombok.*;

@Data
public class AccountDto {
    @IdentityConstraint
    private Long id;
    private String email;
    private boolean isBlocked;
    private Role role;
    private boolean isActivated;
    @Transient
    private String jwt;
}
