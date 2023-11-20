package app.BinarFudBackend.controller;

import app.BinarFudBackend.model.ImageData;
import app.BinarFudBackend.model.Merchants;
import app.BinarFudBackend.model.Products;
import app.BinarFudBackend.model.request.ProductsCreateRequest;
import app.BinarFudBackend.model.request.ProductsUpdateRequest;
import app.BinarFudBackend.model.response.ErrorResponse;
import app.BinarFudBackend.model.response.ProductsResponse;
import app.BinarFudBackend.model.response.Response;
import app.BinarFudBackend.model.response.ResultPageResponse;
import app.BinarFudBackend.service.ImageDataService;
import app.BinarFudBackend.service.MerchantsService;
import app.BinarFudBackend.service.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@Slf4j
@RequestMapping(value = "/api/product")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ImageDataService imageDataService;

    @Autowired
    private MerchantsService merchantsService;

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addProduct(@RequestBody @Valid ProductsCreateRequest request) {

        Optional<Merchants> merchants = merchantsService.findByMerchantCode(request.getMerchantCode());
        Optional<ImageData> imageData = imageDataService.findByName(request.getImageName());
        if (!merchants.isPresent() || !imageData.isPresent() ||
            request.getProductCode() == null || request.getProductCode().isEmpty() ||
            request.getProductName() == null || request.getProductName().isEmpty() ||
            request.getProductPrice() == null || request.getProductPrice().equals(0.0) ||
            request.getMerchantCode() == null || request.getMerchantCode().isEmpty() ||
            request.getImageName() == null || request.getImageName().isEmpty()) {

            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Cannot add data because there is empty or invalid data.")
                            .errorCode(HttpStatus.BAD_REQUEST.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.BAD_REQUEST);
        }

        Optional<Products> existingProductCode = productsService.findByProductCode(request.getProductCode());
        Optional<Products> existingProductName = productsService.findByProductName(request.getProductName());
        if (existingProductCode.isPresent() || existingProductName.isPresent()) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Product with Code or Name already exists.")
                            .errorCode(HttpStatus.CONFLICT.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.CONFLICT);
        }

        try {
            Products products = productsService.addNewProduct(request);
            return new ResponseEntity<>(Response.builder()
                    .successMessage("Add New Product Successful.")
                    .data(ProductsResponse.builder()
                            .productCode(products.getProductCode())
                            .productName(products.getProductName())
                            .productPrice(products.getProductPrice())
                            .imageName(products.getImageData().getName())
                            .merchantName(products.getMerchants().getMerchantName())
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

    @PutMapping(value = "/update/{oldProductCode}")
    public ResponseEntity<Response<Object>> updateProduct(@PathVariable("oldProductCode") String oldProductCode,
                                                          @RequestBody ProductsUpdateRequest request) {

        Optional<Products> existingProduct = productsService.findByProductCode(oldProductCode);
        if (!existingProduct.isPresent()) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Product with Code '" + oldProductCode + "' Not Found.")
                            .errorCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.NOT_FOUND);
        }

        Optional<Products> existingProductCode = productsService.findByProductCode(request.getProductCode());
        Optional<Products> existingProductName = productsService.findByProductName(request.getProductName());
        if ((existingProductCode.isPresent() && !existingProductCode.get().getProductCode().equals(oldProductCode)) ||
                (existingProductName.isPresent() && !existingProductName.get().getProductName().equals(existingProduct.get().getProductName()))) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Product with Code or Name already exists.")
                            .errorCode(HttpStatus.CONFLICT.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.CONFLICT);
        }

        try {
            Products products = productsService.updateProductByCode(oldProductCode, request);
            return new ResponseEntity<>(Response.builder()
                    .successMessage("Add New Product Successful.")
                    .data(ProductsResponse.builder()
                            .productCode(products.getProductCode())
                            .productName(products.getProductName())
                            .productPrice(products.getProductPrice())
                            .imageName(products.getImageData().getName())
                            .merchantName(products.getMerchants().getMerchantName())
                            .build())
                    .isSuccess(Boolean.TRUE)
                    .build(), HttpStatus.OK);

        } catch (InterruptedException | ExecutionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Response.builder()
                            .errorResponse(ErrorResponse.builder()
                                    .errorMessage("Error Occurred : " + e.getMessage())
                                    .errorCode(HttpStatus.BAD_REQUEST.value())
                                    .build())
                            .data(null)
                            .isSuccess(Boolean.FALSE)
                            .build());
        }
    }

    @DeleteMapping(value = "/delete/{productCode}")
    public ResponseEntity<Response<Object>> deleteProduct(@PathVariable("productCode") String productCode) {

        Optional<Products> existingProduct = productsService.findByProductCode(productCode);
        if (!existingProduct.isPresent()) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Product With Code '" + productCode + "' Not Found.")
                            .errorCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.NOT_FOUND);
        }

        CompletableFuture<Boolean> productFuture = productsService.deleteProductByCode(productCode);
        return productFuture.thenApply(products -> ResponseEntity.ok(Response.builder()
                        .successMessage("Delete Product With Code '" + productCode + "' Successful.")
                        .data(productFuture.isDone())
                        .isSuccess(Boolean.TRUE)
                        .build()))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Response.builder()
                                .errorResponse(ErrorResponse.builder()
                                        .errorMessage("Failed to delete product : " + ex.getMessage())
                                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .build())
                                .data(null)
                                .isSuccess(Boolean.FALSE)
                                .build())).join();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Object> getAllProductWithPage(
            @RequestParam(name = "pages", defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", defaultValue = "2") Integer limit,
            @RequestParam(name = "sortBy", defaultValue = "productName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "productName", required = false) String productName) {

        CompletableFuture<ResultPageResponse<ProductsResponse>> futureResult =
                productsService.getAllProductWithPage(pages, limit, sortBy, direction, productName);

        try {
            ResultPageResponse<ProductsResponse> getAllProductPage =
                    futureResult.thenApply(result -> result).get(10L, TimeUnit.SECONDS);

            return ResponseEntity.ok().body(getAllProductPage);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
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

    @GetMapping(value = "/detail")
    public ResponseEntity<Response<Object>> getProductDetail(@RequestParam("productName") String productName) {

        CompletableFuture<ProductsResponse> productsResponse = productsService.getProductDetailByName(productName);
        log.debug("Product detail with name {} fetched with detail {}", productName, productsResponse);

        try {
            ProductsResponse product = productsResponse.get(10L, TimeUnit.SECONDS);

            return Optional.ofNullable(product)
                    .map(p -> ResponseEntity.ok().body(Response.builder()
                            .data(ProductsResponse.builder()
                                    .productCode(p.getProductCode())
                                    .productName(p.getProductName())
                                    .productPrice(p.getProductPrice())
                                    .imageName(p.getImageName())
                                    .merchantName(p.getMerchantName())
                                    .build())
                            .successMessage("Product Details with name '" + productName + "' successfully displayed")
                            .isSuccess(Boolean.TRUE)
                            .build()))
                    .orElseGet(() -> new ResponseEntity<>(Response.builder()
                            .errorResponse(ErrorResponse.builder()
                                    .errorMessage("Product with name '" + productName + "' not found")
                                    .errorCode(HttpStatus.NOT_FOUND.value())
                                    .build())
                            .data(null)
                            .isSuccess(Boolean.FALSE)
                            .build(), HttpStatus.NOT_FOUND));

        } catch (InterruptedException | ExecutionException | TimeoutException e) {
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

    @GetMapping(value = "/{merchantName}")
    public CompletableFuture<ResponseEntity<Response<Object>>> getProductByMerchantName(@PathVariable String merchantName) {
        return productsService.getProductByMerchantName(merchantName)
                .thenApply(products -> {
                    if (!products.isEmpty()) {
                        return ResponseEntity.ok()
                                .body(Response.builder()
                                        .data(products)
                                        .successMessage("Succeeded in displaying the product under the name merchant of " + merchantName)
                                        .isSuccess(Boolean.TRUE)
                                        .build());
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(Response.builder()
                                        .errorResponse(ErrorResponse.builder()
                                                .errorMessage("Product not found in merchant name " + merchantName)
                                                .errorCode(HttpStatus.NOT_FOUND.value())
                                                .build())
                                        .isSuccess(Boolean.FALSE)
                                        .build());
                    }
                })
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Response.builder()
                                .errorResponse(ErrorResponse.builder()
                                        .errorMessage("Error occurred : " + ex.getMessage())
                                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .build())
                                .isSuccess(Boolean.FALSE)
                                .build()));
    }

}
