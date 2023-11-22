package com.komsije.booking.controller;

import com.komsije.booking.dto.AccommodationDTO;
import com.komsije.booking.dto.AccountDTO;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Account;
import com.komsije.booking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

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

}
