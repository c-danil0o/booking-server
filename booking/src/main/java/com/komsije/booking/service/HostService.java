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
    public Host FindOne(Long id) {return hostRepository.findById(id).orElseGet(null);}
    public List<Host> FindAll() {return hostRepository.findAll();}
    public Host Save(Host accommodation) {return hostRepository.save(accommodation);}
    public void Remove(Long id) {hostRepository.deleteById(id);}

}
