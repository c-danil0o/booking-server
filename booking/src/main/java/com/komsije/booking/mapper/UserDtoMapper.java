package com.komsije.booking.mapper;

import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.dto.HostDto;
import com.komsije.booking.dto.UserDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Guest;
import com.komsije.booking.model.Host;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.GuestService;
import com.komsije.booking.service.interfaces.HostService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class UserDtoMapper {
    @Autowired
    private HostService hostService;
    @Autowired
    private GuestService guestService;
    @Autowired
    private AccountService accountService;

    public UserDto toDto(Account account) throws ElementNotFoundException {
        UserDto userDto = new UserDto();
        userDto.setEmail(account.getEmail());
        userDto.setAccountId(account.getId());
        if (account.getAccountType().equals(AccountType.Host)){
            HostDto host = hostService.findById(account.getId());
            userDto.setFirstName(host.getFirstName());
            userDto.setLastName(host.getLastName());
        }else if (account.getAccountType().equals(AccountType.Guest)){
            GuestDto guest = guestService.findById(account.getId());
            userDto.setFirstName(guest.getFirstName());
            userDto.setLastName(guest.getLastName());
        }
        return userDto;
    }

    public Account fromDto(UserDto userDto) throws ElementNotFoundException {
        return accountService.getByEmail(userDto.getEmail());
    }

}
