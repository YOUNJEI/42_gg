package com.example.gg42.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {
    private String userName;
    private String accessToken;
    private String refreshToken;
    private Long expires_in;

    @Builder
    public MemberLoginRequestDto(String userName, String accessToken, String refreshToken, Long expires_in) {
        this.userName = userName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expires_in = expires_in;
    }
}
