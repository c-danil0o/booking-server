package com.komsije.booking.service;

import com.komsije.booking.dto.HostDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.mapper.HostMapper;
import com.komsije.booking.model.Host;
import com.komsije.booking.repository.HostRepository;
import com.komsije.booking.service.interfaces.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostServiceImpl implements HostService {
    @Autowired
    private HostMapper mapper;
    private final HostRepository hostRepository;

    @Autowired
    public HostServiceImpl(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public HostDto findById(Long id) throws ElementNotFoundException {
        return mapper.toDto(hostRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!")));
    }

    public List<HostDto> findAll() {
        return mapper.toDto(hostRepository.findAll());
    }

    public HostDto save(HostDto hostDto) {
        hostRepository.save(mapper.fromDto(hostDto));
        return hostDto;
    }

    @Override
    public HostDto update(HostDto hostDto) throws ElementNotFoundException {
        Host host = hostRepository.findById(hostDto.getId()).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(host, hostDto);
        hostRepository.save(host);
        return hostDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        if (hostRepository.existsById(id)){
            hostRepository.deleteById(id);
        }else{
            throw new  ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }


}
