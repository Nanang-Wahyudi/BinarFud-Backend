//package binar.challenge7.binarfud_v7.controller;
//
//import binar.challenge7.binarfud_v7.model.Merchants;
//import binar.challenge7.binarfud_v7.model.enumeration.MerchantStatus;
//import binar.challenge7.binarfud_v7.model.response.MerchantsResponse;
//import binar.challenge7.binarfud_v7.service.impl.MerchantsServiceImpl;
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
//import java.util.List;
//import java.util.Optional;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class MerchantsControllerTest {
//
//    @InjectMocks
//    private MerchantsController merchantsController;
//
//    @Mock
//    private MerchantsServiceImpl merchantsService;


//    @Test
//    void addMerchant_TestSuccess() {
//        Merchants merchant = Merchants.builder()
//                .merchantCode("SampleCode")
//                .merchantName("SampleMerchant")
//                .merchantLocation("SampleLocation")
//                .merchantStatus(MerchantStatus.OPEN)
//                .build();
//
//        Mockito.when(merchantsService.addNewMerchant(Mockito.any(Merchants.class))).thenReturn(merchant);
//
//        ResponseEntity<Object> response = merchantsController.addMerchant(merchant);
//
//        Mockito.verify(merchantsService).addNewMerchant(merchant);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void deleteMerchant_TestSuccess() {
//        String merchantCode = "SampleMerchantCode";
//
//        Merchants merchant = Merchants.builder()
//                .merchantCode(merchantCode)
//                .merchantName("SampleMerchant")
//                .merchantLocation("SampleLocation")
//                .merchantStatus(MerchantStatus.OPEN)
//                .build();
//
//        Mockito.when(merchantsService.findByMerchantCode(merchantCode)).thenReturn(Optional.of(merchant));
//        Mockito.when(merchantsService.deleteMerchantByCode(merchantCode)).thenReturn(true);
//
//        ResponseEntity<Object> response = merchantsController.deleteMerchant(merchantCode);
//
//        Mockito.verify(merchantsService).findByMerchantCode(merchantCode);
//        Mockito.verify(merchantsService).deleteMerchantByCode(merchantCode);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void updateMerchant_TestSuccess() {
//        String newMerchantCode = "SampleNewMerchantCode";
//        String oldMerchantCode = "SampleOldMerchantCode";
//
//        Merchants updatedMerchant = Merchants.builder()
//                .merchantCode(newMerchantCode)
//                .merchantName("SampleMerchant")
//                .merchantLocation("SampleLocation")
//                .merchantStatus(MerchantStatus.OPEN)
//                .build();
//
//        Mockito.when(merchantsService.updateMerchantByCode(oldMerchantCode, updatedMerchant)).thenReturn(true);
//
//        ResponseEntity<Object> response = merchantsController.updateMerchant(oldMerchantCode, updatedMerchant);
//
//        Mockito.verify(merchantsService).updateMerchantByCode(oldMerchantCode, updatedMerchant);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getMerchant_TestSuccess() {
//        List<MerchantsResponse> responseList = new ArrayList<>();
//
//        MerchantsResponse merchantsResponse1 = MerchantsResponse.builder()
//                .dtoMerchantCode("SampeCode1")
//                .dtoMerchantName("SampleName1")
//                .dtoMerchantLocation("SampleLocation1")
//                .dtoMerchantStatus(MerchantStatus.OPEN.name())
//                .build();
//
//        MerchantsResponse merchantsResponse2 = MerchantsResponse.builder()
//                .dtoMerchantCode("SampeCode2")
//                .dtoMerchantName("SampleName2")
//                .dtoMerchantLocation("SampleLocation2")
//                .dtoMerchantStatus(MerchantStatus.CLOSE.name())
//                .build();
//
//        responseList.add(merchantsResponse1);
//        responseList.add(merchantsResponse2);
//
//        Mockito.when(merchantsService.getMerchant()).thenReturn(responseList);
//
//        ResponseEntity<Object> response = merchantsController.getMerchant();
//
//        Mockito.verify(merchantsService).getMerchant();
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//    }
//
//    @Test
//    void getMerchantDetail_TestSuccess() {
//        String merchantName = "SampleName";
//
//        MerchantsResponse merchantsResponse = MerchantsResponse.builder()
//                .dtoMerchantCode("SampeCode")
//                .dtoMerchantName(merchantName)
//                .dtoMerchantLocation("SampleLocation")
//                .dtoMerchantStatus(MerchantStatus.OPEN.name())
//                .build();
//
//        Mockito.when(merchantsService.getMerchantDetailByName(merchantName)).thenReturn(merchantsResponse);
//
//        ResponseEntity<Object> response = merchantsController.getMerchantDetail(merchantName);
//
//        Mockito.verify(merchantsService).getMerchantDetailByName(merchantName);
//
//        Assertions.assertNotNull(response);
//
//        if (merchantsResponse != null) {
//            Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
//        } else {
//            Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        }
//    }

//}
