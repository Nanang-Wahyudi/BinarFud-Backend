//package binar.challenge7.binarfud_v7.service;
//
//import binar.challenge7.binarfud_v7.model.Merchants;
//import binar.challenge7.binarfud_v7.model.enumeration.MerchantStatus;
//import binar.challenge7.binarfud_v7.model.request.MerchantsCreateRequest;
//import binar.challenge7.binarfud_v7.repository.MerchantsRepository;
//import binar.challenge7.binarfud_v7.service.impl.MerchantsServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class MerchantsServiceTest {
//
//    @InjectMocks
//    private MerchantsServiceImpl merchantsService;
//
//    @Mock
//    private MerchantsRepository merchantsRepository;

//    @Test
//    void addNewMerchant_TestSuccess() {
//        MerchantsCreateRequest createRequest = MerchantsCreateRequest.builder()
//                .merchantCode("M1")
//                .merchantName("Sample Merchant")
//                .merchantLocation("Sample Location")
//                .merchantStatus(MerchantStatus.OPEN.name())
//                .build();
//        Merchants merchants = Merchants.builder()
//                .merchantCode(createRequest.getMerchantCode())
//                .merchantName(createRequest.getMerchantName())
//                .merchantLocation(createRequest.getMerchantLocation())
//                .merchantStatus(MerchantStatus.valueOf(createRequest.getMerchantStatus()))
//                .build();
//
//        Mockito.when(merchantsRepository.save(Mockito.any(Merchants.class))).thenReturn(merchants);
//
//        Merchants savedMerchant = merchantsService.addNewMerchant(createRequest);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).save(Mockito.any(Merchants.class));
//
//        Assertions.assertEquals("M1", savedMerchant.getMerchantCode());
//        Assertions.assertEquals("Sample Merchant", savedMerchant.getMerchantName());
//        Assertions.assertEquals("Sample Location", savedMerchant.getMerchantLocation());
//        Assertions.assertEquals("OPEN", savedMerchant.getMerchantStatus().name());
//    }

