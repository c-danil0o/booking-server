package com.komsije.booking.mapper;

import com.komsije.booking.dto.TimeSlotDto;
import com.komsije.booking.model.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TimeSlotMapper {
    TimeSlotDto toDto(TimeSlot timeSlot);
    TimeSlot fromDto(TimeSlotDto timeSlotDto);
    void update(@MappingTarget TimeSlot timeSlot, TimeSlotDto timeSlotDto);
}
