package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.*;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.time.LocalDate;
import java.util.List;

public interface AccommodationService extends CrudService<AccommodationDto, Long> {
    public List<AccommodationDto> getByAccommodationType(AccommodationType type);
    public AccommodationDto updateAvailability(Long accommodationId, AvailabilityDto availabilityDto) throws ElementNotFoundException;
    public List<AccommodationDto> getByLocationNumOfGuestsAndDate(String location, Integer numOfGuests, LocalDate startDate, LocalDate endDate);
    public List<AccommodationDto> getByAmenities(List<String> amenities);
    public Accommodation findModelById(Long id) throws ElementNotFoundException;
    public List<HostPropertyDto> findByHostId(Long id);

    List<AccommodationShortDto> getAllShort();

    public List<SearchedAccommodationDto> getSearchedAccommodations(SearchAccommodationsDto searchAccommodationsDto);
    public List<HostPropertyDto> getUnapprovedAccommodations();
    public void reserveTimeslot(Long id, LocalDate startDate, LocalDate endDate);
    public GottenAvailabilityPrice getAvailabilityPrice(GetAvailabilityPrice getAvailabilityPrice);

    void restoreTimeslot(Reservation reservation);
}
