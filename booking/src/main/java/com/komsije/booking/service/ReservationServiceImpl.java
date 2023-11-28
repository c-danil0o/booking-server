package com.komsije.booking.service;

import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.mapper.ReservationMapper;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.model.ReservationStatus;
import com.komsije.booking.repository.ReservationRepository;
import com.komsije.booking.service.interfaces.ReportService;
import com.komsije.booking.service.interfaces.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationMapper mapper;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationDto findById(Long id) {
        return mapper.toDto(reservationRepository.findById(id).orElseGet(null));
    }

    public List<ReservationDto> findAll() {
        return mapper.toDto(reservationRepository.findAll());
    }

    public List<ReservationDto> getByReservationStatus(ReservationStatus reservationStatus){return mapper.toDto(reservationRepository.findReservationsByReservationStatus(reservationStatus));}
    public ReservationDto save(ReservationDto reservationDto) {
        reservationRepository.save(mapper.fromDto(reservationDto));
        return reservationDto;
    }

    @Override
    public ReservationDto update(ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(reservationDto.getId()).orElseGet(null);
        if (reservation == null){
            return null;
        }
        Reservation updatedReservation = mapper.fromDto(reservationDto);
        reservationRepository.save(updatedReservation);
        return reservationDto;
    }

    public void delete(Long id) {
        reservationRepository.deleteById(id);
    }
}
