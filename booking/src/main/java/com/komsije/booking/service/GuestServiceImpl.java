package com.komsije.booking.service;

import com.komsije.booking.model.Guest;
import com.komsije.booking.repository.GuestRepository;
import com.komsije.booking.service.interfaces.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestServiceImpl implements GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public Guest findById(Long id) {
        return guestRepository.findById(id).orElseGet(null);
    }

    public List<Guest> findAll() {
        return guestRepository.findAll();
    }

    public Guest save(Guest accommodation) {
        return guestRepository.save(accommodation);
    }

    public void delete(Long id) {
        guestRepository.deleteById(id);
    }
}
