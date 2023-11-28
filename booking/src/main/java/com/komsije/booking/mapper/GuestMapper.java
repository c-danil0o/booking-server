package com.komsije.booking.mapper;

import com.komsije.booking.dto.AddressDto;
import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import com.komsije.booking.model.Reservation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public abstract class GuestMapper {
    abstract GuestDto toDto(Guest guest);
    abstract Guest fromDto(GuestDto guestDto);
    abstract List<GuestDto> toDto(List<Guest> guestsList);
}
