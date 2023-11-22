package com.komsije.booking.service;

import com.komsije.booking.model.Account;
import com.komsije.booking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepositoryAccommodationService;
    public Account findOne(Long id) {return accountRepositoryAccommodationService.findById(id).orElseGet(null);}
    public List<Account> findAll() {return accountRepositoryAccommodationService.findAll();}
    public Account save(Account accommodation) {return accountRepositoryAccommodationService.save(accommodation);}
    public void remove(Long id) {accountRepositoryAccommodationService.deleteById(id);}
}
