package com.komsije.booking.controller;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.LoginDto;
import com.komsije.booking.dto.RegistrationDto;
import com.komsije.booking.dto.TokenDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.HasActiveReservationsException;
import com.komsije.booking.model.Role;
import com.komsije.booking.security.JwtTokenUtil;
import com.komsije.booking.service.RegistrationServiceImpl;
import com.komsije.booking.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping(value = "api")
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RegistrationServiceImpl registrationService;
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService  accountService) {
        this.accountService = accountService;
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/accounts/all")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        List<AccountDto> accounts = accountService.findAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('Admin')")
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

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAccountsByType(@RequestParam String type) {
        try{
            List<AccountDto> accounts = accountService.getByAccountType(Role.valueOf(type));
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping(value = "/accounts/blocked")
    public ResponseEntity<List<AccountDto>> getBlockedAccounts(){
        try{
            List<AccountDto> accounts = accountService.getBlockedAccounts();
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/accounts/save", consumes = "application/json")
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

    //blokirati u servisu
    @PreAuthorize("hasRole('Admin')")
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

    @PreAuthorize("hasRole('Admin')")
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
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                loginDto.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        UserDetails userDetail = userDetailsService.loadUserByUsername(loginDto.getEmail());

        String token = jwtTokenUtil.generateToken(userDetail);
        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);

        return new ResponseEntity<>(tokenDto, HttpStatus.OK);




//        AccountDto account = null;
//        try {
//            account = accountService.checkLoginCredentials(loginDto);
//        } catch (ElementNotFoundException | IncorrectPasswordException | AccountNotActivateException e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @GetMapping(
            value = "/logout",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> logoutUser() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //if (!(auth instanceof AnonymousAuthenticationToken)){
        if (true){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @PreAuthorize("hasRole('Admin')")
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

    @PostMapping(value="/register", consumes = "application/json")
    public ResponseEntity<String> register1(@RequestBody RegistrationDto registrationDto) {
        try {
            return new ResponseEntity<>( registrationService.register(registrationDto),HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/register/confirm")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        try{
            return new ResponseEntity<>(registrationService.confirmToken(token),HttpStatus.OK);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }






}
