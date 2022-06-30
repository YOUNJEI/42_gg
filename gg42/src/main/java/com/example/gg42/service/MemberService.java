package com.example.gg42.service;

import com.example.gg42.domain.member.MemberRepository;
import com.example.gg42.web.dto.MemberLoginRequestDto;
import com.example.gg42.web.dto.ApiMeResponseDto;
import com.example.gg42.web.dto.OAuthTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberLoginRequestDto MemberLogin(String apiUid, String apiSecret, String code, String apiRedirectUri) {
        final String url = "https://api.intra.42.fr/oauth/token";

        // POST 보내기 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // POST 보내기 위한 툴
        RestTemplate restTemplate = new RestTemplate();

        // POST BODY 설정
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", apiUid);
        map.add("client_secret", apiSecret);
        map.add("code", code);
        map.add("redirect_uri", apiRedirectUri);

        // POST 요청
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        try {
            ResponseEntity<OAuthTokenResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, OAuthTokenResponseDto.class);
            ApiMeResponseDto apiMeResponseDto = CallApiMe(response.getBody().getAccess_token());

            return MemberLoginRequestDto.builder()
                    .userName(apiMeResponseDto.getLogin())
                    .accessToken(response.getBody().getAccess_token())
                    .refreshToken(response.getBody().getRefresh_token())
                    .expires_in(response.getBody().getExpires_in())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ApiMeResponseDto CallApiMe(String accessToken) {
        // API URL
        final String url = "https://api.intra.42.fr/v2/me";

        // 헤더에 액세스 토큰 삽입
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity entity = new HttpEntity<>(headers);

        // GET request
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ApiMeResponseDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, ApiMeResponseDto.class);

        memberRepository.save(response.getBody().toEntity());
        return response.getBody();
    }
}
