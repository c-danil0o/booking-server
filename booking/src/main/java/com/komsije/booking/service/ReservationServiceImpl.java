package com.komsije.booking.service;

import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.mapper.ReservationMapper;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.repository.ReservationRepository;
import com.komsije.booking.service.interfaces.AccommodationService;
import com.komsije.booking.service.interfaces.ReportService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationMapper mapper;
    private final ReservationRepository reservationRepository;
    private final AccommodationService accommodationService;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, AccommodationService accommodationService) {
        this.reservationRepository = reservationRepository;
        this.accommodationService = accommodationService;
    }

    public ReservationDto findById(Long id) {
        try{
            return mapper.toDto(reservationRepository.findById(id).orElseGet(null));
        }
        catch (NullPointerException e){
            return null;
        }
    }

    public List<ReservationDto> findAll() {
        return mapper.toDto(reservationRepository.findAll());
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
    /*public static boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && start2.before(end1);
    }*/
    @Override
    public boolean hasOverlappingReservations(LocalDateTime startDate, LocalDateTime endDate) {
       List<Reservation> reservations = reservationRepository.findReservationsByReservationStatus(ReservationStatus.Active);
       for(Reservation reservation: reservations){
           if (startDate.isBefore(reservation.getStartDate().plusDays(reservation.getDays()))&& reservation.getStartDate().isBefore(endDate)){
               return true;
           }
       }
        return false;
    }

    @Override
    public boolean deleteRequest(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseGet(null);
        if (reservation == null){
            return false;
        }
        if (reservation.getReservationStatus().equals(ReservationStatus.Pending)){
            reservationRepository.delete(reservation);
            return true;
        }
        return false;

    }

    @Override
    public ReservationDto updateStatus(Long id, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(id).orElseGet(null);
        if (reservation == null){
            return null;
        }
        reservation.setReservationStatus(status);
        return mapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    public boolean acceptReservationRequest(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseGet(null);
        if (reservation == null){
            return false;
        }
        if(reservation.getReservationStatus().equals(ReservationStatus.Pending)){
            reservation.setReservationStatus(ReservationStatus.Approved);
            reservationRepository.save(reservation);
        }else{
            return false;
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


    public ReservationDto save(ReservationDto reservationDto) {
        Reservation reservation = mapper.fromDto(reservationDto);
        Accommodation accommodation = accommodationService.findModelById(reservationDto.getAccommodationId());
        reservation.setAccommodation(accommodation);
        reservationRepository.save(reservation);
        return reservationDto;
    }

    @Override
    public ReservationDto update(ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(reservationDto.getId()).orElseGet(null);
        if (reservation == null){
            return null;
        }
        mapper.update(reservation, reservationDto);
        reservationRepository.save(reservation);
        return reservationDto;
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
