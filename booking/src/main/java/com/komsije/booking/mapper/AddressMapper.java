package com.komsije.booking.mapper;

import com.komsije.booking.dto.AddressDto;
import com.komsije.booking.model.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);
    Address fromDto(AddressDto addressDto);
}
