package app.BinarFudBackend.service;

import app.BinarFudBackend.model.Orders;
import app.BinarFudBackend.model.request.OrderDetailsCreateRequest;
import app.BinarFudBackend.model.response.OrderDetailsResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface OrderDetailsService {

    Orders createOrder(OrderDetailsCreateRequest request) throws ExecutionException, InterruptedException;

    CompletableFuture<List<OrderDetailsResponse>> getAllOrderDetail();
}
