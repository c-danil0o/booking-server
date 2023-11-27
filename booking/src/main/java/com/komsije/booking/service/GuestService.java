package com.komsije.booking.service;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.Guest;
import com.komsije.booking.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest findOne(Long id) {
        return guestRepository.findById(id).orElseGet(null);
    }

    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    public Guest save(Guest accommodation) {
        return guestRepository.save(accommodation);
    }

    public void remove(Long id) {
        guestRepository.deleteById(id);
    }
}
