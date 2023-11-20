package app.BinarFudBackend.repository;

import app.BinarFudBackend.model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Products, String> {

    Products findByProductCode(String productCode);

    Products findByProductName(String productName);

    @Query(nativeQuery = true, value = "SELECT product_id FROM products WHERE product_name = :productName")
    UUID findProductIdByProductName(@Param("productName") String productName);

    Products findByProductId(String productId);

    @Query(nativeQuery = true, value = "select * from products where merchant_id = (select merchant_id from merchants where merchant_name = :name)")
    List<Products> findProductByMerchantNameWithQuery(@Param("name") String merchantName);

    Page<Products> findByProductNameLikeIgnoreCase(String productName, Pageable pageable);

}
