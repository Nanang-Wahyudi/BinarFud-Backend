//package binar.challenge7.binarfud_v7.controller;
//
//import binar.challenge7.binarfud_v7.service.impl.ImageDataServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class ImageDataControllerTest {
//
//    @InjectMocks
//    private ImageDataController imageDataController;
//
//    @Mock
//    private ImageDataServiceImpl imageDataService;


//    @Test
//    void uploadImage_TestSuccess() throws IOException {
//        MultipartFile sampleFile = createSampleMultipartFile();
//
//        Mockito.when(imageDataService.uploadImage(sampleFile)).thenReturn("SampleImageFileName");
//
//        ResponseEntity<?> response = imageDataController.uploadImage(sampleFile);
//
//        Mockito.verify(imageDataService).uploadImage(sampleFile);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assertions.assertEquals("SampleImageFileName", response.getBody());
//    }
//
//    private MultipartFile createSampleMultipartFile() {
//        byte[] content = "Sample file content".getBytes();
//        return new MockMultipartFile("sampleFile", "sampleFile.txt", "text/plain", content);
//    }
//
//    @Test
//    void downloadImage_TestSuccess() {
//        String fileName = "SampleImageFileName";
//        byte[] imageData = "Sample image data".getBytes();
//
//        Mockito.when(imageDataService.downloadImage(fileName)).thenReturn(imageData);
//
//        ResponseEntity<?> response = imageDataController.downloadImage(fileName);
//
//        Mockito.verify(imageDataService).downloadImage(fileName);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assertions.assertEquals(MediaType.valueOf("image/png"), response.getHeaders().getContentType());
//        Assertions.assertEquals(imageData, response.getBody());
//    }


//}
