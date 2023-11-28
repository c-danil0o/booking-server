package com.komsije.booking.controller;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/accounts")
public class AccountController {
    private final AccountServiceImpl accountService;

    @Autowired
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<Account> accounts = accountService.findAll();

        List<AccountDto> accountDtos = new ArrayList<>();
        for (Account account : accounts) {
            accountDtos.add(new AccountDto(account));
        }
        return new ResponseEntity<>(accountDtos, HttpStatus.OK);

    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {

        Account account = accountService.findById(id);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AccountDto(account), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AccountDto>> getAccountsByType(@RequestParam String type) {
        try{
            List<Account> accounts = accountService.getByAccountType(AccountType.valueOf(type));
            List<AccountDto> accountDtos = new ArrayList<>();
            for (Account account: accounts){
                accountDtos.add(new AccountDto(account));
            }
            return new ResponseEntity<>(accountDtos, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/blocked")
    public ResponseEntity<List<AccountDto>> getBlockedAccounts(){
        try{
            List<Account> accounts = accountService.getBlockedAccounts();

            List<AccountDto> accountDtos = new ArrayList<>();
            for (Account account : accounts) {
                accountDtos.add(new AccountDto(account));
            }
            return new ResponseEntity<>(accountDtos, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccountDto> saveAccount(@RequestBody AccountDto accountDTO) {

        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassword(accountDTO.getPassword());
        account.setBlocked(accountDTO.isBlocked());
        account.setAccountType(accountDTO.getAccountType());

        account = accountService.save(account);
        return new ResponseEntity<>(new AccountDto(account), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {

        Account account = accountService.findById(id);

        if (account != null) {
            accountService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
