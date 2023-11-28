package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.model.Accommodation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, TimeSlotMapper.class})
public interface AccommodationMapper {
    AccommodationDto toDto(Accommodation accommodation);
    Accommodation fromDto(AccommodationDto accommodationDto);
    List<AccommodationDto> toDto(List<Accommodation> accommodationList);

}
