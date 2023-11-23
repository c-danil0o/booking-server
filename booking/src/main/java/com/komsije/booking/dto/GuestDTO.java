package com.komsije.booking.dto;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {
    private Long id;
    private String email;
    private String password;
    private boolean isBlocked;
    private Address address;
    private String firstName;
    private String lastName;
    private String phone;
    private int timesCancelled;

    public GuestDTO(Guest guest){
        this.id=guest.getId();
        this.email=guest.getEmail();
        this.password=guest.getPassword();
        this.isBlocked=guest.isBlocked();
        this.address=guest.getAddress();
        this.firstName=guest.getFirstName();
        this.lastName=guest.getLastName();
        this.phone=guest.getPhone();
        this.timesCancelled=guest.getTimesCancelled();
    }
}
