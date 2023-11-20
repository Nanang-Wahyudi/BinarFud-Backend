package app.BinarFudBackend.controller;

import app.BinarFudBackend.model.Merchants;
import app.BinarFudBackend.model.request.MerchantsCreateRequest;
import app.BinarFudBackend.model.request.MerchantsUpdateRequest;
import app.BinarFudBackend.model.response.ErrorResponse;
import app.BinarFudBackend.model.response.MerchantsResponse;
import app.BinarFudBackend.model.response.Response;
import app.BinarFudBackend.model.response.ResultPageResponse;
import app.BinarFudBackend.service.MerchantsService;
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
@RequestMapping(value = "/api/merchant")
public class MerchantsController {

    @Autowired
    private MerchantsService merchantsService;


    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Response<Object>> addMerchant(@RequestBody @Valid MerchantsCreateRequest request) {

        Optional<Merchants> existingMerchantCode = merchantsService.findByMerchantCode(request.getMerchantCode());
        Optional<Merchants> existingMerchantName = merchantsService.findByMerchantName(request.getMerchantName());
        if (existingMerchantCode.isPresent() || existingMerchantName.isPresent()) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Merchant with Code or Name already exists.")
                            .errorCode(HttpStatus.CONFLICT.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.CONFLICT);
        }

        if (request.getMerchantCode() == null || request.getMerchantCode().isEmpty() ||
                request.getMerchantName() == null || request.getMerchantName().isEmpty() ||
                request.getMerchantLocation() == null || request.getMerchantLocation().isEmpty() ||
                request.getMerchantStatus() == null || request.getMerchantStatus().isEmpty()) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Cannot add data because there is empty or invalid data.")
                            .errorCode(HttpStatus.BAD_REQUEST.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.BAD_REQUEST);
        }

        CompletableFuture<Merchants> merchantsFuture = merchantsService.addNewMerchant(request);
        return merchantsFuture.thenApply(merchants -> ResponseEntity.ok(Response.builder()
                        .successMessage("Add New Merchant Successful.")
                        .data(MerchantsResponse.builder()
                                .merchantCode(merchants.getMerchantCode())
                                .merchantName(merchants.getMerchantName())
                                .merchantLocation(merchants.getMerchantLocation())
                                .merchantStatus(merchants.getMerchantStatus().name())
                                .build())
                        .isSuccess(Boolean.TRUE)
                        .build()))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Response.builder()
                                .errorResponse(ErrorResponse.builder()
                                        .errorMessage("Failed to add a new merchant : " + ex.getMessage())
                                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .build())
                                .data(null)
                                .isSuccess(Boolean.FALSE)
                                .build())).join();
    }

    @DeleteMapping(value = "/delete/{merchantCode}")
    public ResponseEntity<Response<Object>> deleteMerchant(@PathVariable("merchantCode") String merchantCode) {

        Optional<Merchants> existingMerchant = merchantsService.findByMerchantCode(merchantCode);
        if (!existingMerchant.isPresent()) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Merchant With Code '" + merchantCode + "' Not Found.")
                            .errorCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.NOT_FOUND);
        }

        CompletableFuture<Boolean> merchantsFuture = merchantsService.deleteMerchantByCode(merchantCode);
        return merchantsFuture.thenApply(merchants -> ResponseEntity.ok(Response.builder()
                        .successMessage("Delete Merchant With Code '" + merchantCode + "' Successful.")
                        .data(merchantsFuture.isDone())
                        .isSuccess(Boolean.TRUE)
                        .build()))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Response.builder()
                                .errorResponse(ErrorResponse.builder()
                                        .errorMessage("Failed to delete merchant : " + ex.getMessage())
                                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .build())
                                .data(null)
                                .isSuccess(Boolean.FALSE)
                                .build())).join();
    }

    @PutMapping(value = "/update/{merchantCode}")
    public ResponseEntity<Response<Object>> updateMerchant(@PathVariable("merchantCode") String oldmerchantCode,
                                                           @RequestBody MerchantsUpdateRequest request) {

        Optional<Merchants> existingCode = merchantsService.findByMerchantCode(oldmerchantCode);
        if (!existingCode.isPresent()) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Merchant With Code '" + oldmerchantCode + "' Not Found")
                            .errorCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.NOT_FOUND);
        }

        Optional<Merchants> existingMerchantCode = merchantsService.findByMerchantCode(request.getMerchantCode());
        Optional<Merchants> existingMerchantName = merchantsService.findByMerchantName(request.getMerchantName());
        if ((existingMerchantCode.isPresent() && !existingMerchantCode.get().getMerchantCode().equals(oldmerchantCode)) ||
                (existingMerchantName.isPresent() && !existingMerchantName.get().getMerchantName().equals(existingCode.get().getMerchantName()))) {
            return new ResponseEntity<>(Response.builder()
                    .errorResponse(ErrorResponse.builder()
                            .errorMessage("Merchant with Code or Name already exists.")
                            .errorCode(HttpStatus.CONFLICT.value())
                            .build())
                    .data(null)
                    .isSuccess(Boolean.FALSE)
                    .build(), HttpStatus.CONFLICT);
        }

        CompletableFuture<Merchants> merchantsFuture = merchantsService.updateMerchantByCode(oldmerchantCode, request);
        return merchantsFuture.thenApply(merchants -> ResponseEntity.ok(Response.builder()
                        .successMessage("Update Merchant Successful.")
                        .data(MerchantsResponse.builder()
                                .merchantCode(merchants.getMerchantCode())
                                .merchantName(merchants.getMerchantName())
                                .merchantLocation(merchants.getMerchantLocation())
                                .merchantStatus(merchants.getMerchantStatus().name())
                                .build())
                        .isSuccess(Boolean.TRUE)
                        .build()))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Response.builder()
                                .errorResponse(ErrorResponse.builder()
                                        .errorMessage("Failed to update merchant : " + ex.getMessage())
                                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                        .build())
                                .data(null)
                                .isSuccess(Boolean.FALSE)
                                .build())).join();
    }

    @GetMapping()
    public ResponseEntity<Object> getAllMerchantWithPage(
            @RequestParam(name = "pages", defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", defaultValue = "2") Integer limit,
            @RequestParam(name = "sortBy", defaultValue = "merchantName") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "merchantName", required = false) String merchantName) {

        CompletableFuture<ResultPageResponse<MerchantsResponse>> futureResult =
                merchantsService.getAllMerchantWithPage(pages, limit, sortBy, direction, merchantName);

        try {
            ResultPageResponse<MerchantsResponse> getAllMerchantPage =
                    futureResult.thenApply(result -> result).get(10L, TimeUnit.SECONDS);

            return ResponseEntity.ok().body(getAllMerchantPage);
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
    public ResponseEntity<Response<Object>> getMerchantDetail(@RequestParam("merchantName") String merchantName) {

        CompletableFuture<MerchantsResponse> merchantResponse = merchantsService.getMerchantDetailByName(merchantName);
        log.debug("Merchant detail with name {} fetched with detail {}", merchantName, merchantResponse);

        try {
            MerchantsResponse merchant = merchantResponse.get(10L, TimeUnit.SECONDS);

            return Optional.ofNullable(merchant)
                    .map(m -> ResponseEntity.ok().body(Response.builder()
                            .data(MerchantsResponse.builder()
                                    .merchantCode(m.getMerchantCode())
                                    .merchantName(m.getMerchantName())
                                    .merchantLocation(m.getMerchantLocation())
                                    .merchantStatus(m.getMerchantStatus())
                                    .build())
                            .successMessage("Merchant Details with name '" + merchantName + "' successfully displayed")
                            .isSuccess(Boolean.TRUE)
                            .build()))
                    .orElseGet(() -> new ResponseEntity<>(Response.builder()
                            .errorResponse(ErrorResponse.builder()
                                    .errorMessage("Merchant with name '" + merchantName + "' not found")
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

}
