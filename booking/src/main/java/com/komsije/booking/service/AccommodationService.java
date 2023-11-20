package com.komsije.booking.service;

import com.komsije.booking.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;
}
