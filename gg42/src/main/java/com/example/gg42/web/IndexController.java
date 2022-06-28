package com.example.gg42.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class IndexController {

    @Value("${42APIUID}")
    private String apiUid;

    @Value("${42APIREDIRECTURI}")
    private String apiRedirectUri;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        String requestURL = "https://api.intra.42.fr/oauth/authorize?"
                + "client_id=" + apiUid + "&"
                + "redirect_uri=" + apiRedirectUri + "&"
                + "response_type=" + "code";

        return "redirect:" + requestURL;
    }
}
