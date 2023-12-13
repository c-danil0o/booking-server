package com.komsije.booking.mapper;

import com.komsije.booking.dto.HostDto;
import com.komsije.booking.dto.RegistrationDto;
import com.komsije.booking.model.Address;
import com.komsije.booking.model.Host;
import com.komsije.booking.model.Role;
import com.komsije.booking.repository.HostRepository;
import com.komsije.booking.service.interfaces.HostService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class HostMapper {
    @Autowired
    private HostRepository hostRepository;
    public abstract HostDto toDto(Host host);
    public Host fromDto(HostDto hostDto){
        if (!hostRepository.existsById(hostDto.getId())){


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
        }else{
            return hostRepository.findById(hostDto.getId()).orElse(null);
        }
    }
    public abstract List<HostDto> toDto(List<Host> hostList);
    public abstract void update(@MappingTarget Host host, HostDto hostDto);
    public Host fromRegistrationDto(RegistrationDto registrationDto){
        Host host = new Host();
        host.setEmail(registrationDto.getEmail());
        host.setPassword(registrationDto.getPassword());
        host.setPhone(registrationDto.getPhone());
        host.setAddress(new Address(null, registrationDto.getAddress().getStreet(), registrationDto.getAddress().getCity(), registrationDto.getAddress().getNumber()));
        host.setFirstName(registrationDto.getFirstName());
        host.setLastName(registrationDto.getLastName());
        host.setRole(Role.Host);
        return host;
    }
}
