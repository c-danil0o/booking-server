package com.komsije.booking.dto;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.komsije.booking.model.Address}
 */
@EqualsAndHashCode(callSuper = true)
@Value
public record AddressDto(Long id, String street, String city, String number) implements Serializable {
}