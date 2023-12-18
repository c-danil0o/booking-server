package com.komsije.booking.mapper;

import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.dto.RegistrationDto;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import com.komsije.booking.model.Host;
import com.komsije.booking.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, AccommodationMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class GuestMapper {
    public abstract GuestDto toDto(Guest guest);
    public Guest fromDto(GuestDto guestDto){
        Guest guest = new Guest();
        guest.setRole(Role.Guest);
        guest.setEmail(guestDto.getEmail());
        guest.setPassword(guestDto.getPassword());
        guest.setBlocked(guestDto.isBlocked());
        guest.setAddress(new Address(null, guestDto.getAddress().getStreet(), guestDto.getAddress().getCity(), guestDto.getAddress().getNumber(), guestDto.getAddress().getLatitude(), guestDto.getAddress().getLongitude()));
        guest.setFirstName(guestDto.getFirstName());
        guest.setLastName(guestDto.getLastName());
        guest.setPhone(guestDto.getPhone());
        guest.setTimesCancelled(guestDto.getTimesCancelled());
        return guest;
    }
    public abstract List<GuestDto> toDto(List<Guest> guestsList);
    public abstract void update(@MappingTarget Guest guest, GuestDto guestDto);

    public Guest fromRegistrationDto(RegistrationDto registrationDto){
        Guest guest = new Guest();
        guest.setEmail(registrationDto.getEmail());
        guest.setPassword(registrationDto.getPassword());
        guest.setPhone(registrationDto.getPhone());
        guest.setAddress(new Address(null, registrationDto.getAddress().getStreet(), registrationDto.getAddress().getCity(), registrationDto.getAddress().getNumber(), null, null));
        guest.setFirstName(registrationDto.getFirstName());
        guest.setLastName(registrationDto.getLastName());
        guest.setTimesCancelled(0);
        guest.setRole(Role.Guest);
        return guest;
    }
}
