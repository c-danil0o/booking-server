package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.NewReservationDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.dto.ReservationViewDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import com.komsije.booking.service.interfaces.AccountService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses={GuestMapper.class} , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ReservationMapper {
    @Autowired
    private AccountService accountService;
    public ReservationDto toDto(Reservation reservation){
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setStartDate(reservation.getStartDate());
        reservationDto.setDays(reservation.getDays());
        reservationDto.setReservationStatus(reservation.getReservationStatus());
        reservationDto.setAccommodationId(reservation.getAccommodation().getId());
        reservationDto.setGuestId(reservation.getGuestId());
        reservationDto.setHostId(reservation.getHostId());
        reservationDto.setDateCreated(reservation.getDateCreated());

        return reservationDto;
    }
    public abstract Reservation fromDto(ReservationDto reservationDto);
    public List<ReservationDto> toDto(List<Reservation> reservationList){
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation:
             reservationList) {
            reservationDtos.add(toDto(reservation));
        }
        return reservationDtos;
    }
    public abstract void update(@MappingTarget Reservation reservation, ReservationDto reservationDto);

    public ReservationViewDto toViewDto(Reservation reservation){
        ReservationViewDto reservationDto = new ReservationViewDto();
        reservationDto.setId(reservation.getId());
        reservationDto.setStartDate(reservation.getStartDate());
        reservationDto.setEndDate(reservation.getStartDate().plusDays(reservation.getDays()));
        reservationDto.setPrice(reservation.getPrice());
        Accommodation accommodation = reservation.getAccommodation();
        reservationDto.setAccommodationName(accommodation.getName()+" , "+ accommodation.getAddress().getCity());
        reservationDto.setGuestEmail(accountService.getEmail(reservation.getGuestId()));
        reservationDto.setHostEmail(accountService.getEmail(reservation.getHostId()));
        reservationDto.setReservationStatus(reservation.getReservationStatus());
        reservationDto.setNumberOfGuests(reservation.getNumberOfGuests());
        return reservationDto;
    }
    public List<ReservationViewDto> toViewDto(List<Reservation> reservationList){
        List<ReservationViewDto> reservationDtos = new ArrayList<>();
        for (Reservation reservation:
                reservationList) {
            reservationDtos.add(toViewDto(reservation));
        }
        return reservationDtos;
    }

    public Reservation fromNewDto(NewReservationDto reservationDto){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDto.getId());
        reservation.setReservationStatus(reservationDto.getReservationStatus());
        reservation.setHostId(reservationDto.getHostId());
        reservation.setGuestId(reservationDto.getGuestId());
        reservation.setDays(reservationDto.getDays());
        reservation.setStartDate(reservationDto.getStartDate());
        reservation.setPrice(reservationDto.getPrice());
        reservation.setNumberOfGuests(reservationDto.getNumberOfGuests());
        return reservation;
    }
}
