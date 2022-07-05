package com.example.gg42.service.exception;

public class CustomException extends Exception {
    private String code;
    private String message;

    public CustomException(ErrorMessage message) {
        this.code = message.getCode();
        this.message = message.getDesc();
    }
}