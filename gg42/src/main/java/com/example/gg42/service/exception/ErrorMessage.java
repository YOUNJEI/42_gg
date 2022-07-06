package com.example.gg42.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    TOKEN_EXPIRED   ("TOK_EXPIRED", "토큰 만료"),
    TOKEN_INVALID   ("TOK_INVALID", "유효하지 않은 토큰");

    private String code;
    private String desc;
}
