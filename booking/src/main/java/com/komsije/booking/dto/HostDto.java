package com.komsije.booking.dto;

import com.komsije.booking.model.Address;
import com.komsije.booking.model.Host;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class HostDto {
    private Long id;
    private String email;
    private boolean isBlocked;
    private AddressDto address;
    private String firstName;
    private String lastName;
    private String phone;
}
