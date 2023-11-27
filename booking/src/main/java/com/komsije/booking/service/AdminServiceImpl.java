package com.komsije.booking.service;

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
    public AdminServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAccountByAccountType(AccountType.Admin);
    }

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id).orElseGet(null);
    };

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
}
