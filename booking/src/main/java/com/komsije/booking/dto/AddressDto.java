package com.komsije.booking.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

import java.io.Serializable;


@Data
public class AddressDto {

    private Long id;

    private String street;

    private String number;

    private String city;
}