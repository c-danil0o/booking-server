package com.komsije.booking.dto;

import com.komsije.booking.model.*;
import lombok.*;

import java.util.Date;

@Data
public class ReservationDto {
    private Long id;
    private Date startDate;
    private int days;
    private double price;
    private ReservationStatus reservationStatus;
    private Long accommodationId;


}
