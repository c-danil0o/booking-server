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
    public Account FindOne(Long id) {return accountRepositoryAccommodationService.findById(id).orElseGet(null);}
    public List<Account> FindAll() {return accountRepositoryAccommodationService.findAll();}
    public Account Save(Account accommodation) {return accountRepositoryAccommodationService.save(accommodation);}
    public void Remove(Long id) {accountRepositoryAccommodationService.deleteById(id);}
}
