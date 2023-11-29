package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.LoginDto;
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
@RequestMapping(value = "api")
public class AccountController {
    private final AccountServiceImpl accountService;

    @Autowired
    public AccountController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);

    }
    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {

        AccountDto account = accountService.findById(id);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAccountsByType(@RequestParam String type) {
        try{
            List<AccountDto> accounts = accountService.getByAccountType(AccountType.valueOf(type));
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/accounts/blocked")
    public ResponseEntity<List<AccountDto>> getBlockedAccounts(){
        try{
            List<AccountDto> accounts = accountService.getBlockedAccounts();
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/register", consumes = "application/json")
    public ResponseEntity<AccountDto> saveAccount(@RequestBody AccountDto accountDTO) {
        AccountDto account = accountService.save(accountDTO);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/accounts/{id}/block")
    public ResponseEntity<AccountDto> blockAccount(@PathVariable("id") Long id) {
        AccountDto accountDto = accountService.findById(id);
        if (accountDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        accountDto.setBlocked(true);
        accountDto = accountService.update(accountDto);
        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        AccountDto account = accountService.findById(id);
        if (account != null) {
            accountService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<AccountDto> login(@RequestBody LoginDto loginDto){
        AccountDto account = accountService.checkLoginCredentials(loginDto);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PutMapping(value = "/accounts/update", consumes = "application/json")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto){
        AccountDto account = accountService.update(accountDto);
        if (account == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }






}
