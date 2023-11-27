package com.komsije.booking.service;

import com.komsije.booking.model.Host;
import com.komsije.booking.repository.HostRepository;
import com.komsije.booking.service.interfaces.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostServiceImpl implements HostService {
    private final HostRepository hostRepository;

    @Autowired
    public HostServiceImpl(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    public Host findById(Long id) {
        return hostRepository.findById(id).orElseGet(null);
    }

    public List<Host> findAll() {
        return hostRepository.findAll();
    }

    public Host save(Host accommodation) {
        return hostRepository.save(accommodation);
    }

    public void delete(Long id) {
        hostRepository.deleteById(id);
    }

}
