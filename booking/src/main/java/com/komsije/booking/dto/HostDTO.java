package com.komsije.booking.dto;

import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import com.komsije.booking.model.Host;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostDTO {
    private Long id;
    private String email;
    private String password;
    private boolean isBlocked;
    private Address address;
    private String firstName;
    private String lastName;
    private String phone;

    public HostDTO(Host host){
        this.id=host.getId();
        this.email=host.getEmail();
        this.password=host.getPassword();
        this.isBlocked=host.isBlocked();
        this.address=host.getAddress();
        this.firstName=host.getFirstName();
        this.lastName=host.getLastName();
        this.phone=host.getPhone();
    }
}
