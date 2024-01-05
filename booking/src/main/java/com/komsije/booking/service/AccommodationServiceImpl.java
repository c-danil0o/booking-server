package com.komsije.booking.service;

import com.komsije.booking.dto.*;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.mapper.AccommodationMapper;
import com.komsije.booking.model.*;
import com.komsije.booking.repository.AccommodationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.lang.StrictMath.round;

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

    public List<AccommodationDto> getByLocationNumOfGuestsAndDate(String location, Integer numOfGuests, LocalDate startDate, LocalDate endDate) {
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
            AccommodationStatus status = accommodation.getStatus();
            properties.add(new HostPropertyDto(accommodation.getId(),   accommodation.getName(), address, accommodation.getHost().getFirstName() + accommodation.getHost().getLastName(), status, accommodation.getDescription(), accommodation.getPhotos()));
        }
        return properties;


    }

    @Override
    public List<AccommodationShortDto> getAllShort(){
        List<AccommodationShortDto> properties = new ArrayList<>();
        for (Accommodation accommodation :accommodationRepository.findAll()){
            String address = accommodation.getAddress().getStreet() + ", " + accommodation.getAddress().getCity();
            properties.add(new AccommodationShortDto(accommodation.getId(),   accommodation.getName(), address, accommodation.getDescription(), accommodation.getAverageGrade(), accommodation.getPhotos()));
        }
        return properties;}


    @Override
    public List<SearchedAccommodationDto> getSearchedAccommodations(SearchAccommodationsDto searchAccommodationsDto) {
        List<SearchedAccommodationDto> filteredAccommodations = new ArrayList<>();
        List<Accommodation> accommodations = new ArrayList<>();
        if (searchAccommodationsDto.getGuests()==0)
            accommodations = this.accommodationRepository.getActive();
        else
            accommodations = this.accommodationRepository.getAccommodationsByNumberOfGuests(searchAccommodationsDto.getGuests());

        for (Accommodation accommodation: accommodations) {
            if(isValid(accommodation,searchAccommodationsDto)){
                SearchedAccommodationDto accommodationDto = mapper.toSearchedDto(accommodation);
                double price = calculatePrice(accommodation, searchAccommodationsDto.getStartDate().toLocalDate(), searchAccommodationsDto.getEndDate().toLocalDate(), searchAccommodationsDto.getGuests());
                accommodationDto.setPrice(price);
                int days = (int) ChronoUnit.DAYS.between(searchAccommodationsDto.getStartDate(), searchAccommodationsDto.getEndDate());
                DecimalFormat df = new DecimalFormat("#.##");
                accommodationDto.setPricePerNight(Double.parseDouble(df.format(price/days)));
                filteredAccommodations.add(accommodationDto);
            }
        }
        return filteredAccommodations;
    }

    @Override
    public List<HostPropertyDto> getUnapprovedAccommodations() {
        List<HostPropertyDto> properties = new ArrayList<>();
        for (Accommodation accommodation :accommodationRepository.findUnapproved()){
            String address = accommodation.getAddress().getStreet() + ", " + accommodation.getAddress().getCity();
            AccommodationStatus status = accommodation.getStatus();
            properties.add(new HostPropertyDto(accommodation.getId(),   accommodation.getName(), address, accommodation.getHost().getFirstName() + accommodation.getHost().getLastName(), status, accommodation.getDescription(), accommodation.getPhotos()));
        }
        return properties;
    }

    @Override
    public void reserveTimeslot(Long id, LocalDate startDate, LocalDate endDate) {
        Accommodation accommodation = accommodationRepository.findById(id).orElseThrow(()->new ElementNotFoundException("Element with given ID doesn't exist!"));

        List<TimeSlot> slots = accommodation.getAvailability();
        Set<TimeSlot> slotsToDelete = new HashSet<>();
        Set<TimeSlot> slotsToAdd = new HashSet<>();
        for (TimeSlot slot: slots){
            if (slot.isOccupied()){
                continue;
            }
            else if (startDate.isEqual(slot.getStartDate()) && endDate.isEqual(slot.getEndDate())){
                slot.setOccupied(true);
                accommodationRepository.save(accommodation);
                return;
            }
            else if (startDate.isEqual(slot.getStartDate()) && endDate.isBefore(slot.getEndDate())){
                TimeSlot slot1 = new TimeSlot(null, startDate, endDate, slot.getPrice(), true);
                TimeSlot slot2 = new TimeSlot(null, endDate, slot.getEndDate(), slot.getPrice(), false);
                slotsToAdd.add(slot1);
                slotsToAdd.add(slot2);
                slotsToDelete.add(slot);
                break;
            }
            else if (startDate.isAfter(slot.getStartDate()) && endDate.isEqual(slot.getEndDate())){
                TimeSlot slot1 = new TimeSlot(null, startDate, endDate, slot.getPrice(), true);
                TimeSlot slot2 = new TimeSlot(null, slot.getStartDate(), startDate, slot.getPrice(), false);
                slotsToAdd.add(slot1);
                slotsToAdd.add(slot2);
                slotsToDelete.add(slot);
                break;
            }
            else if (startDate.isAfter(slot.getStartDate()) && endDate.isBefore(slot.getEndDate())){
                TimeSlot slot1 = new TimeSlot(null, slot.getStartDate(), startDate, slot.getPrice(), false);
                TimeSlot slot2 = new TimeSlot(null, endDate, slot.getEndDate(), slot.getPrice(), false);
                TimeSlot slot3 = new TimeSlot(null, startDate, endDate,slot.getPrice(), true);
                slotsToAdd.add(slot1);
                slotsToAdd.add(slot2);
                slotsToAdd.add(slot3);
                slotsToDelete.add(slot);
                break;
            }
        }
        for (TimeSlot slt: slotsToDelete){
            accommodation.getAvailability().remove(slt);
        }
        for (TimeSlot slt: slotsToAdd){
            accommodation.getAvailability().add(slt);
        }
        this.accommodationRepository.save(accommodation);
    }

    @Override
    public void restoreTimeslot(Reservation reservation){
        Accommodation accommodation = reservation.getAccommodation();
        List<TimeSlot> timeSlots = accommodation.getAvailability();
        timeSlots.sort((item1, item2) -> {
            return Math.toIntExact(item1.getStartDate().toEpochDay() - item2.getStartDate().toEpochDay());
        });
        LocalDate resStart = reservation.getStartDate();
        LocalDate resEnd = reservation.getStartDate().plusDays(reservation.getDays());
        List<TimeSlot> forRemoval = new ArrayList<>();
        for (int i =0; i< timeSlots.size()-1; i++){
            if (timeSlots.get(i).getStartDate().isEqual(resStart) && timeSlots.get(i).getEndDate().isEqual(resEnd)){
                timeSlots.get(i).setOccupied(false);
            }
            if (timeSlots.get(i).getEndDate().isEqual(timeSlots.get(i+1).getStartDate()) && timeSlots.get(i).getPrice() == timeSlots.get(i+1).getPrice()){
                timeSlots.get(i+1).setStartDate(timeSlots.get(i).getStartDate());
                forRemoval.add(timeSlots.get(i));
            }
        }
        for (TimeSlot slot: forRemoval){
            timeSlots.remove(slot);
        }
        accommodation.setAvailability(timeSlots);
        accommodationRepository.save(accommodation);

    }

    private boolean isValid(Accommodation accommodation, SearchAccommodationsDto searchAccommodationsDto){
        return accommodation.getAddress().getCity().toLowerCase().equals(searchAccommodationsDto.getPlace().toLowerCase().trim()) && isAvailable(accommodation,searchAccommodationsDto.getStartDate().toLocalDate(),searchAccommodationsDto.getEndDate().toLocalDate());
    }

    private boolean isAvailable(Accommodation accommodation, LocalDate startDate, LocalDate endDate){
        List<TimeSlot> slots = accommodation.getAvailability();
        for (TimeSlot slot : slots) {
            if (slot.isOccupied())
                continue;
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


    private double calculatePrice(Accommodation accommodation, LocalDate startDate, LocalDate endDate, int numberOfGuests){
        double price = 0;
        List<TimeSlot> slots = accommodation.getAvailability();
        int guestNumber = 1;
        if(accommodation.isPricePerGuest()){
            if(numberOfGuests==0)
                guestNumber=accommodation.getMaxGuests();
            else
                guestNumber=numberOfGuests;
        }
        for (TimeSlot slot : slots) {
            if(slot.getEndDate().isBefore(startDate))
                continue;
            else if (slot.getStartDate().isAfter(startDate))
                if(slot.getEndDate().isAfter(endDate))
                    continue;
                else{
                    int days = (int) ChronoUnit.DAYS.between(slot.getStartDate(), endDate) + 1;
                    price = price + slot.getPrice()*days*guestNumber;
                    endDate = slot.getStartDate().minusDays(1);
                    if (startDate.isAfter(endDate) || startDate.isEqual(endDate))
                        break;
                }
            else if(slot.getEndDate().isAfter(endDate) || slot.getEndDate().isEqual(endDate))
            {
                int days = (int) ChronoUnit.DAYS.between(startDate,endDate);
                price = price+slot.getPrice()*days*guestNumber;
                return price;
            }
            else{   //equals
                int days = (int) ChronoUnit.DAYS.between(startDate, slot.getEndDate()) + 1;
                price = price + slot.getPrice()*days*guestNumber;

                startDate=slot.getEndDate().plusDays(1);
                if (startDate.isAfter(endDate) || startDate.isEqual(endDate))
                    break;
                continue;
            }
        }
        return price;
    }

    public GottenAvailabilityPrice getAvailabilityPrice(GetAvailabilityPrice getAvailabilityPrice) {
        Accommodation accommodation = accommodationRepository.findById(getAvailabilityPrice.getAccommodationId()).orElseThrow(() -> new ElementNotFoundException("Accommodation not found"));

        boolean isAvailable = isAvailable(accommodation, getAvailabilityPrice.getStartDate(), getAvailabilityPrice.getEndDate());
        if(!isAvailable) {
            return new GottenAvailabilityPrice(false, 0, 0);
        }
        GottenAvailabilityPrice gottenAvailabilityPrice = new GottenAvailabilityPrice();
        gottenAvailabilityPrice.setAvailable(true);

        //promenjena je calculatePrice funkcija
        double price = calculatePrice(accommodation, getAvailabilityPrice.getStartDate(), getAvailabilityPrice.getEndDate(),0);
        gottenAvailabilityPrice.setTotalPrice(price);

        int days = (int) ChronoUnit.DAYS.between(getAvailabilityPrice.getStartDate(), getAvailabilityPrice.getEndDate());
        DecimalFormat df = new DecimalFormat("#.##");
        gottenAvailabilityPrice.setPricePerNight(Double.parseDouble(df.format(price/days)));

        return gottenAvailabilityPrice;
    }
}
