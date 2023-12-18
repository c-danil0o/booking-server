package com.komsije.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ImageDto {
    private List<String> files = new ArrayList<>();
}
