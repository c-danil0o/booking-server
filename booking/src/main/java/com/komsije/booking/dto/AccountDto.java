package com.komsije.booking.dto;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String email;
    private String password;
    private boolean isBlocked;
    private AccountType accountType;

    public AccountDto(Account account) {
        this.id=account.getId();
        this.email=account.getEmail();
        this.password=account.getPassword();
        this.isBlocked=account.isBlocked();
        this.accountType=account.getAccountType();
    }

}
