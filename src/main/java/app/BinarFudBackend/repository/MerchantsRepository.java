package app.BinarFudBackend.repository;

import app.BinarFudBackend.model.Merchants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface MerchantsRepository extends JpaRepository<Merchants, String> {

    Merchants findByMerchantName(String merchantName);

    Optional<Merchants> findByMerchantId(String merchantId);

    Optional<Merchants> findByMerchantCode(String merchantCode);

    @Query(nativeQuery = true, value = "SELECT merchant_id FROM merchants WHERE merchant_code = :merchantCode")
    Optional<UUID> findMerchantIdByMerchantCode(@Param("merchantCode") String merchantCode);

    Page<Merchants> findByMerchantNameLikeIgnoreCase(String merchantName, Pageable pageable);

}
