package com.komsije.booking.mapper;

import com.komsije.booking.dto.AddressDto;
import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.dto.HostDto;
import com.komsije.booking.model.AccountType;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.Guest;
import com.komsije.booking.model.Host;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public abstract class HostMapper {
    public abstract HostDto toDto(Host host);
    public Host fromDto(HostDto hostDto){
        Host host = new Host();
        host.setAccountType(AccountType.Host);
        host.setEmail(hostDto.getEmail());
        host.setPassword(hostDto.getPassword());
        host.setBlocked(hostDto.isBlocked());
        host.setAddress(hostDto.getAddress());
        host.setFirstName(hostDto.getFirstName());
        host.setLastName(hostDto.getLastName());
        host.setPhone(hostDto.getPhone());
        return host;
    }
    public abstract List<HostDto> toDto(List<Host> hostList);
    public abstract void update(@MappingTarget Host host, HostDto hostDto);
}
