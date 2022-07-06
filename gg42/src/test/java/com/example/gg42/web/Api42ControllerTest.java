package com.example.gg42.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class Api42ControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Value("${TESTOAUTHACCESSTOKEN}")
    private String access_token;

    private MockMvc mockMvc;

    @BeforeEach
    public void SetUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void API_ME_호출테스트() throws Exception {
        // given
        final String url = "/api/v1/me";
        final String expectedName = "youko";
        final Cookie cookie = new Cookie("Authorization", "Bearer " + access_token);

        // when
        MvcResult result = mockMvc.perform(get(url)
                        .cookie(cookie))
                        .andExpect(status().isOk())
                        .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("id");
        assertThat(content).contains("email");
        assertThat(content).contains("login");
    }
}
