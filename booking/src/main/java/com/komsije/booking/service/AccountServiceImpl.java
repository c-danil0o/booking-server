package com.komsije.booking.service;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.mapper.AccountMapper;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Guest;
import com.komsije.booking.repository.AccountRepository;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.komsije.booking.model.AccountType.Guest;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ReservationService reservationService;
    @Autowired
    private AccountMapper mapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, ReservationService reservationService) {
        this.accountRepository = accountRepository;
        this.reservationService = reservationService;
    }

    public AccountDto findById(Long id) {
        try {
            return mapper.toDto(accountRepository.findById(id).orElseGet(null));
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public List<AccountDto> findAll() {
        return mapper.toDto(accountRepository.findAll());
    }

    public AccountDto save(AccountDto accountDto) {
        accountRepository.save(mapper.fromDto(accountDto));
        return accountDto;
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        Account account = accountRepository.findById(accountDto.getId()).orElseGet(null);
        if (account == null){
            return null;
        }
        mapper.update(account, accountDto);
        accountRepository.save(account);
        return accountDto;
    }

    public void delete(Long id) {
        Account account = accountRepository.findById(id).orElseGet(null);
        if (account == null){
            return;
        }
        if (!reservationService.hasActiveReservations(id)){
            accountRepository.deleteById(id);
        }
    }

    public List<AccountDto> getByAccountType(AccountType type) {
        return mapper.toDto(accountRepository.findAccountByAccountType(type));
    }

    public List<AccountDto> getBlockedAccounts() {
        return mapper.toDto(accountRepository.findAccountByIsBlocked(true));
    }

    @Override
    public Account getByEmail(String email) {
        return accountRepository.getAccountByEmail(email);
    }

    @Override
    public AccountDto checkLoginCredentials(LoginDto loginDto) {
        Account account = accountRepository.getAccountByEmail(loginDto.getEmail());
        if (account == null){
            return null;
        }
        if (!account.isActivated()){
            return null;
        }
        if (account.getPassword().equals(loginDto.getPassword())){
            return mapper.toDto(account);
        }
        return null;
    }


}
