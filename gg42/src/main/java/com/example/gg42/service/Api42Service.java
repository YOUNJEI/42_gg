package com.example.gg42.service;

import com.example.gg42.service.exception.CustomException;
import com.example.gg42.service.exception.ErrorMessage;
import com.example.gg42.web.dto.OAuthTokenRequestDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class Api42Service {

    public String Call(String accessToken) {
        try {
            final String url = "https://api.intra.42.fr/oauth/token/info";
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", accessToken);
            HttpEntity entity = new HttpEntity(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new CustomException(ErrorMessage.TOKEN_EXPIRED);
            }
        } catch (CustomException e) {
            final String url = "https://api.intra.42.fr/oauth/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> map = OAuthTokenRequestDto.builder()
                    .grant_type("refresh_token")
                    .client_id(apiUid)
                    .client_secret(apiSecret)
                    .code(code)
                    .redirect_uri(apiRedirectUri)
                    .build().toEntity();
            // POST 요청
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        } finally {

        }
    }

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
