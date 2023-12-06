package com.komsije.booking.service;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.mapper.AccountMapper;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Role;
import com.komsije.booking.repository.AccountRepository;
import com.komsije.booking.service.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final AccountRepository accountRepository;
    @Autowired
    private AccountMapper mapper;

    @Autowired
    public AdminServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<AccountDto> findAll() {
        return mapper.toDto(accountRepository.findAccountByRole(Role.Admin));
    }

    @Override
    public AccountDto findById(Long id) throws ElementNotFoundException {
            return mapper.toDto(accountRepository.findById(id).orElseThrow(() -> new ElementNotFoundException("No such element with given ID!")));
    }


    @Override
    public AccountDto save(AccountDto accountDto) {
        accountRepository.save(mapper.fromDto(accountDto));
        return accountDto;
    }

    @Override
    public AccountDto update(AccountDto accountDto) throws ElementNotFoundException {
        Account account = accountRepository.findById(accountDto.getId()).orElseThrow(() -> new ElementNotFoundException("No such element with given ID!"));
        mapper.update(account, accountDto);
        accountRepository.save(account);
        return accountDto;
    }

    @Override
    public void delete(Long id) throws ElementNotFoundException {
        if (accountRepository.existsById(id)){
            accountRepository.deleteById(id);
        }else{
            throw new ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }
}
