package com.aldanalaurito.priceapi.service;

import com.aldanalaurito.priceapi.dto.ProductPriceResponseDTO;
import com.aldanalaurito.priceapi.helper.Constants;
import com.aldanalaurito.priceapi.persistance.entities.PriceEntity;
import com.aldanalaurito.priceapi.persistance.repository.PricesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PricesRepository repository;

    @InjectMocks
    PriceServiceImpl service;

    static List<PriceEntity> priceEntityList;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);

    @BeforeAll
    static void beforeAll(){
        PriceEntity price1 = PriceEntity.builder().brandId(1).productId(35455L).priceList(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00", formatter))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59", formatter))
                .priority(0).price(new BigDecimal("35.50")).curr("EUR").build();

        PriceEntity price2 = PriceEntity.builder().brandId(1).productId(35455L).priceList(2)
                .startDate(LocalDateTime.parse("2020-06-14T15:00:00", formatter))
                .endDate(LocalDateTime.parse("2020-06-14T18:30:00", formatter))
                .priority(1).price(new BigDecimal("25.45")).curr("EUR").build();

        PriceEntity price3 = PriceEntity.builder().brandId(1).productId(35455L).priceList(3)
                .startDate(LocalDateTime.parse("2020-06-15T00:00:00", formatter))
                .endDate(LocalDateTime.parse("2020-06-15T11:00:00", formatter))
                .priority(1).price(new BigDecimal("30.50")).curr("EUR").build();

        PriceEntity price4 = PriceEntity.builder().brandId(1).productId(35455L).priceList(4)
                .startDate(LocalDateTime.parse("2020-06-15T16:00:00", formatter))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59", formatter))
                .priority(1).price(new BigDecimal("38.95")).curr("EUR").build();

        priceEntityList = List.of(price2, price3, price4, price1);
    }


    @Test
    void obtainPriceList_ok_test_case1(){
        LocalDateTime date = LocalDateTime.parse("2020-06-14T10:00:00", formatter);

        when(repository.findByProductIdAndBrandIdOrderByPriorityDesc(35455L, 1)).thenReturn(priceEntityList);

        ProductPriceResponseDTO priceToApply = Assertions.assertDoesNotThrow(() -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertNotNull(priceToApply);
        Assertions.assertEquals(1, priceToApply.getPriceList());
    }

    @Test
    void obtainPriceList_ok_test_case2(){
        LocalDateTime date = LocalDateTime.parse("2020-06-14T16:00:00", formatter);

        when(repository.findByProductIdAndBrandIdOrderByPriorityDesc(35455L, 1)).thenReturn(priceEntityList);

        ProductPriceResponseDTO priceToApply = Assertions.assertDoesNotThrow(() -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertNotNull(priceToApply);
        Assertions.assertEquals(2, priceToApply.getPriceList());
    }

    @Test
    void obtainPriceList_ok_test_case3(){
        LocalDateTime date = LocalDateTime.parse("2020-06-14T21:00:00", formatter);

        when(repository.findByProductIdAndBrandIdOrderByPriorityDesc(35455L, 1)).thenReturn(priceEntityList);

        ProductPriceResponseDTO priceToApply = Assertions.assertDoesNotThrow(() -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertNotNull(priceToApply);
        Assertions.assertEquals(1, priceToApply.getPriceList());
    }

    @Test
    void obtainPriceList_ok_test_case4(){
        LocalDateTime date = LocalDateTime.parse("2020-06-15T10:00:00", formatter);

        when(repository.findByProductIdAndBrandIdOrderByPriorityDesc(35455L, 1)).thenReturn(priceEntityList);

        ProductPriceResponseDTO priceToApply = Assertions.assertDoesNotThrow(() -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertNotNull(priceToApply);
        Assertions.assertEquals(3, priceToApply.getPriceList());
    }

    @Test
    void obtainPriceList_ok_test_case5(){
        LocalDateTime date = LocalDateTime.parse("2020-06-16T21:00:00", formatter);

        when(repository.findByProductIdAndBrandIdOrderByPriorityDesc(35455L, 1)).thenReturn(priceEntityList);

        ProductPriceResponseDTO priceToApply = Assertions.assertDoesNotThrow(() -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertNotNull(priceToApply);
        Assertions.assertEquals(4, priceToApply.getPriceList());
    }

    @Test
    void obtainPriceList_error_no_element_found_due_to_date(){
        LocalDateTime date = LocalDateTime.parse("2021-06-16T21:00:00", formatter);

        when(repository.findByProductIdAndBrandIdOrderByPriorityDesc(35455L, 1)).thenReturn(priceEntityList);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> service.obtainProductPriceByDateAndBrand(1,35455L, date));
        Assertions.assertTrue(exception.getMessage().contains("No price list was found"));
    }

    @Test
    void obtainPriceList_error_no_element_found_due_to_brandId(){
        LocalDateTime date = LocalDateTime.parse("2020-06-15T10:00:00", formatter);

        when(repository.findByProductIdAndBrandIdOrderByPriorityDesc(35455L, 2)).thenReturn(List.of());

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> service.obtainProductPriceByDateAndBrand(2,35455L, date));
        Assertions.assertTrue(exception.getMessage().contains("No price list was found"));
    }
}