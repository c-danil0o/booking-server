package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.model.Guest;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.List;
import java.util.Set;

public interface GuestService extends CrudService<GuestDto, Long> {
    public List<AccommodationDto> getFavoritesByGuestId(Long id);
    public List<AccommodationDto> addToFavorites(Long id, AccommodationDto accommodationDto);
 }
