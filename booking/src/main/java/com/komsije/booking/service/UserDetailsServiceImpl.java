package com.komsije.booking.service;

import com.komsije.booking.model.Account;
import com.komsije.booking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = Optional.ofNullable(accountRepository.getAccountByEmail(username));
        if(!account.isEmpty()){
            return org.springframework.security.core.userdetails.User.withUsername(username).password(account.get().getPassword()).roles(account.get().getRole().toString()).build();
        }
        throw new UsernameNotFoundException("User not found with this username: " + username);
    }
}
