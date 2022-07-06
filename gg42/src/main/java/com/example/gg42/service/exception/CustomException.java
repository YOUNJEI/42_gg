package com.example.gg42.service.exception;

import lombok.Getter;

@Getter
public class CustomException extends Exception {
    private String code;
    private String message;

    public CustomException(ErrorMessage message) {
        this.code = message.getCode();
        this.message = message.getDesc();
    }
}