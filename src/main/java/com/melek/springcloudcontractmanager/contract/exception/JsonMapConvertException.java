package com.melek.springcloudcontractmanager.contract.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JsonMapConvertException extends RuntimeException {
     public JsonMapConvertException(String message, JsonProcessingException e) {
        super(message + e);

    }
}
