//package binar.challenge7.binarfud_v7.controller;
//
//import binar.challenge7.binarfud_v7.model.ImageData;
//import binar.challenge7.binarfud_v7.model.Merchants;
//import binar.challenge7.binarfud_v7.model.Products;
//import binar.challenge7.binarfud_v7.model.response.ProductsResponse;
//import binar.challenge7.binarfud_v7.service.impl.ProductsServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class ProductsControllerTest {
//
//    @InjectMocks
//    private ProductsController productsController;
//
//    @Mock
//    private ProductsServiceImpl productsService;


//    @Test
//    void addProduct_TestSuccess() {
//        String merchantCode = "SampleMerchantCode";
//        String imageName = "SampleImageName";
//
//        Products products = Products.builder()
//                .productCode("SampleCode")
//                .productName("SampleName")
//                .productPrice(100.0)
//                .merchants(new Merchants())
//                .imageData(new ImageData())
//                .build();
//
//        Mockito.when(productsService.addNewProduct(products, merchantCode, imageName)).thenReturn(products);
//
//        ResponseEntity<Object> response = productsController.addProduct(products, merchantCode, imageName);
//
//        Mockito.verify(productsService).addNewProduct(products, merchantCode, imageName);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assertions.assertEquals("Add New Product Successful", response.getBody());
//    }
//
//    @Test
//    void updateProduct_TestSuccess() {
//        String oldProductCode = "SampleOldProductCode";
//        String newMerchantCode = "SampleNewMerchantCode";
//        String newImageName = "SampleNewImageName";
//
//        Products products = Products.builder()
//                .productId("SampleId")
//                .productCode("SampleCode")
//                .productName("SampleName")
//                .productPrice(100.0)
//                .merchants(new Merchants())
//                .imageData(new ImageData())
//                .build();
//
//        Mockito.when(productsService.updateProductByCode(products, oldProductCode, newMerchantCode, newImageName)).thenReturn(products);
//
//        ResponseEntity<Object> response = productsController.updateProduct(products, oldProductCode, newMerchantCode, newImageName);
//
//        Mockito.verify(productsService).updateProductByCode(products, oldProductCode, newMerchantCode, newImageName);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        Assertions.assertEquals("Update Product Successful", response.getBody());
//    }
//
//    @Test
//    void deleteProduct_TestSuccess() {
//        String productCode = "SampleProductCode";
//
//        Products products = Products.builder()
//                .productCode("SampleCode")
//                .productName("SampleName")
//                .productPrice(100.0)
//                .merchants(new Merchants())
//                .imageData(new ImageData())
//                .build();
//
//        Mockito.when(productsService.findByProductCode(productCode)).thenReturn(Optional.of(products));
//        Mockito.when(productsService.deleteProductByCode(productCode)).thenReturn(true);
//
//        ResponseEntity<Object> response = productsController.deleteProduct(productCode);
//
//        Mockito.verify(productsService).findByProductCode(productCode);
//        Mockito.verify(productsService).deleteProductByCode(productCode);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getAllProduct_TestSuccess() {
//        List<ProductsResponse> responseList = new ArrayList<>();
//
//        ProductsResponse productsResponse1 = ProductsResponse.builder()
//                .dtoProductCode("SampleCode1")
//                .dtoProductName("SampleName1")
//                .dtoProductPrice(100.0)
//                .dtoMerchantName("SampleMerchantName1")
//                .dtoImageName("SampleImageName1")
//                .build();
//
//        ProductsResponse productsResponse2 = ProductsResponse.builder()
//                .dtoProductCode("SampleCode2")
//                .dtoProductName("SampleName2")
//                .dtoProductPrice(100.0)
//                .dtoMerchantName("SampleMerchantName2")
//                .dtoImageName("SampleImageName2")
//                .build();
//
//        responseList.add(productsResponse1);
//        responseList.add(productsResponse2);
//
//        Mockito.when(productsService.getAllProduct()).thenReturn(responseList);
//
//        ResponseEntity<Object> response = productsController.getAllProduct();
//
//        Mockito.verify(productsService).getAllProduct();
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getProductDetail_TestSuccess() {
//        String productName = "SampleName";
//
//        ProductsResponse productsResponse = ProductsResponse.builder()
//                .dtoProductCode("SampleCode")
//                .dtoProductName(productName)
//                .dtoProductPrice(100.0)
//                .dtoMerchantName("SampleMerchantName")
//                .dtoImageName("SampleImageName")
//                .build();
//
//        Mockito.when(productsService.getProductDetailByName(productName)).thenReturn(productsResponse);
//
//        ResponseEntity<Object> response = productsController.getProductDetail(productName);
//
//        Mockito.verify(productsService).getProductDetailByName(productName);
//
//        Assertions.assertNotNull(response);
//
//        if (productsResponse != null) {
//            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        } else {
//            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        }
//    }
//
//    @Test
//    void getProductByMerchantName_TestSuccess() {
//        String merchantName = "SampleMerchantName";
//
//        List<ProductsResponse> responseList = Collections.singletonList(ProductsResponse.builder()
//                .dtoProductCode("SampleCode")
//                .dtoProductName("SampleName")
//                .dtoProductPrice(100.0)
//                .dtoMerchantName(merchantName)
//                .dtoImageName("SampleImageName")
//                .build());
//
//        Mockito.when(productsService.getProductByMerchantName(merchantName)).thenReturn(responseList);
//
//        ResponseEntity<Object> response = productsController.getProductByMerchantName(merchantName);
//
//        Mockito.verify(productsService).getProductByMerchantName(merchantName);
//
//        Assertions.assertNotNull(response);
//
//        if (!responseList.isEmpty()) {
//            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        } else {
//            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        }
//    }

//}
