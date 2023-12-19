package com.komsije.booking.service;

import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.dto.ReservationViewDto;
import com.komsije.booking.exceptions.ElementNotFoundException;
import com.komsije.booking.exceptions.InvalidTimeSlotException;
import com.komsije.booking.exceptions.PendingReservationException;
import com.komsije.booking.mapper.ReservationMapper;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.repository.ReservationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.GuestService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationMapper mapper;
    private final ReservationRepository reservationRepository;
    private final AccommodationService accommodationService;
    private final GuestService guestService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, AccommodationService accommodationService, GuestService guestService) {
        this.reservationRepository = reservationRepository;
        this.accommodationService = accommodationService;
        this.guestService = guestService;
    }

    public ReservationDto findById(Long id) throws ElementNotFoundException {
        return mapper.toDto(reservationRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!")));
    }

    public List<ReservationDto> findAll() {
        return mapper.toDto(reservationRepository.findAll());
    }

    public List<ReservationViewDto> getAll(){
        return mapper.toViewDto(reservationRepository.findAll());
    }

    @Override
    public List<ReservationViewDto> getByHostId(Long id) {
        List<Reservation> reservations = this.reservationRepository.findByHostId(id);
        return mapper.toViewDto(reservations);
    }

    @Override
    public List<ReservationViewDto> getByGuestId(Long id) {
        List<Reservation> reservations = this.reservationRepository.findByGuestId(id);
        return mapper.toViewDto(reservations);
    }

    public List<ReservationDto> getByReservationStatus(ReservationStatus reservationStatus){return mapper.toDto(reservationRepository.findReservationsByReservationStatus(reservationStatus));}

    @Override
    public boolean hasActiveReservations(Long accountId) {
        List<Reservation> reservations = reservationRepository.findAll();
        for (Reservation reservation: reservations){
            if (reservation.getGuest().getId().equals(accountId) && reservation.getReservationStatus().equals(ReservationStatus.Active)){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean overlappingActiveReservationsExist(LocalDateTime startDate, LocalDateTime endDate) throws InvalidTimeSlotException {
        if (startDate.isAfter(endDate)){
            throw new InvalidTimeSlotException("Start date is after end date");
        }
       List<Reservation> reservations = reservationRepository.findReservationsByReservationStatus(ReservationStatus.Active);
       for(Reservation reservation: reservations){
           if (startDate.isBefore(reservation.getStartDate().plusDays(reservation.getDays()))&& reservation.getStartDate().isBefore(endDate)){
               return true;
           }
       }
        return false;
    }

    @Override
    public boolean deleteRequest(Long id) throws ElementNotFoundException, PendingReservationException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        if (reservation.getReservationStatus().equals(ReservationStatus.Pending)){
            reservationRepository.delete(reservation);
            return true;
        }else{
            throw new PendingReservationException("Can't delete non pending reservations!");
        }

    }

    @Override
    public ReservationDto updateStatus(Long id, ReservationStatus status) throws ElementNotFoundException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        reservation.setReservationStatus(status);
        return mapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    public boolean acceptReservationRequest(Long id) throws ElementNotFoundException, PendingReservationException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        if(reservation.getReservationStatus().equals(ReservationStatus.Pending) || reservation.getReservationStatus().equals(ReservationStatus.Denied)){
            reservation.setReservationStatus(ReservationStatus.Approved);
            reservationRepository.save(reservation);
        }else{
            throw new PendingReservationException("Reservation is not in pending or denied state!");
        }
        LocalDateTime startDate = reservation.getStartDate();
        LocalDateTime endDate = reservation.getStartDate().plusDays(reservation.getDays());
        List<Reservation> reservations = reservationRepository.findReservationsByReservationStatus(ReservationStatus.Pending);
        for(Reservation res: reservations){
            if (startDate.isBefore(reservation.getStartDate().plusDays(reservation.getDays()))&& reservation.getStartDate().isBefore(endDate)){
                res.setReservationStatus(ReservationStatus.Denied);
                reservationRepository.save(res);
            }
        }
        return true;
    }

    @Override
    public boolean denyReservationRequest(Long id) throws ElementNotFoundException, PendingReservationException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        if(reservation.getReservationStatus().equals(ReservationStatus.Pending) || reservation.getReservationStatus().equals(ReservationStatus.Approved)){
            reservation.setReservationStatus(ReservationStatus.Denied);
            reservationRepository.save(reservation);
        }else{
            throw new PendingReservationException("Reservation is not in pending or approved state!");
        }
//        todo: update accommodations if reservation was approved
        return true;
    }

    @Override
    public boolean cancelReservationRequest(Long id) throws ElementNotFoundException, PendingReservationException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        if(reservation.getReservationStatus().equals(ReservationStatus.Pending) || reservation.getReservationStatus().equals(ReservationStatus.Approved)){
            reservation.setReservationStatus(ReservationStatus.Cancelled);
            reservationRepository.save(reservation);
        }else{
            throw new PendingReservationException("Reservation is not in pending or approved state!");
        }
//        todo: update accommodations if reservation was approved

        guestService.increaseCancelations(reservation.getGuest().getId());
        return true;
    }


    public ReservationDto save(ReservationDto reservationDto) throws ElementNotFoundException {
        Reservation reservation = mapper.fromDto(reservationDto);
        Accommodation accommodation = accommodationService.findModelById(reservationDto.getAccommodationId());
        reservation.setAccommodation(accommodation);
        reservationRepository.save(reservation);
        return reservationDto;
    }

    @Override
    public ReservationDto update(ReservationDto reservationDto) throws ElementNotFoundException {
        Reservation reservation = reservationRepository.findById(reservationDto.getId()).orElseThrow(() ->  new ElementNotFoundException("Element with given ID doesn't exist!"));
        mapper.update(reservation, reservationDto);
        reservationRepository.save(reservation);
        return reservationDto;
    }

    public void delete(Long id) throws ElementNotFoundException {
        if (reservationRepository.existsById(id)){
            reservationRepository.deleteById(id);
        }else{
            throw  new ElementNotFoundException("Element with given ID doesn't exist!");
        }

    }
}
