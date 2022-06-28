package com.example.gg42.web;

import com.example.gg42.service.MemberService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

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
    public String GetAccessToken(@RequestParam(value = "code", required = false) String code,
                                 @RequestParam(value = "error", required = false) String error) throws Exception {
        if (code == null) {
            throw new IllegalStateException("승인이 필요합니다.");
        }
        memberService.GetAccessToken(apiUid, apiSecret, code, apiRedirectUri);
        return "redirect:" + "/";
    }
}
