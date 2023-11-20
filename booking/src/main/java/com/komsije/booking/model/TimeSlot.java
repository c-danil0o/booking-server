package com.komsije.booking.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class TimeSlot {
    private Date startDate;
    private Date endDate;
    private double price;
    private boolean isOccupied;

}
