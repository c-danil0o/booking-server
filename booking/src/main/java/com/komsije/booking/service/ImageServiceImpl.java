package com.komsije.booking.service;

import com.komsije.booking.repository.ImageRepository;
import com.komsije.booking.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public byte[] findByFilename(String filename) throws IOException {
        return this.imageRepository.findByFilename(filename);
    }

    @Override
    public void save(MultipartFile file) {
        this.imageRepository.save(file);
    }
}
