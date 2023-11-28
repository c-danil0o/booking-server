package com.komsije.booking.mapper;

import com.komsije.booking.dto.AccommodationDto;
import com.komsije.booking.dto.AvailabilityDto;
import com.komsije.booking.dto.TimeSlotDto;
import com.komsije.booking.model.Accommodation;
import com.komsije.booking.model.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, TimeSlotMapper.class})
public abstract class AccommodationMapper {
    public abstract AccommodationDto toDto(Accommodation accommodation);
    public abstract Accommodation fromDto(AccommodationDto accommodationDto);
    public abstract List<AccommodationDto> toDto(List<Accommodation> accommodationList);
    public abstract void update(@MappingTarget Accommodation accommodation, AccommodationDto accommodationDto);
    public void update(@MappingTarget Accommodation accommodation, AvailabilityDto availabilityDto){
        if (availabilityDto.getCancellationDeadline() != null)
            accommodation.setCancellationDeadline(availabilityDto.getCancellationDeadline());
        for(TimeSlotDto timeSlotDto: availabilityDto.getAvailability()){
            accommodation.getAvailability().add(new TimeSlot(null, timeSlotDto.getStartDate(), timeSlotDto.getEndDate(), timeSlotDto.getPrice(), timeSlotDto.isOccupied()));
        }
    }
}
