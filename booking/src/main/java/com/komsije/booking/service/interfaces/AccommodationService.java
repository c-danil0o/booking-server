package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.*;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.time.LocalDateTime;
import java.util.List;

public interface AccommodationService extends CrudService<AccommodationDto, Long> {
    public List<AccommodationDto> getByAccommodationType(AccommodationType type);
    public AccommodationDto updateAvailability(Long accommodationId, AvailabilityDto availabilityDto) throws ElementNotFoundException;
    public List<AccommodationDto> getByLocationNumOfGuestsAndDate(String location, Integer numOfGuests, LocalDateTime startDate, LocalDateTime endDate);
    public List<AccommodationDto> getByAmenities(List<String> amenities);
    public Accommodation findModelById(Long id) throws ElementNotFoundException;
    public List<HostPropertyDto> findByHostId(Long id);
    public List<SearchedAccommodationDto> getSearchedAccommodations(SearchAccommodationsDto searchAccommodationsDto);

}
