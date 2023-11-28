package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface AccountService extends CrudService<AccountDto, Long> {
    public List<AccountDto> getByAccountType(AccountType type);
    public List<AccountDto> getBlockedAccounts();
    public Account getByEmail(String email);
}
