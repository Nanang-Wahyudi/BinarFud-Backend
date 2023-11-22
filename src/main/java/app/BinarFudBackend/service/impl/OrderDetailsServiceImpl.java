package app.BinarFudBackend.service.impl;

import app.BinarFudBackend.exception.BadRequestException;
import app.BinarFudBackend.model.OrderDetails;
import app.BinarFudBackend.model.Orders;
import app.BinarFudBackend.model.Products;
import app.BinarFudBackend.model.Users;
import app.BinarFudBackend.model.enumeration.OrderPhase;
import app.BinarFudBackend.model.request.OrderDetailsCreateRequest;
import app.BinarFudBackend.model.response.OrderDetailsResponse;
import app.BinarFudBackend.repository.OrderDetailsRepository;
import app.BinarFudBackend.repository.OrdersRepository;
import app.BinarFudBackend.service.OrderDetailsService;
import app.BinarFudBackend.service.ProductsService;
import app.BinarFudBackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Transactional
@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Override
    public Orders createOrder(OrderDetailsCreateRequest request) throws ExecutionException, InterruptedException {
        return CompletableFuture.allOf(usersService.findUserIdByUsername(request.getUsername()),
                productsService.findProductIdByProductName(request.getProductName()))
                .thenApplyAsync(__ -> {
                    UUID orderId = UUID.randomUUID();
                    UUID orderDetailId = UUID.randomUUID();

                    UUID userId = usersService.findUserIdByUsername(request.getUsername()).join()
                            .orElseThrow(() -> new BadRequestException("Username Not Found"));
                    UUID productId = productsService.findProductIdByProductName(request.getProductName()).join()
                            .orElseThrow(() -> new BadRequestException("Product Name Not Found"));

                    if (userId != null && productId != null) {
                        Users users = usersService.findByUserId(String.valueOf(userId)).join()
                                .orElseThrow(() -> new BadRequestException("User ID Not Found"));
                        Products products = productsService.findByProductId(String.valueOf(productId)).join()
                                .orElseThrow(() -> new BadRequestException("Product ID Not Found"));

                        if (users != null && products != null) {
                            Orders orders = Orders.builder()
                                    .orderId(String.valueOf(orderId))
                                    .orderPhase(OrderPhase.PROCESSED)
                                    .orderTime(LocalDateTime.now())
                                    .destinationAddress(request.getDestinationAddress())
                                    .users(users)
                                    .products(products)
                                    .build();
                            OrderDetails orderDetails = OrderDetails.builder()
                                    .orderDetailId(String.valueOf(orderDetailId))
                                    .quantity(request.getQuantity())
                                    .totalPrice(products.getProductPrice() * request.getQuantity())
                                    .build();
                            orders.addOrderDetail(orderDetails);
                            ordersRepository.save(orders);
                            return orders;
                        }
                    }
                    throw new BadRequestException("Invalid Username or Product Name");
                }).get();
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<List<OrderDetailsResponse>> getAllOrderDetail() {
        return CompletableFuture.supplyAsync(() ->
                orderDetailsRepository.findAll().stream()
                        .map(orderDetails -> OrderDetailsResponse.builder()
                                .username(orderDetails.getOrders().getUsers().getUserName())
                                .productName(orderDetails.getOrders().getProducts().getProductName())
                                .destinationAddress(orderDetails.getOrders().getDestinationAddress())
                                .quantity(orderDetails.getQuantity())
                                .totalPrice(orderDetails.getTotalPrice())
                                .orderTime(orderDetails.getOrders().getOrderTime())
                                .orderPhase(String.valueOf(orderDetails.getOrders().getOrderPhase()))
                                .build())
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<OrderDetailsResponse> getOrderByUsername(String username) {
        return ordersRepository.findOrdersByUsername(username).stream()
                .map(o -> OrderDetailsResponse.builder()
                        .username(o.getUsers().getUserName())
                        .productName(o.getProducts().getProductName())
                        .destinationAddress(o.getDestinationAddress())
                        .quantity(o.getOrderDetails().get(0).getQuantity())
                        .totalPrice(o.getOrderDetails().get(0).getTotalPrice())
                        .orderTime(o.getOrderTime())
                        .orderPhase(String.valueOf(o.getOrderPhase()))
                        .build())
                .collect(Collectors.toList());
    }

}
