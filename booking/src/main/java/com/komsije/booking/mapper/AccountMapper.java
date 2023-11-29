package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public interface AccountMapper {
    AccountDto toDto(Account account);
    Account fromDto(AccountDto accountDto);
    List<AccountDto> toDto(List<Account> accountList);
    void update(@MappingTarget Account account, AccountDto accountDto);

}
