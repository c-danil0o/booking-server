package com.komsije.booking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Image {
    private Long id;
    private String name;
    private String type;
    private byte[] content;
}
