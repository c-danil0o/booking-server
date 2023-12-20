package com.komsije.booking.repository;

import com.komsije.booking.exceptions.UploadFileException;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class ImageRepository {
    private final String location = System.getProperty("user.dir") + "/src/main/upload/images/";
    private final Path root = Paths.get(location);

    public byte[] findByFilename(String filename) throws IOException {
        System.out.println(location + filename);
        BufferedImage bImage = ImageIO.read(new File(location + filename));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String formatName = filename.substring(filename.lastIndexOf('.') + 1);
        ImageIO.write(bImage, formatName, bos);
        return bos.toByteArray();
    }

    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                System.out.println("File already exists, skipping upload!" + file.getOriginalFilename());
                return;
            }

            throw new UploadFileException(e.getMessage());
        }
    }



}
