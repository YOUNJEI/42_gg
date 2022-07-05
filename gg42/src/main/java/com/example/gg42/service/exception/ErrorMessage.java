package com.example.gg42.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    TOKEN_EXPIRED   ("TOK001", "토큰 만료");

    private String code;
    private String desc;
}
