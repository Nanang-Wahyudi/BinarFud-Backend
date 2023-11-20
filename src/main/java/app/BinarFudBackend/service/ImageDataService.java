package app.BinarFudBackend.service;

import app.BinarFudBackend.model.ImageData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface ImageDataService {

    String uploadImage(MultipartFile file) throws IOException;

    byte[] downloadImage(String fileName);

    CompletableFuture<Optional<Long>> findIdImageByName(String imageName);

    CompletableFuture<Optional<ImageData>> findByIdImage(Long idImage);

    Optional<ImageData> findByName(String imageName);

}
