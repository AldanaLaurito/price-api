package com.aldanalaurito.priceapi.controller.advice;

import com.aldanalaurito.priceapi.controller.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PricesControllerAdvice  extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorDTO> runtimeException(RuntimeException exception){
        ErrorDTO errorDTO = new ErrorDTO(exception.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.CONFLICT);
    }

}
