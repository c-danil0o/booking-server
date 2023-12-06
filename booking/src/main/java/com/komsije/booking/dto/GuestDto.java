package com.komsije.booking.dto;

import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class GuestDto {
    private Long id;
    private String email;
    private String password;
    private boolean isBlocked;
    private AddressDto address;
    private String firstName;
    private String lastName;
    private String phone;
    private int timesCancelled;
}
