package com.komsije.booking.service.interfaces;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface AccountService extends CrudService<Account, Long> {
    public List<Account> getByAccountType(AccountType type);
    public List<Account> getBlockedAccounts();
    public Account getByEmail(String email);
}
