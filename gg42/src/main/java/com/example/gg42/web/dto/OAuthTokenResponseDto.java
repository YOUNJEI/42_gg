package com.example.gg42.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthTokenResponseDto {
    private String access_token;
    private String token_type;
    private long expires_in;
    private String refresh_token;
    private String scope;
}
