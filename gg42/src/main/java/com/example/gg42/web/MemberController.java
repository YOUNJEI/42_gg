package com.example.gg42.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class MemberController {

    @Value("${42APIUID}")
    private String apiUid;

    @Value("${42APISECRET}")
    private String apiSecret;

    @Value("${42APIREDIRECTURI}")
    private String apiRedirectUri;

    @GetMapping("/login")
    public String GetAuthCode() {
        String requestURL = "https://api.intra.42.fr/oauth/authorize?"
                + "client_id=" + apiUid + "&"
                + "redirect_uri=" + apiRedirectUri + "&"
                + "response_type=" + "code";

        return "redirect:" + requestURL;
    }

    @GetMapping("/api/v1/login")
    @ResponseBody
    public String GetAccessToken(@RequestParam("code") String code) {
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

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
        String response = restTemplate.postForObject(url, entity, String.class);

        return response;
    }


}
