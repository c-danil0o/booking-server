package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDTO;
import com.komsije.booking.dto.AccountDTO;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Account;
import com.komsije.booking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts(){
        List<Account> accounts = accountService.findAll();

        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (Account account : accounts) {
            accountDTOs.add(new AccountDTO(account));
        }
        return new ResponseEntity<>(accountDTOs, HttpStatus.OK);

    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {

        Account account = accountService.findOne(id);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) {

        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());
        account.setBlocked(accountDTO.isBlocked());
        account.setAccountType(accountDTO.getAccountType());

        account = accountService.save(account);
        return new ResponseEntity<>(new AccountDTO(account), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {

        Account account = accountService.findOne(id);

        if (account != null) {
            accountService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
