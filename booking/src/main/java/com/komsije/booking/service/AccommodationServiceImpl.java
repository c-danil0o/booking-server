package com.komsije.booking.service;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AvailabilityDto;
import com.komsije.booking.mapper.AccommodationMapper;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.repository.AccommodationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    @Autowired
    private  AccommodationMapper mapper;
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public AccommodationDto findById(Long id) {
        try {
            return mapper.toDto(accommodationRepository.findById(id).orElseGet(null));
        }
        catch (NullPointerException e){
            return null;
        }
    }



    public List<AccommodationDto> findAll() {
       return mapper.toDto(accommodationRepository.findAll());
    }

    public AccommodationDto save(AccommodationDto accommodationDto) {
        accommodationRepository.save(mapper.fromDto(accommodationDto));
        return accommodationDto;
    }

    @Override
    public AccommodationDto update(AccommodationDto accommodationDto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationDto.getId()).orElseGet(null);
        if (accommodation == null){
            return null;
        }
//        Accommodation updatedAccommodation = mapper.fromDto(accommodationDto);
        mapper.update(accommodation,accommodationDto);
        accommodationRepository.save(accommodation);
        return accommodationDto;
    }

    public void delete(Long id) {
        accommodationRepository.deleteById(id);
    }

    public List<AccommodationDto> getByAccommodationType(AccommodationType type) {
        return mapper.toDto(accommodationRepository.getAccommodationByAccommodationType(type));
    }

    @Override
    public AccommodationDto updateAvailability(Long accommodationId, AvailabilityDto availabilityDto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseGet(null);
        if (accommodation == null){
            return null;
        }
        mapper.update(accommodation, availabilityDto);
        accommodationRepository.save(accommodation);
        return mapper.toDto(accommodation);
    }

    public List<AccommodationDto> getByLocationNumOfGuestsAndDate(String location, Integer numOfGuests, LocalDateTime startDate, LocalDateTime endDate){
        return mapper.toDto(accommodationRepository.getAccommodationsByLocationNumOfGuestsAndDate(location,numOfGuests, startDate, endDate));
    }

    public List<AccommodationDto> getByAmenities(List<String> amenities){
        return mapper.toDto(accommodationRepository.getAccommodationsByAmenities(amenities));
    }

    public Accommodation findModelById(Long id){
        try {
            return accommodationRepository.findById(id).orElseGet(null);
        }
        catch (Exception e){
            return null;
        }
    }





}
