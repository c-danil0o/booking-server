package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.GuestDto;
import com.komsije.booking.model.Guest;
import com.komsije.booking.service.interfaces.crud.CrudService;

public interface GuestService extends CrudService<GuestDto, Long> {
}
