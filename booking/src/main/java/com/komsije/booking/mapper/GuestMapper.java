package com.komsije.booking.mapper;

import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, AccommodationMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class GuestMapper {
    public abstract GuestDto toDto(Guest guest);
    public Guest fromDto(GuestDto guestDto){
        Guest guest = new Guest();
        guest.setAccountType(AccountType.Guest);
        guest.setEmail(guestDto.getEmail());
        guest.setPassword(guestDto.getPassword());
        guest.setBlocked(guestDto.isBlocked());
        guest.setAddress(guestDto.getAddress());
        guest.setFirstName(guestDto.getFirstName());
        guest.setLastName(guestDto.getLastName());
        guest.setPhone(guestDto.getPhone());
        guest.setTimesCancelled(guestDto.getTimesCancelled());
        return guest;
    }
    public abstract List<GuestDto> toDto(List<Guest> guestsList);
    public abstract void update(@MappingTarget Guest guest, GuestDto guestDto);
}
