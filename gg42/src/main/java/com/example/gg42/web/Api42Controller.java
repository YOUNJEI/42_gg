package com.example.gg42.web;

import com.example.gg42.service.Api42Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;

@RequiredArgsConstructor
@Controller
public class Api42Controller {
    private final Api42Service api42Service;

    @ResponseBody
    @GetMapping("/api/v1/me")
    public String ApiMe(@CookieValue(value = "Authorization", required = false) String accessToken) {
        try {
            return api42Service.ApiMe(URLDecoder.decode(accessToken, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}