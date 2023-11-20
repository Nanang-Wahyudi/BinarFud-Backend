package app.BinarFudBackend.service;

import app.BinarFudBackend.model.Merchants;
import app.BinarFudBackend.model.request.MerchantsCreateRequest;
import app.BinarFudBackend.model.request.MerchantsUpdateRequest;
import app.BinarFudBackend.model.response.MerchantsResponse;
import app.BinarFudBackend.model.response.ResultPageResponse;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MerchantsService {

    CompletableFuture<Merchants> addNewMerchant(MerchantsCreateRequest request);

    CompletableFuture<Boolean> deleteMerchantByCode(String merchantCode);

    CompletableFuture<Merchants> updateMerchantByCode(String merchantCode, MerchantsUpdateRequest request);

    CompletableFuture<ResultPageResponse<MerchantsResponse>> getAllMerchantWithPage(Integer pages, Integer limit, String sortBy, String direction, String merchantName);

    CompletableFuture<MerchantsResponse> getMerchantDetailByName(String merchantName);

    Optional<Merchants> findByMerchantCode(String merchantCode);

    CompletableFuture<Optional<Merchants>> findByMerchantId(String merchantId);

    CompletableFuture<Optional <UUID>> findMerchantIdByMerchantCode(String merchantCode);

    Optional<Merchants> findByMerchantName(String merchantName);

}
