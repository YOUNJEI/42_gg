package com.example.gg42.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class OAuthTokenRequestDto {
    String grant_type;
    String client_id;
    String client_secret;
    String code;
    String redirect_uri;
    String refresh_token;

    @Builder
    OAuthTokenRequestDto(String grant_type, String client_id, String client_secret, String code,
                         String redirect_uri, String refresh_token) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.code = code;
        this.redirect_uri = redirect_uri;
        this.refresh_token = refresh_token;
    }

    public MultiValueMap<String, String> toEntity() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grant_type);
        map.add("client_id", client_id);
        map.add("client_secret", client_secret);
        map.add("code", code);
        map.add("redirect_uri", redirect_uri);
        map.add("refresh_token", refresh_token);
        return map;
    }
}
