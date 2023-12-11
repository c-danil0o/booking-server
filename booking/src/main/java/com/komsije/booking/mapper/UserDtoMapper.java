package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccountDto;
import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.dto.HostDto;
import com.komsije.booking.dto.UserDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.model.Account;
import com.komsije.booking.model.Role;
import com.komsije.booking.service.interfaces.AccountService;
import com.komsije.booking.service.interfaces.GuestService;
import com.komsije.booking.service.interfaces.HostService;
import org.mapstruct.Mapper;
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
        if (account.getRole().equals(Role.Host)){
            HostDto host = hostService.findById(account.getId());
            userDto.setFirstName(host.getFirstName());
            userDto.setLastName(host.getLastName());
        }else if (account.getRole().equals(com.komsije.booking.model.Role.Guest)){
            GuestDto guest = guestService.findById(account.getId());
            userDto.setFirstName(guest.getFirstName());
            userDto.setLastName(guest.getLastName());
        }
        return userDto;
    }

    public AccountDto fromDto(UserDto userDto) throws ElementNotFoundException {
        return accountService.getByEmail(userDto.getEmail());
    }

}
