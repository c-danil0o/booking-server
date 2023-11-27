package com.komsije.booking.service.interfaces;

import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.AccommodationType;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;

public interface AccommodationService extends CrudService<Accommodation, Long> {
    public List<Accommodation> getByAccommodationType(AccommodationType type);
}
