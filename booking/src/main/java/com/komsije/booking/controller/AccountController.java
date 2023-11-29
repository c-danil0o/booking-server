package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.exceptions.AccountNotActivateException;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.exceptions.IncorrectPasswordException;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.service.AccountServiceImpl;
import com.komsije.booking.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService  accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);

    }
    @GetMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long id) {

        AccountDto account = null;
        try {
            account = accountService.findById(id);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
        AccountDto account = null;
        try {
            account = accountService.save(accountDTO);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/accounts/{id}/block")
    public ResponseEntity<AccountDto> blockAccount(@PathVariable("id") Long id) {
        AccountDto accountDto = null;
        try {
            accountDto = accountService.findById(id);
            accountDto.setBlocked(true);
            accountDto = accountService.update(accountDto);
        } catch (ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(accountDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        try {
            accountService.delete(id);
        } catch (HasActiveReservationsException | ElementNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<AccountDto> login(@RequestBody LoginDto loginDto){
        AccountDto account = null;
        try {
            account = accountService.checkLoginCredentials(loginDto);
        } catch (ElementNotFoundException | IncorrectPasswordException | AccountNotActivateException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PutMapping(value = "/accounts/update", consumes = "application/json")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDto){
        AccountDto account = null;
        try {
            account = accountService.update(accountDto);
        } catch (ElementNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }






}
