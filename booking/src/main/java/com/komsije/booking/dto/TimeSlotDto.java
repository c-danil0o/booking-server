package com.komsije.booking.dto;

import com.komsije.booking.model.TimeSlot;
import jakarta.annotation.sql.DataSourceDefinition;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Data
public class TimeSlotDto {
    private Long id;
    private Date startDate;
    private Date endDate;
    private double price;
    private boolean isOccupied;

}
