package com.komsije.booking.service;

import com.komsije.booking.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;
}
