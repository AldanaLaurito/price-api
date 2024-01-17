package com.aldanalaurito.priceapi.service;

import com.aldanalaurito.priceapi.controller.dto.ProductPriceResponseDTO;
import com.aldanalaurito.priceapi.persistance.entities.PriceEntity;
import com.aldanalaurito.priceapi.persistance.repository.PricesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceServiceImpl {

    @Autowired
    private PricesRepository pricesRepository;

    public ProductPriceResponseDTO obtainProductPriceByDateAndBrand(int brandId, long productId, LocalDateTime dateApplication){

        RuntimeException exceptionNoPriceListFound = new RuntimeException("No price list was found for the brand, product or date given.");

        List<PriceEntity> priceEntityList = pricesRepository.findByProductIdAndBrandIdOrderByPriorityDesc(productId, brandId);

        PriceEntity priceEntity = priceEntityList.stream()
                .filter(pe -> dateIsInsideRange(dateApplication, pe))
                .findFirst()
                .orElseThrow(() -> exceptionNoPriceListFound);

        return new ObjectMapper().convertValue(priceEntity, ProductPriceResponseDTO.class);
    }

    private boolean dateIsInsideRange(LocalDateTime date, PriceEntity priceEntity){
        return (date.isAfter(priceEntity.getStartDate()) && date.isBefore(priceEntity.getEndDate()));
    }
}
