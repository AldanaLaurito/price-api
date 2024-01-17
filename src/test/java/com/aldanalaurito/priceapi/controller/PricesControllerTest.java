package com.aldanalaurito.priceapi.controller;

import com.aldanalaurito.priceapi.controller.advice.PricesControllerAdvice;
import com.aldanalaurito.priceapi.dto.ProductPriceResponseDTO;
import com.aldanalaurito.priceapi.service.PriceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PricesControllerTest {

    PriceServiceImpl priceService = Mockito.mock(PriceServiceImpl.class);

    PricesController pricesController = new PricesController(priceService);

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders.standaloneSetup(pricesController).setControllerAdvice(new PricesControllerAdvice()).build();
        when(priceService.obtainProductPriceByDateAndBrand(anyInt(), anyLong(), any())).thenReturn(new ProductPriceResponseDTO());
    }

    @Test
    void endpoint_obtainProductPriceByDateAndBrand_ok() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/prices/obtainPrice")
                                .queryParam("brand", "1")
                                .queryParam("product", "35455")
                                .queryParam("datetime", "2020-06-15T10:00:00"))
                .andExpect(status().isOk()).andReturn().getResponse();
        Assertions.assertNotNull(response);
        Assertions.assertFalse(response.getContentAsString().isEmpty());
    }

    @Test
    void endpoint_obtainProductPriceByDateAndBrand_error_due_to_datetime_format() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                        get("/api/v1/prices/obtainPrice")
                                .queryParam("brand", "1")
                                .queryParam("product", "35455")
                                .queryParam("datetime", "2020-06-15 10:00:00"))
                .andExpect(status().isConflict()).andReturn().getResponse();

        Assertions.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
        Assertions.assertTrue(response.getContentAsString().contains("ERROR_DETAIL"));
        Assertions.assertTrue(response.getContentAsString().contains("could not be parsed"));
    }
}