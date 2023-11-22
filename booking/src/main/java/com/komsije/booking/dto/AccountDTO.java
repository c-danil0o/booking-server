package com.komsije.booking.dto;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String email;
    private boolean isBlocked;
    private AccountType accountType;

    public AccountDTO(Account account) {
        this.id=account.getId();
        this.email=account.getEmail();
        this.isBlocked=account.isBlocked();
        this.accountType=account.getAccountType();
    }

}
