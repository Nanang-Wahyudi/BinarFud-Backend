package app.BinarFudBackend.service.impl;

import app.BinarFudBackend.model.ImageData;
import app.BinarFudBackend.repository.ImageDataRepository;
import app.BinarFudBackend.service.ImageDataService;
import app.BinarFudBackend.config.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Transactional
@Service
public class ImageDataServiceImpl implements ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    @Override
    public byte[] downloadImage(String fileName) {
        Optional<ImageData> dbImageData = imageDataRepository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getImageData());
        return images;
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<Optional<Long>> findIdImageByName(String imageName) {
        return CompletableFuture.supplyAsync(() -> imageDataRepository.findIdImageByNameWithQuery(imageName));
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<Optional<ImageData>> findByIdImage(Long idImage) {
        return CompletableFuture.supplyAsync(() -> imageDataRepository.findByIdImage(idImage));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ImageData> findByName(String imageName) {
        return imageDataRepository.findByName(imageName);
    }

}
