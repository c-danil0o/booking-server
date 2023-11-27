package com.komsije.booking.dto;

import com.komsije.booking.model.TimeSlot;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class TimeSlotDTO {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double price;
    private boolean isOccupied;

    public TimeSlotDTO(TimeSlot timeSlot){
        this.id = timeSlot.getId();
        this.startDate = timeSlot.getStartDate();
        this.endDate = timeSlot.getEndDate();
        this.price = timeSlot.getPrice();
        this.isOccupied = timeSlot.isOccupied();
    }
}
