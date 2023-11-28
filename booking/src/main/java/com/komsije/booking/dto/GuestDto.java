package com.komsije.booking.dto;

import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import lombok.*;

@Data
public class GuestDto {
    private Long id;
    private String email;
    private String password;
    private boolean isBlocked;
    private Address address;
    private String firstName;
    private String lastName;
    private String phone;
    private int timesCancelled;
}
