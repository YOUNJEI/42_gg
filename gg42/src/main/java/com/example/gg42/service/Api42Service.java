package com.example.gg42.service;

import com.example.gg42.service.exception.CustomException;
import com.example.gg42.service.exception.ErrorMessage;
import com.example.gg42.web.Config;
import com.example.gg42.web.MemberController;
import com.example.gg42.web.dto.OAuthTokenRequestDto;
import com.example.gg42.web.dto.OAuthTokenResponseDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class Api42Service {

    // 토큰 만료여부를 확인하고 필요시 갱신하여 새로운 accessToken을 리턴해주는 메소드
    public String IsExpiredToken(String accessToken) {
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
            return accessToken;
        } catch (CustomException e) {
            final String url = "https://api.intra.42.fr/oauth/token";

            // 세션 정보 불러오기
            RequestAttributes servletContainer = RequestContextHolder.getRequestAttributes();
            HttpServletRequest httpServletRequest = ((ServletRequestAttributes)servletContainer).getRequest();
            HttpSession session = httpServletRequest.getSession();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> map = OAuthTokenRequestDto.builder()
                    .grant_type("refresh_token")
                    .client_id(Config.getApiUid())
                    .client_secret(Config.getApiSecret())
                    .refresh_token(session.getAttribute("refreshToken").toString())
                    .redirect_uri(Config.getApiRedirectUri())
                    .build().toEntity();

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
            ResponseEntity<OAuthTokenResponseDto> response = restTemplate.exchange(url, HttpMethod.POST,
                    entity, OAuthTokenResponseDto.class);

            MemberController.SetCookie("Authorization", "Bearer " + response.getBody().getAccess_token());
            session.setAttribute("refreshToken", response.getBody().getRefresh_token());
            return "Bearer " + response.getBody().getAccess_token();
        }
    }

    public String ApiMe(String accessToken) {
        // API URL
        final String url = "https://api.intra.42.fr/v2/me";

        accessToken = IsExpiredToken(accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", accessToken);
        HttpEntity entity = new HttpEntity(headers);

        // GET request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}