package com.komsije.booking.service.interfaces;

import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.dto.ReservationViewDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.InvalidTimeSlotException;
import com.komsije.booking.exceptions.PendingReservationException;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.service.interfaces.crud.CrudService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ReservationService extends CrudService<ReservationDto, Long> {
    public List<ReservationViewDto> getAll();
    public List<ReservationViewDto> getByHostId(Long id);
    public List<ReservationViewDto> getByGuestId(Long id);
    public List<ReservationDto> getByReservationStatus(ReservationStatus reservationStatus);

    public boolean hasActiveReservations(Long accountId);
    public boolean overlappingActiveReservationsExist(LocalDateTime startDate, LocalDateTime endDate) throws InvalidTimeSlotException;
    public boolean deleteRequest(Long id) throws ElementNotFoundException, PendingReservationException;
    public ReservationDto updateStatus(Long id, ReservationStatus status) throws ElementNotFoundException;
    public boolean acceptReservationRequest(Long id) throws ElementNotFoundException, PendingReservationException;
    public boolean denyReservationRequest(Long id) throws ElementNotFoundException, PendingReservationException;
    public boolean cancelReservationRequest(Long id) throws ElementNotFoundException, PendingReservationException;



}
