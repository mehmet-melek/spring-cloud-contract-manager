package com.melek.springcloudcontractmanager.contract.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ContractExceptionHandler {

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<String> contractNotFoundExceptionHandler(ContractNotFoundException contractNotFoundException) {
        return new ResponseEntity<>(contractNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JsonMapConvertException.class)
    public ResponseEntity<String> jsonMapConverterExceptionHandler(JsonMapConvertException jsonMapConvertException) {
        return new ResponseEntity<>(jsonMapConvertException.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }


}
