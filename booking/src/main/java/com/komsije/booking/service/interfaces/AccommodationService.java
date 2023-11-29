package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AvailabilityDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface AccommodationService extends CrudService<AccommodationDto, Long> {
    public List<AccommodationDto> getByAccommodationType(AccommodationType type);
    public AccommodationDto updateAvailability(Long accommodationId, AvailabilityDto availabilityDto);
    public List<AccommodationDto> getByLocationNumOfGuestsAndDate(String location, int numOfGuests);
    public List<AccommodationDto> getByAmenities(List<String> amenities);
    public Accommodation findModelById(Long id);

}
