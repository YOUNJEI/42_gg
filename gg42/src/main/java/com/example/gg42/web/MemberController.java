package com.example.gg42.web;

import com.example.gg42.service.MemberService;
import com.example.gg42.web.dto.MemberLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String GetAuthCode() {
        String requestURL = "https://api.intra.42.fr/oauth/authorize?"
                + "client_id=" + Config.getApiUid() + "&"
                + "redirect_uri=" + Config.getApiRedirectUri() + "&"
                + "response_type=" + "code";
        return "redirect:" + requestURL;
    }

    @GetMapping("/api/v1/login")
    public String Login(@RequestParam(value = "code", required = false) String code,
                            HttpServletRequest request, RedirectAttributes rttr) {
        if (code == null) {
            rttr.addFlashAttribute("msg", "권한 승인이 필요합니다!");
        }
        else {
            MemberLoginRequestDto response = memberService.MemberLogin(Config.getApiUid(), Config.getApiSecret(), code, Config.getApiRedirectUri());
            if (response == null) {
                rttr.addFlashAttribute("msg", "로그인 오류");
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute("refreshToken", response.getRefreshToken());
                SetCookie("Authorization", "Bearer " + response.getAccessToken());
                SetCookie("userName", response.getUserName());
            }
        }
        return "redirect:" + "/";
    }

    @GetMapping("/api/v1/logout")
    public String Logout(HttpSession session) {

        // 세션과 쿠키 삭제
        RemoveCookie("userName");
        RemoveCookie("Authorization");
        session.invalidate();
        return "redirect:" + "/";
    }

    private static void SetCookie(String key, String value) {
        RequestAttributes servletContainer = RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = ((ServletRequestAttributes)servletContainer).getResponse();

        try {
            Cookie cookie = new Cookie(key,
                    URLEncoder.encode(value, "UTF-8"));
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RemoveCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");

        RequestAttributes servletContainer = RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = ((ServletRequestAttributes)servletContainer).getResponse();

        httpServletResponse.addCookie(cookie);
    }
}
