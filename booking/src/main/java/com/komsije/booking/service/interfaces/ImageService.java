package com.komsije.booking.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    byte[] findByFilename(String filename) throws IOException;
    void save(MultipartFile file);
}
