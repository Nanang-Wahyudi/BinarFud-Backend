//package binar.challenge7.binarfud_v7.service;
//
//import binar.challenge7.binarfud_v7.model.ImageData;
//import binar.challenge7.binarfud_v7.repository.ImageDataRepository;
//import binar.challenge7.binarfud_v7.service.impl.ImageDataServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.Optional;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class ImageDataServiceTest {
//
//    @Mock
//    private ImageDataRepository imageDataRepository;
//
//    @InjectMocks
//    private ImageDataServiceImpl imageDataService;


//    @Test
//    void uploadImage_TestSuccess() throws IOException {
//        byte[] imageBytes = "Sample Image Data".getBytes();
//        String originalFilename = "sample-image.jpg";
//        String contentType = "image/jpeg";
//
//        Mockito.when(imageDataRepository.save(Mockito.any(ImageData.class))).thenReturn(createSampleImageData());
//
//        MultipartFile file = Mockito.mock(MultipartFile.class);
//        Mockito.when(file.getBytes()).thenReturn(imageBytes);
//        Mockito.when(file.getOriginalFilename()).thenReturn(originalFilename);
//        Mockito.when(file.getContentType()).thenReturn(contentType);
//
//        String result = imageDataService.uploadImage(file);
//
//        Mockito.verify(imageDataRepository).save(Mockito.any(ImageData.class));
//
//        Assertions.assertEquals("file uploaded successfully : " + originalFilename, result);
//    }
//
//    private ImageData createSampleImageData() {
//        return ImageData.builder()
//                .name("sample-image.jpg")
//                .type("image/jpeg")
//                .imageData("Sample Compressed Image Data".getBytes())
//                .build();
//    }
//
//    @Test
//    void downloadImage_TestSuccess() {
//        String fileName = "sample-image.jpg";
//        byte[] compressedImageData = "Sample Compressed Image Data".getBytes();
//        byte[] decompressedImageData = "Sample Image Data".getBytes();
//
//        ImageData image = createSampleImageData(fileName, compressedImageData);
//        Mockito.when(imageDataRepository.findByName(fileName)).thenReturn(Optional.of(image));
//
//        byte[] result = imageDataService.downloadImage(fileName);
//
//        Mockito.verify(imageDataRepository).findByName(fileName);
//
//        Assertions.assertArrayEquals(decompressedImageData, result);
//    }
//
//    private ImageData createSampleImageData(String fileName, byte[] compressedImageData) {
//        return ImageData.builder()
//                .name(fileName)
//                .type("image/jpeg")
//                .imageData(compressedImageData)
//                .build();
//    }

//}
