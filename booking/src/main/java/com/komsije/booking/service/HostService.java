package com.komsije.booking.service;

import com.komsije.booking.repository.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostService {
    @Autowired
    private HostRepository hostRepository;
}
