//package binar.challenge7.binarfud_v7.controller;
//
//import binar.challenge7.binarfud_v7.model.OrderDetails;
//import binar.challenge7.binarfud_v7.model.Orders;
//import binar.challenge7.binarfud_v7.model.enumeration.OrderPhase;
//import binar.challenge7.binarfud_v7.model.response.OrderDetailsResponse;
//import binar.challenge7.binarfud_v7.service.impl.OrderDetailsServiceImpl;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@AutoConfigureMockMvc
//@SpringBootTest
//public class OrderDetailsControllerTest {
//
//    @InjectMocks
//    private OrderDetailsController orderDetailsController;
//
//    @Mock
//    private OrderDetailsServiceImpl orderDetailsService;


//    @Test
//    void createOrder_TestSuccess() {
//        String productName = "SampleProduct";
//        String userName = "SampleUser";
//        String destinationAddress = "SampleAddress";
//        String orderPhase = "SamplePhase";
//
//        OrderDetails orderDetails = OrderDetails.builder()
//                .quantity(2)
//                .totalPrice(100.0)
//                .orders(new Orders())
//                .build();
//
//        ResponseEntity<Object> response = orderDetailsController.createOrder(orderDetails, productName, userName, destinationAddress, orderPhase);
//
//        Mockito.verify(orderDetailsService).createOrder(orderDetails, productName, userName, destinationAddress, orderPhase);
//
//        Assertions.assertEquals("Order Successful", response.getBody());
//    }
//
//    @Test
//    void getAllOrderDetail_TestSuccess() {
//        List<OrderDetailsResponse> orderDetailsResponseList = new ArrayList<>();
//
//        OrderDetailsResponse orderDetailsResponse1 = OrderDetailsResponse.builder()
//                .dtoUsername("SampleUsername1")
//                .dtoProductName("SampleProductName1")
//                .dtoQuantity(1)
//                .dtoTotalPrice(100.0)
//                .dtoDestinationAddress("SampleDestinationAddress1")
//                .dtoOrderTime(LocalDateTime.now())
//                .dtoOrderPhase(OrderPhase.PROCESSED.name())
//                .build();
//
//        OrderDetailsResponse orderDetailsResponse2 = OrderDetailsResponse.builder()
//                .dtoUsername("SampleUsername2")
//                .dtoProductName("SampleProductName2")
//                .dtoQuantity(2)
//                .dtoTotalPrice(200.0)
//                .dtoDestinationAddress("SampleDestinationAddress2")
//                .dtoOrderTime(LocalDateTime.now())
//                .dtoOrderPhase(OrderPhase.SENT.name())
//                .build();
//
//        orderDetailsResponseList.add(orderDetailsResponse1);
//        orderDetailsResponseList.add(orderDetailsResponse2);
//
//        Mockito.when(orderDetailsService.getAllOrderDetail()).thenReturn(orderDetailsResponseList);
//
//        ResponseEntity<Object> response = orderDetailsController.getAllOrderDetail();
//
//        Mockito.verify(orderDetailsService).getAllOrderDetail();
//
//        Assertions.assertEquals(orderDetailsResponseList, response.getBody());
//    }

//}
