package com.example.gg42.config.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Config {
    @Getter
    private static String apiUid;

    @Getter
    private static String apiSecret;

    @Getter
    private static String apiRedirectUri;

    @Value("${42APIUID}")
    public void setApiUid(String value) {
        apiUid = value;
    }

    @Value("${42APISECRET}")
    public void setApiSecret(String value) {
        apiSecret = value;
    }

    @Value("${42APIREDIRECTURI}")
    public void setApiRedirectUri(String value) {
        apiRedirectUri = value;
    }
}