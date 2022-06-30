package com.example.gg42.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Api42Service {
    public String ApiMe(String accessToken) {
        // API URL
        String url = "https://api.intra.42.fr/v2/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity entity = new HttpEntity(headers);

        // GET request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
