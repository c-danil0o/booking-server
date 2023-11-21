package com.komsije.booking.service;

import com.komsije.booking.model.Account;
import com.komsije.booking.model.Guest;
import com.komsije.booking.repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {
    @Autowired
    private GuestRepository guestRepository;

    public Guest FindOne(Long id) {return guestRepository.findById(id).orElseGet(null);}
    public List<Guest> FindAll() {return guestRepository.findAll();}
    public Guest Save(Guest accommodation) {return guestRepository.save(accommodation);}
    public void Remove(Long id) {guestRepository.deleteById(id);}
}