//    @Test
//    void deleteMerchantByCode_TestSuccess() {
//        String merchantCode = "MRCHNTTST";
//
//        boolean result = merchantsService.deleteMerchantByCode(merchantCode);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).deleteMerchantByCodeWithQuery(merchantCode);
//
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void deleteMerchantByCode_TestFailure() {
//        String merchantCode = "MRCHNTTST";
//
//        Mockito.doThrow(new RuntimeException("Simulated Error")).when(merchantsRepository).deleteMerchantByCodeWithQuery(merchantCode);
//
//        boolean result = merchantsService.deleteMerchantByCode(merchantCode);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).deleteMerchantByCodeWithQuery(merchantCode);
//
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    void updateMerchantByCode_TestSuccess() {
//        String oldMerchantCode = "MRCHNTTST";
//        Merchants merchants = Merchants.builder()
//                .merchantCode("TST1")
//                .merchantName("Name Test")
//                .merchantLocation("Location Test")
//                .merchantStatus(MerchantStatus.OPEN)
//                .build();
//
//        boolean result = merchantsService.updateMerchantByCode(oldMerchantCode, merchants);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).updateMerchantByCodeWithQuery(oldMerchantCode, merchants.getMerchantCode(), merchants.getMerchantName(), merchants.getMerchantLocation(), String.valueOf(merchants.getMerchantStatus()));
//
//        Assertions.assertTrue(result);
//    }
//
//    @Test
//    void updateMerchantByCode_TestFailure() {
//        String oldMerchantCode = "MRCHNTTST";
//        Merchants merchants = Merchants.builder()
//                .merchantCode("TST1")
//                .merchantName("Name Test")
//                .merchantLocation("Location Test")
//                .merchantStatus(MerchantStatus.OPEN)
//                .build();
//
//        Mockito.doThrow(new RuntimeException("Simulated Error")).when(merchantsRepository).updateMerchantByCodeWithQuery(oldMerchantCode, merchants.getMerchantCode(), merchants.getMerchantName(), merchants.getMerchantLocation(), merchants.getMerchantStatus().toString());
//
//        boolean result = merchantsService.updateMerchantByCode(oldMerchantCode, merchants);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).updateMerchantByCodeWithQuery(oldMerchantCode, merchants.getMerchantCode(), merchants.getMerchantName(), merchants.getMerchantLocation(), String.valueOf(merchants.getMerchantStatus()));
//
//        Assertions.assertFalse(result);
//    }
//
//    @Test
//    void getMerchant_TestSuccess() {
//        List<Merchants> merchantsList = new ArrayList<>();
//        Merchants merchant1 = Merchants.builder()
//                .merchantCode("TST1")
//                .merchantName("Merchant 1")
//                .merchantLocation("Location 1")
//                .merchantStatus(MerchantStatus.OPEN)
//                .build();
//
//        Merchants merchant2 = Merchants.builder()
//                .merchantCode("TST2")
//                .merchantName("Merchant 2")
//                .merchantLocation("Location 2")
//                .merchantStatus(MerchantStatus.CLOSE)
//                .build();
//
//        merchantsList.add(merchant1);
//        merchantsList.add(merchant2);
//
//        Mockito.when(merchantsRepository.findAll()).thenReturn(merchantsList);
//
//        List<MerchantsResponse> responseList = merchantsService.getMerchant();
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).findAll();
//
//        Assertions.assertEquals(2, responseList.size());
//
//        MerchantsResponse response1 = responseList.get(0);
//        Assertions.assertEquals("TST1", response1.getDtoMerchantCode());
//        Assertions.assertEquals("Merchant 1", response1.getDtoMerchantName());
//        Assertions.assertEquals("Location 1", response1.getDtoMerchantLocation());
//        Assertions.assertEquals("OPEN", response1.getDtoMerchantStatus());
//
//        MerchantsResponse response2 = responseList.get(1);
//        Assertions.assertEquals("TST2", response2.getDtoMerchantCode());
//        Assertions.assertEquals("Merchant 2", response2.getDtoMerchantName());
//        Assertions.assertEquals("Location 2", response2.getDtoMerchantLocation());
//        Assertions.assertEquals("CLOSE", response2.getDtoMerchantStatus());
//    }
//
//    @Test
//    void getMerchantDetailByName_TestFound() {
//        String merchantName = "Merchant 1";
//
//        Merchants merchant = Merchants.builder()
//                .merchantCode("TST1")
//                .merchantName(merchantName)
//                .merchantLocation("Location 1")
//                .merchantStatus(MerchantStatus.OPEN)
//                .build();
//
//        Mockito.when(merchantsRepository.findByMerchantName(merchantName)).thenReturn(merchant);
//
//        MerchantsResponse response = merchantsService.getMerchantDetailByName(merchantName);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).findByMerchantName(merchantName);
//
//        Assertions.assertNotNull(response);
//        Assertions.assertEquals("TST1", response.getDtoMerchantCode());
//        Assertions.assertEquals(merchantName, response.getDtoMerchantName());
//        Assertions.assertEquals("Location 1", response.getDtoMerchantLocation());
//        Assertions.assertEquals("OPEN", response.getDtoMerchantStatus());
//    }
//
//    @Test
//    void getMerchantDetailByName_TestNotFound() {
//        String merchantName = "Non-Existent Merchant";
//
//        Mockito.when(merchantsRepository.findByMerchantName(merchantName)).thenReturn(null);
//
//        MerchantsResponse response = merchantsService.getMerchantDetailByName(merchantName);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).findByMerchantName(merchantName);
//
//        Assertions.assertNull(response);
//    }
//
//    @Test
//    void findByMerchantCode_TestFound() {
//        String merchantCode = "TST1";
//
//        Merchants merchant = Merchants.builder()
//                .merchantCode(merchantCode)
//                .merchantName("Merchant 1")
//                .merchantLocation("Location 1")
//                .merchantStatus(MerchantStatus.OPEN)
//                .build();
//
//        Mockito.when(merchantsRepository.findByMerchantCode(merchantCode)).thenReturn(Optional.ofNullable(merchant));
//
//        Optional<Merchants> result = merchantsService.findByMerchantCode(merchantCode);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).findByMerchantCode(merchantCode);
//
//        Assertions.assertTrue(result.isPresent());
//        Assertions.assertEquals(merchantCode, result.get().getMerchantCode());
//    }
//
//    @Test
//    void findByMerchantCode_TestNotFound() {
//        String merchantCode = "Non-Existent Code";
//
//        Mockito.when(merchantsRepository.findByMerchantCode(merchantCode)).thenReturn(null);
//
//        Optional<Merchants> result = merchantsService.findByMerchantCode(merchantCode);
//
//        Mockito.verify(merchantsRepository, Mockito.times(1)).findByMerchantCode(merchantCode);
//
//        Assertions.assertFalse(result.isPresent());
//    }

//}
