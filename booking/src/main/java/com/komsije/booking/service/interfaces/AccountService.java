package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.dto.NewPasswordDto;
import com.komsije.booking.exceptions.AccountNotActivatedException;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.IncorrectPasswordException;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Role;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface AccountService extends CrudService<AccountDto, Long> {
    Account findModelById(Long id) throws ElementNotFoundException;

    public List<AccountDto> getByAccountType(Role type);
    public List<AccountDto> getBlockedAccounts();
    public AccountDto getByEmail(String email) throws ElementNotFoundException;

    Account getModelByEmail(String email) throws ElementNotFoundException;

    String getEmail(Long id);

    public AccountDto checkLoginCredentials(LoginDto loginDto) throws ElementNotFoundException, AccountNotActivatedException, IncorrectPasswordException;
    public void activateAccount(String email);
    public void changePassword(NewPasswordDto newPasswordDto) throws ElementNotFoundException, IncorrectPasswordException;
    public void blockAccount(Long id);

    void applySettings(Long userId, List<String> settings);
}
