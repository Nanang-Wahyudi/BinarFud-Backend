package app.BinarFudBackend.service.impl;

import app.BinarFudBackend.config.PaginationUtils;
import app.BinarFudBackend.exception.BadRequestException;
import app.BinarFudBackend.model.ImageData;
import app.BinarFudBackend.model.Merchants;
import app.BinarFudBackend.model.Products;
import app.BinarFudBackend.model.request.ProductsCreateRequest;
import app.BinarFudBackend.model.request.ProductsUpdateRequest;
import app.BinarFudBackend.model.response.ProductsResponse;
import app.BinarFudBackend.model.response.ResultPageResponse;
import app.BinarFudBackend.repository.ProductsRepository;
import app.BinarFudBackend.service.ImageDataService;
import app.BinarFudBackend.service.MerchantsService;
import app.BinarFudBackend.service.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private MerchantsService merchantsService;

    @Autowired
    private ImageDataService imageDataService;


    @Override
    public Products addNewProduct(ProductsCreateRequest request) throws ExecutionException, InterruptedException {
        return CompletableFuture.allOf(imageDataService.findIdImageByName(request.getImageName()
        ), merchantsService.findMerchantIdByMerchantCode(request.getMerchantCode()))
                .thenApplyAsync(__ -> {
                    UUID productId = UUID.randomUUID();
                    UUID merchantId = merchantsService.findMerchantIdByMerchantCode(request.getMerchantCode()).join()
                            .orElseThrow(() -> new BadRequestException("Merchant Code Not Found"));
                    Long imageId = imageDataService.findIdImageByName(request.getImageName()).join()
                            .orElseThrow(() -> new BadRequestException("Image Name Not Found"));

                    if (merchantId != null && imageId != null) {
                        Merchants merchant = merchantsService.findByMerchantId(String.valueOf(merchantId)).join()
                                .orElseThrow(() -> new BadRequestException("Merchant ID Not Found"));
                        ImageData imageData = imageDataService.findByIdImage(imageId).join()
                                .orElseThrow(() -> new BadRequestException("Image ID Not Found"));

                        if (merchant != null && imageData != null) {
                            Products products = Products.builder()
                                    .productId(String.valueOf(productId))
                                    .productCode(request.getProductCode())
                                    .productName(request.getProductName())
                                    .productPrice(request.getProductPrice())
                                    .merchants(merchant)
                                    .imageData(imageData)
                                    .build();
                            return productsRepository.save(products);
                        }
                    }
                    throw new BadRequestException("Invalid Merchant Code or Image Name");
                }).get();
    }

    @Override
    public Products updateProductByCode(String oldProductCode, ProductsUpdateRequest request) throws ExecutionException, InterruptedException {
        return CompletableFuture.allOf(imageDataService.findIdImageByName(request.getImageName()
        ), merchantsService.findMerchantIdByMerchantCode(request.getMerchantCode()))
                .thenApplyAsync(__ -> {
                    Products products = productsRepository.findByProductCode(oldProductCode);
                    if (products == null) {
                        throw new BadRequestException("Product Not Found");
                    }

                    if (request.getProductCode() != null && !request.getProductCode().isEmpty()) {
                        products.setProductCode(request.getProductCode());
                    }
                    if (request.getProductName() != null && !request.getProductName().isEmpty()) {
                        products.setProductName(request.getProductName());
                    }
                    if (request.getProductPrice() != null && request.getProductPrice() != 0.0) {
                        products.setProductPrice(request.getProductPrice());
                    }
                    if (request.getImageName() != null && !request.getImageName().isEmpty()) {
                        Long imageId = imageDataService.findIdImageByName(request.getImageName()).join()
                                .orElseThrow(() -> new BadRequestException("Image Name Not Found"));
                        ImageData imageData = imageDataService.findByIdImage(imageId).join()
                                .orElseThrow(() -> new BadRequestException("Image ID Not Found"));

                        products.setImageData(imageData);
                    }
                    if (request.getMerchantCode() != null && !request.getMerchantCode().isEmpty()) {
                        UUID merchantId = merchantsService.findMerchantIdByMerchantCode(request.getMerchantCode()).join()
                                .orElseThrow(() -> new BadRequestException("Merchant Code Not Found"));

                        Merchants merchant = merchantsService.findByMerchantId(merchantId.toString()).join()
                                .orElseThrow(() -> new BadRequestException("Merchant ID Not Found"));

                        products.setMerchants(merchant);
                    }

                    return productsRepository.save(products);

                }).get();
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteProductByCode(String productCode) {
        return CompletableFuture.supplyAsync(() -> {
            Products products = productsRepository.findByProductCode(productCode);

            if (products != null) {
                productsRepository.delete(products);
                return true;
            } else {
                return false;
            }
        });
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<ResultPageResponse<ProductsResponse>> getAllProductWithPage(Integer pages, Integer limit, String sortBy, String direction, String productName) {
        productName = StringUtils.isBlank(productName) ? "%" : productName + "%";
        Sort sort = Sort.by(new Sort.Order(PaginationUtils.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(pages, limit, sort);
        Page<Products> pageResult = productsRepository.findByProductNameLikeIgnoreCase(productName, pageable);
        List<ProductsResponse> responses = pageResult.stream()
                .map((products -> ProductsResponse.builder()
                        .productCode(products.getProductCode())
                        .productName(products.getProductName())
                        .productPrice(products.getProductPrice())
                        .imageName(products.getImageData().getName())
                        .merchantName(products.getMerchants().getMerchantName())
                        .build())).collect(Collectors.toList());
        ResultPageResponse<ProductsResponse> getAllProduct=  PaginationUtils.createResultPageDTO(responses, pageResult.getTotalElements(), pageResult.getTotalPages(), (pageResult.getPageable().getPageNumber() + 1));
        return CompletableFuture.completedFuture(getAllProduct);
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<ProductsResponse> getProductDetailByName(String productName) {
        return CompletableFuture.supplyAsync(() -> {
            Products products = productsRepository.findByProductName(productName);
            return Optional.ofNullable(products)
                    .map(product -> ProductsResponse.builder()
                            .productCode(product.getProductCode())
                            .productName(product.getProductName())
                            .productPrice(product.getProductPrice())
                            .imageName(product.getImageData().getName())
                            .merchantName(product.getMerchants().getMerchantName())
                            .build())
                    .orElse(null);
        });
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<List<ProductsResponse>> getProductByMerchantName(String merchantName) {
        return CompletableFuture.supplyAsync(() ->
                productsRepository.findProductByMerchantNameWithQuery(merchantName).stream()
                .map(products -> ProductsResponse.builder()
                        .productCode(products.getProductCode())
                        .productName(products.getProductName())
                        .productPrice(products.getProductPrice())
                        .merchantName(products.getMerchants().getMerchantName())
                        .imageName(products.getImageData().getName())
                        .build())
                .collect(Collectors.toList()));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Products> findByProductCode(String productCode) {
        return Optional.ofNullable(productsRepository.findByProductCode(productCode));
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<Optional<UUID>> findProductIdByProductName(String productName) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(productsRepository.findProductIdByProductName(productName)));
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<Optional<Products>> findByProductId(String productId) {
        return CompletableFuture.supplyAsync(() -> Optional.ofNullable(productsRepository.findByProductId(productId)));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Products> findByProductName(String productName) {
        return Optional.ofNullable(productsRepository.findByProductName(productName));
    }

}
