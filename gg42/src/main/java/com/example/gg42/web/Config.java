package com.example.gg42.web;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class Config {
    @Getter
    @Value("${42APIUID}")
    private static String apiUid;

    @Getter
    @Value("${42APISECRET}")
    private static String apiSecret;

    @Getter
    @Value("${42APIREDIRECTURI}")
    private static String apiRedirectUri;
}
