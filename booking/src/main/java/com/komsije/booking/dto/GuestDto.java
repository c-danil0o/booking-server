package com.komsije.booking.dto;

import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
public class GuestDto {
    private Long id;
    @NotNull
    private String email;
    private boolean isBlocked;
    @NotNull
    private AddressDto address;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phone;
    @Min(0)
    private int timesCancelled;
}
