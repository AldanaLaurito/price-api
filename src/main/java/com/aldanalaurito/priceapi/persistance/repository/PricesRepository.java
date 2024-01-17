package com.aldanalaurito.priceapi.persistance.repository;

import com.aldanalaurito.priceapi.persistance.entities.PriceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PricesRepository extends CrudRepository<PriceEntity, Long> {
    List<PriceEntity> findByProductIdAndBrandId(Long productId, Integer brandId);
}
