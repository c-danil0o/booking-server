package com.komsije.booking.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccommodationAnalysis {
    private String name;
    private List<Double> moneyPerMonth;
    private List<Integer> reservationsPerMonth;
}
