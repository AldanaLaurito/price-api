package com.aldanalaurito.priceapi.service;

import com.aldanalaurito.priceapi.controller.dto.ProductPriceResponseDTO;
import com.aldanalaurito.priceapi.helper.Constants;
import com.aldanalaurito.priceapi.persistance.entities.PriceEntity;
import com.aldanalaurito.priceapi.persistance.repository.PricesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PricesRepository repository;

    @InjectMocks
    PriceServiceImpl service;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);


    @Test
    void obtainPriceList_ok(){
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00", formatter);

        PriceEntity price = PriceEntity.builder().brandId(1).productId(35455L).priceList(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00", formatter))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59", formatter))
                .priority(0).price(new BigDecimal("35.50")).curr("EUR").build();

        when(repository.findFirstByProductIdAndBrandIdAndDatetime(anyLong(), anyInt(), any())).thenReturn(price);

        ProductPriceResponseDTO priceToApply = Assertions.assertDoesNotThrow(() -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertNotNull(priceToApply);
        Assertions.assertEquals(price.getPriceList(), priceToApply.getPriceList());
        Assertions.assertEquals(price.getPrice(), priceToApply.getPrice());
    }

    @Test
    void obtainPriceList_error_no_element_found_database_returns_empty_element(){
        LocalDateTime date = LocalDateTime.parse("2021-06-16T21:00:00", formatter);

        when(repository.findFirstByProductIdAndBrandIdAndDatetime(anyLong(), anyInt(), any())).thenReturn(new PriceEntity());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertTrue(exception.getMessage().contains("No price list was found"));
    }

    @Test
    void obtainPriceList_error_no_element_found_database_returns_null(){
        LocalDateTime date = LocalDateTime.parse("2020-06-15T10:00:00", formatter);

        when(repository.findFirstByProductIdAndBrandIdAndDatetime(anyLong(), anyInt(), any())).thenReturn(null);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertTrue(exception.getMessage().contains("No price list was found"));
    }
}