package com.example.gg42.service;

import com.example.gg42.service.exception.CustomException;
import com.example.gg42.service.exception.ErrorMessage;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Api42Service {
/*
    public String Call(String accessToken) {
        final String url = "https://api.intra.42.fr/oauth/token/info";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", accessToken);
            HttpEntity entity = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new CustomException(ErrorMessage.TOKEN_EXPIRED);
            }
        } catch (CustomException e) {

        } finally {

        }
    }

 */
    public String ApiMe(String accessToken) {
        // API URL
        final String url = "https://api.intra.42.fr/v2/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity entity = new HttpEntity(headers);

        // GET request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
