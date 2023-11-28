package com.komsije.booking.mapper;

import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.model.Guest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public abstract class GuestMapper {
    public abstract GuestDto toDto(Guest guest);
    public abstract Guest fromDto(GuestDto guestDto);
    public abstract List<GuestDto> toDto(List<Guest> guestsList);
}
