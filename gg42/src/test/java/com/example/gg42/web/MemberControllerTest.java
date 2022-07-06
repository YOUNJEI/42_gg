package com.example.gg42.web;

import com.example.gg42.domain.member.Member;
import com.example.gg42.domain.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @Value("${TESTOAUTHLOGINCODE}")
    private String code;

    private MockMvc mockMvc;

    @BeforeEach
    public void SetUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterEach
    public void clear() {
        memberRepository.deleteAll();
    }

    @Test
    public void 유효한_인증코드로_로그인시도_유효한_인증코드_제공시_에러가없어야함() throws Exception {
        // given
        final String url = "/api/v1/login?code=" + code;
        final String expectedName = "youko";

        // when
        mockMvc.perform(get(url))
                .andExpect(redirectedUrl("/"))
                .andExpect(cookie().value("userName", expectedName));

        // then
        List<Member> all = memberRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo(expectedName);
    }
}
