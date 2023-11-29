package com.komsije.booking.service;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.mapper.AccountMapper;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.repository.AccountRepository;
import com.komsije.booking.service.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AccountRepository accountRepository;
    @Autowired
    private AccountMapper mapper;

    @Autowired
    public AdminServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> findAll() {
        return mapper.toDto(accountRepository.findAccountByAccountType(AccountType.Admin));
    }

    @Override
    public AccountDto findById(Long id) {
        try {
            return mapper.toDto(accountRepository.findById(id).orElseGet(null));
        }
        catch (NullPointerException e){
            return null;
        }
    }


    @Override
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

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}
