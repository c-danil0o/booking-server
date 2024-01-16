package com.komsije.booking.dto;

import lombok.Data;

@Data
public class AccommodationTotalEarnings {
    private String name;
    private double totalEarnings;
    private int totalReservations;
}
