package com.komsije.booking.service;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    public Accommodation FindOne(Long id) {return accommodationRepository.findById(id).orElseGet(null);}
    public List<Accommodation> FindAll() {return accommodationRepository.findAll();}
    public Accommodation Save(Accommodation accommodation) {return accommodationRepository.save(accommodation);}
    public void Remove(Long id) {accommodationRepository.deleteById(id);}



}
