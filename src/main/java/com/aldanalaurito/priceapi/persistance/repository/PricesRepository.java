package com.aldanalaurito.priceapi.persistance.repository;

import com.aldanalaurito.priceapi.persistance.entities.PriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PricesRepository extends CrudRepository<PriceEntity, Long> {

    @Query(value = "SELECT * FROM PRICES WHERE " +
            "PRODUCT_ID = :productId AND " +
            "BRAND_ID = :brandId AND " +
            "START_DATE <= :dateTime AND " +
            "END_DATE >= :dateTime " +
            "ORDER BY PRIORITY DESC LIMIT 1",
            nativeQuery = true)
    PriceEntity findFirstByProductIdAndBrandIdAndDatetime(
            Long productId, Integer brandId, LocalDateTime dateTime);
}
