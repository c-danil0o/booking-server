package com.komsije.booking.mapper;

import com.komsije.booking.dto.TimeSlotDto;
import com.komsije.booking.model.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
public abstract class TimeSlotMapper {
    public TimeSlotDto toDto(TimeSlot timeSlot) {
        if ( timeSlot == null ) {
            return null;
        }

        TimeSlotDto timeSlotDto = new TimeSlotDto();

        timeSlotDto.setId( timeSlot.getId() );
        timeSlotDto.setStartDate( timeSlot.getStartDate().atStartOfDay() );
        timeSlotDto.setEndDate( timeSlot.getEndDate().atStartOfDay() );
        timeSlotDto.setPrice( timeSlot.getPrice() );
        timeSlotDto.setOccupied( timeSlot.isOccupied() );

        return timeSlotDto;
    }


    public TimeSlot fromDto(TimeSlotDto timeSlotDto) {
        if ( timeSlotDto == null ) {
            return null;
        }

        TimeSlot timeSlot = new TimeSlot();

        timeSlot.setId( timeSlotDto.getId() );
        timeSlot.setStartDate( timeSlotDto.getStartDate().toLocalDate() );
        timeSlot.setEndDate( timeSlotDto.getEndDate().toLocalDate() );
        timeSlot.setPrice( timeSlotDto.getPrice() );
        timeSlot.setOccupied( timeSlotDto.isOccupied() );

        return timeSlot;
    }


    public void update(TimeSlot timeSlot, TimeSlotDto timeSlotDto) {
        if ( timeSlotDto == null ) {
            return;
        }

        if ( timeSlotDto.getId() != null ) {
            timeSlot.setId( timeSlotDto.getId() );
        }
        if ( timeSlotDto.getStartDate() != null ) {
            timeSlot.setStartDate( timeSlotDto.getStartDate().toLocalDate() );
        }
        if ( timeSlotDto.getEndDate() != null ) {
            timeSlot.setEndDate( timeSlotDto.getEndDate().toLocalDate() );
        }
        timeSlot.setPrice( timeSlotDto.getPrice() );
        timeSlot.setOccupied( timeSlotDto.isOccupied() );
    }
}
