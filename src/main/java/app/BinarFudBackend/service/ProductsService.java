package app.BinarFudBackend.service;

import app.BinarFudBackend.model.Products;
import app.BinarFudBackend.model.request.ProductsCreateRequest;
import app.BinarFudBackend.model.request.ProductsUpdateRequest;
import app.BinarFudBackend.model.response.ProductsResponse;
import app.BinarFudBackend.model.response.ResultPageResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ProductsService {

    Products addNewProduct(ProductsCreateRequest request) throws ExecutionException, InterruptedException;

    Products updateProductByCode(String productCode, ProductsUpdateRequest request) throws ExecutionException, InterruptedException;

    CompletableFuture<Boolean> deleteProductByCode(String productCode);

    CompletableFuture<ResultPageResponse<ProductsResponse>> getAllProductWithPage(Integer pages, Integer limit, String sortBy, String direction, String productName);

    CompletableFuture<ProductsResponse> getProductDetailByName(String productName);

    CompletableFuture<List<ProductsResponse>> getProductByMerchantName(String merchantName);

    Optional<Products> findByProductCode(String productCode);

    CompletableFuture<Optional<UUID>> findProductIdByProductName(String productName);

    CompletableFuture<Optional<Products>> findByProductId(String productId);

    Optional<Products> findByProductName(String productName);

}
