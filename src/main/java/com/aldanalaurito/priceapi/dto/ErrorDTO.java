package com.aldanalaurito.priceapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "error-dto")
public class ErrorDTO {
    @JsonProperty("ERROR_DETAIL")
    @Schema(name = "ERROR_DETAIL")
    String errorDetail;
}