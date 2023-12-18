package com.komsije.booking.service;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AvailabilityDto;
import com.komsije.booking.dto.HostPropertyDto;
import com.komsije.booking.dto.SearchAccommodationsDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.mapper.AccommodationMapper;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationStatus;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.model.TimeSlot;
import com.komsije.booking.repository.AccommodationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static java.lang.StrictMath.round;
import static java.lang.StrictMath.tan;

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

    @Override
    public List<AccommodationDto> getSearchedAccommodations(SearchAccommodationsDto searchAccommodationsDto) {
        List<AccommodationDto> filteredAccommodations = new ArrayList<>();
        List<Accommodation> accommodations = new ArrayList<>();
        if (searchAccommodationsDto.getGuests()==0)
            accommodations = this.accommodationRepository.findAll();
        else
            accommodations = this.accommodationRepository.getAccommodationsByNumberOfGuests(searchAccommodationsDto.getGuests());

        for (Accommodation accommodation: accommodations) {
            if(isValid(accommodation,searchAccommodationsDto)){
                AccommodationDto accommodationDto = mapper.toDto(accommodation);
                double price = calculatePrice(accommodation, searchAccommodationsDto.getStartDate(), searchAccommodationsDto.getEndDate());
                accommodationDto.setPrice(price);
                int days = (int) ChronoUnit.DAYS.between(searchAccommodationsDto.getStartDate(), searchAccommodationsDto.getEndDate());
                DecimalFormat df = new DecimalFormat("#.##");
                accommodationDto.setPricePerNight(Double.parseDouble(df.format(price/days)));
                filteredAccommodations.add(accommodationDto);
            }
        }
        return filteredAccommodations;
    }

    private boolean isValid(Accommodation accommodation, SearchAccommodationsDto searchAccommodationsDto){
        return accommodation.getAddress().getCity().equals(searchAccommodationsDto.getPlace()) && isAvailable(accommodation,searchAccommodationsDto.getStartDate(),searchAccommodationsDto.getEndDate());
    }

    private boolean isAvailable(Accommodation accommodation, LocalDateTime startDate, LocalDateTime endDate){
        Set<TimeSlot> slots = accommodation.getAvailability();
        for (TimeSlot slot : slots) {
            slot.setStartDate(slot.getStartDate().withHour(startDate.getHour()));
            slot.setEndDate(slot.getEndDate().withHour(endDate.getHour()));
            if(slot.getEndDate().isBefore(startDate))
                continue;
            else if (slot.getStartDate().isAfter(startDate))
                if(slot.getEndDate().isAfter(endDate))
                    continue;
                else{
                    endDate = slot.getStartDate().minusDays(1);
                }
            else if(slot.getEndDate().isAfter(endDate) || slot.getEndDate().isEqual(endDate))
                return true;
            else{
                startDate=slot.getEndDate().plusDays(1);
                continue;
            }
        }
        return false;
    }

    private double calculatePrice(Accommodation accommodation, LocalDateTime startDate, LocalDateTime endDate){
        double price = 0;
        Set<TimeSlot> slots = accommodation.getAvailability();
        for (TimeSlot slot : slots) {
            slot.setStartDate(slot.getStartDate().withHour(startDate.getHour()));
            slot.setEndDate(slot.getEndDate().withHour(endDate.getHour()));
            if(slot.getEndDate().isBefore(startDate))
                continue;
            else if (slot.getStartDate().isAfter(startDate))
                if(slot.getEndDate().isAfter(endDate))
                    continue;
                else{
                    int days = (int) ChronoUnit.DAYS.between(slot.getStartDate(), endDate) + 1;
                    price = price + slot.getPrice()*days;
                    endDate = slot.getStartDate().minusDays(1);
                    if (startDate.isAfter(endDate) || startDate.isEqual(endDate))
                        break;
                }
            else if(slot.getEndDate().isAfter(endDate) || slot.getEndDate().isEqual(endDate))
            {
                int days = (int) ChronoUnit.DAYS.between(startDate,endDate);
                price = price+slot.getPrice()*days;
                return price;
            }
            else{   //equals
                int days = (int) ChronoUnit.DAYS.between(startDate, slot.getEndDate()) + 1;
                price = price + slot.getPrice()*days;

                startDate=slot.getEndDate().plusDays(1);
                if (startDate.isAfter(endDate) || startDate.isEqual(endDate))
                    break;
                continue;
            }
        }
        return price;
    }


}
