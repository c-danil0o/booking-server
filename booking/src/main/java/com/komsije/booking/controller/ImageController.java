package com.komsije.booking.controller;

import com.komsije.booking.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ImageController {
    private final ImageService imageService;


    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping(value = "files/{id}",
            headers = "Accept=image/jpeg, image/jpg, image/png, image/gif",
            produces = "image/jpg",
            method = RequestMethod.GET)
    public ResponseEntity<byte[]> getArticleImage(@PathVariable String id) {
        byte[] image = new byte[0];
        try {
            image = imageService.findByFilename(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(image.length);
            return new ResponseEntity<byte[]>(image,headers, HttpStatus.OK);
        } catch (IOException e) {
            System.out.println("image not found: " + id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

    @PostMapping(value="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile[] file) {
        String message = "";
        try {
            for (MultipartFile f: file){
                this.imageService.save(f);
                message = "Uploaded the file successfully: " + f.getOriginalFilename();
            }
            return ResponseEntity.status(HttpStatus.OK).body("Uploaded!");
        } catch (Exception e) {
            message = "Could not upload the file: " + file.toString() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Upload failed!");
        }
    }
}
