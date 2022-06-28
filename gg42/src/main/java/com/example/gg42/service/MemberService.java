package com.example.gg42.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import sun.security.util.Debug;

@Service
public class MemberService {

    public String GetAccessToken(String apiUid, String apiSecret, String code, String apiRedirectUri) {
        String url = "https://api.intra.42.fr/oauth/token";

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
        //String response = restTemplate.postForObject(url, entity, String.class);
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // JSON parsing
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(response.getBody().toString());
            return jsonObject.get("access_token").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
