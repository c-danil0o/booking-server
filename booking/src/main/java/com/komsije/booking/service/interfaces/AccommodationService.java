package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface AccommodationService extends CrudService<AccommodationDto, Long> {
    public List<AccommodationDto> getByAccommodationType(AccommodationType type);
}
