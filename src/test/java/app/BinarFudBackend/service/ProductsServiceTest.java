//package binar.challenge7.binarfud_v7.service;
//
//import binar.challenge7.binarfud_v7.model.ImageData;
//import binar.challenge7.binarfud_v7.model.Merchants;
//import binar.challenge7.binarfud_v7.model.Products;
//import binar.challenge7.binarfud_v7.model.response.ProductsResponse;
//import binar.challenge7.binarfud_v7.repository.ImageDataRepository;
//import binar.challenge7.binarfud_v7.repository.MerchantsRepository;
//import binar.challenge7.binarfud_v7.repository.ProductsRepository;
//import binar.challenge7.binarfud_v7.service.impl.ProductsServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class ProductsServiceTest {
//
//    @InjectMocks
//    private ProductsServiceImpl productsService;
//
//    @Mock
//    private ProductsRepository productsRepository;
//
//    @Mock
//    private MerchantsRepository merchantsRepository;
//
//    @Mock
//    private ImageDataRepository imageDataRepository;


//    @Test
//    void addNewProduct_TestSuccess() {
//        String merchantCode = "M123";
//        String imageName = "image123";
//
//        Products newProduct = Products.builder()
//                .productId("1")
//                .productCode("P456")
//                .productName("Product 2")
//                .productPrice(200.0)
//                .build();
//
//        UUID merchantId = UUID.randomUUID();
//        newProduct.setMerchants(new Merchants());
//        newProduct.setImageData(new ImageData());
//
//        Mockito.when(merchantsRepository.findMerchantIdByMerchantCode(merchantCode)).thenReturn(merchantId);
//        Mockito.when(imageDataRepository.findIdImageByNameWithQuery(imageName)).thenReturn(1L);
//        Mockito.when(merchantsRepository.findByMerchantId(String.valueOf(merchantId))).thenReturn(newProduct.getMerchants());
//        Mockito.when(imageDataRepository.findByIdImage(1L)).thenReturn(newProduct.getImageData());
//
//        Products result = productsService.addNewProduct(newProduct, merchantCode, imageName);
//
//        Assertions.assertNotNull(result);
//
//        Assertions.assertNotNull(result.getMerchants());
//        Assertions.assertNotNull(result.getImageData());
//        Assertions.assertEquals(merchantId.toString(), result.getMerchants().getMerchantId());
//        Assertions.assertEquals("1", result.getImageData().getIdImage().toString());
//
//        Mockito.verify(productsRepository, Mockito.times(1)).save(newProduct);
//    }
//
//    @Test
//    void addNewProductMerchant_TestNotFound() {
//        String merchantCode = "Non-Existent Merchant";
//        String imageName = "image123";
//
//        Mockito.when(merchantsRepository.findMerchantIdByMerchantCode(merchantCode)).thenReturn(null);
//
//        Products result = productsService.addNewProduct(new Products(), merchantCode, imageName);
//
//        Assertions.assertNull(result);
//    }
//
//    @Test
//    void addNewProductImage_TestNotFound() {
//        String merchantCode = "M123";
//        String imageName = "Non-Existent Image";
//
//        UUID merchantId = UUID.randomUUID();
//
//        Mockito.when(merchantsRepository.findMerchantIdByMerchantCode(merchantCode)).thenReturn(merchantId);
//        Mockito.when(imageDataRepository.findIdImageByNameWithQuery(imageName)).thenReturn(null);
//
//        Products result = productsService.addNewProduct(new Products(), merchantCode, imageName);
//
//        Assertions.assertNull(result);
//    }
//
//    @Test
//    void updateProductByCode_TestSuccess() {
//        String oldProductCode = "P123";
//        String newMerchantCode = "M456";
//        String imageName = "image123";
//
//        Products existingProduct = Products.builder()
//                .productCode(oldProductCode)
//                .productName("Product 1")
//                .productPrice(100.0)
//                .merchants(new Merchants())
//                .imageData(new ImageData())
//                .build();
//
//        Merchants merchant = new Merchants();
//        merchant.setMerchantId("1");
//        ImageData image = new ImageData();
//        image.setIdImage(1L);
//
//        Mockito.when(productsRepository.findByProductCode(oldProductCode)).thenReturn(existingProduct);
//        Mockito.when(merchantsRepository.findMerchantIdByMerchantCode(newMerchantCode)).thenReturn(UUID.fromString(merchant.getMerchantId()));
//        Mockito.when(imageDataRepository.findIdImageByNameWithQuery(imageName)).thenReturn(image.getIdImage());
//        Mockito.when(merchantsRepository.findByMerchantId(merchant.getMerchantId())).thenReturn(merchant);
//        Mockito.when(imageDataRepository.findByIdImage(image.getIdImage())).thenReturn(image);
//
//        Products newProduct = Products.builder()
//                .productCode("P456")
//                .productName("Product 2")
//                .productPrice(200.0)
//                .build();
//
//        Products result = productsService.updateProductByCode(newProduct, oldProductCode, newMerchantCode, imageName);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).findByProductCode(oldProductCode);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).findMerchantIdByMerchantCode(newMerchantCode);
//
//        Mockito.verify(imageDataRepository, Mockito.times(1)).findIdImageByNameWithQuery(imageName);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(newProduct.getProductCode(), result.getProductCode());
//        Assertions.assertEquals(newProduct.getProductName(), result.getProductName());
//        Assertions.assertEquals(newProduct.getProductPrice(), result.getProductPrice());
//        Assertions.assertEquals(merchant, result.getMerchants());
//        Assertions.assertEquals(image, result.getImageData());
//    }
//
//    @Test
//    void updateProductByCodeProduct_TestNotFound() {
//        String oldProductCode = "Non-Existent Code";
//        String newMerchantCode = "M456";
//        String imageName = "image123";
//
//        Mockito.when(productsRepository.findByProductCode(oldProductCode)).thenReturn(null);
//
//        Products result = productsService.updateProductByCode(new Products(), oldProductCode, newMerchantCode, imageName);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).findByProductCode(oldProductCode);
//
//        Assertions.assertNull(result);
//    }
//
//    @Test
//    void deleteProductByCode_TestSuccess() {
//        String productCode = "P123";
//
//        boolean result = productsService.deleteProductByCode(productCode);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).deleteProductByCodeWithQuery(productCode);
//
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void deleteProductByCode_TestFailure() {
//        String productCode = "P123";
//
//        Mockito.doThrow(new RuntimeException("Simulated Error")).when(productsRepository).deleteProductByCodeWithQuery(productCode);
//
//        boolean result = productsService.deleteProductByCode(productCode);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).deleteProductByCodeWithQuery(productCode);
//
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    void getAllProduct_TestSuccess() {
//        List<Products> productsList = new ArrayList<>();
//        Products product1 = new Products();
//        product1.setProductCode("P123");
//        product1.setProductName("Product 1");
//        product1.setProductPrice(100.0);
//        product1.setMerchants(new Merchants());
//        product1.setImageData(new ImageData());
//        product1.getMerchants().setMerchantName("Merchant 1");
//        product1.getImageData().setName("Image 1");
//
//        Products product2 = new Products();
//        product2.setProductCode("P456");
//        product2.setProductName("Product 2");
//        product2.setProductPrice(200.0);
//        product2.setMerchants(new Merchants());
//        product2.setImageData(new ImageData());
//        product2.getMerchants().setMerchantName("Merchant 2");
//        product2.getImageData().setName("Image 2");
//
//        productsList.add(product1);
//        productsList.add(product2);
//
//        Mockito.when(productsRepository.findAll()).thenReturn(productsList);
//
//        List<ProductsResponse> responseList = productsService.getAllProduct();
//
//        Mockito.verify(productsRepository, Mockito.times(1)).findAll();
//
//        Assertions.assertEquals(2, responseList.size());
//
//        ProductsResponse response1 = responseList.get(0);
//        Assertions.assertEquals("P123", response1.getDtoProductCode());
//        Assertions.assertEquals("Product 1", response1.getDtoProductName());
//        Assertions.assertEquals(100.0, response1.getDtoProductPrice());
//        Assertions.assertEquals("Image 1", response1.getDtoImageName());
//        Assertions.assertEquals("Merchant 1", response1.getDtoMerchantName());
//
//        ProductsResponse response2 = responseList.get(1);
//        Assertions.assertEquals("P456", response2.getDtoProductCode());
//        Assertions.assertEquals("Product 2", response2.getDtoProductName());
//        Assertions.assertEquals(200.0, response2.getDtoProductPrice());
//        Assertions.assertEquals("Image 2", response2.getDtoImageName());
//        Assertions.assertEquals("Merchant 2", response2.getDtoMerchantName());
//    }
//
//    @Test
//    void getProductDetailByName_TestSuccess() {
//        String productName = "Product 1";
//
//        Products product = new Products();
//        product.setProductCode("P123");
//        product.setProductName(productName);
//        product.setProductPrice(100.0);
//        product.setMerchants(new Merchants());
//        product.setImageData(new ImageData());
//        product.getMerchants().setMerchantName("Merchant 1");
//        product.getImageData().setName("Image 1");
//
//        Mockito.when(productsRepository.findByProductName(productName)).thenReturn(product);
//
//        ProductsResponse response = productsService.getProductDetailByName(productName);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).findByProductName(productName);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals("P123", response.getDtoProductCode());
//        Assertions.assertEquals(productName, response.getDtoProductName());
//        Assertions.assertEquals(100.0, response.getDtoProductPrice());
//        Assertions.assertEquals("Image 1", response.getDtoImageName());
//        Assertions.assertEquals("Merchant 1", response.getDtoMerchantName());
//    }
//
//    @Test
//    void getProductDetailByName_TestNotFound() {
//        String productName = "Non-Existent Product";
//
//        Mockito.when(productsRepository.findByProductName(productName)).thenReturn(null);
//
//        ProductsResponse response = productsService.getProductDetailByName(productName);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).findByProductName(productName);
//
//        Assertions.assertNull(response);
//    }
//
//    @Test
//    void findByProductCode_TestFound() {
//        String productCode = "P123";
//
//        Products product = Products.builder()
//                .productCode(productCode)
//                .productName("Product 1")
//                .productPrice(100.0)
//                .build();
//
//        Mockito.when(productsRepository.findByProductCode(productCode)).thenReturn(product);
//
//        Optional<Products> result = productsService.findByProductCode(productCode);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).findByProductCode(productCode);
//
//        Assertions.assertTrue(result.isPresent());
//        Assertions.assertEquals(productCode, result.get().getProductCode());
//    }
//
//    @Test
//    void findByProductCode_TestNotFound() {
//        String productCode = "Non-Existent Code";
//
//        Mockito.when(productsRepository.findByProductCode(productCode)).thenReturn(null);
//
//        Optional<Products> result = productsService.findByProductCode(productCode);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).findByProductCode(productCode);
//
//        Assertions.assertFalse(result.isPresent());
//    }
//
//    @Test
//    void getProductByMerchantName_TestSuccess() {
//        String merchantName = "Merchant 1";
//
//        List<Products> productsList = new ArrayList<>();
//        Products product1 = new Products();
//        product1.setProductCode("P123");
//        product1.setProductName("Product 1");
//        product1.setProductPrice(100.0);
//        product1.setMerchants(new Merchants());
//        product1.setImageData(new ImageData());
//        product1.getMerchants().setMerchantName(merchantName);
//        product1.getImageData().setName("Image 1");
//
//        Products product2 = new Products();
//        product2.setProductCode("P456");
//        product2.setProductName("Product 2");
//        product2.setProductPrice(200.0);
//        product2.setMerchants(new Merchants());
//        product2.setImageData(new ImageData());
//        product2.getMerchants().setMerchantName(merchantName);
//        product2.getImageData().setName("Image 2");
//
//        productsList.add(product1);
//        productsList.add(product2);
//
//        Mockito.when(productsRepository.findProductByMerchantNameWithQuery(merchantName)).thenReturn(productsList);
//
//        List<ProductsResponse> responseList = productsService.getProductByMerchantName(merchantName);
//
//        Mockito.verify(productsRepository, Mockito.times(1)).findProductByMerchantNameWithQuery(merchantName);
//
//        Assertions.assertEquals(2, responseList.size());
//
//        ProductsResponse response1 = responseList.get(0);
//        Assertions.assertEquals("P123", response1.getDtoProductCode());
//        Assertions.assertEquals("Product 1", response1.getDtoProductName());
//        Assertions.assertEquals(100.0, response1.getDtoProductPrice());
//        Assertions.assertEquals(merchantName, response1.getDtoMerchantName());
//        Assertions.assertEquals("Image 1", response1.getDtoImageName());
//
//        ProductsResponse response2 = responseList.get(1);
//        Assertions.assertEquals("P456", response2.getDtoProductCode());
//        Assertions.assertEquals("Product 2", response2.getDtoProductName());
//        Assertions.assertEquals(200.0, response2.getDtoProductPrice());
//        Assertions.assertEquals(merchantName, response2.getDtoMerchantName());
//        Assertions.assertEquals("Image 2", response2.getDtoImageName());
//    }

//}
