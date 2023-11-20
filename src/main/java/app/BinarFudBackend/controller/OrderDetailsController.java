package app.BinarFudBackend.controller;

import app.BinarFudBackend.model.Orders;
import app.BinarFudBackend.model.Products;
import app.BinarFudBackend.model.Users;
import app.BinarFudBackend.model.request.OrderDetailsCreateRequest;
import app.BinarFudBackend.model.response.ErrorResponse;
import app.BinarFudBackend.model.response.OrderDetailsResponse;
import app.BinarFudBackend.model.response.Response;
import app.BinarFudBackend.service.OrderDetailsService;
import app.BinarFudBackend.service.ProductsService;
import app.BinarFudBackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/order")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ProductsService productsService;

    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<Object> createOrder(@RequestBody @Valid OrderDetailsCreateRequest request) {

        Optional<Users> users = usersService.findByUsername(request.getUsername());
        Optional<Products> products = productsService.findByProductName(request.getProductName());

       if (!users.isPresent() || !products.isPresent() ||
           request.getUsername() == null || request.getUsername().isEmpty() ||
           request.getProductName() == null || request.getProductName().isEmpty() ||
           request.getDestinationAddress() == null || request.getDestinationAddress().isEmpty() ||
           request.getQuantity() == null || request.getQuantity().equals(0)) {

           return new ResponseEntity<>(Response.builder()
                   .errorResponse(ErrorResponse.builder()
                           .errorMessage("Cannot add data because there is empty or invalid data.")
                           .errorCode(HttpStatus.BAD_REQUEST.value())
                           .build())
                   .data(null)
                   .isSuccess(Boolean.FALSE)
                   .build(), HttpStatus.BAD_REQUEST);
       }

        try {
            Orders orders = orderDetailsService.createOrder(request);
            return new ResponseEntity<>(Response.builder()
                    .successMessage("Order Successful.")
                    .data(OrderDetailsResponse.builder()
                            .username(orders.getUsers().getUserName())
                            .productName(orders.getProducts().getProductName())
                            .quantity(orders.getOrderDetails().get(0).getQuantity())
                            .destinationAddress(orders.getDestinationAddress())
                            .orderTime(orders.getOrderTime())
                            .orderPhase(orders.getOrderPhase().name())
                            .totalPrice(orders.getOrderDetails().get(0).getTotalPrice())
                            .build())
                    .isSuccess(Boolean.TRUE)
                    .build(), HttpStatus.OK);

        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.builder()
                            .errorResponse(ErrorResponse.builder()
                                    .errorMessage("Error Occurred : " + e.getMessage())
                                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                    .build())
                            .data(null)
                            .isSuccess(Boolean.FALSE)
                            .build());
        }
    }

    @GetMapping(value = "/orderDetail")
    public ResponseEntity<Object> getAllOrderDetail() throws ExecutionException, InterruptedException {
        CompletableFuture<List<OrderDetailsResponse>> futureOrderDetails = orderDetailsService.getAllOrderDetail();

        List<OrderDetailsResponse> orderDetails = futureOrderDetails.get();

        return ResponseEntity.ok(orderDetails);
    }

}
