package com.komsije.booking.service;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.exceptions.AccountNotActivateException;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.exceptions.IncorrectPasswordException;
import com.komsije.booking.mapper.AccountMapper;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Role;
import com.komsije.booking.repository.AccountRepository;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final ReservationService reservationService;
    @Autowired
    private AccountMapper mapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ReservationService reservationService) {
        this.accountRepository = accountRepository;
        this.reservationService = reservationService;
    }

    public AccountDto findById(Long id) throws ElementNotFoundException {
            return mapper.toDto(accountRepository.findById(id).orElseThrow(()->new ElementNotFoundException("Element with given ID doesn't exist!")));
    }

    public List<AccountDto> findAll() {
        return mapper.toDto(accountRepository.findAll());
    }

    public AccountDto save(AccountDto accountDto) {
        accountRepository.save(mapper.fromDto(accountDto));
        return accountDto;
    }

    @Override
    public AccountDto update(AccountDto accountDto) throws  ElementNotFoundException {
        Account account = accountRepository.findById(accountDto.getId()).orElseThrow(()->new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(account, accountDto);
        accountRepository.save(account);
        return accountDto;
    }

    public void delete(Long id) throws NoSuchElementException, HasActiveReservationsException {
        Account account = accountRepository.findById(id).orElseThrow();
        if (!reservationService.hasActiveReservations(id)){
            accountRepository.deleteById(id);
        }else{
            throw new HasActiveReservationsException("Account has active reservations and can't be deleted!");
        }
    }

    public List<AccountDto> getByAccountType(Role type) {
        return mapper.toDto(accountRepository.findAccountByRole(type));
    }

    public List<AccountDto> getBlockedAccounts() {
        return mapper.toDto(accountRepository.findAccountByIsBlocked(true));
    }

    @Override
    public Account getByEmail(String email) throws ElementNotFoundException {
        Account account = accountRepository.getAccountByEmail(email);
        if (account == null){
            throw new ElementNotFoundException("Account with given email doesn't exit!");
        }
        return account;
    }

    @Override
    public AccountDto checkLoginCredentials(LoginDto loginDto) throws ElementNotFoundException, AccountNotActivateException, IncorrectPasswordException {
        Account account = accountRepository.getAccountByEmail(loginDto.getEmail());
        if (account == null){
            throw new ElementNotFoundException("Account with given email doesn't exist!");
        }
        if (!account.isActivated()){
            throw new AccountNotActivateException("Account exists but it is not activated!");
        }
        if (account.getPassword().equals(loginDto.getPassword())){
            return mapper.toDto(account);
        }else{
            throw new IncorrectPasswordException("Given password is not valid!");
        }

    }


}
