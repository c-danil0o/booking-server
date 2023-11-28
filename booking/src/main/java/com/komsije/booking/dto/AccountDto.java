package com.komsije.booking.dto;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import lombok.*;

@Data
public class AccountDto {
    private Long id;
    private String email;
    private String password;
    private boolean isBlocked;
    private AccountType accountType;


}
