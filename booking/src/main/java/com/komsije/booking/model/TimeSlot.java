package com.komsije.booking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class TimeSlot {
    @Id
    private Long id;
    private Date startDate;
    private Date endDate;
    private double price;
    private boolean isOccupied;
}
