package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.model.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account fromDto(AccountDto accountDto);
    List<AccountDto> toDto(List<Account> accountList);
}
