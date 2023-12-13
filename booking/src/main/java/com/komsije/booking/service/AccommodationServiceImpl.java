package com.komsije.booking.service;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AvailabilityDto;
import com.komsije.booking.dto.HostPropertyDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.mapper.AccommodationMapper;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationStatus;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.repository.AccommodationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccommodationServiceImpl implements AccommodationService {
    @Autowired
    private AccommodationMapper mapper;
    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public AccommodationDto findById(Long id) throws ElementNotFoundException {
        return mapper.toDto(accommodationRepository.findById(id).orElseThrow(()->new ElementNotFoundException("Element with given ID doesn't exist!")));
    }


    public List<AccommodationDto> findAll() {
        return mapper.toDto(accommodationRepository.findAll());
    }

    public AccommodationDto save(AccommodationDto accommodationDto) {
        accommodationRepository.save(mapper.fromDto(accommodationDto));
        return accommodationDto;
    }

    @Override
    public AccommodationDto update(AccommodationDto accommodationDto) throws ElementNotFoundException {
        Accommodation accommodation = accommodationRepository.findById(accommodationDto.getId()).orElseThrow(()->new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(accommodation, accommodationDto);
        accommodationRepository.save(accommodation);
        return accommodationDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        if (accommodationRepository.existsById(id)){
            accommodationRepository.deleteById(id);
        }else{
            throw new ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }

    public List<AccommodationDto> getByAccommodationType(AccommodationType type) {
        return mapper.toDto(accommodationRepository.getAccommodationByAccommodationType(type));
    }

    @Override
    public AccommodationDto updateAvailability(Long accommodationId, AvailabilityDto availabilityDto) throws ElementNotFoundException {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(()->new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(accommodation, availabilityDto);
        accommodationRepository.save(accommodation);
        return mapper.toDto(accommodation);
    }

    public List<AccommodationDto> getByLocationNumOfGuestsAndDate(String location, Integer numOfGuests, LocalDateTime startDate, LocalDateTime endDate) {
        return mapper.toDto(accommodationRepository.getAccommodationsByLocationNumOfGuestsAndDate(location, numOfGuests, startDate, endDate));
    }

    public List<AccommodationDto> getByAmenities(List<String> amenities) {
        return mapper.toDto(accommodationRepository.getAccommodationsByAmenities(amenities));
    }

    public Accommodation findModelById(Long id) throws ElementNotFoundException {
        return accommodationRepository.findById(id).orElseThrow(()->new ElementNotFoundException("Element with given ID doesn't exist!"));
    }

    public List<HostPropertyDto> findByHostId(Long id){
        List<HostPropertyDto> properties = new ArrayList<>();
        for (Accommodation accommodation :accommodationRepository.findByHostId(id)){
            String address = accommodation.getAddress().getStreet() + ", " + accommodation.getAddress().getCity();
            AccommodationStatus status;
            if (accommodation.isApproved()){
                status = AccommodationStatus.Active;
            }else{
                status = AccommodationStatus.Pending;
            }
            properties.add(new HostPropertyDto(accommodation.getId(),   accommodation.getName(), address, status));
        }
        return properties;
    }


}
