package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.ReservationDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDto toDto(Reservation reservation);
    Reservation fromDto(ReservationDto reservationDto);
    List<ReservationDto> toDto(List<Reservation> reservationList);
    void update(@MappingTarget Reservation reservation, ReservationDto reservationDto);
}
