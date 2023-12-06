package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.exceptions.AccountNotActivateException;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.IncorrectPasswordException;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Role;
import com.komsije.booking.service.interfaces.crud.CrudService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AccountService extends CrudService<AccountDto, Long> {
    public List<AccountDto> getByAccountType(Role type);
    public List<AccountDto> getBlockedAccounts();
    public Account getByEmail(String email) throws ElementNotFoundException;
    public AccountDto checkLoginCredentials(LoginDto loginDto) throws ElementNotFoundException, AccountNotActivateException, IncorrectPasswordException;

}
