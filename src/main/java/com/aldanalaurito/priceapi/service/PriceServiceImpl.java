package com.aldanalaurito.priceapi.service;

import com.aldanalaurito.priceapi.controller.dto.ProductPriceResponseDTO;
import com.aldanalaurito.priceapi.exceptions.PriceNotFoundException;
import com.aldanalaurito.priceapi.persistance.entities.PriceEntity;
import com.aldanalaurito.priceapi.persistance.repository.PricesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceServiceImpl {

    @Autowired
    private PricesRepository pricesRepository;

    public ProductPriceResponseDTO obtainProductPriceByDateAndBrand(int brandId, long productId, LocalDateTime dateApplication){

        PriceEntity priceEntity = pricesRepository.findFirstByProductIdAndBrandIdAndDatetime(productId, brandId, dateApplication);

        if(priceEntity == null || priceEntity.getPriceList() == null) throw new PriceNotFoundException("No price list was found for the brand, product or date given.");

        return new ObjectMapper().convertValue(priceEntity, ProductPriceResponseDTO.class);
    }
}
