package com.komsije.booking.controller;

import com.komsije.booking.dto.ImageDto;
import com.komsije.booking.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated

public class ImageController {
    private final ImageService imageService;


    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "files/{id}",
            headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
            produces = "image/jpg",
            method = RequestMethod.GET)
    public ResponseEntity<byte[]> getArticleImage(@PathVariable String id) throws IOException {
        byte[] image = imageService.findByFilename(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new ResponseEntity<byte[]>(image,headers, HttpStatus.OK);
    }

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImageDto> uploadFile(@RequestParam("file") MultipartFile[] file) {
        List<String> files = new ArrayList<>();
        try {
            for (MultipartFile f: file){
                this.imageService.save(f);
                files.add(f.getOriginalFilename());
            }
            return new ResponseEntity<>(new ImageDto(files), HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ImageDto(files));
        }
    }
}
