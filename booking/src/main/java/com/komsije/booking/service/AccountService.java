package com.komsije.booking.service;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account findOne(Long id) {return accountRepository.findById(id).orElseGet(null);}
    public List<Account> findAll() {return accountRepository.findAll();}
    public Account save(Account accommodation) {return accountRepository.save(accommodation);}
    public void remove(Long id) {
        accountRepository.deleteById(id);}
    public List<Account> getByAccountType(AccountType type){
        return accountRepository.findAccountByAccountType(type);
    }

    public List<Account> getBlockedAccounts() {return accountRepository.findAccountByIsBlocked(true);}
}
