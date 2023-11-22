package app.BinarFudBackend.service.impl;

import app.BinarFudBackend.config.PaginationUtils;
import app.BinarFudBackend.exception.BadRequestException;
import app.BinarFudBackend.model.Merchants;
import app.BinarFudBackend.model.enumeration.MerchantStatus;
import app.BinarFudBackend.model.request.MerchantsCreateRequest;
import app.BinarFudBackend.model.request.MerchantsUpdateRequest;
import app.BinarFudBackend.model.response.MerchantsResponse;
import app.BinarFudBackend.model.response.ResultPageResponse;
import app.BinarFudBackend.repository.MerchantsRepository;
import app.BinarFudBackend.service.MerchantsService;
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
import java.util.stream.Collectors;

@Transactional
@Service
public class MerchantsServiceImpl implements MerchantsService {

    @Autowired
    private MerchantsRepository merchantsRepository;

    @Async
    @Override
    public CompletableFuture<Merchants> addNewMerchant(MerchantsCreateRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Merchants merchants = Merchants.builder()
                    .merchantCode(request.getMerchantCode())
                    .merchantName(request.getMerchantName())
                    .merchantLocation(request.getMerchantLocation())
                    .merchantStatus(MerchantStatus.valueOf(request.getMerchantStatus()))
                    .build();

            return merchantsRepository.save(merchants);
        });
    }

    @Async
    @Override
    public CompletableFuture<Boolean> deleteMerchantByCode(String merchantCode) {
        return CompletableFuture.supplyAsync(() -> {
            Merchants merchants = merchantsRepository.findByMerchantCode(merchantCode)
                    .orElseThrow(() -> new BadRequestException("Merchant Code Not Found"));

            if (merchants != null) {
                merchantsRepository.delete(merchants);
                return true;
            } else {
                return false;
            }
        });
    }

    @Async
    @Override
    public CompletableFuture<Merchants> updateMerchantByCode(String merchantCode, MerchantsUpdateRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            Merchants merchants = merchantsRepository.findByMerchantCode(merchantCode)
                    .orElseThrow(() -> new BadRequestException("Invalid Merchant Code."));

            merchants.setMerchantCode(
                    request.getMerchantCode() != null && !request.getMerchantCode().isEmpty()
                            ? request.getMerchantCode()
                            : merchants.getMerchantCode()
            );
            merchants.setMerchantName(
                    request.getMerchantName() != null && !request.getMerchantName().isEmpty()
                            ? request.getMerchantName()
                            : merchants.getMerchantName()
            );
            merchants.setMerchantLocation(
                    request.getMerchantLocation() != null && !request.getMerchantLocation().isEmpty()
                            ? request.getMerchantLocation()
                            : merchants.getMerchantLocation()
            );

            if (request.getMerchantStatus() != null && !request.getMerchantStatus().isEmpty()) {
                try {
                    merchants.setMerchantStatus(MerchantStatus.valueOf(request.getMerchantStatus()));
                } catch (IllegalArgumentException e) {
                    throw new BadRequestException("Invalid Merchant Status.");
                }
            }

            return merchantsRepository.save(merchants);
        });
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<ResultPageResponse<MerchantsResponse>> getAllMerchantWithPage(Integer pages, Integer limit, String sortBy, String direction, String merchantName) {
        pages -= 1;
        merchantName = StringUtils.isBlank(merchantName) ? "%" : merchantName + "%";
        Sort sort = Sort.by(new Sort.Order(PaginationUtils.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(pages, limit, sort);
        Page<Merchants> pageResult = merchantsRepository.findByMerchantNameLikeIgnoreCase(merchantName, pageable);
        List<MerchantsResponse> responses = pageResult.stream()
                .map((merchants -> MerchantsResponse.builder()
                        .merchantCode(merchants.getMerchantCode())
                        .merchantName(merchants.getMerchantName())
                        .merchantLocation(merchants.getMerchantLocation())
                        .merchantStatus(merchants.getMerchantStatus().name())
                        .build())).collect(Collectors.toList());
        ResultPageResponse<MerchantsResponse> getAllMerchant =  PaginationUtils.createResultPageDTO(responses, pageResult.getTotalElements(), pageResult.getTotalPages(), (pageResult.getPageable().getPageNumber() + 1));
        return CompletableFuture.completedFuture(getAllMerchant);
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<MerchantsResponse> getMerchantDetailByName(String merchantName) {
        return CompletableFuture.supplyAsync(() -> {
            Merchants merchants = merchantsRepository.findByMerchantName(merchantName);
            return Optional.ofNullable(merchants)
                    .map(merchant -> MerchantsResponse.builder()
                            .merchantCode(merchant.getMerchantCode())
                            .merchantName(merchant.getMerchantName())
                            .merchantLocation(merchant.getMerchantLocation())
                            .merchantStatus(merchant.getMerchantStatus().toString())
                            .build())
                    .orElse(null);
        });
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Merchants> findByMerchantCode(String merchantCode) {
        return merchantsRepository.findByMerchantCode(merchantCode);
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<Optional<Merchants>> findByMerchantId(String merchantId) {
        return CompletableFuture.supplyAsync(() -> merchantsRepository.findByMerchantId(merchantId));
    }

    @Async
    @Transactional(readOnly = true)
    @Override
    public CompletableFuture<Optional<UUID>> findMerchantIdByMerchantCode(String merchantCode) {
        return CompletableFuture.supplyAsync(() -> merchantsRepository.findMerchantIdByMerchantCode(merchantCode));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Merchants> findByMerchantName(String merchantName) {
        return Optional.ofNullable(merchantsRepository.findByMerchantName(merchantName));
    }

}
