package com.example.gg42.web;

import com.example.gg42.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
            String userName = memberService.MemberLogin(apiUid, apiSecret, code, apiRedirectUri);
            if (userName == null) {
                rttr.addFlashAttribute("msg", "로그인 오류");
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute("userName", userName);
            }
        }
        return "redirect:" + "/";
    }

    @GetMapping("/api/v1/logout")
    public String Logout(HttpSession session) {

        // 세션 삭제
        session.invalidate();

        return "redirect:" + "/";
    }
}
