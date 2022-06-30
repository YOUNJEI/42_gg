package com.example.gg42.web;

import com.example.gg42.domain.member.Member;
import com.example.gg42.service.MemberService;
import com.example.gg42.web.dto.MemberLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String Login(@RequestParam(value = "code", required = false) String code,
                            HttpServletRequest request, RedirectAttributes rttr) {
        if (code == null) {
            rttr.addFlashAttribute("msg", "권한 승인이 필요합니다!");
        }
        else {
            MemberLoginRequestDto response = memberService.MemberLogin(apiUid, apiSecret, code, apiRedirectUri);
            if (response.getUserName() == null) {
                rttr.addFlashAttribute("msg", "로그인 오류");
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute("refreshToken", response.getRefreshToken());
                SetCookie(response.getUserName(), response.getAccessToken(), response.getExpires_in());
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

    private void SetCookie(String userName, String accessToken, Long expires) {
        RequestAttributes servletContainer = RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = ((ServletRequestAttributes)servletContainer).getResponse();

        try {
            Cookie cookie = new Cookie("Authorization",
                    URLEncoder.encode("Bearer " + accessToken, "UTF-8"));
            cookie.setMaxAge(expires.intValue());
            cookie.setPath("/");
            httpServletResponse.addCookie(cookie);

            Cookie cookie2 = new Cookie("userName", userName);
            cookie2.setMaxAge(expires.intValue());
            cookie2.setPath("/");
            httpServletResponse.addCookie(cookie2);
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
