package com.komsije.booking.service;

import com.komsije.booking.model.Host;
import com.komsije.booking.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostService {
    @Autowired
    private HostRepository hostRepository;
    public Host findOne(Long id) {return hostRepository.findById(id).orElseGet(null);}
    public List<Host> findAll() {return hostRepository.findAll();}
    public Host save(Host accommodation) {return hostRepository.save(accommodation);}
    public void remove(Long id) {hostRepository.deleteById(id);}

}
