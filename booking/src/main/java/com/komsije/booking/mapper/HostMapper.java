package com.komsije.booking.mapper;

import com.komsije.booking.dto.AddressDto;
import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.dto.HostDto;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import com.komsije.booking.model.Host;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HostMapper {
    HostDto toDto(Host host);
    Host fromDto(HostDto hostDto);
    List<HostDto> toDto(List<Host> hostList);
    AddressDto toDto(Address address);
    Address fromDto(AddressDto addressDto);
}
