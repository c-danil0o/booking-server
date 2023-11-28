package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AddressDto;
import com.komsije.booking.dto.TimeSlotDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.TimeSlot;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccommodationMapper {
    AccommodationDto toDto(Accommodation accommodation);
    Accommodation fromDto(AccommodationDto accommodationDto);
    List<AccommodationDto> toDto(List<Accommodation> accommodationList);
    AddressDto toDto(Address address);
    Address fromDto(AddressDto addressDto);
    TimeSlotDto toDto(TimeSlot timeSlot);
    TimeSlot fromDto(TimeSlotDto timeSlotDto);
}
