package com.komsije.booking.service;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.repository.AccommodationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id).orElseGet(null);
    }

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    public void delete(Long id) {
        accommodationRepository.deleteById(id);
    }
    public List<Accommodation> getByAccommodationType(AccommodationType type){
        return accommodationRepository.getAccommodationByAccommodationType(type);
    }


}
