package com.komsije.booking.mapper;

import com.komsije.booking.dto.HostDto;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.Host;
import com.komsije.booking.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class HostMapper {
    public abstract HostDto toDto(Host host);
    public Host fromDto(HostDto hostDto){
        Host host = new Host();
        host.setRole(Role.Host);
        host.setEmail(hostDto.getEmail());
        host.setPassword(hostDto.getPassword());
        host.setBlocked(hostDto.isBlocked());
        host.setAddress(new Address(null, hostDto.getAddress().getStreet(), hostDto.getAddress().getCity(), hostDto.getAddress().getNumber()));
        host.setFirstName(hostDto.getFirstName());
        host.setLastName(hostDto.getLastName());
        host.setPhone(hostDto.getPhone());
        return host;
    }
    public abstract List<HostDto> toDto(List<Host> hostList);
    public abstract void update(@MappingTarget Host host, HostDto hostDto);
}
