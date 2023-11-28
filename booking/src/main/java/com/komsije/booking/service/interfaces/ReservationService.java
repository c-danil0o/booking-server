package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.util.Date;
import java.util.List;

public interface ReservationService extends CrudService<ReservationDto, Long> {
    public List<ReservationDto> getByReservationStatus(ReservationStatus reservationStatus);

    public boolean hasActiveReservations(Long accountId);
    public boolean hasOverlappingReservations(Date startDate, Date endDate);
}
